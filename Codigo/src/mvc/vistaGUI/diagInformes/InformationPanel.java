package mvc.vistaGUI.diagInformes;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InformationPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel infoLinea1, infoLinea2, infoLinea3;

	
	public InformationPanel(){
		super();
		
		this.setLayout(new GridLayout(4, 1));
		
		infoLinea1 = new JLabel("  Selecciona los items de los cuales quieres generar reportes.");
		infoLinea2 = new JLabel("  Usa 'a' para seleccionar todos.");
		infoLinea3 = new JLabel("  Usa 'c' para deseleccionar todos.");
		
		this.add(infoLinea1);
		this.add(infoLinea2);
		this.add(infoLinea3);
	}
	
	
}
