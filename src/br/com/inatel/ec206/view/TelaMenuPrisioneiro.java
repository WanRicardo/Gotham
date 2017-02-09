package br.com.inatel.ec206.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.inatel.ec206.controller.MenuController;

public class TelaMenuPrisioneiro extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtProcurar;
	private JButton btnNovo;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnSair;
	private JScrollPane scrLista;
	private JLabel imgGothamGCPD;
	
	public JTextField getTxtProcurar() {
		return txtProcurar;
	}
	public JButton getBtnNovo() {
		return btnNovo;
	}
	public JButton getBtnEditar() {
		return btnEditar;
	}
	public JButton getBtnExcluir() {
		return btnExcluir;
	}
	public JButton getBtnSair() {
		return btnSair;
	}

	public TelaMenuPrisioneiro() {
		setTitle("GCPD - Gerenciar Cadastros de Prisioneiros");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 400);
		setVisible(true);
		setResizable(false);
		this.setLocationRelativeTo(null);

		Container telaCadastro = getContentPane();
		telaCadastro.setLayout(new BorderLayout(10, 5));

		Container centro = new JPanel();
		centro.setLayout(new GridLayout());
		scrLista = new JScrollPane(MenuController.getInstance().getTblListaPresidiario());
		centro.add(scrLista);

		Container cabecalho = new JPanel();
		cabecalho.setLayout(new FlowLayout());
		
		imgGothamGCPD = new JLabel();
		Image gcpd = new ImageIcon(this.getClass().getResource(
				"/GothamGCPD.jpg")).getImage();
		imgGothamGCPD.setIcon(new ImageIcon(gcpd));
		cabecalho.add(imgGothamGCPD);
		cabecalho.add(new JLabel("                                "));
		
		txtProcurar = new JTextField();
		txtProcurar.setFont(new Font("serif", Font.PLAIN, 18));
		txtProcurar.setColumns(20);
		cabecalho.add(txtProcurar);
		
		btnEditar = new JButton("Editar cadastro");
		cabecalho.add(btnEditar);
		cabecalho.add(new JLabel("                               "));

		Container rodape = new JPanel();
		rodape.setLayout(new FlowLayout());
		
		btnNovo = new JButton("Novo cadastro");
		rodape.add(btnNovo);
		
		btnExcluir = new JButton("Excluir cadastro");
		rodape.add(btnExcluir);
		
		btnSair = new JButton("Sair");
		rodape.add(btnSair);
		
		telaCadastro.add(BorderLayout.NORTH, cabecalho);
		telaCadastro.add(BorderLayout.CENTER, centro);
		telaCadastro.add(BorderLayout.SOUTH, rodape);
		telaCadastro.add(BorderLayout.EAST, new JLabel());
		telaCadastro.add(BorderLayout.WEST, new JLabel());
	}
}
