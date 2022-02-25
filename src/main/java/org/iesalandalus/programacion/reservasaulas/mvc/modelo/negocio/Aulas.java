package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;

public class Aulas {

	private List <Aula> coleccionAulas;

	// constructor por defecto

	public Aulas(int capacidad) throws IllegalArgumentException,
	NullPointerException {
		if (capacidad < 1) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}
		coleccionAulas = new ArrayList<>();
	}

	public Aulas(Aulas aulas) {
		setAulas(aulas);

	}

	private void setAulas(Aulas aulas) {
		if (aulas == null) {
			throw new IllegalArgumentException("ERROR: No se pueden copiar aulas nulas.");
		}
		coleccionAulas = copiaProfundaAulas(aulas.coleccionAulas);

	}

	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}

	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> otrasAulas = new ArrayList<>();
		for (Aula aula : aulas) {
			otrasAulas.add(new Aula(aula));
		}
		return otrasAulas;
	}

	public int getNumAulas() {
		return coleccionAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("ERROR: No se puede insertar un aula nula.");
		}
		if (coleccionAulas.contains(aula)){
			throw new OperationNotSupportedException("ERROR: No se aceptan más aulas.");

		} else coleccionAulas.add(new Aula(aula));
	}




	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un aula nula.");
		}

		if (!coleccionAulas.remove(aula)) {
			throw new OperationNotSupportedException("ERROR: El aula a borrar no existe.");
		}
	}


	// To string

	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		for ( Aula aula : coleccionAulas){
			representacion.add(aula.toString());
		}
		return representacion;
	}



}