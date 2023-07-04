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
import java.io.Serializable;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @website https://el-admin.vip
* @description /
* @author zuohaitao
* @date 2023-07-04
**/
@Data
public class CompanyDto implements Serializable {

    /** 公司id */
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long companyId;

    /** 公司名称 */
    private String companyName;

    /** 联系电话 */
    private String companyPhone;

    /** 备注 */
    private String remark;

    /** 备注1 */
    private String remark1;

    /** 备注2 */
    private String remark2;

    /** 备注3 */
    private String remark3;

    /** 备注4 */
    private String remark4;

    /** 备注5 */
    private String remark5;

    /** 备注6 */
    private String remark6;
}