package br.com.inatel.ec206.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.inatel.ec206.controller.LoginController;

public class TelaProcurados extends JDialog{
	private static final long serialVersionUID = 1L;
	private JScrollPane scrLista;
	private JLabel tituloProcurados;
	
	public TelaProcurados() {
		setTitle("PROCURADOS E FORAGIDOS");
		setResizable(false);
		setSize(700, 300);
		setLocationRelativeTo(null);
		setModal(true);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Container telaProcurados = getContentPane();
		telaProcurados.setLayout(new BorderLayout(10, 5));
		
		Container centro = new JPanel();
		centro.setLayout(new GridLayout());
		scrLista = new JScrollPane(LoginController.getInstance().getTblListaViloes());
		centro.add(scrLista);
		
		tituloProcurados = new JLabel("  Lista dos procurados e foragidos mais perigosos de Gotham");
		tituloProcurados.setFont(new Font("serif", Font.BOLD, 22));
		
		telaProcurados.add(BorderLayout.NORTH, tituloProcurados);
		telaProcurados.add(BorderLayout.CENTER, centro);
		telaProcurados.add(BorderLayout.EAST, new JLabel());
		telaProcurados.add(BorderLayout.WEST, new JLabel());
		telaProcurados.add(BorderLayout.SOUTH, new JLabel());
	}
}
