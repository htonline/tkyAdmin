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

import me.zhengjie.modules.system.domain.Exceptions;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ExceptionsRepository;
import me.zhengjie.modules.system.service.ExceptionsService;
import me.zhengjie.modules.system.service.dto.ExceptionsDto;
import me.zhengjie.modules.system.service.dto.ExceptionsQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ExceptionsMapper;
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
public class ExceptionsServiceImpl implements ExceptionsService {

    private final ExceptionsRepository exceptionsRepository;
    private final ExceptionsMapper exceptionsMapper;

    @Override
    public Map<String,Object> queryAll(ExceptionsQueryCriteria criteria, Pageable pageable){
        Page<Exceptions> page = exceptionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(exceptionsMapper::toDto));
    }

    @Override
    public List<ExceptionsDto> queryAll(ExceptionsQueryCriteria criteria){
        return exceptionsMapper.toDto(exceptionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ExceptionsDto findById(Long exceptionId) {
        Exceptions exceptions = exceptionsRepository.findById(exceptionId).orElseGet(Exceptions::new);
        ValidationUtil.isNull(exceptions.getExceptionId(),"Exceptions","exceptionId",exceptionId);
        return exceptionsMapper.toDto(exceptions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExceptionsDto create(Exceptions resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setExceptionId(snowflake.nextId()); 
        return exceptionsMapper.toDto(exceptionsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Exceptions resources) {
        Exceptions exceptions = exceptionsRepository.findById(resources.getExceptionId()).orElseGet(Exceptions::new);
        ValidationUtil.isNull( exceptions.getExceptionId(),"Exceptions","id",resources.getExceptionId());
        exceptions.copy(resources);
        exceptionsRepository.save(exceptions);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long exceptionId : ids) {
            exceptionsRepository.deleteById(exceptionId);
        }
    }

    @Override
    public void download(List<ExceptionsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExceptionsDto exceptions : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("异常名称", exceptions.getExceptionName());
            map.put("雷达文件名称", exceptions.getRadarName());
            map.put("岩土性质", exceptions.getSoilProperty());
            map.put("尺寸", exceptions.getDiseaseSize());
            map.put("经纬度坐标", exceptions.getLongLat());
            map.put("删除标志", exceptions.getDelFlag());
            map.put("创建者", exceptions.getCreateBy());
            map.put("创建时间", exceptions.getCreateTime());
            map.put("更新者", exceptions.getUpdateBy());
            map.put("更新时间", exceptions.getUpdateTime());
            map.put("备注", exceptions.getRemark());
            map.put("备注", exceptions.getRemark1());
            map.put("备注", exceptions.getRemark2());
            map.put("备注", exceptions.getRemark3());
            map.put("备注", exceptions.getRemark4());
            map.put("备注", exceptions.getRemark5());
            map.put("备注", exceptions.getRemark6());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}