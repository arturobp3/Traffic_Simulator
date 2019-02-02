package mvc.vistaGUI.panelSuperior;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class PanelAreaTexto extends JPanel {

// ATRIBUTOS:
	
	protected JTextArea areatexto;

// CONSTRUCTORA:
	
	public PanelAreaTexto(String titulo, boolean editable) {
		this.setLayout(new GridLayout(1,1));
		this.areatexto = new JTextArea(40, 30);
		this.areatexto.setEditable(editable);
		this.add(areatexto);
		this.areatexto.setLineWrap(true); //Corta las palabras cuando llegan al final de la linea
		this.add(new JScrollPane(this.areatexto));
		this.setBorde(titulo);
	}

// METODOS:
	
	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}
	
	public String getTexto() {
		return this.areatexto.getText();
	}
	
	public void setTexto(String texto) {
		this.areatexto.setText(texto);
	}
	
	public void limpiar() {
		this.areatexto.setText(null);
	}
	
	public void inserta(String valor) {
		this.areatexto.insert(valor, this.areatexto.getCaretPosition());
	}
}
