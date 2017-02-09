package br.com.inatel.ec206.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.inatel.ec206.model.dao.LoginDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TabelaBandido;
import br.com.inatel.ec206.view.TelaLogin;
import br.com.inatel.ec206.view.TelaProcurados;

public class LoginController implements ActionListener{
	private static LoginController INSTANCE;
	private LoginDAO loginDao = LoginDAO.getInstance();
	private TelaLogin telaLogin;
	private TelaProcurados telaProcurados;
	private JTable tableVilao;
	private TabelaBandido modelTableBandido;
	
	private LoginController() {}
	
	public static synchronized LoginController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LoginController();
		}
		return INSTANCE;
	}
	
	public TelaLogin newTelaLogin() {
		telaLogin = new TelaLogin();
		inicializaListeners();
		telaLogin.setVisible(true);
		return telaLogin;
	}
	
	public TelaProcurados newTelaProcurados() {
		telaProcurados = new TelaProcurados();
		telaProcurados.setVisible(true);
		return telaProcurados;
	}
	
	public int getIdUsuario() {
		return validarDados(telaLogin.getTxtUsuario().getText().trim(), telaLogin.getTxtSenha().getText().trim());
	}
	
	public void inicializaListeners() {		
		telaLogin.getBtnLogin().addActionListener(this);		
		telaLogin.getBtnListaProcurados().addActionListener(this);		
		telaLogin.getBtnSair().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaLogin.getBtnLogin()) {
			if (telaLogin.getTxtUsuario().getText().trim().equals("") || 
					telaLogin.getTxtSenha().getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null,
						"Os campos não podem estar em branco!",
						"BURRO",
						JOptionPane.CANCEL_OPTION);
			} else if ( getIdUsuario() == 0) {
				JOptionPane.showMessageDialog(null,
						"Senha incorreta ou usuário inválido!",
						"BURRO",
						JOptionPane.CANCEL_OPTION);
			} else {
				telaLogin.setVisible(false);
				MenuController.getInstance().newTelaMenu();
			}
		} else if (evento.getSource() == this.telaLogin.getBtnListaProcurados()) {
			newTelaProcurados();
		} else {
			System.exit(0);
		}
	}

	public JTable getTblListaViloes() {
		tableVilao = null;
		modelTableBandido = new TabelaBandido(getViloes());
		tableVilao = new JTable();
		tableVilao.setModel(modelTableBandido);
		configTableAlignment(modelTableBandido, tableVilao);
		return tableVilao;
	}
	public List<Vilao> getViloes() {
		List<Vilao> pegaViloes, listaViloes = VilaoDAO.getInstance().getTodosSoltos();
		pegaViloes = new ArrayList<>();
		for (Vilao vilao : listaViloes) {
			pegaViloes.add(vilao);
		}
		return pegaViloes;
	}
	private void configTableAlignment(TabelaBandido modeloLista, JTable tabela) {
		TableColumnModel columnModel = tabela.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i < modeloLista.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(centerRenderer);
		}
	}
	
	public int validarDados(String usuario, String senha) {		
		return loginDao.validarDados(usuario, senha);
	}
}
