package vn.myclass.core.presistence.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type")
public class TypeEntity {
    @Id
    @Column(name = "typeid")
    private Integer typeId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "typeEntity" , fetch = FetchType.LAZY)
    private List<ExerciseEntity> exerciseEntityList;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExerciseEntity> getExerciseEntityList() {
        return exerciseEntityList;
    }

    public void setExerciseEntityList(List<ExerciseEntity> exerciseEntityList) {
        this.exerciseEntityList = exerciseEntityList;
    }
}
