package br.ufc.qx.tizeeter.test.filter;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.filter.AutenticacaoFilter;

public class AutenticacaoFilterTest extends Mockito {

	@Before
	public void setUp() {

	}

	@Test
	public void usuarioAcessaPaginaPublica() throws IOException, ServletException {

		String[] paginasPublicas = { "/login.jsp", "/cadastrar.jsp", "/autenticar", "/usuario/novo", "/logout", ".css",
				".js", "403.jsp" };

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		AutenticacaoFilter filtro = new AutenticacaoFilter();
		for (int i = 0; i < paginasPublicas.length; i++) {
			when(request.getRequestURI()).thenReturn(paginasPublicas[i]);
			filtro.doFilter(request, response, chain);
		}

		verify(chain, times(paginasPublicas.length)).doFilter(request, response);
	}

	@Test
	public void usuarioNaoAutenticadoAcessaPaginaPrivada() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		when(request.getSession()).thenReturn(null);
		when(request.getRequestURI()).thenReturn("/todos");

		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("/login.jsp", invocation.getArgumentAt(0, String.class));
				return dispatcher;
			}
		}).when(request).getRequestDispatcher(anyString());

		AutenticacaoFilter filtro = new AutenticacaoFilter();
		filtro.doFilter(request, response, chain);

	}

	@Test
	public void usuarioAutenticadoSemPermissaodeAcesso() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		FilterChain chain = mock(FilterChain.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		when(request.getRequestURI()).thenReturn("/todos");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("autenticado")).thenReturn(true);
		when(session.getAttribute("admin")).thenReturn(false);

		doAnswer(new Answer<RequestDispatcher>() {

			@Override
			public RequestDispatcher answer(InvocationOnMock invocation) throws Throwable {
				assertEquals("/403.jsp", invocation.getArgumentAt(0, String.class));
				return dispatcher;
			}
		}).when(request).getRequestDispatcher(anyString());

		AutenticacaoFilter filtro = new AutenticacaoFilter();
		filtro.doFilter(request, response, chain);

	}

	@Test
	public void usuarioAdminAcesso() throws IOException, ServletException {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		FilterChain chain = mock(FilterChain.class);

		when(request.getRequestURI()).thenReturn("/todos");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("autenticado")).thenReturn(true);
		when(session.getAttribute("admin")).thenReturn(true);

		AutenticacaoFilter filtro = new AutenticacaoFilter();
		filtro.doFilter(request, response, chain);
		verify(chain, times(1)).doFilter(request, response);
	}

}
