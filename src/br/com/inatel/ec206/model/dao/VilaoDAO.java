package br.com.inatel.ec206.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.controller.Conexao;
import br.com.inatel.ec206.model.entity.Vilao;

public class VilaoDAO {

	// Design Pattern SINGLETON
	// http://pt.wikipedia.org/wiki/Singleton

	// Atributo estático que conterá a instancia do singleton.
	private static VilaoDAO INSTANCE;

	// Atributos privados da classe
	private Connection conn;
	
	private PreparedStatement stmt;
	
	private ResultSet rs;

	private final String INSERT = "INSERT INTO vilao(Policia_idPolicia, nomeVilao, pesoVilao, alturaVilao, fotoVilao,"
			+ " atk, def, vida, statusVilao, estadoFisico) VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	private final String UPDATE = "UPDATE vilao SET Policia_idPolicia = ?, nomeVilao = ?, pesoVilao = ?, alturaVilao = ?, fotoVilao = ?, statusVilao = ?,"
			+ " estadoFisico = ? WHERE idVilao = ?";

	private final String REMOVE = "DELETE FROM vilao WHERE nomeVilao = ?";
	
	private final String REMOVEPRESO = "DELETE FROM vilao WHERE nomeVilao = ? AND statusVilao = 'Preso'";
	
	private final String REMOVEPELOID = "DELETE FROM vilao WHERE idVilao = ?";
	
	private final String REMOVEPRESOPELOID = "DELETE FROM vilao WHERE idVilao = ? AND statusVilao = 'Preso'";

	private final String SELECTALL = "SELECT * FROM vilao";

	private final String SELECTID = "SELECT * FROM vilao WHERE idVilao = ?";
	
	private final String SELECTNOME = "SELECT * FROM vilao WHERE nomeVilao = ?";
	
	private final String SELECTNOMEPRESO = "SELECT * FROM vilao WHERE nomeVilao = ? AND statusVilao = 'Preso'";
	
	private final String SELECTIDPRESO = "SELECT * FROM vilao WHERE idVilao = ? AND statusVilao = 'Preso'";
	
	private final String SELECTFORA = "SELECT * FROM vilao WHERE statusVilao != 'Preso'";
	
	private final String SELECTPRESOS = "SELECT * FROM vilao WHERE statusVilao = 'Preso'";
	
	private final String SELECTNUMPRIS = "SELECT count(Policia_idPolicia) FROM vilao WHERE Policia_idPolicia = ?";
	
	private final String SELECTNOMEPRIS = "SELECT nomeVilao FROM vilao WHERE Policia_idPolicia = ?";
	
	private final String PROXID = "SELECT proximoCodigoVilao()";

	// Construtor privado. Suprime o construtor público padrao.
	private VilaoDAO() {
	}

	// Método público estático de acesso único ao objeto!
	public static synchronized VilaoDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VilaoDAO();
		}
		return INSTANCE;
		// Retorna o a instância do objeto
	}

	public boolean cadastrar(Vilao vilao) {
		boolean salvou = false;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setNull(1, vilao.getIdPolicia());
			stmt.setString(2, vilao.getNome());
			stmt.setInt(3, vilao.getPeso());
			stmt.setInt(4, vilao.getAltura());
			stmt.setBlob(5, vilao.getFotoByteVilao());
			stmt.setInt(6, vilao.getAtaque());
			stmt.setInt(7, vilao.getDefesa());
			stmt.setInt(8, vilao.getVida());
			stmt.setString(9, vilao.getStatus());
			stmt.setString(10, vilao.getEstadoFisico());
			stmt.executeUpdate();
			salvou = true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar executar o cadastro!");
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
	
	public boolean cadastrarPresidiario(Vilao presidiario) {
		boolean salvou = false;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setInt(1, presidiario.getIdPolicia());
			stmt.setString(2, presidiario.getNome());
			stmt.setInt(3, presidiario.getPeso());
			stmt.setInt(4, presidiario.getAltura());
			stmt.setBlob(5, presidiario.getFotoByteVilao());
			stmt.setInt(6, presidiario.getAtaque());
			stmt.setInt(7, presidiario.getDefesa());
			stmt.setInt(8, presidiario.getVida());
			stmt.setString(9, presidiario.getStatus());
			stmt.setString(10, presidiario.getEstadoFisico());
			stmt.executeUpdate();
			salvou = true;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar executar o cadastro!");
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

	public boolean editar(Vilao vilao) {
		boolean editou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(8, vilao.getId());
			stmt.setNull(1, vilao.getIdPolicia());
			stmt.setString(2, vilao.getNome());
			stmt.setInt(3, vilao.getPeso());
			stmt.setInt(4, vilao.getAltura());
			stmt.setBlob(5, vilao.getFotoByteVilao());
			stmt.setString(6, vilao.getStatus());
			stmt.setString(7, vilao.getEstadoFisico());
			if (stmt.executeUpdate() == 1)
				editou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar editar o cadastro do vilao!");
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
	
	public boolean editarPresidiario(Vilao presidiario) {
		boolean editou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(8, presidiario.getId());
			stmt.setInt(1, presidiario.getIdPolicia());
			stmt.setString(2, presidiario.getNome());
			stmt.setInt(3, presidiario.getPeso());
			stmt.setInt(4, presidiario.getAltura());
			stmt.setBlob(5, presidiario.getFotoByteVilao());
			stmt.setString(6, presidiario.getStatus());
			stmt.setString(7, presidiario.getEstadoFisico());
			if (stmt.executeUpdate() == 1)
				editou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar editar o cadastro do vilao!");
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
	
	public boolean remover(String nomeVilao) {
		boolean removido = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVE);
			stmt.setString(1, nomeVilao);
			if(stmt.executeUpdate() == 1)
				removido = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro do vilão!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return removido;
	}
	
	public boolean removerPrisioneiro(String nomeVilao) {
		boolean removido = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVEPRESO);
			stmt.setString(1, nomeVilao);
			if(stmt.executeUpdate() == 1)
				removido = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro do prisioneiro!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return removido;
	}
	
	public boolean removerPeloId(int idVilao) {
		boolean removido = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVEPELOID);
			stmt.setInt(1, idVilao);
			if(stmt.executeUpdate() == 1)
				removido = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro do vilão!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return removido;
	}
	
	public boolean removerPrisioneiroPeloId(int idVilao) {
		boolean removido = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVEPRESOPELOID);
			stmt.setInt(1, idVilao);
			if(stmt.executeUpdate() == 1)
				removido = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro do prisioneiro!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return removido;
	}

	public List<Vilao> getTodos() {
		List<Vilao> lista = null;
		Vilao bandido = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTALL);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				bandido = new Vilao();
				bandido.setId(rs.getInt("idVilao"));
				bandido.setNome(rs.getString("nomeVilao"));
				bandido.setPeso(rs.getInt("pesoVilao"));
				bandido.setAltura(rs.getInt("alturaVilao"));
				bandido.setFotoVilao(rs.getBlob("fotoVilao"));
				bandido.setAtaque(rs.getInt("atk"));
				bandido.setDefesa(rs.getInt("def"));
				bandido.setVida(rs.getInt("vida"));
				bandido.setStatus(rs.getString("statusVilao"));
				bandido.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(bandido);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar os cadastros!");
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
	public List<Vilao> getTodosSoltos() {
		List<Vilao> lista = null;
		Vilao bandido = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTFORA);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				bandido = new Vilao();
				bandido.setId(rs.getInt("idVilao"));
				bandido.setNome(rs.getString("nomeVilao"));
				bandido.setPeso(rs.getInt("pesoVilao"));
				bandido.setAltura(rs.getInt("alturaVilao"));
				bandido.setFotoVilao(rs.getBlob("fotoVilao"));
				bandido.setAtaque(rs.getInt("atk"));
				bandido.setDefesa(rs.getInt("def"));
				bandido.setVida(rs.getInt("vida"));
				bandido.setStatus(rs.getString("statusVilao"));
				bandido.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(bandido);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar os cadastros!");
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
	
	public List<Vilao> getTodosPresos() {
		List<Vilao> lista = null;
		Vilao bandido = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTPRESOS);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				bandido = new Vilao();
				bandido.setId(rs.getInt("idVilao"));
				bandido.setNome(rs.getString("nomeVilao"));
				bandido.setPeso(rs.getInt("pesoVilao"));
				bandido.setAltura(rs.getInt("alturaVilao"));
				bandido.setFotoVilao(rs.getBlob("fotoVilao"));
				bandido.setAtaque(rs.getInt("atk"));
				bandido.setDefesa(rs.getInt("def"));
				bandido.setVida(rs.getInt("vida"));
				bandido.setStatus(rs.getString("statusVilao"));
				bandido.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(bandido);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar os cadastros do presidiários!");
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

	public List<Vilao> getPeloId(int id) {
		List<Vilao> lista = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Vilao vilao = new Vilao();
				vilao.setId(rs.getInt("idVilao"));
				vilao.setNome(rs.getString("nomeVilao"));
				vilao.setPeso(rs.getInt("pesoVilao"));
				vilao.setAltura(rs.getInt("alturaVilao"));
				vilao.setFotoVilao(rs.getBlob("fotoVilao"));
				vilao.setAtaque(rs.getInt("atk"));
				vilao.setDefesa(rs.getInt("def"));
				vilao.setVida(rs.getInt("vida"));
				vilao.setStatus(rs.getString("statusVilao"));
				vilao.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(vilao);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o vilão!");
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
	
	public List<Vilao> getPeloIdPrisioneiro(int id) {
		List<Vilao> lista = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTIDPRESO);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Vilao vilao = new Vilao();
				vilao.setId(rs.getInt("idVilao"));
				vilao.setNome(rs.getString("nomeVilao"));
				vilao.setPeso(rs.getInt("pesoVilao"));
				vilao.setAltura(rs.getInt("alturaVilao"));
				vilao.setFotoVilao(rs.getBlob("fotoVilao"));
				vilao.setAtaque(rs.getInt("atk"));
				vilao.setDefesa(rs.getInt("def"));
				vilao.setVida(rs.getInt("vida"));
				vilao.setStatus(rs.getString("statusVilao"));
				vilao.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(vilao);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o prisioneiro!");
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
	
	public List<Vilao> getPeloNome(String nomeVilao) {
		List<Vilao> lista = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOME);
			stmt.setString(1, nomeVilao);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Vilao vilao = new Vilao();
				vilao.setId(rs.getInt("idVilao"));
				vilao.setNome(rs.getString("nomeVilao"));
				vilao.setPeso(rs.getInt("pesoVilao"));
				vilao.setAltura(rs.getInt("alturaVilao"));
				vilao.setFotoVilao(rs.getBlob("fotoVilao"));
				vilao.setAtaque(rs.getInt("atk"));
				vilao.setDefesa(rs.getInt("def"));
				vilao.setVida(rs.getInt("vida"));
				vilao.setStatus(rs.getString("statusVilao"));
				vilao.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(vilao);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o vilão!");
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
	
	public List<Vilao> getPeloNomePrisioneiro(String nomeVilao) {
		List<Vilao> lista = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOMEPRESO);
			stmt.setString(1, nomeVilao);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Vilao vilao = new Vilao();
				vilao.setId(rs.getInt("idVilao"));
				vilao.setNome(rs.getString("nomeVilao"));
				vilao.setPeso(rs.getInt("pesoVilao"));
				vilao.setAltura(rs.getInt("alturaVilao"));
				vilao.setFotoVilao(rs.getBlob("fotoVilao"));
				vilao.setAtaque(rs.getInt("atk"));
				vilao.setDefesa(rs.getInt("def"));
				vilao.setVida(rs.getInt("vida"));
				vilao.setStatus(rs.getString("statusVilao"));
				vilao.setEstadoFisico(rs.getString("estadoFisico"));
				lista.add(vilao);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o vilão!");
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
	
	public int getNumPrisioneiros(int idPolicia) {
		int numPris = 0;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNUMPRIS);
			stmt.setInt(1, idPolicia);
			rs = stmt.executeQuery();
			if (rs.next()) {
				numPris = rs.getInt("count(Policia_idPolicia)");
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
		return numPris;
	}
	
	public List<Vilao> getListaPrisioneiros(int idPolicial) {
		List<Vilao> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOMEPRIS);
			stmt.setInt(1, idPolicial);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Vilao vilao = new Vilao();
				vilao.setNome(rs.getString("nomeVilao"));
				lista.add(vilao);
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
				proximoId = (rs.getInt("proximoCodigoVilao()"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar pegar o proximo id vilao!");
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
