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

import me.zhengjie.modules.system.domain.RadarPicture;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
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
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
}