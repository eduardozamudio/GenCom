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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.edu.isistan.genCom.util.Statistics;

/**
 * Conjunto de Ejecuciones del algoritmo genético con información 
 * @author eduardo
 *
 */
public class Generacion {
	private ConfiguracionAG configuracion;
	private List<Ejecucion> ejecuciones;
	private Date fecha;
	
	public ConfiguracionAG getConfiguracion() {
		return configuracion;
	}
	public void setConfiguracion(ConfiguracionAG configuracion) {
		this.configuracion = configuracion;
	}
	public List<Ejecucion> getEjecuciones() {
		return ejecuciones;
	}
	public void setEjecuciones(List<Ejecucion> ejecuciones) {
		this.ejecuciones = ejecuciones;
	}
	public void addEjecucion(Ejecucion ejecucion){
		if (ejecuciones == null)
			ejecuciones = new ArrayList<>();
		this.ejecuciones.add(ejecucion);
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * Obtiene el fitness promedio de las ejecuciones
	 * @return
	 */
	public double getFitnessPromedio(){
		double result = 0;

		for (Ejecucion ejecucion : ejecuciones) {
			result += ejecucion.getFitness();
		}
		
		result /= ejecuciones.size();		
		return result;
	}
	
	/**
	 * Obtiene la desviación standard del fitness de las ejecuciones
	 * @param ejecuciones
	 * @return
	 */
	public double getFitnessDesviacion(){
		double fitness[] = new double[ejecuciones.size()]; 
		
		int i = 0;
		for (Ejecucion ejecucion : ejecuciones) {
			fitness[i] = (ejecucion.getFitness());
			i++;
		}
		
		Statistics st = new Statistics(fitness);			
		return st.getStdDev();
	}	
	
}
