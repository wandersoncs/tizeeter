package br.ufc.qx.tizeeter.dao;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.ufc.qx.tizeeter.model.Usuario;

public class UsuarioDAO {

	private static final int COL_NOME_DO_USUARIO = 1;
	private static final int COL_EMAIL = 2;
	private static final int COL_SENHA = 3;
	private static final int COL_NOME = 4;
	private static final int COL_ENDERECO = 5;
	private static final int COL_NASCIMENTO = 6;
	private static final int COL_SEXO = 7;
	private static final int COL_NOVIDADES = 8;
	private static final int COL_ADMIN = 9;

	private static final String COL_LABEL_ID = "id";
	private static final String COL_LABEL_NOME_DO_USUARIO = "login";
	private static final String COL_LABEL_EMAIL = "email";
	private static final String COL_LABEL_NOME = "nome";
	private static final String COL_LABEL_ENDERECO = "endereco";
	private static final String COL_LABEL_DATA_DE_NASCIMENTO = "data_de_nascimento";
	private static final String COL_LABEL_SEXO = "sexo";
	private static final String COL_LABEL_NOVIDADES = "novidades";
	private static final String COL_LABEL_ADMIN = "admin";

	private Connection conexao;
	private static final String AUTENTICACAO_QUERY = "SELECT * FROM usuario WHERE login = ? and senha = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM usuario";
	private static final String BUSCAR_PELO_LOGIN_QUERY = "SELECT * FROM usuario where login = ?";
	private static final String INSERT_QUERY = "INSERT INTO usuario "
			+ " (login, email, senha, nome, endereco, data_de_nascimento, sexo, novidades, admin) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public Usuario adiciona(Usuario usuario) {

		PreparedStatement statement = null;
		try {
			statement = conexao.prepareStatement(INSERT_QUERY);

			statement.setString(COL_NOME_DO_USUARIO, usuario.getNomeDeUsuario());
			statement.setString(COL_EMAIL, usuario.getEmail());
			statement.setString(COL_SENHA, cripitografar(usuario.getSenha()));
			statement.setString(COL_NOME, usuario.getNome());
			statement.setString(COL_ENDERECO, usuario.getEndereco());
			statement.setDate(COL_NASCIMENTO, new Date(usuario.getDataDeNascimento().getTime()));
			statement.setString(COL_SEXO, String.valueOf(usuario.getSexo()));
			statement.setBoolean(COL_NOVIDADES, usuario.getNovidades());
			statement.setBoolean(COL_ADMIN, usuario.isAdmin());

			statement.execute();

			return usuario;
		} catch (MySQLIntegrityConstraintViolationException e) {
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Usuario> getTodos() {
		try (PreparedStatement statement = conexao.prepareStatement(SELECT_ALL_QUERY);
				ResultSet resultado = statement.executeQuery();) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultado.next()) {
				Usuario usuario = getUsuarioApartirDoResultado(resultado);
				usuarios.add(usuario);
			}
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Usuario getUsuarioApartirDoResultado(ResultSet resultado) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(resultado.getLong(COL_LABEL_ID));
		usuario.setNomeDeUsuario(resultado.getString(COL_LABEL_NOME_DO_USUARIO));
		usuario.setEmail(resultado.getString(COL_LABEL_EMAIL));
		usuario.setNome(resultado.getString(COL_LABEL_NOME));
		usuario.setEndereco(resultado.getString(COL_LABEL_ENDERECO));
		usuario.setDataDeNascimento(resultado.getDate(COL_LABEL_DATA_DE_NASCIMENTO));
		usuario.setSexo(resultado.getString(COL_LABEL_SEXO).charAt(0));
		usuario.setNovidades(resultado.getBoolean(COL_LABEL_NOVIDADES));
		usuario.setAdmin(resultado.getBoolean(COL_LABEL_ADMIN));

		return usuario;
	}

	public boolean autenticar(String login, String senha) {
		
		try (PreparedStatement statement = conexao.prepareStatement(AUTENTICACAO_QUERY)) {
			statement.setString(1, login);
			statement.setString(2, cripitografar(senha));
			try (ResultSet resultado = statement.executeQuery()) {
				return resultado.first();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Usuario getUsuario(String login) {
		try (PreparedStatement statement = conexao.prepareStatement(BUSCAR_PELO_LOGIN_QUERY)) {
			statement.setString(1, login);
			try (ResultSet resultado = statement.executeQuery()) {
				if (resultado.first()) {
					return getUsuarioApartirDoResultado(resultado);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private String cripitografar(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] textoEmBytes = md.digest(texto.getBytes("UTF-8"));
			return new BigInteger(1, textoEmBytes).toString(16);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
