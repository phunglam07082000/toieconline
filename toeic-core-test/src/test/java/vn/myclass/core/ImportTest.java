package vn.myclass.core;

import org.testng.annotations.Test;
import vn.myclass.core.dao.RoleDao;
import vn.myclass.core.presistence.entity.RoleEntity;
import vnmy.classs.core.daoimpl.RoleDaoImpl;

public class ImportTest {
    @Test

    public  void TestImport(){
        RoleDao roleDao= new RoleDaoImpl();
        RoleEntity entity=roleDao.findEqualUnique("name", "USER");
        System.out.println(entity.getName());


    }
}
