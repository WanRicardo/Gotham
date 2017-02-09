package br.com.inatel.ec206.controller;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.com.inatel.ec206.model.Constants;
import br.com.inatel.ec206.model.dao.ArmaDAO;
import br.com.inatel.ec206.model.entity.Arma;
import br.com.inatel.ec206.view.TelaCadastroArma;

public class CadastroArmaController implements ActionListener {

	private static CadastroArmaController INSTANCE;
	private ArmaDAO armaDao = ArmaDAO.getInstance();
	private TelaCadastroArma telaCadastroArma;
	private JFileChooser escolherArquivo;
	private FileNameExtensionFilter filtroExtensao;
	private Image fotoArma;
	private Arma arma;
	private BufferedImage imgBufferizada;
	private Graphics2D g2d;
	private ByteArrayOutputStream baos;
	private ByteArrayInputStream bais;
	
	public Image getFotoArma() {
		return fotoArma;
	}
	public void setFotoArma(Image fotoArma) {
		this.fotoArma = fotoArma;
	}

	private CadastroArmaController() {}
	
	public static synchronized CadastroArmaController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CadastroArmaController();
		}
		return INSTANCE;
	}
	
	public void newTelaCadastroArma() throws ParseException {
		telaCadastroArma = new TelaCadastroArma();
		inicializaListeners();
		telaCadastroArma.setVisible(true);
	}
	
	public void disposeTelaCadastroArma() {
		telaCadastroArma.dispose();
	}
	
	private void inicializaListeners() {
		telaCadastroArma.getBtnAlterar().addActionListener(this);
		telaCadastroArma.getBtnOk().addActionListener(this);
		telaCadastroArma.getBtnSair().addActionListener(this);
	}

	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == telaCadastroArma.getBtnAlterar()) {
			getFotoSelecionada();
		} else if (evento.getSource() == telaCadastroArma.getBtnOk()) {
			try {
				if (telaCadastroArma.getTxtAtq().getText().trim().equals("")
						|| telaCadastroArma.getTxtDef().getText().trim().equals("")
						|| telaCadastroArma.getTxtNome().getText().trim().equals("")
						|| telaCadastroArma.getTxtPeso().getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				} else {
					try {
						novaArma();
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
		} else
			disposeTelaCadastroArma();
	}
	private void novaArma() throws NumberFormatException, ParseException {
		arma = new Arma();
		arma.setNomeArma(telaCadastroArma.getTxtNome().getText());
		arma.setPesoArma(Integer.parseInt(telaCadastroArma.getTxtPeso().getText()));
		arma.setAtaque(Integer.parseInt(telaCadastroArma.getTxtAtq().getText()));
		arma.setDefesa(Integer.parseInt(telaCadastroArma.getTxtDef().getText()));
		arma.setImgByteArma(getFotoArmaToByte(getNovaFotoArma()));
		if (cadastarArma(arma)) {
			JOptionPane.showMessageDialog(null, "Arma cadastrada com sucesso");
			disposeTelaCadastroArma();
		} else JOptionPane.showMessageDialog(null, "Falha no cadastro da arma");
	}
	
	private void getFotoSelecionada() {
		int state = getEscolherArquivo().showOpenDialog(null);
		if (state == JFileChooser.APPROVE_OPTION) {
			telaCadastroArma.getLblImgArma().setIcon(new ImageIcon(getNovaFotoArma()));
		}
	}
	
	private JFileChooser getEscolherArquivo() {
		if (escolherArquivo == null) {
			escolherArquivo = new JFileChooser(Constants.DEFAULT_PATH);
			escolherArquivo.setFileFilter(getFiltroExtensao());
			escolherArquivo.setMultiSelectionEnabled(false);
		}
		return escolherArquivo;
	}
	
	private FileNameExtensionFilter getFiltroExtensao() {
		if (filtroExtensao == null) {
			filtroExtensao = new FileNameExtensionFilter("JPG e PNG", new String[] { "jpg" , "png" });
		}
		return filtroExtensao;
	}
	
	private Image getNovaFotoArma() {
		setFotoArma(new ImageIcon(getEscolherArquivo().getSelectedFile().getPath()).getImage());
		return getFotoArma();
	}
	
	//transforma a imagem em um array de bytes para armazenar no banco
		private ByteArrayInputStream getFotoArmaToByte(Image fotoArma) {
			imgBufferizada = new BufferedImage(fotoArma.getWidth(null), fotoArma.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			g2d = imgBufferizada.createGraphics();
			g2d.drawImage(fotoArma, 0, 0, null);
			g2d.dispose();
			baos = null;
			try {
			    baos = new ByteArrayOutputStream();
			    ImageIO.write(imgBufferizada, "jpg", baos);
			} catch (Exception e1){
				JOptionPane.showMessageDialog(null, "Deu alguma zica pra transformar a imagem em array de bytes");
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
	
	public boolean cadastarArma(Arma arma) {
		return armaDao.cadastrar(arma);
	}
}
