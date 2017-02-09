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

public class Heroi_equipa_armaDAO {

	private static Heroi_equipa_armaDAO INSTANCE;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private final String INSERT = "INSERT INTO heroi_equipa_arma VALUES(?,?,?)";
	
	private final String SELECTMAO1 = "call selectArmasEquipadas(?, ?)";
	
	private final String SELECTMAO2 = "call selectArmasEquipadas(?, ?)";
	
	private final String REMOVE = "DELETE FROM Heroi_equipa_arma WHERE Heroi_idHeroi = ? and mao = ?";
	
	private Heroi_equipa_armaDAO() {}
	
	public static synchronized Heroi_equipa_armaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Heroi_equipa_armaDAO();
		}
		return INSTANCE;
	}
	
	public boolean cadastrar(int idArma, int idHeroi, int maoArma) {
		boolean cadastrou = false;
		
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, idArma);
			stmt.setInt(2, idHeroi);
			stmt.setInt(3, maoArma);
			if (stmt.executeUpdate() == 1)
				cadastrou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao cadastrar uma arma no heroi!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return cadastrou;
	}
	
	public List<Arma> listarMao1(int id) {
		List<Arma> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTMAO1);
			stmt.setInt(1, id);
			stmt.setInt(2, 1);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Arma arma = new Arma();
				arma.setMao(rs.getInt("mao"));
				arma.setAtaque(rs.getInt("atk"));
				arma.setDefesa(rs.getInt("def"));
				arma.setImgArma(rs.getBlob("imgArma"));
				lista.add(arma);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao listar uma arma do heroi!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return lista;
	}
	
	public List<Arma> listarMao2(int id) {
		List<Arma> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTMAO2);
			stmt.setInt(1, id);
			stmt.setInt(2, 2);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Arma arma = new Arma();
				arma.setMao(rs.getInt("mao"));
				arma.setAtaque(rs.getInt("atk"));
				arma.setDefesa(rs.getInt("def"));
				arma.setImgArma(rs.getBlob("imgArma"));
				lista.add(arma);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao listar uma arma do heroi!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return lista;
	}
	
	public void remover(int idHeroi, int mao) {
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVE);
			stmt.setInt(1, idHeroi);
			stmt.setInt(2, mao);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu tentar deletar uma arma!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
	}
}
