package br.com.inatel.ec206.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.controller.Conexao;
import br.com.inatel.ec206.model.entity.Arma;

public class ArmaDAO {

	private static ArmaDAO INSTANCE;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private final String INSERT = "INSERT INTO arma(nomeArma, pesoArma, imgArma, atk, def) VALUES(?,?,?,?,?)";
	
	private final String SELECTALL = "SELECT * FROM arma";

	private ArmaDAO() {}
	
	public static synchronized ArmaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ArmaDAO();
		}
		return INSTANCE;
	}
	
	public boolean cadastrar(Arma arma) {
		boolean cadastrou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setString(1, arma.getNomeArma());
			stmt.setInt(2, arma.getPesoArma());
			stmt.setBlob(3, arma.getImgByteArma());
			stmt.setInt(4, arma.getAtaque());
			stmt.setInt(5, arma.getDefesa());
			if (stmt.executeUpdate() == 1)
				cadastrou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar cadastrar a arma!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return cadastrou;
	}
	
	public List<Arma> getTodas() {
		List<Arma> lista = null;
		Arma weapon = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTALL);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				weapon = new Arma();
				weapon.setIdArma(rs.getInt("idArma"));				
				weapon.setNomeArma(rs.getString("nomeArma"));
				weapon.setImgArma(rs.getBlob("imgArma"));
				weapon.setAtaque(rs.getInt("atk"));
				weapon.setDefesa(rs.getInt("def"));
				lista.add(weapon);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar os cadastros dos heróis!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return lista;
	}
}
