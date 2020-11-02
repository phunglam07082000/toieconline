package vn.myclass.core.service;

import java.util.List;
import java.util.Map;

public interface ExaminationService {
    Object[] findExaminationByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);


}
