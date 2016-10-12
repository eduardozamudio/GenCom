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
package main.java.edu.isistan.genCom.evolutive.ag.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;


public class InsertMutation implements EvolutionaryOperator<List<Integer>> {
	private final NumberGenerator<Integer> mutationCountVariable;
	private final NumberGenerator<Integer> mutationAmountVariable;

	/**
	 * Default is one mutation per candidate.
	 */
	public InsertMutation() {
		this(1, 1);
	}

	/**
	 * @param mutationCount
	 *            The constant number of mutations to apply to each individual
	 *            in the population.
	 * @param mutationAmount
	 *            The constant number of positions by which a list element will
	 *            be displaced as a result of mutation.
	 */
	public InsertMutation(int mutationCount, int mutationAmount) {
		this(new ConstantGenerator<Integer>(mutationCount),
				new ConstantGenerator<Integer>(mutationAmount));
	}

	/**
	 * Typically the mutation count will be from a Poisson distribution. The
	 * mutation amount can be from any discrete probability distribution and can
	 * include negative values.
	 * 
	 * @param mutationCount
	 *            A random variable that provides a number of mutations that
	 *            will be applied to each individual.
	 * @param mutationAmount
	 *            A random variable that provides a number of positions by which
	 *            to displace an element when mutating.
	 */
	public InsertMutation(NumberGenerator<Integer> mutationCount,
			NumberGenerator<Integer> mutationAmount) {
		this.mutationCountVariable = mutationCount;
		this.mutationAmountVariable = mutationAmount;
	}

	@Override
	public List<List<Integer>> apply(List<List<Integer>> selectedCandidates,
			Random rng) {
		List<List<Integer>> result = new ArrayList<List<Integer>>(
				selectedCandidates.size());
		for (List<Integer> candidate : selectedCandidates) {
			List<Integer> newCandidate = new ArrayList<Integer>(candidate);
			int mutationCount = Math.abs(mutationCountVariable.nextValue());
			
			for (int i = 0; i < mutationCount; i++) {
				int fromIndex = rng.nextInt(newCandidate.size());
				int mutationAmount = mutationAmountVariable.nextValue();
				int toIndex = (fromIndex + mutationAmount)
						% newCandidate.size();
				if (toIndex < 0) {
					toIndex += newCandidate.size();
				}

				// Hago la mutación
				newCandidate = candidate;
//				TODO Reemplazar la probabilidad de mutación por aquella obtenida de Evolutive
//				if (rng.nextDouble() <= Evolutive.ProbabilidadMutacion.doubleValue()) {
				Probability prob =new Probability(0.14d);  
				if (rng.nextDouble() <= prob.doubleValue()) {
					for (int j = toIndex; j > (fromIndex + 1); j--) {
						int aux = newCandidate.get(j);
						newCandidate.set(j, newCandidate.get(j - 1));
						newCandidate.set(j - 1, aux);
					}
				}
				result.add(newCandidate);
			}
		
		}
		return result;
	}
}