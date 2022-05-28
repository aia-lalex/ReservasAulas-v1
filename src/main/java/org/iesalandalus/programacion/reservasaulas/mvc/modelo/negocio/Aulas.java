package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

public class Aulas {
	
	

	private List <Aula> coleccionAulas = new ArrayList<>();

	public Aulas() {

	}
	// constructor por defecto


	public Aulas(Aulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		coleccionAulas = aulas.getAulas();
	}
	


	public List<Aula> getAulas() {
		return copiaProfundaAulas();
	}

	private List<Aula> copiaProfundaAulas() throws IllegalArgumentException, NullPointerException {
		List<Aula> copiaAulas = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			copiaAulas.add(new Aula(it.next()));
		}
		return copiaAulas;
	}
	

	public int getNumAulas() {
		return coleccionAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		if (coleccionAulas.contains(aula)){
			throw new OperationNotSupportedException("ERROR: No se aceptan más aulas.");

		} else coleccionAulas.add(new Aula(aula));
	}




	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		boolean borrado = false;
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			if (it.next().equals(aula)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} 
	}
	
	public Aula buscar(Aula aula) throws IllegalArgumentException, NullPointerException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			if (it.next().equals(aula)) {
				return new Aula(aula);
			}
		}
		return null;

	}


	// To string

	public List<String> representar() {
		List<String> cadena = new ArrayList<>();
		Iterator<Aula> it = coleccionAulas.iterator();
		while (it.hasNext()) {
			cadena.add(it.next().toString());
		}
		return cadena;
	}


}