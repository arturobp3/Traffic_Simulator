package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoVehiculos.EventoNuevaBicicleta;

public class ConstructorEventoNuevaBicicleta extends ConstructorEventoNuevoVehiculo {

// CONSTRUCTOR:
	
	public ConstructorEventoNuevaBicicleta() {
		 this.etiqueta = "new_vehicle";
		 this.claves = new String[] { "time", "id","max_speed", "itinerary", "type" };
		 this.valoresPorDefecto = new String[] { "", "", "", "", "bike"};
	}
	
// METODOS:
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("bike")) return null;
		else
			return new EventoNuevaBicicleta(
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
		 // Tipo
		 ConstructorEventos.identificadorValido(section, "type")
		 );
	
	}
	
	@Override
	public String toString() { return "Nueva Bicicleta"; }
}
