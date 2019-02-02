package entidades.carreteras;

import java.util.Comparator;
import java.util.List;

import codigoCampus.IniSection;
import entidades.ObjetoSimulacion;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import principal.SortedArrayList;

public class Carretera extends ObjetoSimulacion {

// ATRIBUTOS:
	
	protected int longitud; 							// Longitud de la carretera
	protected int velocidadMaxima; 						// Velocidad máxima
	protected CruceGenerico<?> cruceOrigen; 			// Cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; 			// Cruce al que llega la carretera
	protected List<Vehiculo> vehiculos; 				// Lista ordenada de vehículos en la carretera (ordenada por localización)
	protected Comparator<Vehiculo> comparadorVehiculo; 	// Orden entre vehículos
	private int obstaculos;
	private int velocidadBase;

// CONSTRUCTOR:
	
	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest) {
		super(id);
		this.longitud = length;
		this.velocidadMaxima = maxSpeed;
		this.cruceOrigen = src;
		this.cruceDestino = dest;
		this.obstaculos  = 0;
		this.velocidadBase = 0;
		this.comparadorVehiculo = new Comparator<Vehiculo>() {

			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				if (o1.getLocalizacion() == o2.getLocalizacion()) return 0;
				else if (o1.getLocalizacion() < o2.getLocalizacion()) return 1;
				else return -1;
			}
			
		};
		
		vehiculos = new SortedArrayList<Vehiculo>(comparadorVehiculo);
	}
	
	
// METODOS:
	
	@Override
	public void avanza() {
		 velocidadBase = this.calculaVelocidadBase();
		 int factor = 1; //Por defecto el factor es 1
		 int vel;
		
		 for (int i = 0; i < this.vehiculos.size(); i++) {
	
			 if(i > 0){
				 obstaculos = this.obstaculosDelante(i); //Calcula el numero de obstaculos delante de ese vehiculo
				 factor = this.calculaFactorReduccion(obstaculos); /* Se aplica a los coches que estén por detrás
				    de uno averiado. El primer coche no tiene a nadie delante averiado
				    por tanto no se aplica.*/
			 }
			 vel = velocidadBase / factor;
			 
			if(this.vehiculos.get(i).getAveria()){
				vel = 0;
			}
			 
			this.vehiculos.get(i).setVelocidadActual(vel);
			this.vehiculos.get(i).avanza();
			
			if(vehiculos.get(i).getTiempoAveria() == 0 && vehiculos.get(i).getAveria()){ /*Cuando una averia ha acabado, hay un obstaculo menos*/								
				obstaculos--;
				vehiculos.get(i).setAveria(false);
			}
			 
		 }
		 this.vehiculos.sort(comparadorVehiculo);
		 
		 // Para cada vehículo de la lista “vehiculos”:
		 /* 1. Si el vehículo está averiado se incrementa el número de obstaculos.
		 	2. Se fija la velocidad actual del vehículo
		 	3. Se pide al vehículo que avance.
		 // ordenar la lista de vehículos */
	}
	
	private int obstaculosDelante(int indiceVehiculo){
		int num = 0;
		for(int i = indiceVehiculo - 1; i >= 0; i--){
			if(vehiculos.get(i).getAveria()){
				num++;
			}
		}
		
		return num;
	}
	
	public void entraVehiculo(Vehiculo vehiculo) {
		// Si el vehículo no existe en la carretera, se añade a la lista de vehículos y se ordena la lista.
		// Si existe no se hace nada.
		
		if(!this.vehiculos.contains(vehiculo)) {
			this.vehiculos.add(vehiculo);
			this.vehiculos.sort(comparadorVehiculo);
		}
	}
	
	// Elimina el vehículo de la lista de vehículos:
	public void saleVehiculo(Vehiculo vehiculo) {
		
		this.vehiculos.remove(vehiculo);
	}
	
	// Añade el vehículo al “cruceDestino” de la carretera”:
	public void entraVehiculoAlCruce(Vehiculo v) {
		
		this.cruceDestino.entraVehiculoAlCruce(this.getIdentificador(), v);
	}
	
	protected int calculaVelocidadBase() {
		return Math.min(this.velocidadMaxima, (this.velocidadMaxima/Math.max(this.vehiculos.size(), 1)) + 1);
	}
	
	protected int calculaFactorReduccion(int obstaculos) {
		int facRed;
		if (obstaculos == 0) {
			facRed = 1;
		}
		else { facRed = 2; }
		
		return facRed;
	}
	
	@Override
	protected String getNombreSeccion() {
		return "road_report";
	}
		
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		// Crea “vehicles = (v1,10), (v2,10)”
		String report = "";
		
		if(vehiculos.size() > 0){
			
			for (int i = 0; i < this.vehiculos.size(); i++) {
				report += "(";
				report += vehiculos.get(i).getIdentificador() + 
						"," + this.vehiculos.get(i).getLocalizacion() + ")";
				
				if(i < vehiculos.size() - 1){
					report += ",";
				} 
			}
		}
		
		
		is.setValue("state", report);
	}
	
	public int getLongitud() {
		return this.longitud;
	}
	
	public int getVelMaxima() {
		return this.velocidadMaxima;
	}
	
	public String getListaVehiculos() {
		String res = "["; 
		int i = 0;
		if (this.vehiculos.size() > 0) {
			for (i = 0; i < this.vehiculos.size() - 1; i++) {
				res += this.vehiculos.get(i).getId() + ",";
			}
			res += this.vehiculos.get(i).getId();
		}
		res += "]";
		return res;
	}
	
	//Necesario para el JDialog de informes
	@Override
	public String toString() {
		return this.getIdentificador();
	}

// Metodos getter requeridos en ComponenteMapa:

	public CruceGenerico<?> getCruceOrigen() {
		
		return this.cruceOrigen;
	}


	public CruceGenerico<?> getCruceDestino() {
	
		return this.cruceDestino;
	}


	public List<Vehiculo> getVehiculos() {
		
		return this.vehiculos;
	}

}