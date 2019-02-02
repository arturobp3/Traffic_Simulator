package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;
import eventos.eventoVehiculos.EventoNuevaAveria;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {

// ATRIBUTOS:
	
	protected Integer duracionInfraccion;
	protected String[] vehiculos;
	
// CONSTRUCTOR:
	
	public ConstructorEventoAveriaCoche() {
		
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] { "time", "vehicles","duration" };
		this.valoresPorDefecto = new String[] { "", "", ""};
	}
	
	
// METODOS:
	
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) || section.getValue("type") != null) return null;
		else
			return new EventoNuevaAveria(
		 // extrae el valor del campo “time” en la sección
		 // 0 es el valor por defecto en caso de no especificar el tiempo
		 ConstructorEventos.parseaInt(section, "time"),
		 // Lista de vehiculos a averiar:
		 ConstructorEventos.identificadorValido2(section, "vehicles"),
		 // Duracion de averia
		 ConstructorEventos.parseaInt(section, "duration"));
	}
	
	public Integer getDuracionInfraccion() {
		return duracionInfraccion;
	}
	
	@Override
	public String toString() { return "Nuevo Vehiculo Averiado"; }
}
