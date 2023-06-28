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
public class RadarDto implements Serializable {

    /** 雷达id */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long radarId;

    /** 雷达文件名称 */
    private String radarName;

    /** 来源公司id */
    private Long companyId;

    /** 经纬度坐标 */
    private String longLat;

    /** 来源城市 */
    private String cityName;

    /** 道路名称 */
    private String roadName;

    /** 雷达型号 */
    private String radarType;

    /** 天线主频 */
    private String basicFrequency;

    /** 时间窗 */
    private String timeWindow;

    /** 采样间隔 */
    private String samplingInterval;

    /** 测量宽度 */
    private String measuringWidth;

    /** 数据大小 */
    private String dataSize;

    /** 总道数 */
    private String traceNum;

    /** 通道数 */
    private String channel;

    /** 起始里程 */
    private String startMileage;

    /** 终止里程 */
    private String endMileage;

    /** 删除标志 */
    private String delFlag;

    /** 原始雷达数据存放地址 */
    private String radAdd;

    /** Gps文件存放地址 */
    private String gpsAdd;

    /** 解析后雷达数据存放地址 */
    private String anaAdd;

    /** 雷达图片存放地址 */
    private String picAdd;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Timestamp updateTime;

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
}