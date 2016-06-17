package br.ufc.qx.tizeeter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AutenticacaoFilter implements Filter {

	@Override
	public void destroy() {	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String URI = ((HttpServletRequest) request).getRequestURI();
		HttpSession session = ((HttpServletRequest) request).getSession();

		if (URI.equals("/Tizeeter/"))
			((HttpServletResponse) response).sendRedirect("home");
		else if (URI.contains("login.jsp") || URI.contains("autenticar") || URI.contains("cadastrar.jsp")
				|| URI.endsWith(".css") || URI.endsWith(".js") || URI.endsWith(".ico") || URI.endsWith(".png")
				|| URI.contains("403.jsp") || URI.contains("usuario/novo") || URI.contains("logout"))
			chain.doFilter(request, response);
		else if (session.getAttribute("autenticado") != null && (boolean) session.getAttribute("autenticado")) {
			if ((URI.contains("todos") && (boolean) session.getAttribute("admin")) || URI.contains("home")
					|| URI.contains("index.jsp") || URI.contains("tizeet"))
				chain.doFilter(request, response);
			else
				((HttpServletResponse) response).sendRedirect("403.jsp");
		} else
			((HttpServletResponse) response).sendRedirect("login.jsp");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException { }

}
