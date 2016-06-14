package br.ufc.qx.tizeeter.model;

import java.util.Date;

public class Usuario {

	private Long id;
	private String nomeDeUsuario;
	private String email;
	private String senha;

	private String nome;
	private String endereco;
	private Date dataDeNascimento;
	private char sexo;
	private boolean novidades;
	private boolean admin;

	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public boolean getNovidades() {
		return novidades;
	}

	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public void setNovidades(boolean novidades) {
		this.novidades = novidades;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Long getId(){
		return this.id;
	}
	
	

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nomeDeUsuario == null) ? 0 : nomeDeUsuario.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nomeDeUsuario == null) {
			if (other.nomeDeUsuario != null)
				return false;
		} else if (!nomeDeUsuario.equals(other.nomeDeUsuario))
			return false;
		return true;
	}
	
	

}
