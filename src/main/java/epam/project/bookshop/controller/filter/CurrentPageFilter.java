package epam.project.bookshop.controller.filter;

import epam.project.bookshop.command.ParameterName;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(urlPatterns = "*.jsp")
public class CurrentPageFilter implements Filter {

    private static final Logger logger= LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(ParameterName.CURRENT_PAGE, httpServletRequest.getServletPath());
        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void destroy() {
    }

}
