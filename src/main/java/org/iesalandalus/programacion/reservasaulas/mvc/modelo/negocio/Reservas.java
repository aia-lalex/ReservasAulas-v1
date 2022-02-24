package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Reservas {

	Reserva[] coleccionReservas;
	private int capacidad;
	private int tamano;


	public Reservas(int capacidad) {
		this.capacidad = capacidad;
		coleccionReservas = new Reserva[capacidad];
		this.tamano = 0;
	}

	public Reserva[] get() {
		return copiaProfundaReservas(coleccionReservas);
	}


	private Reserva[] copiaProfundaReservas(Reserva[] reservas) {
		Reserva[] otrasReservas = new Reserva[reservas.length];
		for (int i = 0; i < reservas.length && reservas[i] != null; i++) {
			otrasReservas[i] = new Reserva(reservas[i]);
		}
		return otrasReservas;
	}	    

	public int getCapacidad() {
		return capacidad;
	}

	public int getTamano() {
		return tamano;
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("ERROR: No se puede realizar una reserva nula.");
		}
		int indice = buscarIndiceReserva(reserva);
		if (!tamanoSuperado(indice)) {
			coleccionReservas[indice] = reserva;
			tamano++;
		} else {
			if (capacidadSuperada(indice)) {
				throw new OperationNotSupportedException("ERROR: La reserva ya existe.");
			} else {
				throw new OperationNotSupportedException("ERROR: No se aceptan más reservas.");
			}		}
	}
	public Reserva[] getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}


	public int getNumReservas() {
		return tamano;
	}

	private int buscarIndiceReserva(Reserva reserva) {
		int indice = 0;
		boolean reservaEncontrada = false;
		while (tamanoSuperado(indice) && !reservaEncontrada) {
			if (coleccionReservas[indice].equals(reserva)) {
				reservaEncontrada = true;
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
		return indice > capacidad;
	}

	public Reserva buscar(Reserva reserva) {
		int indice = 0;
		indice = buscarIndiceReserva(reserva);
		if (tamanoSuperado(indice)) {
			return null;
		} else {
			return coleccionReservas[indice];
		}
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("ERROR: No se puede anular una reserva nula.");
		}
		int indice = buscarIndiceReserva(reserva);
		if (tamanoSuperado(indice)) {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		}
		else {
			desplazarUnaPosicionHaciaIzquierda(indice);

		}
	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < tamano - 1; i++) {
			coleccionReservas[i] = coleccionReservas[i+1];
		}
		coleccionReservas [tamano] = null;
		tamano--;
	}

	public String[] representar() {
		String[] representacion = new String[tamano];
		for (int i = 0; tamanoSuperado(i); i++) {
			representacion[i] = coleccionReservas[i].toString();
		}
		return representacion;
	}

	public Reserva[] getReservasAula(Aula aula) {

		if(aula==null)
			throw new IllegalArgumentException("ERROR: No se pueden comprobar las reservas realizadas sobre un aula nula.");
		Reserva[] reservaAula = new Reserva[capacidad];
		int indice = 0;
		for(int i = 0; i<tamano; i++) {
			if(coleccionReservas[i]!=null && coleccionReservas[i].getAula().equals(aula))   
				reservaAula[indice] = new Reserva(coleccionReservas[i]);
			indice++;
		}

		return reservaAula;
	}




	public Reserva[] getReservasProfesor(Profesor profesor) {

		if(profesor==null)
			throw new IllegalArgumentException("ERROR: No se pueden comprobar las reservas de un profesor nulo.");
		Reserva[] reservaProfesor = new Reserva[capacidad];
		int indice = 0;
		for(int i = 0; i<tamano; i++) {
			if(coleccionReservas[i]!=null && coleccionReservas[i].getProfesor().equals(profesor)) 
				reservaProfesor[indice] = new Reserva(coleccionReservas[i]);
			indice++;
		}

		return reservaProfesor;
	}


	public Reserva[] getReservasPermanencia(Permanencia permanencia) {
		if(permanencia==null)
			throw new IllegalArgumentException("ERROR: No se pueden consultar las reservas de una permanencia nula.");
		Reserva[] reservaPermanencia = new Reserva[capacidad];
		int reserva=0;
		for(int i=0; i<tamano; i++) {

			if(coleccionReservas[i]!=null && coleccionReservas[i].getPermanencia().equals(permanencia)) 
				reservaPermanencia[i] = new Reserva(coleccionReservas[i]);
			reserva++;
		}

		return reservaPermanencia;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if(aula==null)
			throw new IllegalArgumentException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		if(permanencia==null)
			throw new IllegalArgumentException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		for(int i = 0; i<coleccionReservas.length && coleccionReservas[i]!=null; i++) {
			if(coleccionReservas[i].getAula().equals(aula) && coleccionReservas[i].getPermanencia().equals(permanencia))
				return false;
		}
		return true;
	}

}
