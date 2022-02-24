package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;
import com.google.common.base.Objects;

public class Aula {
	private String nombre;

	public Aula(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}
		setNombre(nombre);
	}

	public Aula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}
		setNombre(aula.getNombre());
	}


	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("ERROR: El nombre del aula no puede ser nulo.");
		}
		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROREl nombre del aula no puede estar vacío.");
		}
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Aula other = (Aula) obj;
		if (!Objects.equal(this.nombre, other.nombre)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "nombre Aula="+nombre;
	}


}