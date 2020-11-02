package vn.myclass.core.utils;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.presistence.entity.ExerciseEntity;

public class ExerciseBeanUtil {

    public static ExerciseDTO entity2Dto(ExerciseEntity entity) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setExerciseId(entity.getExerciseId());
        dto.setName(entity.getName());

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setTypeDTO(TypeBeanUtil.entity2Dto(entity.getTypeEntity()));
        return dto;
    }
    public static ExerciseEntity dto2Entity(ExerciseDTO dto) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setExerciseId(dto.getExerciseId());
        entity.setName(dto.getName());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        entity.setTypeEntity(TypeBeanUtil.dto2Entity(dto.getTypeDTO()));
        return entity;
    }


}
