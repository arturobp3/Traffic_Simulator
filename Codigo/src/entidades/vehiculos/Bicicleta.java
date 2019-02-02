package entidades.vehiculos;

import java.util.List;

import codigoCampus.IniSection;
import entidades.cruces.CruceGenerico;
import excepciones.ErrorDeSimulacion;

public class Bicicleta extends Vehiculo {

// CONSTRUCTOR:
	
	public Bicicleta(String id, int velMaxima, List<CruceGenerico<?>> iti) throws ErrorDeSimulacion {
		super(id, velMaxima, iti);
	}
	

// METODOS: 
	
	@Override
	public void avanza() {
		super.avanza();
	}
	
	@Override
	public void setTiempoAveria(Integer duracionAveria) {
		
		if (this.velocidadActual >= this.velocidadMaxima / 2) {
			super.setTiempoAveria(duracionAveria);
		}
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		super.completaDetallesSeccion(is);
		is.setValue("type", "bike");
	}
	
}
