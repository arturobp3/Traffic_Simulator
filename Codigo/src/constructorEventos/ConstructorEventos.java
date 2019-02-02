package constructorEventos;

import codigoCampus.IniSection;
import eventos.Evento;

public abstract class ConstructorEventos {

	// Cada clase dará los valores correspondientes a estos atributos en la constructora
	protected String etiqueta; // Etiqueta de la entrada (“new_road”, etc..)
	protected String[] claves; // Campos de la entrada (“time”, “vehicles”, etc.)
	
	
	protected String[] valoresPorDefecto;
	
	
	ConstructorEventos() {
		this.etiqueta = null;
		this.claves = null;
		this.valoresPorDefecto = null;
	}
	
	 //...
	
	public abstract Evento parser(IniSection section);
	 
	protected static String identificadorValido(IniSection seccion, String clave) {
		 String s = seccion.getValue(clave);
		 if (!esIdentificadorValido(s))
			 throw new IllegalArgumentException("El valor " + s + " para " + clave + " no es un ID valido");
		 else return s;
	}
	
	protected static String[] identificadorValido2(IniSection seccion, String clave) {
		 String s = seccion.getValue(clave);
		 String[] vehiculos = s.split(",");
		 boolean exito = true;
		 int i = 0;
		 for(; i < vehiculos.length && exito; i++){
			 if (!esIdentificadorValido(vehiculos[i])){
				 exito = false;
			 }
		 }

		 if(!exito) throw new IllegalArgumentException("El valor " + vehiculos[i] + " para " + clave + " no es un ID valido");
		 else return vehiculos;
	}
	 
	// identificadores válidos
	// sólo pueden contener letras, números y subrayados
	private static boolean esIdentificadorValido(String id) {
		 return id != null && id.matches("[a-z0-9_]+");
	}
	
	protected static int parseaInt(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		else return Integer.parseInt(seccion.getValue(clave));
	}
	
	protected static long parseaLong(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		else return Long.parseLong(seccion.getValue(clave));
	}
	
	protected static double parseaDouble(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		else return Double.parseDouble(seccion.getValue(clave));
	}
	
	protected static int parseaInt(IniSection seccion, String clave, int valorPorDefecto) {
		 String v = seccion.getValue(clave);
		 return (v != null) ? Integer.parseInt(seccion.getValue(clave)) : valorPorDefecto;
	}
	
	protected static int parseaIntNoNegativo(IniSection seccion, String clave, int valorPorDefecto) {
		 int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		 if (i < 0)
			 throw new IllegalArgumentException("El valor " + i + " para " + clave + " no es un ID valido");
		 else return i;
	}
	
	protected static String[] separator(IniSection seccion, String clave) {
		
		 String s = seccion.getValue(clave);
		 String [] aux = s.split(",");
		 
		 for(int i = 0; i < aux.length; i++){
			 if (!esIdentificadorValido(aux[i]))
				 throw new IllegalArgumentException("El valor " + aux[i] + " para " + clave + " no es un ID valido");
		 }

		return aux;
	}
	
	public String template(){
		
		String plantilla = "";
		
		plantilla = "[" + etiqueta + "]\n";
		
		for(int i = 0; i < claves.length; i++){
			plantilla += claves[i] + " = " + valoresPorDefecto[i] + "\n";
		}
		
		return plantilla;
	}
}
