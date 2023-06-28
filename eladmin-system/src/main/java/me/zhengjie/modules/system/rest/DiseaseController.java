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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Disease;
import me.zhengjie.modules.system.service.DiseaseService;
import me.zhengjie.modules.system.service.dto.DiseaseQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Zuo Haitao
* @date 2023-05-16
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "disease管理")
@RequestMapping("/api/disease")
public class DiseaseController {

    private final DiseaseService diseaseService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('disease:list')")
    public void exportDisease(HttpServletResponse response, DiseaseQueryCriteria criteria) throws IOException {
        diseaseService.download(diseaseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询disease")
    @ApiOperation("查询disease")
    @PreAuthorize("@el.check('disease:list')")
    public ResponseEntity<Object> queryDisease(DiseaseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(diseaseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增disease")
    @ApiOperation("新增disease")
    @PreAuthorize("@el.check('disease:add')")
    public ResponseEntity<Object> createDisease(@Validated @RequestBody Disease resources){
        return new ResponseEntity<>(diseaseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改disease")
    @ApiOperation("修改disease")
    @PreAuthorize("@el.check('disease:edit')")
    public ResponseEntity<Object> updateDisease(@Validated @RequestBody Disease resources){
        diseaseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除disease")
    @ApiOperation("删除disease")
    @PreAuthorize("@el.check('disease:del')")
    public ResponseEntity<Object> deleteDisease(@RequestBody Long[] ids) {
        diseaseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}