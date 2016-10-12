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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.edu.isistan.genCom.redSocial.dao.HibernateUtil;

public class Conexion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tfUserName;
	private JTextField tfPassword;
	private JTextField tfURL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Conexion dialog = new Conexion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Conexion() {
		setTitle("Configuración de la base de datos");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 36, 330, 140);
		contentPanel.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblBaseDeDatos = new JLabel("url");
		GridBagConstraints gbc_lblBaseDeDatos = new GridBagConstraints();
		gbc_lblBaseDeDatos.anchor = GridBagConstraints.EAST;
		gbc_lblBaseDeDatos.insets = new Insets(0, 0, 5, 5);
		gbc_lblBaseDeDatos.gridx = 0;
		gbc_lblBaseDeDatos.gridy = 0;
		panel.add(lblBaseDeDatos, gbc_lblBaseDeDatos);
		
		tfURL = new JTextField();
		tfURL.setColumns(10);
		GridBagConstraints gbc_tfURL = new GridBagConstraints();
		gbc_tfURL.insets = new Insets(0, 0, 5, 0);
		gbc_tfURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfURL.gridx = 1;
		gbc_tfURL.gridy = 0;
		panel.add(tfURL, gbc_tfURL);
		
		JLabel lblUsername = new JLabel("username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		panel.add(lblUsername, gbc_lblUsername);
		
		tfUserName = new JTextField();
		tfUserName.setColumns(10);
		GridBagConstraints gbc_tfUserName = new GridBagConstraints();
		gbc_tfUserName.insets = new Insets(0, 0, 5, 0);
		gbc_tfUserName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfUserName.gridx = 1;
		gbc_tfUserName.gridy = 1;
		panel.add(tfUserName, gbc_tfUserName);
		
		JLabel lblPassword = new JLabel("password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 0, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		panel.add(lblPassword, gbc_lblPassword);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		GridBagConstraints gbc_tfPassword = new GridBagConstraints();
		gbc_tfPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPassword.gridx = 1;
		gbc_tfPassword.gridy = 2;
		panel.add(tfPassword, gbc_tfPassword);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							guardarConexion();
							Conexion.this.dispose();
						} catch (Exception e1) {
							// TODO generar un mensaje de error de conexión
							JDialog dialog = new JDialog(Conexion.this, true);
							dialog.setVisible(true);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Conexion.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}


	/**
	 * Guarda los datos de la conexión
	 * @return
	 */
	protected void guardarConexion() throws Exception{
		// Guardar los datos de la conexión
		String url = tfURL.getText();
		String username = tfUserName.getText();
		String password = tfPassword.getText();

		HibernateUtil.setConfig(url, username, password);
//		TODO guardar los datos de conexión en el archivo de configuración
		
	}
	
	/**
	 * Carga los datos de la conexión
	 */
	public void cargarDatos(){
		Map<String, String> datos = HibernateUtil.getDatosDeConexion();
		
		// Guardar los datos de la conexión
		tfURL.setText(datos.get("url"));
		tfUserName.setText(datos.get("username"));
		tfPassword.setText(datos.get("password"));
	}
}
