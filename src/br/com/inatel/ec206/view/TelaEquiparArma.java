package br.com.inatel.ec206.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import br.com.inatel.ec206.controller.MenuController;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.model.entity.Vilao;

public class TelaEquiparArma extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTabbedPane jtpTela;
	private JPanel paneHero, paneFoe;
	private JLabel lblImgHeroi, lblArma1Hero, lblArma2Hero, lblImgVilao, lblArma1Vilao, lblArma2Vilao, lblDefHeroi, lblAtkHeroi, lblDefVilao, lblAtkVilao;
	private JComboBox<Heroi> jcbHeroi;
	private JComboBox<Vilao> jcbVilao;
	private JScrollPane scrTableArma;
	private JButton btnAplicarHeroi, btnOkHeroi, btnSairHeroi, btnAplicarVilao, btnOkVilao, btnSairVilao, btnAddArmaHeroi, btnAddArmaVilao;
	private JRadioButton jrbArma1Hero, jrbArma2Hero, jrbArma1Vilao, jrbArma2Vilao;
	private JTable tableArmaHeroi, tableArmaVilao;
	
	public JLabel getLblDefHeroi() {
		if (lblDefHeroi == null) {
			lblDefHeroi = new JLabel();
			lblDefHeroi.setFont(new Font("serif", Font.PLAIN, 24));
			lblDefHeroi.setBounds(285, 155, 100, 25);
		}
		return lblDefHeroi;
	}
	public JLabel getLblAtkHeroi() {
		if (lblAtkHeroi == null) {
			lblAtkHeroi = new JLabel();
			lblAtkHeroi.setFont(new Font("serif", Font.PLAIN, 24));
			lblAtkHeroi.setBounds(285, 105, 100, 25);
		}
		return lblAtkHeroi;
	}

	public JLabel getLblArma1Hero() {
		if (lblArma1Hero == null) {
			lblArma1Hero = new JLabel();
			lblArma1Hero.setBorder(BorderFactory.createLineBorder(Color.black));
			lblArma1Hero.setBounds(35, 255, 100, 100);
		}
		return lblArma1Hero;
	}
	public JLabel getLblArma2Hero() {
		if (lblArma2Hero == null) {
			lblArma2Hero = new JLabel();
			lblArma2Hero.setBorder(BorderFactory.createLineBorder(Color.black));
			lblArma2Hero.setBounds(265, 255, 100, 100);
		}
		return lblArma2Hero;
	}
	public JLabel getLblImgHeroi() {
		if (lblImgHeroi == null) {
			lblImgHeroi = new JLabel();
			lblImgHeroi.setBorder(BorderFactory.createLineBorder(Color.black));
			lblImgHeroi.setBounds(125, 55, 150, 150);
		}
		return lblImgHeroi;
	}
	public JTable getTableArmaHeroi() {
		tableArmaHeroi = MenuController.getInstance().getTblListaArmaHeroi();
		return tableArmaHeroi;
	}
	public JRadioButton getJrbArma1Hero() {
		if (jrbArma1Hero == null) {
			jrbArma1Hero = new JRadioButton("Arma 1");
			jrbArma1Hero.setBounds(50, 220, 70, 30);
		}
		return jrbArma1Hero;
	}
	public JRadioButton getJrbArma2Hero() {
		if (jrbArma2Hero == null) {
			jrbArma2Hero = new JRadioButton("Arma 2");
			jrbArma2Hero.setBounds(280, 220, 70, 30);
		}
		return jrbArma2Hero;
	}
	public JButton getBtnAddArmaHeroi() {
		if (btnAddArmaHeroi == null) {
			btnAddArmaHeroi = new JButton("Equipar Arma");
			btnAddArmaHeroi.setBounds(135, 510, 130, 30);
		}
		return btnAddArmaHeroi;
	}

	public JButton getBtnAplicarHeroi() {
		if (btnAplicarHeroi == null) {
			btnAplicarHeroi = new JButton("Del Arma");
			btnAplicarHeroi.setBounds(25, 560, 100, 30);
		}
		return btnAplicarHeroi;
	}
	public JButton getBtnOkHeroi() {
		if (btnOkHeroi == null) {
			btnOkHeroi = new JButton("Ok");
			btnOkHeroi.setBounds(150, 560, 100, 30);
		}
		return btnOkHeroi;
	}
	public JButton getBtnSairHeroi() {
		if (btnSairHeroi == null) {
			btnSairHeroi = new JButton("Sair");
			btnSairHeroi.setBounds(275, 560, 100, 30);
		}
		return btnSairHeroi;
	}
	public JComboBox getJcbHeroi() {
		if (jcbHeroi == null) {
			jcbHeroi = new JComboBox();
			jcbHeroi.setMaximumRowCount(3);
			jcbHeroi.setBounds(125, 20, 150, 25);
		}
		return jcbHeroi;
	}
	public JPanel getPaneHero() {
		if (paneHero == null) {
			paneHero = new JPanel();
			paneHero.setLayout(null);
			paneHero.add(getJcbHeroi());
			paneHero.add(getLblImgHeroi());
			
			JLabel hp = new JLabel("HP");
			hp.setFont(new Font("serif", Font.BOLD, 20));
			hp.setBounds(45, 70, 30, 15);
			
			JLabel vida = new JLabel("100");
			vida.setFont(new Font("serif", Font.PLAIN, 24));
			vida.setBounds(38, 90, 40, 25);
			
			JLabel ataque = new JLabel("ATK");
			ataque.setFont(new Font("serif", Font.BOLD, 20));
			ataque.setBounds(290, 80, 50, 25);
			
			JLabel defesa = new JLabel("DEF");
			defesa.setFont(new Font("serif", Font.BOLD, 20));
			defesa.setBounds(290, 130, 50, 25);
			
			ButtonGroup grupoRadio = new ButtonGroup();
			grupoRadio.add(getJrbArma1Hero());
			grupoRadio.add(getJrbArma2Hero());
			getJrbArma1Hero().setSelected(true);
			
			scrTableArma = new JScrollPane(getTableArmaHeroi());
			scrTableArma.setBounds(50, 395, 300, 105);
			
			paneHero.add(getJrbArma1Hero());
			paneHero.add(getJrbArma2Hero());
			paneHero.add(getLblArma1Hero());
			paneHero.add(getLblArma2Hero());
			paneHero.add(scrTableArma);
			paneHero.add(getBtnAddArmaHeroi());
			paneHero.add(getBtnAplicarHeroi());
			paneHero.add(getBtnOkHeroi());
			paneHero.add(getBtnSairHeroi());
			paneHero.add(hp);
			paneHero.add(vida);
			paneHero.add(ataque);
			paneHero.add(defesa);
			paneHero.add(getLblAtkHeroi());
			paneHero.add(getLblDefHeroi());
		}
		return paneHero;
	}
	
	
	
	
	public JLabel getLblDefVilao() {
		if (lblDefVilao == null) {
			lblDefVilao = new JLabel();
			lblDefVilao.setFont(new Font("serif", Font.PLAIN, 24));
			lblDefVilao.setBounds(285, 155, 100, 25);
		}
		return lblDefVilao;
	}

	public JLabel getLblAtkVilao() {
		if (lblAtkVilao == null) {
			lblAtkVilao = new JLabel();
			lblAtkVilao.setFont(new Font("serif", Font.PLAIN, 24));
			lblAtkVilao.setBounds(285, 105, 100, 25);
		}
		return lblAtkVilao;
	}

	public JLabel getLblArma1Vilao() {
		if (lblArma1Vilao == null) {
			lblArma1Vilao = new JLabel();
			lblArma1Vilao.setBorder(BorderFactory.createLineBorder(Color.black));
			lblArma1Vilao.setBounds(35, 255, 100, 100);
		}
		return lblArma1Vilao;
	}
	public JLabel getLblArma2Vilao() {
		if (lblArma2Vilao == null) {
			lblArma2Vilao = new JLabel();
			lblArma2Vilao.setBorder(BorderFactory.createLineBorder(Color.black));
			lblArma2Vilao.setBounds(265, 255, 100, 100);
		}
		return lblArma2Vilao;
	}
	public JLabel getLblImgVilao() {
		if (lblImgVilao == null) {
			lblImgVilao = new JLabel();
			lblImgVilao.setBorder(BorderFactory.createLineBorder(Color.black));
			lblImgVilao.setBounds(125, 55, 150, 150);
		}
		return lblImgVilao;
	}
	public JTable getTableArmaVilao() {
		tableArmaVilao = MenuController.getInstance().getTblListaArmaVilao();
		return tableArmaVilao;
	}
	public JRadioButton getJrbArma1Vilao() {
		if (jrbArma1Vilao == null) {
			jrbArma1Vilao = new JRadioButton("Arma 1");
			jrbArma1Vilao.setBounds(50, 220, 70, 30);
		}
		return jrbArma1Vilao;
	}
	public JRadioButton getJrbArma2Vilao() {
		if (jrbArma2Vilao == null) {
			jrbArma2Vilao = new JRadioButton("Arma 2");
			jrbArma2Vilao.setBounds(280, 220, 70, 30);
		}
		return jrbArma2Vilao;
	}
	public JButton getBtnAddArmaVilao() {
		if (btnAddArmaVilao == null) {
			btnAddArmaVilao = new JButton("Equipar Arma");
			btnAddArmaVilao.setBounds(135, 510, 130, 30);
		}
		return btnAddArmaVilao;
	}
	public JButton getBtnAplicarVilao() {
		if (btnAplicarVilao == null) {
			btnAplicarVilao = new JButton("Del Arma");
			btnAplicarVilao.setBounds(25, 560, 100, 30);
		}
		return btnAplicarVilao;
	}
	public JButton getBtnOkVilao() {
		if (btnOkVilao == null) {
			btnOkVilao = new JButton("Ok");
			btnOkVilao.setBounds(150, 560, 100, 30);
		}
		return btnOkVilao;
	}
	public JButton getBtnSairVilao() {
		if (btnSairVilao == null) {
			btnSairVilao = new JButton("Sair");
			btnSairVilao.setBounds(275, 560, 100, 30);
		}
		return btnSairVilao;
	}
	public JComboBox getJcbVilao() {
		if (jcbVilao == null) {
			jcbVilao = new JComboBox();
			jcbVilao.setMaximumRowCount(3);
			jcbVilao.setBounds(125, 20, 150, 25);
		}
		return jcbVilao;
	}
	public JPanel getPaneFoe() {
		if (paneFoe == null) {
			paneFoe = new JPanel();
			paneFoe.setLayout(null);
			paneFoe.add(getJcbVilao());
			paneFoe.add(getLblImgVilao());
			
			JLabel hp = new JLabel("HP");
			hp.setFont(new Font("serif", Font.BOLD, 20));
			hp.setBounds(45, 70, 30, 15);
			
			JLabel vida = new JLabel("100");
			vida.setFont(new Font("serif", Font.PLAIN, 24));
			vida.setBounds(38, 90, 40, 25);
			
			JLabel ataque = new JLabel("ATK");
			ataque.setFont(new Font("serif", Font.BOLD, 20));
			ataque.setBounds(290, 80, 50, 25);
			
			JLabel defesa = new JLabel("DEF");
			defesa.setFont(new Font("serif", Font.BOLD, 20));
			defesa.setBounds(290, 130, 50, 25);
			
			ButtonGroup grupoRadio = new ButtonGroup();
			grupoRadio.add(getJrbArma1Vilao());
			grupoRadio.add(getJrbArma2Vilao());
			getJrbArma1Vilao().setSelected(true);
			
			scrTableArma = new JScrollPane(getTableArmaVilao());
			scrTableArma.setBounds(50, 395, 300, 105);
			
			paneFoe.add(getJrbArma1Vilao());
			paneFoe.add(getJrbArma2Vilao());
			paneFoe.add(getLblArma1Vilao());
			paneFoe.add(getLblArma2Vilao());
			paneFoe.add(scrTableArma);
			paneFoe.add(getBtnAddArmaVilao());
			paneFoe.add(getBtnAplicarVilao());
			paneFoe.add(getBtnOkVilao());
			paneFoe.add(getBtnSairVilao());
			paneFoe.add(hp);
			paneFoe.add(vida);
			paneFoe.add(ataque);
			paneFoe.add(getLblAtkVilao());
			paneFoe.add(defesa);
			paneFoe.add(getLblDefVilao());
		}
		return paneFoe;
	}

	public JTabbedPane getJtpTela() {
		if (jtpTela == null) {
			jtpTela = new JTabbedPane();
		}
		return jtpTela;
	}
	
	public TelaEquiparArma() {
		setTitle("Equipar Armas");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(410, 660);
		setLocationRelativeTo(null);
		
		getContentPane().add(getJtpTela());
		getJtpTela().addTab("Armamento Herói", getPaneHero());
		getJtpTela().addTab("Armamento Vilão", getPaneFoe());
	}
}
