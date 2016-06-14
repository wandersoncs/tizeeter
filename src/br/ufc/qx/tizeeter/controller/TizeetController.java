package br.ufc.qx.tizeeter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufc.qx.tizeeter.dao.TizeetDAO;
import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

@WebServlet(name = "Tizeet", urlPatterns = "/tizeet")
public class TizeetController extends TizeeterGenericBaseHttpServlet<TizeetDAO> {

	private static final long serialVersionUID = 5288235092948947056L;

	public TizeetController() {
		setDAO(new TizeetDAO());
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		getDAO().setConexao((Connection) request.getAttribute("connection"));
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		String conteudo = request.getParameter("tizeet");
		
		Tizeet tizeet = new Tizeet();
		tizeet.setConteudo(conteudo);
		tizeet.setUsuario(usuario);
		tizeet.setData(Calendar.getInstance().getTime());
		
		TizeetDAO tizeetDAO = getDAO();
		tizeetDAO.adiciona(tizeet);
		
		response.sendRedirect("home");
	}

}
