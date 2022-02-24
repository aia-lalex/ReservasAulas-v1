package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.util.Objects;

public class Profesor {

	private static final String ER_TELEFONO = "[96]\\d{8}";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	public String nombre;
	public String correo;
	public String telefono;


	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}

	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}

	public Profesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		setNombre(profesor.nombre);
		setCorreo(profesor.correo);
		setTelefono(profesor.telefono);
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono != null && !telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
		}
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		}
		this.nombre = nombre;
	}

	private String formateaNombre(String nombre){ // Formateo del nombre, eliminamos espacios y ponemos la primera en mayuscula
		
		char priLetra;
		String mayusculas; 
		String nombreFormateado="";
		nombre = nombre.trim(); // Eliminamos espacio y tabulaciones
		String [] nombr = nombre.replaceAll("\\s{2,}", " ").split(" ");
		
		for (int i=0; i<nombr.length; i++) {
			priLetra = nombr[i].charAt(0);
			mayusculas = priLetra+"".toUpperCase();
			nombreFormateado += mayusculas+nombr[i].substring(1).toLowerCase() + " ";
		}

		nombreFormateado = nombreFormateado.substring(0,nombreFormateado.length()-1);
		
		return nombreFormateado;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(ER_CORREO, ER_TELEFONO, correo, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(ER_CORREO, other.ER_CORREO) && Objects.equals(ER_TELEFONO, other.ER_TELEFONO)
				&& Objects.equals(correo, other.correo) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		String cadenaTelefono = (telefono == null) ? "" : ", telefono=" + telefono;
		return String.format("nombre=%s, correo=%s%s", nombre, correo, cadenaTelefono);
	}



}


