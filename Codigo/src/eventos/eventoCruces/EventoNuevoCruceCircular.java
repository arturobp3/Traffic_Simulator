package eventos.eventoCruces;

import entidades.cruces.CruceCircular;
import entidades.cruces.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {

	protected Integer maxValorIntervalo;
	protected Integer minValorIntervalo;
	 
	public EventoNuevoCruceCircular(int time, String id) {
		super(time, id);
	}
	
	
	public EventoNuevoCruceCircular(int time, String id, int minValorIntervalo, int maxValorIntervalo) {
		super(time, id);
		this.maxValorIntervalo = maxValorIntervalo;
		this.minValorIntervalo = minValorIntervalo;
	}
	
	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
	}

	@Override
	public String toString() {
		return "New Circular Junction";
	}
}
