package br.com.inatel.ec206.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaCombate extends JFrame{
	private static final long serialVersionUID = 1L;
	private JComboBox jcbHeroi, jcbVilao;
	private JLabel lblImgVilao, lblImgHeroi, lblAtkDefHeroi, lblAtkDefVilao, lblVidaHeroi, lblVidaVilao;
	private JProgressBar barraVidaHeroi, barraVidaVilao;
	private JButton btnLutar;
	private JTextArea logDeCombate;
	
	public JLabel getLblAtkDefHeroi() {
		if (lblAtkDefHeroi == null) {
			lblAtkDefHeroi = new JLabel();
			lblAtkDefHeroi.setFont(new Font("serif", Font.BOLD, 20));
			lblAtkDefHeroi.setBounds(120, 295, 150, 30);
		}
		return lblAtkDefHeroi;
	}
	
	public JLabel getLblAtkDefVilao() {
		if (lblAtkDefVilao == null) {
			lblAtkDefVilao = new JLabel();
			lblAtkDefVilao.setFont(new Font("serif", Font.BOLD, 20));
			lblAtkDefVilao.setBounds(650, 295, 150, 30);
		}
		return lblAtkDefVilao;
	}
	
	public JLabel getLblVidaHeroi() {
		if (lblVidaHeroi == null) {
			lblVidaHeroi = new JLabel();
			lblVidaHeroi.setFont(new Font("serif", Font.BOLD, 20));
			lblVidaHeroi.setBounds(90, 270, 100, 30);
		}
		return lblVidaHeroi;
	}
	
	public JLabel getLblVidaVilao() {
		if (lblVidaVilao == null) {
			lblVidaVilao = new JLabel();
			lblVidaVilao.setFont(new Font("serif", Font.BOLD, 20));
			lblVidaVilao.setBounds(620, 270, 100, 30);
		}
		return lblVidaVilao;
	}
	
	public JTextArea getLogDeCombate() {
		if (logDeCombate == null) {
			logDeCombate = new JTextArea();
			logDeCombate.setEditable(false);
//			logDeCombate.setBounds(150, 380, 500, 150);
		}
		return logDeCombate;
	}
	public JButton getBtnLutar() {
		if (btnLutar == null) {
			btnLutar = new JButton("LUTAR!");
			btnLutar.setBounds(360, 300, 80, 40);
		}
		return btnLutar;
	}
	
	public JProgressBar getBarraVidaHeroi() {
		if (barraVidaHeroi == null) {
			barraVidaHeroi = new JProgressBar();
			barraVidaHeroi.setMinimum(0);
			barraVidaHeroi.setMaximum(100);
			barraVidaHeroi.setValue(100);
			barraVidaHeroi.setBounds(50, 250, 150, 20);
		}
		return barraVidaHeroi;
	}
	public void atualizaVidaHeroi(int vidaAtual) {
		barraVidaHeroi.setValue(vidaAtual);
	}
	
	public JComboBox getJcbHeroi() {
		if (jcbHeroi == null) {
			jcbHeroi = new JComboBox();
			jcbHeroi.setMaximumRowCount(3);
			jcbHeroi.setBounds(50, 60, 150, 25);
		}
		return jcbHeroi;
	}
	public JLabel getLblImgHeroi() {
		if (lblImgHeroi == null) {
			lblImgHeroi = new JLabel();
			lblImgHeroi.setBorder(BorderFactory.createLineBorder(Color.black));
			lblImgHeroi.setBounds(50, 95, 150, 150);
		}
		return lblImgHeroi;
	}
	public JProgressBar getBarraVidaVilao() {
		if (barraVidaVilao == null) {
			barraVidaVilao = new JProgressBar();
			barraVidaVilao.setMinimum(0);
			barraVidaVilao.setMaximum(100);
			barraVidaVilao.setValue(100);
			barraVidaVilao.setBounds(580, 250, 150, 20);
		}
		return barraVidaVilao;
	}
	public void atualizaVidaVilao(int vidaAtual) {
		barraVidaVilao.setValue(vidaAtual);
	}
	
	public JComboBox getJcbVilao() {
		if (jcbVilao == null) {
			jcbVilao = new JComboBox();
			jcbVilao.setMaximumRowCount(3);
			jcbVilao.setBounds(580, 60, 150, 25);
		}
		return jcbVilao;
	}
	public JLabel getLblImgVilao() {
		if (lblImgVilao == null) {
			lblImgVilao = new JLabel();
			lblImgVilao.setBorder(BorderFactory.createLineBorder(Color.black));
			lblImgVilao.setBounds(580, 95, 150, 150);
		}
		return lblImgVilao;
	}
	
	public TelaCombate() {
		setTitle("Duelo Monxtro");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		Container tela = getContentPane();
		tela.setLayout(null);
		
		JLabel versus = new JLabel("Vs");
		versus.setFont(new Font("serif", Font.ITALIC, 50));
		versus.setBounds(375, 30, 50, 50);
		
		JLabel hpHeroi = new JLabel("HP:");
		hpHeroi.setFont(new Font("serif", Font.BOLD, 20));
		hpHeroi.setBounds(50, 275, 70, 20);
		
		JLabel poderHeroi = new JLabel("Power: ");
		poderHeroi.setFont(new Font("serif", Font.BOLD, 20));
		poderHeroi.setBounds(50, 295, 70, 30);
		
		JLabel hpVilao = new JLabel("HP:");
		hpVilao.setFont(new Font("serif", Font.BOLD, 20));
		hpVilao.setBounds(580, 275, 40, 20);

		JLabel poderVilao = new JLabel("Power:");
		poderVilao.setFont(new Font("serif", Font.BOLD, 20));
		poderVilao.setBounds(580, 295, 70, 30);
		
		JLabel lblCombate = new JLabel("Log de combate");
		lblCombate.setBounds(150, 370, 100, 20);
		
		JScrollPane scrLogDeCombate = new JScrollPane(getLogDeCombate());
		scrLogDeCombate.setBounds(150, 390, 500, 150);
		
		tela.add(getJcbHeroi());
		tela.add(getJcbVilao());
		tela.add(getLblImgHeroi());
		tela.add(getLblImgVilao());
		tela.add(getBarraVidaHeroi());
		tela.add(getBarraVidaVilao());
		tela.add(versus);
		tela.add(hpHeroi);
		tela.add(hpVilao);
		tela.add(poderHeroi);
		tela.add(poderVilao);
		tela.add(getBtnLutar());
		tela.add(scrLogDeCombate);
		tela.add(getLblAtkDefHeroi());
		tela.add(getLblVidaHeroi());
		tela.add(getLblAtkDefVilao());
		tela.add(getLblVidaVilao());
		tela.add(lblCombate);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaCombate().setVisible(true);
//					EquiparArmaController.getInstance().newTelaEquiparArma();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
