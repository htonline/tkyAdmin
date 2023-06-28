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
import me.zhengjie.modules.system.domain.Radar;
import me.zhengjie.modules.system.service.RadarService;
import me.zhengjie.modules.system.service.dto.RadarQueryCriteria;
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
@Api(tags = "radar管理")
@RequestMapping("/api/radar")
public class RadarController {

    private final RadarService radarService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('radar:list')")
    public void exportRadar(HttpServletResponse response, RadarQueryCriteria criteria) throws IOException {
        radarService.download(radarService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询radar")
    @ApiOperation("查询radar")
    @PreAuthorize("@el.check('radar:list')")
    public ResponseEntity<Object> queryRadar(RadarQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(radarService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增radar")
    @ApiOperation("新增radar")
    @PreAuthorize("@el.check('radar:add')")
    public ResponseEntity<Object> createRadar(@Validated @RequestBody Radar resources){
        return new ResponseEntity<>(radarService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改radar")
    @ApiOperation("修改radar")
    @PreAuthorize("@el.check('radar:edit')")
    public ResponseEntity<Object> updateRadar(@Validated @RequestBody Radar resources){
        radarService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除radar")
    @ApiOperation("删除radar")
    @PreAuthorize("@el.check('radar:del')")
    public ResponseEntity<Object> deleteRadar(@RequestBody Long[] ids) {
        radarService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}