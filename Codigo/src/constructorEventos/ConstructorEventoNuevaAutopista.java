package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoCarreteras.EventoNuevaAutopista;

public class ConstructorEventoNuevaAutopista extends ConstructorEventoNuevaCarretera {

// CONSTRUCTOR:
	
	public ConstructorEventoNuevaAutopista() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length", "lanes", "type" };
		this.valoresPorDefecto = new String[] { "", "", "", "", "", "", "", "lanes" };
	}
	
// METODOS:
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("lanes")) return null;
		else
			return new EventoNuevaAutopista(
		 // extrae el valor del campo �time� en la secci�n
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
		 // extrae el valor del campo �id� de la secci�n
		 ConstructorEventos.identificadorValido(section, "id"),
		 // Cruce Origen:
		 ConstructorEventos.identificadorValido(section, "src"),
		 // Cruce Destino del itinerario:
		 ConstructorEventos.identificadorValido(section, "dest"),
		 // Velocidad maxima:
		 ConstructorEventos.parseaInt(section, "max_speed"),
		 // Longitud de la carretera:
		 ConstructorEventos.parseaInt(section, "length"),
		 // Lanes:
		 ConstructorEventos.parseaInt(section, "lanes"),
		 // Tipo de carretera:
		 ConstructorEventos.identificadorValido(section, "type")
		 );
	}
	
	@Override
	public String toString() { return "Nueva Autopista"; }
}
