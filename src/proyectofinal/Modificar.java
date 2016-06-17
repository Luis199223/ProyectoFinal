/*
 * Aquí definimos el paquete Y importamos la librerías necesarias.
 */
package proyectofinal;

import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

/**
 * Esta es la clase que se encarga de mostrar un formulario para modificar
 * clientes de la base de datos.
 *
 * @author Luis Ramos Medina
 * @version
 */
public class Modificar extends javax.swing.JFrame {

    /**
     * Este método es el encargado de la posición de la ventana y el icono de
     * esta.
     */
    public Modificar() {
        LocalizacionMasIcono();
    }

    public void LocalizacionMasIcono() {
        initComponents();
        //Situamos el JFrame en el centro de la Pantalla:
        this.setLocationRelativeTo(null);
        //Seleccionamos el icono de la aplicación:
        setIconImage(new ImageIcon(getClass().getResource("../Iconos/Modificar.png")).getImage());
    }

    /**
     * Este método es el encargado de visualizar los clientes en una tabla
     * realizando una consulta de la base de datos.
     */
    public void mostrarClientes() {
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
         * Generamos la consulta SQL recogiendo los valores a insertar de las
         * variables anteriores y la ejecutamos.
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
     * Este método realiza la modificación de los clientes registrados en la
     * base datos.
     */
    public void modificarClientes() {
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
         * Preparamos los datos a recoger: Y preparamos la sentencia SQL Dentro
         * de un bloque Try Catch para controlar los errores.
         */
        try {
            //Actualización SQL:
            String sql = "UPDATE clientes SET cif=?, nombre=?, apellido=?, contrasena=?, telefono=?, email=?, "
                    + " tipo_incidencia=?, estado_incidencia=?, "
                    + "descripcion_incidencia=?, fecha_entrada=?, fecha_salida=? WHERE idcliente=?";

            //Ahora creamos una variable que obtenga el valor seleccionado
            int fila = jTable1.getSelectedRow();
            String dao = (String) jTable1.getValueAt(fila, 0);
            PreparedStatement ps = con.prepareCall(sql);
            /**
             * Recogemos todos los valos a cambiar desde la ventana, para
             * posteriormente introducirlos en la BD.
             */
            //Ahora recogemos cada valor a cambiar:
            ps.setString(1, jTextField1.getText());
            ps.setString(2, jTextField2.getText());
            ps.setString(3, jTextField3.getText());
            ps.setString(4, jTextField4.getText());
            ps.setString(5, jTextField5.getText());
            ps.setString(6, jTextField6.getText());
            ps.setString(7, (String) jComboBox1.getSelectedItem());
            ps.setString(8, (String) jComboBox2.getSelectedItem());
            ps.setString(9, jTextArea1.getText());
            ps.setString(10, jTextField7.getText());
            ps.setString(11, jTextField8.getText());
            /**
             * Controlamos que se modifican o no los datos a través del bloque
             * Try catch y con mensajes para notificar de los errores.
             */
            //Ahora mostramos el cambio en la tabla:
            ps.setString(12, dao);
            //Ejecutamos la actualización:
            int n = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos actualizados de la BD con éxito,"
                    + "vuelva a mostrar los Clientes para ver los resultados.");
        } catch (SQLException e) {
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo al actualizar datos a la Base de Datos.");
        }
    }

    /**
     * Este método rellena le formularío de la ventana con los datos del cliente
     * que se ha introducido su ID.
     */
    public void rellenarFormulario() {
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
        //Variable que va a recoger el ID:
        String ID = jTextField9.getText();

        try {
            //Consulta para recoger valores:
            String sql = "SELECT * FROM clientes WHERE idcliente=" + ID + "";

            Statement st;
            ResultSet rs;
            //Ejecutamos la sentencia SQL:
            st = con.createStatement();
            rs = st.executeQuery(sql);

            //Mediante este bucle while recogemos los valores de la BD:
            String form[] = new String[11];
            /**
             * Recogemos los valores de la Base de datos correspondientes en
             * esta.
             */
            while (rs.next()) {

                form[0] = rs.getString("cif");
                form[1] = rs.getString("nombre");
                form[2] = rs.getString("apellido");
                form[3] = rs.getString("contrasena");
                form[4] = rs.getString("telefono");
                form[5] = rs.getString("email");
                form[6] = rs.getString("tipo_incidencia");
                form[7] = rs.getString("estado_incidencia");
                form[8] = rs.getString("descripcion_incidencia");
                form[9] = rs.getString("fecha_entrada");
                form[10] = rs.getString("fecha_salida");

                /**
                 * Ahora rellenamos el Formulario con los datos modificados en
                 * el formulario.
                 */
                //Ahora rellenamos el formulario:
                this.jTextField1.setText(form[0]);
                this.jTextField2.setText(form[1]);
                this.jTextField3.setText(form[2]);
                this.jTextField4.setText(form[3]);
                this.jTextField5.setText(form[4]);
                this.jTextField6.setText(form[5]);
                this.jComboBox1.setSelectedItem(form[6]);
                this.jComboBox2.setSelectedItem(form[7]);
                this.jTextArea1.setText(form[8]);
                this.jTextField7.setText(form[9]);
                this.jTextField8.setText(form[10]);

                JOptionPane.showMessageDialog(null, "Formulario rellenado con éxito.");
            }

        } catch (SQLException e) {
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Fallo al rellenar el formulario.");
        }
    }

    /**
     * Este método vacia los campos del formulario de la ventana insertar.
     */
    public void vaciarFormulario() {

        //Le asignamos valores nulos:
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextField4.setText("");
        this.jTextField5.setText("");
        this.jTextField6.setText("");
        this.jComboBox1.setSelectedItem("Hardware");
        this.jComboBox2.setSelectedItem("Solucionada");
        this.jTextArea1.setText("");
        this.jTextField7.setText("");
        this.jTextField8.setText("");
    }

    /**
     * En estos métodos tenemos lo que hará cada botón o campo de la ventana
     * Modificar Cliente.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Clients Administrator"));

        jLabel1.setText("MODIFICAR CLIENTE:");

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

        jButton1.setText("Mostrar Clientes");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modificar Cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setText("Modificar datos Cliente seleccionado:");

        jLabel3.setText("DNI:");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/IconoModificar.png"))); // NOI18N

        jLabel5.setText("Nombre:");

        jLabel6.setText("Apellidos:");

        jLabel7.setText("Contraseña:");

        jLabel8.setText("Teléfono:");

        jLabel9.setText("Email:");

        jLabel10.setText("Tipo de Incidencia:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hardware", "Software", "Red" }));

        jLabel11.setText("Estado de la Incidencia:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Solucionada", "NoSolucionada", "Pendiente" }));

        jLabel12.setText("Descripción de la Incidencia:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel13.setText("Fecha de Entrada:");

        jLabel14.setText("Fecha de Salida:");

        jLabel15.setText("AAAA-MM-DD HH:MM:SS");

        jButton3.setText("Salir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel16.setText("Introduzca ID:");

        jButton4.setText("Rellenar Formulario");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Vaciar Formulario");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(65, 65, 65))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(jTextField4)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel14))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                    .addComponent(jTextField7))))
                        .addGap(507, 507, 507))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(333, 333, 333))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(50, 50, 50)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jButton3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Inicio");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/undo.gif"))); // NOI18N
        jMenuItem2.setText("Volver a Inicio");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/application-exit.png"))); // NOI18N
        jMenuItem3.setText("Cerrar Aplicación");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help.png"))); // NOI18N
        jMenuItem1.setText("Abrir Gestor de Ayuda");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /**
         * Botón salir:
         */
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /**
         * Botón mostrar clientes que llama al método mostrarClientes();
         */
        mostrarClientes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        /**
         * Botón modificar clientes que llama al método modificarCliente();
         */
        modificarClientes();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        /**
         * Botón rellenar el formulario que llama al método
         * rellenarFormulario();
         */
        rellenarFormulario();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        /**
         * Botón vaciar el formulario que llama al método vaciarFormulario();
         */
        vaciarFormulario();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        /**
         * Botón cerar aplicación:
         */
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        /**
         * Botón volver a incio;
         */
        dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        /**
         * Botón que abre la ventana del gestor de ayuda de Modificar:
         */
        GestorModificar m = new GestorModificar();
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

}
