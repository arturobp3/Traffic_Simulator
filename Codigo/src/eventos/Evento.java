package eventos;

import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public abstract class Evento {

	protected Integer tiempo;
	
	public Evento(int tiempo) { 
		this.tiempo = tiempo;
	}
	public int getTiempo() {
		return tiempo;
		
	}
	
	//...
	
	// cada clase que hereda implementa su método ejecuta, que creará
	// el correspondiente objeto de la simulación.
	
	public abstract void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion;

}
