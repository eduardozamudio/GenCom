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
 * Creator for Fitness Functions
 * @author zamu
 *
 */
public abstract class FFCreator {
	protected String nombre;
	
	/**
	 * Gets a new instance for the implemented class
	 * @param red
	 * @return
	 */
	public abstract FitnessAbs getInstance(RedSocial red);
	
	/**
	 * Gets the name of the function
	 * @return
	 */
	public String getNombre(){
		return this.nombre;
	}
	
	@Override
	public String toString() {
		return getNombre();
	}
}
