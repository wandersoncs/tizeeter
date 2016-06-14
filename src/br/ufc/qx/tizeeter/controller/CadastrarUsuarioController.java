package br.ufc.qx.tizeeter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

@WebServlet(name="CadastrarUsuario", urlPatterns="/usuario/novo")
public class CadastrarUsuarioController extends TizeeterGenericBaseHttpServlet<UsuarioDAO>{

	private static final long serialVersionUID = -1605679868630199122L;
	
	public CadastrarUsuarioController() {
		setDAO(new UsuarioDAO());
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UsuarioDAO usuarioDAO = getDAO();
		usuarioDAO.setConexao((Connection) request.getAttribute("connection"));
		
		
		Usuario usuario = new Usuario();
		usuario.setNomeDeUsuario(request.getParameter("username"));
		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("password"));
		usuario.setNome(request.getParameter("name"));
		usuario.setEndereco(request.getParameter("address"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataDeNascimento;
		try {
			dataDeNascimento = format.parse(request.getParameter("year") +
					"-" + request.getParameter("month") + 
					"-" + request.getParameter("day"));
			usuario.setDataDeNascimento(dataDeNascimento);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		} 
		
		usuario.setSexo(request.getParameter("sex").toCharArray()[0]);
		usuario.setNovidades("s".equalsIgnoreCase(request.getParameter("news")));
		PrintWriter out = response.getWriter();
		
		if(usuarioDAO.adiciona(usuario) == null){
			showNegativeFeedback(out, usuario);
		} else {		
			showPositiveFeedback(out, usuario);
		}
	}

	private void showPositiveFeedback(PrintWriter out, Usuario usuario) throws IOException {
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/layout.css\"/>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/principal.css\"/>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/login.css\"/>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"conteudo\">");
		out.println("<div id=\"principal\" class=\"module\">");
		out.println("<p>Usu&aacute;rio cadastrado com sucesso</p>");
		out.println("<p>Verifique seu email:"+usuario.getEmail()+"</p>");
		out.println("<hr/>");
		out.println("<p><a href=\"login.jsp\">Entrar</a></p>");
		out.println("</div>");
		out.println("</body></html>");
	}
	
	private void showNegativeFeedback(PrintWriter out, Usuario usuario) throws IOException {
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/layout.css\"/>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/principal.css\"/>");
		out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../css/login.css\"/>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div id=\"conteudo\">");
		out.println("<div id=\"principal\" class=\"module error\">");
		out.println("<p>Erro</p>");
		out.println("<p>Usuario com login ou email ja existentes</p>");
		out.println("<hr/>");
		out.println("<p><a href=\"../cadastrar.jsp\">Voltar</a></p>");
		out.println("</div>");
		out.println("</body></html>");
	}

}
