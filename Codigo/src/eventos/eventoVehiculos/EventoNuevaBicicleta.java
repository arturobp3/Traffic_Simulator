package eventos.eventoVehiculos;

import java.util.List;

import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Bicicleta;
import entidades.vehiculos.Vehiculo;
import eventos.eventoCarreteras.ParserCarreteras;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevaBicicleta extends EventoNuevoVehiculo {

// CONSTRUCTOR:
	
	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario, String type) {
		super(tiempo, id, velocidadMaxima, itinerario);
	}
	

// METODOS:
	
	@Override
	protected Vehiculo creaVehiculo(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> parseItinerario = ParserCarreteras.parseaListaCruces(itinerario, mapa);
		return new Bicicleta(this.id, this.velocidadMaxima, parseItinerario);
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 super.ejecuta(mapa);
	}
	
	@Override
	public String toString() {
		return "New Bike";
	};

}
