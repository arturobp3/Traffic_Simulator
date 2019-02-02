package principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import excepciones.ErrorDeSimulacion;

public class SortedArrayList<E> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Comparator<E> cmp;
	
	private int buscarPos(E e){ //Busca una posicion ordernada utilizando busqueda binaria
		int pos, ini = 0, fin = this.size() - 1, mitad = 0;
		boolean encontrado = false;
		boolean final_ = false;

		while ((ini <= fin) && !encontrado){
			mitad = (ini + fin) / 2;

			if (cmp.compare(this.get(mitad) , e) == 1){ //Compare devuelve 1 si "e" deberia ir antes que "mitad"				
				fin = mitad - 1;						// porque su orden lo establece
			}
			else if (cmp.compare(this.get(mitad) , e) == -1){//"e" debe ir despues de mitad
				ini = mitad + 1;
			}
			else if(cmp.compare(this.get(mitad) , e) == 0){ //El elemento se coloca al final
				final_ = true;
				encontrado = true;
			}
			else encontrado = true;
		}

		if (encontrado && !final_) pos = mitad;
		else if(encontrado && final_) pos = this.size(); //Se inserta al final
		else pos = ini; 

		return pos;
	}
	
	
	public SortedArrayList(Comparator<E> cmp) {
		this.cmp = cmp;
	}
	 
	@Override
	public boolean add(E e) {
		// programar la inserción ordenada
		
		int posOrdenada = buscarPos(e);
	
		super.add(posOrdenada, e);

		return true;
	}
	 
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// programar inserción ordenada (invocando a add)

		for(E i: c){ //Para cada elemento de tipo E de la lista c
			this.add(i); //Lo añade a la lista ordenada
		}
		
		return true;
	}


	// sobreescribir los métodos que realizan operaciones de
	// inserción basados en un índice para que lancen excepcion.
	// Ten en cuenta que esta operación rompería la ordenación.
	// estos métodos son add(int index, E element),
	// addAll(int index, Colection<? Extends E>) y E set(int index, E element).
	
	
	public void add(int index, E element) {
		try {
			throw new ErrorDeSimulacion();
		} catch (ErrorDeSimulacion e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			throw new ErrorDeSimulacion();
		} catch (ErrorDeSimulacion e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public E set(int index, E element) {
		
		try {
			throw new ErrorDeSimulacion();
		} catch (ErrorDeSimulacion e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}
