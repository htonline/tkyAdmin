/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.system.domain.TkyDetectionInformation;
import me.zhengjie.modules.system.service.TkyTestInformationService;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.TkyDetectionInformationRepository;
import me.zhengjie.modules.system.service.TkyDetectionInformationService;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationDto;
import me.zhengjie.modules.system.service.dto.TkyDetectionInformationQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.TkyDetectionInformationMapper;
import okhttp3.*;
import org.apache.hc.core5.util.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static me.zhengjie.modules.system.verify.RsaUtil.jwt;

/**
 * @author wuxiaoxuan
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2022-06-05
 **/
@Service
@RequiredArgsConstructor
public class TkyDetectionInformationServiceImpl implements TkyDetectionInformationService {
    private final FileProperties properties;
    private final TkyDetectionInformationRepository tkyDetectionInformationRepository;
    private final TkyDetectionInformationMapper tkyDetectionInformationMapper;
    @Override
    public Map<String, Object> queryAll(TkyDetectionInformationQueryCriteria criteria, Pageable pageable) {
        Page<TkyDetectionInformation> page = tkyDetectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(tkyDetectionInformationMapper::toDto));
    }

    @Override
    public List<TkyDetectionInformationDto> queryAll(TkyDetectionInformationQueryCriteria criteria) {
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public TkyDetectionInformationDto findById(Integer id) {
        TkyDetectionInformation tkyDetectionInformation = tkyDetectionInformationRepository.findById(id).orElseGet(TkyDetectionInformation::new);
        ValidationUtil.isNull(tkyDetectionInformation.getId(), "TkyDetectionInformation", "id", id);
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TkyDetectionInformationDto create(TkyDetectionInformation resources) {
        return tkyDetectionInformationMapper.toDto(tkyDetectionInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TkyDetectionInformation resources) {
        TkyDetectionInformation tkyDetectionInformation = tkyDetectionInformationRepository.findById(resources.getId()).orElseGet(TkyDetectionInformation::new);
        ValidationUtil.isNull(tkyDetectionInformation.getId(), "TkyDetectionInformation", "id", resources.getId());
        tkyDetectionInformation.copy(resources);
        tkyDetectionInformationRepository.save(tkyDetectionInformation);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            tkyDetectionInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TkyDetectionInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TkyDetectionInformationDto tkyDetectionInformation : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("报验单编号", tkyDetectionInformation.getBydbh());
            map.put("用户账号", tkyDetectionInformation.getAccount());
            map.put("试验类型", tkyDetectionInformation.getTestType());
            map.put("实际检测开始里程", tkyDetectionInformation.getSjstartMile());
            map.put("实际检测结束里程", tkyDetectionInformation.getSjstopMile());
            map.put("厚度数据", tkyDetectionInformation.getAppFileTypeRadar());
            map.put("现场照片", tkyDetectionInformation.getAppFileTypePhoto());
            map.put("备注1", tkyDetectionInformation.getBeizhu1());
            map.put(" beizhu2", tkyDetectionInformation.getBeizhu2());
            map.put(" beizhu3", tkyDetectionInformation.getBeizhu3());
            map.put(" beizhu4", tkyDetectionInformation.getBeizhu4());
            map.put(" beizhu5", tkyDetectionInformation.getBeizhu5());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void uploadDZSdata(String bydbh, String sjstartMile, String sjstopMile, String filename, String i, String account,String cxbh) {
        String urlstring = "https://apps.r93535.com/services/ZL02203/sdljzljcapi/sd_projectinspectiondeclaration/uploadFxjg?bydbh=" + bydbh
                + "&app_file_type=" + i
                + "&sjstart_mile=" + sjstartMile
                + "&sjstop_mile=" + sjstopMile
                + "&attachment_categoryid="+cxbh
                + "&account=" + account;
        //"http://39.104.163.161:8888/demo/sd_projectinspectiondeclaration/uploadFxjg?bydbh=CZSCZQ-2-20220526-002&app_file_type=2&sjstart_mile=DK11+111&sjstop_mile=DK11+111"
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

//        File file = new File("ceshi.raw");//filename
        String path = null;
        if (i.equals("0")) {
            path = properties.getPath().getAvatar();
        } else if (i.equals("3")) {
            path = properties.getPath().getPath();
        }
        File file = new File(path, filename);
        if (!file.exists()) {
            return;
        }

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();
        Request request = new Request.Builder()
                .url(urlstring)
                .method("POST", body)
                .addHeader("Cookie", "CRBIMSSOJWT=" + jwt)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.code()+"    "+response.body().string()+"   "+response.message().toString());
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    @Override
    public void downloadFile(String radardata, String photodata, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();
        if (!TextUtils.isEmpty(radardata)) {
            files.add(new File(properties.getPath().getPath(), radardata));
        }
        if (!TextUtils.isEmpty(photodata)) {
            files.add(new File(properties.getPath().getAvatar(), photodata));
        }

        // files.add(new File("C:\\eladmin\\avatar\\"+tupianPath2));
        //files.add(new File("C:\\eladmin\\avatar\\"+tupianPath3));
//        files.add(new File("C:\\eladmin\\file\\"+wenjianPath));
//        files.add(new File())
        //  String zipPath = "C:\\eladmin\\采集数据"+ ".zip";
        File zf = new File("D:\\eladmin\\采集数据" + ".zip");
        byte[] buf = new byte[1024];
        // 获取输出流
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.reset(); // 重点突出
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + zf.getName());

            // ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out = new ZipOutputStream(bos);
            for (int i = 0; i < files.size(); i++) {
                FileInputStream in = new FileInputStream(files.get(i));
                // 给列表中的文件单独命名
                out.putNextEntry(new ZipEntry(files.get(i).getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            bos.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  FileUtil.downloadFile(request, response, zf, true);
    }
}