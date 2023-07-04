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

import me.zhengjie.modules.system.domain.Company;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CompanyRepository;
import me.zhengjie.modules.system.service.CompanyService;
import me.zhengjie.modules.system.service.dto.CompanyDto;
import me.zhengjie.modules.system.service.dto.CompanyQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CompanyMapper;
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
* @author zuohaitao
* @date 2023-07-04
**/
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Map<String,Object> queryAll(CompanyQueryCriteria criteria, Pageable pageable){
        Page<Company> page = companyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(companyMapper::toDto));
    }

    @Override
    public List<CompanyDto> queryAll(CompanyQueryCriteria criteria){
        return companyMapper.toDto(companyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CompanyDto findById(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseGet(Company::new);
        ValidationUtil.isNull(company.getCompanyId(),"Company","companyId",companyId);
        return companyMapper.toDto(company);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyDto create(Company resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setCompanyId(snowflake.nextId()); 
        return companyMapper.toDto(companyRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Company resources) {
        Company company = companyRepository.findById(resources.getCompanyId()).orElseGet(Company::new);
        ValidationUtil.isNull( company.getCompanyId(),"Company","id",resources.getCompanyId());
        company.copy(resources);
        companyRepository.save(company);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long companyId : ids) {
            companyRepository.deleteById(companyId);
        }
    }

    @Override
    public void download(List<CompanyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CompanyDto company : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("公司名称", company.getCompanyName());
            map.put("联系电话", company.getCompanyPhone());
            map.put("备注", company.getRemark());
            map.put("备注1", company.getRemark1());
            map.put("备注2", company.getRemark2());
            map.put("备注3", company.getRemark3());
            map.put("备注4", company.getRemark4());
            map.put("备注5", company.getRemark5());
            map.put("备注6", company.getRemark6());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}