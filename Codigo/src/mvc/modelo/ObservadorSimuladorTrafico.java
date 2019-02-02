package mvc.modelo;

import java.util.List;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public interface ObservadorSimuladorTrafico {

// METODOS:
	
	// Notifica errores:
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e);
	
	// Notifica el avance de los objetos de simulación:
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	
	// Notifica que se ha generado un nuevo evento:
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
	
	// Notifica que la simulación se ha reiniciado:
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos);
}
