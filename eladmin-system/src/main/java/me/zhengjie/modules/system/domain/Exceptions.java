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
@Table(name="exceptions")
public class Exceptions implements Serializable {

    @Id
    @Column(name = "Exception_id")
    @ApiModelProperty(value = "异常id")
    private Long exceptionId;

    @Column(name = "Exception_name")
    @ApiModelProperty(value = "异常名称")
    private String exceptionName;

    @Column(name = "Radar_name")
    @ApiModelProperty(value = "雷达文件名称")
    private String radarName;

    @Column(name = "Soil_property")
    @ApiModelProperty(value = "岩土性质")
    private String soilProperty;

    @Column(name = "Disease_size")
    @ApiModelProperty(value = "尺寸")
    private String diseaseSize;

    @Column(name = "Long_lat")
    @ApiModelProperty(value = "经纬度坐标")
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

    @Column(name = "Update_time")
    @UpdateTimestamp
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

    public void copy(Exceptions source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}