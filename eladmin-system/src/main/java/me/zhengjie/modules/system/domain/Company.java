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
* @date 2023-07-04
**/
@Entity
@Data
@Table(name="company")
public class Company implements Serializable {

    @Id
    @Column(name = "Company_id")
    @ApiModelProperty(value = "公司id")
    private Long companyId;

    @Column(name = "Company_name")
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @Column(name = "Company_phone")
    @ApiModelProperty(value = "联系电话")
    private String companyPhone;

    @Column(name = "Remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "Remark1")
    @ApiModelProperty(value = "备注1")
    private String remark1;

    @Column(name = "Remark2")
    @ApiModelProperty(value = "备注2")
    private String remark2;

    @Column(name = "Remark3")
    @ApiModelProperty(value = "备注3")
    private String remark3;

    @Column(name = "Remark4")
    @ApiModelProperty(value = "备注4")
    private String remark4;

    @Column(name = "Remark5")
    @ApiModelProperty(value = "备注5")
    private String remark5;

    @Column(name = "Remark6")
    @ApiModelProperty(value = "备注6")
    private String remark6;

    public void copy(Company source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}