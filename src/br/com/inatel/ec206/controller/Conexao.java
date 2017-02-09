package br.com.inatel.ec206.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.inatel.ec206.model.Constants;

public class Conexao {

	 public static Connection conectar() throws Exception
	 {
		Connection connection = null;
		
		Class.forName(Constants.JDBC_DRIVER);
		connection = DriverManager.getConnection(
			 		Constants.JDBC_URL + Constants.JDBC_DATABASE,
					Constants.JDBC_USER,
					Constants.JDBC_PASSWORD);
 
        return connection;
	 }
 
	 public static void desconectar(Connection conn, PreparedStatement stmt, ResultSet rs) {
		 try {
			
		 	 if (rs != null) rs.close();
			 if (stmt != null) stmt.close();
			 if (conn != null) conn.close();
	
		 } catch (Exception e) {
			 System.out.println("Erro ao desconectar: " + e.getClass());
			 e.printStackTrace();
		 }
	 }
}
