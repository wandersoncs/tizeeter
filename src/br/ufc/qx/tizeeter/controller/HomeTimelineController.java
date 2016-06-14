package br.ufc.qx.tizeeter.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufc.qx.tizeeter.dao.TizeetDAO;
import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

@WebServlet(urlPatterns = "/home")
public class HomeTimelineController extends TizeeterGenericBaseHttpServlet<TizeetDAO> {

	private static final long serialVersionUID = -9190372911923612234L;

	public HomeTimelineController() {
		setDAO(new TizeetDAO());
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getDAO().setConexao((Connection) req.getAttribute("connection"));
		HttpSession session = req.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		List<Tizeet> tizeets = getDAO().getTodos(usuario);
		req.setAttribute("tizeets", tizeets);
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
