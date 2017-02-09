package br.com.inatel.ec206.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.controller.Conexao;
import br.com.inatel.ec206.model.entity.Heroi;

public class HeroiDAO {

	private static HeroiDAO INSTANCE;
	
	private HeroiDAO() {}
	
	public static synchronized HeroiDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HeroiDAO();
		}
		return INSTANCE;
	}
	
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private final String SELECTALL = "SELECT * FROM heroi";
	
	private final String SELECTNOME = "SELECT * FROM heroi WHERE nomeHeroi = ?";
	
	private final String REMOVE = "DELETE FROM heroi WHERE nomeHeroi = ?";
	
	private final String SELECTID = "SELECT * FROM heroi WHERE idHeroi = ?";
	
	private final String INSERT = "INSERT INTO heroi (Policia_idPolicia, nomeHeroi, pesoHeroi, alturaHeroi, fotoHeroi,"
			+ " atk, def, vida, statusHeroi, estadoFisico, tendenciaMoral) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	
	private final String UPDATE = "UPDATE heroi SET nomeHeroi = ?, pesoHeroi = ?, alturaHeroi = ?, fotoHeroi = ?, statusHeroi = ?,"
			+ " estadoFisico = ?, tendenciaMoral = ? WHERE idHeroi = ?";
	
	private final String REMOVEPELOID = "DELETE FROM heroi WHERE idHeroi = ?";
	
	private final String SELECTNOMEHEROI = "SELECT nomeHeroi FROM heroi WHERE Policia_idPolicia = ?";
	
	private final String PROXID = "SELECT proximoCodigoHeroi()";
	
	public boolean cadastrar(Heroi heroi) {
		boolean salvou = false;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, heroi.getIdPolicia());
			stmt.setString(2, heroi.getNome());
			stmt.setInt(3, heroi.getPeso());
			stmt.setInt(4, heroi.getAltura());
			stmt.setBlob(5, heroi.getFotoByteHeroi());
			stmt.setInt(6, heroi.getAtk());
			stmt.setInt(7, heroi.getDef());
			stmt.setInt(8, heroi.getVida());
			stmt.setString(9, heroi.getStatusHeroi());
			stmt.setString(10, heroi.getEstadoFisico());
			stmt.setString(11, heroi.getTendenciaMoral());
			if (stmt.executeUpdate() == 1)
				salvou = true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar executar o cadastro do heroi!");
			e.printStackTrace();
			salvou = false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return salvou;

	}
	
	public List<Heroi> getTodos() {
		List<Heroi> lista = null;
		Heroi hero = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTALL);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				hero = new Heroi();
				hero.setId(rs.getInt("idHeroi"));				
				hero.setNome(rs.getString("nomeHeroi"));
				hero.setPeso(rs.getInt("pesoHeroi"));
				hero.setAltura(rs.getInt("alturaHeroi"));
				hero.setStatusHeroi(rs.getString("statusHeroi"));
				hero.setEstadoFisico(rs.getString("estadoFisico"));
				hero.setTendenciaMoral(rs.getString("tendenciaMoral"));
				lista.add(hero);
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
	
	public List<Heroi> getPeloNome(String nomeHeroi) {
		List<Heroi> lista = null;
		
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOME);
			stmt.setString(1, nomeHeroi);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Heroi hero = new Heroi();
				hero.setId(rs.getInt("idHeroi"));
				hero.setNome(rs.getString("nomeHeroi"));
				hero.setPeso(rs.getInt("pesoHeroi"));
				hero.setAltura(rs.getInt("alturaHeroi"));
				hero.setFotoHeroi(rs.getBlob("fotoHeroi"));
				hero.setAtk(rs.getInt("atk"));
				hero.setDef(rs.getInt("def"));
				hero.setVida(rs.getInt("vida"));
				hero.setStatusHeroi(rs.getString("statusHeroi"));
				hero.setEstadoFisico(rs.getString("estadoFisico"));
				hero.setTendenciaMoral(rs.getString("tendenciaMoral"));
				lista.add(hero);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o Heroi pelo nome!");
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
	
	public boolean removerHeroi(String nomeHeroi) {
		boolean feito = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVE);
			stmt.setString(1, nomeHeroi);
			if (stmt.executeUpdate() == 1)
				feito = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro heroi!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return feito;
	}
	
	public boolean removerHeroiPeloId(int idHeroi) {
		boolean feito = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVEPELOID);
			stmt.setInt(1, idHeroi);
			if (stmt.executeUpdate() == 1)
				feito = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro heroi!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return feito;
	}
	
	public List<Heroi> getPeloId(int idHeroi) {
		List<Heroi> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTID);
			stmt.setInt(1, idHeroi);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			if (rs.next()) {
				Heroi hero = new Heroi();
				hero.setId(rs.getInt("idHeroi"));
				hero.setNome(rs.getString("nomeHeroi"));
				hero.setPeso(rs.getInt("pesoHeroi"));
				hero.setAltura(rs.getInt("alturaHeroi"));
				hero.setFotoHeroi(rs.getBlob("fotoHeroi"));
				hero.setAtk(rs.getInt("atk"));
				hero.setDef(rs.getInt("def"));
				hero.setVida(rs.getInt("vida"));
				hero.setStatusHeroi(rs.getString("statusHeroi"));
				hero.setEstadoFisico(rs.getString("estadoFisico"));
				hero.setTendenciaMoral(rs.getString("tendenciaMoral"));
				lista.add(hero);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o heroi!");
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
	
	public boolean editarHeroi(Heroi heroi) {
		boolean editou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(8, heroi.getId());
			stmt.setString(1, heroi.getNome());
			stmt.setInt(2, heroi.getPeso());
			stmt.setInt(3, heroi.getAltura());
			stmt.setBlob(4, heroi.getFotoByteHeroi());
			stmt.setString(5, heroi.getStatusHeroi());
			stmt.setString(6, heroi.getEstadoFisico());
			stmt.setString(7, heroi.getTendenciaMoral());
			if (stmt.executeUpdate() == 1)
				editou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar editar o cadastro heroi!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return editou;
	}
	
	public List<Heroi> getListaHerois(int idPolicial) {
		List<Heroi> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOMEHEROI);
			stmt.setInt(1, idPolicial);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Heroi heroi = new Heroi();
				heroi.setNome(rs.getString("nomeHeroi"));
				lista.add(heroi);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar pegar o numero de prisões do policial!");
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
	
	public List<Heroi> getNomeHerois() {
		List<Heroi> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTALL);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Heroi heroi = new Heroi();
				heroi.setId(rs.getInt("idHeroi"));				
				heroi.setNome(rs.getString("nomeHeroi"));
				lista.add(heroi);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar pegar o numero de prisões do policial!");
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
	
	public Integer getProxId() {
		Integer proximoId = 0;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(PROXID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				proximoId = (rs.getInt("proximoCodigoHeroi()"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar pegar o proximo id heroi!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return proximoId;
	}
}
