/**
 *     This file is part of GenCom.
 *
 *     GenCom is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GenCom is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with GenCom.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.edu.isistan.genCom.redSocial;

// Generated 06-mar-2014 11:43:22 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Investigadores generated by hbm2java
 */
public class Investigador implements java.io.Serializable, Comparable {

	private long investigadorId;
	private String cargo;
	private String nombre;
	private boolean esEspecialista;
	private Set indicesInvestigadoresReds = new HashSet(0);
	private Set lugaresDeTrabajos = new HashSet(0);
	private Set redeses = new HashSet(0);
	private Set redeses_1 = new HashSet(0);
	private Set centralidadReds = new HashSet(0);
	private Set publicaciones = new HashSet(0);
	private Set redeses_2 = new HashSet(0);

	public Investigador() {
	}

	
	public boolean isEsEspecialista() {
		return esEspecialista;
	}


	public void setEsEspecialista(boolean esEspecialista) {
		this.esEspecialista = esEspecialista;
	}


	public Investigador(long investigadorId) {
		this.investigadorId = investigadorId;
	}

	public Investigador(long investigadorId, String cargo, String nombre,
			Set indicesInvestigadoresReds, Set lugaresDeTrabajos, Set redeses,
			Set redeses_1, Set centralidadReds, Set publicaciones,
			Set redeses_2) {
		this.investigadorId = investigadorId;
		this.cargo = cargo;
		this.nombre = nombre;
		this.indicesInvestigadoresReds = indicesInvestigadoresReds;
		this.lugaresDeTrabajos = lugaresDeTrabajos;
		this.redeses = redeses;
		this.redeses_1 = redeses_1;
		this.centralidadReds = centralidadReds;
		this.publicaciones = publicaciones;
		this.redeses_2 = redeses_2;
	}

	public long getInvestigadorId() {
		return this.investigadorId;
	}

	public void setInvestigadorId(long investigadorId) {
		this.investigadorId = investigadorId;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set getIndicesInvestigadoresReds() {
		return this.indicesInvestigadoresReds;
	}

	public void setIndicesInvestigadoresReds(Set indicesInvestigadoresReds) {
		this.indicesInvestigadoresReds = indicesInvestigadoresReds;
	}

	public Set getLugaresDeTrabajos() {
		return this.lugaresDeTrabajos;
	}

	public void setLugaresDeTrabajos(Set lugaresDeTrabajos) {
		this.lugaresDeTrabajos = lugaresDeTrabajos;
	}

	public Set getRedeses() {
		return this.redeses;
	}

	public void setRedeses(Set redeses) {
		this.redeses = redeses;
	}

	public Set getRedeses_1() {
		return this.redeses_1;
	}

	public void setRedeses_1(Set redeses_1) {
		this.redeses_1 = redeses_1;
	}

	public Set getCentralidadReds() {
		return this.centralidadReds;
	}

	public void setCentralidadReds(Set centralidadReds) {
		this.centralidadReds = centralidadReds;
	}

	public Set getPublicaciones() {
		return this.publicaciones;
	}

	public void setPublicaciones(Set publicaciones) {
		this.publicaciones = publicaciones;
	}

	public Set getRedeses_2() {
		return this.redeses_2;
	}

	public void setRedeses_2(Set redeses_2) {
		this.redeses_2 = redeses_2;
	}
	
	@Override
	public String toString() {
		String s = new String();
		s = this.getNombre().concat("_").concat(String.valueOf(this.getInvestigadorId()));
		return s;
	}


	@Override
	public int compareTo(Object o) {
		Investigador inv = (Investigador) o;
		
		return nombre.compareTo(inv.getNombre());
	}

}
