package mvc.vistaGUI.tablas;

import java.util.List;

import entidades.cruces.CruceGenerico;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {

	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // Necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getIdentificador(); break;
			case 1: s = this.lista.get(indiceFil).getCrucesVerde(); break; // Aqui se cargan los cruces en verde
			case 2: s = this.lista.get(indiceFil).getCrucesRojo(); break; // Aqui en rojo
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces(); this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces(); this.fireTableStructureChanged();

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCruces(); this.fireTableStructureChanged();

	}

}
