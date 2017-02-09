package br.com.inatel.ec206.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.inatel.ec206.model.entity.Arma;

public class TabelaArma extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// constantes que vão representar as colunas
	private final int COL_ID = 0;
	private final int COL_NOME = 1;
	private final int COL_ATK = 2;
	private final int COL_DEF = 3;

	// lista dos policiais que serão exibidos
	private List<Arma> arma;

	public TabelaArma() {
		arma = new ArrayList<>();
	}

	public TabelaArma(List<Arma> lista) {
		this();
		arma.addAll(lista);
	}

	public int getRowCount() {
		// cada policia na lista será uma linha
		return arma.size();
	}

	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		// qual o nome da coluna
		if (column == COL_ID) {
			return "ID";
		} else if (column == COL_NOME) {
			return "NOME";
		} else if (column == COL_ATK) {
			return "ATAQUE";
		} else if (column == COL_DEF) {
			return "DEFESA";
		} 
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// retorna a classe que representa a coluna
		if (columnIndex == COL_ID) {
			return Integer.class;
		} else if (columnIndex == COL_NOME) {
			return String.class;
		} else if (columnIndex == COL_ATK) {
			return Integer.class;
		} else if (columnIndex == COL_DEF) {
			return Integer.class;
		} 
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// pega o policial da linha
		Arma a = arma.get(rowIndex);

		// verifica qual valor deve ser retornado
		if (columnIndex == COL_ID) {
			return a.getIdArma();
		} else if (columnIndex == COL_NOME) {
			return a.getNomeArma();
		} else if (columnIndex == COL_ATK) {
			return a.getAtaque();
		} else if (columnIndex == COL_DEF) {
			return a.getDefesa();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// pega o policial da linha
		Arma a = arma.get(rowIndex);

		// verifica qual valor vai ser alterado
		if (columnIndex == COL_ID) {
			a.setIdArma(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_NOME) {
			a.setNomeArma(aValue.toString());
		} else if (columnIndex == COL_ATK) {
			a.setAtaque(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_DEF) {
			a.setDefesa(Integer.parseInt(aValue.toString()));
		} 
		// avisa que os dados mudaram
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
