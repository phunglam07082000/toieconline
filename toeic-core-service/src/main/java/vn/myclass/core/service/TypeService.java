package vn.myclass.core.service;

import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.dto.TypeDTO;

import java.util.List;

public interface TypeService {
    List<TypeDTO> findAll();
}
