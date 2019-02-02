package mvc.vistaGUI.panelSuperior;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import mvc.vistaGUI.VentanaPrincipal;
import constructorEventos.ConstructorEventos;
import constructorEventos.ParserEventos;


public class PopUpMenu extends JPopupMenu{

	private static final long serialVersionUID = 1L;

	public PopUpMenu(VentanaPrincipal mainWindow) throws HeadlessException {
		super();
		
		JMenu plantillas = new JMenu("Nueva plantilla");
		this.add(plantillas);
	
		
		// añadir las opciones con sus listeners
		for (ConstructorEventos ce : ParserEventos.getConstrutoresEventos()) {
			JMenuItem mi = new JMenuItem(ce.toString());
			
			mi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainWindow.inserta(ce.template() /*+ System.lineSeparator()*/);
				}

			});
			
			plantillas.add(mi);
			// String template() es un método público que debe definirse en la
			// clase ConstructorEventos, y que debe generar la plantilla
			// correspondiente en función de los campos, y teniendo en cuenta
			// los posibles valores por defecto.
		}
		
		this.addSeparator();
		
		JMenuItem load = new JMenuItem("Cargar fichero");
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.cargaFichero();
			}
			
		});
		
		JMenuItem save = new JMenuItem("Guardar fichero");
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.guardaEvento();
			}
			
		});
		
		JMenuItem clear = new JMenuItem("Limpiar Eventos");
		clear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainWindow.limpiarEditor();
			}
			
		});
		
		
		this.add(load);
		this.add(save);
		this.add(clear);
		
	}

}
