package filter;

import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final String[] PUBLIC = {"/login", "/register", "/index.jsp"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path   = request.getServletPath();
        String method = request.getMethod();

        // Ressources statiques et pages publiques
        if (isPublic(path)) {
            chain.doFilter(req, res);
            return;
        }

        // Consultation des offres accessible sans connexion (GET uniquement)
        if (path.equals("/offer") && "GET".equals(method)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Contrôle d'accès par rôle
        if (path.startsWith("/admin") && user.getIdRole() != 4) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }
        if (path.startsWith("/manager") && user.getIdRole() != 3) {
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isPublic(String path) {
        for (String p : PUBLIC) {
            if (path.equals(p) || path.startsWith("/css/")) return true;
        }
        return false;
    }

    @Override public void init(FilterConfig fc) {}
    @Override public void destroy() {}
}