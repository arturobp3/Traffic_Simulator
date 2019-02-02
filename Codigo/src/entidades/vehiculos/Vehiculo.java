package entidades.vehiculos;

import java.util.*;

import codigoCampus.IniSection;
import entidades.ObjetoSimulacion;
import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;

public class Vehiculo extends ObjetoSimulacion {

// ATRIBUTOS:
	
	protected Carretera carretera; 		// Carretera en la que está el vehículo
	protected int velocidadMaxima; 		// Velocidad máxima
	protected int velocidadActual; 		// Velocidad actual
	protected int kilometraje; 			// Distancia recorrida
	protected int localizacion; 		// Localización en la carretera
	protected int tiempoAveria; 		// Tiempo que estará averiado
	protected List<CruceGenerico<?>> itinerario; 	// Itinerario a recorrer (mínimo 2)
	
	// Propios (no vienen explicitamente en las diapositivas):
	protected boolean haLlegado; 	// El metodo completaDetalles confirma que esto es un atributo
	protected boolean estaEnCruce; 	// Usado en avanza()
	private int contador;			// Usado en moverASiguienteCarretera() para calcular la siguiente carretera
	protected boolean averia; // true = averiado, false = no averiado
	
// CONSTRUCTOR:
	
	public Vehiculo(String id, int velMaxima, List<CruceGenerico<?>> parseItinerario) throws ErrorDeSimulacion {
		super(id);
		
		this.itinerario = parseItinerario;
		this.velocidadMaxima = velMaxima;
		
		if (velocidadMaxima >= 0 && itinerario.size() >= 2) {
			this.carretera =  null;
			
			// Atributos inicializados que no estan presentes en los parametros del metodo:
			this.kilometraje = 0;
			this.localizacion = 0;
			this.tiempoAveria = 0;
			this.contador = 0;
			this.averia = false;
		}
		else {
			throw new ErrorDeSimulacion();
		}
	}
	

// METODOS:
	
	public int getLocalizacion() {
		return localizacion;
	}
	
	public int getTiempoAveria() {
		return tiempoAveria;
	}


	public boolean getAveria() {
		return averia;
	}
	
	public String getCarretera() {
		if (this.carretera == null) {
			return "arrived";
		}
		return this.carretera.getIdentificador();
	}
	
	public int getVelocidad() {
		return this.velocidadActual;
	}

	public void setAveria(boolean averia) {
		this.averia = averia;
	}

	public int getKilometraje() {
		return kilometraje;
	}
	
	public String getItinerario() {
		String res = "["; 
		int i = 0;
		for (i = 0; i < this.itinerario.size() - 1; i++) {
			res += this.itinerario.get(i).getId() + ",";
		}
		res += this.itinerario.get(i).getId() + "]";
		return res;
	}


	public void setVelocidadActual(int velocidad) {
		
		if (velocidad < 0) { velocidadActual = 0; }
		else if (velocidad > velocidadMaxima) { velocidadActual = velocidadMaxima; }
		else { velocidadActual = velocidad; }
	}
	
	public void setTiempoAveria(Integer duracionAveria) {
		
		if(carretera != null) {
			
			if(tiempoAveria > 0) {
				tiempoAveria += duracionAveria;
				velocidadActual = 0;
			}
			else {
				tiempoAveria = duracionAveria;
			}
			
			averia = true;
		}
		//llamado por EventoAveriaCoche
	}

	@Override
	public void avanza() {
		
		if (tiempoAveria > 0) { // Si coche averiado
			tiempoAveria--;		// Decrementamos tiempo
		}
		else {
			this.localizacion += velocidadActual;
			this.kilometraje += velocidadActual;
			
			if (this.localizacion >= carretera.getLongitud()) {
				
				this.kilometraje -= (this.localizacion - carretera.getLongitud());
				this.localizacion = carretera.getLongitud();
				this.estaEnCruce = true; 			// Marcar que vehiculo esta en cruce
				this.carretera.entraVehiculoAlCruce(this);// Indicar a la carretera que el vehiculo entra al cruce
				this.velocidadActual = 0; // Cuando entra en un cruce se pone a 0
			}
		}
	}
	
	public void moverASiguienteCarretera() throws ErrorDeSimulacion {
		
		if (this.carretera != null) {
			this.carretera.saleVehiculo(this);
			
			if (itinerario.get(contador) == this.itinerario.get(itinerario.size() - 1)) {
				this.haLlegado = true;
				this.carretera = null;
				this.velocidadActual = 0;
				this.localizacion = 0;
			}
			else {
				// Se calcula la siguiente carretera:
				//carretera = this.carretera.cruceDestino.carreteraHaciaCruce(this.itinerario.get(contador));
				carretera = itinerario.get(contador).carreteraHaciaCruce(itinerario.get(contador + 1));
				contador++;
				
				
				if(carretera == null) {
					throw new ErrorDeSimulacion(); // Si la carretera no existe
				}
				else {
					// Introducimos vehiculo en la carretera
					this.localizacion = 0;
					carretera.entraVehiculo(this);
				}
			}
		}
		else{ // Si no hay carretera se coloca en la primera que le toca
			carretera = itinerario.get(0).carreteraHaciaCruce(itinerario.get(1));
			carretera.entraVehiculo(this);
			localizacion = 0;
			contador++;
		}
		this.estaEnCruce = false;
	}

	@Override
	protected String getNombreSeccion() {
		return "vehicle_report";
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria > 0 ? this.tiempoAveria : 0);
		is.setValue("location", this.haLlegado ? "arrived" : "(" + carretera.getIdentificador() + "," + this.getLocalizacion() + ")");
	}

	//Necesario para el JDialog de informes
	@Override
	public String toString() {
		return this.getIdentificador();
	}

// Metodos getter requeridos por ComponenteMapa:
	
	public double getTiempoDeInfraccion() {
		// Revisar
		return this.tiempoAveria;
	}


	public String getId() {
		
		return this.getIdentificador();
	}

}

