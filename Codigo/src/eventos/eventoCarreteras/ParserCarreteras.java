package eventos.eventoCarreteras;

import java.util.ArrayList;
import java.util.List;

import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;

public class ParserCarreteras {

	public static List<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) throws ErrorDeSimulacion {
		List<CruceGenerico<?>> lista = new ArrayList<CruceGenerico<?>>();
		for (int i = 0; i < itinerario.length; i++) {
			lista.add(mapa.getCruce(itinerario[i]));
		}
		return lista;
	}
}
