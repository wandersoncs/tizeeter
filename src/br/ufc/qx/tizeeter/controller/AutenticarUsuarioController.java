package br.ufc.qx.tizeeter.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

@WebServlet(urlPatterns="/autenticar")
public class AutenticarUsuarioController extends TizeeterGenericBaseHttpServlet<UsuarioDAO>{

	private static final String FLAG_AUTENTIFICADO = "autenticado";
	private static final long serialVersionUID = 1979208128920557545L;
	
	public AutenticarUsuarioController() {
		setDAO(new UsuarioDAO());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		UsuarioDAO usuarioDAO = getDAO();
		usuarioDAO.setConexao((Connection) request.getAttribute("connection"));
		boolean autenticado = usuarioDAO.autenticar(login, senha);
		
		if(autenticado){
			HttpSession sessao = request.getSession(true);
			sessao.setAttribute(FLAG_AUTENTIFICADO, true);
			
			Usuario usuario = usuarioDAO.getUsuario(login);
			sessao.setAttribute("usuario", usuario);
			sessao.setAttribute("admin", usuario.isAdmin());
			response.sendRedirect("home");
		} else {			
			request.setAttribute(FLAG_AUTENTIFICADO, false);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
