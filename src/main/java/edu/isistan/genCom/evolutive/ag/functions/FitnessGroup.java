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
/**
 * 
 */
package main.java.edu.isistan.genCom.evolutive.ag.functions;

import java.util.Arrays;
import java.util.List;

import main.java.edu.isistan.genCom.redSocial.Investigador;

/**
 * Fitness functions composite
 * @author zamu
 *
 */
/**
 * @author zamu
 *
 */
public class FitnessGroup extends FitnessAbs {
	private List<FitnessAbs> fitnesses;
	private double[] weights;


	/**
	 * Initializes the list of contained fitness functions
	 * 
	 * @param fitnesses
	 */
	public FitnessGroup(List<FitnessAbs> fitnesses) {
		super();
		this.setFitnesses(fitnesses);
	}

	/**
	 * Initializes the list of contained fitnesses with weights
	 * 
	 * @param fitnesses	Fitness function list
	 * @param weights	Weights for fitnessfunctions
	 */
	public FitnessGroup(List<FitnessAbs> fitnesses, double[] weights) {
		assert (Integer.compare(fitnesses.size(), weights.length) == 0);

		// TODO Revisar la suma de doubles no genere problemas
		double sum = 0;

		for (int i = 0; i < weights.length; i++) {
			sum += weights[i];
		}
		
		assert (Double.compare(sum, 1d) == 0);

		this.weights = weights;
		this.fitnesses = fitnesses;
	}

	@Override
	public double getFitness(List<Investigador> comission) {
		double result = 0;

		for (int i = 0; i < fitnesses.size(); i++) {
			FitnessAbs fitnessAbs = fitnesses.get(i);

			result += fitnessAbs.getFitness(comission) * weights[i];
		}

		if (!fitnesses.isEmpty())
			result /= fitnesses.size();

		return result;
	}

	public List<FitnessAbs> getFitnesses() {
		return fitnesses;
	}

	private void setFitnesses(List<FitnessAbs> fitnesses) {
		this.weights = new double[this.fitnesses.size()];
		
		Arrays.fill(weights, 1d);

		this.fitnesses = fitnesses;
	}

	

}
