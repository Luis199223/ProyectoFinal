/**
 * Aquí definimos el paquete Y importamos la librerías necesarias.
 */
package proyectofinal;

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 * Clase Conexión
 *
 * Esta clase contiene lo la conexión a la base de datos MYSQL
 * clientes_informática, utilizando su dirección, Contraseña, usuario y el
 * driver JDBC.
 *
 * @author Luis Ramos Medina
 * @version 2.0
 *
 */
public class Conexion {

    /**
     * Aquí definimos los variables que recogerán los valores de
     * servidor,usuario,contraseña y driver.
     *
     */

    /**
     * URL del servidor MYSQL
     */
    private String servidor = "jdbc:mysql://localhost:3306/clientes_informatica";
    /**
     * Usuario
     */
    private String user = "root";
    /**
     * Contraseña
     */
    private String pass = "";
    /**
     * Driver JDBC
     */
    private String driver = "com.mysql.jdbc.Driver";
    private Connection conexion;

    /**
     * Este método crea la conexión a la base de datos.
     *
     */
    public void crearConexion() {
        try {
            Class.forName(getDriver());
            /**
             * Creamos la conexión y insertamos los valores de las variables.
             */
            conexion = DriverManager.getConnection(getServidor(), getUser(), getPass());
            JOptionPane.showMessageDialog(null, "Conectado a la BD con éxito.");
        } catch (ClassNotFoundException | SQLException e) {
            /**
             * Controlamos los errores de conexión con estre Catch en caso de
             * fallo de SQL.
             */
            String mensaje = e.getMessage();
            System.err.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Conexión  fallida a la Base de Datos.");
            System.exit(0);
        }
    }

    /**
     * Aquí tenemos los Getter y Setter de las variables principales.
     *
     * @return
     *
     */
    public Connection getConection() {
        return conexion;
    }

    /**
     * @return  recoge servidor.
     */
    public String getServidor() {
        return servidor;
    }

    /**
     * @param servidor te permite cambiar servidor.
     */
    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    /**
     * @return  recoge user.
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user  te permite cambiar user.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return recoge pass.
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass te permite cambiar pass.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return recoge driver.
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver te permtie cambiar driver.
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

}
