package mvc.vistaGUI.tablas;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;

@SuppressWarnings("serial")
public abstract class ModeloTabla<T> extends DefaultTableModel implements ObservadorSimuladorTrafico {

// ATRIBUTOS:
	
	protected String[] columnIds;
	protected List<T> lista;

	
// CONSTRUCTORA:
	
	public ModeloTabla(String[] columnIdEventos, Controlador ctrl) {
		this.lista = null;
		this.columnIds = columnIdEventos;
		ctrl.addObserver(this);
	}
	

// METODOS:
	
	@Override
	public String getColumnName(int col) { return this.columnIds[col]; }
	
	@Override
	public int getColumnCount() { return this.columnIds.length; }
	
	@Override
	public int getRowCount() { return this.lista == null ? 0 : this.lista.size(); }
}
