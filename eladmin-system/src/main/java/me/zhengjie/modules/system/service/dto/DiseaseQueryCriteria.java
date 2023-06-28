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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author Zuo Haitao
* @date 2023-05-16
**/
@Data
public class DiseaseQueryCriteria{

    /** 精确 */
    @Query
    private Long diseaseId;

    /** 精确 */
    @Query
    private String radarName;

    /** 精确 */
    @Query
    private String soilProperty;

    /** 精确 */
    @Query
    private String diseaseType;

    /** 精确 */
    @Query
    private String startDepth;

    /** 精确 */
    @Query
    private String endDepth;

    /** 精确 */
    @Query
    private String freeHeight;

    /** 精确 */
    @Query
    private String startWidth;

    /** 精确 */
    @Query
    private String endWidth;

    /** 精确 */
    @Query
    private String diseaseSize;

    /** 精确 */
    @Query
    private String longLat;

    /** 精确 */
    @Query
    private String delFlag;

    /** 精确 */
    @Query
    private String createBy;

    /** 精确 */
    @Query
    private String updateBy;

    /** 精确 */
    @Query
    private String remark;

    /** 精确 */
    @Query
    private String remark1;

    /** 精确 */
    @Query
    private String remark2;

    /** 精确 */
    @Query
    private String remark3;

    /** 精确 */
    @Query
    private String remark4;

    /** 精确 */
    @Query
    private String remark5;

    /** 精确 */
    @Query
    private String remark6;
}