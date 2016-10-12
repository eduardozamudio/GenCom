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
package main.java.edu.isistan.genCom.util;

/**
 * Calculates the entropy weights for a decision matrix
 * 
 * Based on eq. (1)
 * Xiaozhan Xu, A note on the subjective and objective integrated
 * approach to determine attribute weights, European Journal of Operational
 * Research, Volume 156, Issue 2, 16 July 2004, Pages 530-532, ISSN 0377-2217,
 * http://dx.doi.org/10.1016/S0377-2217(03)00146-2.
 * (http://www.sciencedirect.com/science/article/pii/S0377221703001462)
 * Keywords: Multiple attribute decision making; Attribute weight; Objective
 * weight; Entropy weight
 * 
 * @author eduardo
 *
 */
public class Entropy {

	/**
	 * Returns the weights for every attribute in decision matrix p
	 * 
	 * @param p
	 *            decision matrix <i>nxm</i>
	 * @return
	 */
	public static double[] getValues(double[][] p) {
		// Normalize p
		int n = p.length;
		int m = p[0].length;

		double[] result = new double[m];

		for (int j = 0; j < m; j++) {
			double sumM = 0;

			for (int i = 0; i < n; i++) {
				sumM += p[i][j];
			}

			if (sumM > 0) {
				for (int i = 0; i < n; i++) {
					p[i][j] /= sumM;
				}
			}

		}

		// Gets Entropy
		for (int j = 0; j < m; j++) {
			double sumE = 0;

			for (int i = 0; i < n; i++) {
				double pij = p[i][j];

				sumE += pij * Math.log(pij);
			}

			result[j] = (-sumE) / Math.log(n);
		}

		// Normalize entropy
		double sumResult = 0;

		for (int i = 0; i < result.length; i++) {
			sumResult += (1 - result[i]);
		}

		if (sumResult > 0) {
			for (int i = 0; i < result.length; i++) {
				result[i] = (1 - result[i]) / sumResult;
			}
		}

		return result;
	}
}
