package org.iesalandalus.programacion.reservasaulas.mvc.vista;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.Reservas;

public class Vista {


	private static final String ERROR = "ERROR: ";
	private static final String NOMBRE_VALIDO = "Alex";
	private static final String CORREO_VALIDO = "aia-lalex@hotmail.es";
	private static Controlador controlador;

	public Vista() {

		Opcion.setVista(this);
	}
	public void setControlador(Controlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}

	public void comenzar() {
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	public void salir() {
		controlador.terminar();
		System.out.println("Aplicación finalizada");;
	}

	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		try {
			controlador.insertarAula( Consola.leerAula());		
			System.out.println("Aula insertado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		try {
			controlador.borrarAula(Consola.leerNombreAula());
			System.out.println("ERROR: Aula borrada correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula;
		try {
			aula = controlador.buscarAula(Consola.leerAula());
			if (aula != null) {
				System.out.println("El aula buscada es: " + aula);
			} else {
				System.out.println("ERROR: No existe ningún aula con dicho nombre.");
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}


		public void listarAulas() {
			Consola.mostrarCabecera("LISTADO DE AULAS");
			List<String> listaAulas = controlador.representarAulas();
			if (!listaAulas.isEmpty()) {
				Iterator<String> it = listaAulas.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
				}
			} else {
				System.out.println("No hay aulas que mostrar");
			}
		}  
	
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {
			controlador.insertarProfesor(Consola.leerProfesor()); 
			System.out.println("Profesor insertado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
	
	// creamos borrarProfesor
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		try {
			controlador.borrarProfesor(Consola.leerProfesor());
			System.out.println("Profesor borrado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor;
		try {
			profesor = controlador.buscarProfesor(Consola.leerProfesor());
			if (profesor == null) {
				System.out.println("No existe ningún profesor con dicho nombre.");
			} else {
				System.out.println(profesor.toString());
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	
		public void listarProfesores() {
			Consola.mostrarCabecera("Listar profesores");
			List<String> listaProfesores = controlador.representarProfesores();
			if (!listaProfesores.isEmpty()) {
				Iterator<String> it = listaProfesores.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
				}
			} else {
				System.out.println("No hay profesores que listar");
			}
		}

		private Reserva leerReserva() {
			Reserva reserva = null;
			boolean centinela = false;
			boolean centinelaDisponibilidad = false;
			Aula aula = null;
			Permanencia permanencia = null;
			do {
				do {
					aula = Consola.leerAula();
					permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
					if (controlador.consultarDisponibilidad(aula, permanencia)) {
						centinelaDisponibilidad = true;
					}
				} while (!centinelaDisponibilidad);
				try {
					reserva = new Reserva(Consola.leerProfesor(), aula, permanencia);
					centinela = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} while (!centinela);
			return reserva;
		}


		public void realizarReserva() {
			try {
				System.out.println("Introduce los datos para realizar la reserva");
				Reserva reserva = leerReserva();
				Boolean centinela = true;
				if (controlador.buscarAula(reserva.getAula()) == null) {
					System.out.println("El aula no esta en listado");
					centinela = false;
				}
				if (controlador.buscarProfesor(reserva.getProfesor()) == null) {
					System.out.println("El profesor no esta en listado. ");
					centinela = false;
				}
				if (centinela) {
					controlador.realizarReserva(reserva);
					System.out.println("Reserva realizada con exito.");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	public void anularReserva() {
		Consola.mostrarCabecera("Borrar reserva");
		Profesor profesorABuscar = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
		boolean lecturaCorrecta = true;
		if(controlador.buscarProfesor(profesorABuscar)==null) {
			System.out.println("El profesor introducido no existe.");
			lecturaCorrecta = false;
		}
		Reserva reserva = null;
		if(lecturaCorrecta) {
			reserva = leerReserva();
			if(reserva==null)
				System.out.println("El aula introducida no existe.");
		}
		if(reserva==null)
			System.out.println("La reserva no se pudo anular.");
		else {
			try {
				controlador.anularReserva(reserva);
			} catch (OperationNotSupportedException | NullPointerException e) {
				System.out.println(ERROR + e.getMessage());
			}
			System.out.println("Reserva anulada correctamente.");
		}
	}


	public void listarReservas() {
	
			Consola.mostrarCabecera("Lista reservas");
			List<String> reservas = controlador.representarReservas();
			if (!reservas.isEmpty()) {
				Iterator<String> it = reservas.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
				}
			} else {
				System.out.println("No hay reservas para mostrar");
			}
		}

	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas por aula");
		Aula aula = new Aula(Consola.leerNombreAula());
		boolean lecturaCorrecta = true;
		if(controlador.buscarAula (aula) == null){
			System.out.println(ERROR + "El aula introducida no existe.");
			lecturaCorrecta = false;
		}
		List<Reserva> reservas = controlador.getReservasAula(aula);
		if(lecturaCorrecta && reservas.isEmpty())
			System.out.println("El aula indicada no está reservada.");
		if(lecturaCorrecta) {
			reservas.forEach((_item) -> {
				System.out.println(reservas);
			});
		}
	}
	
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listado reservas por profesor");
		List<Reserva> reservas = controlador.getReservasProfesor(Consola.leerProfesor());
		if (!reservas.isEmpty()) {
			Iterator<Reserva> it = reservas.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("El profesor indicado no tiene ningún aula reservada.");
		}
	}


	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("Listar reservas por permanencia");
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		List<Reserva> reservas = controlador.getReservasPermanencia(permanencia);
		if(reservas.isEmpty())
			System.out.println("En ese tramo no hay ningún aula reservada.");
		reservas.forEach((_item) -> {
			System.out.println(reservas);
		});
	}


	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar disponibilidad");
		Aula aula = new Aula(Consola.leerNombreAula());
		boolean lecturaCorrecta = true;
		if(controlador.buscarAula(aula) == null) {
			System.out.println(ERROR + "El aula indicada no existe.");
			lecturaCorrecta = false;
		}
		if(lecturaCorrecta) {
			Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
			boolean disponible = controlador.consultarDisponibilidad(aula, permanencia);
			if(disponible)
				System.out.println("El aula consultada está disponible para el tramo especificado.");
			else
				System.out.println("El aula consultada no está disponible para el tramo especificado.");
		}
	}
}

		
	