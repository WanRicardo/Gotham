package br.com.inatel.ec206.model;

public class Constants {

	//configuraçao do banco de dados (AcessoDB)
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
	public static final String JDBC_DATABASE = "batima";
	public static final String JDBC_USER = "root";
	public static final String JDBC_PASSWORD = "root";
	
	//constantes usadas em botoes (Policia)
	public static final String POLICIA_CREATE = "policia.create";
	public static final String POLICIA_RETRIEVE = "policia.retrieve";
	public static final String POLICIA_UPDATE = "policia.update";
	public static final String POLICIA_DELETE = "policia.delete";
	public static final String POLICIA_RETRIEVE_ALL = "policia.retrieveAll";
	public static final String POLICIA_LOGOUT = "policia.sair";

	//constantes usadas em botoes (Vilao)
	public static final String VILAO_CREATE = "vilao.create";
	public static final String VILAO_RETRIEVE = "vilao.retrieve";
	public static final String VILAO_UPDATE = "vilao.update";
	public static final String VILAO_DELETE = "vilao.delete";
	public static final String VILAO_RETRIEVE_WANTED = "vilao.retrieveWanted";
	
	public static final String DEFAULT_PATH = "C:/Users/usuario/Documents/Inatel/2015 - 2/EC206 - Engenharia de Software II/Lab/ProjetoBátima/Gotham/Images Gotham/";
	public static final String DEFAULT_PATH_RELATORIO = "C:/Users/usuario/Documents/Inatel/2015 - 2/EC206 - Engenharia de Software II/Lab/ProjetoBátima/Gotham/Relatorios/";
	
}
