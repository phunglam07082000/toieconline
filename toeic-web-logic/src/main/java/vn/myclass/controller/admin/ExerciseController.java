package vn.myclass.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import vn.myclass.command.ExerciseCommand;
import vn.myclass.core.dto.ExerciseDTO;
import vn.myclass.core.dto.TypeDTO;
import vn.myclass.core.service.utils.SingletonDaoUtil;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;
import vn.myclass.core.web.utils.RequestUtil;
import vn.myclass.core.web.utils.SingletonServiceUtil;
import vn.myclass.core.web.utils.WebCommonUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/admin-exercise-list.html", "/ajax-admin-exercise-edit.html"})
public class ExerciseController extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());

    ResourceBundle bundle = ResourceBundle.getBundle("ResourcesBundle");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExerciseCommand command = FormUtil.populate(ExerciseCommand.class, request);
        ExerciseDTO pojo = command.getPojo();
        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)) {
                List<Integer> ids = new ArrayList<Integer>();
                for (String item : command.getCheckList()) {
                    ids.add(Integer.parseInt(item));

                }
                Integer result = SingletonServiceUtil.getExerciseServiceInstance().delete(ids);
                if (result != ids.size()) {
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);

                }


            }

            executeSearchExercise(request, command);
            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buidMapRedirectMessage(bundle);
                WebCommonUtil.addRedirectMessage(request, command.getCrudaction(), mapMessage);
            }

            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("views/admin/exercise/list.jsp");
            rd.forward(request, response);

        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (pojo != null && pojo.getExerciseId() != null) {

                command.setPojo(SingletonServiceUtil.getExerciseServiceInstance().findById(pojo.getExerciseId()));
            }
            command.setTypes(SingletonServiceUtil.getTypeServiceInstance().findAll());

            request.setAttribute(WebConstant.FORM_ITEM, command);
            RequestDispatcher rd = request.getRequestDispatcher("views/admin/exercise/edit.jsp");
            rd.forward(request, response);

        }
    }

    private Map<String, String> buidMapRedirectMessage(ResourceBundle bundle) {

        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, bundle.getString("label.exercise.message.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, bundle.getString("label.exercise.message.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, bundle.getString("label.exercise.message.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, bundle.getString("label.message.error"));
        return mapMessage;
    }

    private void executeSearchExercise(HttpServletRequest request, ExerciseCommand command) {
        Map<String, Object> properties = buildMapProperties(command);
        RequestUtil.initSearchBean(request, command);
        Object[] objects = SingletonServiceUtil.getExerciseServiceInstance().findExerciseByProperties(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<ExerciseDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));


    }

    private Map<String, Object> buildMapProperties(ExerciseCommand command) {
        Map<String, Object> properties = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(command.getPojo().getName())) {

            properties.put("name", command.getPojo().getName());

        }

        return properties;


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            ExerciseCommand command = FormUtil.populate(ExerciseCommand.class, request);
            ExerciseDTO pojo = command.getPojo();


            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {

                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {

                    TypeDTO typeDTO = new TypeDTO();

                    typeDTO.setTypeId(command.getTypeId());

                    if (typeDTO.getTypeId() != -1) {
                        pojo.setTypeDTO(typeDTO);
                        if (pojo != null && pojo.getExerciseId() != null) {
                            SingletonServiceUtil.getExerciseServiceInstance().updateExercise(pojo);
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);

                        } else {
                            SingletonServiceUtil.getExerciseServiceInstance().saveExercise(pojo);
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);

                        }

                    } else {

                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
                        RequestDispatcher rd = request.getRequestDispatcher("views/admin/exercise/edit.jsp");
                        rd.forward(request, response);


                    }
                }

                RequestDispatcher rd = request.getRequestDispatcher("views/admin/exercise/edit.jsp");
                rd.forward(request, response);

            } else {

            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);

        }


    }


}
