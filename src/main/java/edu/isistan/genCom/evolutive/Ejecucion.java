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
package main.java.edu.isistan.genCom.evolutive;

import java.util.List;

import main.java.edu.isistan.genCom.redSocial.Investigador;

/**
 * Registra la configuración de una ejecución del AG 
 * @author eduardo
 *
 */
public class Ejecucion {
	private ConfiguracionAG configuracion;
	private long tiempo;
	private double fitness;
	private List<Investigador> comision;
	
	
	/**
	 * @param configuracion
	 */
	public Ejecucion(ConfiguracionAG configuracion) {
		super();
		this.configuracion = configuracion;
	}
	public ConfiguracionAG getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(ConfiguracionAG configuracion) {
		this.configuracion = configuracion;
	}
	public long getTiempo() {
		return tiempo;
	}
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double distancia) {
		this.fitness = distancia;
	}
	public List<Investigador> getComision() {
		return comision;
	}
	public void setComision(List<Investigador> comision) {
		this.comision = comision;
	}
	
		
	
	
}
