package vn.myclass.controller.admin;

import org.apache.log4j.Logger;
import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.dto.CheckLogin;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.service.UserService;
import vn.myclass.core.service.impl.UserServiceImpl;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;
import vn.myclass.core.web.utils.SingletonServiceUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet( urlPatterns = {"/login.html", "/logout.html"})
public class LoginController extends HttpServlet {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("ResourcesBundle");
    private final Logger log = Logger.getLogger(this.getClass());
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action=request.getParameter("action");
        if( action.equals(WebConstant.LOGIN)){

            RequestDispatcher rd= request.getRequestDispatcher("/views/web/login.jsp");
            rd.forward(request, response);
        }else  if(action.equals(WebConstant.LOGOUT)){
            SessionUtil.getInstance().remove(request, WebConstant.LOGIN_NAME);
            response.sendRedirect("home.html");


        }


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session= request.getSession();
        UserCommand command = FormUtil.populate(UserCommand.class, request);
        UserDTO pojo= command.getPojo();
        if(pojo!=null){

            CheckLogin login=SingletonServiceUtil.getUserServiceInstance().checkLogin(pojo.getName(), pojo.getPassword());
            if(login.isUserExit()){
                SessionUtil.getInstance().putValue(request,WebConstant.LOGIN_NAME,pojo.getName());
                if(login.getRoleName().equals(WebConstant.ROLE_ADMIN)){
                    response.sendRedirect("admin-home.html");
                }else if(login.getRoleName().equals(WebConstant.ROLE_USER)){
                    response.sendRedirect("home.html");
                }

            }else {

                request.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
                request.setAttribute(WebConstant.MESSAGE_RESPONSE,resourceBundle.getString("label.name.password.wrong"));
                RequestDispatcher rd= request.getRequestDispatcher("/views/web/login.jsp");
                rd.forward(request, response);
            }
        }


    }


}

       /* try {
            if(SingletonServiceUtil.getUserServiceInstance().isUserExist(pojo)!=null){
                if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo)!=null
                        && SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO()!=null){
                    if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_ADMIN)){
                        response.sendRedirect("admin-home.html");
                    }else if(SingletonServiceUtil.getUserServiceInstance().findRoleByUser(pojo).getRoleDTO().getName().equals(WebConstant.ROLE_USER)){
                        response.sendRedirect("home.html");
                    }
                }
            }
        }catch (NullPointerException e){
            log.error(e.getMessage(), e);
            request.setAttribute(WebConstant.ALERT,WebConstant.TYPE_ERROR);
            request.setAttribute(WebConstant.MESSAGE_RESPONSE,"Tên hoặc mật khẩu sai");
            RequestDispatcher rd= request.getRequestDispatcher("/views/web/login.jsp");
            rd.forward(request, response);
        }*/