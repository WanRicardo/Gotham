package br.com.inatel.ec206.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaCadastroPrisioneiro extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtId, txtNome, txtStatus, txtEstado, txtPeso, txtAltura;
	private JButton btnFoto, btnOk, btnSair;
	private JLabel lblId, lblNome, lblPeso, lblAltura, lblStatus, lblEstado, lblFoto, lblHp, lblVida, lblPower, lblAtkDef;
	private JCheckBox chboxPrisioneiro;
	
	public JCheckBox getChboxPrisioneiro() {
		return chboxPrisioneiro;
	}

	public JLabel getLblFoto() {
		return lblFoto;
	}
	
	public JLabel getLblVida() {
		return lblVida;
	}

	public JLabel getLblAtkDef() {
		return lblAtkDef;
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

	public JTextField getTxtStatus() {
		return txtStatus;
	}

	public JTextField getTxtEstado() {
		return txtEstado;
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

	public TelaCadastroPrisioneiro() throws ParseException {
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
		
		lblHp = new JLabel("HP:");
		lblHp.setBounds(20, 205, 20, 25);
		janela.add(lblHp);
		
		lblVida = new JLabel();
		lblVida.setBounds(45, 205, 30, 25);
		janela.add(lblVida);
		
		lblPower = new JLabel("Power:");
		lblPower.setBounds(85, 205, 40, 25);
		janela.add(lblPower);
		
		lblAtkDef = new JLabel();
		lblAtkDef.setBounds(130, 205, 40, 25);
		janela.add(lblAtkDef);
		
		lblId = new JLabel("Id");
		lblId.setFont(fonteLabel);
		lblId.setBounds(194, 20, 15, 25);
		janela.add(lblId);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(214, 20, 50, 25);
		janela.add(txtId);
		
		lblStatus = new JLabel("Status");
		lblStatus.setFont(fonteLabel);
		lblStatus.setBounds(280, 20, 45, 25);
		janela.add(lblStatus);
		
		txtStatus = new JTextField();
		txtStatus.setBounds(325, 20, 119, 25);
		janela.add(txtStatus);
		
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
		
		lblEstado = new JLabel("Estado físico");
		lblEstado.setFont(fonteLabel);
		lblEstado.setBounds(194, 125, 80, 25);
		janela.add(lblEstado);
		
		txtEstado = new JTextField();
		txtEstado.setBounds(279, 125, 165, 25);
		janela.add(txtEstado);
		
		chboxPrisioneiro = new JCheckBox("Presidiario");
		chboxPrisioneiro.setBounds(194, 160, 100, 25);
		janela.add(chboxPrisioneiro);
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(220, 210, 100, 25);
		janela.add(btnOk);
		
		btnSair = new JButton("Sair");
		btnSair.setBounds(344, 210, 100, 25);
		janela.add(btnSair);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaCadastroPrisioneiro().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
