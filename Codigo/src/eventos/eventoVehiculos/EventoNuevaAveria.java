package eventos.eventoVehiculos;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class EventoNuevaAveria extends Evento {

	private Integer duracionInfraccion;
	private String[] vehiculos;
	
	public EventoNuevaAveria(int tiempo, String[] vehiculos, int duracion) {
		super(tiempo);
		duracionInfraccion = duracion;
		this.vehiculos = vehiculos;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		for (int i = 0; i < vehiculos.length; i++) {
			mapa.getVehiculo(vehiculos[i]).setTiempoAveria(duracionInfraccion);
		}
	}
	
	@Override
	public String toString() {
		return "New Faulty";
	}
}
