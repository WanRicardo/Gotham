package br.com.inatel.ec206.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.inatel.ec206.model.entity.Heroi;

public class TabelaHeroi extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	// constantes que vão representar as colunas
	private final int COL_ID = 0;
	private final int COL_NOME = 1;
	private final int COL_PESO = 2;
	private final int COL_ALTURA = 3;
	private final int COL_STATUS = 4;
	private final int COL_ESTADO = 5;
	private final int COL_TENDENCIA = 6;

	// lista dos policiais que serão exibidos
	private List<Heroi> heroi;

	public TabelaHeroi() {
		heroi = new ArrayList<>();
	}

	public TabelaHeroi(List<Heroi> lista) {
		this();
		heroi.addAll(lista);
	}

	public int getRowCount() {
		// cada policia na lista será uma linha
		return heroi.size();
	}

	public int getColumnCount() {
		return 7;
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
		} else if (column == COL_TENDENCIA) {
			return "TENDENCIA MORAL";
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
		} else if (columnIndex == COL_TENDENCIA) {
			return String.class;
		}
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// pega o policial da linha
		Heroi h = heroi.get(rowIndex);

		// verifica qual valor deve ser retornado
		if (columnIndex == COL_ID) {
			return h.getId();
		} else if (columnIndex == COL_NOME) {
			return h.getNome();
		} else if (columnIndex == COL_PESO) {
			return h.getPeso();
		} else if (columnIndex == COL_ALTURA) {
			return h.getAltura();
		} else if (columnIndex == COL_STATUS) {
			return h.getStatusHeroi();
		} else if (columnIndex == COL_ESTADO) {
			return h.getEstadoFisico();
		} else if (columnIndex == COL_TENDENCIA) {
			return h.getTendenciaMoral();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// pega o policial da linha
		Heroi v = heroi.get(rowIndex);

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
			v.setStatusHeroi(aValue.toString());
		} else if (columnIndex == COL_ESTADO) {
			v.setEstadoFisico(aValue.toString());
		} else if (columnIndex == COL_TENDENCIA) {
			v.setTendenciaMoral(aValue.toString());
		}
		// avisa que os dados mudaram
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
