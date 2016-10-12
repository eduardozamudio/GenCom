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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.GraphMLWriter;
import main.java.edu.isistan.genCom.evolutive.ConfiguracionAG;
import main.java.edu.isistan.genCom.evolutive.Ejecucion;
import main.java.edu.isistan.genCom.evolutive.Evolutive;
import main.java.edu.isistan.genCom.evolutive.Generacion;
import main.java.edu.isistan.genCom.evolutive.ag.functions.CreatorFFGroup;
import main.java.edu.isistan.genCom.evolutive.ag.functions.CreatorIndependence;
import main.java.edu.isistan.genCom.evolutive.ag.functions.CreatorKPPneg;
import main.java.edu.isistan.genCom.evolutive.ag.functions.CreatorKPPpos;
import main.java.edu.isistan.genCom.evolutive.ag.functions.FFCreator;
import main.java.edu.isistan.genCom.evolutive.ag.functions.FitnessFunction;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorGenerational;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorOpInsert;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorOpOX;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorOpPMX;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorOpSwap;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorSUS;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorSteadyState;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CreatorTournament;
import main.java.edu.isistan.genCom.evolutive.ag.operator.CrossoverCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.ModeloCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.ModeloPoblacional;
import main.java.edu.isistan.genCom.evolutive.ag.operator.MutationCreator;
import main.java.edu.isistan.genCom.evolutive.ag.operator.OperadorEvo;
import main.java.edu.isistan.genCom.evolutive.ag.operator.OperadorSeleccion;
import main.java.edu.isistan.genCom.evolutive.ag.operator.SelectionCreator;
import main.java.edu.isistan.genCom.redSocial.Investigador;
import main.java.edu.isistan.genCom.redSocial.RedSocial;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField txEnlaces;
	private JTextField tfNodos;
	private JComboBox cbCruce;
	private JComboBox cbSeleccion;
	private JSpinner txIntegrantes;
	private JSpinner tfEjecuciones;
	private JTable tbComision;
	private JTable tbHistorial;

	private RedSocial redSocial;
	private Evolutive evolutive;
	private FitnessFunction fitnessFunctions;
	private OperadorEvo crossoverOperators;
	private OperadorEvo mutationOperators;
	private OperadorSeleccion selectionOperators;
	private ModeloPoblacional modelos;

	private JComboBox cbMutacion;
	private JComboBox cbModelo;
	private JTextField tfProbCruce;
	private JTextField txProbMutacion;
	private JSpinner txCorte;
	private JComboBox cbFitness;
	private JTable tbEjecuciones;
	private JTable tbResumen;
	private Generacion generacionCargada;
	private JTextField tfComponentes;
	private JTable tbComponentes;
	private JButton btnSeleccionar;
	private JTable tbNodos;

	public RedSocial getRedSocial() {
		return redSocial;
	}

	public void setRedSocial(RedSocial redSocial) {
		this.redSocial = redSocial;
	}

	public Evolutive getEvolutive() {
		return evolutive;
	}

	public void setEvolutive(Evolutive evolutive) {
		this.evolutive = evolutive;
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		inicializar();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1009, 597);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnInicio = new JMenu("File");
		menuBar.add(mnInicio);

		JMenuItem mntmSalir = new JMenuItem("Quit");
		mnInicio.add(mntmSalir);
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Principal.this.dispose();
			}
		});

		JMenu mnHerramientas = new JMenu("Social Network");
		menuBar.add(mnHerramientas);

		JMenuItem mntmEvaluarFitness = new JMenuItem("Test fitness");
		mntmEvaluarFitness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirEvaluador();
			}
		});
		mnHerramientas.add(mntmEvaluarFitness);
		mntmEvaluarFitness.setEnabled(false);

		JMenuItem mntmConexin = new JMenuItem("DB Connection");
		mnHerramientas.add(mntmConexin);

		JMenuItem mntmExportargraphml = new JMenuItem("Export (graphml)");
		mntmExportargraphml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportarRed();
			}
		});
		mnHerramientas.add(mntmExportargraphml);
		mnHerramientas.setEnabled(false);
		
		mntmConexin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexion conexion = new Conexion();
				conexion.cargarDatos();
				conexion.setVisible(true);
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_herramientas = new JPanel();
		panel_herramientas.setBorder(new EmptyBorder(0, 0, 0, 5));
		contentPane.add(panel_herramientas, BorderLayout.WEST);
		GridBagLayout gbl_panel_herramientas = new GridBagLayout();
		gbl_panel_herramientas.columnWidths = new int[] { 150, 0 };
		gbl_panel_herramientas.rowHeights = new int[] { 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_herramientas.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_herramientas.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_herramientas.setLayout(gbl_panel_herramientas);

		JLabel lblInformacinDeLa = new JLabel("Social network summary");
		lblInformacinDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacinDeLa.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblInformacinDeLa = new GridBagConstraints();
		gbc_lblInformacinDeLa.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInformacinDeLa.insets = new Insets(0, 0, 5, 0);
		gbc_lblInformacinDeLa.gridx = 0;
		gbc_lblInformacinDeLa.gridy = 0;
		panel_herramientas.add(lblInformacinDeLa, gbc_lblInformacinDeLa);

		JPanel panel_info_red = new JPanel();
		GridBagConstraints gbc_panel_info_red = new GridBagConstraints();
		gbc_panel_info_red.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_info_red.insets = new Insets(0, 0, 5, 0);
		gbc_panel_info_red.anchor = GridBagConstraints.NORTH;
		gbc_panel_info_red.gridx = 0;
		gbc_panel_info_red.gridy = 1;
		panel_herramientas.add(panel_info_red, gbc_panel_info_red);
		GridBagLayout gbl_panel_info_red = new GridBagLayout();
		gbl_panel_info_red.columnWidths = new int[] { 150, 45, 0 };
		gbl_panel_info_red.rowHeights = new int[] { 0, 15, 0, 0, 0 };
		gbl_panel_info_red.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_info_red.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_info_red.setLayout(gbl_panel_info_red);

		JLabel label = new JLabel("Nodes");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_info_red.add(label, gbc_label);

		tfNodos = new JTextField();
		tfNodos.setEditable(false);
		tfNodos.setColumns(10);
		GridBagConstraints gbc_tfNodos = new GridBagConstraints();
		gbc_tfNodos.insets = new Insets(0, 0, 5, 0);
		gbc_tfNodos.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNodos.gridx = 1;
		gbc_tfNodos.gridy = 0;
		panel_info_red.add(tfNodos, gbc_tfNodos);

		JLabel lblEnlaces = new JLabel("Links");
		GridBagConstraints gbc_lblEnlaces = new GridBagConstraints();
		gbc_lblEnlaces.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnlaces.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblEnlaces.gridx = 0;
		gbc_lblEnlaces.gridy = 1;
		panel_info_red.add(lblEnlaces, gbc_lblEnlaces);

		txEnlaces = new JTextField();
		txEnlaces.setEditable(false);
		GridBagConstraints gbc_txEnlaces = new GridBagConstraints();
		gbc_txEnlaces.insets = new Insets(0, 0, 5, 0);
		gbc_txEnlaces.fill = GridBagConstraints.HORIZONTAL;
		gbc_txEnlaces.gridx = 1;
		gbc_txEnlaces.gridy = 1;
		panel_info_red.add(txEnlaces, gbc_txEnlaces);
		txEnlaces.setColumns(10);

		JButton button = new JButton("Update");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizarDatosDeRed();
			}
		});

		JLabel lblComponentes = new JLabel("Components");
		GridBagConstraints gbc_lblComponentes = new GridBagConstraints();
		gbc_lblComponentes.anchor = GridBagConstraints.EAST;
		gbc_lblComponentes.insets = new Insets(0, 0, 5, 5);
		gbc_lblComponentes.gridx = 0;
		gbc_lblComponentes.gridy = 2;
		panel_info_red.add(lblComponentes, gbc_lblComponentes);

		tfComponentes = new JTextField();
		tfComponentes.setEditable(false);
		tfComponentes.setColumns(10);
		GridBagConstraints gbc_tfComponentes = new GridBagConstraints();
		gbc_tfComponentes.insets = new Insets(0, 0, 5, 0);
		gbc_tfComponentes.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfComponentes.gridx = 1;
		gbc_tfComponentes.gridy = 2;
		panel_info_red.add(tfComponentes, gbc_tfComponentes);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.EAST;
		gbc_button.gridx = 1;
		gbc_button.gridy = 3;
		panel_info_red.add(button, gbc_button);

		JScrollPane scrollPane_4 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
		gbc_scrollPane_4.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_4.gridx = 0;
		gbc_scrollPane_4.gridy = 2;
		panel_herramientas.add(scrollPane_4, gbc_scrollPane_4);

		tbComponentes = new JTable();
		tbComponentes.setAutoCreateRowSorter(true);
		tbComponentes.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Node", "Component", "Size" }));
		tbComponentes.getColumnModel().getColumn(0).setPreferredWidth(0);
		tbComponentes.getColumnModel().getColumn(0).setMinWidth(0);
		tbComponentes.getColumnModel().getColumn(0).setMaxWidth(0);

		tbComponentes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					if (tbComponentes.getSelectedRow() != -1) {
						Set nodos = (Set) tbComponentes.getValueAt(tbComponentes.getSelectedRow(), 0);
						cargarNodos(nodos);
					}
				}
			}

		});

		scrollPane_4.setViewportView(tbComponentes);

		btnSeleccionar = new JButton("Select");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tbComponentes.getSelectedRow() != -1) {
					DefaultTableModel dtm = (DefaultTableModel) tbComponentes.getModel();

					// Seleccionar el componente
					redSocial.reducirA((Set) dtm.getValueAt(tbComponentes.getSelectedRow(), 0));

					tbComponentes.setEnabled(false);
					btnSeleccionar.setEnabled(false);
				}

			}
		});
		GridBagConstraints gbc_btnSeleccionar = new GridBagConstraints();
		gbc_btnSeleccionar.anchor = GridBagConstraints.EAST;
		gbc_btnSeleccionar.insets = new Insets(0, 0, 5, 0);
		gbc_btnSeleccionar.gridx = 0;
		gbc_btnSeleccionar.gridy = 3;
		panel_herramientas.add(btnSeleccionar, gbc_btnSeleccionar);

		JScrollPane scrollPane_5 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
		gbc_scrollPane_5.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_5.gridx = 0;
		gbc_scrollPane_5.gridy = 4;
		panel_herramientas.add(scrollPane_5, gbc_scrollPane_5);

		tbNodos = new JTable();
		tbNodos.setAutoCreateRowSorter(true);
		tbNodos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nodes", "Elegible" }));
		scrollPane_5.setViewportView(tbNodos);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 5;
		panel_herramientas.add(separator_1, gbc_separator_1);

		JLabel lblConfiguracinDelAg = new JLabel("GA configuration");
		lblConfiguracinDelAg.setHorizontalAlignment(SwingConstants.LEFT);
		lblConfiguracinDelAg.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblConfiguracinDelAg = new GridBagConstraints();
		gbc_lblConfiguracinDelAg.insets = new Insets(0, 0, 5, 0);
		gbc_lblConfiguracinDelAg.gridx = 0;
		gbc_lblConfiguracinDelAg.gridy = 6;
		panel_herramientas.add(lblConfiguracinDelAg, gbc_lblConfiguracinDelAg);

		JPanel panel_AG = new JPanel();
		GridBagConstraints gbc_panel_AG = new GridBagConstraints();
		gbc_panel_AG.insets = new Insets(0, 0, 5, 0);
		gbc_panel_AG.anchor = GridBagConstraints.NORTH;
		gbc_panel_AG.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_AG.gridx = 0;
		gbc_panel_AG.gridy = 8;
		panel_herramientas.add(panel_AG, gbc_panel_AG);
		GridBagLayout gbl_panel_AG = new GridBagLayout();
		gbl_panel_AG.columnWidths = new int[] { 150, 45, 43, 0 };
		gbl_panel_AG.rowHeights = new int[] { 0, 0, 0, 0, 15, 0, 0, 0, 0 };
		gbl_panel_AG.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_AG.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_AG.setLayout(gbl_panel_AG);

		JLabel lblProbabilidad = new JLabel("Probabilty");
		GridBagConstraints gbc_lblProbabilidad = new GridBagConstraints();
		gbc_lblProbabilidad.insets = new Insets(0, 0, 5, 0);
		gbc_lblProbabilidad.gridx = 2;
		gbc_lblProbabilidad.gridy = 0;
		panel_AG.add(lblProbabilidad, gbc_lblProbabilidad);

		JLabel lblOperador = new JLabel("Crossover");
		GridBagConstraints gbc_lblOperador = new GridBagConstraints();
		gbc_lblOperador.anchor = GridBagConstraints.EAST;
		gbc_lblOperador.insets = new Insets(0, 0, 5, 5);
		gbc_lblOperador.gridx = 0;
		gbc_lblOperador.gridy = 1;
		panel_AG.add(lblOperador, gbc_lblOperador);

		cbCruce = new JComboBox();
		cbCruce.setModel(new DefaultComboBoxModel(crossoverOperators.getKeys()));
		cbCruce.setEditable(false);
		GridBagConstraints gbc_cbCruce = new GridBagConstraints();
		gbc_cbCruce.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbCruce.insets = new Insets(0, 0, 5, 5);
		gbc_cbCruce.gridx = 1;
		gbc_cbCruce.gridy = 1;
		panel_AG.add(cbCruce, gbc_cbCruce);

		tfProbCruce = new JTextField();
		tfProbCruce.setText("0.7");
		GridBagConstraints gbc_tfProbCruce = new GridBagConstraints();
		gbc_tfProbCruce.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfProbCruce.insets = new Insets(0, 0, 5, 0);
		gbc_tfProbCruce.gridx = 2;
		gbc_tfProbCruce.gridy = 1;
		panel_AG.add(tfProbCruce, gbc_tfProbCruce);

		JLabel lblMutacin = new JLabel("Mutation");
		GridBagConstraints gbc_lblMutacin = new GridBagConstraints();
		gbc_lblMutacin.anchor = GridBagConstraints.EAST;
		gbc_lblMutacin.insets = new Insets(0, 0, 5, 5);
		gbc_lblMutacin.gridx = 0;
		gbc_lblMutacin.gridy = 2;
		panel_AG.add(lblMutacin, gbc_lblMutacin);

		cbMutacion = new JComboBox();
		cbMutacion.setModel(new DefaultComboBoxModel(mutationOperators.getKeys()));
		cbMutacion.setEditable(false);
		GridBagConstraints gbc_cbMutacion = new GridBagConstraints();
		gbc_cbMutacion.insets = new Insets(0, 0, 5, 5);
		gbc_cbMutacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbMutacion.gridx = 1;
		gbc_cbMutacion.gridy = 2;
		panel_AG.add(cbMutacion, gbc_cbMutacion);

		txProbMutacion = new JTextField();
		txProbMutacion.setText("0.15");
		GridBagConstraints gbc_txProbMutacion = new GridBagConstraints();
		gbc_txProbMutacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txProbMutacion.insets = new Insets(0, 0, 5, 0);
		gbc_txProbMutacion.gridx = 2;
		gbc_txProbMutacion.gridy = 2;
		panel_AG.add(txProbMutacion, gbc_txProbMutacion);

		JSeparator separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 0;
		gbc_separator_3.gridy = 3;
		panel_AG.add(separator_3, gbc_separator_3);

		JSeparator separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.insets = new Insets(0, 0, 5, 0);
		gbc_separator_4.gridx = 2;
		gbc_separator_4.gridy = 3;
		panel_AG.add(separator_4, gbc_separator_4);

		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 1;
		gbc_separator_2.gridy = 3;
		panel_AG.add(separator_2, gbc_separator_2);

		JLabel lblSeleccin = new JLabel("Selection");
		GridBagConstraints gbc_lblSeleccin = new GridBagConstraints();
		gbc_lblSeleccin.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSeleccin.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccin.gridx = 0;
		gbc_lblSeleccin.gridy = 4;
		panel_AG.add(lblSeleccin, gbc_lblSeleccin);

		cbSeleccion = new JComboBox();
		cbSeleccion.setModel(new DefaultComboBoxModel(selectionOperators.getKeys()));
		cbSeleccion.setEditable(false);
		GridBagConstraints gbc_cbSeleccion = new GridBagConstraints();
		gbc_cbSeleccion.insets = new Insets(0, 0, 5, 5);
		gbc_cbSeleccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSeleccion.gridx = 1;
		gbc_cbSeleccion.gridy = 4;
		panel_AG.add(cbSeleccion, gbc_cbSeleccion);

		JLabel label_1 = new JLabel("Population");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 5;
		panel_AG.add(label_1, gbc_label_1);

		cbModelo = new JComboBox();
		cbModelo.setModel(new DefaultComboBoxModel(modelos.getKeys()));
		cbModelo.setEditable(false);
		GridBagConstraints gbc_cbModelo = new GridBagConstraints();
		gbc_cbModelo.insets = new Insets(0, 0, 5, 5);
		gbc_cbModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbModelo.gridx = 1;
		gbc_cbModelo.gridy = 5;
		panel_AG.add(cbModelo, gbc_cbModelo);

		JLabel label_ff = new JLabel("Fitness Function");
		GridBagConstraints gbc_label_ff = new GridBagConstraints();
		gbc_label_ff.anchor = GridBagConstraints.EAST;
		gbc_label_ff.insets = new Insets(0, 0, 5, 5);
		gbc_label_ff.gridx = 0;
		gbc_label_ff.gridy = 8;
		panel_AG.add(label_ff, gbc_label_ff);

		cbFitness = new JComboBox();
		cbFitness.setModel(new DefaultComboBoxModel(fitnessFunctions.getKeys()));
		cbFitness.setEditable(false);
		GridBagConstraints gbc_cbFitness = new GridBagConstraints();
		gbc_cbFitness.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbFitness.insets = new Insets(0, 0, 5, 5);
		gbc_cbFitness.gridx = 1;
		gbc_cbFitness.gridy = 8;
		panel_AG.add(cbFitness, gbc_cbFitness);

		JLabel label_2 = new JLabel("Runs");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 6;
		panel_AG.add(label_2, gbc_label_2);

		tfEjecuciones = new JSpinner();
		tfEjecuciones.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		GridBagConstraints gbc_tfEjecuciones = new GridBagConstraints();
		gbc_tfEjecuciones.insets = new Insets(0, 0, 5, 5);
		gbc_tfEjecuciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEjecuciones.gridx = 1;
		gbc_tfEjecuciones.gridy = 6;
		panel_AG.add(tfEjecuciones, gbc_tfEjecuciones);

		JLabel lblGeneracionesDeCorte = new JLabel("Generations");
		GridBagConstraints gbc_lblGeneracionesDeCorte = new GridBagConstraints();
		gbc_lblGeneracionesDeCorte.insets = new Insets(0, 0, 0, 5);
		gbc_lblGeneracionesDeCorte.gridx = 0;
		gbc_lblGeneracionesDeCorte.gridy = 7;
		panel_AG.add(lblGeneracionesDeCorte, gbc_lblGeneracionesDeCorte);

		txCorte = new JSpinner();
		txCorte.setModel(new SpinnerNumberModel(25, 0, 50000, 1));
		GridBagConstraints gbc_txCorte = new GridBagConstraints();
		gbc_txCorte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txCorte.insets = new Insets(0, 0, 0, 5);
		gbc_txCorte.gridx = 1;
		gbc_txCorte.gridy = 7;
		panel_AG.add(txCorte, gbc_txCorte);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 8;
		panel_herramientas.add(separator, gbc_separator);

		JLabel lblComisiones = new JLabel("Groups");
		lblComisiones.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblComisiones = new GridBagConstraints();
		gbc_lblComisiones.insets = new Insets(0, 0, 5, 0);
		gbc_lblComisiones.gridx = 0;
		gbc_lblComisiones.gridy = 9;
		panel_herramientas.add(lblComisiones, gbc_lblComisiones);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 10;
		panel_herramientas.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblIntegrantes = new JLabel("Members");
		GridBagConstraints gbc_lblIntegrantes = new GridBagConstraints();
		gbc_lblIntegrantes.insets = new Insets(0, 0, 0, 5);
		gbc_lblIntegrantes.anchor = GridBagConstraints.EAST;
		gbc_lblIntegrantes.gridx = 0;
		gbc_lblIntegrantes.gridy = 0;
		panel.add(lblIntegrantes, gbc_lblIntegrantes);

		txIntegrantes = new JSpinner();
		txIntegrantes.setModel(new SpinnerNumberModel(5, 1, 100, 1));
		GridBagConstraints gbc_txIntegrantes = new GridBagConstraints();
		gbc_txIntegrantes.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIntegrantes.gridx = 1;
		gbc_txIntegrantes.gridy = 0;
		panel.add(txIntegrantes, gbc_txIntegrantes);

		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generarComisión();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 11;
		panel_herramientas.add(btnNewButton, gbc_btnNewButton);

		JPanel panel_comision_generada = new JPanel();
		panel_comision_generada.setBorder(new EmptyBorder(0, 5, 0, 0));
		contentPane.add(panel_comision_generada, BorderLayout.CENTER);
		GridBagLayout gbl_panel_comision_generada = new GridBagLayout();
		gbl_panel_comision_generada.columnWidths = new int[] { 0, 0 };
		gbl_panel_comision_generada.rowHeights = new int[] { 65, 111, 122, 194, 0 };
		gbl_panel_comision_generada.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_comision_generada.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		panel_comision_generada.setLayout(gbl_panel_comision_generada);

		JPanel pnResumen = new JPanel();
		GridBagConstraints gbc_pnResumen = new GridBagConstraints();
		gbc_pnResumen.insets = new Insets(0, 0, 5, 0);
		gbc_pnResumen.fill = GridBagConstraints.BOTH;
		gbc_pnResumen.gridx = 0;
		gbc_pnResumen.gridy = 0;
		panel_comision_generada.add(pnResumen, gbc_pnResumen);
		GridBagLayout gbl_pnResumen = new GridBagLayout();
		gbl_pnResumen.columnWidths = new int[] { 216, 0 };
		gbl_pnResumen.rowHeights = new int[] { 15, 47, 0 };
		gbl_pnResumen.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_pnResumen.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnResumen.setLayout(gbl_pnResumen);

		JLabel label_3 = new JLabel("Summary");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 0);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 0;
		pnResumen.add(label_3, gbc_label_3);

		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 1;
		pnResumen.add(scrollPane_2, gbc_scrollPane_2);

		tbResumen = new JTable();
		tbResumen.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Runs", "Crossover", "Mutation",
						"Selection", "Population", "Generations", "Fitness Function", "Fitness avg.", "Fitness std.dev." }));
		scrollPane_2.setViewportView(tbResumen);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel_comision_generada.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 167, 0 };
		gbl_panel_1.rowHeights = new int[] { 15, 74, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblEjecuciones = new JLabel("Runs");
		GridBagConstraints gbc_lblEjecuciones = new GridBagConstraints();
		gbc_lblEjecuciones.anchor = GridBagConstraints.NORTH;
		gbc_lblEjecuciones.insets = new Insets(0, 0, 5, 0);
		gbc_lblEjecuciones.gridx = 0;
		gbc_lblEjecuciones.gridy = 0;
		panel_1.add(lblEjecuciones, gbc_lblEjecuciones);

		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 0;
		gbc_scrollPane_3.gridy = 1;
		panel_1.add(scrollPane_3, gbc_scrollPane_3);

		tbEjecuciones = new JTable();
		tbEjecuciones.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tbEjecuciones.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Runs", "Fitness", "Time" }));
		scrollPane_3.setViewportView(tbEjecuciones);
		tbEjecuciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting()) {
					if (tbEjecuciones.getSelectedRow() != -1) {
						Integer indice = Integer
								.valueOf(tbEjecuciones.getValueAt(tbEjecuciones.getSelectedRow(), 0).toString());
						cargarComision(indice);
					}
				}
			}

		});

		JPanel panel_comision_generada_datos = new JPanel();
		GridBagConstraints gbc_panel_comision_generada_datos = new GridBagConstraints();
		gbc_panel_comision_generada_datos.insets = new Insets(0, 0, 5, 0);
		gbc_panel_comision_generada_datos.fill = GridBagConstraints.BOTH;
		gbc_panel_comision_generada_datos.gridx = 0;
		gbc_panel_comision_generada_datos.gridy = 2;
		panel_comision_generada.add(panel_comision_generada_datos, gbc_panel_comision_generada_datos);
		GridBagLayout gbl_panel_comision_generada_datos = new GridBagLayout();
		gbl_panel_comision_generada_datos.columnWidths = new int[] { 0, 0 };
		gbl_panel_comision_generada_datos.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_comision_generada_datos.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_comision_generada_datos.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_comision_generada_datos.setLayout(gbl_panel_comision_generada_datos);

		JLabel lblComisinGenerada = new JLabel("Group members");
		GridBagConstraints gbc_lblComisinGenerada = new GridBagConstraints();
		gbc_lblComisinGenerada.insets = new Insets(0, 0, 5, 0);
		gbc_lblComisinGenerada.gridx = 0;
		gbc_lblComisinGenerada.gridy = 0;
		panel_comision_generada_datos.add(lblComisinGenerada, gbc_lblComisinGenerada);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_comision_generada_datos.add(scrollPane, gbc_scrollPane);

		tbComision = new JTable();
		scrollPane.setViewportView(tbComision);
		tbComision.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Node", "Description", "Degree", "Betweeness", "Closeness", "Eigenvector" }));
		tbComision.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 3;
		panel_comision_generada.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);

		JLabel lblHistoriaDeEjecuciones = new JLabel("History");
		GridBagConstraints gbc_lblHistoriaDeEjecuciones = new GridBagConstraints();
		gbc_lblHistoriaDeEjecuciones.insets = new Insets(0, 0, 5, 0);
		gbc_lblHistoriaDeEjecuciones.gridx = 0;
		gbc_lblHistoriaDeEjecuciones.gridy = 0;
		panel_2.add(lblHistoriaDeEjecuciones, gbc_lblHistoriaDeEjecuciones);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);

		tbHistorial = new JTable();
		scrollPane_1.setViewportView(tbHistorial);
		tbHistorial.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Datetime", "Crossover", "Mutation", "Selection", "Population", "Runs",
						"Generations", "Fitness Function", "Fitness avg.", "Fitness std.dev." }));
		tbHistorial.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tbHistorial.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				if (arg0.getValueIsAdjusting()) {
					if (tbHistorial.getSelectedRow() != -1) {
						Integer indice = tbHistorial.getSelectedRow();
						generacionCargada = evolutive.getHistorialEjecuciones().get(indice);
						cargarGeneracion();
					}
				}
			}

		});
	}

	/**
	 * Initialize the esential components
	 */
	private void inicializar() {
//		Fitness functions
		fitnessFunctions = new FitnessFunction();

		List<FFCreator> creators = new ArrayList<>();

		creators.add(new CreatorKPPpos());
		creators.add(new CreatorKPPneg());
		creators.add(new CreatorIndependence());
		creators.add(new CreatorFFGroup());

		for (FFCreator ffCreator : creators) {
			fitnessFunctions.set(ffCreator.getNombre(), ffCreator);
		}
		
		
//		Crossover operators
		crossoverOperators = new OperadorEvo();
		
		List<CrossoverCreator> creatorsCO = new ArrayList<>();
		
		creatorsCO.add(new CreatorOpPMX());
		creatorsCO.add(new CreatorOpOX());
		
		for (CrossoverCreator creator : creatorsCO) {
			crossoverOperators.set(creator.getNombre(), creator);
		}
		
		
//		Mutation operators
		mutationOperators = new OperadorEvo();
		
		List<MutationCreator> creatorsM = new ArrayList<>();
		
		creatorsM.add(new CreatorOpInsert());
		creatorsM.add(new CreatorOpSwap());
		
		for (MutationCreator creator : creatorsM) {
			mutationOperators.set(creator.getNombre(), creator);
		}
		
		
//		Selection operators
		selectionOperators = new OperadorSeleccion();
		
		List<SelectionCreator> creatorsS = new ArrayList<>();
		
		creatorsS.add(new CreatorTournament());
		creatorsS.add(new CreatorSUS());
		
		for (SelectionCreator creator : creatorsS) {
			selectionOperators.set(creator.getNombre(), creator);
		}
		
		
//		Modelos
		modelos = new ModeloPoblacional();
		
		List<ModeloCreator> creatorsMod = new ArrayList<>();
		
		creatorsMod.add(new CreatorGenerational());
		creatorsMod.add(new CreatorSteadyState());
		
		for (ModeloCreator creator : creatorsMod) {
			modelos.set(creator.getNombre(), creator);
		}
	}

	/**
	 * Persiste la red actual en un archivo XML utilizando la biblioteca GraphML
	 * 
	 * @param archivo
	 *            Nombre del archivo utilizado para persistir la red
	 */
	public void exportarRed() {
		// TODO Chequear que la red esté cargada

		// TODO Abrir un dialog para solicitar el path del destino
		String archivo = "red.graphml";

		try {
			GraphMLWriter<Investigador, String> graphWriter = new GraphMLWriter<Investigador, String>();

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(archivo)));

			// Descripción de los nodos
			graphWriter.addVertexData("investigador", null, "0", new Transformer<Investigador, String>() {
				@Override
				public String transform(Investigador i) {
					return i.toString();
				}
			});

			// Descripción de los enlaces
			graphWriter.addEdgeData("relacion", null, "0", new Transformer<String, String>() {
				@Override
				public String transform(String r) {
					String result = r;
					return result;
				}
			});

			graphWriter.save(this.redSocial.getRed(), out);

		} catch (Exception e) {
			System.out.println("Ocurrió un error al persistir la red: " + e.getMessage());
		}

	}

	/**
	 * Carga los nodos del componente en la tabla
	 * 
	 * @param nodos
	 */
	protected void cargarNodos(Set nodos) {
		DefaultTableModel dtm = (DefaultTableModel) tbNodos.getModel();
		limpiarModelo(dtm);

		// Carga los nodos en la tabla
		for (Object object : nodos) {
			Investigador inv = (Investigador) object;
			String valorEspecialista = inv.isEsEspecialista() ? "S" : "N";

			dtm.addRow(new String[] { inv.toString(), valorEspecialista });
		}
	}

	/**
	 * Lanza la ventana del evaluador de fitness
	 */
	protected void abrirEvaluador() {
		// Crear una instancia de la ventana
		Evaluador evaluador = new Evaluador(redSocial);

		// inicializar el evaluador
		evaluador.inicializar();

		// mostrar la ventana como un diálogo
		evaluador.setVisible(true);

	}

	/**
	 * Genera la comisión a partir de la configuración seleccionada del
	 * algoritmo genético
	 */
	protected void generarComisión() {
		// Configurar el AG
		CrossoverCreator cruceCreator = (CrossoverCreator) crossoverOperators.get(cbCruce.getSelectedItem().toString());
		Double probCruce = Double.valueOf(tfProbCruce.getText());

		MutationCreator mutacionCreator = (MutationCreator) mutationOperators.get(cbMutacion.getSelectedItem().toString());
		Double probMutacion = Double.valueOf(txProbMutacion.getText());

		SelectionCreator seleccionCreator = selectionOperators.get(cbSeleccion.getSelectedItem().toString());

		ModeloCreator modeloCreator = (ModeloCreator) modelos.get(cbModelo.getSelectedItem().toString());

		Integer ejecuciones = (Integer) tfEjecuciones.getValue();
		Integer comisionSize = (Integer) txIntegrantes.getValue();
		Integer corte = (Integer) txCorte.getValue();

		FFCreator fitnessCreator = fitnessFunctions.get(cbFitness.getSelectedItem().toString());

		cruceCreator.setProbability(probCruce.doubleValue());
		mutacionCreator.setGenerators(probMutacion.doubleValue(), 1);
		
		evolutive.setConfig(new ConfiguracionAG(cruceCreator, mutacionCreator, seleccionCreator, modeloCreator, corte, fitnessCreator), ejecuciones,
				comisionSize);

		
		// Generar la comisión
		try {
			this.generacionCargada = evolutive.generarComision();
			
			
			// Load results
			cargarGeneracion();
			cargarHistorial();
			
		} catch (Exception e) {
			// TODO Generar un mensaje de error
			e.printStackTrace();
		}

	}

	/**
	 * Carga la sección con el resumen de la generación
	 * 
	 * @param generacion
	 */
	private void cargarGeneracion() {
		ConfiguracionAG conf = generacionCargada.getConfiguracion();

		// Carga la tabla resumen
		DefaultTableModel dtm = (DefaultTableModel) tbResumen.getModel();
		limpiarModelo(dtm);

		dtm.addRow(new String[] { String.valueOf(generacionCargada.getEjecuciones().size()), 
				conf.getCruce().toString(),
				conf.getMutacion().toString(), 
				conf.getSeleccion().toString(), 
				conf.getModelo().toString(),
				String.valueOf(conf.getCorte()), 
				generacionCargada.getConfiguracion().getFitness().toString(),
				String.valueOf(generacionCargada.getFitnessPromedio()),
				String.valueOf(generacionCargada.getFitnessDesviacion()) });

		// Carga la tabla de ejecuciones
		DefaultTableModel dtmEjecucion = (DefaultTableModel) tbEjecuciones.getModel();
		limpiarModelo(dtmEjecucion);

		List<Ejecucion> ejecuciones = generacionCargada.getEjecuciones();
		for (Ejecucion ejecucion : ejecuciones) {
			dtmEjecucion.addRow(new String[] { String.valueOf(ejecuciones.indexOf(ejecucion)),
					String.valueOf(ejecucion.getFitness()), String.valueOf(ejecucion.getTiempo()) + " ms." });
		}

		// Limpia la tabla de comision
		DefaultTableModel dtmComision = (DefaultTableModel) tbComision.getModel();
		limpiarModelo(dtmComision);

	}

	/**
	 * Limpia todos los elementos de las tablas
	 */
	private void limpiarModelos() {
		Set<DefaultTableModel> modelos = new HashSet<>();

		modelos.add((DefaultTableModel) tbResumen.getModel());
		modelos.add((DefaultTableModel) tbEjecuciones.getModel());
		modelos.add((DefaultTableModel) tbComision.getModel());
		modelos.add((DefaultTableModel) tbHistorial.getModel());

		for (DefaultTableModel defaultTableModel : modelos) {
			limpiarModelo(defaultTableModel);
		}

	}

	/**
	 * Registra la generación en el historial
	 */
	private void cargarHistorial() {
		ConfiguracionAG conf = generacionCargada.getConfiguracion();

		// Carga la tabla de historial
		DefaultTableModel dtmLog = (DefaultTableModel) tbHistorial.getModel();

		dtmLog.addRow(new String[] {
				DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(generacionCargada.getFecha()),
				conf.getCruce().toString(), 
				conf.getMutacion().toString(), 
				conf.getSeleccion().toString(),
				conf.getModelo().toString(), 
				String.valueOf(generacionCargada.getEjecuciones().size()),
				String.valueOf(conf.getCorte()), 
				generacionCargada.getConfiguracion().getFitness().toString(),
				String.valueOf(generacionCargada.getFitnessPromedio()),
				String.valueOf(generacionCargada.getFitnessDesviacion()) });
	}

	/**
	 * Genera una nueva red social con los nodos y relaciones obtenidos a partir
	 * de la base de datos
	 */
	protected void actualizarDatosDeRed() {
		// Resetea el historial de ejecuciones
		evolutive.resetHistorial();

		// Actualiza la red
		redSocial.actualizar();
		Set componentes = redSocial.getComponentes();

		// Presenta información de la red
		tfNodos.setText(String.valueOf(redSocial.getCantidadDeNodos()));
		txEnlaces.setText(String.valueOf(redSocial.getCantidadDeEnlaces()));
		tfComponentes.setText(String.valueOf(componentes.size()));

		// Actualiza el listado de componentes
		DefaultTableModel dtm = (DefaultTableModel) tbComponentes.getModel();
		limpiarModelo(dtm);

		int i = 0;
		for (Object object : componentes) {
			Set vertices = (Set) object;

			if (vertices.size() > 1) {
				dtm.addRow(new Object[] { vertices, String.valueOf(i), String.valueOf(vertices.size()) });
			}
			i++;
		}

		// limpia las tablas de datos
		limpiarModelos();

		// Habilita la selección de componentes
		tbComponentes.setEnabled(true);
		btnSeleccionar.setEnabled(true);
	}

	private void cargarComision(int indice) {
		// // Carga la tabla de comisiones
		DefaultTableModel dtmComision = (DefaultTableModel) tbComision.getModel();
		limpiarModelo(dtmComision);

		List<Ejecucion> ejecuciones = generacionCargada.getEjecuciones();
		Ejecucion ejecucion = ejecuciones.get(indice);

		List<Investigador> comision = ejecucion.getComision();

		for (Investigador inv : comision) {
			dtmComision.addRow(new String[] { String.valueOf(comision.indexOf(inv)), inv.toString(),
					String.valueOf(redSocial.getDegree(inv)), String.valueOf(redSocial.getBetweeness(inv)),
					String.valueOf(redSocial.getCloseness(inv)), String.valueOf(redSocial.getEigenVector(inv)) });
		}
	}

	/**
	 * Elimina las filas de una tabla
	 * 
	 * @param dtm
	 *            Modelo de la tabla
	 */
	private void limpiarModelo(DefaultTableModel dtm) {
		int j = dtm.getRowCount();
		for (int i = j - 1; i >= 0; i--) {
			dtm.removeRow(i);
		}
	}
}
