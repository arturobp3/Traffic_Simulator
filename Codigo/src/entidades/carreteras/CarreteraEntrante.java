package entidades.carreteras;

import java.util.ArrayList;
import java.util.List;

import entidades.vehiculos.Vehiculo;
import excepciones.ErrorDeSimulacion;

public class CarreteraEntrante {

// ATRIBUTOS:
	
	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo; // true = verde, false = rojo
	

// CONSTRUCTOR:
	
	public CarreteraEntrante(Carretera carretera) {
		this.carretera = carretera;
		this.semaforo = false;
		this.colaVehiculos = new ArrayList<Vehiculo>();
	}
	
	
// METODOS:
	
	public boolean contains(Vehiculo vehiculo){
		if(colaVehiculos.contains(vehiculo)) return true;
		else return false;
	}
	
	public boolean add(Vehiculo vehiculos){
		colaVehiculos.add(vehiculos);
		return true;
	}
	
	public Integer tamanoCola() {
		return this.colaVehiculos.size();
	}
	
	public void ponSemaforo(boolean color) {
		this.semaforo = color;
	}
	
	
	public boolean getSemaforo() {
		return semaforo;
	}


	// Coge el primer vehiculo de la cola, lo elimina, y lo manda que se mueva a su siguiente carretera:
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		
		if(colaVehiculos.size() > 0){
			Vehiculo v = this.colaVehiculos.get(0);
			v.moverASiguienteCarretera();
			this.colaVehiculos.remove(0);
		}
	}
	
	@Override
	public String toString() {
		// Utilizado en el completaDetallesSeccion de Cruce, revisar en el futuro
		return this.carretera.getIdentificador();
	}
	
	public String listarVehiculos(){
		String report = "";
		
		if(colaVehiculos.size() > 0){
			report = "[";
			
			for(int i = 0; i < colaVehiculos.size(); i++){
				report += colaVehiculos.get(i).getIdentificador();
				if(i < colaVehiculos.size() - 1){
					report += ",";
				}
			}
			report += "]";
		}
		else{
			report = "[]";
		}
	
		return report;
	}


	public Carretera getCarretera() {
		
		return this.carretera;
	}


	public boolean tieneSemaforoVerde() {
		
		return this.getSemaforo();
	}

}