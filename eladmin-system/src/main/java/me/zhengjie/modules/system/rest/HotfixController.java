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
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.config.FileProperties;
import me.zhengjie.modules.system.domain.Hotfix;
import me.zhengjie.modules.system.service.HotfixService;
import me.zhengjie.modules.system.service.dto.HotfixQueryCriteria;
import me.zhengjie.utils.FileUtils;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author wuxiaoxuan
* @date 2022-08-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/hotfix管理")
@RequestMapping("/api/hotfix")
public class HotfixController {

    private final HotfixService hotfixService;
    private final FileProperties properties;
    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('hotfix:list')")
    public void exportHotfix(HttpServletResponse response, HotfixQueryCriteria criteria) throws IOException {
        hotfixService.download(hotfixService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/hotfix")
    @ApiOperation("查询api/hotfix")
    @PreAuthorize("@el.check('hotfix:list')")
    public ResponseEntity<Object> queryHotfix(HotfixQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(hotfixService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/hotfix")
    @ApiOperation("新增api/hotfix")
    @PreAuthorize("@el.check('hotfix:add')")
    public ResponseEntity<Object> createHotfix(@Validated @RequestBody Hotfix resources){
        return new ResponseEntity<>(hotfixService.create(resources),HttpStatus.CREATED);
    }

//    @PostMapping
    @Log("获得api/hotfix")
    @AnonymousGetMapping(value = "/gethotfixpag")
    public void getHotfix(HttpServletRequest request, HttpServletResponse response){
        File file = new File(properties.getPath().getPath(),"patch_signed_7zip.apk");
        try {
        if (file.exists()){
            HttpHeaders headers = new HttpHeaders();
             headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置文件名
            headers.setContentDispositionFormData("attachment", file.getName());
            FileInputStream in = null;
            in = new FileInputStream(file);
            response.reset();
            response.setHeader("content-disposition",
                    "attachment;fileName=" + file.getName());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding("utf-8");
            int count;
            byte[] by = new byte[1024];
            //通过response对象回去outputStream流
            ServletOutputStream output = response.getOutputStream();
            while ((count = in.read(by)) != -1) {
                //将缓存区的数据输出到浏览器
                output.write(by, 0, count);
            }
            in.close();
            output.flush();
            output.close();
            return;
//            return new ResponseEntity<>(FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
        }
        else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PutMapping
    @Log("修改api/hotfix")
    @ApiOperation("修改api/hotfix")
    @PreAuthorize("@el.check('hotfix:edit')")
    public ResponseEntity<Object> updateHotfix(@Validated @RequestBody Hotfix resources){
        hotfixService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/hotfix")
    @ApiOperation("删除api/hotfix")
    @PreAuthorize("@el.check('hotfix:del')")
    public ResponseEntity<Object> deleteHotfix(@RequestBody Integer[] ids) {
        hotfixService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}