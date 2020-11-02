package vn.myclass.core;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import vn.myclass.core.dao.ListenGuidelineDao;
import vnmy.classs.core.daoimpl.ListenGuidelineDaoImpl;

import java.util.HashMap;
import java.util.Map;


public class ListenGuidelineTest {

    ListenGuidelineDao listenGuidelineDao;
    @BeforeTest
    public void initData() {
        listenGuidelineDao= new ListenGuidelineDaoImpl();
    }

    @Test

    public void findByProperties() {
        //ListenGuidelineDao listenGuidelineDao = new ListenGuidelineDaoImpl();
        // Object[] result=listenGuidelineDao.findByProperty(null, null, null,null,2,2);
    }
    @Test
    public  void checkFindByProperty(){
        Map<String , Object> property= new HashMap<String, Object>();
        property.put("title","Bai hd 8");
        property.put("content","Noi dung bai hd 8");
        Object[] objects=listenGuidelineDao.findByProperty(property, null, null, null, null);
    }
}
