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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Zuo Haitao
* @date 2023-05-16
**/
@Entity
@Data
@Table(name="radar")
public class Radar implements Serializable {

    @Id
    @Column(name = "Radar_id")
    @ApiModelProperty(value = "雷达id")
    private Long radarId;

    @Column(name = "Radar_name")
    @ApiModelProperty(value = "雷达文件名称")
    private String radarName;

    @Column(name = "Company_id")
    @ApiModelProperty(value = "来源公司id")
    private Long companyId;

    @Column(name = "Long_lat")
    @ApiModelProperty(value = "经纬度坐标")
    private String longLat;

    @Column(name = "City_name")
    @ApiModelProperty(value = "来源城市")
    private String cityName;

    @Column(name = "Road_name")
    @ApiModelProperty(value = "道路名称")
    private String roadName;

    @Column(name = "Radar_type")
    @ApiModelProperty(value = "雷达型号")
    private String radarType;

    @Column(name = "Basic_frequency")
    @ApiModelProperty(value = "天线主频")
    private String basicFrequency;

    @Column(name = "Time_window")
    @ApiModelProperty(value = "时间窗")
    private String timeWindow;

    @Column(name = "Sampling_interval")
    @ApiModelProperty(value = "采样间隔")
    private String samplingInterval;

    @Column(name = "Measuring_width")
    @ApiModelProperty(value = "测量宽度")
    private String measuringWidth;

    @Column(name = "Data_size")
    @ApiModelProperty(value = "数据大小")
    private String dataSize;

    @Column(name = "Trace_Num")
    @ApiModelProperty(value = "总道数")
    private String traceNum;

    @Column(name = "Channel")
    @ApiModelProperty(value = "通道数")
    private String channel;

    @Column(name = "Start_mileage")
    @ApiModelProperty(value = "起始里程")
    private String startMileage;

    @Column(name = "End_mileage")
    @ApiModelProperty(value = "终止里程")
    private String endMileage;

    @Column(name = "Del_flag")
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    @Column(name = "Rad_add")
    @ApiModelProperty(value = "原始雷达数据存放地址")
    private String radAdd;

    @Column(name = "Gps_add")
    @ApiModelProperty(value = "Gps文件存放地址")
    private String gpsAdd;

    @Column(name = "Ana_add")
    @ApiModelProperty(value = "解析后雷达数据存放地址")
    private String anaAdd;

    @Column(name = "Pic_add")
    @ApiModelProperty(value = "雷达图片存放地址")
    private String picAdd;

    @Column(name = "Create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "Create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "Update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "Update_time")
    @CreationTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "Remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "Remark1")
    @ApiModelProperty(value = "备注")
    private String remark1;

    @Column(name = "Remark2")
    @ApiModelProperty(value = "备注")
    private String remark2;

    @Column(name = "Remark3")
    @ApiModelProperty(value = "备注")
    private String remark3;

    @Column(name = "Remark4")
    @ApiModelProperty(value = "备注")
    private String remark4;

    @Column(name = "Remark5")
    @ApiModelProperty(value = "备注")
    private String remark5;

    @Column(name = "Remark6")
    @ApiModelProperty(value = "备注")
    private String remark6;

    public void copy(Radar source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}