package eventos.eventoCarreteras;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevaCarretera extends Evento {

// ATRIBUTOS:
	
	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	

// CONSTRUCTOR:
	
	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id = id;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
		this.velocidadMaxima = velocidadMaxima;
		this.longitud = longitud;
	}

	
// METODOS:
	
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Carretera(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino);
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// Obten cruce origen y cruce destino utilizando el mapa
		// Crea la carretera
		// Añade al mapa la carretera

		CruceGenerico<?> cruceOrigen = mapa.getCruce(this.cruceOrigenId);
		CruceGenerico<?> cruceDestino = mapa.getCruce(this.cruceDestinoId);
		Carretera c = creaCarretera(cruceOrigen,cruceDestino); // Lo sobreescriben
		mapa.addCarretera(this.id,cruceOrigen,c,cruceDestino);
	 }
	 
	 @Override
	 public String toString() {
		return "New Road";
	 }
}
