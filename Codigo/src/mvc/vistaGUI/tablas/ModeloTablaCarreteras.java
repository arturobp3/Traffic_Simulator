package mvc.vistaGUI.tablas;

import java.util.List;

import entidades.carreteras.Carretera;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class ModeloTablaCarreteras extends ModeloTabla<Carretera> {

	public ModeloTablaCarreteras(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		this.lista = ctrl.getListCarreteras();
	}

	@Override // Necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getIdentificador(); break;
			case 1: s = this.lista.get(indiceFil).getCruceOrigen().toString(); break; // El cruce origen
			case 2: s = this.lista.get(indiceFil).getCruceDestino().toString(); break; // El cruce destino
			case 3: s = this.lista.get(indiceFil).getLongitud(); break; // Longitud de la carretera
			case 4: s = this.lista.get(indiceFil).getVelMaxima(); break; // Velocidad maxima
			case 5: s = this.lista.get(indiceFil).getListaVehiculos(); break; // Vehiculos en la carretera
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); this.fireTableStructureChanged();

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getCarreteras(); this.fireTableStructureChanged();

	}

}
