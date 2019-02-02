package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoCruces.EventoNuevoCruceCongestionado;

public class ConstructorEventoNuevoCruceCongestionado extends ConstructorEventoNuevoCruce {


	public ConstructorEventoNuevoCruceCongestionado() {
		 this.etiqueta = "new_junction";
		 this.claves = new String[] { "time", "id", "type"};
		 this.valoresPorDefecto = new String[] { "", "", "mc"};
	}
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || !section.getValue("type").equals("mc")) return null;
		else
			return new EventoNuevoCruceCongestionado(
		 // extrae el valor del campo “time” en la sección
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
		 // extrae el valor del campo “id” de la sección
		 ConstructorEventos.identificadorValido(section, "id"));
		
	}
	
	@Override
	public String toString() { return "Nuevo Cruce Congestionado"; }
	
}
