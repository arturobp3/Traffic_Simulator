package eventos.eventoCarreteras;

import entidades.carreteras.Autopista;
import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevaAutopista extends EventoNuevaCarretera {

// ATRIBUTOS:
	
	protected Integer numCarriles;
	

// CONSTRUCTOR:
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, int lanes, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		this.numCarriles = lanes;
	}
	
// METODOS:
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Autopista(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino, this.numCarriles);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// Obten cruce origen y cruce destino utilizando el mapa
		// Crea la carretera
		// Añade al mapa la carretera

		CruceGenerico<?> cruceOrigen = mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> cruceDestino = mapa.getCruce(this.cruceDestinoId);
		Carretera c = creaCarretera(cruceOrigen, cruceDestino); // Crea la nueva autopista
		mapa.addCarretera(this.id, cruceOrigen, c, cruceDestino);
	 }
	 
	 @Override
	 public String toString() {
		return "New Road(Freeway)";
	 }

}
