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
import me.zhengjie.modules.system.domain.RadarDiseasetypePictures;
import me.zhengjie.modules.system.service.RadarDiseasetypePicturesService;
import me.zhengjie.modules.system.service.RadarPictureService;
import me.zhengjie.modules.system.service.dto.RadarDiseasetypePicturesQueryCriteria;
import me.zhengjie.modules.system.service.dto.RadarPictureDto;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author zuohaitao
* @date 2023-06-30
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "radarDiseasetypePictures管理")
@RequestMapping("/api/radarDiseasetypePictures")
public class RadarDiseasetypePicturesController {

    private final RadarDiseasetypePicturesService radarDiseasetypePicturesService;
    private final RadarPictureService radarPictureService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('radarDiseasetypePictures:list')")
    public void exportRadarDiseasetypePictures(HttpServletResponse response, RadarDiseasetypePicturesQueryCriteria criteria) throws IOException {
        radarDiseasetypePicturesService.download(radarDiseasetypePicturesService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询radarDiseasetypePictures")
    @ApiOperation("查询radarDiseasetypePictures")
    @PreAuthorize("@el.check('radarDiseasetypePictures:list')")
    public ResponseEntity<Object> queryRadarDiseasetypePictures(RadarDiseasetypePicturesQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(radarDiseasetypePicturesService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增radarDiseasetypePictures")
    @ApiOperation("新增radarDiseasetypePictures")
    @PreAuthorize("@el.check('radarDiseasetypePictures:add')")
    public ResponseEntity<Object> createRadarDiseasetypePictures(@Validated @RequestBody RadarDiseasetypePictures resources){
        return new ResponseEntity<>(radarDiseasetypePicturesService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改radarDiseasetypePictures")
    @ApiOperation("修改radarDiseasetypePictures")
    @PreAuthorize("@el.check('radarDiseasetypePictures:edit')")
    public ResponseEntity<Object> updateRadarDiseasetypePictures(@Validated @RequestBody RadarDiseasetypePictures resources){
        radarDiseasetypePicturesService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除radarDiseasetypePictures")
    @ApiOperation("删除radarDiseasetypePictures")
    @PreAuthorize("@el.check('radarDiseasetypePictures:del')")
    public ResponseEntity<Object> deleteRadarDiseasetypePictures(@RequestBody Integer[] ids) {
        radarDiseasetypePicturesService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/selectPhotos")
    @Log("查询图片api/selectPhotos")
    @ApiOperation("查询图片")
    public ResponseEntity<Object> selectPhotos(@Validated @RequestBody RadarDiseasetypePictures data){
        if (data.getId() == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
//            RadarPictureDto one = radarPictureService.findById(data.getId());
//            根据id, 去radar_picture表中, 找出所有 radar_id == data.getId()的;
            List<RadarPictureDto> radarPictureDtoList = radarPictureService.findByRadarId(data.getId());
            return new ResponseEntity<>(radarPictureDtoList, HttpStatus.OK);
        }
    }
}