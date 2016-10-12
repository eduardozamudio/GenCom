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
package main.java.edu.isistan.genCom.evolutive.ag.operator;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;

/**
 * Factory for Tournament instances selection strategy
 * @author eduardo
 *
 */
public class CreatorTournament extends SelectionCreator {
	private Probability probability;
	
	public CreatorTournament() {
		this.nombre = "TOURNAMENT";
	}
	
	public SelectionStrategy getInstance(double probability) {
		this.probability = new Probability(probability);
		
		return new TournamentSelection(getProbability());
	}

	@Override
	public SelectionStrategy getInstance() {
		double p = 1;
		
		return getInstance(p);
	}
	
	public Probability getProbability() {
		return probability;
	}
	
	@Override
	public String toString() {
		String result = this.nombre + " (" + String.valueOf(this.probability) + ")";

		return result;
	}

}
