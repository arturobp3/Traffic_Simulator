package mvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import codigoCampus.Ini;
import codigoCampus.IniSection;
import constructorEventos.ParserEventos;
import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.modelo.ObservadorSimuladorTrafico;
import mvc.modelo.SimuladorTrafico;
import mvc.vistaGUI.VentanaPrincipal;
import mvc.vistaGUI.barras.TextOutput;

public class Controlador {

// ATRIBUTOS:
	
	 private SimuladorTrafico simulador;
	 private OutputStream ficheroSalida;
	 private InputStream ficheroEntrada;
	 private int pasosSimulacion;
	 //...
	 
// CONSTRUCTORA:
	 
	public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {
		 simulador = sim;
		 ficheroSalida = os;
		 ficheroEntrada = is;
		 pasosSimulacion = limiteTiempo;
	}


// METODOS:
	
	public void ejecuta() throws ErrorDeSimulacion, IOException {
		this.cargaEventos(this.ficheroEntrada);
		this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
	}
	 
	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		 Ini ini;
		 try {
			 // lee el fichero y carga su atributo iniSections
			 ini = new Ini(inStream);
		 }
		 catch (IOException e) {
			 throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		 }
		 // recorremos todas los elementos de iniSections para generar el evento
		 // correspondiente
		 for (IniSection sec : ini.getSections()) {
			 // parseamos la sección para ver a que evento corresponde
			 Evento e = ParserEventos.parseaEvento(sec);
			 if (e != null) this.simulador.insertaEvento(e);
			 else
				 throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		 }
	}
	 
	
	public void ejecuta(int pasos) throws ErrorDeSimulacion, IOException {
		this.simulador.ejecuta(pasos, this.ficheroSalida);
	}
	
	public void reinicia() throws FileNotFoundException { this.simulador.reinicia(); }
	

	
	public void addObserver(ObservadorSimuladorTrafico o) {
		this.simulador.addObservador(o);
	}
	
	public void removeObserver(ObservadorSimuladorTrafico o) {
		this.simulador.removeObservador(o);
	}
	
	
	public String getReport() {
		return this.simulador.getReport();
	}
	
// Metodo para las tablas:
	
	public List<Evento> getListEventos() {
		return this.simulador.getEventos();
	}
	
	public List<CruceGenerico<?>> getListCruces() {
		
		return this.simulador.getCruces();
	}
	
	public List<Carretera> getListCarreteras() {
		
		return this.simulador.getCarreteras();
	}
	
	public List<Vehiculo> getListVehiculos() {
		
		return this.simulador.getVehiculos();
	}
	
	
	//Metodos para el TextOutput
	
	public void redirigeSalida(VentanaPrincipal mainWindow){
		ficheroSalida = new TextOutput(mainWindow);
	}
	public void corrigeSalida(){
		ficheroSalida = null;
	}
}
