package vn.myclass.core.service.impl;

import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.presistence.entity.RoleEntity;
import vn.myclass.core.service.RoleService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.RoleBeanUtils;
import vnmy.classs.core.daoimpl.RoleDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {


    public List<RoleDTO> findAll() {


        List<RoleEntity> entities = SingletonDaoUtil.getRoleDaoInstance().findAll();
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (RoleEntity item : entities) {
            RoleDTO dto = RoleBeanUtils.entity2Dto(item);
            dtos.add(dto);


        }
        return dtos;
    }
}
