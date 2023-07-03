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

import io.swagger.models.auth.In;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.RadarDiseasetypePictures;
import me.zhengjie.modules.system.domain.RadarPicture;
import me.zhengjie.modules.system.service.RadarPictureService;
import me.zhengjie.modules.system.service.dto.RadarPictureQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Zuo Haitao
* @date 2023-06-28
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/radarpicture管理")
@RequestMapping("/api/radarPicture")
public class RadarPictureController {

    private final RadarPictureService radarPictureService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('radarPicture:list')")
    public void exportRadarPicture(HttpServletResponse response, RadarPictureQueryCriteria criteria) throws IOException {
        radarPictureService.download(radarPictureService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/radarpicture")
    @ApiOperation("查询api/radarpicture")
    @PreAuthorize("@el.check('radarPicture:list')")
    public ResponseEntity<Object> queryRadarPicture(RadarPictureQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(radarPictureService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/radarpicture")
    @ApiOperation("新增api/radarpicture")
    @PreAuthorize("@el.check('radarPicture:add')")
    public ResponseEntity<Object> createRadarPicture(@Validated @RequestBody RadarPicture resources){
        return new ResponseEntity<>(radarPictureService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/radarpicture")
    @ApiOperation("修改api/radarpicture")
    @PreAuthorize("@el.check('radarPicture:edit')")
    public ResponseEntity<Object> updateRadarPicture(@Validated @RequestBody RadarPicture resources){
        radarPictureService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/radarpicture")
    @ApiOperation("删除api/radarpicture")
    @PreAuthorize("@el.check('radarPicture:del')")
    public ResponseEntity<Object> deleteRadarPicture(@RequestBody Integer[] ids) {
        radarPictureService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("上传图片")
    @PostMapping(value = "/uploadPicture")
    public ResponseEntity<Object> uploadPicture(@RequestParam String id, @RequestParam String category1,
                                                @RequestParam String category2, @RequestParam String category3,
                                                @RequestParam String category4, @RequestParam String category5,
                                                @RequestParam String category6, @RequestParam String category7,
                                                @RequestParam String category8, @RequestParam String category9,
                                                @RequestParam String category10, @RequestParam String category11,
                                                @RequestParam String category12, @RequestParam String category13,
                                                @RequestParam String category14, @RequestParam String category15,
                                                @RequestParam("file") MultipartFile file){
        RadarDiseasetypePictures radarDiseasetypePictures = new RadarDiseasetypePictures();
        radarDiseasetypePictures.setId(Integer.parseInt(id));
        radarDiseasetypePictures.setCategory1(category1);
        radarDiseasetypePictures.setCategory2(category2);
        radarDiseasetypePictures.setCategory3(category3);
        radarDiseasetypePictures.setCategory4(category4);
        radarDiseasetypePictures.setCategory5(category5);
        radarDiseasetypePictures.setCategory6(category6);
        radarDiseasetypePictures.setCategory7(category7);
        radarDiseasetypePictures.setCategory8(category8);
        radarDiseasetypePictures.setCategory9(category9);
        radarDiseasetypePictures.setCategory10(category10);
        radarDiseasetypePictures.setCategory11(category11);
        radarDiseasetypePictures.setCategory12(category12);
        radarDiseasetypePictures.setCategory12(category13);
        radarDiseasetypePictures.setCategory14(category14);
        radarDiseasetypePictures.setCategory15(category15);
        RadarPicture radarPicture = radarPictureService.uploadPicture(radarDiseasetypePictures, file);
        return new ResponseEntity<>(radarPicture,HttpStatus.OK);
    }

}