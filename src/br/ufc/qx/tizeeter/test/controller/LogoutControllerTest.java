package br.ufc.qx.tizeeter.test.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.controller.LogoutController;

public class LogoutControllerTest extends Mockito{
	
	@Test
	public void logout() throws ServletException, IOException{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		
		when(request.getSession(false)).thenReturn(session);
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("login.jsp", invocation.getArgumentAt(0, String.class));
				return null;
			}
		}).when(response).sendRedirect(anyString());
		
		LogoutController servlet = new LogoutController();
		servlet.service(request, response);
		
		verify(session, times(1)).invalidate();
		
	}

}
