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
import java.sql.Timestamp;
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @website https://el-admin.vip
* @description /
* @author Zuo Haitao
* @date 2023-05-16
**/
@Data
public class DiseaseDto implements Serializable {

    /** 病害id */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long diseaseId;

    /** 雷达文件名称 */
    private String radarName;

    /** 岩土性质 */
    private String soilProperty;

    /** 病害种类 */
    private String diseaseType;

    /** 起始深度（采样点数） */
    private String startDepth;

    /** 终止深度（采样点数） */
    private String endDepth;

    /** 净空高 */
    private String freeHeight;

    /** 宽度开始通道数 */
    private String startWidth;

    /** 宽度结束通道数 */
    private String endWidth;

    /** 尺寸 */
    private String diseaseSize;

    /** 中心点经纬度坐标 */
    private String longLat;

    /** 删除标志 */
    private String delFlag;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新者 */
    private String updateBy;

    /** 备注 */
    private String remark;

    /** 备注 */
    private String remark1;

    /** 备注 */
    private String remark2;

    /** 备注 */
    private String remark3;

    /** 备注 */
    private String remark4;

    /** 备注 */
    private String remark5;

    /** 备注 */
    private String remark6;

    /** 更新时间 */
    private Timestamp updateTime;
}