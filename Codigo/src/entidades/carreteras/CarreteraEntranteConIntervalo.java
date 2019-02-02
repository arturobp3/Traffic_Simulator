package entidades.carreteras;

import excepciones.ErrorDeSimulacion;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante {
	
	private int intervaloDeTiempo; // Tiempo que ha de transcurrir para poner el semáforo de la 
									//carretera en rojo
	
	private int unidadesDeTiempoUsadas; // Se incrementa cada vez que avanza un vehículo
	
	private boolean usoCompleto; // Controla que en cada paso con el semáforo en verde, ha pasado
								//un vehículo
	
	private boolean usadaPorUnVehiculo; // Controla que al menos ha pasado un vehículo mientras el semáforo
										// estaba en verde.

	

	public CarreteraEntranteConIntervalo(Carretera carretera, int intervalo, int unidades) {
		super(carretera);
		intervaloDeTiempo = intervalo;
		unidadesDeTiempoUsadas = unidades;
		usoCompleto = true;
		usadaPorUnVehiculo = false;
	}
	
	@Override
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion { // Cambiado protected por public
		unidadesDeTiempoUsadas++;
		
		if(this.colaVehiculos.isEmpty()) usoCompleto = false;
		else{
			super.avanzaPrimerVehiculo();
			usadaPorUnVehiculo = true;
		}
		
		// Incrementa unidadesDeTiempoUsadas
		// Actualiza usoCompleto:
		// - Si “colaVehiculos” es vacía, entonces “usoCompleto=false”
		// - En otro caso saca el primer vehículo “v” de la “colaVehiculos”,
		// y le mueve a la siguiente carretera (“v.moverASiguienteCarretera()”)
		// Pone “usadaPorUnVehiculo” a true.
	}
	
	public boolean tiempoConsumido() {
		return (unidadesDeTiempoUsadas >= intervaloDeTiempo);
		
		// comprueba si se ha agotado el intervalo de tiempo.
		// “unidadesDeTiempoUsadas >= “intervaloDeTiempo”
	}
	
	public boolean usoCompleto() {
		return usoCompleto;
	} 
	
	public boolean usada() {
		return usadaPorUnVehiculo;
	}

	public void setIntervaloDeTiempo(int intervaloDeTiempo) {
		this.intervaloDeTiempo = intervaloDeTiempo;
	}

	public int getIntervaloDeTiempo() {
		return intervaloDeTiempo;
	}
	

	public int getUnidadesDeTiempoUsadas() {
		return unidadesDeTiempoUsadas;
	}

	public void setUnidadesDeTiempoUsadas(int unidadesDeTiempoUsadas) {
		this.unidadesDeTiempoUsadas = unidadesDeTiempoUsadas;
	} 
	
	// GetCrucesVerde en CruceGenerico
	@Override
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
		report += ", " + this.getIntervaloDeTiempo();
	
		return report;
	}
	
}
