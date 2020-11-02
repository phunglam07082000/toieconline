package vn.myclass.core.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import vn.myclass.core.dao.ListenGuidelineDao;
import vn.myclass.core.dto.ListenGuidelineDTO;
import vn.myclass.core.presistence.entity.ListenGuidelineEntity;
import vn.myclass.core.service.ListenGuidelineService;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.utils.ListenGuidelineBeanUtils;
import vnmy.classs.core.daoimpl.ListenGuidelineDaoImpl;

import java.sql.Timestamp;
import java.util.*;

public class ListenGuidelineServiceImpl implements ListenGuidelineService {


    public Object[] findListenGuidelineByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {

        List<ListenGuidelineDTO> result = new ArrayList<ListenGuidelineDTO>();
        Object[] objects = SingletonDaoUtil.getListenGuidelineDaoInstance().findByProperty(property, sortExpression, sortExpression, offset, limit,null);
        for (ListenGuidelineEntity item : (List<ListenGuidelineEntity>) objects[1]) {
            ListenGuidelineDTO dto = ListenGuidelineBeanUtils.entity2Dto(item);
            result.add(dto);

        }
        objects[1] = result;
        return objects;

    }

    public ListenGuidelineDTO findByListenGuidelineId(String property, Integer listenGuideLineId) {
        ListenGuidelineEntity entity = SingletonDaoUtil.getListenGuidelineDaoInstance().findEqualUnique(property, listenGuideLineId);
        ListenGuidelineDTO dto = ListenGuidelineBeanUtils.entity2Dto(entity);
        return dto;
    }

    public ListenGuidelineDTO updateListenGuideline(ListenGuidelineDTO dto) {


        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(timestamp);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtils.dto2Entity(dto);
        entity = SingletonDaoUtil.getListenGuidelineDaoInstance().update(entity);
        dto = ListenGuidelineBeanUtils.entity2Dto(entity);
        return dto;



    }

    public void saveListenGuideline(ListenGuidelineDTO dto) throws ConstraintViolationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ListenGuidelineEntity entity = ListenGuidelineBeanUtils.dto2Entity(dto);
        SingletonDaoUtil.getListenGuidelineDaoInstance().save(entity);
    }

    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoUtil.getListenGuidelineDaoInstance().delete(ids);
        return result;
    }
}
