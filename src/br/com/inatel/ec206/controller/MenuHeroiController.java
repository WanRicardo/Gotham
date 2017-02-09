package br.com.inatel.ec206.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.view.TelaMenuHeroi;

public class MenuHeroiController implements ActionListener {
	private static MenuHeroiController INSTANCE;
	private TelaMenuHeroi telaMenuHeroi;
	private HeroiDAO heroiDao = HeroiDAO.getInstance();
	
	private MenuHeroiController() {}
	
	public static synchronized MenuHeroiController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MenuHeroiController();
		}
		return INSTANCE;
	}
	
	public TelaMenuHeroi newTelaMenuHeroi() {
		telaMenuHeroi = new TelaMenuHeroi();
		inicializaListeners();
		telaMenuHeroi.setVisible(true);
		return telaMenuHeroi;
	}
	
	public void atualizarTelaMenuHeroi() {
		telaMenuHeroi.dispose();
		newTelaMenuHeroi();
	}
	
	private void inicializaListeners() {
		telaMenuHeroi.getBtnNovo().addActionListener(this);
		telaMenuHeroi.getBtnEditar().addActionListener(this);
		telaMenuHeroi.getBtnExcluir().addActionListener(this);
		telaMenuHeroi.getBtnSair().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaMenuHeroi.getBtnNovo()) {
			CadastroHeroiController.getInstance().newTelaCadastroHeroi();
		} else if (evento.getSource() == telaMenuHeroi.getBtnEditar()) {
			try {
				editarHeroi();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenuHeroi.getBtnExcluir()) {
			excluirHeroi(evento);
		} else if (evento.getSource() == telaMenuHeroi.getBtnSair()) {
			telaMenuHeroi.dispose();
		}
	}

	private void excluirHeroi(ActionEvent evento) {
		if (!telaMenuHeroi.getTxtProcurar().getText().trim().equals("")) {
			int opcao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento);
			if (opcao == 0) {
				if (excluirHeroi(telaMenuHeroi.getTxtProcurar().getText().trim())) {
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuHeroi();
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível excluir \"" + telaMenuHeroi.getTxtProcurar().getText().trim() + "\",\n\nCadastro inválido ou inexistente!", "Erro!", 0);
				}
			}
		} else {
			try {
				MenuController.getInstance().getIdLinhaSelecionadaHeroi();
				if (JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir o cadastro?", "Confirmação de Exclusão", 0, 3, null, null, evento) == 0) {
					excluirHeroiPeloId(MenuController.getInstance().getIdLinhaSelecionadaHeroi());
					JOptionPane.showMessageDialog(null, "Cadastro Excluído com sucesso!", "Deu bom!", 2);
					atualizarTelaMenuHeroi();
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "  DIGITE UM NOME OU SELECIONE\nA MERDA DE UM CARA PRIMEIRO!!!", "SEU BURRO", 0);
			}
		}
	}

	private void editarHeroi() throws ParseException {
		String heroi = telaMenuHeroi.getTxtProcurar().getText().trim();
		if (!heroi.equals("")) {
				List<Heroi> hero = getPeloNome(heroi);
				for (Heroi heroes : hero) {
					CadastroHeroiController.getInstance().editarCadastroHeroi(heroes.getId(), heroes.getNome(), heroes.getPeso(),
							heroes.getAltura(), heroes.getFotoHeroi(), heroes.getAtk(), heroes.getDef(), heroes.getVida(), heroes.getStatusHeroi(), heroes.getEstadoFisico(), heroes.getTendenciaMoral());
				}
		} else
			JOptionPane.showMessageDialog(null, "Entre com um nome válido");
	}
	
	private List<Heroi> getPeloNome(String nomeHeroi) {
		return heroiDao.getPeloNome(nomeHeroi);
	}
	private boolean excluirHeroi(String nomeHeroi) {
		return heroiDao.removerHeroi(nomeHeroi);
	}
	private boolean excluirHeroiPeloId(int idHeroi) {
		return heroiDao.removerHeroiPeloId(idHeroi);
	}
}
