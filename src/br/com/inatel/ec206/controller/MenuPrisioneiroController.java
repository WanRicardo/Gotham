package br.com.inatel.ec206.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaMenuPrisioneiro;

public class MenuPrisioneiroController implements ActionListener {

	private static MenuPrisioneiroController INSTANCE;
	private TelaMenuPrisioneiro telaMenuPrisioneiro;
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	
	private MenuPrisioneiroController() {}
	
	public static synchronized MenuPrisioneiroController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MenuPrisioneiroController();
		}
		return INSTANCE;
	}
	
	public TelaMenuPrisioneiro newTelaMenuPrisioneiro() {
		telaMenuPrisioneiro = new TelaMenuPrisioneiro();
		inicializaListeners();
		telaMenuPrisioneiro.setVisible(true);
		return telaMenuPrisioneiro;
	}
	
	public void atualizarTelaMenuPrisioneiro() {
		telaMenuPrisioneiro.dispose();
		newTelaMenuPrisioneiro();
	}
	
	private void inicializaListeners() {
		telaMenuPrisioneiro.getBtnNovo().addActionListener(this);
		telaMenuPrisioneiro.getBtnEditar().addActionListener(this);
		telaMenuPrisioneiro.getBtnExcluir().addActionListener(this);
		telaMenuPrisioneiro.getBtnSair().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaMenuPrisioneiro.getBtnNovo()) {
			try {
				CadastroPrisioneiroController.getInstance().newTelaCadastroPrisioneiro();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenuPrisioneiro.getBtnEditar()) {
			try {
				editarPrisioneiro();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenuPrisioneiro.getBtnExcluir()) {
			excluirPrisioneiro(evento);
		} else if (evento.getSource() == telaMenuPrisioneiro.getBtnSair()) {
			telaMenuPrisioneiro.dispose();
		}
	}

	private void excluirPrisioneiro(ActionEvent evento) {
		if (!telaMenuPrisioneiro.getTxtProcurar().getText().trim().equals("")) {
			int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento);
			if (opcao == 0) {
				if (excluirPrisioneiroPeloNome(telaMenuPrisioneiro.getTxtProcurar().getText().trim())) {
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuPrisioneiro();
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível excluir \"" + telaMenuPrisioneiro.getTxtProcurar().getText().trim() + "\",\n\nCadastro inválido ou inexistente!", "Erro!", 0);
				}
			}
		} else {
			try {
				MenuController.getInstance().getNomeLinhaSelecionadaPrisioneiro();
				if (JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento) == 0) {
					excluirPrisioneiroPeloId(MenuController.getInstance().getIdLinhaSelecionadaPrisioneiro());
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuPrisioneiro();
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "  DIGITE UM NOME OU SELECIONE\nA MERDA DE UM CARA PRIMEIRO!!!", "SEU BURRO", 0);
			}
		} 
	}

	private void editarPrisioneiro() throws ParseException {
		String prisioneiro = telaMenuPrisioneiro.getTxtProcurar().getText().trim();
		if (!prisioneiro.equals("")) {
				List<Vilao> foe = getPrisioneiroPeloNome(prisioneiro);
				for (Vilao enemy : foe) {
					CadastroPrisioneiroController.getInstance().editarCadastroPrisioneiro(enemy.getId(), enemy.getNome(), enemy.getPeso(),
							enemy.getAltura(), enemy.getFotoVilao(), enemy.getAtaque(), enemy.getDefesa(), enemy.getVida(), enemy.getStatus(), enemy.getEstadoFisico());
				}
		} else
			JOptionPane.showMessageDialog(null, "Entre com um nome válido");
	}
	
	private List<Vilao> getPrisioneiroPeloNome(String nomePrisioneiro) {
		return vilaoDao.getPeloNomePrisioneiro(nomePrisioneiro);
	}
	private boolean excluirPrisioneiroPeloNome(String nomePrisioneiro) {
		return vilaoDao.removerPrisioneiro(nomePrisioneiro);
	}
	private boolean excluirPrisioneiroPeloId(int idPrisioneiro) {
		return vilaoDao.removerPrisioneiroPeloId(idPrisioneiro);
	}
}
