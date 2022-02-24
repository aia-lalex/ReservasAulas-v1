package org.iesalandalus.programacion.reservasaulas.mvc.vista;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.Reservas;

public class Vista {


	private static final String ERROR = "ERROR: ";
	private static final String NOMBRE_VALIDO = "Alex";
	private static final String CORREO_VALIDO = "aia-lalex@hotmail.es";
	private Controlador controlador;


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
		System.out.println("Aplicación finalizada");;
	}

	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		try {
			Aula aula = Consola.leerAula();			
			controlador.insertarAula(aula);
			System.out.println("Aula insertado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		try {
			Aula aula = Consola.leerNombreAula();
			controlador.borrarAula(aula);
			System.out.println("Aula borrada correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula = null;
		try {
			aula = Consola.leerNombreAula();
			aula = controlador.buscarAula(aula);
			if (aula != null) {
				System.out.println("El aula buscado es: " + aula);
			} else {
				System.out.println("No existe ningún aula con dicho nombre.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarAulas() {
		Consola.mostrarCabecera("Listar aulas");
		String[] aulas = controlador.representarAulas();
		if (aulas.length > 0) {
			for (String aula : aulas) {
				System.out.println(aula);
			}
		} else {
			System.out.println("No hay aulas que listar.");
		}
	}
	//profesores       
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {
			Profesor profesor = Consola.leerProfesor();
			controlador.insertarProfesor(profesor);
			System.out.println("Profesor insertado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void borrarProfesor() {
		String nombre="";
		Consola.mostrarCabecera("Borrar profesor");
		try {
			nombre=Consola.leerNombreProfesor();
			Profesor profesor =new Profesor(nombre,CORREO_VALIDO);
			controlador.borrarProfesor(profesor);
			System.out.println("Profesor borrado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor = null;
		String nombre="";
		try {
			nombre = Consola.leerNombreProfesor();
			profesor=new Profesor(nombre,CORREO_VALIDO);
			profesor = controlador.buscarProfesor(profesor);
			if (profesor != null) {
				System.out.println("El profesor buscado es: " + profesor);
			} else {
				System.out.println("No existe ningún profesor con dicho nombre.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarProfesores() {
		Consola.mostrarCabecera("Listar profesores");
		String[] profesores = controlador.representarProfesores();
		if (profesores.length > 0) {
			for (String profesor : profesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que listar.");
		}
	}




	private Reserva leerReserva(Profesor profesor) {
		Profesor profesorAEncontrar = controlador.buscarProfesor(profesor);
		if(profesorAEncontrar == null)
			return null;
		Aula buscada = controlador.buscarAula(new Aula(Consola.leerNombreAula()));
		if(buscada==null)
			return null;
		return new Reserva(profesorAEncontrar, buscada, new Permanencia(Consola.leerDia(), Consola.leerTramo()));
	}

	public void realizarReserva() {
		Consola.mostrarCabecera("REALIZAR RESERVA");
		Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
		boolean lecturaCorrecta = true;
		if(controlador.buscarProfesor(profesor)==null) {
			System.out.println(ERROR + "El profesor introducido no existe.");
			lecturaCorrecta = false;
		}
		Reserva reserva = null;
		if(lecturaCorrecta) {
			reserva = leerReserva(profesor);
			if(reserva==null)
				System.out.println(ERROR + "El aula introducida no existe.");
		}
		if(reserva==null)
			System.out.println(ERROR + "La reserva no se pudo realizar.");
		else {
			try {
				controlador.realizarReserva(reserva);
				System.out.println("Reserva realizada correctamente.");
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
		}
	}


	public void anularReserva() {
		Consola.mostrarCabecera("ANULAR RESERVA");
		Profesor profesorABuscar = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
		boolean lecturaCorrecta = true;
		if(controlador.buscarProfesor(profesorABuscar)==null) {
			System.out.println("El profesor introducido no existe.");
			lecturaCorrecta = false;
		}
		Reserva reserva = null;
		if(lecturaCorrecta) {
			reserva = leerReserva(profesorABuscar);
			if(reserva==null)
				System.out.println("El aula introducida no existe.");
		}
		if(reserva==null)
			System.out.println("La reserva no se pudo anular.");
		else {
			try {
				controlador.anularReserva(reserva);
			} catch (OperationNotSupportedException e) {
				System.out.println(ERROR + e.getMessage());
			}
			System.out.println("Reserva anulada correctamente.");
		}
	}


	public void listarReservas() {
		Consola.mostrarCabecera("LISTAR RESERVAS");
		String[] reservas = controlador.representarReservas();
		if(reservas.length==0)
			System.out.println("No hay ninguna reserva hecha.");
		for(int i = 0; i<reservas.length && reservas[i]!=null; i++)
			System.out.println(reservas[i]);
	}

	public void listarReservasAula() {
		Consola.mostrarCabecera("LISTAR RESERVAS AULA");
		Aula aula = new Aula(Consola.leerNombreAula());
		boolean lecturaCorrecta = true;
		if(controlador.buscarAula(aula)==null){
			System.out.println(ERROR + "El aula introducida no existe.");
			lecturaCorrecta = false;
		}
		Reserva[] reservas = controlador.getReservasAula(aula);
		if(lecturaCorrecta && reservas[0] == null)
			System.out.println("El aula indicada no está reservada.");
		if(lecturaCorrecta) {
			for(int i = 0; i<reservas.length && reservas[i]!=null; i++)
				System.out.println(reservas[i]);
		}
	}


	public void listarReservasProfesor() {
		Consola.mostrarCabecera("LISTAR RESERVAS PROFESOR");
		Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
		boolean lecturaCorrecta = true;
		if(controlador.buscarProfesor(profesor)==null){
			System.out.println(ERROR + "El profesor introducida no existe.");
			lecturaCorrecta = false;
		}
		Reserva[] reservas = controlador.getReservasProfesor(profesor);
		if(lecturaCorrecta && reservas[0]==null)
			System.out.println("El profesor indicado no tiene ningún aula reservada.");
		if(lecturaCorrecta) {
			for(int i = 0; i<reservas.length && reservas[i]!=null; i++)
				System.out.println(reservas[i]);
		}
	}

	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("LISTAR RESERVAS PERMANENCIA");
		Permanencia permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());
		Reserva[] reservas = controlador.getReservasPermanencia(permanencia);
		if(reservas[0]==null)
			System.out.println("En ese tramo no hay ningún aula reservada.");
		for(int i = 0; i<reservas.length && reservas[i]!=null; i++)
			System.out.println(reservas[i]);
	}

	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("CONSULTAR DISPONIBILIDAD");
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

