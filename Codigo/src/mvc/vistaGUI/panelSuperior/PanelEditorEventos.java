package mvc.vistaGUI.panelSuperior;

import mvc.vistaGUI.VentanaPrincipal;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


@SuppressWarnings("serial")
public class PanelEditorEventos extends PanelAreaTexto {

// CONSTRUCTORA:
	
	public PanelEditorEventos(String titulo, String texto, boolean editable, VentanaPrincipal mainWindow) {
		super(titulo, editable);
		this.setTexto(texto);
		
		// OPCIONAL
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		this.areatexto.add(popUp);
		this.areatexto.addMouseListener(new MouseListener() {
			//...
			@Override
			public void mousePressed(MouseEvent e) {
				if (areatexto.isEnabled()){
					popUp.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

}



