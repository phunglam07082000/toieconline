package vn.myclass.filter;

import vn.myclass.command.UserCommand;
import vn.myclass.core.common.utils.SessionUtil;
import vn.myclass.core.dto.UserDTO;
import vn.myclass.core.web.common.WebConstant;
import vn.myclass.core.web.utils.FormUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI();
        if (url.startsWith("/admin")) {
            UserDTO userDTO= (UserDTO) SessionUtil.getInstance().getValue(request, WebConstant.LOGIN_NAME);
            if (userDTO != null) {

                if (userDTO.getRoleDTO().getName().equals(WebConstant.ADMIN)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (userDTO.getRoleDTO().getName().equals(WebConstant.USER)) {
                    response.sendRedirect(request.getContextPath() + "/login.html");

                }
            }else {

                response.sendRedirect(request.getContextPath()+"");
            }

        }else {
            filterChain.doFilter(servletRequest, servletResponse);

        }


    }

    public void destroy() {

    }
}
