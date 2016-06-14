package br.ufc.qx.tizeeter.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ufc.qx.tizeeter.model.Tizeet;
import br.ufc.qx.tizeeter.model.Usuario;

public class TizeetDAO {

	private Connection conexao;

	private static final int COL_CONTEUDO = 1;
	private static final int COL_USUARIO_ID = 2;
	private static final int COL_CREATE_TIME = 3;

	private static final String ADICIONA_TIZEET_QUERY 
								= "INSERT INTO tizeet (conteudo, usuario_id, create_time) values (?, ?, ?) ";
	private static final String SELECT_TIZEET_USUARIO_QUERY 
								= "SELECT * FROM tizeet WHERE usuario_id = ?";

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public Connection getConexao() {
		return conexao;
	}

	public Tizeet adiciona(Tizeet tizeet) {
		if (tizeet.getConteudo() != null && tizeet.getUsuario() != null) {
			try (PreparedStatement statement = getConexao().prepareStatement(ADICIONA_TIZEET_QUERY)) {
				statement.setString(COL_CONTEUDO, tizeet.getConteudo());
				statement.setLong(COL_USUARIO_ID, tizeet.getUsuario().getId());
				statement.setDate(COL_CREATE_TIME, new Date(tizeet.getData().getTime()));
				statement.execute();
				return tizeet;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public List<Tizeet> getTodos(Usuario usuario) {
		List<Tizeet> tizeets = new ArrayList<Tizeet>();
		try (PreparedStatement statement = conexao.prepareStatement(SELECT_TIZEET_USUARIO_QUERY);) {
			statement.setLong(1, usuario.getId());
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					Tizeet tizeet = getTizeet(result);
					tizeet.setUsuario(usuario);
					tizeets.add(tizeet);
				}
			}
		} catch (Exception e) {
			return new ArrayList<Tizeet>();
		}
		return tizeets;
	}

	private Tizeet getTizeet(ResultSet result) throws SQLException {
		Tizeet tizeet = new Tizeet();
		tizeet.setId(result.getLong("id"));
		tizeet.setConteudo(result.getString("conteudo"));
		tizeet.setData(result.getDate("create_time"));
		return tizeet;
	}

}
