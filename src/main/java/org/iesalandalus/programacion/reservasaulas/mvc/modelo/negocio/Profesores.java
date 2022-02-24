package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public class Profesores {

	private Profesor[] coleccionProfesores;
	private int capacidad;
	private int tamano;


	public Profesores(int capacidad) {
		this.capacidad = capacidad;
		coleccionProfesores = new Profesor[capacidad];
		this.tamano = 0;
	}

	public Profesor[] get() {
		return copiaProfundaProfesores(coleccionProfesores);
	}


	private Profesor[] copiaProfundaProfesores(Profesor[] profesores) {
		Profesor[] otrosProfesores = new Profesor[profesores.length];
		for (int i = 0; i < profesores.length && profesores[i] != null; i++) {
			otrosProfesores[i] = new Profesor(profesores[i]);
		}
		return otrosProfesores;
	}


	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}    


	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más profesores.");
		} else if(capacidadSuperada(indice)) {			
			coleccionProfesores[indice] = profesor;
			this.capacidad++;
		} else {
			throw new OperationNotSupportedException("ERROR: El profesor ya existe.");
		}
	}
	private int buscarIndice(Profesor profesor) {
		int indice = 0;
		boolean profesorEncontrado = false;
		while (!tamanoSuperado(indice) && !profesorEncontrado) {
			if (coleccionProfesores[indice].equals(profesor)) {
				profesorEncontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	private boolean tamanoSuperado(int indice) {
		return indice > tamano;
	}

	private boolean capacidadSuperada(int indice) {

		return indice > tamano;
	}


	public Profesor buscar(Profesor profesor) {
		int indice = 0;
		indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return coleccionProfesores[indice];

		}
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		int indice = buscarIndice(profesor);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: No se puede buscar un prefesor nulo.");
		}
		else {

			desplazarUnaPosicionHaciaIzquierda(indice);

		}
	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		int i = indice;
		for (i = indice; !tamanoSuperado(i); i++) {
			coleccionProfesores[i] = coleccionProfesores[i+1];
		}
		coleccionProfesores [i] = null;
		tamano--;
	}

	public String[] representar() {
		String[] representacion = new String[tamano];
		for (int i = 0; tamanoSuperado(i); i++) {
			representacion[i] = coleccionProfesores[i].toString();
		}
		return representacion;
	}

}
