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

import cn.hutool.core.util.ObjectUtil;
import me.zhengjie.config.FileProperties;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.RadarDiseasetypePictures;
import me.zhengjie.modules.system.domain.RadarPicture;
import me.zhengjie.utils.*;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.RadarPictureRepository;
import me.zhengjie.modules.system.service.RadarPictureService;
import me.zhengjie.modules.system.service.dto.RadarPictureDto;
import me.zhengjie.modules.system.service.dto.RadarPictureQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.RadarPictureMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zuo Haitao
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2023-06-28
 **/
@Service
@RequiredArgsConstructor
public class RadarPictureServiceImpl implements RadarPictureService {

    private final RadarPictureRepository radarPictureRepository;
    private final RadarPictureMapper radarPictureMapper;
    private final FileProperties properties;

    @Override
    public Map<String, Object> queryAll(RadarPictureQueryCriteria criteria, Pageable pageable) {
        Page<RadarPicture> page = radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(radarPictureMapper::toDto));
    }

    @Override
//    这段代码的目的是根据给定的查询条件criteria，在数据库中查询满足条件的RadarPicture对象，
//    并将查询结果转换为RadarPictureDto类型的列表返回。
    public List<RadarPictureDto> queryAll(RadarPictureQueryCriteria criteria) {
        return radarPictureMapper.toDto(radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public RadarPictureDto findById(Integer id) {
        RadarPicture radarPicture = radarPictureRepository.findById(id).orElseGet(RadarPicture::new);
        ValidationUtil.isNull(radarPicture.getId(), "RadarPicture", "id", id);
        return radarPictureMapper.toDto(radarPicture);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RadarPictureDto create(RadarPicture resources) {
        return radarPictureMapper.toDto(radarPictureRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RadarPicture resources) {
        RadarPicture radarPicture = radarPictureRepository.findById(resources.getId()).orElseGet(RadarPicture::new);
        ValidationUtil.isNull(radarPicture.getId(), "RadarPicture", "id", resources.getId());
        radarPicture.copy(resources);
        radarPictureRepository.save(radarPicture);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            radarPictureRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RadarPictureDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RadarPictureDto radarPicture : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名称", radarPicture.getFileName());
            map.put("文件路径", radarPicture.getPath());
            map.put("创建时间", radarPicture.getCreateTime());
            map.put("对应的雷达id", radarPicture.getRadarId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<RadarPictureDto> findByRadarId(Integer radarId) {
//        创建一个与表radarPicture一致的实体类;
        RadarPictureQueryCriteria queryCriteria = new RadarPictureQueryCriteria();
//        将实体类的radarId属性设置为
        queryCriteria.setRadarId(radarId);
        List<RadarPictureDto> radarPictureDtoList = radarPictureMapper.toDto(radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder)));
        return radarPictureDtoList;
    }

    @Override
//    ↓表示该方法在执行过程中将被包装在一个事务中，并且在出现异常时进行回滚。
    @Transactional(rollbackFor = Exception.class)
    public RadarPicture uploadPicture(RadarDiseasetypePictures radarDiseasetypePictures, MultipartFile multipartFile) {
        String id = radarDiseasetypePictures.getId().toString();
//        1.上传文件的大小是否超过了设定的最大值。properties.getMaxSize()获取了最大文件大小的配置信息。
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
//        2.获取上传文件的后缀名。
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
//        3.根据文件后缀名获取文件类型。
        String type = FileUtil.getFileType(suffix);
//        4.文件写入磁盘（将上传的文件保存到指定路径中。）获取了文件保存路径的配置信息。
//        File.separator 是一个表示文件路径分隔符的常量，用于在不同操作系统上正确处理文件路径的分隔符。使得你的代码可以在不同的操作系统上运行而不需要手动更改文件路径分隔符。
//        properties.getPath().getPath(): C:\eladmin\file\
        String filePath = properties.getPath().getPath() + type;  // 文件存储路径(不包括文件名,文件名的添加在FileUtil.upload()方法中)
        if (radarDiseasetypePictures.getCategory1() != null && !radarDiseasetypePictures.getCategory1().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory1();
        }
        if (radarDiseasetypePictures.getCategory2() != null && !radarDiseasetypePictures.getCategory2().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory2();
        }
        if (radarDiseasetypePictures.getCategory3() != null && !radarDiseasetypePictures.getCategory3().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory3();
        }
        if (radarDiseasetypePictures.getCategory4() != null && !radarDiseasetypePictures.getCategory4().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory4();
        }
        if (radarDiseasetypePictures.getCategory5() != null && !radarDiseasetypePictures.getCategory5().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory5();
        }
        if (radarDiseasetypePictures.getCategory6() != null && !radarDiseasetypePictures.getCategory6().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory6();
        }
        if (radarDiseasetypePictures.getCategory7() != null && !radarDiseasetypePictures.getCategory7().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory7();
        }
        if (radarDiseasetypePictures.getCategory8() != null && !radarDiseasetypePictures.getCategory8().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory8();
        }
        if (radarDiseasetypePictures.getCategory9() != null && !radarDiseasetypePictures.getCategory9().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory9();
        }
        if (radarDiseasetypePictures.getCategory10() != null && !radarDiseasetypePictures.getCategory10().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory10();
        }
        if (radarDiseasetypePictures.getCategory11() != null && !radarDiseasetypePictures.getCategory11().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory11();
        }
        if (radarDiseasetypePictures.getCategory12() != null && !radarDiseasetypePictures.getCategory12().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory12();
        }
        if (radarDiseasetypePictures.getCategory13() != null && !radarDiseasetypePictures.getCategory13().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory13();
        }
        if (radarDiseasetypePictures.getCategory14() != null && !radarDiseasetypePictures.getCategory14().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory14();
        }
        if (radarDiseasetypePictures.getCategory15() != null && !radarDiseasetypePictures.getCategory15().equals("null")) {
            filePath += File.separator + radarDiseasetypePictures.getCategory15();
        }

        filePath += File.separator;
        File file = FileUtil.upload(multipartFile, filePath);
//        5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
//            6.将文件录入数据库
            RadarPicture radarPicture = new RadarPicture();
            radarPicture.setRadarId(Integer.parseInt(id));
            radarPicture.setPath(file.getPath());
            radarPicture.setFileName(file.getName());
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());  // 获取当前时间戳(sql)
            radarPicture.setCreateTime(currentTime);
            return radarPictureRepository.save(radarPicture);
        } catch (Exception e) {
//            如果在保存过程中出现异常，即catch块中的代码被执行，调用FileUtil.del(file)删除之前上传的文件，并将异常继续抛出
            FileUtil.del(file);
            throw e;
        }

    }
}