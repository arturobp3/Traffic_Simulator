package eventos.eventoCruces;

import entidades.cruces.Cruce;
import entidades.cruces.CruceGenerico;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevoCruce extends Evento {
	
// ATRIBUTOS:
	
	protected String id;

	
// CONSTRUCTOR:
	
	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id = id;
	}
	

// METODOS:
	
	 
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		mapa.addCruce(this.id, this.creaCruce()); // lo sobreescriben las clases que heredan
	}
	
	protected CruceGenerico<?> creaCruce() {
		return new Cruce(this.id);
	}
	
	@Override
	public String toString() {
		return "New Junction";
	}
}
