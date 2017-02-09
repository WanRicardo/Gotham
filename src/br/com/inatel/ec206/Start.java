package br.com.inatel.ec206;

import java.awt.EventQueue;

import br.com.inatel.ec206.controller.LoginController;

public class Start {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginController.getInstance().newTelaLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}