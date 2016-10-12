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

import org.uncommons.maths.statistics.DataSet;

import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * Implements the metric <i> weighted proportion </i> for the Key Player
 * Problem Set (KPP Pos).
 * 
 * The aim of KPP Pos is to find the k nodes maximally connected to the network. KPP
 * Pos is a diffusion strategy. Based on the KPP problem from Borgatti and
 * Everett.
 * 
 * Eq. 2.4 in:  
 * Ortiz-Arroyo, D. (2010). Discovering sets of key players in social
 * networks. In Computational social network analysis (pp. 27-47). Springer
 * London.
 * 
 * @author eduardo
 *
 */
public class FitnessKPPpos extends FitnessSimple {
	private RedSocial red;

	public FitnessKPPpos(RedSocial red) {
		super();
		this.red = red;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * main.java.edu.isistan.genCom.evolutive.FitnessAbs#getFitness(java.util.
	 * List)
	 */
	@Override
	public double getFitness(List<Investigador> comission) {
		double result = 0;
		double sumDistances = 0;

		int nodesCount = red.getCantidadDeNodos();

		List<Double> distancias = red.getDistanciasProportion(comission);
		double[] vectorDistancias = new double[distancias.size()];
		
		for (int i = 0; i < distancias.size(); i++) {
			vectorDistancias[i] = 1 / distancias.get(i);
		}
		
		DataSet distanciasStat = new DataSet(vectorDistancias);

		sumDistances = distanciasStat.getAggregate();

		result = sumDistances / (nodesCount);

		return result;
	}

}
