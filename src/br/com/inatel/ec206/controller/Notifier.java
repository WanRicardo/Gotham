package br.com.inatel.ec206.controller;

import java.util.Date;
import java.util.Random;

public class Notifier implements Runnable {

	private Message message;
	private Random r;
	private CombateController combateController = CombateController.getInstance();
	int resultadoAtaqueHeroi, vidaRestante, porradaHeroi, bloqueioHeroi, vidaAtualHeroi, porradaVilao, bloqueioVilao, vidaAtualVilao;

	public int getResultadoAtaqueHeroi() {
		return resultadoAtaqueHeroi;
	}

	public void setResultadoAtaqueHeroi(int resultadoAtaqueHeroi) {
		this.resultadoAtaqueHeroi = resultadoAtaqueHeroi;
	}

	public int getVidaRestante() {
		return vidaRestante;
	}

	public void setVidaRestante(int vidaRestante) {
		this.vidaRestante = vidaRestante;
	}

	public int getPorradaHeroi() {
		return porradaHeroi;
	}

	public void setPorradaHeroi(int porradaHeroi) {
		this.porradaHeroi = porradaHeroi;
	}

	public int getBloqueioHeroi() {
		return bloqueioHeroi;
	}

	public void setBloqueioHeroi(int bloqueioHeroi) {
		this.bloqueioHeroi = bloqueioHeroi;
	}

	public int getVidaAtualHeroi() {
		return vidaAtualHeroi;
	}

	public void setVidaAtualHeroi(int vidaAtualHeroi) {
		this.vidaAtualHeroi = vidaAtualHeroi;
	}

	public int getPorradaVilao() {
		return porradaVilao;
	}

	public void setPorradaVilao(int porradaVilao) {
		this.porradaVilao = porradaVilao;
	}

	public int getBloqueioVilao() {
		return bloqueioVilao;
	}

	public void setBloqueioVilao(int bloqueioVilao) {
		this.bloqueioVilao = bloqueioVilao;
	}

	public int getVidaAtualVilao() {
		return vidaAtualVilao;
	}

	public void setVidaAtualVilao(int vidaAtualVilao) {
		this.vidaAtualVilao = vidaAtualVilao;
	}

	public Notifier(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		r = new Random();
		setPorradaHeroi(combateController.getAtaqueTotalHeroi());
		setBloqueioHeroi(combateController.getDefesaTotalHeroi());
		setVidaAtualHeroi(combateController.getVidaTotalHeroi());
		
		setPorradaVilao(combateController.getDefesaTotalVilao());
		setBloqueioVilao(combateController.getAtaqueTotalVilao());
//		setVidaAtualVilao(combateController.getVidaTotalVilao());
		
		setVidaRestante(getVidaAtualHeroi() - getPorradaVilao());
		setVidaAtualHeroi(getVidaRestante());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		synchronized (message) {
			System.out.println("Vilao atacou com " + getPorradaVilao() + " de dano.");
//			System.out.println("Heroi defendeu com " + getBloqueioHeroi() + " de def.");
			System.out.println("Heroi ficou com " + getVidaAtualHeroi() + " de vida.");
			message.setText("Luta demorou 1 segundo " + new Date());
			message.notify();
		}

	}

}