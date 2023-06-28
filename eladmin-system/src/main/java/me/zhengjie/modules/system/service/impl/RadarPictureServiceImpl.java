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
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
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
* @website https://el-admin.vip
* @description 服务实现
* @author Zuo Haitao
* @date 2023-06-28
**/
@Service
@RequiredArgsConstructor
public class RadarPictureServiceImpl implements RadarPictureService {

    private final RadarPictureRepository radarPictureRepository;
    private final RadarPictureMapper radarPictureMapper;
    private final FileProperties properties;

    @Override
    public Map<String,Object> queryAll(RadarPictureQueryCriteria criteria, Pageable pageable){
        Page<RadarPicture> page = radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(radarPictureMapper::toDto));
    }

    @Override
//    这段代码的目的是根据给定的查询条件criteria，在数据库中查询满足条件的RadarPicture对象，
//    并将查询结果转换为RadarPictureDto类型的列表返回。
    public List<RadarPictureDto> queryAll(RadarPictureQueryCriteria criteria){
        return radarPictureMapper.toDto(radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RadarPictureDto findById(Integer id) {
        RadarPicture radarPicture = radarPictureRepository.findById(id).orElseGet(RadarPicture::new);
        ValidationUtil.isNull(radarPicture.getId(),"RadarPicture","id",id);
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
        ValidationUtil.isNull( radarPicture.getId(),"RadarPicture","id",resources.getId());
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
            Map<String,Object> map = new LinkedHashMap<>();
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
        RadarPictureQueryCriteria queryCriteria = new RadarPictureQueryCriteria();
        queryCriteria.setRadarId(radarId);
        List<RadarPictureDto> radarPictureDtoList = radarPictureMapper.toDto(radarPictureRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, queryCriteria, criteriaBuilder)));
        return radarPictureDtoList;
    }

    @Override
//    ↓表示该方法在执行过程中将被包装在一个事务中，并且在出现异常时进行回滚。
    @Transactional(rollbackFor = Exception.class)
    public RadarPicture uploadPicture(String id, MultipartFile multipartFile) {
//        1.上传文件的大小是否超过了设定的最大值。properties.getMaxSize()获取了最大文件大小的配置信息。
        FileUtil.checkSize(properties.getMaxSize(), multipartFile.getSize());
//        2.获取上传文件的后缀名。
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
//        3.根据文件后缀名获取文件类型。
        String type = FileUtil.getFileType(suffix);
//        4.文件写入磁盘（将上传的文件保存到指定路径中。）获取了文件保存路径的配置信息。
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type +  File.separator);
//        5.如果上传文件失败（file为空），则抛出一个BadRequestException异常，提示上传失败。
        if(ObjectUtil.isNull(file)){
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
        }catch (Exception e){
//            如果在保存过程中出现异常，即catch块中的代码被执行，调用FileUtil.del(file)删除之前上传的文件，并将异常继续抛出
            FileUtil.del(file);
            throw e;
        }

    }
}