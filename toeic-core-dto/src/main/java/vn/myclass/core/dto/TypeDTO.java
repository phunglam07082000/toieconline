package vn.myclass.core.dto;

import java.io.Serializable;

public class TypeDTO implements Serializable {
    private Integer typeId;
    private String name;

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
}
