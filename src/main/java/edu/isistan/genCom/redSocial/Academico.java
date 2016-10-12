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
 * Controlador para obtener información sobre el aspecto académico de la red social
 * 
 */
package main.java.edu.isistan.genCom.redSocial;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.edu.isistan.genCom.redSocial.dao.DAOInvestigador;

/**
 * @author eduardo
 * 
 */
public class Academico {

	/**
	 * Obtiene el listado de todos los investigadores de la red
	 * 
	 * @return Listado de investigadores
	 */
	public List<Investigador> getInvestigadores() {
		List<Investigador> result = null;
		DAOInvestigador investigadores;
		investigadores = new DAOInvestigador();

		result = investigadores.getAll();
		return result;
	}

	/**
	 * Obtiene el listado de todos los investigadores relacionados a un
	 * investigador
	 * 
	 * @param investigador
	 * @return
	 */
	public Set<Investigador> getRelacionados(Investigador investigador) {
		Set<Investigador> result = new HashSet<>();

		// Obtener los investigadores con igual lugar de trabajo y mismas publicaciones
		DAOInvestigador investigadores;
		investigadores = new DAOInvestigador();

		result = investigadores.getRelacionados(investigador);

		return result;
	}

}
