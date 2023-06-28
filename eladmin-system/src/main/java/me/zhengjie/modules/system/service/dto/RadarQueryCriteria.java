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
public class RadarQueryCriteria{

    /** 精确 */
    @Query
    private Long radarId;

    /** 精确 */
    @Query
    private String radarName;

    /** 精确 */
    @Query
    private Long companyId;

    /** 精确 */
    @Query
    private String longLat;

    /** 精确 */
    @Query
    private String roadName;

    /** 精确 */
    @Query
    private String radarType;

    /** 精确 */
    @Query
    private String basicFrequency;

    /** 精确 */
    @Query
    private String timeWindow;

    /** 精确 */
    @Query
    private String samplingInterval;

    /** 精确 */
    @Query
    private String measuringWidth;

    /** 精确 */
    @Query
    private String dataSize;

    /** 精确 */
    @Query
    private String traceNum;

    /** 精确 */
    @Query
    private String channel;

    /** 精确 */
    @Query
    private String startMileage;

    /** 精确 */
    @Query
    private String endMileage;

    /** 精确 */
    @Query
    private String delFlag;

    /** 精确 */
    @Query
    private String radAdd;

    /** 精确 */
    @Query
    private String gpsAdd;

    /** 精确 */
    @Query
    private String anaAdd;

    /** 精确 */
    @Query
    private String picAdd;

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