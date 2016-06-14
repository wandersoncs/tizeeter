package br.ufc.qx.tizeeter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

@WebServlet(name="ListarUsuarios", urlPatterns="/todos")
public class ListarUsuarioController extends TizeeterGenericBaseHttpServlet<UsuarioDAO> {

	private static final long serialVersionUID = -5539645030336411978L;
	
	
	public ListarUsuarioController() {
		setDAO(new UsuarioDAO());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UsuarioDAO usuarioDAO = getDAO();
		usuarioDAO.setConexao((Connection) request.getAttribute("connection"));
		List<Usuario> usuarios = usuarioDAO.getTodos();
		RequestDispatcher dispatcher = request.getRequestDispatcher("todos.jsp");
		request.setAttribute("usuarios", usuarios);
		dispatcher.forward(request, response);
	}

}

