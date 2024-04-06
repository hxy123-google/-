package com.example.common.enums;

/**
 * ClassName: CategoryEnum
 * Package: com.example.common.enums
 * Description:
 *
 * @Author Hxy
 * @Create 2024/4/7 0:01
 * @Version 1.0
 */
public enum CategoryEnum {
    CompositeMaterials("复合材料"),
    Graphene(" 石墨烯"),
    Metallurgy("冶金学"),
    Polyurethane("碳纳米管"),
    TiO2(" TiO_2"),
    BuildingMaterials("建筑材料"),
    ;
    public String info;
    CategoryEnum(String info){
        this.info=info;
    }
}
