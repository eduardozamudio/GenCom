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
 * Genera la red social en base. Esta clase es la encargada de mantenerla actualizada, 
 * y a la vez es responsable de procesar su manipulación
 * 
 */
package main.java.edu.isistan.genCom.redSocial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * @author eduardo
 * 
 */
public class RedSocial {
	private static final Log log = LogFactory.getLog(RedSocial.class);

	private Graph<Investigador, String> red;

	/**
	 * Actualiza la red social a partir de los datos almacenados en la base de
	 * datos.
	 */
	public void actualizar() {
		// inicializa la red

		red = new UndirectedSparseGraph<Investigador, String>();
		Academico academico = new Academico();

		// Obtiene el listado de nodos y los agrega al grafo

		List<Investigador> investigadores = academico.getInvestigadores();

		for (Investigador investigador : investigadores) {

			if (!red.containsVertex(investigador))
				red.addVertex(investigador);
		}

		// Por cada nodo, obtiene sus relaciones y las agrega a la red,
		// verificando que ésta no exista ya.

		for (Investigador investigador : investigadores) {

			Set<Investigador> relacionados;
			relacionados = academico.getRelacionados(investigador);
			agregarRelacionados(investigador, relacionados);
		}

		// TODO Limitar los nodos a los especialistas para integrar comisiones
		// de informáticos

	}

	/**
	 * Agrega los investigadores relacionados a un investigador particular
	 * 
	 * @param investigador
	 * @param relacionados
	 */
	private void agregarRelacionados(Investigador investigador, Set<Investigador> relacionados) {

		for (Investigador investigador2 : relacionados) {
			Investigador inv2 = obtenerNodo(investigador2);

			// Agrega la relación a la red
			if (!red.isNeighbor(investigador, inv2)) {
				try {
					red.addEdge(new String().concat(investigador.toString().concat(inv2.toString())), investigador,
							inv2);
				} catch (Exception e) {
					log.error("Ocurrió un error al registrar el edge", e);
					e.printStackTrace();
				}
			}

		}

	}

	private boolean contieneVertice(Investigador investigador) {
		boolean result = false;

		Collection<Investigador> vertices = red.getVertices();
		for (Investigador investigador2 : vertices) {
			if (investigador.getInvestigadorId() == investigador2.getInvestigadorId())
				return true;
		}

		return result;
	}

	/**
	 * Gets the network diameter
	 * 
	 * @return
	 */
	public double getDiameter() {
		return DistanceStatistics.diameter(getRed());
	}

	/**
	 * Obtiene el betweeness de un nodo
	 * 
	 * @param inv
	 * @return
	 */
	public double getBetweeness(Investigador inv) {
		double result = 0d;
		BetweennessCentrality ranker = new BetweennessCentrality(red);
		ranker.setRemoveRankScoresOnFinalize(false);
		ranker.evaluate();
		result = ranker.getVertexRankScore(inv);

		return result;
	}

	/**
	 * Obtiene la cantidad de enlaces de la red
	 * 
	 * @return
	 */
	public int getCantidadDeEnlaces() {
		int result;
		result = red.getEdgeCount();

		return result;
	}

	/**
	 * Obtiene la cantidad de nodos de la red
	 * 
	 * @return
	 */
	public int getCantidadDeNodos() {
		int result;
		result = red.getVertexCount();

		return result;
	}

	/**
	 * Obtiene el closeness de un nodo
	 * 
	 * @param inv
	 * @return
	 */
	public double getCloseness(Investigador inv) {
		double result = 0d;
		ClosenessCentrality ranker = new ClosenessCentrality<>(red);
		result = ranker.getVertexScore(inv);

		return result;
	}

	public Set getComponentes() {
		WeakComponentClusterer wcc = new WeakComponentClusterer<>();
		Set componentes = wcc.transform(red);

		return componentes;
	}

	/**
	 * Obtiene el degree de un nodo
	 * 
	 * @param inv
	 * @return
	 */
	public int getDegree(Investigador inv) {
		return red.degree(inv);
	}

	/**
	 * Obtiene la distancia del shortest path entre dos nodos
	 * 
	 * @param investigador
	 * @param investigador2
	 * @return 0 si no hay relación
	 */
	public int getDistancias(Investigador investigador, Investigador investigador2) {
		Number distancia;
		UnweightedShortestPath<Investigador, String> uWSP = new UnweightedShortestPath(red);

		distancia = uWSP.getDistance(investigador, investigador2);

		return distancia.intValue();
	}

	/**
	 * Obtiene las distancias no dirigidas para cada combinación de candidatos
	 * 
	 * @param comisionNodes
	 *            coleccion de nodos
	 * @return
	 */
	public List<Double> getDistancesIn(List<Investigador> comisionNodes) {
		List<Double> distancias = new ArrayList<>();
		UnweightedShortestPath<Investigador, String> uWSP = new UnweightedShortestPath(red);

		for (int i = 0; i < comisionNodes.size(); i++) {

			for (int j = (i + 1); j < comisionNodes.size(); j++) {

				Number d = uWSP.getDistance(comisionNodes.get(i), comisionNodes.get(j));

				distancias.add(d.doubleValue());
			}
		}

		return distancias;
	};

	/**
	 * Returns the degree of reachability by getting the distances of every pair of vertices in the network when a vertices set is removed
	 * network
	 * 
	 * Degree of reachability by Borgatti
	 * 
	 * @param comission
	 *            Nodes to be removed
	 * @return List of distances
	 */
	public List<Double> getDegreeOfReachability(List<Investigador> comission) {
		List<Double> distancias = new ArrayList<>();
		
		try {
			RedSocial reduced = null;
			
			reduced = (RedSocial) this.clone();
			
			Set<Investigador> invSet = new HashSet<>(getNodos());
			invSet.removeAll(comission);
			
			reduced.reducirA(invSet);
			
			UnweightedShortestPath<Investigador, String> uWSP = new UnweightedShortestPath(reduced.getRed());

			List<Investigador> nodos = reduced.getNodos();
			
			for (int i = 0; i < nodos.size(); i++) {
				Investigador inv1 = nodos.get(i);

				for (int j = 0; j < i; j++) {
					Investigador inv2 = nodos.get(j);

					Number dist = uWSP.getDistance(inv1, inv2);
					
					Double d = dist != null? dist.doubleValue(): Double.POSITIVE_INFINITY;
					
					distancias.add(d);
				}
			}
			
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return distancias;
	}

	/**
	 * Obtiene las distancias no dirigidas de cada candidato en comisionNodes al
	 * resto de la red
	 * 
	 * @param comisionNodes
	 *            coleccion de nodos
	 * @return
	 */
	public List<Double> getDistanciasProportion(List<Investigador> comisionNodes) {
		List<Double> distancias = new ArrayList<>();
		UnweightedShortestPath<Investigador, String> uWSP = new UnweightedShortestPath(red);
		Double diameter = getDiameter();
		List<Investigador> nodos = getNodos();

		for (Investigador nodo : nodos) {
			Double minDistanceK = diameter;

			if (!comisionNodes.contains(nodo)) {

				for (Investigador miembro : comisionNodes) {

					Double d = uWSP.getDistance(miembro, nodo).doubleValue();

					minDistanceK = d.compareTo(minDistanceK) < 0 ? d : minDistanceK;
				}

				distancias.add(minDistanceK);
			}
		}

		return distancias;

	}

	public double getEigenVector(Investigador inv) {
		double result = 0d;
		EigenvectorCentrality ranker = new EigenvectorCentrality(red);
		result = (double) ranker.getVertexScore(inv);
		return result;
	}

	/**
	 * Obtiene el conjunto de nodos de la red
	 * 
	 * @return
	 */
	public List<Investigador> getNodos() {
		List<Investigador> result = new ArrayList<>();

		result.addAll(red.getVertices());

		return result;
	}

	public Graph<Investigador, String> getRed() {
		return red;
	}

	/**
	 * Obtienen la instancia del investigador con igual ID que se encuentra en
	 * la red como un nodo
	 * 
	 * @param investigador2
	 * @return
	 */
	private Investigador obtenerNodo(Investigador investigador2) {
		Collection<Investigador> nodos = red.getVertices();

		for (Investigador investigador : nodos) {
			if (investigador.getInvestigadorId() == investigador2.getInvestigadorId())
				return investigador;
		}

		return null;
	}

	/**
	 * Reduce la red a los nodos indicados
	 * 
	 * @param nodos
	 */
	public void reducirA(Set nodos) {
		Collection<Investigador> vertices = red.getVertices();
		Set toBeRemoved = new HashSet<>();

		for (Investigador investigador : vertices) {
			if (!nodos.contains(investigador))
				toBeRemoved.add(investigador);
		}

		for (Object object : toBeRemoved) {
			red.removeVertex((Investigador) object);
		}
	}

	public void setRed(Graph<Investigador, String> red) {
		this.red = red;
	}

	/**
	 * Gets a collection of instances of Investigadores by Id
	 * 
	 * @param nodeIds
	 * @return Node instances
	 */
	public List<Investigador> getNodos(Long[] nodeIds) {

		List<Investigador> nodes = new ArrayList<>();

		List<Long> nodeList = Arrays.asList(nodeIds);

		for (Investigador investigador : getNodos()) {
			for (Long id : nodeList) {
				if (Long.compare(investigador.getInvestigadorId(), id) == 0) {
					nodes.add(investigador);

				}
			}
		}

		return nodes;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		RedSocial clone = new RedSocial();
		
		UndirectedSparseGraph<Investigador, String> cloneRed = new UndirectedSparseGraph<>();

//		Add nodes
		List<Investigador> vertices = new ArrayList<>(red.getVertices());

		for (Investigador investigador : vertices) {
			cloneRed.addVertex(investigador);
		}
		
//		Add edges
		for (String e : red.getEdges()) {
			cloneRed.addEdge(e, red.getEndpoints(e));
		}

		clone.setRed(cloneRed);
		
		return clone;

	}

}
