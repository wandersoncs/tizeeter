package br.ufc.qx.tizeeter.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

import br.ufc.qx.tizeeter.dao.UsuarioDAO;
import br.ufc.qx.tizeeter.model.Usuario;

public class UsuarioDAOTest extends Mockito {

	private static final String SENHA_DE_TESTE = "123456";
	private static final String LOGIN_DE_TESTE = "joazin";
	private Connection conexao;
	private UsuarioDAO dao;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		conexao = DriverManager
				.getConnection("jdbc:h2:mem:test;MODE=MYSQL;" + 
						"INIT=RUNSCRIPT FROM 'classpath:resource/01-schema.sql'\\;" +
						"RUNSCRIPT FROM 'classpath:resource/02-adicionando-tipo-de-usuario.sql'\\;"+
						"RUNSCRIPT FROM 'classpath:resource/populate-usuario.sql'");

		dao = new UsuarioDAO();
		dao.setConexao(conexao);
	}

	@Test
	public void inserirUsuario() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from usuario");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = getUsuarioTeste();

		dao.adiciona(usuario);

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(++total, resultado.getInt(1));
		statment.close();
	}

	@Test
	public void inserirComLoginDuplicadoUsuario() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from usuario");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = getUsuarioTeste();

		dao.adiciona(usuario);
		usuario.setEmail("diferente@gmail.com");
		
		exception.expect(RuntimeException.class);
		dao.adiciona(usuario);

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(++total, resultado.getInt(1));
		statment.close();
	}

	@Test
	public void inserirComEmailDuplicadoUsuario() throws SQLException {
		PreparedStatement statment = conexao.prepareStatement("SELECT count(*) from usuario");
		ResultSet resultado = statment.executeQuery();
		resultado.first();
		int total = resultado.getInt(1);

		Usuario usuario = getUsuarioTeste();

		dao.adiciona(usuario);
		usuario.setNomeDeUsuario("zeramalho");
		
		exception.expect(RuntimeException.class);
		dao.adiciona(usuario);

		resultado = statment.executeQuery();
		assertTrue(resultado.next());
		assertEquals(++total, resultado.getInt(1));
		statment.close();
	}

	private Usuario getUsuarioTeste() {
		Usuario usuario = new Usuario();
		usuario.setNome("joao augusto");
		usuario.setSenha(SENHA_DE_TESTE);
		usuario.setNomeDeUsuario(LOGIN_DE_TESTE);
		usuario.setEmail("joaoaugusto@gmail.com");
		usuario.setDataDeNascimento(new Date());
		usuario.setEndereco("rua x");
		usuario.setSexo('m');
		usuario.setNovidades(true);
		return usuario;
	}
	
	private Usuario getUsuarioTesteAdmin() {
		Usuario usuario = new Usuario();
		usuario.setNome("joao das neves");
		usuario.setSenha(SENHA_DE_TESTE);
		usuario.setNomeDeUsuario("dasneves");
		usuario.setEmail("dasneves@blackcastle.com");
		usuario.setDataDeNascimento(new Date());
		usuario.setEndereco("muralha");
		usuario.setSexo('m');
		usuario.setNovidades(true);
		usuario.setAdmin(true);
		return usuario;
	}

	@Test
	public void recuperarTodos() throws SQLException {
		List<Usuario> usuarios = dao.getTodos();
		assertFalse(usuarios.isEmpty());
		assertEquals(25, usuarios.size());
	}
	
	@Test
	public void loginValido() {
		Usuario usuario = getUsuarioTeste();
		dao.adiciona(usuario);
		
		assertTrue(dao.autenticar(LOGIN_DE_TESTE, SENHA_DE_TESTE));
		
	}
	
	@Test
	public void loginInvalido() {
		Usuario usuario = getUsuarioTeste();
		dao.adiciona(usuario);
		
		assertFalse(dao.autenticar(LOGIN_DE_TESTE, ""));
	}
	
	
	@Test
	public void buscarUsuarioCadastradoPeloLogin(){
		Usuario usuario = getUsuarioTeste();
		dao.adiciona(usuario);		
		assertEquals(usuario, dao.getUsuario(usuario.getNomeDeUsuario()));
	}
	
	@Test
	public void buscarUsuarioAdminCadastradoPeloLogin(){
		Usuario usuario = getUsuarioTesteAdmin();
		dao.adiciona(usuario);		
		assertEquals(usuario, dao.getUsuario(usuario.getNomeDeUsuario()));
		assertTrue(usuario.isAdmin());
	}
	
	
	@Test
	public void buscarUsuarioNaoCadastradoPeloLogin(){
		Usuario usuario = getUsuarioTeste();
		assertNull(dao.getUsuario(usuario.getNomeDeUsuario()));
	}
	

	@After
	public void tearDown() throws SQLException {
		conexao.close();
	}

}
