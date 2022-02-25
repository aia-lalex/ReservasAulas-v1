package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;

public class Profesores {

	private List <Profesor> coleccionProfesores;



	public Profesores(int capacidad) {
		coleccionProfesores = new ArrayList<>();
	}

	public Profesores (Profesores profesores) {
		setProfesores(profesores);
	}



	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new IllegalArgumentException("No se pueden copiar profesores nulos.");
		}
		coleccionProfesores = copiaProfundaProfesores(profesores.coleccionProfesores);

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
			throw new IllegalArgumentException("No se puede insertar un profesor nulo.");
		}

		if (coleccionProfesores.contains(profesor))
		{
			throw new OperationNotSupportedException("El profesor ya existe.");
		}
		else coleccionProfesores.add(new Profesor(profesor));


	}


	public Profesor buscar(Profesor profesor) {
		int indice = coleccionProfesores.indexOf(profesor);

		if (indice != -1) {
			return new Profesor (coleccionProfesores.get(indice));
		} else {
			return null;
		}
	}


	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}

		if (!coleccionProfesores.remove(profesor)) {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
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
