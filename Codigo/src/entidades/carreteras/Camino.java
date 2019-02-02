package entidades.carreteras;

import codigoCampus.IniSection;
import entidades.cruces.CruceGenerico;

public class Camino extends Carretera {

// CONSTRUCTOR:
	
	public Camino(String id, int longitud, int velocidadMaxima, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		super(id, longitud, velocidadMaxima, cruceOrigen, cruceDestino);
	}
	
	
// METODOS:
	
	@Override
	protected int calculaVelocidadBase() { return this.velocidadMaxima; }
			
	@Override 
	protected int calculaFactorReduccion(int obstacles) {	 
		return obstacles + 1;
	}
			 
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "dirt");
	}
	
}
