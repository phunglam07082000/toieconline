package vn.myclass.core.service;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface ExerciseService {
    Object[] findExerciseByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);

    Integer delete(List<Integer> ids);

    ExerciseDTO findById(Integer exerciseId);

    void saveExercise(ExerciseDTO exerciseDTO);

    ExerciseDTO updateExercise(ExerciseDTO exerciseDTO);
}
