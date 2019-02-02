package entidades.cruces;

import codigoCampus.IniSection;
import entidades.carreteras.Carretera;
import entidades.carreteras.CarreteraEntranteConIntervalo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {

	public CruceCongestionado(String identificador) {
		super(identificador);
	}

	@Override
	protected void actualizaSemaforos() {
		indiceSemaforoVerde++;

		int indice = (this.indiceSemaforoVerde) % carreterasEntrantes.size();
		
		int max = carreteraConMasVehiculos(indice);
		
		if(indice == -1){
		
			carreterasEntrantes.get(max).ponSemaforo(true);
		}
		else{
			if(carreterasEntrantes.get(indice).tiempoConsumido()){
				carreterasEntrantes.get(indice).ponSemaforo(false);
				
				carreterasEntrantes.get(max).ponSemaforo(true);
				
				carreterasEntrantes.get(max).setIntervaloDeTiempo(
						Math.max( (carreterasEntrantes.get(indice).tamanoCola() / 2), 0 )
				);
			}
			
		}
		
		/*
		 - Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
		 selecciona la carretera que tenga más vehículos en su cola de vehículos.
		 - Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=
		 -1) entonces:
		 1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
		 1.1. Se pone el semáforo de "ri" a rojo.
		 1.2. Se inicializan los atributos de "ri".
		 1.3. Se busca la posición "max" que ocupa la primera carretera entrante
		 distinta de "ri" con el mayor número de vehículos en su cola.
		 1.4. "indiceSemaforoVerde" se pone a "max".
		 1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
		 a verde y se inicializan los atributos de "rj", entre ellos el
		 "intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1)
		*/
	}
	
	//Devuelve el indice de la carreteraEntrante con mas vehiculos en su cola
	private int carreteraConMasVehiculos(int ind){
		int indice = ind;
		int max = -2;
		for(int i = 0; i < carreterasEntrantes.size(); i++){
			
			if(i != ind && max < carreterasEntrantes.get(i).tamanoCola()){
				indice = i;
				max = carreterasEntrantes.get(i).tamanoCola();
			}
		}
		return indice;
	}
	
	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, 0, 0);
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String semaforo;
		String report = "";
		
		if(carreterasEntrantes.size() > 0){
			
			for(int i = 0; i < carreterasEntrantes.size(); i++){
				report += "(";
				
				if(carreterasEntrantes.get(i).getSemaforo()){
					semaforo = "green";
					report += carreterasEntrantes.get(i).toString() + "," + semaforo + ":1" 
							+ "," + carreterasEntrantes.get(i).listarVehiculos();
				}
				else{
					semaforo = "red";
					report += carreterasEntrantes.get(i).toString() + "," + semaforo 
							+ "," + carreterasEntrantes.get(i).listarVehiculos();
				}

				report += ")";
				
				if(i < carreterasEntrantes.size() - 1){
					report += ",";
				}
			}
		}

		is.setValue("queues", report);
		is.setValue("type", "mc");
		
	}

}
