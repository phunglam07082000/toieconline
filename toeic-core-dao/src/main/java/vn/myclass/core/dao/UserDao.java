package vn.myclass.core.dao;



import vn.myclass.core.data.dao.GenericDao;
import vn.myclass.core.presistence.entity.UserEntity;

import java.util.List;

public interface UserDao extends GenericDao<Integer, UserEntity> {
    Object[] checkLogin(String name, String password);

    List<UserEntity> findByUsers(List<String> names);


}
