package br.com.inatel.ec206.model.entity;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

public class Vilao {

	private int id;
	private String nome;
	private int idPolicia;
	private int peso;
	private int altura;
	private Blob fotoVilao;
	private ByteArrayInputStream fotoByteVilao;
	private int ataque;
	private int defesa;
	private int vida;
	private String status;
	private String estadoFisico;
	
	public int getIdPolicia() {
		return idPolicia;
	}
	public void setIdPolicia(int idPolicia) {
		this.idPolicia = idPolicia;
	}
	public ByteArrayInputStream getFotoByteVilao() {
		return fotoByteVilao;
	}
	public void setFotoByteVilao(ByteArrayInputStream fotoByteVilao) {
		this.fotoByteVilao = fotoByteVilao;
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
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public Blob getFotoVilao() {
		return fotoVilao;
	}
	public void setFotoVilao(Blob fotoVilao) {
		this.fotoVilao = fotoVilao;
	}
	public int getAtaque() {
		return ataque;
	}
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	public int getDefesa() {
		return defesa;
	}
	public void setDefesa(int defesa) {
		this.defesa = defesa;
	}
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEstadoFisico() {
		return estadoFisico;
	}
	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}
	
}
