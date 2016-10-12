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

import java.util.HashMap;
import java.util.Map;

public class OperadorSeleccion {
	Map<String, SelectionCreator> creators;
	
	public OperadorSeleccion() {
		this.creators = new HashMap<>();
	}
	
	public SelectionCreator get(String nombre){
		return this.creators.get(nombre);
	}
	
	public void set(String nombre, SelectionCreator creator){
		this.creators.put(nombre, creator);
	}
	
	public String[] getKeys(){
		Object[] keys = creators.keySet().toArray();
		String[] result = new String[keys.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = keys[i].toString();
		}
		
		return result;
	}

}
