package eventos.eventoCruces;

import entidades.cruces.CruceCongestionado;
import entidades.cruces.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce {

	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
	}
	
	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCongestionado(this.id);
	}

	@Override
	public String toString() {
		return "New Overcrowded Junction";
	}
}
