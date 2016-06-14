package br.ufc.qx.tizeeter.test.controller;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import br.ufc.qx.tizeeter.controller.CadastrarUsuarioController;
import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

public class CadastrarUsuarioControllerTest extends Mockito{

	@Test
	public void testService() throws IOException, ServletException{
		
		UsuarioDAO dao = mock(UsuarioDAO.class);
		
		HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
		
		String[] requestData = {"usuario", "usuario@gmail.com", "123456", "1234567",
				"usuario da silva", "rua sem nome", "01", "02", "1980", "m", "sim"
		};
		
		when(stubHttpServletRequest.getParameter("username")).thenReturn(requestData[0]);
		when(stubHttpServletRequest.getParameter("email")).thenReturn(requestData[1]);
		when(stubHttpServletRequest.getParameter("password")).thenReturn(requestData[2]);
		when(stubHttpServletRequest.getParameter("conf-password")).thenReturn(requestData[3]);
		
		when(stubHttpServletRequest.getParameter("name")).thenReturn(requestData[4]);
		when(stubHttpServletRequest.getParameter("address")).thenReturn(requestData[5]);
		when(stubHttpServletRequest.getParameter("day")).thenReturn(requestData[6]);
		when(stubHttpServletRequest.getParameter("month")).thenReturn(requestData[7]);
		when(stubHttpServletRequest.getParameter("year")).thenReturn(requestData[8]);
		when(stubHttpServletRequest.getParameter("sex")).thenReturn(requestData[9]);
		when(stubHttpServletRequest.getParameter("news")).thenReturn(requestData[10]);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		when(stubHttpServletResponse.getWriter()).thenReturn(pw);
		
		CadastrarUsuarioController servlet = new CadastrarUsuarioController();
		servlet.setDAO(dao);
		when(dao.adiciona(any(Usuario.class))).thenReturn(new Usuario());
		servlet.service(stubHttpServletRequest, stubHttpServletResponse);
		
		verify(dao, times(1)).setConexao(any());
		String result = sw.getBuffer().toString().trim();
		assertTrue(result.contains(requestData[1]));
	}
	
	
	@Test
	public void testServiceComUsuarioDuplicado() throws IOException, ServletException{
		
		UsuarioDAO dao = mock(UsuarioDAO.class);
		HttpServletRequest stubHttpServletRequest = mock(HttpServletRequest.class);
		HttpServletResponse stubHttpServletResponse = mock(HttpServletResponse.class);
		
		String[] requestData = {"usuario", "usuario@gmail.com", "123456", "1234567",
				"usuario da silva", "rua sem nome", "01", "02", "1980", "m", "sim"
		};
		
		when(stubHttpServletRequest.getParameter("username")).thenReturn(requestData[0]);
		when(stubHttpServletRequest.getParameter("email")).thenReturn(requestData[1]);
		when(stubHttpServletRequest.getParameter("password")).thenReturn(requestData[2]);
		when(stubHttpServletRequest.getParameter("conf-password")).thenReturn(requestData[3]);
		
		when(stubHttpServletRequest.getParameter("name")).thenReturn(requestData[4]);
		when(stubHttpServletRequest.getParameter("address")).thenReturn(requestData[5]);
		when(stubHttpServletRequest.getParameter("day")).thenReturn(requestData[6]);
		when(stubHttpServletRequest.getParameter("month")).thenReturn(requestData[7]);
		when(stubHttpServletRequest.getParameter("year")).thenReturn(requestData[8]);
		when(stubHttpServletRequest.getParameter("sex")).thenReturn(requestData[9]);
		when(stubHttpServletRequest.getParameter("news")).thenReturn(requestData[10]);
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		when(stubHttpServletResponse.getWriter()).thenReturn(pw);
		
		CadastrarUsuarioController servlet = new CadastrarUsuarioController();
		servlet.setDAO(dao);
		servlet.service(stubHttpServletRequest, stubHttpServletResponse);
		when(dao.adiciona(any(Usuario.class))).thenReturn(null);
		
		verify(dao, times(1)).setConexao(any());
		String result = sw.getBuffer().toString().trim();
		assertTrue(result.contains("Erro"));
		assertTrue(result.contains("Usuario com login ou email ja existentes"));
	}
}
