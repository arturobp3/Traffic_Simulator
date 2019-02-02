package entidades.carreteras;

import codigoCampus.IniSection;
import entidades.cruces.CruceGenerico;

public class Autopista extends Carretera {

// ATRIBUTOS:
	
	private int numCarriles;
	
	
// CONSTRUCTOR:
	
	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino, int numCarriles) {
		super(id, length, maxSpeed, cruceOrigen, cruceDestino);
		this.numCarriles = numCarriles;
	}
	
// METODOS:
	
	@Override
	protected int calculaVelocidadBase() {
		return Math.min(this.velocidadMaxima, ((this.velocidadMaxima * this.numCarriles)/Math.max(this.vehiculos.size(), 1)) + 1);
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "lanes");
	}
	
	@Override
	protected int calculaFactorReduccion(int obstacles) {
		return obstacles < this.numCarriles ? 1 : 2;
	}

}
