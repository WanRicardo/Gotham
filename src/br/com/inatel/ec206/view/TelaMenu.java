package br.com.inatel.ec206.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.inatel.ec206.controller.LoginController;

public class TelaMenu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JLabel lblNome, imgGothamPD, lblData;
	private JScrollPane scrLista;
	private JButton btnPresidiarios, btnHerois, btnViloes, btnLogout, btnCadastros; 
	private Container telaMenu, containerLista, esquerda;
	private Image gpdMajor;
	private JMenuBar barraDeMenu;
	private JMenu menuOpcoes, menu;
	private JMenuItem relatorios, novoRelatorio, abrirRelatorio, armas, novaArma, equiparArma, duelo;
	
	public JMenuItem getNovaArma() {
		return novaArma;
	}
	public JMenuItem getEquiparArma() {
		return equiparArma;
	}
	public JMenuItem getDuelo() {
		return duelo;
	}
	public JMenuItem getAbrirRelatorio() {
		return abrirRelatorio;
	}
	public JMenuItem getNovoRelatorioPolicial() {
		return novoRelatorio;
	}
	public JLabel getLblNome() {
		return lblNome;
	}
	public JButton getBtnCadastros() {
		return btnCadastros;
	}
	public JButton getBtnPresidiarios() {
		return btnPresidiarios;
	}
	public JButton getBtnHerois() {
		return btnHerois;
	}
	public JButton getBtnViloes() {
		return btnViloes;
	}
	public JButton getBtnLogout() {
		return btnLogout;
	}
	
	public TelaMenu() {
		setTitle("MENU - Sistema Prisional de Gotham");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(989, 577);
		setLocationRelativeTo(null);
		setResizable(false);
		
		telaMenu = getContentPane();
		telaMenu.setLayout(null);
		
		barraDeMenu = new JMenuBar();
		setJMenuBar(barraDeMenu);
		
		menuOpcoes = new JMenu("Opções");
		menuOpcoes.setMnemonic('O');
		barraDeMenu.add(menuOpcoes);
		
		relatorios = new JMenu("Relatórios");
		relatorios.setMnemonic('R');
		menuOpcoes.add(relatorios);
		
		novoRelatorio = new JMenuItem("Gerar Novo");
		novoRelatorio.setMnemonic('G');
		relatorios.add(novoRelatorio);
		abrirRelatorio = new JMenuItem("Abrir Relatório");
		abrirRelatorio.setMnemonic('A');
		relatorios.add(abrirRelatorio);
		
		menu = new JMenu("Menu");
		menu.setMnemonic('M');
		barraDeMenu.add(menu);
		
		armas = new JMenu("Armas");
		armas.setMnemonic('s');
		menu.add(armas);
		
		novaArma = new JMenuItem("Nova arma");
		novaArma.setMnemonic('N');
		armas.add(novaArma);
		
		equiparArma = new JMenuItem("Equipar arma");
		equiparArma.setMnemonic('E');
		armas.add(equiparArma);
		
		duelo = new JMenuItem("Duelo");
		duelo.setMnemonic('D');
		menu.add(duelo);
		
		lblNome = new JLabel();
		lblNome.setBounds(10, 184, 400, 25);
		telaMenu.add(lblNome);
		
		lblData = new JLabel(getData());
		lblData.setFont(new Font("serif", Font.BOLD, 17));
		lblData.setBounds(880, 184, 90, 25);
		telaMenu.add(lblData);
		
		containerLista = new JPanel();
		containerLista.setBounds(170, 214, 802, 302);
		containerLista.setLayout(new GridLayout(1, 1));
		telaMenu.add(containerLista);
		
		scrLista = new JScrollPane(LoginController.getInstance().getTblListaViloes());
		containerLista.add(scrLista);

		esquerda = new JPanel();
		esquerda.setLayout(new GridLayout(8, 1, 5, 15));
		esquerda.setBounds(10, 214, 150, 350);
		
		btnCadastros = new JButton("Cadastros");
		esquerda.add(btnCadastros);
		
		btnPresidiarios = new JButton("Presidiários");
		esquerda.add(btnPresidiarios);

		btnHerois = new JButton("Heróis");
		esquerda.add(btnHerois);

		btnViloes = new JButton("Vilões");
		esquerda.add(btnViloes);
		
		esquerda.add(new JLabel());
		esquerda.add(new JLabel());

		btnLogout = new JButton("Logout");
		esquerda.add(btnLogout);

		imgGothamPD = new JLabel();
		gpdMajor = new ImageIcon(this.getClass().getResource(
				"/GothamMajorCrimes.jpg")).getImage();
		imgGothamPD.setIcon(new ImageIcon(gpdMajor));
		imgGothamPD.setBounds(0, 0, 989, 174);
		telaMenu.add(imgGothamPD);
		telaMenu.add(esquerda);
	}
	
	private String getData() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		DateFormat f = DateFormat.getDateInstance();
		return f.format(date);
	}
}
