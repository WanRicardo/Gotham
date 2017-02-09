package br.com.inatel.ec206.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaRelatorioPolicial extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTextArea txtAreaRelatorio;
	private JButton btnFechar;
	private JScrollPane scrTxtArea;
	
	public JTextArea getTxtAreaRelatorio() {
		return txtAreaRelatorio;
	}
	public JButton getBtnFechar() {
		return btnFechar;
	}
	
	public TelaRelatorioPolicial() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setModal(true);
		setLocationRelativeTo(null);
		
		Container janela = getContentPane();
		janela.setLayout(new BorderLayout(10, 10));
		
		Container containerBotao = new JPanel();
		txtAreaRelatorio = new JTextArea();
		txtAreaRelatorio.setEditable(false);
		scrTxtArea = new JScrollPane(txtAreaRelatorio);
		
		btnFechar = new JButton("Fechar");
		containerBotao.add(btnFechar);
		janela.add(BorderLayout.CENTER, scrTxtArea);
		janela.add(BorderLayout.EAST, new JLabel());
		janela.add(BorderLayout.WEST, new JLabel());
		janela.add(BorderLayout.SOUTH, containerBotao);
		janela.add(BorderLayout.NORTH, new JLabel());
	}
}
