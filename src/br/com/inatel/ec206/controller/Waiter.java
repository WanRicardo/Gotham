package br.com.inatel.ec206.controller;

import java.util.Random;

public class Waiter implements Runnable {

	private Message message;
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

	private Random r;
	
	public Waiter(Message message) {
		this.message = message;
	}
	
	public void run() {
		synchronized (message) {
			try {
				r = new Random();
				setPorradaHeroi(combateController.getAtaqueTotalHeroi());
				setBloqueioHeroi(combateController.getDefesaTotalHeroi());
				setVidaAtualHeroi(combateController.getVidaTotalHeroi());
				
				setPorradaVilao(combateController.getDefesaTotalVilao());
				setBloqueioVilao(combateController.getAtaqueTotalVilao());
//				setVidaAtualVilao(combateController.getVidaTotalVilao());
				
				
				setVidaRestante(getVidaAtualVilao() - getPorradaHeroi());
				setVidaAtualVilao(getVidaRestante());
				System.out.println("Herói atacou com " + getPorradaHeroi() + " de dano.");
//				System.out.println("Vilao defendeu com " + getBloqueioVilao() + " de def.");
				System.out.println("Vilao ficou com " + getVidaAtualVilao() + " de vida.");
				message.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
