package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoCruces.EventoNuevoCruceCircular;

public class ConstructorEventoNuevoCruceCircular extends ConstructorEventoNuevoCruce {

	public ConstructorEventoNuevoCruceCircular() {
		 this.etiqueta = "new_junction";
		 this.claves = new String[] { "time", "id", "type", "max_time_slice", "min_time_slice" };
		 this.valoresPorDefecto = new String[] { "", "", "rr", "", ""};
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("rr")) return null;
		else
			return new EventoNuevoCruceCircular(
		 // extrae el valor del campo “time” en la sección
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
		 // extrae el valor del campo “id” de la sección
		 ConstructorEventos.identificadorValido(section, "id"),
		 
		 ConstructorEventos.parseaInt(section, "min_time_slice"),
		 
		 ConstructorEventos.parseaInt(section, "max_time_slice"));
		
	}
	
	@Override
	public String toString() { return "Nuevo Cruce Circular"; }

}
