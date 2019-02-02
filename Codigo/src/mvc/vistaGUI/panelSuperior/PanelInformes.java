package mvc.vistaGUI.panelSuperior;

import java.util.List;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico {

// CONSTRUCTORA:
	
	public PanelInformes(String titulo, boolean editable, Controlador ctrl) {
		super(titulo, editable);
		ctrl.addObserver(this); // se añade como observador
	}

	
// METODOS:
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
	}
}
