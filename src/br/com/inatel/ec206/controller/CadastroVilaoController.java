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
import br.com.inatel.ec206.view.TelaCadastroVilao;

public class CadastroVilaoController implements ActionListener, ItemListener {
	private static CadastroVilaoController INSTANCE;
	private VilaoDAO vilaoDao = VilaoDAO.getInstance();
	private Vilao vilao;
	private TelaCadastroVilao telaCadastroVilao;
	private JFileChooser escolherArquivo = null;
	private FileNameExtensionFilter filtroExtensao = null;
	private Image fotoVilao;
	private byte[] imgVilaoByte = null;
	private boolean novaFoto = false;
	private BufferedImage imgBufferizada;
	private Graphics2D g2d;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	
	public Image getFotoVilao() {
		return fotoVilao;
	}
	public void setFotoVilao(Image fotoVilao) {
		this.fotoVilao = fotoVilao;
	}

	private CadastroVilaoController() {}
	
	public static synchronized CadastroVilaoController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CadastroVilaoController();
		}
		return INSTANCE;
	}
	
	public TelaCadastroVilao newTelaCadastroVilao() throws ParseException {
		telaCadastroVilao = new TelaCadastroVilao();
		inicializaListeners();
		telaCadastroVilao.getTxtId().setText(getProxId().toString());
		telaCadastroVilao.setVisible(true);
		return telaCadastroVilao;
	}
	
	public void disposeTelaCadastroVilao() {
		this.telaCadastroVilao.dispose();
	}
	
	private Image getFotoCadastradaVilao(Blob foto) {
		try {
			imgVilaoByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgVilaoByte");
			e.printStackTrace();
		}
		setFotoVilao(new ImageIcon(imgVilaoByte).getImage());
		return getFotoVilao();
	}

	public void editarCadastroVilao(int id, String nome, int peso, int altura, Blob foto, int atk, int def, int vida,
			String status, String estado) throws ParseException {
		telaCadastroVilao = new TelaCadastroVilao();
		telaCadastroVilao.getTxtId().setText(String.valueOf(id));
		telaCadastroVilao.getTxtId().setEditable(false);
		telaCadastroVilao.getTxtNome().setText(nome);
		telaCadastroVilao.getTxtPeso().setText(String.valueOf(peso));
		telaCadastroVilao.getTxtAltura().setText(String.valueOf(altura));
		telaCadastroVilao.getLblFoto().setIcon(new ImageIcon(getFotoCadastradaVilao(foto)));
		telaCadastroVilao.getLblAtkDef().setText(String.valueOf(atk) + "/" + String.valueOf(def));
		telaCadastroVilao.getLblVida().setText(String.valueOf(vida));
		telaCadastroVilao.getTxtStatus().setText(status);
		if (status.equals("Preso")) {
			telaCadastroVilao.getTxtStatus().setEditable(false);
			telaCadastroVilao.getChboxPrisioneiro().setSelected(true);
		}
		telaCadastroVilao.getTxtEstado().setText(estado);		
		inicializaListeners();
		telaCadastroVilao.getBtnOk().setText("Atualizar");
		telaCadastroVilao.getBtnOk().setActionCommand("Atualizar");
		telaCadastroVilao.setVisible(true);
	}
	
	private void inicializaListeners() {
		telaCadastroVilao.getBtnOk().setActionCommand("Inserir");
		telaCadastroVilao.getBtnOk().addActionListener(this);
		telaCadastroVilao.getBtnSair().addActionListener(this);
		telaCadastroVilao.getBtnFoto().addActionListener(this);
		telaCadastroVilao.getChboxPrisioneiro().addItemListener(this);
	}
	
	public void itemStateChanged(ItemEvent evento) {
		if (evento.getStateChange() == 1) {
			telaCadastroVilao.getTxtStatus().setText("Preso");
			telaCadastroVilao.getTxtStatus().setEditable(false);
		} else {
			telaCadastroVilao.getTxtStatus().setText("");
			telaCadastroVilao.getTxtStatus().setEditable(true);
		}		
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals("Atualizar")) {
			try {
				editarVilao();
			} catch (NumberFormatException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getActionCommand().equals("Inserir")) {
			try {
				if (telaCadastroVilao.getTxtNome().getText().trim().equals("")
						|| telaCadastroVilao.getTxtPeso().getText().trim().equals("")
						|| telaCadastroVilao.getTxtAltura().getText().trim().equals("")
						|| telaCadastroVilao.getTxtEstado().getText().trim().equals("")
						|| telaCadastroVilao.getTxtStatus().getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				} else {
					try {
						novoCadastroVilao();
					} catch (NumberFormatException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (HeadlessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaCadastroVilao.getBtnSair()) {
			disposeTelaCadastroVilao();
		} else if (evento.getSource() == telaCadastroVilao.getBtnFoto()) {
			getFotoSelecionada();
		}
	}

	private void editarVilao() throws NumberFormatException, ParseException {
		vilao = new Vilao();
		vilao.setId(Integer.parseInt(telaCadastroVilao.getTxtId().getText().toString()));
		vilao.setNome(telaCadastroVilao.getTxtNome().getText());
		vilao.setPeso(Integer.parseInt(telaCadastroVilao.getTxtPeso().getText().toString()));
		vilao.setAltura(Integer.parseInt(telaCadastroVilao.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			vilao.setFotoByteVilao(getFotoVilaoToByte(getFotoVilao()));
		} else vilao.setFotoByteVilao(getFotoVilaoToByte(getNovaFotoVilao()));
		vilao.setStatus(telaCadastroVilao.getTxtStatus().getText());
		vilao.setEstadoFisico(telaCadastroVilao.getTxtEstado().getText());

		if (telaCadastroVilao.getTxtStatus().getText().equals("Preso")) {
			vilao.setIdPolicia(LoginController.getInstance().getIdUsuario());
			if (atualizarCadastroPresidiario(vilao)) {
				JOptionPane.showMessageDialog(null, "Atualização do cadastro efetuada com sucesso!", "Nóis memo", 2);
			} else
				JOptionPane.showMessageDialog(null, "Não rolou atualização!", "Deu ruim", 0);
		} else {
			if (atualizarCadastroVilao(vilao)) {
				JOptionPane.showMessageDialog(null, "Atualização do cadastro efetuada com sucesso!", "Nóis memo", 2);
			} else
				JOptionPane.showMessageDialog(null, "Não rolou atualização!", "Deu ruim", 0);
		}
		atualizaNumPrisioneiros(LoginController.getInstance().getIdUsuario());
		disposeTelaCadastroVilao();
		MenuVilaoController.getInstance().atualizarTelaMenuVilao();
	}

	private void novoCadastroVilao() throws NumberFormatException, ParseException {
		vilao = new Vilao();
		vilao.setId(Integer.parseInt(telaCadastroVilao.getTxtId().getText().toString()));
		vilao.setNome(telaCadastroVilao.getTxtNome().getText());
		vilao.setPeso(Integer.parseInt(telaCadastroVilao.getTxtPeso().getText().toString()));
		vilao.setAltura(Integer.parseInt(telaCadastroVilao.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			setFotoVilao(new ImageIcon(Constants.DEFAULT_PATH + "desconhecido.jpg").getImage());
			vilao.setFotoByteVilao(getFotoVilaoToByte(getFotoVilao()));
		} else vilao.setFotoByteVilao(getFotoVilaoToByte(getNovaFotoVilao()));
		vilao.setStatus(telaCadastroVilao.getTxtStatus().getText());
		vilao.setEstadoFisico(telaCadastroVilao.getTxtEstado().getText());
		//TODO
		vilao.setAtaque(15);
		vilao.setDefesa(10);
		vilao.setVida(100);
		
		if (telaCadastroVilao.getTxtStatus().getText().equals("Preso")) {
			vilao.setIdPolicia(LoginController.getInstance().getIdUsuario());
			if (cadastrarPresidiario(vilao)) {
				JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Nóis memo", 2);
			} else
				JOptionPane.showMessageDialog(null, "Não cadastrou!", "Deu ruim", 1);
		} else {
			if (cadastrarVilao(vilao)) {
				JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Nóis memo", 2);
			} else
				JOptionPane.showMessageDialog(null, "Não cadastrou!", "Deu ruim", 1);
		}
		atualizaNumPrisioneiros(LoginController.getInstance().getIdUsuario());
		disposeTelaCadastroVilao();
		MenuVilaoController.getInstance().atualizarTelaMenuVilao();
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
			telaCadastroVilao.getLblFoto().setIcon(new ImageIcon(getNovaFotoVilao()));
		}
	}

	private Image getNovaFotoVilao() {
		setFotoVilao(new ImageIcon(getEscolherArquivo().getSelectedFile().getPath()).getImage());
		return getFotoVilao();
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
	
	private boolean cadastrarVilao(Vilao vilao) {
		return vilaoDao.cadastrar(vilao);
	}
	private boolean atualizarCadastroVilao(Vilao vilao) {
		return vilaoDao.editar(vilao);
	}
	private boolean cadastrarPresidiario(Vilao presidiario) {
		return vilaoDao.cadastrarPresidiario(presidiario);
	}
	private boolean atualizarCadastroPresidiario(Vilao presidiario) {
		return vilaoDao.editarPresidiario(presidiario);		
	}
	private void atualizaNumPrisioneiros(int idPolicia) {
		PoliciaDAO.getInstance().setNumPrisioneiros(vilaoDao.getNumPrisioneiros(idPolicia), idPolicia);
	}
	private Integer getProxId() {
		return vilaoDao.getProxId();
	}
}
