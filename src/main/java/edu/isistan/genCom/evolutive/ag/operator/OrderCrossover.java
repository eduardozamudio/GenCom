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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

public class OrderCrossover<T> extends AbstractCrossover<List<T>>{

	/**
	 * Perform ordered crossover (also known as OX) on the specified tours.
	 * 
	 * Ordered crossover works in two stages. First, a random slice is swapped
	 * between the two tours, as in a two-point crossover. Second, repeated
	 * cities not in the swapped area are removed, and the remaining integers
	 * are added from the other tour, in the order that they appear starting
	 * from the end index of the swapped section.
	 * 
	 * @param tour1
	 *            A tour.
	 * @param tour2
	 *            Another tour.
	 * @see jmona.CrossoverFunction#crossover(Object, Object)
	 */
	public void crossover(final List<Integer> tour1, final List<Integer> tour2) {

//		// get the size of the tours
//		final int size = tour1.size();
//
//		// choose two random numbers for the start and end indices of the slice
//		// (one can be at index "size")
//		final int number1 = RandomUtils.randomData().nextInt(0, size - 1);
//		final int number2 = RandomUtils.randomData().nextInt(0, size);
//
//		// make the smaller the start and the larger the end
//		final int start = Math.min(number1, number2);
//		final int end = Math.max(number1, number2);
//
//		// instantiate two child tours
//		final List<Integer> child1 = new Vector<Integer>();
//		final List<Integer> child2 = new Vector<Integer>();
//
//		// add the sublist in between the start and end points to the children
//		child1.addAll(tour1.subList(start, end));
//		child2.addAll(tour2.subList(start, end));
//
//		// iterate over each city in the parent tours
//		int currentCityIndex = 0;
//		int currentCityInTour1 = 0;
//		int currentCityInTour2 = 0;
//		for (final int i : new Range(size)) {
//
//			// get the index of the current city
//			currentCityIndex = (end + i) % size;
//
//			// get the city at the current index in each of the two parent tours
//			currentCityInTour1 = tour1.get(currentCityIndex);
//			currentCityInTour2 = tour2.get(currentCityIndex);
//
//			// if child 1 does not already contain the current city in tour 2,
//			// add it
//			if (!child1.contains(currentCityInTour2)) {
//				child1.add(currentCityInTour2);
//			}
//
//			// if child 2 does not already contain the current city in tour 1,
//			// add it
//			if (!child2.contains(currentCityInTour1)) {
//				child2.add(currentCityInTour1);
//			}
//		}
//
//		// rotate the lists so the original slice is in the same place as in the
//		// parent tours
//		Collections.rotate(child1, start);
//		Collections.rotate(child2, start);
//
//		// copy the tours from the children back into the parents, because
//		// crossover
//		// functions are in-place!
//		Collections.copy(tour1, child2);
//		Collections.copy(tour2, child1);
	}

	/**
	 * Creates a cross-over operator with a cross-over probability of 1.
	 */
	public OrderCrossover() {
		this(Probability.ONE);
	}

	/**
	 * Creates a cross-over operator with the specified cross-over probability.
	 * 
	 * @param crossoverProbability
	 *            The probability that cross-over will be performed for any
	 *            given pair.
	 */
	public OrderCrossover(Probability crossoverProbability) {
		super(2, // Requires exactly two cross-over points.
				crossoverProbability);
	}

	/**
	 * Creates a cross-over operator where cross-over may or may not be applied
	 * to a given pair of parents depending on the {@code crossoverProbability}.
	 * 
	 * @param crossoverProbabilityVariable
	 *            The probability that, once selected, a pair of parents will be
	 *            subjected to cross-over rather than being copied, unchanged,
	 *            into the output population.
	 */
	public OrderCrossover(
			NumberGenerator<Probability> crossoverProbabilityVariable) {
		super(new ConstantGenerator<Integer>(2), // Requires exactly two
													// cross-over points.
				crossoverProbabilityVariable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<List<T>> mate(List<T> parent1, List<T> parent2,
			int numberOfCrossoverPoints, Random rng) {
		assert numberOfCrossoverPoints == 2 : "Expected number of cross-over points to be 2.";

//		if (parent1.size() != parent2.size()) {
//			throw new IllegalArgumentException(
//					"Cannot perform cross-over with different length parents.");
//		}
//
//		List<T> offspring1 = new ArrayList<T>(parent1); // Use a random-access
//														// list for performance.
//		List<T> offspring2 = new ArrayList<T>(parent2);
//
//		int point1 = rng.nextInt(parent1.size());
//		int point2 = rng.nextInt(parent1.size());
//
//		int length = point2 - point1;
//		if (length < 0) {
//			length += parent1.size();
//		}
//
//		Map<T, T> mapping1 = new HashMap<T, T>(length * 2); // Big enough map to
//															// avoid re-hashing.
//		Map<T, T> mapping2 = new HashMap<T, T>(length * 2);
//		for (int i = 0; i < length; i++) {
//			int index = (i + point1) % parent1.size();
//			T item1 = offspring1.get(index);
//			T item2 = offspring2.get(index);
//			offspring1.set(index, item2);
//			offspring2.set(index, item1);
//			mapping1.put(item1, item2);
//			mapping2.put(item2, item1);
//		}
//
//		checkUnmappedElements(offspring1, mapping2, point1, point2);
//		checkUnmappedElements(offspring2, mapping1, point1, point2);
//
//		List<List<T>> result = new ArrayList<List<T>>(2);
//		result.add(offspring1);
//		result.add(offspring2);
//		return result;
		
		// get the size of the tours
		final int size = parent1.size();

		// choose two random numbers for the start and end indices of the slice
		// (one can be at index "size")
		final int number1 = rng.nextInt(size);
		final int number2 = rng.nextInt(size);

		// make the smaller the start and the larger the end
		final int start = Math.min(number1, number2);
		final int end = Math.max(number1, number2);

		// instantiate two child tours
		final List<Integer> child1 = new Vector<Integer>();
		final List<Integer> child2 = new Vector<Integer>();

		// add the sublist in between the start and end points to the children
		child1.addAll((Collection<? extends Integer>) parent1.subList(start, end));
		child2.addAll((Collection<? extends Integer>) parent2.subList(start, end));

		// iterate over each city in the parent tours
		int currentCityIndex = 0;
		int currentCityInTour1 = 0;
		int currentCityInTour2 = 0;
//		Vector<Integer> vec = new Vector<Integer>(size);
		for (int i = 0 ; i < size; i++) {

			// get the index of the current city
			currentCityIndex = (end + i) % size;

			// get the city at the current index in each of the two parent tours
			currentCityInTour1 = (Integer) parent1.get(currentCityIndex);
			currentCityInTour2 = (Integer) parent2.get(currentCityIndex);

			// if child 1 does not already contain the current city in tour 2,
			// add it
			if (!child1.contains(currentCityInTour2)) {
				child1.add(currentCityInTour2);
			}

			// if child 2 does not already contain the current city in tour 1,
			// add it
			if (!child2.contains(currentCityInTour1)) {
				child2.add(currentCityInTour1);
			}
		}

		// rotate the lists so the original slice is in the same place as in the
		// parent tours
		Collections.rotate(child1, start);
		Collections.rotate(child2, start);

		// copy the tours from the children back into the parents, because
		// crossover
		// functions are in-place!
//		Collections.copy(parent1, child2);
//		Collections.copy(parent2, child1);
		List<List<T>> result = new ArrayList<List<T>>();
		result.add((List<T>) child2);
		result.add((List<T>) child1);
		return result;		
	}

	/**
	 * Checks elements that are outside of the partially mapped section to see
	 * if there are any duplicate items in the list. If there are, they are
	 * mapped appropriately.
	 */
	private void checkUnmappedElements(List<T> offspring, Map<T, T> mapping,
			int mappingStart, int mappingEnd) {
		for (int i = 0; i < offspring.size(); i++) {
			if (!isInsideMappedRegion(i, mappingStart, mappingEnd)) {
				T mapped = offspring.get(i);
				while (mapping.containsKey(mapped)) {
					mapped = mapping.get(mapped);
				}
				offspring.set(i, mapped);
			}
		}
	}

	/**
	 * Checks whether a given list position is within the partially mapped
	 * region used for cross-over.
	 * 
	 * @param position
	 *            The list position to check.
	 * @param startPoint
	 *            The starting index (inclusive) of the mapped region.
	 * @param endPoint
	 *            The end index (exclusive) of the mapped region.
	 * @return True if the specified position is in the mapped region, false
	 *         otherwise.
	 */
	private boolean isInsideMappedRegion(int position, int startPoint,
			int endPoint) {
		boolean enclosed = (position < endPoint && position >= startPoint);
		boolean wrapAround = (startPoint > endPoint && (position >= startPoint || position < endPoint));
		return enclosed || wrapAround;
	}


}
