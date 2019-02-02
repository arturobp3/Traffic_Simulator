package entidades.cruces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codigoCampus.IniSection;
import entidades.ObjetoSimulacion;
import entidades.carreteras.Carretera;
import entidades.carreteras.CarreteraEntrante;
import entidades.vehiculos.Vehiculo;
import excepciones.ErrorDeSimulacion;

abstract public class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

// ATRIBUTOS:
	
	protected int indiceSemaforoVerde;
	protected List<T> carreterasEntrantes;
	protected Map<String, T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> mapaCarreterasSalientes;
	protected List<Carretera> carreterasSalientes; 	// Lista creada porque segun indicicaciones en clase, la colocamos en Cruce 
													// y, por tanto, aqui tambien deberia ir
	
// CONSTRUCTOR:
	
	public CruceGenerico(String identificador) {
		super(identificador);
		this.indiceSemaforoVerde = 0;
		
		carreterasEntrantes = new ArrayList<T>();
		mapaCarreterasEntrantes = new HashMap<String, T>(); // Mapa no ordenado
		mapaCarreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>(); 
		carreterasSalientes = new ArrayList<Carretera>();
	}
	

// METODOS:
	
	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) {
		
		return this.mapaCarreterasSalientes.get(cruce);
	}
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		T ri = creaCarreteraEntrante(carretera);
		this.mapaCarreterasEntrantes.put(idCarretera, ri);
		this.carreterasEntrantes.add(ri);
	}
	
	
	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {
		
		this.mapaCarreterasSalientes.put(destino, carr);
		this.carreterasSalientes.add(carr); // Añadimos a la nueva lista de carreteras salientes
	}
	
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
		
		if(!this.mapaCarreterasEntrantes.get(idCarretera).contains(vehiculo)) { // Si no lo contiene, lo añadirá
			this.mapaCarreterasEntrantes.get(idCarretera).add(vehiculo);
		}
	}
	
	@Override
	public void avanza() throws ErrorDeSimulacion {
		
		if (!this.carreterasEntrantes.isEmpty()) {
			this.carreterasEntrantes.get((this.indiceSemaforoVerde) % carreterasEntrantes.size()).avanzaPrimerVehiculo();
			this.actualizaSemaforos();
		}
	}
	
	protected String getNombreSeccion() {
		return "junction_report";
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		// genera la sección queues = (r2,green,[]),...
		String semaforo;
		String report = "";
		
		if(carreterasEntrantes.size() > 0){
			
			for(int i = 0; i < carreterasEntrantes.size(); i++){
				report += "(";
				
				if(carreterasEntrantes.get(i).getSemaforo()){
					semaforo = "green";
				}
				else semaforo = "red";
			
				report += carreterasEntrantes.get(i).toString() + "," + semaforo + "," + carreterasEntrantes.get(i).listarVehiculos();
				
				report += ")";
				
				if(i < carreterasEntrantes.size() - 1){
					report += ",";
				}
			}
		}

		is.setValue("queues", report);
	}
	
	abstract protected T creaCarreteraEntrante(Carretera carretera);
	abstract protected void actualizaSemaforos();
	
	public String getCrucesVerde() {
		String res = "";
		int i;
		for (i = 0; i < this.carreterasEntrantes.size(); i++) {
			if(carreterasEntrantes.get(i).getSemaforo()){
				res += carreterasEntrantes.get(i).toString() + " ";
			}
		}
		return res;
	}
	
	public String getCrucesRojo() {
		String res = "";
		int i;
		for (i = 0; i < this.carreterasEntrantes.size(); i++) {
			if(!carreterasEntrantes.get(i).getSemaforo()){
				res += carreterasEntrantes.get(i).toString() + " ";
			}
		}
		return res;
	}
	
	//Necesario para el JDialog de informes
	@Override
	public String toString() {
		return this.getId();
	}

// Metodo get requerido en ComponenteMapa:
	
	public String getId() {
		return this.getIdentificador();
	}


	public List<T> getCarreteras() {

		return this.carreterasEntrantes;
	} 

}
