package vn.myclass.core;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import vn.myclass.core.dao.UserDao;
import vn.myclass.core.presistence.entity.UserEntity;
import vnmy.classs.core.daoimpl.UserDaoImpl;

public class LoginTest {
    private final Logger log = Logger.getLogger(this.getClass());

   /* @Test
    public void checkIsUserExist() {

        UserDao userDao = new UserDaoImpl();
        String name = "truongtunglam";
        String password = "123456";
        UserEntity entity = userDao.isUserExist(name, password);
        if (entity != null) {
            log.error("thanh công");

        } else {
            log.error("không thành công");

        }
    }
*/
  /*  @Test
    public void checkFindRoleByUser() {

        UserDao userDao = new UserDaoImpl();
        String name = "truongtunglam";
        String password = "123456";
        UserEntity entity = userDao.findRoleByUser(name, password);
        log.error(entity.getRoleEntity().getRoleId() + "-" + entity.getRoleEntity().getName());
    }*/


}
