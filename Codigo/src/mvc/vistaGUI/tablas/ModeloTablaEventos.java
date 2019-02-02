package mvc.vistaGUI.tablas;

import java.util.List;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class ModeloTablaEventos extends ModeloTabla<Evento> {

// CONSTRUCTORA:
	
	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		this.lista = ctrl.getListEventos(); // Cargamos la lista con los eventos leidos
	}


// METODOS:
	
	@Override // Necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = indiceFil; break;
			case 1: s = this.lista.get(indiceFil).getTiempo(); break;
			case 2: s = this.lista.get(indiceFil).toString(); break;
		}
		return s;
	}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos; this.fireTableStructureChanged();
	}
	
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos; this.fireTableStructureChanged();
	}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = eventos; this.fireTableStructureChanged();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}

}


	
