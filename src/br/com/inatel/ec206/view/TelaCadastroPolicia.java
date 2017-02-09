package br.com.inatel.ec206.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastroPolicia extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtId, txtNome, txtCargo, txtUsuario, txtSenha, txtPeso, txtAltura;
	private JButton btnFoto, btnOk, btnSair;
	private JLabel lblId, lblNome, lblPeso, lblAltura, lblCargo, lblUsuario, lblSenha, lblFoto;
	private JCheckBox isAdm;
	
	public JCheckBox getIsAdm() {
		return isAdm;
	}
	public JLabel getLblFoto() {
		return lblFoto;
	}
	public JTextField getTxtId() {
		return txtId;
	}
	public JTextField getTxtNome() {
		return txtNome;
	}
	public JTextField getTxtPeso() throws ParseException {
		if (txtPeso == null) {
			txtPeso = new JFormattedTextField();
			txtPeso.setBounds(259, 90, 50, 25);
		}
		return txtPeso;
	}

	public JTextField getTxtAltura() throws ParseException {
		if (txtAltura == null) {
			txtAltura = new JFormattedTextField();
			txtAltura.setBounds(394, 90, 50, 25);
		}
		return txtAltura;
	}

	public JTextField getTxtCargo() {
		return txtCargo;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JTextField getTxtSenha() {
		return txtSenha;
	}

	public JButton getBtnFoto() {
		return btnFoto;
	}

	public JButton getBtnOk() {
		return btnOk;
	}

	public JButton getBtnSair() {
		return btnSair;
	}

	public TelaCadastroPolicia() throws ParseException {
		setTitle("Cadastros GCPD");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setSize(470, 280);
		setLocationRelativeTo(null);
		
		Font fonteLabel = new Font("serif", Font.PLAIN, 16);
		
		Container janela = getContentPane();
		janela.setLayout(null);
		
		lblFoto = new JLabel();
		lblFoto.setBorder(BorderFactory.createLineBorder(Color.black));
//		Image desconhecido = new ImageIcon(this.getClass().getResource("/desconhecido.jpg")).getImage();
//		lblFoto.setIcon(new ImageIcon(desconhecido));
		lblFoto.setBounds(20, 20, 154, 140);
		janela.add(lblFoto);
		
		btnFoto = new JButton("Alterar");
		btnFoto.setBounds(20, 170, 154, 25);
		janela.add(btnFoto);
		
		isAdm = new JCheckBox("Administrador");
		isAdm.setBounds(20, 210, 154, 25);
		janela.add(isAdm);
		
		lblId = new JLabel("Id");
		lblId.setFont(fonteLabel);
		lblId.setBounds(194, 20, 15, 25);
		janela.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(214, 20, 50, 25);
		janela.add(txtId);
		
		lblCargo = new JLabel("Cargo");
		lblCargo.setFont(fonteLabel);
		lblCargo.setBounds(280, 20, 45, 25);
		janela.add(lblCargo);
		
		txtCargo = new JTextField();
		txtCargo.setBounds(325, 20, 119, 25);
		janela.add(txtCargo);
		
		lblNome = new JLabel("Nome");
		lblNome.setFont(fonteLabel);
		lblNome.setBounds(194, 55, 40, 25);
		janela.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(239, 55, 205, 25);
		janela.add(txtNome);
		
		lblPeso = new JLabel("Peso (kg)");
		lblPeso.setFont(fonteLabel);
		lblPeso.setBounds(194, 90, 60, 25);
		janela.add(lblPeso);
		
		janela.add(getTxtPeso());
		
		lblAltura = new JLabel("Altura (cm)");
		lblAltura.setFont(fonteLabel);
		lblAltura.setBounds(319, 90, 70, 25);
		janela.add(lblAltura);
		
		janela.add(getTxtAltura());
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(fonteLabel);
		lblUsuario.setBounds(194, 125, 50, 25);
		janela.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(249, 125, 195, 25);
		janela.add(txtUsuario);
		
		lblSenha = new JLabel("Senha");
		lblSenha.setFont(fonteLabel);
		lblSenha.setBounds(194, 160, 50, 25);
		janela.add(lblSenha);
		
		txtSenha = new JTextField();
		txtSenha.setBounds(249, 160, 195, 25);
		janela.add(txtSenha);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(220, 210, 100, 25);
		janela.add(btnOk);
		
		btnSair = new JButton("Sair");
		btnSair.setBounds(344, 210, 100, 25);
		janela.add(btnSair);
	}
}
