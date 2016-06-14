package br.ufc.qx.tizeeter.test.filter;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.ufc.qx.tizeeter.filter.InjectConnectionFilter;
import br.ufc.qx.tizeeter.model.ConnectionFactory;

public class InjectConnectionFilterTest extends Mockito{
	
	private HashMap<String, Object> attributes;
	
	@Before
	public void setUp(){
		attributes = new HashMap<String, Object>();
	}
	
	@Test
	public void injectedConnectionTest() throws IOException, ServletException{
		ConnectionFactory factory = mock(ConnectionFactory.class);
		Connection connection = mock(Connection.class);
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);
		
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
		        attributes.put(args[0].toString(), args[1]);
				return null;
			}
		}).when(request).setAttribute(anyString(), anyObject());
		
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return attributes.get(invocation.getArguments()[0]);
			}
		}).when(request).getAttribute(anyString());
		
		when(factory.getConnection()).thenReturn(connection);
		
		InjectConnectionFilter filter = new InjectConnectionFilter();
		filter.setFactory(factory);
		filter.doFilter(request, response, chain);
		assertEquals(connection, request.getAttribute("connection"));
	}

}
