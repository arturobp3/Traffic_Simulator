package eventos.eventoCarreteras;

import entidades.carreteras.Camino;
import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevoCamino extends EventoNuevaCarretera {

// CONSTRUCTOR:
	
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
	}

	
// METODOS:
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// Obten cruce origen y cruce destino utilizando el mapa
		// Crea la carretera
		// Añade al mapa la carretera
		
		CruceGenerico<?> cruceOrigen = mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> cruceDestino = mapa.getCruce(this.cruceDestinoId);
		Carretera c = creaCarretera(cruceOrigen,cruceDestino); // Crea un nuevo camino
		mapa.addCarretera(this.id,cruceOrigen,c,cruceDestino);
	 }
	 
	 @Override
	 public String toString() {
		return "New Road(Path)";
	 }
}
