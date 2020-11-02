package vn.myclass.core.service.impl;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.presistence.entity.ExerciseEntity;
import vn.myclass.core.service.ExerciseService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ExerciseBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseServiceImpl implements ExerciseService {
    public Object[] findExerciseByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExerciseDTO> result = new ArrayList<ExerciseDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit,null);
        for (ExerciseEntity item: (List<ExerciseEntity>)objects[1]) {
            ExerciseDTO dto = ExerciseBeanUtil.entity2Dto(item);
            result.add(dto);
        }
        objects[1] = result;
        return objects;
    }

    public Integer delete(List<Integer> ids) {
       Integer result=SingletonDaoUtil.getExerciseDaoInstance().delete(ids);
       return  result;
    }

    public ExerciseDTO findById(Integer exerciseId) {
        ExerciseEntity entity=SingletonDaoUtil.getExerciseDaoInstance().findById(exerciseId);
        ExerciseDTO dto=ExerciseBeanUtil.entity2Dto(entity);
        return  dto;



    }

    public void saveExercise(ExerciseDTO exerciseDTO) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        exerciseDTO.setCreatedDate(createdDate);
        ExerciseEntity entity= ExerciseBeanUtil.dto2Entity(exerciseDTO);
        SingletonDaoUtil.getExerciseDaoInstance().save(entity);

    }

    public ExerciseDTO updateExercise(ExerciseDTO exerciseDTO) {
        Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
        exerciseDTO.setModifiedDate(modifiedDate);
        ExerciseEntity entity=ExerciseBeanUtil.dto2Entity(exerciseDTO);
        entity=SingletonDaoUtil.getExerciseDaoInstance().update(entity);
        exerciseDTO=ExerciseBeanUtil.entity2Dto(entity);
        return exerciseDTO;




    }


}
