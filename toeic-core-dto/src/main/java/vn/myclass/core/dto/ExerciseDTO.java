package vn.myclass.core.dto;

import java.sql.Timestamp;
import java.util.List;

public class ExerciseDTO {
    private Integer exerciseId;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private TypeDTO typeDTO;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public TypeDTO getTypeDTO() {
        return typeDTO;
    }

    public void setTypeDTO(TypeDTO typeDTO) {
        this.typeDTO = typeDTO;
    }

    private List<ExerciseQuestionDTO> exerciseQuestions;

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<ExerciseQuestionDTO> getExerciseQuestions() {
        return exerciseQuestions;
    }

    public void setExerciseQuestions(List<ExerciseQuestionDTO> exerciseQuestions) {
        this.exerciseQuestions = exerciseQuestions;
    }


}
