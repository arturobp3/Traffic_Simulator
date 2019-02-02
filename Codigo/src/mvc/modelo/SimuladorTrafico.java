package mvc.modelo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import principal.MapaCarreteras;
import principal.SortedArrayList;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {

// ATRIBUTOS:
	
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private List<ObservadorSimuladorTrafico> observadores; // Nuevo atributo
	
// CONSTRUCTORA:
	
	public SimuladorTrafico() {
		 this.mapa = new MapaCarreteras();
		 this.contadorTiempo = 0;
		 Comparator<Evento> cmp = new Comparator<Evento>() {
			@Override
			public int compare(Evento arg0, Evento arg1) {
				if (arg0.getTiempo() == arg1.getTiempo()) return 0;
				else if (arg0.getTiempo() < arg1.getTiempo()) return -1;
				else return 1;
			}};
		 this.eventos = new SortedArrayList<Evento>(cmp); // estructura ordenada por “tiempo”
		 this.observadores = new ArrayList<>();
	}
	
	
// METODOS:
	
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion, IOException {
		 int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		 
		 while (this.contadorTiempo <= limiteTiempo) {
			 // ejecutar todos los eventos correspondientes a “this.contadorTiempo”
			 
			for(Evento i: eventos){
				if(i.getTiempo() == this.contadorTiempo){
					i.ejecuta(mapa);
				}
			}
			
			// Actualizar “mapa”
			try {
				mapa.actualizar();
				this.contadorTiempo++;
				this.notificaAvanza(); // Se ha producido el avance correctamente
			}
			catch (ErrorDeSimulacion e) {
				this.notificaError(e); // Notificamos la aparicion de un error
			}
			
			// Escribir el informe en “ficheroSalida”, controlando que no sea null.
			if(ficheroSalida != null) {
				
				// Crear informe llamando a "generaInforme" para cada objeto simulado
				// y escribirlo en ficheroSalida
				String informe = this.getReport();
				
				
				byte info[] = informe.getBytes(); //Copiamos el informe en un array de bytes
				
				ficheroSalida.write(info);
			}
		 }
	}
	
	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		 // inserta un evento en “eventos”, controlando que el tiempo de
		 // ejecución del evento sea menor que “contadorTiempo”
		
		if (e != null) {
			if (e.getTiempo() < this.contadorTiempo) {
				ErrorDeSimulacion err = new ErrorDeSimulacion();
				this.notificaError(err);
				throw err;
			} 
			else /*if(e.getTiempo() >= contadorTiempo)*/ {
				this.eventos.add(e);
				this.notificaNuevoEvento(); // Se notifica a los observadores
			}
		} 
		else {
			ErrorDeSimulacion err = new ErrorDeSimulacion();
			this.notificaError(err); // Se notifica a los observadores
			throw err;
		}
	}
	
	public void reinicia () {
		
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		Comparator<Evento> cmp = new Comparator<Evento>() {
			@Override
			public int compare(Evento arg0, Evento arg1) {
				if (arg0.getTiempo() == arg1.getTiempo()) return 0;
				else if (arg0.getTiempo() < arg1.getTiempo()) return -1;
				else return 1;
			}
		};
		
		this.eventos = new SortedArrayList<Evento>(cmp); // estructura ordenada por “tiempo”
		
		this.notificaReinicia(); // Se notifica el reinicio a los observadores
	}

	
	public String getReport() {
		return mapa.generateReport(contadorTiempo);
	}
	
	
// Metodos notificadores:
	
	private void notificaNuevoEvento() {
		
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.addEvento(this.contadorTiempo, this.mapa, this.eventos);
		}
	}
	
	private void notificaReinicia() {
		
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
		}
	}
	
	private void notificaError(ErrorDeSimulacion e) {
		
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, e);
		}
	}
	
	private void notificaAvanza() {
		
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.avanza(this.contadorTiempo, this.mapa, this.eventos);
		}
	}
	
// Metodos implementados de la interfaz Observador:
	
	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}
	}
	
// Metodos para las tablas:
	
	public List<Evento> getEventos() {
		
		return this.eventos;
	}
	
	public List<CruceGenerico<?>> getCruces() {
		
		return this.mapa.getCruces();
	}
	
	public List<Carretera> getCarreteras() {
		
		return this.mapa.getCarreteras();
	}
	
	public List<Vehiculo> getVehiculos() {
		
		return this.mapa.getVehiculos();
	}
	
}
