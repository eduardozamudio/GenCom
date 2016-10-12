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
package main.java.edu.isistan.genCom.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessAbs;
import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessIndependence;
import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

public class Evaluador extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JComboBox cbNodos;
	private JButton btEliminar;
	
	private FitnessAbs fitness; //TODO Implement fitness function selector
	private RedSocial red;

	
	/**
	 * Create the frame.
	 * @param red Red social
	 */
	public Evaluador(RedSocial red) {
		this.red = red;
		this.fitness = new FitnessIndependence(red);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 787, 409);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 775, 0 };
		gbl_contentPane.rowHeights = new int[] { 36, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 32, 117, 0 };
		gbl_panel.rowHeights = new int[] { 25, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		cbNodos = new JComboBox();
		GridBagConstraints gbc_cbNodos = new GridBagConstraints();
		gbc_cbNodos.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbNodos.anchor = GridBagConstraints.NORTH;
		gbc_cbNodos.insets = new Insets(0, 0, 0, 5);
		gbc_cbNodos.gridx = 0;
		gbc_cbNodos.gridy = 0;
		panel.add(cbNodos, gbc_cbNodos);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbNodos.getSelectedIndex() != -1) {
					Investigador inv = (Investigador) cbNodos.getSelectedItem();
					agregarNodo(inv);
				}
			}
		});
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnAgregar.gridx = 1;
		gbc_btnAgregar.gridy = 0;
		panel.add(btnAgregar, gbc_btnAgregar);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Nodo", "Degree", "Betweeness", "Degree", "Eigenvalue" }));
		// TODO Evaluar el fitness a medida que se modifican las filas de la
		// tabla
		scrollPane.setViewportView(table);

		btEliminar = new JButton("Quitar");
		btEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarSeleccionado();
			}
		});
		GridBagConstraints gbc_btEliminar = new GridBagConstraints();
		gbc_btEliminar.anchor = GridBagConstraints.EAST;
		gbc_btEliminar.insets = new Insets(0, 0, 5, 0);
		gbc_btEliminar.gridx = 0;
		gbc_btEliminar.gridy = 2;
		contentPane.add(btEliminar, gbc_btEliminar);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.SOUTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		contentPane.add(panel_1, gbc_panel_1);

		JLabel lblNewLabel = new JLabel("Fitness");
		panel_1.add(lblNewLabel);

		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
	}

	/**
	 * Quita el registro de la tabla seleccionado
	 */
	protected void quitarSeleccionado() {
		int i = table.getSelectedRow();

		if (i != -1) {
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
			dtm.removeRow(i);
		}
	}

	/**
	 * Agrega el nodo seleccionado a la comisión para su evaluación
	 */
	protected void agregarNodo(Investigador inv) {
		boolean existeEnTabla = false;

		// verifica que el nodo no se encuentre ingresado ya
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();

		for (int i = 0; i < dtm.getRowCount(); i++) {

			Investigador nodo = (Investigador) dtm.getValueAt(i, 0);
			if (inv == nodo) {
				existeEnTabla = true;
				break;
			}
		}

		if (!existeEnTabla) {
			// Obtiene sus métricas de centralidad desde la Red Social
			double d, b, c, e = 0;
			d = red.getDegree(inv);
			b = red.getBetweeness(inv);
			c = red.getCloseness(inv);
			e = red.getEigenVector(inv);

			// Agrega el nodo seleccionado a la tabla
			dtm.addRow(new Object[] { inv, String.valueOf(d),
					String.valueOf(b), String.valueOf(c), String.valueOf(e)

			});

			// TODO Calcula el fitness desde Evolutive
			double result = 0;
//			DataSet distancias = new DataSet();
//			double minDistancia = DIAMETRO;
//			
//			for (int i = 0; i < dtm.getRowCount(); i++) {
//				Investigador inv1 = (Investigador) dtm.getValueAt(i, 0);
//				for (int j = i + 1; j < dtm.getRowCount(); j++) {
//					Investigador inv2 = (Investigador) dtm.getValueAt(j, 0);
//					double d1 = red.getDistancias(inv1, inv2);
//					result = result + d1;
//					distancias.addValue(d1);
//					
//					if (d1 < minDistancia && d1 != 0)
//						minDistancia = d1;
//					;
//				}
//			}
//
//			// Calcula el promedio
//			double desvStd = 0;
//			
//			if (distancias.getSize() != 0)
//				result = result / distancias.getSize();
//
//			desvStd = distancias.getStandardDeviation();
//			
////			Factoriza el resultado
//			result = (result + minDistancia) / (2*DIAMETRO);			

			List<Investigador> comission = new ArrayList<>();
			
			for (int i = 0; i < dtm.getRowCount(); i++) {
				comission.add((Investigador) dtm.getValueAt(i, 0));
			}
			
			result = fitness.getFitness(comission);
			
			textField.setText(String.valueOf(result));
		}
	}

	/**
	 * Establece los parámetros iniciales de los componentes de la ventana
	 */
	public void inicializar() {
		// Cargar los combos
		List<Investigador> nodos = red.getNodos();
		Collections.sort(nodos, new Comparator<Investigador>() {

			@Override
			public int compare(Investigador arg0, Investigador arg1) {
				return arg0.getNombre().compareToIgnoreCase(arg1.getNombre());
			}
		});

		cbNodos.removeAll();
		for (Object object : nodos) {
			cbNodos.addItem(object);
		}

		// Limpiar la tabla
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < dtm.getRowCount(); i++) {
			dtm.removeRow(i);
		}

	}

	public FitnessAbs getFitness() {
		return fitness;
	}

	public void setFitness(FitnessAbs fitness) {
		this.fitness = fitness;
	}
}
