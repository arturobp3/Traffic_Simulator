package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoCarreteras.EventoNuevaCarretera;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	public ConstructorEventoNuevaCarretera() {
		 this.etiqueta = "new_road";
		 this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length" };
		 this.valoresPorDefecto = new String[] { "", "", "","", "", "" };
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		else
			return new EventoNuevaCarretera(
		 // extrae el valor del campo “time” en la sección
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
		 // extrae el valor del campo “id” de la sección
		 ConstructorEventos.identificadorValido(section, "id"),
		 // Cruce Origen:
		 ConstructorEventos.identificadorValido(section, "src"),
		 // Cruce Destino del itinerario
		 ConstructorEventos.identificadorValido(section, "dest"),
		 // Velocidad maxima:
		 ConstructorEventos.parseaInt(section, "max_speed"),
		 // Longitud de la carretera
		 ConstructorEventos.parseaInt(section, "length")
		 );
	
	}
	
	@Override
	public String toString() { return "Nueva Carretera"; }
}
