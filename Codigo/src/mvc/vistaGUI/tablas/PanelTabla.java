package mvc.vistaGUI.tablas;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PanelTabla<T> extends JPanel {

// ATRIBUTOS:

	private ModeloTabla<T> modelo;

// CONSTRUCTORA:
	
	public PanelTabla(String bordeId, ModeloTabla<T> modelo) {
		this.setLayout(new GridLayout(1,1)); // Diap. 35
		Border b = BorderFactory.createTitledBorder(bordeId);
		this.setBorder(b); // Establecido borde
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		this.add(new JScrollPane(tabla)); // (Diap. 7, Apuntes GUI-2 Campus)
	}
}
