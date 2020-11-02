package vn.myclass.core.utils;

import vn.myclass.core.dto.TypeDTO;
import vn.myclass.core.presistence.entity.TypeEntity;

public class TypeBeanUtil {
    public  static TypeDTO entity2Dto(TypeEntity entity){
        TypeDTO dto= new TypeDTO();
        dto.setTypeId(entity.getTypeId());
        dto.setName(entity.getName());
        return dto;
    }

    public  static TypeEntity dto2Entity(TypeDTO dto){
        TypeEntity entity= new TypeEntity();
        entity.setTypeId(dto.getTypeId());
        entity.setName(dto.getName());

        return  entity;
    }

}
