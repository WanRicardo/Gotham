package br.com.inatel.ec206.controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.PoliciaDAO;
import br.com.inatel.ec206.model.entity.Policia;
import br.com.inatel.ec206.view.TelaMenuPolicia;

public class MenuPoliciaController implements ActionListener {
	private static MenuPoliciaController INSTANCE;
	private TelaMenuPolicia telaMenuPolicia;
	private PoliciaDAO policiaDao = PoliciaDAO.getInstance();
	
	private MenuPoliciaController() {}
	
	public static synchronized MenuPoliciaController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MenuPoliciaController();
		}
		return INSTANCE;
	}

	public TelaMenuPolicia newTelaMenuPolicia() {
		telaMenuPolicia = new TelaMenuPolicia();
		inicializaListeners();
		telaMenuPolicia.setVisible(true);
		return telaMenuPolicia;
	}
	public void atualizarTelaMenuPolicia() {
		telaMenuPolicia.dispose();
		newTelaMenuPolicia();
	}
	
	private void inicializaListeners() {
		telaMenuPolicia.getBtnEditar().addActionListener(this);
		telaMenuPolicia.getBtnExcluir().addActionListener(this);
		telaMenuPolicia.getBtnNovo().addActionListener(this);
		telaMenuPolicia.getBtnSair().addActionListener(this);		
		if (verificaAdmin(LoginController.getInstance().getIdUsuario()) == 0) {
			telaMenuPolicia.getBtnEditar().setEnabled(false);
			telaMenuPolicia.getBtnExcluir().setEnabled(false);
			telaMenuPolicia.getBtnNovo().setEnabled(false);
		}
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == this.telaMenuPolicia.getBtnEditar()) {
			try {
				editarPolicia();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == this.telaMenuPolicia.getBtnExcluir()) {
			excluirPolicia(evento);
		} else if (evento.getSource() == this.telaMenuPolicia.getBtnNovo()) {
			try {
				CadastroPoliciaController.getInstance().newTelaCadastroPolicia();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == this.telaMenuPolicia.getBtnSair()) {
			telaMenuPolicia.dispose();
		}
	}

	private void excluirPolicia(ActionEvent evento) {
		try {
			MenuController.getInstance().getIdLinhaSelecionadaPolicia();
			if (MenuController.getInstance().getIdLinhaSelecionadaPolicia() != 1) {
				if (JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento) == 0) {
					if (MenuController.getInstance().getIdLinhaSelecionadaPolicia() == LoginController.getInstance().getIdUsuario()) {
						if (JOptionPane.showOptionDialog(null, "O cadastro prestes a ser excluído é o mesmo que iniciou esta sessão,"
															+ "\n   se confirmado você será deslogado e a sessão será reiniciada."
															+ "\n             Tem certeza que deseja prosseguir com a ação?", "ATENÇÃO!", 0, 2, null, null, evento) == 0) {
							excluirPoliciaPeloId(MenuController.getInstance().getIdLinhaSelecionadaPolicia());
							JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
							Frame[] frame = Frame.getFrames();
							for (Frame frame2 : frame) {
								frame2.dispose();
							}
							LoginController.getInstance().newTelaLogin();
						}
					} else {
						excluirPoliciaPeloId(MenuController.getInstance().getIdLinhaSelecionadaPolicia());
						JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
						atualizarTelaMenuPolicia();
					}
				}
			} else JOptionPane.showMessageDialog(null, "Este cadastro não pode ser excluído pois"
													+ "\n é o administrador principal do programa!", "OPERAÇÃO NEGADA", 0);
		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "  PRIMEIRO SELECIONE UM"
					+ "							\nCADASTRO PARA REMOVÊ-LO!!!", "SEU BURRO", 0);
		}
	}

	private void editarPolicia() throws ParseException {
		if (!telaMenuPolicia.getTxtProcurar().getText().trim().equals("")) {
			String policial = telaMenuPolicia.getTxtProcurar().getText();
			List<Policia> police = getPeloNome(policial);
			for (Policia policia : police) {
				CadastroPoliciaController.getInstance().editarCadastroPolicia(policia.getId(), policia.getIdAdmin(), policia.getNome(),
						policia.getPeso(), policia.getAltura(), policia.getFotoPolicia(), policia.getCargo(), policia.getUsuario(), policia.getSenha());
			}
		} else
			JOptionPane.showMessageDialog(null, "Entre com um nome válido");
	}
	
	private List<Policia> getPeloNome(String nomePolicia) {
		return policiaDao.getPeloNome(nomePolicia);
	}
	private boolean excluirPoliciaPeloId(int idPolicia) {
		return policiaDao.removerPoliciaPeloId(idPolicia);
	}
	private int verificaAdmin(int idPolicia) {
		return policiaDao.verificaAdmin(idPolicia);
	}
}
