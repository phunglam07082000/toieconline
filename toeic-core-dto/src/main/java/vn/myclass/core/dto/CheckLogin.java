package vn.myclass.core.dto;

import java.io.Serializable;

public class CheckLogin implements Serializable {
    private boolean isUserExit;
    private String roleName;

    public boolean isUserExit() {
        return isUserExit;
    }

    public void setUserExit(boolean userExit) {
        isUserExit = userExit;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
