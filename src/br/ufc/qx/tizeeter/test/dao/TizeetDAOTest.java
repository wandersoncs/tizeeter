package br.ufc.qx.tizeeter.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ufc.qx.tizeeter.dao.TizeetDAO;
import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

public class TizeetDAOTest extends Mockito {

	private Connection conexao;
	private TizeetDAO dao;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		conexao = DriverManager.getConnection(
				"jdbc:h2:mem:test;MODE=MYSQL;" + "INIT=RUNSCRIPT FROM 'classpath:resource/01-schema.sql'\\;"
						+ "RUNSCRIPT FROM 'classpath:resource/02-adicionando-tipo-de-usuario.sql'\\;"
						+ "RUNSCRIPT FROM 'classpath:resource/03-tizeet-e-timeline.sql'\\;"
						+ "RUNSCRIPT FROM 'classpath:resource/populate-usuario.sql'");

		dao = new TizeetDAO();
		dao.setConexao(conexao);
	}

	@Test
	public void inserirTizeet() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from tizeet");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = new Usuario();
		usuario.setId(1);

		Tizeet tizeet = new Tizeet();
		tizeet.setUsuario(usuario);
		tizeet.setConteudo("Conteudo teste");
		tizeet.setData(new Date());

		dao.adiciona(tizeet);

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(++total, resultado.getInt(1));
		statment.close();
	}

	@Test
	public void inserirTizeetSemConteudo() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from tizeet");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = new Usuario();
		usuario.setId(1);

		Tizeet tizeet = new Tizeet();
		tizeet.setUsuario(usuario);

		assertNull(dao.adiciona(tizeet));

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(total, resultado.getInt(1));
		statment.close();
	}

	@Test
	public void inserirTizeetSemUsuario() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from tizeet");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = new Usuario();

		Tizeet tizeet = new Tizeet();
		tizeet.setConteudo("Conteudo teste");

		assertNull(dao.adiciona(tizeet));

		tizeet.setUsuario(usuario);
		assertNull(dao.adiciona(tizeet));

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(total, resultado.getInt(1));
		statment.close();
	}

	@Test
	public void buscarTodosOsTizeets() throws SQLException {

		Usuario usuario = new Usuario();
		usuario.setId(1);

		Tizeet tizeet1 = new Tizeet();
		tizeet1.setUsuario(usuario);
		tizeet1.setConteudo("Conteudo teste");
		tizeet1.setData(new Date());

		assertTrue(dao.adiciona(tizeet1) != null);

		Tizeet tizeet2 = new Tizeet();
		tizeet2.setUsuario(usuario);
		tizeet2.setConteudo("Tizeet numero 2");
		tizeet2.setData(new Date());

		assertTrue(dao.adiciona(tizeet2) != null);

		List<Tizeet> resultado = dao.getTodos(usuario);

		assertEquals(2, resultado.size());
		assertEquals(tizeet1, resultado.get(0));
		assertEquals(tizeet2, resultado.get(1));
	}

	@After
	public void tearDown() throws SQLException {
		conexao.close();
	}

}
