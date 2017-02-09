package br.com.inatel.ec206.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaMenuVilao;

public class MenuVilaoController implements ActionListener {

	private static MenuVilaoController INSTANCE;
	private TelaMenuVilao telaMenuVilao;
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	
	private MenuVilaoController() {}
	
	public static synchronized MenuVilaoController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MenuVilaoController();
		}
		return INSTANCE;
	}
	
	public TelaMenuVilao newTelaMenuVilao() {
		telaMenuVilao = new TelaMenuVilao();
		inicializaListeners();
		telaMenuVilao.setVisible(true);
		return telaMenuVilao;
	}
	
	public void atualizarTelaMenuVilao() {
		telaMenuVilao.dispose();
		newTelaMenuVilao();
	}
	
	private void inicializaListeners() {
		telaMenuVilao.getBtnNovo().addActionListener(this);
		telaMenuVilao.getBtnEditar().addActionListener(this);
		telaMenuVilao.getBtnExcluir().addActionListener(this);
		telaMenuVilao.getBtnSair().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaMenuVilao.getBtnNovo()) {
			try {
				CadastroVilaoController.getInstance().newTelaCadastroVilao();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenuVilao.getBtnEditar()) {
			try {
				editarVilao();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenuVilao.getBtnExcluir()) {
			excluirVilao(evento);
		} else if (evento.getSource() == telaMenuVilao.getBtnSair()) {
			telaMenuVilao.dispose();
			MenuController.getInstance().atualizarTelaMenu();
		}
	}

	private void excluirVilao(ActionEvent evento) {
		if (!telaMenuVilao.getTxtProcurar().getText().trim().equals("")) {
			int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento);
			if (opcao == 0) {
				if (excluirVilaoPeloNome(telaMenuVilao.getTxtProcurar().getText().trim())) {
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuVilao();
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível excluir \"" + telaMenuVilao.getTxtProcurar().getText().trim() + "\",\n\nCadastro inválido ou inexistente!", "Erro!", 0);
				}
			}
		} else {
			try {
				MenuController.getInstance().getIdLinhaSelecionadaVilao();
				if (JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento) == 0) {
					excluirVilaoPeloId(MenuController.getInstance().getIdLinhaSelecionadaVilao());
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuVilao();
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "  DIGITE UM NOME OU SELECIONE\nA MERDA DE UM CARA PRIMEIRO!!!", "SEU BURRO", 0);
			}
		} 
	}

	private void editarVilao() throws ParseException {
		String vilao = telaMenuVilao.getTxtProcurar().getText().trim();
		if (!vilao.equals("")) {
				List<Vilao> foe = getVilaoPeloNome(vilao);
				for (Vilao enemy : foe) {
					CadastroVilaoController.getInstance().editarCadastroVilao(enemy.getId(), enemy.getNome(), enemy.getPeso(),
							enemy.getAltura(), enemy.getFotoVilao(), enemy.getAtaque(), enemy.getDefesa(), enemy.getVida(), enemy.getStatus(), enemy.getEstadoFisico());
				}
		} else
			JOptionPane.showMessageDialog(null, "Entre com um nome válido");
	}
	
	private List<Vilao> getVilaoPeloNome(String nomeVilao) {
		return vilaoDao.getPeloNome(nomeVilao);
	}
	private boolean excluirVilaoPeloNome(String nomeVilao) {
		return vilaoDao.remover(nomeVilao);
	}
	private boolean excluirVilaoPeloId(int idVilao) {
		return vilaoDao.removerPeloId(idVilao);
	}
}
