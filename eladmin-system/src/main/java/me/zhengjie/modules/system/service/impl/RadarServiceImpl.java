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

import me.zhengjie.modules.system.domain.Radar;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.RadarRepository;
import me.zhengjie.modules.system.service.RadarService;
import me.zhengjie.modules.system.service.dto.RadarDto;
import me.zhengjie.modules.system.service.dto.RadarQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.RadarMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
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
* @date 2023-05-16
**/
@Service
@RequiredArgsConstructor
public class RadarServiceImpl implements RadarService {

    private final RadarRepository radarRepository;
    private final RadarMapper radarMapper;

    @Override
    public Map<String,Object> queryAll(RadarQueryCriteria criteria, Pageable pageable){
        Page<Radar> page = radarRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(radarMapper::toDto));
    }

    @Override
    public List<RadarDto> queryAll(RadarQueryCriteria criteria){
        return radarMapper.toDto(radarRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public RadarDto findById(Long radarId) {
        Radar radar = radarRepository.findById(radarId).orElseGet(Radar::new);
        ValidationUtil.isNull(radar.getRadarId(),"Radar","radarId",radarId);
        return radarMapper.toDto(radar);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RadarDto create(Radar resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setRadarId(snowflake.nextId()); 
        return radarMapper.toDto(radarRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Radar resources) {
        Radar radar = radarRepository.findById(resources.getRadarId()).orElseGet(Radar::new);
        ValidationUtil.isNull( radar.getRadarId(),"Radar","id",resources.getRadarId());
        radar.copy(resources);
        radarRepository.save(radar);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long radarId : ids) {
            radarRepository.deleteById(radarId);
        }
    }

    @Override
    public void download(List<RadarDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RadarDto radar : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("雷达文件名称", radar.getRadarName());
            map.put("来源公司id", radar.getCompanyId());
            map.put("经纬度坐标", radar.getLongLat());
            map.put("来源城市", radar.getCityName());
            map.put("道路名称", radar.getRoadName());
            map.put("雷达型号", radar.getRadarType());
            map.put("天线主频", radar.getBasicFrequency());
            map.put("时间窗", radar.getTimeWindow());
            map.put("采样间隔", radar.getSamplingInterval());
            map.put("测量宽度", radar.getMeasuringWidth());
            map.put("数据大小", radar.getDataSize());
            map.put("总道数", radar.getTraceNum());
            map.put("通道数", radar.getChannel());
            map.put("起始里程", radar.getStartMileage());
            map.put("终止里程", radar.getEndMileage());
            map.put("删除标志", radar.getDelFlag());
            map.put("原始雷达数据存放地址", radar.getRadAdd());
            map.put("Gps文件存放地址", radar.getGpsAdd());
            map.put("解析后雷达数据存放地址", radar.getAnaAdd());
            map.put("雷达图片存放地址", radar.getPicAdd());
            map.put("创建者", radar.getCreateBy());
            map.put("创建时间", radar.getCreateTime());
            map.put("更新者", radar.getUpdateBy());
            map.put("更新时间", radar.getUpdateTime());
            map.put("备注", radar.getRemark());
            map.put("备注", radar.getRemark1());
            map.put("备注", radar.getRemark2());
            map.put("备注", radar.getRemark3());
            map.put("备注", radar.getRemark4());
            map.put("备注", radar.getRemark5());
            map.put("备注", radar.getRemark6());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}