package br.ufc.qx.tizeeter.test.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.controller.TizeetController;
import br.ufc.qx.tizeeter.dao.TizeetDAO;
import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

public class TizeetControllerTest extends Mockito {

	@Test
	public void postarTizeetTest() throws IOException, ServletException {

		TizeetDAO dao = mock(TizeetDAO.class);

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		Usuario usuario = new Usuario();
		usuario.setId(1);

		Tizeet tizeet = new Tizeet();
		tizeet.setUsuario(usuario);
		tizeet.setConteudo("conteudo de teste");

		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuario")).thenReturn(usuario);
		when(request.getParameter("tizeet")).thenReturn(tizeet.getConteudo());
		when(dao.adiciona(any(Tizeet.class))).thenReturn(tizeet);

		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("", invocation.getArgumentAt(0, String.class));
				return dispatcher;
			}
		}).when(response).sendRedirect(anyString());

		TizeetController servlet = new TizeetController();
		servlet.setDAO(dao);
		servlet.doPost(request, response);

		verify(dao, times(1)).setConexao(any());
	}

}
