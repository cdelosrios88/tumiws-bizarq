package pe.com.tumi.report.cnx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CnxOrcl {
	private String strDriver = "oracle.jdbc.driver.OracleDriver";
	private String strCadenaConexion = "jdbc:oracle:thin:@192.168.100.14:1521:DBTUMI";
	private String strUsuario = "socioqc_201";
	private String strClave = "qc201";
	
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
