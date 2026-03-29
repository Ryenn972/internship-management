package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Filtre de sécurité global.
 * - Si l'utilisateur n'est pas connecté → redirige vers /login
 * - Bloque l'accès aux routes réservées selon le rôle :
 *     /admin/*  → rôle 4 (admin)
 *     /manager* → rôle 3 (manager)
 *     /offer POST (add/update/delete) → rôle 2 (company)
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    // Pages publiques (accessibles sans session)
    private static final String[] PUBLIC = {"/login", "/register", "/index.jsp"};

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getServletPath();

        // Laisser passer les ressources statiques et les pages publiques
        if (isPublic(path)) {
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