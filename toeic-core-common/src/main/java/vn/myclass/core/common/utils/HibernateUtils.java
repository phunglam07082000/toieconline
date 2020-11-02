package vn.myclass.core.common.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Map;


public class HibernateUtils {
    private static  final  SessionFactory sessionFactory=buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable throwable) {
            System.out.println("ko thanh cong");
            throw  new ExceptionInInitializerError(throwable);

        }
    }

    public static  SessionFactory getSessionFactory(){
        return  sessionFactory;


    }
//tìm kiếm gần đúng
    public static Object[] buildNameQuery(Map<String, Object> property){

        StringBuilder nameQuery = new StringBuilder();
        if (property != null && property.size() > 0) {
            String[] params = new String[property.size()];
            Object[] values = new Object[property.size()];
            int i = 0 ;
            for(Map.Entry item: property.entrySet()) {
                params[i] = (String) item.getKey();
                values[i] = item.getValue();
                i++;
            }
            for (int i1 = 0; i1 < params.length ; i1++) {
                nameQuery.append(" and ").append(" LOWER("+params[i1]+") LIKE '%' || :"+params[i1]+" || '%' ");
            }
            return new Object[]{nameQuery, params, values};
        }
        return new Object[]{nameQuery.toString()};

    }
}
