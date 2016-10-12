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

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.uncommons.maths.statistics.DataSet;

import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * Implements the independence metric group for committees based on social network geodesic distances.
 * @author zamu
 *
 */
public class FitnessIndependence extends FitnessSimple{
	private RedSocial red;
	
	public FitnessIndependence(RedSocial red) {
		super();
		this.red = red;
	}

	@Override
	public double getFitness(List<Investigador> comission) {
		double result = 0;
		double diametro = red.getDiameter();
		double minDistancia = Double.valueOf(diametro);
		double sumDistancias = 0;
		
		List<Double> distancias = red.getDistancesIn(comission);

		DataSet distanciasStat = new DataSet(ArrayUtils.toPrimitive(distancias.toArray(new Double[0])));

		minDistancia = distanciasStat.getMinimum();
		sumDistancias = distanciasStat.getAggregate();

		if (!distancias.isEmpty())
			result = sumDistancias /distancias.size();

		// Normaliza el resultado
		result = (result + minDistancia) / (2 * diametro);
		
		return result;
	}

}
