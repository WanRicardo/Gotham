package br.com.inatel.ec206.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.inatel.ec206.model.entity.Vilao;

public class TabelaBandido extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// constantes que vão representar as colunas
	private final int COL_ID = 0;
	private final int COL_NOME = 1;
	private final int COL_PESO = 2;
	private final int COL_ALTURA = 3;
	private final int COL_STATUS = 4;
	private final int COL_ESTADO = 5;

	// lista dos policiais que serão exibidos
	private List<Vilao> bandido;

	public TabelaBandido() {
		bandido = new ArrayList<>();
	}

	public TabelaBandido(List<Vilao> lista) {
		this();
		bandido.addAll(lista);
	}

	public int getRowCount() {
		// cada policia na lista será uma linha
		return bandido.size();
	}

	public int getColumnCount() {
		//6 colunas no total
		return 6;
	}

	@Override
	public String getColumnName(int column) {
		// qual o nome da coluna
		if (column == COL_ID) {
			return "ID";
		} else if (column == COL_NOME) {
			return "NOME";
		} else if (column == COL_PESO) {
			return "PESO (kg)";
		} else if (column == COL_ALTURA) {
			return "ALTURA (cm)";
		} else if (column == COL_STATUS) {
			return "STATUS";
		} else if (column == COL_ESTADO) {
			return "ESTADO FISICO";
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
		} else if (columnIndex == COL_PESO) {
			return Integer.class;
		} else if (columnIndex == COL_ALTURA) {
			return Integer.class;
		} else if (columnIndex == COL_STATUS) {
			return String.class;
		} else if (columnIndex == COL_ESTADO) {
			return String.class;
		}
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// pega o policial da linha
		Vilao v = bandido.get(rowIndex);

		// verifica qual valor deve ser retornado
		if (columnIndex == COL_ID) {
			return v.getId();
		} else if (columnIndex == COL_NOME) {
			return v.getNome();
		} else if (columnIndex == COL_PESO) {
			return v.getPeso();
		} else if (columnIndex == COL_ALTURA) {
			return v.getAltura();
		} else if (columnIndex == COL_STATUS) {
			return v.getStatus();
		} else if (columnIndex == COL_ESTADO) {
			return v.getEstadoFisico();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// pega o policial da linha
		Vilao v = bandido.get(rowIndex);

		// verifica qual valor vai ser alterado
		if (columnIndex == COL_ID) {
			v.setId(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_NOME) {
			v.setNome(aValue.toString());
		} else if (columnIndex == COL_PESO) {
			v.setPeso(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_ALTURA) {
			v.setAltura(Integer.parseInt(aValue.toString()));
		} else if (columnIndex == COL_STATUS) {
			v.setStatus(aValue.toString());
		} else if (columnIndex == COL_ESTADO) {
			v.setEstadoFisico(aValue.toString());
		}
		// avisa que os dados mudaram
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
