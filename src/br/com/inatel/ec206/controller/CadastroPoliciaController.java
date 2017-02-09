package br.com.inatel.ec206.controller;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import br.com.inatel.ec206.model.dao.LoginDAO;
import br.com.inatel.ec206.model.dao.PoliciaDAO;
import br.com.inatel.ec206.model.entity.Policia;
import br.com.inatel.ec206.view.TelaCadastroPolicia;

public class CadastroPoliciaController implements ActionListener {
	private static CadastroPoliciaController INSTANCE;
	private TelaCadastroPolicia telaCadastroPolicia;
	private PoliciaDAO policiaDao = PoliciaDAO.getInstance();
	private LoginDAO loginDao = LoginDAO.getInstance();
	private JFileChooser escolherArquivo;
	private FileNameExtensionFilter filtroExtensao;
	private Policia policial;
	private Image fotoPolicia;
	private byte[] imgPoliciaByte;
	private boolean novaFoto = false;
	private BufferedImage imgBufferizada;
	private Graphics2D g2d;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;

	public Image getFotoPolicia() {
		return fotoPolicia;
	}
	public void setFotoPolicia(Image fotoPolicia) {
		this.fotoPolicia = fotoPolicia;
	}

	private CadastroPoliciaController() {}
	
	public static synchronized CadastroPoliciaController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CadastroPoliciaController();
		}
		return INSTANCE;
	}
	
	public TelaCadastroPolicia newTelaCadastroPolicia() throws ParseException {
		telaCadastroPolicia = new TelaCadastroPolicia();
		inicializaListeners();
		telaCadastroPolicia.getTxtId().setText(getProxId().toString());
		telaCadastroPolicia.setVisible(true);
		return telaCadastroPolicia;
	}
	
	public void disposeTelaCadastroPolicia() {
		this.telaCadastroPolicia.dispose();
	}
	
	private Image getFotoCadastradaDoBancoPolicia(Blob foto) {
		try {
			imgPoliciaByte = foto.getBytes(1, (int) foto.length());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar imgVilaoByte");
			e.printStackTrace();
		}
		setFotoPolicia(new ImageIcon(imgPoliciaByte).getImage());
		return getFotoPolicia();
	}
	
	public void editarCadastroPolicia(int id, int idAdmin, String nome, int peso, int altura, Blob foto, String cargo, String usuario, String senha) throws ParseException {
		telaCadastroPolicia = new TelaCadastroPolicia();
		if (idAdmin != 0) {
			telaCadastroPolicia.getIsAdm().setSelected(true);
		}
		else {
			telaCadastroPolicia.getIsAdm().setSelected(false);
		}
		telaCadastroPolicia.getTxtId().setText(String.valueOf(id));
		telaCadastroPolicia.getTxtId().setEditable(false);
		telaCadastroPolicia.getTxtNome().setText(nome);
		telaCadastroPolicia.getTxtPeso().setText(String.valueOf(peso));
		telaCadastroPolicia.getTxtAltura().setText(String.valueOf(altura));
		telaCadastroPolicia.getLblFoto().setIcon(new ImageIcon(getFotoCadastradaDoBancoPolicia(foto)));
		telaCadastroPolicia.getTxtCargo().setText(cargo);
		telaCadastroPolicia.getTxtUsuario().setText(usuario);
		telaCadastroPolicia.getTxtSenha().setText(senha);
		inicializaListeners();
		telaCadastroPolicia.getBtnOk().setText("Atualizar");
		telaCadastroPolicia.getBtnOk().setActionCommand("Atualizar");
		telaCadastroPolicia.setVisible(true);
	}
	
	public void inicializaListeners() {
		telaCadastroPolicia.getBtnOk().setActionCommand("Inserir");
		telaCadastroPolicia.getBtnOk().addActionListener(this);
		telaCadastroPolicia.getBtnSair().addActionListener(this);
		telaCadastroPolicia.getBtnFoto().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals("Atualizar")) {
			try {
				editarPolicia();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getActionCommand().equals("Inserir")) {
			try {
				if (telaCadastroPolicia.getTxtCargo().getText().trim().equals("")
						|| telaCadastroPolicia.getTxtNome().getText().trim().equals("")
						|| telaCadastroPolicia.getTxtPeso().getText().trim().equals("")
						|| telaCadastroPolicia.getTxtAltura().getText().trim().equals("")
						|| telaCadastroPolicia.getTxtUsuario().getText().trim().equals("")
						|| telaCadastroPolicia.getTxtSenha().getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				} else {
					try {
						novoCadastro();
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (HeadlessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getSource() == telaCadastroPolicia.getBtnSair()) {
			disposeTelaCadastroPolicia();
		} else if (evento.getSource() == telaCadastroPolicia.getBtnFoto()) {
			getFotoSelecionada();
		}
	}

	private void editarPolicia() throws NumberFormatException, ParseException {
		policial = new Policia();
		policial.setId(Integer.parseInt(telaCadastroPolicia.getTxtId().getText().toString()));
		policial.setNome(telaCadastroPolicia.getTxtNome().getText());
		policial.setPeso(Integer.parseInt(telaCadastroPolicia.getTxtPeso().getText().toString()));
		policial.setAltura(Integer.parseInt(telaCadastroPolicia.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			policial.setFotoBytePolicia(getFotoPoliciaToByte(getFotoPolicia()));
		} else policial.setFotoBytePolicia(getFotoPoliciaToByte(getNovaFotoPolicia()));
		policial.setCargo(telaCadastroPolicia.getTxtCargo().getText());
		int idPolicia = Integer.parseInt(telaCadastroPolicia.getTxtId().getText().toString());
		String usuario = telaCadastroPolicia.getTxtUsuario().getText();
		String senha = telaCadastroPolicia.getTxtSenha().getText();
		if (atualizarCadastroPolicia(policial) && atualizarLoginPolicia(idPolicia, usuario, senha)) {
			JOptionPane.showMessageDialog(null, "Atualização do cadastro efetuada com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não rolou atualização!", "Deu ruim", 0);
		
		if (telaCadastroPolicia.getIsAdm().isSelected()) {
			setIsAdmin(idPolicia);
		} else setIsNotAdmin(idPolicia);
		disposeTelaCadastroPolicia();
		MenuPoliciaController.getInstance().atualizarTelaMenuPolicia();
	}

	private void novoCadastro() throws NumberFormatException, ParseException {
		policial = new Policia();
		policial.setId(Integer.parseInt(telaCadastroPolicia.getTxtId().getText().toString()));
		policial.setNome(telaCadastroPolicia.getTxtNome().getText());
		policial.setPeso(Integer.parseInt(telaCadastroPolicia.getTxtPeso().getText().toString()));
		policial.setAltura(Integer.parseInt(telaCadastroPolicia.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			setFotoPolicia(new ImageIcon(Constants.DEFAULT_PATH + "desconhecido.jpg").getImage());
			policial.setFotoBytePolicia(getFotoPoliciaToByte(getFotoPolicia()));
		} else policial.setFotoBytePolicia(getFotoPoliciaToByte(getNovaFotoPolicia()));
		policial.setCargo(telaCadastroPolicia.getTxtCargo().getText());
		int idPolicia = Integer.parseInt(telaCadastroPolicia.getTxtId().getText().toString());
		String usuario = telaCadastroPolicia.getTxtUsuario().getText();
		String senha = telaCadastroPolicia.getTxtSenha().getText();
		
		if (cadastrarPolicia(policial) && cadastraLogin(idPolicia, usuario, senha)) {
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não cadastrou!", "Deu ruim", 1);
		
		if (telaCadastroPolicia.getIsAdm().isSelected()) {
			setIsAdmin(idPolicia);
		}
		disposeTelaCadastroPolicia();
		MenuPoliciaController.getInstance().atualizarTelaMenuPolicia();
	}
	
	//transforma a imagem em um array de bytes para armazenar no banco
	private ByteArrayInputStream getFotoPoliciaToByte(Image fotoPolicia) {
		imgBufferizada = new BufferedImage(fotoPolicia.getWidth(null), fotoPolicia.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g2d = imgBufferizada.createGraphics();
		g2d.drawImage(fotoPolicia, 0, 0, null);
		g2d.dispose();
		baos = null;
		try {
		    baos = new ByteArrayOutputStream();
		    ImageIO.write(imgBufferizada, "jpg", baos);
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
			telaCadastroPolicia.getLblFoto().setIcon(new ImageIcon(getNovaFotoPolicia()));
		}
	}
	
	private Image getNovaFotoPolicia() {
		setFotoPolicia(new ImageIcon(getEscolherArquivo().getSelectedFile().getPath()).getImage());
		return getFotoPolicia();
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
	
	private boolean cadastrarPolicia(Policia policia) {
		return policiaDao.cadastrar(policia);
	}
	private void setIsAdmin(int idPolicia) {
		policiaDao.setIsAdmin(idPolicia);
	}
	private void setIsNotAdmin(int idPolicia) {
		policiaDao.setIsNotAdmin(idPolicia);
	}
	private boolean cadastraLogin(int idPolicia, String usuario, String senha) {
		return loginDao.cadastrarLogin(idPolicia, usuario, senha);
	}
	private boolean atualizarCadastroPolicia(Policia policia) {
		return policiaDao.editarPolicia(policia);
	}
	private boolean atualizarLoginPolicia(int idPolicia, String usuario, String senha) {
		return loginDao.atualizarLogin(idPolicia, usuario, senha);
	}
	private Integer getProxId() {
		return policiaDao.getProxId();
	}
}
