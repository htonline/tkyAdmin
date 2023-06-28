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
@Table(name="disease")
public class Disease implements Serializable {

    @Id
    @Column(name = "Disease_id")
    @ApiModelProperty(value = "病害id")
    private Long diseaseId;

    @Column(name = "Radar__name")
    @ApiModelProperty(value = "雷达文件名称")
    private String radarName;

    @Column(name = "Soil_property")
    @ApiModelProperty(value = "岩土性质")
    private String soilProperty;

    @Column(name = "Disease_type")
    @ApiModelProperty(value = "病害种类")
    private String diseaseType;

    @Column(name = "Start_depth")
    @ApiModelProperty(value = "起始深度（采样点数）")
    private String startDepth;

    @Column(name = "End_depth")
    @ApiModelProperty(value = "终止深度（采样点数）")
    private String endDepth;

    @Column(name = "Free_height")
    @ApiModelProperty(value = "净空高")
    private String freeHeight;

    @Column(name = "Start_width")
    @ApiModelProperty(value = "宽度开始通道数")
    private String startWidth;

    @Column(name = "End_width")
    @ApiModelProperty(value = "宽度结束通道数")
    private String endWidth;

    @Column(name = "Disease_size")
    @ApiModelProperty(value = "尺寸")
    private String diseaseSize;

    @Column(name = "Long_lat")
    @ApiModelProperty(value = "中心点经纬度坐标")
    private String longLat;

    @Column(name = "Del_flag")
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

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

    @Column(name = "Update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    public void copy(Disease source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}