package entidades.vehiculos;

import java.util.List;
import java.util.Random;

import codigoCampus.IniSection;
import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;

public class Coche extends Vehiculo {

// ATRIBUTOS:
	
	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;


// CONSTRUCTOR:
	
	public Coche(String id, int velocidadMaxima, int resistencia, double probabilidad, long semilla, 
			int duracionMaximaInfraccion, List<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion {
		
		super(id, velocidadMaxima, itinerario);
		this.resistenciaKm = resistencia;
		this.probabilidadDeAveria = probabilidad;
		this.duracionMaximaAveria = duracionMaximaInfraccion;
		this.numAleatorio = new Random(semilla);
	}
	

// METODOS:
	
	@Override
	public void avanza() {
		// - Si el coche está averiado poner “kmUltimaAveria” a “kilometraje”.
		// - Sino el coche no está averiado y ha recorrido “resistenciakm”, y además
		// “numAleatorio”.nextDouble() < “probabilidadDeAveria”, entonces
		// incrementar “tiempoAveria” con “numAleatorio.nextInt(“duracionMaximaAveria”) + 1
		// - Llamar a super.avanza();
		
		
		if (tiempoAveria > 0) { // Si el coche esta averiado
			this.kmUltimaAveria = this.kilometraje;
		}
		else {
			//double num = this.numAleatorio.nextDouble();

			if (this.kilometraje >= this.resistenciaKm && this.numAleatorio.nextDouble() < this.probabilidadDeAveria) {
				this.tiempoAveria += this.numAleatorio.nextInt(this.duracionMaximaAveria) + 1;
				this.velocidadActual = 0;
				this.averia = true;
			}
		}
		super.avanza();
	}
			
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "car");
	}

}
