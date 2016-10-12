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

import java.util.ArrayList;
import java.util.List;

import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * @author zamu
 *
 */
public class CreatorFFGroup extends FFCreator {
	/**
	 * Pesos de comisiones actuales
	 */
	double[] WEIGHTS = { 0.5675761994d, 0.4317373858d, 0.0006864148d }; 

	public CreatorFFGroup() {
		this.nombre = "WEIGHTED_SUM (KPP+IND)";
	}

	/* (non-Javadoc)
	 * @see main.java.edu.isistan.genCom.evolutive.ag.functions.FFCreator#getInstance(main.java.edu.isistan.genCom.redSocial.RedSocial)
	 */
	@Override
	public FitnessAbs getInstance(RedSocial red) {
		List<FitnessAbs> fitnesses = new ArrayList<>();
		
		fitnesses.add(new FitnessIndependence(red));
		fitnesses.add(new FitnessKPPpos(red));
		fitnesses.add(new FitnessKPPneg(red));
		
		
		return new FitnessGroup(fitnesses, this.WEIGHTS);
	}

}
