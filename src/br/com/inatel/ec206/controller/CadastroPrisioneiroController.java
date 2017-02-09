package br.com.inatel.ec206.controller;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.inatel.ec206.model.Constants;
import br.com.inatel.ec206.model.dao.PoliciaDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaCadastroPrisioneiro;

public class CadastroPrisioneiroController implements ActionListener, ItemListener {
	private static CadastroPrisioneiroController INSTANCE;
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	private Vilao vilao;
	private TelaCadastroPrisioneiro telaCadastroPrisioneiro;
	private JFileChooser escolherArquivo = null;
	private FileNameExtensionFilter filtroExtensao = null;
	private Image fotoPrisioneiro;
	private byte[] imgPrisioneiroByte = null;
	private boolean novaFoto = false;
	private BufferedImage imgBufferizada;
	private Graphics2D g2d;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	
	public Image getFotoPrisioneiro() {
		return fotoPrisioneiro;
	}
	public void setFotoPrisioneiro(Image fotoPrisioneiro) {
		this.fotoPrisioneiro = fotoPrisioneiro;
	}

	private CadastroPrisioneiroController() {}
	
	public static synchronized CadastroPrisioneiroController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CadastroPrisioneiroController();
		}
		return INSTANCE;
	}
	
	public TelaCadastroPrisioneiro newTelaCadastroPrisioneiro() throws ParseException {
		telaCadastroPrisioneiro = new TelaCadastroPrisioneiro();
		inicializaListeners();
		telaCadastroPrisioneiro.getChboxPrisioneiro().setSelected(true);
		telaCadastroPrisioneiro.getChboxPrisioneiro().setVisible(false);
		telaCadastroPrisioneiro.getTxtStatus().setEditable(false);
		telaCadastroPrisioneiro.getTxtId().setText(getProxId().toString());
		telaCadastroPrisioneiro.setVisible(true);
		return telaCadastroPrisioneiro;
	}
	
	public void disposeTelaCadastroPrisioneiro() {
		this.telaCadastroPrisioneiro.dispose();
	}
	
	private Image getFotoCadastradaPrisioneiro(Blob foto) {
		try {
			imgPrisioneiroByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgVilaoByte");
			e.printStackTrace();
		}
		setFotoPrisioneiro(new ImageIcon(imgPrisioneiroByte).getImage());
		return getFotoPrisioneiro();
	}

	public void editarCadastroPrisioneiro(int id, String nome, int peso, int altura, Blob foto, int atk, int def, int vida,
			String status, String estado) throws ParseException {
		telaCadastroPrisioneiro = new TelaCadastroPrisioneiro();
		telaCadastroPrisioneiro.getTxtId().setText(String.valueOf(id));
		telaCadastroPrisioneiro.getTxtId().setEditable(false);
		telaCadastroPrisioneiro.getTxtNome().setText(nome);
		telaCadastroPrisioneiro.getTxtPeso().setText(String.valueOf(peso));
		telaCadastroPrisioneiro.getTxtAltura().setText(String.valueOf(altura));
		telaCadastroPrisioneiro.getLblFoto().setIcon(new ImageIcon(getFotoCadastradaPrisioneiro(foto)));
		telaCadastroPrisioneiro.getLblAtkDef().setText(String.valueOf(atk) + "/" + String.valueOf(def));
		telaCadastroPrisioneiro.getLblVida().setText(String.valueOf(vida));
		telaCadastroPrisioneiro.getTxtStatus().setText(status);
		telaCadastroPrisioneiro.getTxtStatus().setEditable(false);
		telaCadastroPrisioneiro.getChboxPrisioneiro().setSelected(true);
		telaCadastroPrisioneiro.getChboxPrisioneiro().setEnabled(false);
		telaCadastroPrisioneiro.getTxtEstado().setText(estado);
		inicializaListeners();
		telaCadastroPrisioneiro.getBtnOk().setText("Atualizar");
		telaCadastroPrisioneiro.getBtnOk().setActionCommand("Atualizar");
		telaCadastroPrisioneiro.setVisible(true);
	}
	
	private void inicializaListeners() {
		telaCadastroPrisioneiro.getBtnOk().setActionCommand("Inserir");
		telaCadastroPrisioneiro.getBtnOk().addActionListener(this);
		telaCadastroPrisioneiro.getBtnSair().addActionListener(this);
		telaCadastroPrisioneiro.getBtnFoto().addActionListener(this);
		telaCadastroPrisioneiro.getChboxPrisioneiro().addItemListener(this);
	}
	
	public void itemStateChanged(ItemEvent evento) {
		String status = "";
		if (evento.getStateChange() == 1) {
			status = telaCadastroPrisioneiro.getTxtStatus().getText();
			telaCadastroPrisioneiro.getTxtStatus().setText("Preso");
			telaCadastroPrisioneiro.getTxtStatus().setEditable(false);
		} else {
			telaCadastroPrisioneiro.getTxtStatus().setText(status);
			telaCadastroPrisioneiro.getTxtStatus().setEditable(true);
		}		
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals("Atualizar")) {
			try {
				editarVilao();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getActionCommand().equals("Inserir")) {
			try {
				if (telaCadastroPrisioneiro.getTxtNome().getText().trim().equals("")
						|| telaCadastroPrisioneiro.getTxtPeso().getText().trim().equals("")
						|| telaCadastroPrisioneiro.getTxtAltura().getText().trim().equals("")
						|| telaCadastroPrisioneiro.getTxtEstado().getText().trim().equals("")
						|| telaCadastroPrisioneiro.getTxtStatus().getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				} else {
					try {
						novoCadastroPrisioneiro();
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (HeadlessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaCadastroPrisioneiro.getBtnSair()) {
			disposeTelaCadastroPrisioneiro();
		} else if (evento.getSource() == telaCadastroPrisioneiro.getBtnFoto()) {
			getFotoSelecionada();
		}
	}

	private void editarVilao() throws NumberFormatException, ParseException {
		vilao = new Vilao();
		vilao.setId(Integer.parseInt(telaCadastroPrisioneiro.getTxtId().getText().toString()));
		vilao.setNome(telaCadastroPrisioneiro.getTxtNome().getText());
		vilao.setPeso(Integer.parseInt(telaCadastroPrisioneiro.getTxtPeso().getText().toString()));
		vilao.setAltura(Integer.parseInt(telaCadastroPrisioneiro.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			vilao.setFotoByteVilao(getFotoVilaoToByte(getFotoPrisioneiro()));
		} else vilao.setFotoByteVilao(getFotoVilaoToByte(getNovaFotoPrisioneiro()));
		
		vilao.setStatus(telaCadastroPrisioneiro.getTxtStatus().getText());
		vilao.setEstadoFisico(telaCadastroPrisioneiro.getTxtEstado().getText());
		vilao.setIdPolicia(LoginController.getInstance().getIdUsuario());

		if (atualizarCadastroPresidiario(vilao)) {
			JOptionPane.showMessageDialog(null, "Atualização do cadastro efetuada com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não rolou atualização!", "Deu ruim", 0);
		disposeTelaCadastroPrisioneiro();
		MenuPrisioneiroController.getInstance().atualizarTelaMenuPrisioneiro();
	}

	private void novoCadastroPrisioneiro() throws NumberFormatException, ParseException {
		vilao = new Vilao();
		vilao.setId(Integer.parseInt(telaCadastroPrisioneiro.getTxtId().getText().toString()));
		vilao.setNome(telaCadastroPrisioneiro.getTxtNome().getText());
		vilao.setPeso(Integer.parseInt(telaCadastroPrisioneiro.getTxtPeso().getText().toString()));
		vilao.setAltura(Integer.parseInt(telaCadastroPrisioneiro.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			setFotoPrisioneiro(new ImageIcon(Constants.DEFAULT_PATH + "desconhecido.jpg").getImage());
			vilao.setFotoByteVilao(getFotoVilaoToByte(getFotoPrisioneiro()));
		} else vilao.setFotoByteVilao(getFotoVilaoToByte(getNovaFotoPrisioneiro()));
		vilao.setStatus(telaCadastroPrisioneiro.getTxtStatus().getText());
		vilao.setEstadoFisico(telaCadastroPrisioneiro.getTxtEstado().getText());
		vilao.setIdPolicia(LoginController.getInstance().getIdUsuario());
		//TODO
		vilao.setAtaque(15);
		vilao.setDefesa(10);
		vilao.setVida(100);
		if (cadastrarPresidiario(vilao)) {
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não cadastrou!", "Deu ruim", 1);
		atualizaNumPrisioneiros(LoginController.getInstance().getIdUsuario());
		disposeTelaCadastroPrisioneiro();
		MenuPrisioneiroController.getInstance().atualizarTelaMenuPrisioneiro();
	}
	
	//transforma a imagem em um array de bytes para armazenar no banco
	private ByteArrayInputStream getFotoVilaoToByte(Image fotoVilao) {
		imgBufferizada = new BufferedImage(fotoVilao.getWidth(null), fotoVilao.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g2d = imgBufferizada.createGraphics();
		g2d.drawImage(fotoVilao, 0, 0, null);
		g2d.dispose();
		baos = null;
		try {
		    baos = new ByteArrayOutputStream();
		    ImageIO.write(imgBufferizada, "png", baos);
		} catch (Exception e1){
			JOptionPane.showMessageDialog(null, "Deu alguma zica pra transformar em array de bytes");
			e1.printStackTrace();
		} finally {
		    try {
		        baos.close();
		    } catch (Exception e1) {
		    	JOptionPane.showMessageDialog(null, "Não fechou");
		    	e1.printStackTrace();
		    }
		}
		bais = new ByteArrayInputStream(baos.toByteArray());
		return bais;
	}

	private void getFotoSelecionada() {
		int state = getEscolherArquivo().showOpenDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			novaFoto = true;
			telaCadastroPrisioneiro.getLblFoto().setIcon(new ImageIcon(getNovaFotoPrisioneiro()));
		}
	}
	
	private Image getNovaFotoPrisioneiro() {
		setFotoPrisioneiro(new ImageIcon(getEscolherArquivo().getSelectedFile().getPath()).getImage());
		return getFotoPrisioneiro();
	}

	private FileNameExtensionFilter getFiltroExtensao() {
		if (filtroExtensao == null) {
			filtroExtensao = new FileNameExtensionFilter("JPG e PNG", new String[] { "jpg" , "png" });
		}
		return filtroExtensao;
	}
	private JFileChooser getEscolherArquivo() {
		if (escolherArquivo == null) {
			escolherArquivo = new JFileChooser(Constants.DEFAULT_PATH);
			escolherArquivo.setFileFilter(getFiltroExtensao());
			escolherArquivo.setMultiSelectionEnabled(false);
		}
		return escolherArquivo;
	}
	
	private boolean cadastrarPresidiario(Vilao presidiario) {
		return vilaoDao.cadastrarPresidiario(presidiario);
	}
	private boolean atualizarCadastroPresidiario(Vilao presidiario) {
		return vilaoDao.editarPresidiario(presidiario);		
	}
	private Integer getProxId() {
		return vilaoDao.getProxId();
	}
	private void atualizaNumPrisioneiros(int idPolicia) {
		PoliciaDAO.getInstance().setNumPrisioneiros(vilaoDao.getNumPrisioneiros(idPolicia), idPolicia);
	}
}
