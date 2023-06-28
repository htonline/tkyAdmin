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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.RadarPicture;
import me.zhengjie.modules.system.service.dto.RadarPictureDto;
import me.zhengjie.modules.system.service.dto.RadarPictureQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author Zuo Haitao
* @date 2023-06-28
**/
public interface RadarPictureService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RadarPictureQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RadarPictureDto>
    */
    List<RadarPictureDto> queryAll(RadarPictureQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return RadarPictureDto
     */
    RadarPictureDto findById(Integer id);

    /**
    * 创建
    * @param resources /
    * @return RadarPictureDto
    */
    RadarPictureDto create(RadarPicture resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(RadarPicture resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<RadarPictureDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据id, 去radar_picture表中, 找出所有 radar_id == data.getId()的;
     * @param id
     * @return  返回所有 radar_id == data.getId()的行
     */
    List<RadarPictureDto> findByRadarId(Integer id);

    /**
     * 上传图片
     * @param name 文件名undefined(不重要，仿写)
     * @param file  文件
     * @return 插入的那个文件
     */
    RadarPicture uploadPicture(String id, MultipartFile file);
}