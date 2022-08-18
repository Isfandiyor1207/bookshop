//package epam.project.bookshop.controller.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebFilter(filterName = "PageSecurityFilter",urlPatterns = "/pages/*",
//        initParams = {@WebInitParam(name = "INDEX",value = "/index.jsp")})
//public class PageSecurityFilter implements Filter {
//
//    private String indexPath;
//
//    public void init(FilterConfig config) throws ServletException {
//        indexPath = config.getInitParameter("INDEX");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
//        chain.doFilter(request, response);
//    }
//
//    public void destroy() {
//    }
//
//}