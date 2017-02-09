package br.com.inatel.ec206.model.entity;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

public class Heroi {

	private int id;
	private String nome;
	private int idPolicia;
	private int peso;
	private int altura;
	private Blob fotoHeroi;
	private ByteArrayInputStream fotoByteHeroi;
	private int atk;
	private int def;
	private int vida;
	private String statusHeroi;
	private String estadoFisico;
	private String tendenciaMoral;
	
	public int getIdPolicia() {
		return idPolicia;
	}
	public void setIdPolicia(int idPolicia) {
		this.idPolicia = idPolicia;
	}
	public ByteArrayInputStream getFotoByteHeroi() {
		return fotoByteHeroi;
	}
	public void setFotoByteHeroi(ByteArrayInputStream fotoByteHeroi) {
		this.fotoByteHeroi = fotoByteHeroi;
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
	public Blob getFotoHeroi() {
		return fotoHeroi;
	}
	public void setFotoHeroi(Blob fotoHeroi) {
		this.fotoHeroi = fotoHeroi;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getVida() {
		return vida;
	}
	public void setVida(int vida) {
		this.vida = vida;
	}
	public String getStatusHeroi() {
		return statusHeroi;
	}
	public void setStatusHeroi(String statusHeroi) {
		this.statusHeroi = statusHeroi;
	}
	public String getEstadoFisico() {
		return estadoFisico;
	}
	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}
	public String getTendenciaMoral() {
		return tendenciaMoral;
	}
	public void setTendenciaMoral(String tendenciaMoral) {
		this.tendenciaMoral = tendenciaMoral;
	}
}
