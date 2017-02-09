package br.com.inatel.ec206.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.controller.Conexao;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginDAO.
 */
public class LoginDAO {

	private static LoginDAO INSTANCE = null;

	private Connection conn;
	
	private PreparedStatement stmt;
	
	private ResultSet rs;

	private final String SELECTUSER = "SELECT idPolicia, nomePolicia, usuario, senha FROM policia INNER JOIN login ON policia.idPolicia=login.Policia_idPolicia WHERE usuario = (?)";
	
	private final String INSERT = "INSERT INTO login(Policia_idPolicia, usuario, senha) VALUES(?,?,?)";
	
	private final String UPDATE = "UPDATE login SET usuario = ?, senha = ? WHERE Policia_idPolicia = ?";

	private LoginDAO() { }
	
	public static LoginDAO getInstance() {
		if (INSTANCE == null) {
			inicializaInstancia();
		}
		return INSTANCE;
	}
	
	private static synchronized void inicializaInstancia() {
		if (INSTANCE == null) {
			INSTANCE = new LoginDAO();
		}
	}

	/**
	 * Validar dados.
	 *
	 * @param usuario the usuario
	 * @param senha the senha
	 * @return the string
	 */
	public int validarDados(String usuario, String senha) {
		int idPolicia = 0;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTUSER);
			stmt.setString(1, usuario);
			rs = stmt.executeQuery();
			while (rs.next()) {
				String verUser = rs.getString("usuario");
				String verSenha = rs.getString("senha");
				if (verUser.equals(usuario) && verSenha.equals(senha)) {
					idPolicia = rs.getInt("idPolicia");
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao validar o usuário!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return idPolicia;
	}
	
	public boolean cadastrarLogin(int idPolicia, String usuario, String senha) {
		boolean cadastrou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, idPolicia);
			stmt.setString(2, usuario);
			stmt.setString(3, senha);
			stmt.executeUpdate();
			cadastrou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao cadastrar o usuário!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return cadastrou;
	}
	
	public boolean atualizarLogin(int idPolicia, String usuario, String senha) {
		boolean atualizou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(3, idPolicia);
			stmt.setString(1, usuario);
			stmt.setString(2, senha);
			if (stmt.executeUpdate() == 1)
				atualizou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao cadastrar o usuário!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return atualizou;
	}

}