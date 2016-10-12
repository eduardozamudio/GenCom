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

import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * Factory for Independence instances
 * 
 * @author zamu
 *
 */
public class CreatorIndependence extends FFCreator {
	
	
	public CreatorIndependence() {
		this.nombre = "INDEPENDENCE";
	}
	
	/* (non-Javadoc)
	 * @see main.java.edu.isistan.genCom.evolutive.ag.functions.FFCreator#getInstance(main.java.edu.isistan.genCom.redSocial.RedSocial)
	 */
	@Override
	public FitnessAbs getInstance(RedSocial red) {
		return new FitnessIndependence(red);
	}


}
