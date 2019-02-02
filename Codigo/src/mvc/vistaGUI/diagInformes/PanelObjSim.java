package mvc.vistaGUI.diagInformes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class PanelObjSim<T> extends JPanel{

// ATRIBUTOS:
	
	private ListModel<T> listModel;
	private JList<T> objList;
	

// CONSTRUCTORA:
	
	public PanelObjSim(String titulo) {
		
		this.listModel = new ListModel<T>();
		this.objList = new JList<T>(this.listModel);
		
		this.setLayout(new GridLayout(1,1));
		this.setBorder(BorderFactory.createTitledBorder(titulo));
		
		objList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		objList.setFixedCellHeight(20);
		objList.setFixedCellWidth(140);
		
		this.addCleanSelectionListner(objList);
		this.add(new JScrollPane(objList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
					 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
	}
	

// METODOS:
	
	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {
			// limpiar la seleccion de items pulsando “c”
			// selecciona todo al pulsar la tecla "a"
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == DialogoInformes.TECLALIMPIAR)
					list.clearSelection();
				if(e.getKeyChar() == DialogoInformes.TECLA_SELECCIONAR_TODO){
					list.addSelectionInterval(0, listModel.getSize() - 1);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	public List<T> getSelectedItems() {
		List<T> l = new ArrayList<>();
		for (int i : this.objList.getSelectedIndices()) {
			l.add(listModel.getElementAt(i));
		}
		return l;
	}
	
	public void setList(List<T> lista) { this.listModel.setList(lista);}
	
	public boolean isEmpty(){
		return (listModel.getSize() == 0);
	}



	
}
