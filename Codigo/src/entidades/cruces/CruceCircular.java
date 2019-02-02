package entidades.cruces;

import codigoCampus.IniSection;
import entidades.carreteras.Carretera;
import entidades.carreteras.CarreteraEntranteConIntervalo;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {

	protected int minValorIntervalo;
	protected int maxValorIntervalo;

	
	public CruceCircular(String identificador, Integer minValorIntervalo2, Integer maxValorIntervalo2) {
		super(identificador);
		minValorIntervalo = minValorIntervalo2;
		maxValorIntervalo = maxValorIntervalo2;
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, maxValorIntervalo, -1);
	}

	@Override
	protected void actualizaSemaforos() {
		indiceSemaforoVerde++;
		
		int indice = (this.indiceSemaforoVerde) % carreterasEntrantes.size();
		int indice2 = (this.indiceSemaforoVerde + 1) % carreterasEntrantes.size();
		
		if(carreterasEntrantes.get(indice2).tiempoConsumido()){
			
			carreterasEntrantes.get(indice2).ponSemaforo(false);
			
			if(carreterasEntrantes.get(indice2).usoCompleto()){
				
				carreterasEntrantes.get(indice2).setIntervaloDeTiempo(
						Math.min(
								(carreterasEntrantes.get(indice2).getIntervaloDeTiempo() + 1), maxValorIntervalo
								)
				);
			}
			else if(!carreterasEntrantes.get(indice2).usada()){
				
				carreterasEntrantes.get(indice2).setIntervaloDeTiempo(
						Math.max(
								(carreterasEntrantes.get(indice2).getIntervaloDeTiempo() - 1), minValorIntervalo
								)
				);
			}
			
			carreterasEntrantes.get(indice2).setUnidadesDeTiempoUsadas(0);
			carreterasEntrantes.get(indice).setUnidadesDeTiempoUsadas(0);

			this.carreterasEntrantes.get(indice).ponSemaforo(true);
			
		}
		else{
			if(!carreterasEntrantes.get(indice).getSemaforo()) this.carreterasEntrantes.get(indice2).ponSemaforo(true);
			indiceSemaforoVerde++;
		}
		
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String semaforo;
		String report = "";
		int unidadesRestantes;
		
		if(carreterasEntrantes.size() > 0){
			
			for(int i = 0; i < carreterasEntrantes.size(); i++){
				report += "(";
				
				unidadesRestantes = carreterasEntrantes.get(i).getIntervaloDeTiempo() - carreterasEntrantes.get(i).getUnidadesDeTiempoUsadas();
				
				if(carreterasEntrantes.get(i).getSemaforo()){
					semaforo = "green";
					report += carreterasEntrantes.get(i).toString() + "," + semaforo + ":" + unidadesRestantes
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
		is.setValue("type", "rr");
		
	}

}
