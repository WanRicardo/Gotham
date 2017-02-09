package br.com.inatel.ec206.model.entity;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

public class Arma {
	private int idArma;
	private String nomeArma;
	private int pesoArma;
	private Blob imgArma;
	private ByteArrayInputStream imgByteArma;
	private int ataque;
	private int defesa;
	private int mao;
	
	public int getMao() {
		return mao;
	}
	public void setMao(int mao) {
		this.mao = mao;
	}
	public int getIdArma() {
		return idArma;
	}
	public void setIdArma(int idArma) {
		this.idArma = idArma;
	}
	public String getNomeArma() {
		return nomeArma;
	}
	public void setNomeArma(String nomeArma) {
		this.nomeArma = nomeArma;
	}
	public int getPesoArma() {
		return pesoArma;
	}
	public void setPesoArma(int pesoArma) {
		this.pesoArma = pesoArma;
	}
	public Blob getImgArma() {
		return imgArma;
	}
	public void setImgArma(Blob imgArma) {
		this.imgArma = imgArma;
	}
	public ByteArrayInputStream getImgByteArma() {
		return imgByteArma;
	}
	public void setImgByteArma(ByteArrayInputStream imgByteArma) {
		this.imgByteArma = imgByteArma;
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
	
}
