package br.ufc.qx.tizeeter.test.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.controller.ListarUsuarioController;
import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

public class ListarUsuarioControllerTest extends Mockito{
	
	
	@Test
	public void testService() throws IOException, ServletException{
		HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		ListarUsuarioController servlet = new ListarUsuarioController();
		
		UsuarioDAO dao = mock(UsuarioDAO.class);
		
		Usuario usuario = new Usuario();
		usuario.setNome("margarida");
		usuario.setSenha("123456");
		usuario.setNomeDeUsuario("flor");
		usuario.setEmail("margarida@gmail.com");
		usuario.setDataDeNascimento(new Date());
		usuario.setEndereco("rua x");
		usuario.setSexo('m');
		usuario.setNovidades(true);
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		when(dao.getTodos()).thenReturn(usuarios);
		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("todos.jsp", invocation.getArgumentAt(0, String.class));
				return dispatcher;
			}
		}).when(stubHttpServletRequest).getRequestDispatcher(anyString());

		
		servlet.setDAO(dao);
		servlet.service(stubHttpServletRequest, stubHttpServletResponse);
		
		verify(dao, times(1)).setConexao(any());
		
	}

}
