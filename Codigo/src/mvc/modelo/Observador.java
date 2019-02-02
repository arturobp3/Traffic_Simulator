package mvc.modelo;

public interface Observador<T> {

// METODOS:
	
	public void addObservador(T o);
	
	public void removeObservador(T o);
}
