package eventos.eventoVehiculos;

import java.util.List;

import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import eventos.eventoCarreteras.ParserCarreteras;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevoVehiculo extends Evento {
	
// ATRIBUTOS:
	
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;


// CONSTRUCTOR:
	
	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = itinerario;
	}

	
// METODOS:
	
	protected Vehiculo creaVehiculo(MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> parseItinerario = ParserCarreteras.parseaListaCruces(itinerario, mapa);
		return new Vehiculo(this.id, this.velocidadMaxima, parseItinerario);
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		 List<CruceGenerico<?>> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		 // Si iti es null o tiene menos de dos cruces lanzar excepción
		 // en otro caso crear el vehículo y añadirlo al mapa.
		 if (iti == null || iti.size() < 2) {
			 throw new ErrorDeSimulacion();
		 }
		 else {
			 // El itinerario tiene que parsearse y ser una lista de cruces
			 //List<CruceGenerico<?>> parseItinerario = ParserCarreteras.parseaListaCruces(itinerario, mapa);
			 //Vehiculo v = new Vehiculo(this.id, this.velocidadMaxima, parseItinerario);
			 Vehiculo v = creaVehiculo(mapa);
			 mapa.addVehiculo(v.getIdentificador(), v);
		 }
	}
	
	@Override
	public String toString() {
		return "New Vehicle";
		 
	};
	 
	 /*
	 	this.itinerario = [“j1”,”j2”,”j3”] entonces parseaListaCruces devuelve la
		lista de cruces cuyos identifcadores son los de “j1”, “j2” y “j3”. Para ello
		utiliza el mapa de carreteras.
	 */
}
