package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoVehiculos.EventoNuevoCoche;

public class ConstructorEventoNuevoCoche extends ConstructorEventoNuevoVehiculo {

// CONSTRUCTOR:
	
	public ConstructorEventoNuevoCoche() {
		 this.etiqueta = "new_vehicle";
		 this.claves = new String[] { "time", "id","max_speed", "itinerary", "resistance", "fault_probability", "max_fault_duration", "seed", "type"};
		 this.valoresPorDefecto = new String[] { "", "", "", "", "", "", "", "", "car"};
	}
	
// METODOS:
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("car")) return null;
		else
			return new EventoNuevoCoche(
		 // extrae el valor del campo “time” en la sección
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
		 // extrae el valor del campo “id” de la sección
		 ConstructorEventos.identificadorValido(section, "id"),
		 // Velocidad maxima:
		 ConstructorEventos.parseaInt(section, "max_speed"),
		 // Lista de cruce por los que tiene que pasar el vehiculo
		 // Leer lista y separar por comas
		 ConstructorEventos.separator(section, "itinerary"),
		 ConstructorEventos.parseaInt(section, "resistance"),
		 ConstructorEventos.parseaDouble(section, "fault_probability"),
		 ConstructorEventos.parseaInt(section, "max_fault_duration"),
		 ConstructorEventos.parseaLong(section, "seed"),
		 ConstructorEventos.identificadorValido(section, "type")
		 );
	
	}
	
	@Override
	public String toString() { return "Nuevo Coche"; }
}
