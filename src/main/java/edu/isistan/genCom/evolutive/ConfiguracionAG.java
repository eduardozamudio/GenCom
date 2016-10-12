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

import org.uncommons.watchmaker.framework.SelectionStrategy;

import main.java.edu.isistan.genCom.evolutive.ag.functions.FFCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CrossoverCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.ModeloCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.MutationCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.SelectionCreator;

public class ConfiguracionAG {

	private CrossoverCreator cruce;
	private MutationCreator mutacion;
	private SelectionCreator seleccion;
	private ModeloCreator modelo;
	private int corte;
	
	private FFCreator fitness;

	
	public CrossoverCreator getCruce() {
		return cruce;
	}

	public void setCruce(CrossoverCreator cruce) {
		this.cruce = cruce;
	}
	
	/**
	 * Obtiene un operador de mutación de acuerdo al tipo y probabilidad indicados
	 * en la configuración
	 * 
	 * @return
	 */
	public MutationCreator getMutacion() {
		return mutacion;
	}

	public void setMutacion(MutationCreator mutacion) {
		this.mutacion = mutacion;
	}

	/**
	 * Obtiene un operador de selección de acuerdo al tipo indicado
	 * en la configuración
	 * 
	 * @return
	 */
	public SelectionCreator getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(SelectionCreator seleccion) {
		this.seleccion = seleccion;
	}

	public ModeloCreator getModelo() {
		return modelo;
	}

	public void setModelo(ModeloCreator modelo) {
		this.modelo = modelo;
	}

	public int getCorte() {
		return corte;
	}

	public void setCorte(int corte) {
		this.corte = corte;
	}

	public FFCreator getFitness() {
		return fitness;
	}

	public void setFitness(FFCreator fitness) {
		this.fitness = fitness;
	}
	
	public ConfiguracionAG() {
		super();
	}

	/**
	 * Initialize the configuration
	 * 
	 * @param cruce	Crossover operator
	 * @param mutacion	Mutation operator 
	 * @param seleccion	Selection operator
	 * @param modelo	Survivor strategy
	 * @param corte	Generations threshold
	 * @param probCruce	Crossover probability
	 */
	public ConfiguracionAG(CrossoverCreator cruce, 
			MutationCreator mutacion, SelectionCreator seleccion,
			ModeloCreator modelo, int corte) {
		super();
		this.cruce = cruce;
		this.mutacion = mutacion;
		this.seleccion = seleccion;
		this.modelo = modelo;
		this.corte = corte;
	}
	
	

	/**
	 * Initialize the configuration
	 * 
	 * @param cruce	Crossover operator
	 * @param mutacion	Mutation operator 
	 * @param seleccion	Selection operator
	 * @param modelo	Survivor strategy
	 * @param corte	Generations threshold
	 * @param fitness	Fitness function evaluator
	 * @param probCruce	Crossover probability
	 */
	public ConfiguracionAG(CrossoverCreator cruce, 
			MutationCreator mutacion, SelectionCreator seleccion,
			ModeloCreator modelo, int corte, FFCreator fitness) {
		super();
		this.cruce = cruce;
		this.mutacion = mutacion;
		this.seleccion = seleccion;
		this.modelo = modelo;
		this.corte = corte;
		this.fitness = fitness;
	}

	
}
