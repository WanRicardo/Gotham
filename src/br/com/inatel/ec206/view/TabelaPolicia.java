package br.com.inatel.ec206.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.inatel.ec206.model.entity.Policia;

public class TabelaPolicia extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// constantes que vão representar as colunas
	private final int COL_ID = 0;
	private final int COL_NOME = 1;
	private final int COL_PESO = 2;
	private final int COL_ALTURA = 3;
	private final int COL_CARGO = 4;
	private final int COL_PRISIONEIROS = 5;
	
	// lista dos policiais que serão exibidos
	private List<Policia> policia;

	public TabelaPolicia() {
		policia = new ArrayList<>();
	}

	public TabelaPolicia(List<Policia> lista) {
		this();
		policia.addAll(lista);
	}

	public int getRowCount() {
		// cada policia na lista será uma linha
		return policia.size();
	}

	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int column) {
		// qual o nome da coluna
		if (column == COL_ID) {
			return "ID";
		} else if (column == COL_NOME) {
			return "NOME";
		} else if (column == COL_ALTURA) {
			return "ALTURA (cm)";
		} else if (column == COL_PESO) {
			return "PESO (kg)";
		} else if (column == COL_CARGO) {
			return "CARGO";
		} else if (column == COL_PRISIONEIROS) {
			return "QTD. PRISÕES";
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
		} else if (columnIndex == COL_ALTURA) {
			return Integer.class;
		} else if (columnIndex == COL_PESO) {
			return Integer.class;
		} else if (columnIndex == COL_CARGO) {
			return String.class;
		} else if (columnIndex == COL_PRISIONEIROS) {
			return Integer.class;
		}
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// pega o policial da linha
		Policia p = policia.get(rowIndex);

		// verifica qual valor deve ser retornado
		if (columnIndex == COL_ID) {
			return p.getId();
		} else if (columnIndex == COL_NOME) {
			return p.getNome();
		} else if (columnIndex == COL_ALTURA) {
			return p.getAltura();
		} else if (columnIndex == COL_PESO) {
			return p.getPeso();
		} else if (columnIndex == COL_CARGO) {
			return p.getCargo();
		} else if (columnIndex == COL_PRISIONEIROS) {
			return p.getNumPrisioneiros();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// pega o policial da linha
		Policia p = policia.get(rowIndex);

		// verifica qual valor vai ser alterado
		if (columnIndex == COL_ID) {
			p.setId(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_NOME) {
			p.setNome(aValue.toString());
		} else if (columnIndex == COL_ALTURA) {
			p.setAltura(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_PESO) {
			p.setPeso(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_CARGO) {
			p.setCargo(aValue.toString());
		} else if (columnIndex == COL_PRISIONEIROS) {
			p.setNumPrisioneiros(Integer.parseInt(aValue.toString()));
		}
		// avisa que os dados mudaram
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
