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

import java.util.Random;

import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.PoissonGenerator;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

/**
 * Abstract Factory for Mutation Operator 
 * @author eduardo
 *
 */
public abstract class MutationCreator extends OperatorCreator {
	protected PoissonGenerator rnd;
	protected ConstantGenerator cons;

	public MutationCreator() {
		super();
	}
	
	public abstract EvolutionaryOperator getInstance();
	
	/**
	 * 
	 * @param probability Seed which defines the random generator to define the displace of the elements in the chromosome
	 * @param constant	The number of mutations to be applied to every chromosome
	 */
	public void setGenerators(double probability, int constant){
		rnd = new PoissonGenerator(probability, new Random());
		cons = new ConstantGenerator(constant);
	}
	
	
	
	
}
