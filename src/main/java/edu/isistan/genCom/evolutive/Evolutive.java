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
package main.java.edu.isistan.genCom.evolutive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.factories.ListPermutationFactory;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessAbs;
import main.java.edu.isistan.genCom.evolutive.ag.operator.ModeloPoblacional;
import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.InvestigadorSortByEspecialista;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

/**
 * Configuración y evolución de la red social
 * 
 * @author eduardo
 * 
 */
public class Evolutive {
	
	private Integer ejecuciones;
	private List<Generacion> historialEjecuciones = new ArrayList<>();
	private ConfiguracionAG configuracion;
	private RedSocial redSocial;
	private int cromosomaSize;		
	private int comisionSize;
	private List<Investigador> investigadores;	

	public int getCromosomaSize() {
		return cromosomaSize;
	}

	public void setCromosomaSize(int size) {
		if (size > 0)
			cromosomaSize = size;
	}

	public RedSocial getRedSocial() {
		return redSocial;
	}

	public void setRedSocial(RedSocial redSocial) {
		this.redSocial = redSocial;
	}

	public List<Generacion> getHistorialEjecuciones() {
		return historialEjecuciones;
	}

	public void setHistorialEjecuciones(List<Generacion> historialEjecuciones) {
		this.historialEjecuciones = historialEjecuciones;
	}

	public ConfiguracionAG getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(ConfiguracionAG configuracion) {
		this.configuracion = configuracion;
	}

	public Integer getEjecuciones() {
		return ejecuciones;
	}

	public void setEjecuciones(Integer ejecuciones) {
		this.ejecuciones = ejecuciones;
	}

	public int getComisionSize() {
		return comisionSize;
	}

	public void setComisionSize(int comisionSize) {
		this.comisionSize = comisionSize;
	}

	/**
	 * Establece la configuración
	 * 
	 * @param configuracion
	 * @param ejecuciones
	 * @param comisionSize
	 */
	public void setConfig(ConfiguracionAG configuracion, int ejecuciones,
			int comisionSize) {
		this.configuracion = configuracion;
		this.ejecuciones = ejecuciones;
		this.comisionSize = comisionSize;
	}

	/**
	 * Genera la comisión
	 * 
	 * @return Ejecuciones del AG
	 * 
	 * @throws Exception
	 */
	public Generacion generarComision() throws Exception {
		/*Configurar los parámetros de ejecución del AG (genes, fitness function, factory, operadores, pipeline, selection, engine)*/

		Generacion generacion = new Generacion();
		generacion.setConfiguracion(configuracion);

//		Cromosoma
		List<Integer> cromosoma = generarCromosoma();

		RedFitness evaluador = iniciarEvaluador(cromosoma);
		
		evaluador.setComisionAleatoria(comisionSize);

		CandidateFactory<List> factory = new ListPermutationFactory(cromosoma);

//		Pipeline
		List<EvolutionaryOperator> operators = new LinkedList<EvolutionaryOperator>();

		operators.add(configuracion.getCruce().getInstance());
		operators.add(configuracion.getMutacion().getInstance());

		EvolutionaryOperator pipeline = new EvolutionPipeline(operators);

//		Selection		
		SelectionStrategy selection = configuracion.getSeleccion().getInstance();
		
//		Engine
		EvolutionEngine engine = configuracion.getModelo().getInstance(evaluador,
				factory, pipeline, selection);

		
//		Committees
		Integer poblacionSize = investigadores.size() / comisionSize;

		for (int j = 0; j < ejecuciones; j++) {

			Ejecucion ejecucion = new Ejecucion(getConfiguracion());

			double fitnessDeComision;
			long tiempo = System.currentTimeMillis();

			List result = (List) engine.evolve(poblacionSize, 0,
					new GenerationCount(configuracion.getCorte()));


//			Results
			List<Integer> comisionResultado = new ArrayList(comisionSize);

			boolean[] comision = evaluador.getComision();

			for (int i = 0; i < comision.length; i++) {
				if (comision[i])
					comisionResultado.add((Integer) result.get(i));
			}

			List<Investigador> comResultInvestigadores = evaluador
					.getCommisionNodes(comisionResultado);
			
			fitnessDeComision = evaluador
					.getFitnessAcumulated(comResultInvestigadores);


//			Store results
			List<Investigador> nodos = new ArrayList<>();

			for (Integer o : comisionResultado) {
				nodos.add(investigadores.get(o));
			}

			tiempo = System.currentTimeMillis() - tiempo;

			ejecucion.setComision(nodos);
			ejecucion.setFitness(fitnessDeComision);
			ejecucion.setTiempo(tiempo);

			generacion.addEjecucion(ejecucion);
		}

		generacion.setFecha(new Date());
		historialEjecuciones.add(generacion);

		return generacion;

	}

	/**
	 * Inicializa el evaluador que contiene la función de fitness
	 * 
	 * @param cromosoma
	 *            Listado con los punteros del listado de nodos contenidos en la
	 *            red social
	 * @return Una nueva instancia del evaluador de fitness
	 */
	public RedFitness iniciarEvaluador(List<Integer> cromosoma) {
		FitnessAbs fitnessFunction = configuracion.getFitness().getInstance(redSocial);
		
		RedFitness evaluador = new RedFitness(redSocial, cromosoma.size(), investigadores, fitnessFunction);
		
		return evaluador;
	}

	/**
	 * Genera un vector de enteros representando los id de los nodos de la red
	 * 
	 * @return vector de enteros con los punteros del listado de nodos de la red
	 */
	public List<Integer> generarCromosoma() {
		investigadores = redSocial.getNodos();

//		Set chromosome size
		Collections.sort(investigadores, new InvestigadorSortByEspecialista());
		int i = 0;
		
		for (Investigador inv : investigadores) {
			if (inv.isEsEspecialista())
				i++;
		}

		cromosomaSize = i;

		
		List<Integer> cromosoma = new ArrayList<>(cromosomaSize);
		
		for (int j = 0; j < cromosomaSize; j++) {
			cromosoma.add(j);
		}

		return cromosoma;
	}

	public void resetHistorial() {
		historialEjecuciones = new ArrayList<>();
	}

}
