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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zuohaitao
* @date 2023-06-30
**/
@Entity
@Data
@Table(name="radar_diseasetype_pictures")
public class RadarDiseasetypePictures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Integer id;

    @Column(name = "category1")
    @ApiModelProperty(value = "Category1")
    private String category1;

    @Column(name = "category2")
    @ApiModelProperty(value = "Category2")
    private String category2;

    @Column(name = "category3")
    @ApiModelProperty(value = "Category3")
    private String category3;

    @Column(name = "category4")
    @ApiModelProperty(value = "Category4")
    private String category4;

    @Column(name = "category5")
    @ApiModelProperty(value = "Category5")
    private String category5;

    @Column(name = "category6")
    @ApiModelProperty(value = "Category6")
    private String category6;

    @Column(name = "category7")
    @ApiModelProperty(value = "Category7")
    private String category7;

    @Column(name = "category8")
    @ApiModelProperty(value = "Category8")
    private String category8;

    @Column(name = "category9")
    @ApiModelProperty(value = "Category9")
    private String category9;

    @Column(name = "category10")
    @ApiModelProperty(value = "Category10")
    private String category10;

    @Column(name = "category11")
    @ApiModelProperty(value = "Category11")
    private String category11;

    @Column(name = "category12")
    @ApiModelProperty(value = "Category12")
    private String category12;

    @Column(name = "category13")
    @ApiModelProperty(value = "Category13")
    private String category13;

    @Column(name = "category14")
    @ApiModelProperty(value = "Category14")
    private String category14;

    @Column(name = "category15")
    @ApiModelProperty(value = "Category15")
    private String category15;

    public void copy(RadarDiseasetypePictures source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}