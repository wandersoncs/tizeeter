package br.ufc.qx.tizeeter.test.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.controller.HomeTimelineController;
import br.ufc.qx.tizeeter.dao.TizeetDAO;
import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

public class HomeTimelineControllerTest extends Mockito {
	
	@Test
	public void homeTimelineTest() throws ServletException, IOException{
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		TizeetDAO dao = mock(TizeetDAO.class);
		
		Usuario usuario = new Usuario();
		usuario.setId(2);
		usuario.setNome("joao das neves");
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuario")).thenReturn(usuario);
		
		Tizeet t1 = new Tizeet();
		t1.setUsuario(usuario);
		t1.setConteudo("opa meu primeiro tizeet");
		
		Tizeet t2 = new Tizeet();
		t2.setUsuario(usuario);
		t2.setConteudo("tizeeteando de novo");
		
		List<Tizeet> tizeets = Arrays.asList(t1, t2);
		
		when(dao.getTodos(usuario)).thenReturn(tizeets);
		
		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("/index.jsp", invocation.getArgumentAt(0, String.class));
				return dispatcher;
			}
		}).when(request).getRequestDispatcher(anyString());
		
		HomeTimelineController servlet = new HomeTimelineController();
		servlet.setDAO(dao);
		servlet.doGet(request, response);
	}

}
