package br.com.inatel.ec206.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.dao.Heroi_equipa_armaDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.dao.Vilao_equipa_armaDAO;
import br.com.inatel.ec206.model.entity.Arma;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaEquiparArma;

public class EquiparArmaController implements ActionListener {
	private static EquiparArmaController INSTANCE;
	private HeroiDAO heroiDao = HeroiDAO.getInstance();
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	private Heroi_equipa_armaDAO heroiEquipaArmaDao = Heroi_equipa_armaDAO.getInstance();
	private Vilao_equipa_armaDAO vilaoEquipaArmaDao = Vilao_equipa_armaDAO.getInstance(); 
	private TelaEquiparArma telaEquiparArma;
	private byte[] imgHeroiByte, imgVilaoByte, imgArmaByte;
	private Image fotoHeroi, fotoVilao, fotoArma;
	private String ataque, defesa;
	
	public String getAtaque() {
		return "15(+" + ataque + ")";
	}
	public void setAtaque(String ataque) {
		this.ataque = ataque;
	}
	public String getDefesa() {
		return "10(+" + defesa + ")";
	}
	public void setDefesa(String defesa) {
		this.defesa = defesa;
	}
	public Image getFotoArma() {
		return fotoArma;
	}
	public void setFotoArma(Image fotoArma) {
		this.fotoArma = fotoArma;
	}
	public Image getFotoVilao() {
		return fotoVilao;
	}
	public void setFotoVilao(Image fotoVilao) {
		this.fotoVilao = fotoVilao;
	}
	public Image getFotoHeroi() {
		return fotoHeroi;
	}
	public void setFotoHeroi(Image fotoHeroi) {
		this.fotoHeroi = fotoHeroi;
	}

	private EquiparArmaController() {}
	
	public static synchronized EquiparArmaController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EquiparArmaController();
		}
		return INSTANCE;
	}
	
	public void newTelaEquiparArma() {
		telaEquiparArma = new TelaEquiparArma();
		inicializaListeners();
		for (Heroi hero : getNomesHeroisJcbox()) {
			telaEquiparArma.getJcbHeroi().addItem(hero.getId() + " | " + hero.getNome());
		}
		for (Vilao foe : getNomesViloesJcbox()) {
			telaEquiparArma.getJcbVilao().addItem(foe.getId() + " | " + foe.getNome());
		}
		telaEquiparArma.setVisible(true);
	}
	
	public void disposeTelaEquiparArma() {
		telaEquiparArma.dispose();
	}
	
	public void inicializaListeners() {
		telaEquiparArma.getBtnAplicarHeroi().addActionListener(this);
		telaEquiparArma.getBtnAplicarVilao().addActionListener(this);
		telaEquiparArma.getBtnOkHeroi().addActionListener(this);
		telaEquiparArma.getBtnOkVilao().addActionListener(this);
		telaEquiparArma.getBtnSairHeroi().addActionListener(this);
		telaEquiparArma.getBtnSairVilao().addActionListener(this);
		telaEquiparArma.getBtnAddArmaHeroi().addActionListener(this);
		telaEquiparArma.getBtnAddArmaVilao().addActionListener(this);
		telaEquiparArma.getJcbHeroi().addActionListener(this);
		telaEquiparArma.getJcbVilao().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaEquiparArma.getBtnAplicarHeroi()) {
			String getItemJcb = telaEquiparArma.getJcbHeroi().getSelectedItem().toString();
			String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
			int idHeroi = Integer.parseInt(paraString);
			if (telaEquiparArma.getJrbArma1Hero().isSelected()) {
				removerArmaHeroi(idHeroi, 1);
			} else if (telaEquiparArma.getJrbArma2Hero().isSelected()) {
				removerArmaHeroi(idHeroi, 2);
			}
			carregaHeroiEArmas();
		} else if (evento.getSource() == telaEquiparArma.getBtnAplicarVilao()) {
			String getItemJcb = telaEquiparArma.getJcbVilao().getSelectedItem().toString();
			String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
			int idVilao = Integer.parseInt(paraString);
			if (telaEquiparArma.getJrbArma1Vilao().isSelected()) {
				removerArmaVilao(idVilao, 1);
			} else if (telaEquiparArma.getJrbArma2Vilao().isSelected()) {
				removerArmaVilao(idVilao, 2);
			}
			carregaVilaoEArmas();
		} else if (evento.getSource() == telaEquiparArma.getBtnOkHeroi()) {
			disposeTelaEquiparArma();
		} else if (evento.getSource() == telaEquiparArma.getBtnOkVilao()) {
			disposeTelaEquiparArma();
		} else if (evento.getSource() == telaEquiparArma.getBtnSairHeroi()) {
			disposeTelaEquiparArma();
		} else if (evento.getSource() == telaEquiparArma.getBtnSairVilao()) {
			disposeTelaEquiparArma();
		} else if (evento.getSource() == telaEquiparArma.getBtnAddArmaHeroi()) {
			equiparArmaHeroi();
		} else if (evento.getSource() == telaEquiparArma.getBtnAddArmaVilao()) {
			equiparArmaVilao();
		} else if (evento.getSource() == telaEquiparArma.getJcbHeroi()) {
			carregaHeroiEArmas();
		} else if (evento.getSource() == telaEquiparArma.getJcbVilao()) {
			carregaVilaoEArmas();
		}
	}
	private void equiparArmaVilao() {
		String getItemJcb = telaEquiparArma.getJcbVilao().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idVilao = Integer.parseInt(paraString);
		int idArma = MenuController.getInstance().getIdLinhaSelecionadaArmaVilao();
		if (telaEquiparArma.getJrbArma1Vilao().isSelected()) {
			removerArmaVilao(idVilao, 1);
			if (cadastrarArmaVilao(idArma, idVilao, 1)) {
				JOptionPane.showMessageDialog(null, "Arma equipada!");
				carregaVilaoEArmas();
			} else JOptionPane.showMessageDialog(null, "Não equipou arma!");
		} else if (telaEquiparArma.getJrbArma2Vilao().isSelected()) {
			removerArmaVilao(idVilao, 2);
			if (cadastrarArmaVilao(idArma, idVilao, 2)) {
				JOptionPane.showMessageDialog(null, "Arma equipada!");
				carregaVilaoEArmas();
			} else JOptionPane.showMessageDialog(null, "Não equipou arma!");
		}
	}
	private void carregaVilaoEArmas() {
		String getItemJcb = telaEquiparArma.getJcbVilao().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idVilao = Integer.parseInt(paraString);
		Integer poderArma = 0, defesaArma = 0;
		Image novo = new ImageIcon(this.getClass().getResource(
				"/novo.jpg")).getImage();
		telaEquiparArma.getLblArma1Vilao().setIcon(new ImageIcon(novo));
		telaEquiparArma.getLblArma2Vilao().setIcon(new ImageIcon(novo));
		for (Vilao foe : getPeloIdVilao(idVilao)) {
			telaEquiparArma.getLblImgVilao().setIcon(new ImageIcon(getFotoCadastradaDoBancoVilao(foe.getFotoVilao())));
			
		}
		for (Arma arma : listarArmas1Vilao(idVilao) ) {
			telaEquiparArma.getLblArma1Vilao().setIcon(new ImageIcon(getFotoCadastradaDoBancoArma(arma.getImgArma())));
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		for (Arma arma : listarArmas2Vilao(idVilao)) {
			telaEquiparArma.getLblArma2Vilao().setIcon(new ImageIcon(getFotoCadastradaDoBancoArma(arma.getImgArma())));
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		setAtaque(poderArma.toString());
		setDefesa(defesaArma.toString());
		telaEquiparArma.getLblAtkVilao().setText(getAtaque());
		telaEquiparArma.getLblDefVilao().setText(getDefesa());
	}
	private void equiparArmaHeroi() {
		String getItemJcb = telaEquiparArma.getJcbHeroi().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idHeroi = Integer.parseInt(paraString);
		int idArma = MenuController.getInstance().getIdLinhaSelecionadaArmaHeroi();
		if (telaEquiparArma.getJrbArma1Hero().isSelected()) {
			removerArmaHeroi(idHeroi, 1);
			if (cadastrarArmaHeroi(idArma, idHeroi, 1)) {
				JOptionPane.showMessageDialog(null, "Arma equipada!");
				carregaHeroiEArmas();
			} else JOptionPane.showMessageDialog(null, "Não equipou arma!");
		} else if (telaEquiparArma.getJrbArma2Hero().isSelected()) {
			removerArmaHeroi(idHeroi, 2);
			if (cadastrarArmaHeroi(idArma, idHeroi, 2)) {
				JOptionPane.showMessageDialog(null, "Arma equipada!");
				carregaHeroiEArmas();
			} else JOptionPane.showMessageDialog(null, "Não equipou arma!");
		}
	}
	private void carregaHeroiEArmas() {
		String getItemJcb = telaEquiparArma.getJcbHeroi().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idHeroi = Integer.parseInt(paraString);
		Integer poderArma = 0, defesaArma = 0;
		Image novo = new ImageIcon(this.getClass().getResource(
				"/novo.jpg")).getImage();
		telaEquiparArma.getLblArma1Hero().setIcon(new ImageIcon(novo));
		telaEquiparArma.getLblArma2Hero().setIcon(new ImageIcon(novo));
		for (Heroi hero : getPeloIdHeroi(idHeroi)) {
			telaEquiparArma.getLblImgHeroi().setIcon(new ImageIcon(getFotoCadastradaDoBancoHeroi(hero.getFotoHeroi())));
		}
		for (Arma arma : listarArmas1(idHeroi) ) {
			telaEquiparArma.getLblArma1Hero().setIcon(new ImageIcon(getFotoCadastradaDoBancoArma(arma.getImgArma())));
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		for (Arma arma : listarArmas2(idHeroi)) {
			telaEquiparArma.getLblArma2Hero().setIcon(new ImageIcon(getFotoCadastradaDoBancoArma(arma.getImgArma())));
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		setAtaque(poderArma.toString());
		setDefesa(defesaArma.toString());
		telaEquiparArma.getLblAtkHeroi().setText(getAtaque());
		telaEquiparArma.getLblDefHeroi().setText(getDefesa());
	}
	
	private Image getFotoCadastradaDoBancoArma(Blob foto) {
		try {
			imgArmaByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgArmaByte");
			e.printStackTrace();
		}
		setFotoArma(new ImageIcon(imgArmaByte).getImage());
		return getFotoArma();
	}
	
	private Image getFotoCadastradaDoBancoHeroi(Blob foto) {
		try {
			imgHeroiByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgHeroiByte");
			e.printStackTrace();
		}
		setFotoHeroi(new ImageIcon(imgHeroiByte).getImage());
		return getFotoHeroi();
	}
	
	private Image getFotoCadastradaDoBancoVilao(Blob foto) {
		try {
			imgVilaoByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgVilaoByte");
			e.printStackTrace();
		}
		setFotoVilao(new ImageIcon(imgVilaoByte).getImage());
		return getFotoVilao();
	}
	
	private List<Heroi> getNomesHeroisJcbox() {
		return heroiDao.getNomeHerois();
	}
	private List<Vilao> getNomesViloesJcbox() {
		return vilaoDao.getTodos();
	}
	private List<Heroi> getPeloIdHeroi(int idHeroi) {
		return heroiDao.getPeloId(idHeroi);
	}
	private List<Vilao> getPeloIdVilao(int idVilao) {
		return vilaoDao.getPeloId(idVilao);
	}
	private List<Arma> listarArmas1(int idHeroi) {
		return heroiEquipaArmaDao.listarMao1(idHeroi);
	}
	private List<Arma> listarArmas2(int idHeroi) {
		return heroiEquipaArmaDao.listarMao2(idHeroi);
	}
	private List<Arma> listarArmas1Vilao(int idVilao) {
		return vilaoEquipaArmaDao.listarMao1(idVilao);
	}
	private List<Arma> listarArmas2Vilao(int idVilao) {
		return vilaoEquipaArmaDao.listarMao2(idVilao);
	}
	private boolean cadastrarArmaHeroi(int idArma, int idHeroi, int maoArma) {
		return heroiEquipaArmaDao.cadastrar(idArma, idHeroi, maoArma);
	}
	private boolean cadastrarArmaVilao(int idArma, int idVilao, int maoArma) {
		return vilaoEquipaArmaDao.cadastrar(idArma, idVilao, maoArma);
	}
	private void removerArmaHeroi(int idHeroi, int mao){
		heroiEquipaArmaDao.remover(idHeroi, mao);
	}
	private void removerArmaVilao(int idVilao, int mao){
		vilaoEquipaArmaDao.remover(idVilao, mao);
	}
}
