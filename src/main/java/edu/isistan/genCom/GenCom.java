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
package main.java.edu.isistan.genCom;

import main.java.edu.isistan.genCom.evolutive.Evolutive;
import main.java.edu.isistan.genCom.redSocial.RedSocial;
import main.java.edu.isistan.genCom.gui.Principal;

public class GenCom {

	public static void main(String[] args) {

		Principal principal = new Principal();
				
		RedSocial red = new RedSocial();
		Evolutive evolutive = new Evolutive();
		
//		Configurar la pantalla principal
		evolutive.setRedSocial(red);
		principal.setRedSocial(red);
		principal.setEvolutive(evolutive);
		
		principal.setVisible(true);
	
	}

}
