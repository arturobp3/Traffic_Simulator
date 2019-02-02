package entidades;

import codigoCampus.IniSection;
import excepciones.ErrorDeSimulacion;

public abstract class ObjetoSimulacion {
	
// ATRIBUTOS:
	
	private String id;
	

// CONSTRUCTOR:
	
	public ObjetoSimulacion(String identificador) {
		super();
		this.id = identificador;
	}
	
// METODOS:
	
	public String getIdentificador() {
		return id;
	}
	
	@Override
	public String toString() {
		// Falta por completar¿?
		return "";
	}
	
	public abstract void avanza() throws ErrorDeSimulacion;
	
	public String generaInforme(int tiempo) {
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		return is.toString();
	}
	
	protected abstract String getNombreSeccion();
	protected abstract void completaDetallesSeccion(IniSection is);
}
