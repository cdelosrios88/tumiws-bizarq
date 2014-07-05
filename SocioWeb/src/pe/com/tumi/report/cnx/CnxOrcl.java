package pe.com.tumi.report.cnx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pe.com.tumi.common.util.Constante;

public class CnxOrcl {
	private String strDriver = "oracle.jdbc.driver.OracleDriver";
	private String strCadenaConexion = Constante.REPORT_CADENA_CONEXION;
	private String strUsuario = Constante.REPORT_CADENA_USUARIO;
	private String strClave = Constante.REPORT_CADENA_CLAVE;
	
	public Connection getConnection() throws ClassNotFoundException{
		Connection conexion = null;
		
		try {
			Class.forName(strDriver).newInstance();
			conexion = DriverManager.getConnection (strCadenaConexion,strUsuario,strClave);
			
		} catch(SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.toString());
            
            conexion = null;
            
        }catch (Exception e) {
			e.printStackTrace(); //Sirve para saber q excepción se dispara
            System.out.println(e.toString());
            
            conexion = null;
		}
		return conexion;	
	}
}
