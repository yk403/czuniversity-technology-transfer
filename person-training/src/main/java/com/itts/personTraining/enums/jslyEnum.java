package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 技术领域枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum jslyEnum {

    BIOLOGY_AND_MEDICAL_TECHNOLOGY("technical_field_02","生物与新医药技术"),

    ELECTRONIC_TECHNOLOGY("technical_field_03","电子信息技术"),

    AEROSPACE_TECHNOLOGY("technical_field_04","航空航天技术"),

    NEW_MATERIAL_TECHNOLOGY("technical_field_05","新材料技术"),

    HIGH_TECH_SERVICE_INDUSTRY("technical_field_06","高新技术服务业"),

    EQUIPMENT_AND_MANUFACTURING("technical_field_07","高端装备与先进制造"),

    RESOURCE_AND_ENVIRONMENT_TECHNOLOGY("technical_field_08","资源与环境工程技术"),

    CHEMICAL_ENGINEERING("technical_field_09","化学与化学工程"),

    MODERN_AGRICULTURE_TECHNOLOGY("technical_field_10","技术现代农业");

    private String key;

    private String msg;
}
