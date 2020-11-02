package vn.myclass.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.ExcelPoiUtil;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.common.utils.UploadUtil;
import vn.myclass.core.dto.RoleDTO;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.dto.UserImportDTO;

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

@WebServlet(urlPatterns = {"/admin-user-list.html", "/ajax-admin-user-edit.html", "/admin-user-import.html", "/admin-user-import-validate.html"})
public class UserController extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    private final String SHOW_IMPORT_USER = "show_import_user";
    private final String READ_EXCEL = "read_excel";
    private final String VALIDATE_IMPORT = "validate_import";
    private final String LIST_USER_IMPORT = "list_user_import";
    private final String IMPORT_DATA = "import_data";
    ResourceBundle bundle = ResourceBundle.getBundle("ResourcesBundle");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserCommand command = FormUtil.populate(UserCommand.class, request);
        UserDTO pojo = command.getPojo();


        if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_LIST)) {
            if(command.getCrudaction()!=null&& command.getCrudaction().equals(WebConstant.REDIRECT_DELETE)){
                List<Integer> ids=new ArrayList<Integer>();
                for (String item: command.getCheckList()){

                    ids.add(Integer.parseInt(item));
                }
                Integer result =SingletonServiceUtil.getUserServiceInstance().delete(ids);
                if(result!=ids.size()){
                    command.setCrudaction(WebConstant.REDIRECT_ERROR);
                }

            }
              executeSearchUser(request,command);




            if (command.getCrudaction() != null) {
                Map<String, String> mapMessage = buidMapRedirectMessage(bundle);
                WebCommonUtil.addRedirectMessage(request, command.getCrudaction(), mapMessage);
            }
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/list.jsp");
            rd.forward(request, response);
        } else if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
            if (pojo != null && pojo.getUserId() != null) {
                command.setPojo(SingletonServiceUtil.getUserServiceInstance().findById(pojo.getUserId()));

            }
            command.setRoles(SingletonServiceUtil.getRoleServiceInstance().findAll());

            request.setAttribute(WebConstant.FORM_ITEM, command);
            RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/edit.jsp");
            rd.forward(request, response);

        } else if (command.getUrlType() != null && command.getUrlType().equals(SHOW_IMPORT_USER)) {

            RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/importuser.jsp");
            rd.forward(request, response);


        } else if (command.getUrlType() != null && command.getUrlType().equals(VALIDATE_IMPORT)) {
            List<UserImportDTO> userImportDTOS = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request, LIST_USER_IMPORT);
            command.setUserImportDTOS(userImportDTOS);


            command.setUserImportDTOS(returnListUserImport(command, userImportDTOS, request));
            request.setAttribute(WebConstant.LIST_ITEMS, command);
            RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/importuser.jsp");
            rd.forward(request, response);


        }

    }



    private void executeSearchUser(HttpServletRequest request, UserCommand command) {
        Map<String, Object> properties =buildMapProperties(command);
        RequestUtil.initSearchBean(request, command);
        Object[] objects = SingletonServiceUtil.getUserServiceInstance().findByProperty(properties, command.getSortExpression(), command.getSortDirection(), command.getFirstItem(), command.getMaxPageItems());
        command.setListResult((List<UserDTO>) objects[1]);
        command.setTotalItems(Integer.parseInt(objects[0].toString()));

    }

    private Map<String, Object> buildMapProperties(UserCommand command) {
        Map<String, Object> properties=new HashMap<String, Object>();

        if(StringUtils.isNotBlank(command.getPojo().getFullName())){

            properties.put("fullName",command.getPojo().getFullName());

        }

        return  properties;

    }

    private List<UserImportDTO> returnListUserImport(UserCommand command, List<UserImportDTO> userImportDTOS, HttpServletRequest request) {
        command.setMaxPageItems(3);
        RequestUtil.initSearchBean(request, command);
        command.setTotalItems(userImportDTOS.size());
        int fromIndex = command.getFirstItem();
        if (fromIndex > command.getTotalItems()) {
            fromIndex = 0;
            command.setFirstItem(0);

        }
        int toIndex = command.getFirstItem() + command.getMaxPageItems();
        if (userImportDTOS.size() > 0) {
            if (toIndex > userImportDTOS.size()) {
                toIndex = userImportDTOS.size();
            }

        }
        return userImportDTOS.subList(fromIndex, toIndex);

    }

    private Map<String, String> buidMapRedirectMessage(ResourceBundle bundle) {
        Map<String, String> mapMessage = new HashMap<String, String>();
        mapMessage.put(WebConstant.REDIRECT_INSERT, bundle.getString("label.user.message.add.success"));
        mapMessage.put(WebConstant.REDIRECT_UPDATE, bundle.getString("label.user.message.update.success"));
        mapMessage.put(WebConstant.REDIRECT_DELETE, bundle.getString("label.user.message.delete.success"));
        mapMessage.put(WebConstant.REDIRECT_ERROR, bundle.getString("label.message.error"));
        return mapMessage;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UploadUtil uploadUtil = new UploadUtil();
        Set<String> value = new HashSet<String>();
        value.add("urlType");
        Object[] objects = uploadUtil.writeOrUpdateFile(request, value, "excel");

        try {
            UserCommand command = FormUtil.populate(UserCommand.class, request);
            UserDTO pojo = command.getPojo();
            if (command.getUrlType() != null && command.getUrlType().equals(WebConstant.URL_EDIT)) {
                if (command.getCrudaction() != null && command.getCrudaction().equals(WebConstant.INSERT_UPDATE)) {
                    /*request.setAttribute(WebConstant.MESSAGE_RESPONSE,"insert_success");*/
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setRoleId(command.getRoleId());

                    if(roleDTO.getRoleId()!=-1){

                        pojo.setRoleDTO(roleDTO);
                        if (pojo != null && pojo.getUserId() != null) {
                            //update user
                            SingletonServiceUtil.getUserServiceInstance().updateUser(pojo);
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_UPDATE);

                        } else {
                            //save user
                            SingletonServiceUtil.getUserServiceInstance().saveUser(pojo);
                            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_INSERT);
                        }

                    }else {
                        request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
                        RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/edit.jsp");
                        rd.forward(request, response);

                    }

                }
                RequestDispatcher rd = request.getRequestDispatcher("views/admin/user/edit.jsp");
                rd.forward(request, response);
            }
            if (objects != null) {
                Map<String, String> mapValue = (Map<String, String>) objects[3];
                String urlType = null;
                for (Map.Entry<String, String> item : mapValue.entrySet()) {
                    if (item.getKey().equals("urlType")) {
                        urlType = item.getValue();
                    }
                }
                if (urlType != null && urlType.equals(READ_EXCEL)) {

                    String fileLocation = objects[1].toString();
                    //FileInputStream excelFile = new FileInputStream(new File(fileLocation));
                    String fileName = objects[2].toString();

                    List<UserImportDTO> excelValues = returnValueImportExcel(fileName, fileLocation);
                    validateData(excelValues);
                    SessionUtil.getInstance().putValue(request, LIST_USER_IMPORT, excelValues);
                    response.sendRedirect("admin-user-import-validate.html?urlType=validate_import");

                }
            }

            if (command.getUrlType() != null && command.getUrlType().equals(IMPORT_DATA)) {

                List<UserImportDTO> userImportDTOS = (List<UserImportDTO>) SessionUtil.getInstance().getValue(request, LIST_USER_IMPORT);
                SingletonServiceUtil.getUserServiceInstance().saveUserImport(userImportDTOS);
                SessionUtil.getInstance().remove(request, LIST_USER_IMPORT);
                response.sendRedirect("admin-user-list.html?urlType=url_list");

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE, WebConstant.REDIRECT_ERROR);
        }


    }

    private void validateData(List<UserImportDTO> excelValues) {
        Set<String> stringSet = new HashSet<String>();
        for (UserImportDTO item : excelValues) {
            validateRequireField(item);
            validateDuplicate(item, stringSet);

        }
        SingletonServiceUtil.getUserServiceInstance().validateImportUser(excelValues);
    }

    private void validateDuplicate(UserImportDTO item, Set<String> stringSet) {
        String message = item.getError();
        if (!stringSet.contains(item.getUserName())) {
            stringSet.add(item.getUserName());
        } else {
            if (item.isValid()) {
                message += "<br/>";
                message += bundle.getString("label.username.duplicate");
            }
        }
        if (StringUtils.isNotBlank(message)) {
            item.setValid(false);
            item.setError(message);
        }
    }

    private void validateRequireField(UserImportDTO item) {
        String message = "";
        if (StringUtils.isBlank(item.getUserName())) {
            message += "<br/>";
            message += bundle.getString("label.username.notempty");
        }
        if (StringUtils.isBlank(item.getPassword())) {
            message += "<br/>";
            message += bundle.getString("label.password.notempty");
        }
        if (StringUtils.isBlank(item.getRoleName())) {
            message += "<br/>";
            message += bundle.getString("label.rolename.notempty");
        }
        if (StringUtils.isNotBlank(message)) {
            item.setValid(false);
        }
        item.setError(message);


    }


    private List<UserImportDTO> returnValueImportExcel(String fileName, String fileLocation) throws IOException {
        Workbook workbook = ExcelPoiUtil.getWorkBook(fileName, fileLocation);
        Sheet sheet = workbook.getSheetAt(0);
        List<UserImportDTO> excelValues = new ArrayList<UserImportDTO>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            UserImportDTO userImportDTO = readDataFromExcel(row);
            excelValues.add(userImportDTO);
        }
        return excelValues;

    }

    private UserImportDTO readDataFromExcel(Row row) {
        UserImportDTO userImportDTO = new UserImportDTO();
        userImportDTO.setUserName(ExcelPoiUtil.getCellValue(row.getCell(0)));
        userImportDTO.setPassword(ExcelPoiUtil.getCellValue(row.getCell(1)));
        userImportDTO.setFullName(ExcelPoiUtil.getCellValue(row.getCell(2)));
        userImportDTO.setRoleName(ExcelPoiUtil.getCellValue(row.getCell(3)));
        return userImportDTO;
    }

}
