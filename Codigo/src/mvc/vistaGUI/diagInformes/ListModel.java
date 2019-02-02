package mvc.vistaGUI.diagInformes;

import java.util.List;

import javax.swing.DefaultListModel;

@SuppressWarnings("serial")
public class ListModel<T> extends DefaultListModel<T> {

// ATRIBUTOS:
	
	private List<T> lista;
	
// CONSTRUCTORA:
	
	ListModel() { this.lista = null; }
	

// METODOS:
	
	public void setList(List<T> lista) {
		this.lista = lista;
		fireContentsChanged(this, 0, this.lista.size());
	}
	
	@Override
	public T getElementAt(int index) {
		return lista.get(index);
	}
	
	@Override
	public int getSize() {
		return this.lista == null ? 0 : this.lista.size();
	}
}
