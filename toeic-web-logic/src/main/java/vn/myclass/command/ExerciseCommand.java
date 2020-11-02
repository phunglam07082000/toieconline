package vn.myclass.command;

import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.dto.TypeDTO;
import vn.myclass.core.web.command.AbstractCommand;

import java.util.List;

public class ExerciseCommand extends AbstractCommand<ExerciseDTO> {

    private List<TypeDTO> types;
    private Integer typeId;

    public List<TypeDTO> getTypes() {
        return types;
    }

    public void setTypes(List<TypeDTO> types) {
        this.types = types;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public ExerciseCommand() {

        this.pojo = new ExerciseDTO();
    }
}
