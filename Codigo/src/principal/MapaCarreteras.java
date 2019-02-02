package principal;

import java.util.*;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import excepciones.ErrorDeSimulacion;

public class MapaCarreteras {

// ATRIBUTOS:
	
	private List<Carretera> carreteras;
	private List<CruceGenerico<?>> cruces;
	private List<Vehiculo> vehiculos;
	// estructuras para agilizar la búsqueda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;
	
	
// CONSTRUCTORA:
	
	public MapaCarreteras() {
		carreteras = new ArrayList<Carretera>(); // Insercion y modificacion eficientes
		cruces = new ArrayList<CruceGenerico<?>>();
		vehiculos = new ArrayList<Vehiculo>();
		
		mapaDeCarreteras = new HashMap<String, Carretera>(); // El mapa no esta ordenado
		mapaDeCruces = new HashMap<String, CruceGenerico<?>>();
		mapaDeVehiculos = new HashMap<String, Vehiculo>();
		
		
		 // inicializa los atributos a sus constructoras por defecto.
		 // Para carreteras, cruces y vehículos puede usarse ArrayList.
		 // Para los mapas puede usarse HashMap
	}
	

// METODOS:
	
	public void addCruce(String idCruce, CruceGenerico<?> cruceGenerico) throws ErrorDeSimulacion {
		
		if(!mapaDeCruces.containsKey(idCruce)){
			cruces.add(cruceGenerico);
			mapaDeCruces.put(idCruce, cruceGenerico);
		}
		else {
			throw new ErrorDeSimulacion("El cruce ya existe en el mapa");
		}
		
		 // comprueba que “idCruce” no existe en el mapa.
		 // Si no existe, lo añade a “cruces” y a “mapaDeCruces”.
		 // Si existe lanza una excepción.
	}
	//“Solo se ejecuta una vez por Cruce. Cuando se procesa su evento”.
	
	public void addVehiculo(String idVehiculo, Vehiculo vehiculo) throws ErrorDeSimulacion {
		
		if(!mapaDeVehiculos.containsKey(idVehiculo)){
			vehiculos.add(vehiculo);
			mapaDeVehiculos.put(idVehiculo, vehiculo);
			vehiculo.moverASiguienteCarretera();
		}
		else{
			throw new ErrorDeSimulacion("El vehiculo ya existe en el mapa");
		}
		 // comprueba que “idVehiculo” no existe en el mapa.
		 // Si no existe, lo añade a “vehiculos” y a “mapaDeVehiculos”,
		 // y posteriormente solicita al vehiculo que se mueva a la siguiente
		 // carretera de su itinerario (moverASiguienteCarretera).
		 // Si existe lanza una excepción.
	}
	//“Solo se ejecuta una vez por vehículo. Cuando se procesa el evento”.
	
	public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino)
			throws ErrorDeSimulacion {
		
		if(!mapaDeCarreteras.containsKey(idCarretera)){
			carreteras.add(carretera);
			mapaDeCarreteras.put(idCarretera, carretera);
			
			origen.addCarreteraSalienteAlCruce(destino, carretera);
			destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		}
		else{
			throw new ErrorDeSimulacion("El vehiculo ya existe en el mapa");
		}
		// comprueba que “idCarretera” no existe en el mapa.
		// Si no existe, lo añade a “carreteras” y a “mapaDeCarreteras”,
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// - Añade al cruce origen la carretera, como “carretera saliente”
		// - Añade al crude destino la carretera, como “carretera entrante”
		// Si existe lanza una excepción.
	}
	
	public String generateReport(int time) {
		 String report = "";
		 // genera informe para cruces
		 for(CruceGenerico<?> i: cruces){
			 report += i.generaInforme(time)+ "\r\n";
		 }
		 
		 // genera informe para carreteras
		 for(Carretera i: carreteras){
			 report += i.generaInforme(time) + "\r\n";
		 }
		 // genera informe para vehiculos
		 
		 for(Vehiculo i: vehiculos){
			 report += i.generaInforme(time) + "\r\n";
		 }
		 
		return report + "\n";
	}
	
	public void actualizar() throws ErrorDeSimulacion {
		// llama al método avanza de cada carretera
		for(Carretera i: carreteras){
			 i.avanza();
		}
		
		// llama al método avanza de cada cruce
		for(CruceGenerico<?> i: cruces){
			 i.avanza();
		}
	}
	
	public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion {
		// devuelve el cruce con ese “id” utilizando el mapaDeCruces.
		CruceGenerico<?> a_devolver = mapaDeCruces.get(id);
		
		if(a_devolver != null){
			return a_devolver;
		}
		else{
			// sino existe el cruce lanza excepción.
			throw new ErrorDeSimulacion("Cruce no existente");
		}
	}
	
	public Vehiculo getVehiculo(String id) throws ErrorDeSimulacion {
		
		Vehiculo a_devolver = mapaDeVehiculos.get(id);
		
		if(a_devolver != null){
			return a_devolver;
		}
		else{
			throw new ErrorDeSimulacion("Vehiculo no existente");
		}
		// devuelve el vehículo con ese “id” utilizando el mapaDeVehiculos.
		// sino existe el vehículo lanza excepción.
	}
	
	public Carretera getCarretera(String id) throws ErrorDeSimulacion {
		Carretera a_devolver = mapaDeCarreteras.get(id);
		
		if(a_devolver != null){
			return a_devolver;
		}
		else{
			throw new ErrorDeSimulacion("Carretera no existente");
		}
		// devuelve la carretera con ese “id” utilizando el mapaDeCarreteras.
		// sino existe la carretra lanza excepción.
	}

// Metodos getter requeridos en la clase ComponenteMapa:
	
	public List<CruceGenerico<?>> getCruces() {
		
		return this.cruces;
	}

	public List<Carretera> getCarreteras() {
	
		return this.carreteras;
	}

	public List<Vehiculo> getVehiculos() {
		
		return this.vehiculos;
	}
	
// Metodo reinicia utilizado en controlador:
	
	public void reinicia() {
		this.carreteras.clear();
		this.cruces.clear();
		this.vehiculos.clear();
		this.mapaDeCarreteras.clear();
		this.mapaDeCruces.clear();
		this.mapaDeVehiculos.clear();
	}
}
