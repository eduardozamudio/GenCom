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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class SwapMutation implements EvolutionaryOperator<List<Integer>>{

	@Override
	public List<List<Integer>> apply(List<List<Integer>> selectedCandidates, Random arg1) {
		List<List<Integer>> result = new ArrayList<List<Integer>>(selectedCandidates.size());
		
//		hago la mutación de cada cromosoma
		for (List<Integer> candidate : selectedCandidates) {
			// La mutación se realiza en los genes del cromosoma
			List<Integer> newCandidate = new ArrayList<Integer>(candidate.size());
			int fromIndex = new Random().nextInt(candidate.size());
			int mutationAmount = new Random().nextInt();
			int toIndex = (fromIndex + mutationAmount)
					% candidate.size();
			if (toIndex < 0) {
				toIndex += candidate.size();
			}
			
			// Swap the randomly selected element with the one that is the
			// specified displacement distance away.
			List<Integer> cand = new ArrayList<Integer>(newCandidate.size());
			Collections.swap(cand, fromIndex, toIndex);
			for (int j = 0; j < cand.size(); j++) {
				newCandidate.add(j, cand.get(j));
			}
			result.add(newCandidate);			
		}
		return result;
	}
}
