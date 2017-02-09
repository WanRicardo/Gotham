package br.com.inatel.ec206.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TelaLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JButton btnLogin;
	private JLabel lblUser;
	private JLabel lblSenha;
	private JLabel lblIcone;
	private Image dpGotham;
	private JButton btnListaProcurados;
	private JButton btnSair;

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}
	public JTextField getTxtSenha() {
		return txtSenha;
	}
	public JButton getBtnLogin() {
		return btnLogin;
	}
	public JButton getBtnListaProcurados() {
		return btnListaProcurados;
	}
	public JButton getBtnSair() {
		return btnSair;
	}

	public TelaLogin() {
		setTitle("GCPD  LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420, 367);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(129, 216, 184, 20);
		txtUsuario.setColumns(10);
		txtUsuario.setToolTipText("Ex.: bátima");
		contentPane.add(txtUsuario);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(129, 257, 184, 20);
		txtSenha.setColumns(10);
		txtSenha.setToolTipText("Ex.: *****");
		contentPane.add(txtSenha);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(32, 288, 72, 23);
		contentPane.add(btnLogin);

		lblUser = new JLabel("Usu\u00E1rio");
		lblUser.setBounds(32, 219, 46, 14);
		contentPane.add(lblUser);

		lblSenha = new JLabel("Senha");
		lblSenha.setBounds(32, 260, 46, 14);
		contentPane.add(lblSenha);

		btnSair = new JButton("Sair");
		btnSair.setBounds(127, 288, 79, 23);
		contentPane.add(btnSair);
		
		btnListaProcurados = new JButton("Lista Procurados");
		btnListaProcurados.setBounds(221, 288, 150, 23);
		contentPane.add(btnListaProcurados);
		 
		lblIcone = new JLabel();
		dpGotham = new ImageIcon(this.getClass().getResource("/GPD.jpg")).getImage();
		lblIcone.setIcon(new ImageIcon(dpGotham));
		lblIcone.setBounds(101, 11, 224, 194);
		contentPane.add(lblIcone);
	}
}
