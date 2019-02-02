package entidades.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entidades.carreteras.Carretera;
import entidades.carreteras.CarreteraEntrante;
import entidades.vehiculos.Vehiculo;
import excepciones.ErrorDeSimulacion;

public class Cruce extends CruceGenerico<CarreteraEntrante> {

// ATRIBUTOS:
	
	protected int indiceSemaforoVerde; // Lleva el �ndice de la carretera entrante con el sem�foro en verde
	//protected List<CarreteraEntrante> carreterasEntrantes; 
	//protected Map<String, CarreteraEntrante> mapaCarreterasEntrantes; // Para optimizar busquedas -> (IdCarretera, CarreteraEntrante)
	protected Map<Cruce, Carretera> mapaCarreterasSalientes;
	//protected List<Carretera> carreterasSalientes; // Lista creada por indicaciones de clase

	
// CONSTRUCTOR:
	
	public Cruce(String id) {
		super(id);
		this.indiceSemaforoVerde = 0;
		
		carreterasEntrantes = new ArrayList<CarreteraEntrante>();
		mapaCarreterasEntrantes = new HashMap<String, CarreteraEntrante>(); // Mapa no ordenado
		mapaCarreterasSalientes = new HashMap<Cruce, Carretera>(); 
		carreterasSalientes = new ArrayList<Carretera>();
	}


// METODOS:
	
	// Devuelve la carretera que llega a ese cruce desde �this�:
	public Carretera carreteraHaciaCruce(Cruce cruce) {
		
		return this.mapaCarreterasSalientes.get(cruce);
	}
	
	// A�ade una carretera entrante al �mapaCarreterasEntrantes� y a las �carreterasEntrantes�:
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {

		CarreteraEntrante ce = creaCarreteraEntrante(carretera);
		this.mapaCarreterasEntrantes.put(idCarretera, ce);
		this.carreterasEntrantes.add(ce);		
	}
	
	// A�ade una carretera saliente: 
	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
		
		this.mapaCarreterasSalientes.put(destino, road);
		this.carreterasSalientes.add(road); // A�adimos a la nueva lista de carreteras salientes
	}
	
	// A�ade el �vehiculo� a la carretera entrante �idCarretera�:
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		
		if(!this.mapaCarreterasEntrantes.get(idCarretera).contains(vehiculo)){ // Si no lo contiene, lo a�ade
			this.mapaCarreterasEntrantes.get(idCarretera).add(vehiculo);
		}
	}
	
	// Pone el sem�foro de la carretera actual a �verde� y busca la siguiente carretera entrante para ponerlo a �rojo�:
	protected void actualizaSemaforos() {
		
		indiceSemaforoVerde++;
		
		int indice = (this.indiceSemaforoVerde) % carreterasEntrantes.size();
		int indice2 = (this.indiceSemaforoVerde + 1) % carreterasEntrantes.size();
		
		this.carreterasEntrantes.get(indice).ponSemaforo(false);
		this.carreterasEntrantes.get(indice2).ponSemaforo(true);
	} 
	
	@Override
	public void avanza() throws ErrorDeSimulacion {
		// Si �carreterasEntrantes� es vac�o, no hace nada.
		// en otro caso �avanzaPrimerVehiculo� de la carretera con el sem�foro verde.
		// Posteriormente actualiza los sem�foros.
		
		if (!this.carreterasEntrantes.isEmpty()) {
			this.carreterasEntrantes.get((this.indiceSemaforoVerde + 1) % carreterasEntrantes.size()).avanzaPrimerVehiculo();
			this.actualizaSemaforos();
		}
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		
		return new CarreteraEntrante(carretera);
	}
}