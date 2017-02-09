package br.com.inatel.ec206.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.dao.Heroi_equipa_armaDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.dao.Vilao_equipa_armaDAO;
import br.com.inatel.ec206.model.entity.Arma;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaCombate;

public class CombateController implements ActionListener {

	private static CombateController INSTANCE;
	private HeroiDAO heroiDao = HeroiDAO.getInstance();
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	private Heroi_equipa_armaDAO heroiEquipaArmaDao = Heroi_equipa_armaDAO.getInstance();
	private Vilao_equipa_armaDAO vilaoEquipaArmaDao = Vilao_equipa_armaDAO.getInstance();
	private TelaCombate telaCombate;
	private byte[] imgHeroiByte, imgVilaoByte;
	private Image fotoHeroi, fotoVilao;
	private String ataque, defesa;
	private int ataqueTotalVilao, ataqueTotalHeroi, vidaTotalVilao, vidaTotalHeroi, defesaTotalVilao, defesaTotalHeroi;
	private static final String NEWLINE = System.getProperty("line.separator");
	private Random r;
	
	public int getDefesaTotalVilao() {
		return defesaTotalVilao;
	}
	public void setDefesaTotalVilao(int defesaTotalVilao) {
		this.defesaTotalVilao = defesaTotalVilao;
	}
	public int getDefesaTotalHeroi() {
		return defesaTotalHeroi;
	}
	public void setDefesaTotalHeroi(int defesaTotalHeroi) {
		this.defesaTotalHeroi = defesaTotalHeroi;
	}
	public int getAtaqueTotalVilao() {
		return ataqueTotalVilao;
	}
	public void setAtaqueTotalVilao(int ataqueTotalVilao) {
		this.ataqueTotalVilao = ataqueTotalVilao;
	}
	public int getAtaqueTotalHeroi() {
		return ataqueTotalHeroi;
	}
	public void setAtaqueTotalHeroi(int ataqueTotalHeroi) {
		this.ataqueTotalHeroi = ataqueTotalHeroi;
	}
	public int getVidaTotalVilao() {
		return vidaTotalVilao;
	}
	public void setVidaTotalVilao(int vidaTotalVilao) {
		this.vidaTotalVilao = vidaTotalVilao;
	}
	public int getVidaTotalHeroi() {
		return vidaTotalHeroi;
	}
	public void setVidaTotalHeroi(int vidaTotalHeroi) {
		this.vidaTotalHeroi = vidaTotalHeroi;
	}
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
	public Image getFotoHeroi() {
		return fotoHeroi;
	}
	public void setFotoHeroi(Image fotoHeroi) {
		this.fotoHeroi = fotoHeroi;
	}
	public Image getFotoVilao() {
		return fotoVilao;
	}
	public void setFotoVilao(Image fotoVilao) {
		this.fotoVilao = fotoVilao;
	}
	
	private CombateController() {}
	
	public static synchronized CombateController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CombateController();
		}
		return INSTANCE;
	}
	
	public void newTelaCombate() {
		telaCombate = new TelaCombate();
		inicializaListeners();
		for (Heroi hero : getNomesHeroisJcbox()) {
			telaCombate.getJcbHeroi().addItem(hero.getId() + " | " + hero.getNome());
		}
		for (Vilao foe : getNomesViloesJcbox()) {
			telaCombate.getJcbVilao().addItem(foe.getId() + " | " + foe.getNome());
		}
		telaCombate.setVisible(true);
	}
	
	public void inicializaListeners() {
		telaCombate.getBtnLutar().addActionListener(this);
		telaCombate.getJcbHeroi().addActionListener(this);
		telaCombate.getJcbVilao().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaCombate.getJcbHeroi()) {
			carregaHeroiEArmas();
		} else if (e.getSource() == telaCombate.getJcbVilao()) {
			carregaVilaoEArmas();
		} else if (e.getSource() == telaCombate.getBtnLutar()) {
			new Fight();
/*----------------------------------------------------------------------------------------
//			new Thread(new LutaThread()).start();
			Message message = new Message("Howdy");

			Waiter waiter = new Waiter(message);
			Thread waiterThread = new Thread(waiter, "waiterThread");
			waiterThread.start();

			Notifier notifier = new Notifier(message);
			Thread notifierThread = new Thread(notifier, "notifierThread");
			notifierThread.start();
----------------------------------------------------------------------------------------*/
			r = new Random();
			int dmg, atkHero, defFoe, atkFoe, defHero;
			Integer atualHero = 0, atualFoe = 0;
			telaCombate.getLogDeCombate().setText(new Date().toString() + NEWLINE);
			while ( getVidaTotalHeroi() > 0 || getVidaTotalVilao() > 0) {
				atkHero = r.nextInt(getAtaqueTotalHeroi());
				telaCombate.getLogDeCombate().append("Heroi atacou com " + atkHero + " de atk." + NEWLINE);
				defFoe = r.nextInt(getDefesaTotalVilao());
				telaCombate.getLogDeCombate().append("Vilao defendeu com " + defFoe + " de def." + NEWLINE);
				dmg =  atkHero - defFoe;
				if ( dmg >= 0) {
					telaCombate.getLogDeCombate().append("Heroi causou " + dmg + " de dmg." + NEWLINE);
					atualFoe += dmg;
					atualFoe = 100 - atualFoe;
					setVidaTotalVilao(atualFoe);
					telaCombate.getLogDeCombate().append("VILAO ficou com " + getVidaTotalVilao() + " de VIDA" + NEWLINE);
					telaCombate.atualizaVidaVilao(atualFoe);
					telaCombate.getLblVidaVilao().setText(atualFoe.toString());
				} else {
					telaCombate.getLogDeCombate().append("Heroi causou 0 de dmg." + NEWLINE);
				}
				atkFoe = r.nextInt(getAtaqueTotalVilao());
				telaCombate.getLogDeCombate().append("Vilao atacou com " + atkFoe + " de atk." + NEWLINE);
				defHero = r.nextInt(getDefesaTotalHeroi());
				telaCombate.getLogDeCombate().append("Heroi defendeu com " + defHero + " de def." + NEWLINE);
				dmg =  atkFoe - defHero;
				if ( dmg >= 0) {
					telaCombate.getLogDeCombate().append("Vilao causou " + dmg + " de dmg." + NEWLINE);
					atualHero += dmg;
					atualHero = 100 - atualHero;
					setVidaTotalHeroi(atualHero);
					telaCombate.getLogDeCombate().append("HEROI ficou com " + getVidaTotalHeroi() + " de VIDA" + NEWLINE);
					telaCombate.atualizaVidaHeroi(atualHero);
					telaCombate.getLblVidaHeroi().setText(atualHero.toString());
				} else {
					telaCombate.getLogDeCombate().append("Vilao causou 0 de dmg." + NEWLINE);
				}
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
//			telaCombate.getLogDeCombate().append("getAtaqueTotalHeroi(): " + getAtaqueTotalHeroi() + " Ataque desferido: " + r.nextInt(getAtaqueTotalHeroi()) +
//													"\ngetDefesaTotalHeroi(): " + getDefesaTotalHeroi() + " Defendido: " + r.nextInt(getDefesaTotalHeroi()) +
//													"\ngetVidaTotalHeroi(): " + getVidaTotalHeroi() +
//													"\ngetAtaqueTotalVilao(): " + getAtaqueTotalVilao() +
//													"\ngetDefesaTotalVilao(): " + getDefesaTotalVilao() +
//													"\ngetVidaTotalVilao(): " + getVidaTotalVilao() + NEWLINE);
//			telaCombate.getLogDeCombate().setCaretPosition(telaCombate.getLogDeCombate().getDocument().getLength());
		}
	}
	
	private void carregaHeroiEArmas() {
		String getItemJcb = telaCombate.getJcbHeroi().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idHeroi = Integer.parseInt(paraString);
		Integer poderArma = 0, defesaArma = 0;
		for (Heroi hero : getPeloIdHeroi(idHeroi)) {
			telaCombate.getLblImgHeroi().setIcon(new ImageIcon(getFotoCadastradaDoBancoHeroi(hero.getFotoHeroi())));
			Integer heroVida = hero.getVida();
			telaCombate.getLblVidaHeroi().setText(heroVida.toString());
			telaCombate.atualizaVidaHeroi(heroVida);
		}
		for (Arma arma : listarArmas1(idHeroi) ) {
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		for (Arma arma : listarArmas2(idHeroi)) {
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		setAtaque(poderArma.toString());
		setAtaqueTotalHeroi(poderArma + 15);
		setDefesa(defesaArma.toString());
		setDefesaTotalHeroi(defesaArma + 10);
		setVidaTotalHeroi(100);
		telaCombate.getLblAtkDefHeroi().setText(getAtaque() + "/" + getDefesa());
	}
	
	private void carregaVilaoEArmas() {
		String getItemJcb = telaCombate.getJcbVilao().getSelectedItem().toString();
		String paraString = getItemJcb.subSequence(0, getItemJcb.indexOf("|")-1).toString();
		int idVilao = Integer.parseInt(paraString);
		Integer poderArma = 0, defesaArma = 0;
		for (Vilao foe : getPeloIdVilao(idVilao)) {
			telaCombate.getLblImgVilao().setIcon(new ImageIcon(getFotoCadastradaDoBancoVilao(foe.getFotoVilao())));
			Integer foeVida = foe.getVida();
			telaCombate.getLblVidaVilao().setText(foeVida.toString());
			telaCombate.atualizaVidaVilao(foeVida);
		}
		for (Arma arma : listarArmas1Vilao(idVilao) ) {
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		for (Arma arma : listarArmas2Vilao(idVilao)) {
			poderArma += arma.getAtaque();
			defesaArma += arma.getDefesa();
		}
		setAtaque(poderArma.toString());
		setAtaqueTotalVilao(poderArma + 15);
		setDefesa(defesaArma.toString());
		setDefesaTotalVilao(defesaArma + 10);
		setVidaTotalVilao(100);
		telaCombate.getLblAtkDefVilao().setText(getAtaque() + "/" + getDefesa());
	}
	
	private Image getFotoCadastradaDoBancoHeroi(Blob foto) {
		try {
			imgHeroiByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgHeroiByte");
			e.printStackTrace();
		}
		setFotoHeroi(new ImageIcon(imgHeroiByte).getImage());
		return fotoHeroi;
	}
	private Image getFotoCadastradaDoBancoVilao(Blob foto) {
		try {
			imgVilaoByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgVilaoByte");
			e.printStackTrace();
		}
		setFotoVilao(new ImageIcon(imgVilaoByte).getImage());
		return fotoVilao;
	}
	
	private List<Heroi> getPeloIdHeroi(int idHeroi) {
		return heroiDao.getPeloId(idHeroi);
	}
	private List<Arma> listarArmas1(int idHeroi) {
		return heroiEquipaArmaDao.listarMao1(idHeroi);
	}
	private List<Arma> listarArmas2(int idHeroi) {
		return heroiEquipaArmaDao.listarMao2(idHeroi);
	}
	private List<Heroi> getNomesHeroisJcbox() {
		return heroiDao.getNomeHerois();
	}
	private List<Vilao> getNomesViloesJcbox() {
		return vilaoDao.getTodos();
	}
	private List<Vilao> getPeloIdVilao(int idVilao) {
		return vilaoDao.getPeloId(idVilao);
	}
	private List<Arma> listarArmas1Vilao(int idVilao) {
		return vilaoEquipaArmaDao.listarMao1(idVilao);
	}
	private List<Arma> listarArmas2Vilao(int idVilao) {
		return vilaoEquipaArmaDao.listarMao2(idVilao);
	}
}
