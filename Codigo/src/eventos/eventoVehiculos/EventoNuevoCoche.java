package eventos.eventoVehiculos;

import java.util.List;

import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Coche;
import entidades.vehiculos.Vehiculo;
import eventos.eventoCarreteras.ParserCarreteras;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevoCoche extends EventoNuevoVehiculo {

// ATRIBUTOS:
	
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected long semilla;
	
// CONSTRUCTOR:
	
	public EventoNuevoCoche(int tiempo, String id, int velocidadMaxima, String[] itinerario, int resistance, 
			double fault_probability, int max_fault_duration, long seed, String type) {
		super(tiempo, id, velocidadMaxima, itinerario);
		this.resistenciaKm = resistance;
		this.probabilidadDeAveria = fault_probability;
		this.duracionMaximaAveria = max_fault_duration;
		this.semilla = seed;
	}
	
	
// METODOS:
	
	@Override
	protected Vehiculo creaVehiculo(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> parseItinerario = ParserCarreteras.parseaListaCruces(itinerario, mapa);
		return new Coche(this.id, this.velocidadMaxima, this.resistenciaKm, this.probabilidadDeAveria, this.semilla, 
				this.duracionMaximaAveria, parseItinerario);
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 super.ejecuta(mapa);
	}
	
	@Override
	public String toString() {
		return "New Car";
	};

}
