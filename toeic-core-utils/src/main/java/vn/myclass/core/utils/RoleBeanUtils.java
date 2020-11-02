package vn.myclass.core.utils;

import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.presistence.entity.RoleEntity;



public class RoleBeanUtils {
    public  static RoleDTO entity2Dto(RoleEntity entity){
        RoleDTO dto= new RoleDTO();
        dto.setRoleId(entity.getRoleId());
        dto.setName(entity.getName());
        return  dto;
    }

    public  static  RoleEntity dto2Entity(RoleDTO dto){
        RoleEntity entity= new RoleEntity();
        entity.setRoleId(dto.getRoleId());
        entity.setName(dto.getName());

        return  entity;
    }


}
