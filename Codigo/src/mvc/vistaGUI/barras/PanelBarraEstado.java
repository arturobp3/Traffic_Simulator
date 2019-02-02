package mvc.vistaGUI.barras;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico {

// ATRIBUTOS: 
	
	private JLabel infoEjecucion;
	private JProgressBar pb;
	
	
// CONSTRUCTORA:
	
	public PanelBarraEstado(String mensaje, Controlador controlador, JProgressBar pb) {
		this.setLayout(new GridLayout(2,2));
		this.pb = pb;
		this.infoEjecucion = new JLabel(mensaje);
		this.add(this.infoEjecucion);
		this.add(this.pb);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controlador.addObserver(this);
	}
	
	public void setMensaje(String mensaje) {
		// La ventana principal se comunica con el panel
		this.infoEjecucion.setText(mensaje);
	}
	//...
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		
		this.infoEjecucion.setText("Paso: " + tiempo + " del Simulador");
	}
	
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		
		this.infoEjecucion.setText("Evento añadido al simulador");
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Se ha reiniciado el simulador");
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		this.infoEjecucion.setText("Se ha producido un error en el simulador");
	}
}
