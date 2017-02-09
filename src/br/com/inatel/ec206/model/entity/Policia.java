package br.com.inatel.ec206.model.entity;

import java.io.ByteArrayInputStream;
import java.sql.Blob;


public class Policia {
	
	private int id;
	private int idAdmin;
	private String nome;
	private int peso;
	private int altura;
	private Blob fotoPolicia;
	private ByteArrayInputStream fotoBytePolicia;
	private String cargo;
	private int numPrisioneiros;
	private String usuario;
	private String senha;
	
	public ByteArrayInputStream getFotoBytePolicia() {
		return fotoBytePolicia;
	}
	public void setFotoBytePolicia(ByteArrayInputStream fotoBytePolicia) {
		this.fotoBytePolicia = fotoBytePolicia;
	}
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public Blob getFotoPolicia() {
		return fotoPolicia;
	}
	public void setFotoPolicia(Blob fotoPolicia) {
		this.fotoPolicia = fotoPolicia;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public int getNumPrisioneiros() {
		return numPrisioneiros;
	}
	public void setNumPrisioneiros(int numPrisioneiros) {
		this.numPrisioneiros = numPrisioneiros;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/*public Policia() {
		super();
		this.id = 0;
		this.nome = null;
		this.altura = 0;
		this.peso = 0;
		this.usuario = null;
		this.senha = null;
	}	
	public Policia(Integer id, String nome, Integer altura, Integer peso,
			String usuario, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
		this.usuario = usuario;
		this.senha = senha;
	}
	public Policia(Integer id, String nome, Integer altura, Integer peso,
			String usuario) {
		super();
		this.id = id;
		this.nome = nome;
		this.altura = altura;
		this.peso = peso;
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Policia [id=" + id + ", nome=" + nome + ", altura=" + altura
				+ ", peso=" + peso + ", usuario=" + usuario + "]";
	}*/
}
