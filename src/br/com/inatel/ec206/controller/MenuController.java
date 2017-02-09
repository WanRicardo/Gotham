package br.com.inatel.ec206.controller;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import br.com.inatel.ec206.model.dao.ArmaDAO;
import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.dao.PoliciaDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Arma;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.model.entity.Policia;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TabelaArma;
import br.com.inatel.ec206.view.TabelaBandido;
import br.com.inatel.ec206.view.TabelaHeroi;
import br.com.inatel.ec206.view.TabelaPolicia;
import br.com.inatel.ec206.view.TelaMenu;

public class MenuController implements ActionListener, MouseListener{
	private static MenuController INSTANCE;
	private MenuPoliciaController menuPolicialController = MenuPoliciaController.getInstance();
	private MenuHeroiController menuHeroiController = MenuHeroiController.getInstance();
	private MenuVilaoController menuVilaoController = MenuVilaoController.getInstance();
	private MenuPrisioneiroController menuPrisioneiroController = MenuPrisioneiroController.getInstance();
	private RelatorioPolicialController relatorioPolicialController = RelatorioPolicialController.getInstance();
	private CadastroArmaController cadastroArmaController = CadastroArmaController.getInstance();
	private EquiparArmaController equiparArmaController = EquiparArmaController.getInstance();
	private CombateController combateController = CombateController.getInstance();
	private PoliciaDAO policiaDao = PoliciaDAO.getInstance();
	private HeroiDAO heroiDao = HeroiDAO.getInstance();
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	private ArmaDAO armaDao = ArmaDAO.getInstance();
	private TelaMenu telaMenu;
	private JTable tablePolicia, tableHeroi, tableVilao, tablePrisioneiro, tableArmaHeroi, tableArmaVilao;
	private TabelaPolicia modelTablePolicia;
	private TabelaHeroi modelTableHeroi;
	private TabelaBandido modelTableBandido;
	private TabelaArma modelTableArmaHeroi, modelTableArmaVilao;
	private String mouseListenerDo;
		
	private MenuController() {}
	
	public static synchronized MenuController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MenuController();
		}
		return INSTANCE;
	}
	
	public TelaMenu newTelaMenu(){
		telaMenu = new TelaMenu();
		inicializaListeners();
		telaMenu.getLblNome().setText("Bem vindo "+ getNomeUsuario(LoginController.getInstance().getIdUsuario()) +"!");
		telaMenu.setVisible(true);
		return telaMenu;
	}
	
	public void atualizarTelaMenu() {
		telaMenu.dispose();
		newTelaMenu();
	}

	private void inicializaListeners() {
		telaMenu.getBtnCadastros().addActionListener(this);
		telaMenu.getBtnPresidiarios().addActionListener(this);
		telaMenu.getBtnHerois().addActionListener(this);
		telaMenu.getBtnViloes().addActionListener(this);
		telaMenu.getBtnLogout().addActionListener(this);
		telaMenu.getNovoRelatorioPolicial().addActionListener(this);
		telaMenu.getAbrirRelatorio().addActionListener(this);
		telaMenu.getNovaArma().addActionListener(this);
		telaMenu.getDuelo().addActionListener(this);
		telaMenu.getEquiparArma().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == this.telaMenu.getBtnCadastros()){
			menuPolicialController.newTelaMenuPolicia();
		} else if (evento.getSource() == this.telaMenu.getBtnPresidiarios()) {
			menuPrisioneiroController.newTelaMenuPrisioneiro();
		} else if (evento.getSource() == this.telaMenu.getBtnHerois()) {
			menuHeroiController.newTelaMenuHeroi();
		} else if (evento.getSource() == this.telaMenu.getBtnViloes()) {
			menuVilaoController.newTelaMenuVilao();
		} else if (evento.getSource() == telaMenu.getNovoRelatorioPolicial()){
			relatorioPolicialController.escreveRelatorio();
		} else if (evento.getSource() == telaMenu.getAbrirRelatorio()) {
			relatorioPolicialController.newTelaRelatorioPolicial();
		} else if (evento.getSource() == telaMenu.getNovaArma()) {
			try {
				cadastroArmaController.newTelaCadastroArma();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaMenu.getDuelo()) {
			combateController.newTelaCombate();
		} else if (evento.getSource() == telaMenu.getEquiparArma()) {
			equiparArmaController.newTelaEquiparArma();
		} else if (evento.getSource() == this.telaMenu.getBtnLogout()) {
			Frame[] frame = Frame.getFrames();
			for (Frame frame2 : frame) {
				frame2.dispose();
			}
			LoginController.getInstance().newTelaLogin();
		}
	}
	
	public int getLinhaSelecionadaArmaHeroi() {
		return tableArmaHeroi.getSelectedRow();
	}
	public int getIdLinhaSelecionadaArmaHeroi() {
		return Integer.parseInt(modelTableArmaHeroi.getValueAt(getLinhaSelecionadaArmaHeroi(), 0).toString());
	}
	public JTable getTblListaArmaHeroi() {
		tableArmaHeroi = null;
		modelTableArmaHeroi = new TabelaArma(getArmas());
		tableArmaHeroi = new JTable();
		tableArmaHeroi.setModel(modelTableArmaHeroi);
		configTableAlignment(modelTableArmaHeroi, tableArmaHeroi);
		return tableArmaHeroi;
	}
	public int getLinhaSelecionadaArmaVilao() {
		return tableArmaVilao.getSelectedRow();
	}
	public int getIdLinhaSelecionadaArmaVilao() {
		return Integer.parseInt(modelTableArmaVilao.getValueAt(getLinhaSelecionadaArmaVilao(), 0).toString());
	}
	public JTable getTblListaArmaVilao() {
		tableArmaVilao = null;
		modelTableArmaVilao = new TabelaArma(getArmas());
		tableArmaVilao = new JTable();
		tableArmaVilao.setModel(modelTableArmaVilao);
		configTableAlignment(modelTableArmaVilao, tableArmaVilao);
		return tableArmaVilao;
	}
	public List<Arma> getArmas() {
		List<Arma> pegaArmas, listaArmas = getListaArmasCadastradas();
		pegaArmas = new ArrayList<>();
		for (Arma arma : listaArmas) {
			pegaArmas.add(arma);
		}		
		return pegaArmas;
	}
	private void configTableAlignment(TabelaArma modeloLista, JTable tabela) {
		TableColumnModel columnModel = tabela.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < modeloLista.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(centerRenderer);
		}
	}
	
	public JTable getTblListaPolicia() {
		tablePolicia = null;
		atualizaNumPresidiarios();
		modelTablePolicia = new TabelaPolicia(getPoliciais());
		tablePolicia = new JTable();
		tablePolicia.setModel(modelTablePolicia);
		if (verificaAdmin(LoginController.getInstance().getIdUsuario()) != 0) {
			tablePolicia.addMouseListener(this);
		}
		configTableAlignment(modelTablePolicia, tablePolicia);
		mouseListenerDo = "Policial";
		return tablePolicia;
	}
	public List<Policia> getPoliciais() {
		List<Policia> pegaPoliciais, listaPoliciais = getListaPoliciaisCadastrados();
		pegaPoliciais = new ArrayList<>();
		for (Policia policia : listaPoliciais) {
			pegaPoliciais.add(policia);
		}		
		return pegaPoliciais;
	}
	private void configTableAlignment(TabelaPolicia modeloLista, JTable tabela) {
		TableColumnModel columnModel = tabela.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i < modeloLista.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(centerRenderer);
		}
	}
	
	public JTable getTblListaPresidiario() {
		tablePrisioneiro = null;
		modelTableBandido = new TabelaBandido(getPresidiarios());
		tablePrisioneiro = new JTable();
		tablePrisioneiro.setModel(modelTableBandido);
		tablePrisioneiro.addMouseListener(this);
		configTableAlignment(modelTableBandido, tablePrisioneiro);
		mouseListenerDo = "Prisioneiro";
		return tablePrisioneiro;
	}
	public List<Vilao> getPresidiarios() {
		List<Vilao> pegaViloes, listaViloes = getListaPrisioneirosCadastrados();
		pegaViloes = new ArrayList<>();
		for (Vilao vilao : listaViloes) {
			pegaViloes.add(vilao);
		}
		return pegaViloes;
	}
	
	public JTable getTblListaViloes() {
		tableVilao = null;
		modelTableBandido = new TabelaBandido(getViloes());
		tableVilao = new JTable();
		tableVilao.setModel(modelTableBandido);
		tableVilao.addMouseListener(this);
		configTableAlignment(modelTableBandido, tableVilao);
		mouseListenerDo = "Vilao";
		return tableVilao;
	}
	public List<Vilao> getViloes() {
		List<Vilao> pegaViloes, listaViloes = getListaViloesCadastrados();
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
	
	public JTable getTblListaHeroi() {
		tableHeroi = null;
		modelTableHeroi = new TabelaHeroi(getHerois());
		tableHeroi = new JTable();
		tableHeroi.setModel(modelTableHeroi);
		tableHeroi.addMouseListener(this);
		configTableAlignment(modelTableHeroi, tableHeroi);
		mouseListenerDo = "Heroi";
		return tableHeroi;
	}
	
	private List<Heroi> getHerois() {
		List<Heroi> pegaHerois, listaHerois = getListaHeroisCadastrados();
		pegaHerois = new ArrayList<>();
		for (Heroi heroi : listaHerois) {
			pegaHerois.add(heroi);
		}
		return pegaHerois;
	}
	
	private void configTableAlignment(TabelaHeroi modeloLista, JTable tabela) {
		TableColumnModel columnModel = tabela.getColumnModel();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i < modeloLista.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(centerRenderer);
		}
	}

	public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseClicked(MouseEvent e) {
    	if (e.getClickCount() == 2) {
    		if (mouseListenerDo == "Policial") {
    			try {
					abrirTelaEditarPolicial();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		} else if (mouseListenerDo == "Heroi") {
    			try {
					abrirTelaEditarHeroi();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		} else if (mouseListenerDo == "Vilao") {
    			try {
					abrirTelaEditarVilao();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		} else if (mouseListenerDo == "Prisioneiro") {
    			try {
					abrirTelaEditarPrisioneiro();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		} else {
    			JOptionPane.showMessageDialog(null, "Deu zica nos listeners das tabelas");
    		}
    	}
    }

	private void abrirTelaEditarHeroi() throws ParseException {
		System.out.println("Linha: " + getLinhaSelecionadaHeroi() + " ID: " + getIdLinhaSelecionadaHeroi() + " Nome: " + getNomeLinhaSelecionadaHeroi());
		
		List<Heroi> hero = getPeloIdHeroi(getIdLinhaSelecionadaHeroi());
		for (Heroi heroes : hero) {
			CadastroHeroiController.getInstance().editarCadastroHeroi(heroes.getId(), heroes.getNome(), heroes.getPeso(),
					heroes.getAltura(), heroes.getFotoHeroi(), heroes.getAtk(), heroes.getDef(), heroes.getVida(), heroes.getStatusHeroi(), heroes.getEstadoFisico(), heroes.getTendenciaMoral());
		}
	}
	public int getLinhaSelecionadaHeroi() {
		return tableHeroi.getSelectedRow();
	}
	public int getIdLinhaSelecionadaHeroi() {
		return Integer.parseInt(modelTableHeroi.getValueAt(getLinhaSelecionadaHeroi(), 0).toString());
	}
	public String getNomeLinhaSelecionadaHeroi() {
		return modelTableHeroi.getValueAt(getLinhaSelecionadaHeroi(), 1).toString();
	}
	
	private void abrirTelaEditarVilao() throws ParseException {
		System.out.println("Linha: " + getLinhaSelecionadaVilao() + " ID: " + getIdLinhaSelecionadaVilao() + " Nome: " + getNomeLinhaSelecionadaVilao());
		
		List<Vilao> vilao = getPeloIdVilao(getIdLinhaSelecionadaVilao());
		for (Vilao foe : vilao) {
			CadastroVilaoController.getInstance().editarCadastroVilao(foe.getId(), foe.getNome(), foe.getPeso(),
					foe.getAltura(), foe.getFotoVilao(), foe.getAtaque(), foe.getDefesa(), foe.getVida(), foe.getStatus(), foe.getEstadoFisico());
		}
	}
	public int getLinhaSelecionadaVilao() {
		return tableVilao.getSelectedRow();
	}
	public int getIdLinhaSelecionadaVilao() {
		return Integer.parseInt(modelTableBandido.getValueAt(getLinhaSelecionadaVilao(), 0).toString());
	}
	public String getNomeLinhaSelecionadaVilao() {
		return modelTableBandido.getValueAt(getLinhaSelecionadaVilao(), 1).toString();
	}
	
	private void abrirTelaEditarPrisioneiro() throws ParseException {
		System.out.println("Linha: " + getLinhaSelecionadaPrisioneiro() + " ID: " + getIdLinhaSelecionadaPrisioneiro() + " Nome: " + getNomeLinhaSelecionadaPrisioneiro());
		
		List<Vilao> vilao = getPeloIdPrisioneiro(getIdLinhaSelecionadaPrisioneiro());
		for (Vilao foe : vilao) {
			CadastroPrisioneiroController.getInstance().editarCadastroPrisioneiro(foe.getId(), foe.getNome(), foe.getPeso(),
					foe.getAltura(), foe.getFotoVilao(), foe.getAtaque(), foe.getDefesa(), foe.getVida(), foe.getStatus(), foe.getEstadoFisico());
		}
	}
	public int getLinhaSelecionadaPrisioneiro() {
		return tablePrisioneiro.getSelectedRow();
	}
	public int getIdLinhaSelecionadaPrisioneiro() {
		return Integer.parseInt(modelTableBandido.getValueAt(getLinhaSelecionadaPrisioneiro(), 0).toString());
	}
	public String getNomeLinhaSelecionadaPrisioneiro() {
		return modelTableBandido.getValueAt(getLinhaSelecionadaPrisioneiro(), 1).toString();
	}
	
	private void abrirTelaEditarPolicial() throws ParseException {		
		System.out.println("Linha: " + getLinhaSelecionadaPolicia() + " ID: " + getIdLinhaSelecionadaPolicia() + " Nome: " + getNomeLinhaSelecionadaPolicia());
		
		List<Policia> police = getPeloIdPolicia(getIdLinhaSelecionadaPolicia());
		for (Policia policia : police) {
			CadastroPoliciaController.getInstance().editarCadastroPolicia(policia.getId(), policia.getIdAdmin(),
					policia.getNome(), policia.getPeso(), policia.getAltura(), policia.getFotoPolicia(), policia.getCargo(), policia.getUsuario(), policia.getSenha());
		}
	}
	public int getLinhaSelecionadaPolicia() {
		return tablePolicia.getSelectedRow();
	}
	public int getIdLinhaSelecionadaPolicia() {
		return Integer.parseInt(modelTablePolicia.getValueAt(getLinhaSelecionadaPolicia(), 0).toString());
	}
	public String getNomeLinhaSelecionadaPolicia() {
		return modelTablePolicia.getValueAt(getLinhaSelecionadaPolicia(), 1).toString();
	}
    
	
	private List<Policia> getPeloIdPolicia(int idPolicia) {
		return policiaDao.getPeloId(idPolicia);
	}
	private String getNomeUsuario(int idUsuario) {
		return policiaDao.getNomeUsuario(idUsuario);
	}
	private int verificaAdmin(int idPolicia) {
		return policiaDao.verificaAdmin(idPolicia);
	}
	private List<Heroi> getPeloIdHeroi(int idHeroi) {
		return heroiDao.getPeloId(idHeroi);
	}
	private List<Vilao> getPeloIdVilao(int idVilao) {
		return vilaoDao.getPeloId(idVilao);
	}
	private List<Vilao> getPeloIdPrisioneiro(int idPrisioneiro) {
		return vilaoDao.getPeloIdPrisioneiro(idPrisioneiro);
	}
	private List<Heroi> getListaHeroisCadastrados() {
		return heroiDao.getTodos();
	}
	private List<Vilao> getListaViloesCadastrados() {
		return vilaoDao.getTodos();
	}
	private List<Vilao> getListaPrisioneirosCadastrados() {
		return vilaoDao.getTodosPresos();
	}
	private List<Policia> getListaPoliciaisCadastrados() {
		return policiaDao.getTodos();
	}
	private List<Arma> getListaArmasCadastradas() {
		return armaDao.getTodas();
	}
	private void atualizaNumPresidiarios() {
		List<Policia> listaPoliciais = PoliciaDAO.getInstance().getAllPolice();
		for (Policia policia : listaPoliciais) {
			policiaDao.setNumPrisioneiros(vilaoDao.getNumPrisioneiros(policia.getId()), policia.getId());
		}		
	}
}
