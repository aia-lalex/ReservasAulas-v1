package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	private Consola() {

	}

	public static void mostrarMenu() {
		mostrarCabecera("Gestión para profesores de reserva de aula");
		int i=0;
		for (Opcion opcion: Opcion.values()) {
			System.out.println(i + "." + opcion);                                        
			i++;
		}
	}

	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String cadena = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.println("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	public static Aula leerAula() {
		System.out.println("Introduce el nombre del aula: ");
		String nombreAula = Entrada.cadena();

		return new Aula(nombreAula);
	}

	public static Aula leerNombreAula() {
		String aula;
		do {
			System.out.println("Introduce el aula: ");
			aula = Entrada.cadena();
		} while (aula.trim().equals(""));

		return new Aula(aula);
	}            

	public static Profesor leerProfesor() {
		Profesor profesor = null;
		System.out.println("Introduce el nombre: ");
		String nombre = Entrada.cadena();
		System.out.println("Introduce el correo: ");
		String correo = Entrada.cadena();
		System.out.println("Introduce el teléfono: ");
		String telefono = Entrada.cadena();
		if (telefono == null || telefono.trim().equals("")) {
			profesor = new Profesor(nombre, correo);
		} else {
			profesor = new Profesor(nombre, correo, telefono);
		}
		return profesor;
	}

	
	
	public static String leerNombreProfesor() {
		String nombre;
		do {
			System.out.println("Introduce el nombre: ");
			nombre = Entrada.cadena();
		} while (nombre.trim().equals(""));

		return nombre;
	}


	public static Tramo leerTramo() {
		Tramo turno = Tramo.MANANA;
		int opcion;
		do 
		{
			System.out.println("Introduzca turno de 1-mañana o 2-tarde(: ");
			opcion = Entrada.entero();
		} while (opcion < 1 || opcion > 2);

		switch (opcion) 
		{

		case 1:

			turno = Tramo.MANANA;


			break;      
		case 2:                        
			turno = Tramo.TARDE;
			break;
		}    



		return  turno;
	}


	public static LocalDate leerDia()
	{
		boolean centinela = false;
		LocalDate fecha = null;
		do {
			System.out.print("Introduce la fecha con formato dd/mm/yyyy: ");
			String cadena = Entrada.cadena();
			try {
				fecha = LocalDate.parse(cadena, FORMATO_DIA);
				centinela = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} while (!centinela);
		return fecha;
	}
	
}