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

import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;

import main.java.edu.isistan.genCom.evolutive.RedFitness;

/**
 * Factory for Steady State evolution model engine instances
 * 
 * @author eduardo
 *
 */
public class CreatorSteadyState extends ModeloCreator {

	public CreatorSteadyState() {
		this.nombre = "STEADY_STATE";
	}
	
	/* (non-Javadoc)
	 * @see main.java.edu.isistan.genCom.evolutive.ag.operator.ModeloCreator#getInstance(main.java.edu.isistan.genCom.evolutive.RedFitness, org.uncommons.watchmaker.framework.CandidateFactory, org.uncommons.watchmaker.framework.EvolutionaryOperator, org.uncommons.watchmaker.framework.SelectionStrategy)
	 */
	@Override
	public EvolutionEngine getInstance(RedFitness evaluador, CandidateFactory<List> factory,
			EvolutionaryOperator pipeline, SelectionStrategy selection) {

		return new GenerationalEvolutionEngine(factory, pipeline, evaluador, selection, new Random());
	}

}
