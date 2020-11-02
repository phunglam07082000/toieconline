package vn.myclass.core.service.impl;



import vn.myclass.core.dto.TypeDTO;
import vn.myclass.core.presistence.entity.TypeEntity;
import vn.myclass.core.service.TypeService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.TypeBeanUtil;
import java.util.ArrayList;
import java.util.List;

public class TypeServiceImpl implements TypeService {
    public List<TypeDTO> findAll() {


        List<TypeEntity> entities = SingletonDaoUtil.getTypeDaoInstance().findAll();
        List<TypeDTO> dtos = new ArrayList<TypeDTO>();
        for (TypeEntity item : entities) {
            TypeDTO dto = TypeBeanUtil.entity2Dto(item);
            dtos.add(dto);

        }
        return dtos;

    }
}
