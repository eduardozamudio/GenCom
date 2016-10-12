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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessAbs;
import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessIndependence;
import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * Implements the Fitness Evaluator for the Genetic Algorithm
 * 
 * @author eduardo
 *
 */
public class RedFitness implements FitnessEvaluator<List<Integer>> {
	private RedSocial red;
	private FitnessAbs fitnessFunction;
	private List<Investigador> investigadores;

	private int cromosomaSize;
	private boolean[] comision; //Posiciones de genes candidatos a comisión

	
	public RedFitness(RedSocial red, int cromosomaSize, List<Investigador> investigadores, FitnessAbs fitnessFunction) {
		super();
		this.red = red;
		this.cromosomaSize = cromosomaSize;
		this.investigadores = investigadores;
		this.fitnessFunction = fitnessFunction;
	}

	/**
	 * Initializes the social network. FitnessIndependence is set as default.
	 * 
	 * @param red
	 */
	public RedFitness(RedSocial red, int cromosomaSize, List<Investigador> investigadores) {
		this(red, cromosomaSize, investigadores, new FitnessIndependence(red));
	}
	
	public double getFitness(List<Integer> genes, java.util.List<? extends List<Integer>> arg1) {
		List<Integer> comisionValues = getComissionIndices(genes);

		List<Investigador> commisionNodes = getCommisionNodes(comisionValues);

		return getFitnessAcumulated(commisionNodes);
	}

	/**
	 * Obtiene los índices de la comisión
	 * 
	 * @param genes
	 * @return
	 */
	private List<Integer> getComissionIndices(List<Integer> genes) {
		List<Integer> result = new ArrayList<Integer>(genes.size());

		// Obtener los genes que integran la comisión
		for (int i = 0; i < genes.size(); i++) {
			
			if (comision[i]) {
				Integer value = genes.get(i);
				result.add(value);
			}
		}
		return result;
	}

	/**
	 * Obtiene los nodos de la comisión
	 * 
	 * @param commisionValues
	 *            Indices de la colección nodos
	 * @return colección de nodos
	 */
	public List<Investigador> getCommisionNodes(List<Integer> commisionValues) {
		List<Investigador> result = new ArrayList();

		for (Integer idx : commisionValues) {
			result.add(investigadores.get(idx));
		}

		return result;

	}

	/**
	 * Obtiene el fitness acumulado para los genes integrantes de la comisión
	 * 
	 * @param comisionNodes
	 *            genes integrantes de la comisión
	 * 
	 * @return
	 */
	protected double getFitnessAcumulated(List<Investigador> comisionNodes) {
		return fitnessFunction.getFitness(comisionNodes);
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	public void setRed(RedSocial red) {
		this.red = red;
	}

	public RedSocial getRed() {
		return red;
	}


	public void setComision(boolean[] comision) throws Exception {
		if (comision.length != cromosomaSize)
			throw new Exception(
					"La colección de candidatos a comisión no contiene la dimensión especificada: " + cromosomaSize);

		this.comision = comision;
	}

	public boolean[] getComision() {
		return comision;
	}

	/**
	 * Genera las posiciones de la comisión aleatoriamente
	 * 
	 * @param comisionSize
	 *            Cantidad de candidatos en la comisión
	 * @throws Exception
	 */
	public void setComisionAleatoria(int comisionSize) throws Exception {
		comision = new boolean[cromosomaSize];

		Arrays.fill(comision, false);

		int cant = 0;
		Random rnd = new Random();

		while (cant < comisionSize) {
			int posicion = rnd.nextInt(cromosomaSize);

			if (!comision[posicion]) {
				comision[posicion] = true;
				cant++;
			}
		}
	}

	public FitnessAbs getFitnessFunction() {
		return fitnessFunction;
	}

	public void setFitnessFunction(FitnessAbs fitnessFunction) {
		this.fitnessFunction = fitnessFunction;
	}
}
