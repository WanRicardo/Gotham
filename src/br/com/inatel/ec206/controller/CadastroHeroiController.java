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
import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.view.TelaCadastroHeroi;

public class CadastroHeroiController implements ActionListener {
	private static CadastroHeroiController INSTANCE;
	private HeroiDAO heroiDao = HeroiDAO.getInstance();
	private Heroi heroi;
	private TelaCadastroHeroi telaCadastroHeroi;
	private JFileChooser escolherArquivo = null;
	private FileNameExtensionFilter filtroExtensao = null;
	private Image fotoHeroi;
	private byte[] imgHeroiByte = null;
	private boolean novaFoto = false;
	private BufferedImage imgBufferizada;
	private Graphics2D g2d;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	
	public Image getFotoHeroi() {
		return fotoHeroi;
	}
	public void setFotoHeroi(Image fotoHeroi) {
		this.fotoHeroi = fotoHeroi;
	}

	private CadastroHeroiController() {}
	
	public static synchronized CadastroHeroiController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CadastroHeroiController();
		}
		return INSTANCE;
	}
	
	public TelaCadastroHeroi newTelaCadastroHeroi() {
		telaCadastroHeroi = new TelaCadastroHeroi();
		inicializaListeners();
		telaCadastroHeroi.getTxtId().setText(getProxId().toString());
		telaCadastroHeroi.setVisible(true);
		return telaCadastroHeroi;
	}
	
	public void disposeTelaCadastroHeroi() {
		this.telaCadastroHeroi.dispose();
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

	public void editarCadastroHeroi(int id, String nome, int peso, int altura, Blob foto, int atk, int def, int vida,
			String status, String estado, String tendencia) throws ParseException {
		telaCadastroHeroi = new TelaCadastroHeroi();
		telaCadastroHeroi.getTxtId().setText(String.valueOf(id));
		telaCadastroHeroi.getTxtId().setEditable(false);
		telaCadastroHeroi.getTxtNome().setText(nome);
		telaCadastroHeroi.getTxtPeso().setText(String.valueOf(peso));
		telaCadastroHeroi.getTxtAltura().setText(String.valueOf(altura));
		telaCadastroHeroi.getLblFoto().setIcon(new ImageIcon(getFotoCadastradaDoBancoHeroi(foto)));
		telaCadastroHeroi.getLblAtkDef().setText(String.valueOf(atk) + "/" + String.valueOf(def));
		telaCadastroHeroi.getLblVida().setText(String.valueOf(vida));
		telaCadastroHeroi.getTxtStatus().setText(status);
		telaCadastroHeroi.getTxtEstado().setText(estado);
		telaCadastroHeroi.getTxtTendencia().setText(tendencia);
		inicializaListeners();
		telaCadastroHeroi.getBtnOk().setText("Atualizar");
		telaCadastroHeroi.getBtnOk().setActionCommand("Atualizar");
		telaCadastroHeroi.setVisible(true);
	}
	
	private void inicializaListeners() {
		telaCadastroHeroi.getBtnOk().setActionCommand("Inserir");
		telaCadastroHeroi.getBtnOk().addActionListener(this);
		telaCadastroHeroi.getBtnSair().addActionListener(this);
		telaCadastroHeroi.getBtnFoto().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getActionCommand().equals("Atualizar")) {
			try {
				editarHeroi();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (evento.getActionCommand().equals("Inserir")) {
			try {
				if (telaCadastroHeroi.getTxtNome().getText().trim().equals("")
						|| telaCadastroHeroi.getTxtPeso().getText().trim().equals("")
						|| telaCadastroHeroi.getTxtAltura().getText().trim().equals("")
						|| telaCadastroHeroi.getTxtEstado().getText().trim().equals("")
						|| telaCadastroHeroi.getTxtStatus().getText().trim().equals("")
						|| telaCadastroHeroi.getTxtTendencia().getText().trim().equals("")) {
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
		} else if (evento.getSource() == telaCadastroHeroi.getBtnSair()) {
			disposeTelaCadastroHeroi();
		} else if (evento.getSource() == telaCadastroHeroi.getBtnFoto()) {
			getFotoSelecionada();
		}
	}

	private void editarHeroi() throws NumberFormatException, ParseException {
		heroi = new Heroi();
		heroi.setId(Integer.parseInt(telaCadastroHeroi.getTxtId().getText().toString()));
		heroi.setNome(telaCadastroHeroi.getTxtNome().getText());
		heroi.setPeso(Integer.parseInt(telaCadastroHeroi.getTxtPeso().getText().toString()));
		heroi.setAltura(Integer.parseInt(telaCadastroHeroi.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			heroi.setFotoByteHeroi(getFotoHeroiToByte(getFotoHeroi()));
		} else heroi.setFotoByteHeroi(getFotoHeroiToByte(getNovaFotoHeroi()));
		heroi.setStatusHeroi(telaCadastroHeroi.getTxtStatus().getText());
		heroi.setEstadoFisico(telaCadastroHeroi.getTxtEstado().getText());
		heroi.setTendenciaMoral(telaCadastroHeroi.getTxtTendencia().getText());

		if (atualizarCadastroHeroi(heroi)) {
			JOptionPane.showMessageDialog(null, "Atualização do cadastro efetuada com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não rolou atualização!", "Deu ruim", 0);
		disposeTelaCadastroHeroi();
		MenuHeroiController.getInstance().atualizarTelaMenuHeroi();
	}

	private void novoCadastro() throws NumberFormatException, ParseException {
		heroi = new Heroi();
//		heroi.setId(Integer.parseInt(telaCadastroHeroi.getTxtId().getText().toString()));
		heroi.setNome(telaCadastroHeroi.getTxtNome().getText());
		heroi.setPeso(Integer.parseInt(telaCadastroHeroi.getTxtPeso().getText().toString()));
		heroi.setAltura(Integer.parseInt(telaCadastroHeroi.getTxtAltura().getText().toString()));
		if (!novaFoto) {
			setFotoHeroi(new ImageIcon(Constants.DEFAULT_PATH + "desconhecido.jpg").getImage());
			heroi.setFotoByteHeroi(getFotoHeroiToByte(getFotoHeroi()));
		} else heroi.setFotoByteHeroi(getFotoHeroiToByte(getNovaFotoHeroi()));
		heroi.setStatusHeroi(telaCadastroHeroi.getTxtStatus().getText());
		heroi.setEstadoFisico(telaCadastroHeroi.getTxtEstado().getText());
		heroi.setTendenciaMoral(telaCadastroHeroi.getTxtTendencia().getText());
		heroi.setIdPolicia(LoginController.getInstance().getIdUsuario());
		heroi.setAtk(15);
		heroi.setDef(10);
		heroi.setVida(100);
		
		if (cadastrarHeroi(heroi)) {
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!", "Nóis memo", 2);
		} else
			JOptionPane.showMessageDialog(null, "Não cadastrou!", "Deu ruim", 1);
		disposeTelaCadastroHeroi();
		MenuHeroiController.getInstance().atualizarTelaMenuHeroi();
	}
	
	//transforma a imagem em um array de bytes para armazenar no banco
	private ByteArrayInputStream getFotoHeroiToByte(Image fotoHeroi) {
		imgBufferizada = new BufferedImage(fotoHeroi.getWidth(null), fotoHeroi.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		g2d = imgBufferizada.createGraphics();
		g2d.drawImage(fotoHeroi, 0, 0, null);
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
			telaCadastroHeroi.getLblFoto().setIcon(new ImageIcon(getNovaFotoHeroi()));
		}
	}
	
	private Image getNovaFotoHeroi() {
		setFotoHeroi(new ImageIcon(getEscolherArquivo().getSelectedFile().getPath()).getImage());
		return getFotoHeroi();
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
	
	private boolean cadastrarHeroi(Heroi heroi) {
		return heroiDao.cadastrar(heroi);
	}

	private boolean atualizarCadastroHeroi(Heroi heroi) {
		return heroiDao.editarHeroi(heroi);
	}
	private Integer getProxId() {
		return heroiDao.getProxId();
	}
}
