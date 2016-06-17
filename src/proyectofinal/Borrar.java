/*
 * Aquí definimos el paquete Y importamos la librerías necesarias.
 */
package proyectofinal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Esta es la clase que se encarga de mostrar un formulario para Borrar clientes
 * de la base de datos.
 *
 * @author Luis Ramos Medina
 * @version 2.0
 */
public class Borrar extends javax.swing.JFrame {

    /**
     * Este método es el encargado de la posición de la ventana y el icono de
     * esta.
     */
    public Borrar() {
        LocalizacionMasIcono();
    }

    public void LocalizacionMasIcono() {
        initComponents();
        //Situamos el JFrame en el centro de la Pantalla:
        this.setLocationRelativeTo(null);
        //Seleccionamos el icono de la aplicación:
        setIconImage(new ImageIcon(getClass().getResource("../Iconos/borrar.png")).getImage());
    }

    /**
     * Este método es el encargado de visualizar los clientes en una tabla
     * realizando una consulta de la base de datos.
     */
    public void visualizarClientes() {
        /**
         * Llamamos la la conexión.
         *
         * @returns conexion
         */
        //Cargamos la Conexión:
        Conexion conexion = new Conexion();
        conexion.crearConexion();
        Connection con = conexion.getConection();
        /**
         * Creamos la sentencia SQL y la ejecutamos:
         */
        //Preparamos los valores para la jTable1:
        DefaultTableModel model;
        String[] clientes = {"ID", "DNI", "Nombre", "Apellidos", "Contraseña", "Teléfono",
            "Email", "Tipo Incidencia", "Estado Incidencia", "Descripción Incidencia", "Fecha Entrada", "Fecha Salida"};
        model = new DefaultTableModel(null, clientes);
        Statement st;
        ResultSet rs;
        //Creamos la sentencia SQL: 
        String sql = "SELECT * FROM clientes";
        //Ejecutamos la sentencia SQL:
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            //Mostramos los resultados en la tabla del Jframe:
            String fila[] = new String[12];
            /**
             * Rellenamos la tabla con sus correspondientes datos extraido de la
             * base de datos.
             */

            //Mediante el bucle While rellenamos las Filas de la tabla:
            while (rs.next()) {
                fila[0] = rs.getString("idcliente");
                fila[1] = rs.getString("cif");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("contrasena");
                fila[5] = rs.getString("telefono");
                fila[6] = rs.getString("email");
                fila[7] = rs.getString("tipo_incidencia");
                fila[8] = rs.getString("estado_incidencia");
                fila[9] = rs.getString("descripcion_incidencia");
                fila[10] = rs.getString("fecha_entrada");
                fila[11] = rs.getString("fecha_salida");

                model.addRow(fila);

            }
            jTable1.setModel(model);
            /**
             * Controlamos que se muestran o no los datos a través del bloque
             * Try catch y con mensajes para notificar de los errores.
             */

            JOptionPane.showMessageDialog(null, "Datos mostrados de la BD con éxito.");

            //Cerramos la conexión:
            con.close();
            st.close();

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo al mostrar datos a la Base de Datos.");
        }
    }

    /**
     * Este método busca un cliente en la BD y lo muestra en la tabla a través
     * de su nombre.
     */
    public void buscarCliente() {
        /**
         * Llamamos la la conexión.
         *
         * @returns conexion
         */
        //Cargamos la Conexión:
        Conexion conexion = new Conexion();
        conexion.crearConexion();
        Connection con = conexion.getConection();
        /**
         * Preparamos los datos mostrar: Y preparamos la sentencia SQL Dentro de
         * un bloque Try Catch para controlar los errores.
         */
        /**
         * Variable que va a recoger el NOMBRE Y preparamos los valors a poner
         * en a Tabla.
         */
        String NOMBRE = jTextField1.getText();
        DefaultTableModel model;

        String[] clientes = {"ID", "DNI", "Nombre", "Apellidos", "Contraseña", "Teléfono",
            "Email", "Tipo Incidencia", "Estado Incidencia", "Descripción Incidencia", "Fecha Entrada", "Fecha Salida"};
        model = new DefaultTableModel(null, clientes);
        try {
            //Consulta para recoger valores:
            String sql = "SELECT * FROM clientes WHERE nombre='" + NOMBRE + "'";

            Statement st;
            ResultSet rs;
            //Ejecutamos la sentencia SQL:
            st = con.createStatement();
            rs = st.executeQuery(sql);
            /**
             * Rellenamos la tabla con sus correspondientes datos extraido de la
             * base de datos.
             */
            //Mediante este bucle while recogemos los valores de la BD:
            String fila[] = new String[12];

            while (rs.next()) {
                fila[0] = rs.getString("idcliente");
                fila[1] = rs.getString("cif");
                fila[2] = rs.getString("nombre");
                fila[3] = rs.getString("apellido");
                fila[4] = rs.getString("contrasena");
                fila[5] = rs.getString("telefono");
                fila[6] = rs.getString("email");
                fila[7] = rs.getString("tipo_incidencia");
                fila[8] = rs.getString("estado_incidencia");
                fila[9] = rs.getString("descripcion_incidencia");
                fila[10] = rs.getString("fecha_entrada");
                fila[11] = rs.getString("fecha_salida");

                model.addRow(fila);
            }
            /**
             * Controlamos que se muestran o no los datos a través del bloque
             * Try catch y con mensajes para notificar de los errores.
             */
            jTable1.setModel(model);
            JOptionPane.showMessageDialog(null, "Cliente encontrado con éxito.");

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo al encontrar el Cliente.");
        }
    }

    /**
     * Este Método borra el cliente seleccionado en la tabla de la base de
     * datos.
     */
    public void borrarCliente() {
        /**
         * Llamamos la la conexión.
         *
         * @returns conexion
         */
        //Cargamos la Conexión:
        Conexion conexion = new Conexion();
        conexion.crearConexion();
        Connection con = conexion.getConection();

        /**
         * Ahora preparamos la consulta y que borre el cliente de la fila
         * seleccionada.
         */
        try {
            int fila = jTable1.getSelectedRow();
            //Elaboramos la consulta:
            String sql = "DELETE FROM clientes WHERE idcliente=" + jTable1.getValueAt(fila, 0);
            //Y ejecutamos la consulta:
            Statement sent = con.createStatement();
            /**
             * Controlamos que se borran o no los datos a través del bloque Try
             * catch y con mensajes para notificar de los errores.
             */
            int n = sent.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Datos eliminados con éxito,vuelva visualizar para ver los cambios.");

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo al borrar el Cliente.");
        }
    }

    /**
     * En estos métodos tenemos lo que hará cada botón o campo de la ventana
     * Borrar Cliente.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Clients Administrator"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Visualizar Clientes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Buscar Cliente por Nombre:");

        jLabel3.setText("Nombre:");

        jButton3.setText("Buscar Cliente");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("BORRAR CLIENTE:");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/BorrarIco.png"))); // NOI18N

        jButton4.setText("Borrar Cliente");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1))
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(137, 137, 137))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(270, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(378, 378, 378))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(349, 349, 349))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jMenu1.setText("Inicio");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo.gif"))); // NOI18N
        jMenuItem1.setText("Volver a Inicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/application-exit.png"))); // NOI18N
        jMenuItem2.setText("Cerrar Aplicación");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help.png"))); // NOI18N
        jMenuItem3.setText("Abrir Gestor de Ayuda");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /**
         * Botón visualizar clientes que llama al método visualizarClientes();
         */
        visualizarClientes();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        /**
         * Botón salir:
         */
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /**
         * Botón buscar clientes que llama al método buscarCliente();
         */
        buscarCliente();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        /**
         * Botón borrar clientes que llama al método borrarCliente();
         */
        borrarCliente();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        /**
         * Botón salir
         */
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        /**
         * Botón cerrar aplicación:
         */
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        /**
         * Botón que abre la ventana del gestor de ayuda de Borrar:
         */
        GestorBorrar m = new GestorBorrar();
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
