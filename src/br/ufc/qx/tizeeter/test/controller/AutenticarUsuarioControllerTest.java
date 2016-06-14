package br.ufc.qx.tizeeter.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.controller.AutenticarUsuarioController;
import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

public class AutenticarUsuarioControllerTest extends Mockito{
	
	private Usuario joaoDasNeves;
	private HashMap<String, Object> attributes;
	
	@Before
	public void setUp(){
		
		joaoDasNeves = new Usuario();
		joaoDasNeves.setNome("Joao das Neves");
		joaoDasNeves.setNomeDeUsuario("dasneves");
		joaoDasNeves.setEmail("joaodasneves@castelonregro.com");
		joaoDasNeves.setSenha("123456");
		attributes = new HashMap<>();
	}
	
	@Test
	public void usuarioConsegueAutenticar() throws ServletException, IOException{
		
		HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
		HttpSession stubHttpSession = mock(HttpSession.class);
		
		UsuarioDAO dao = mock(UsuarioDAO.class);
		
		
		when(stubHttpServletRequest.getParameter("login")).thenReturn(joaoDasNeves.getNomeDeUsuario());
		when(stubHttpServletRequest.getParameter("senha")).thenReturn(joaoDasNeves.getSenha());
		when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);
		when(stubHttpServletRequest.getSession(true)).thenReturn(stubHttpSession);
		when(dao.autenticar(joaoDasNeves.getNomeDeUsuario(), joaoDasNeves.getSenha())).thenReturn(true);
		when(dao.getUsuario(joaoDasNeves.getNomeDeUsuario())).thenReturn(joaoDasNeves);
		
		doAnswer(salvaAtributos()).when(stubHttpSession).setAttribute(anyString(), anyObject());
		doAnswer(recuperaAtributos()).when(stubHttpSession).getAttribute(anyString());
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("", invocation.getArgumentAt(0, String.class));
				return null;
			}
		}).when(stubHttpServletResponse).sendRedirect(anyString());
		
		AutenticarUsuarioController servlet = new AutenticarUsuarioController();
		servlet.setDAO(dao);
		servlet.service(stubHttpServletRequest, stubHttpServletResponse);
		
		verify(dao, times(1)).setConexao(any());
		assertTrue((Boolean)(stubHttpSession.getAttribute("autenticado")));
		assertEquals(joaoDasNeves, stubHttpSession.getAttribute("usuario"));
		assertEquals(joaoDasNeves.isAdmin(), stubHttpSession.getAttribute("admin"));
	}
	
	
	@Test
	public void usuarioFalhaAoAutenticar() throws ServletException, IOException{
		
		HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
		HttpSession stubHttpSession = mock(HttpSession.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		UsuarioDAO dao = mock(UsuarioDAO.class);
		
		
		when(stubHttpServletRequest.getParameter("login")).thenReturn(joaoDasNeves.getNomeDeUsuario());
		when(stubHttpServletRequest.getParameter("senha")).thenReturn(joaoDasNeves.getSenha());
		when(stubHttpServletRequest.getSession()).thenReturn(stubHttpSession);
		when(stubHttpServletRequest.getSession(true)).thenReturn(stubHttpSession);
		when(dao.autenticar(anyString(), anyString())).thenReturn(false);
		
		doAnswer(salvaAtributos()).when(stubHttpServletRequest).setAttribute(anyString(), anyObject());
		doAnswer(recuperaAtributos()).when(stubHttpServletRequest).getAttribute(anyString());
		
		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("login.jsp", invocation.getArguments()[0]);
				return dispatcher;
			}
		}).when(stubHttpServletRequest).getRequestDispatcher(anyString());
		
		AutenticarUsuarioController servlet = new AutenticarUsuarioController();
		servlet.setDAO(dao);
		servlet.service(stubHttpServletRequest, stubHttpServletResponse);
		
		verify(dao, times(1)).setConexao(any());
		assertFalse((Boolean)(stubHttpServletRequest.getAttribute("autenticado")));
	}

	private Answer<Object> recuperaAtributos() {
		return new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return 	attributes.get(invocation.getArguments()[0]);
			}
		};
	}

	private Answer<Void> salvaAtributos() {
		return new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		        attributes.put(args[0].toString(), args[1]);
				return null;
			}
		};
	}
	
	@After
	public void tearDown(){
		
	}

}
