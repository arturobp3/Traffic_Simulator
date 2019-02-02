package mvc.vistaGUI.tablas;

import java.util.List;

import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
	}

	@Override // Necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0: s = this.lista.get(indiceFil).getIdentificador(); break;
			case 1: s = this.lista.get(indiceFil).getCarretera(); break; // Carretera actual
			case 2: s = this.lista.get(indiceFil).getLoc(); break; // Localizacion
			case 3: s = this.lista.get(indiceFil).getVelocidad(); break; 	// Velocidad actual
			case 4: s = this.lista.get(indiceFil).getKilometraje(); break; 	// Kilometraje
			case 5: s = this.lista.get(indiceFil).getTiempoAveria(); break; // Tiempo averia
			case 6: s = this.lista.get(indiceFil).getItinerario(); break; // Itinerario
		}
		return s;
	}
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos(); this.fireTableStructureChanged();
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos(); this.fireTableStructureChanged();

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.lista = mapa.getVehiculos(); this.fireTableStructureChanged();

	}

}
