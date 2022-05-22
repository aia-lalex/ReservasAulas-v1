package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public class Profesores {

	private List <Profesor> coleccionProfesores;



	public Profesores(int capacidad) {
		coleccionProfesores = new ArrayList<>();
	}

	public Profesores (Profesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		this.coleccionProfesores = profesores.getProfesores();
	}


	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> otrosProfesores = new ArrayList<>();
		for (Profesor profesor : profesores){
			otrosProfesores.add(new Profesor(profesor));
		}
		return otrosProfesores;
	}

	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(coleccionProfesores);
	}



	public int getNumProfesores() {
		return coleccionProfesores.size();
	}



		public void insertar(Profesor profesor) throws OperationNotSupportedException {
			if (profesor == null) {
				throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
			}
			if (coleccionProfesores.contains(profesor)) {
				throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
			} else {
				coleccionProfesores.add(profesor);
			}

		}
		public Profesor buscar(Profesor profesor) throws IllegalArgumentException, NullPointerException {
			if (profesor == null) {
				throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
			}
			Iterator<Profesor> it = coleccionProfesores.iterator();
			while (it.hasNext()) {
				if (it.next().equals(profesor)) {
					return new Profesor(profesor);
				}
			}
			return null;

		}
		
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		boolean borrado = false;
		Iterator<Profesor> it = coleccionProfesores.iterator();
		while (it.hasNext()) {
			if (it.next().equals(profesor)) {
				it.remove();
				borrado = true;
			}
		}
		if (!borrado) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
		} 
	}

	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		for (Profesor profesor : coleccionProfesores){
			representacion.add(profesor.toString());
		}
		return representacion;
	}



}
