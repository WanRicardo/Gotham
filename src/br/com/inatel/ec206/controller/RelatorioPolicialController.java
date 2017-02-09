package br.com.inatel.ec206.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.model.Constants;
import br.com.inatel.ec206.model.dao.HeroiDAO;
import br.com.inatel.ec206.model.dao.PoliciaDAO;
import br.com.inatel.ec206.model.dao.VilaoDAO;
import br.com.inatel.ec206.model.entity.Heroi;
import br.com.inatel.ec206.model.entity.Policia;
import br.com.inatel.ec206.model.entity.Vilao;
import br.com.inatel.ec206.view.TelaRelatorioPolicial;

public class RelatorioPolicialController implements ActionListener {

	private static RelatorioPolicialController INSTANCE;
	private PoliciaDAO policiaDao = PoliciaDAO.getInstance();
	private TelaRelatorioPolicial telaRelatorioPolicial;
	private String relatorio;
	private Path caminho;
	private Charset utf8;

	private RelatorioPolicialController() {}
	
	public static synchronized RelatorioPolicialController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RelatorioPolicialController();
		}
		return INSTANCE;
	}
	
	public TelaRelatorioPolicial newTelaRelatorioPolicial() {
		telaRelatorioPolicial = new TelaRelatorioPolicial();
		leRelatorio();
		telaRelatorioPolicial.setTitle(getData() + "  -   " + relatorio);
		telaRelatorioPolicial.getBtnFechar().addActionListener(this);
		telaRelatorioPolicial.setVisible(true);
		return telaRelatorioPolicial;
	}
	
	public void disposeTelaRelatorio() {
		telaRelatorioPolicial.dispose();
	}

	public void actionPerformed(ActionEvent evento) {
		disposeTelaRelatorio();
	}
	
	private String getData() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		DateFormat f = DateFormat.getDateInstance();
		return f.format(date);
	}
	
	public void escreveRelatorio() {
		relatorio = "Relatorio_Policial.txt";
		caminho = Paths.get(Constants.DEFAULT_PATH_RELATORIO + relatorio);
		utf8 = StandardCharsets.UTF_8;

		try (BufferedWriter w = Files.newBufferedWriter(caminho, utf8))	{		//Buffered é um depósito de dados na memória do pc
			for (Policia policia : getListaPoliciaisCadastrados()) {
				w.write("===============================================================" + 
						"\nId: " + policia.getId());
				if (policia.getIdAdmin() == policia.getId()) {
					w.write("\t\tAdministrador");
				}
				w.write("\nNome: " + policia.getNome() +
						"\nPeso: " + policia.getPeso() + " kg" +
						"\nAltura: " + policia.getAltura() + " cm" +
						"\nCargo: " + policia.getCargo() + 
						"\nNumero de prisoes efetuada: " + policia.getNumPrisioneiros());
				if (policia.getNumPrisioneiros() != 0) {
					w.write("\nNome prisioneiros:");
					for (Vilao vilao : getPrisioneiros(policia.getId())) {
						w.write("\n\t- " + vilao.getNome() + ".");
					}
				}
				w.write("\nHerois cadastrados:");
				for (Heroi heroi : getHerois(policia.getId())) {
					w.write("\n\t- " + heroi.getNome() + ".");
				}
				w.write("\n\n");
			}
			w.write("\t\t\t" + new Date());
			JOptionPane.showMessageDialog(null, "Relatório pronto!", "Sucesso", 2);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Não foi possível escrever no relatório");
			e.printStackTrace();
		}
	}
	
	public void leRelatorio() {
		relatorio = "Relatorio_Policial.txt";
		caminho = Paths.get(Constants.DEFAULT_PATH_RELATORIO + relatorio);
		File file = new File(caminho.toString());
		FileInputStream fis = null;
		String texto = "";
//		utf8 = StandardCharsets.UTF_8;
		
		try {
			fis = new FileInputStream(file);
            int content;
            while ((content = fis.read()) != -1) {
                texto += (char) content;
            }
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		telaRelatorioPolicial.getTxtAreaRelatorio().setText(texto);
//		try(BufferedReader r = Files.newBufferedReader(caminho, utf8))
//		{
//			String line = null;
//			while((line = r.readLine()) != null)
//			{
//				telaRelatorioPolicial.getTxtAreaRelatorio().setText(line);
//				System.out.println(line);
//			}
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
	}

//        textArea.setLineWrap(true); //quebra de linha automática
	
	private List<Policia> getListaPoliciaisCadastrados() {
		return policiaDao.getTodos();
	}
	private List<Vilao> getPrisioneiros(int idPolicia) {
		return VilaoDAO.getInstance().getListaPrisioneiros(idPolicia);
	}
	private List<Heroi> getHerois(int idPolicia) {
		return HeroiDAO.getInstance().getListaHerois(idPolicia);
	}
}
