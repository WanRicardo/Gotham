package br.com.inatel.ec206.view;

import java.awt.Color;
import java.awt.Container;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastroArma extends JDialog {
	private static final long serialVersionUID = 1L;
	private JLabel lblNome, lblPeso, lblAtq, lblDef, lblImgArma;
	private JButton btnAlterar, btnOk, btnSair;
	private JTextField txtNome, txtPeso, txtAtq, txtDef;
	private Container tela;
	
	public JLabel getLblImgArma() {
		return lblImgArma;
	}
	public JButton getBtnAlterar() {
		return btnAlterar;
	}
	public JButton getBtnOk() {
		return btnOk;
	}
	public JButton getBtnSair() {
		return btnSair;
	}
	public JTextField getTxtNome() {
		return txtNome;
	}
	public JTextField getTxtPeso() throws ParseException {
		if (txtPeso == null) {
			txtPeso = new JTextField();
			txtPeso.setBounds(190, 50, 110, 25);
		}
		return txtPeso;
	}
	public JTextField getTxtAtq() throws ParseException {
		if (txtAtq == null) {
			txtAtq = new JTextField();
			txtAtq.setBounds(175, 85, 35, 25);
		}
		return txtAtq;
	}
	public JTextField getTxtDef() throws ParseException {
		if (txtDef == null) {
			txtDef = new JTextField();
			txtDef.setBounds(265, 85, 35, 25);
		}
		return txtDef;
	}

	public TelaCadastroArma() throws ParseException {
		setTitle("Cadastro arma");
		setSize(315, 192);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal(true);
		
		tela = getContentPane();
		tela.setLayout(null);
		
		lblImgArma = new JLabel();
		lblImgArma.setBounds(15, 15, 100, 100);
		lblImgArma.setBorder(BorderFactory.createLineBorder(Color.black));
		tela.add(lblImgArma);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(15, 127, 100, 25);
		tela.add(btnAlterar);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(130, 15, 35, 25);
		tela.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(170, 15, 130, 25);
		tela.add(txtNome);
		
		lblPeso = new JLabel("Peso (kg)");
		lblPeso.setBounds(130, 50, 55, 25);
		tela.add(lblPeso);
		
		tela.add(getTxtPeso());
		
		lblAtq = new JLabel("Ataque");
		lblAtq.setBounds(130, 85, 40, 25);
		tela.add(lblAtq);
		
		tela.add(getTxtAtq());
		
		lblDef = new JLabel("Defesa");
		lblDef.setBounds(220, 85, 40, 25);
		tela.add(lblDef);
		
		tela.add(getTxtDef());
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(150, 127, 70, 25);
		tela.add(btnOk);
		
		btnSair = new JButton("Sair");
		btnSair.setBounds(230, 127, 70, 25);
		tela.add(btnSair);
	}
}
