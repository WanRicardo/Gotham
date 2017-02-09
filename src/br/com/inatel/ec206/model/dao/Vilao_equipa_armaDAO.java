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

public class Vilao_equipa_armaDAO {

	private static Vilao_equipa_armaDAO INSTANCE;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private final String INSERT = "INSERT INTO vilao_equipa_arma VALUES(?,?,?)";
	
	private final String SELECTMAO1 = "call selectArmasEquipadasVilao(?, ?)";
	
	private final String SELECTMAO2 = "call selectArmasEquipadasVilao(?, ?)";
	
	private final String REMOVE = "DELETE FROM vilao_equipa_arma WHERE Vilao_idVilao = ? and mao = ?";
	
	private Vilao_equipa_armaDAO() {}
	
	public static synchronized Vilao_equipa_armaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Vilao_equipa_armaDAO();
		}
		return INSTANCE;
	}
	
	public boolean cadastrar(int idArma, int idVilao, int maoArma) {
		boolean cadastrou = false;
		
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, idArma);
			stmt.setInt(2, idVilao);
			stmt.setInt(3, maoArma);
			if (stmt.executeUpdate() == 1)
				cadastrou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao cadastrar uma arma no vilao!");
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
					"Um erro ocorreu ao listar uma arma do vilao!");
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
					"Um erro ocorreu ao listar uma arma do vilao!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return lista;
	}
	
	public void remover(int idVilao, int mao) {
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVE);
			stmt.setInt(1, idVilao);
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
