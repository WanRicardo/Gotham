package br.com.inatel.ec206.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.inatel.ec206.controller.Conexao;
import br.com.inatel.ec206.model.entity.Policia;


public class PoliciaDAO {

	// Design Pattern SINGLETON
	// http://pt.wikipedia.org/wiki/Singleton

	/** The instance. */
	// Atributo estático que conterá a instancia do singleton.
	private static PoliciaDAO INSTANCE;

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;

	/** The insert. */
	private final String INSERT = "INSERT INTO policia (nomePolicia, pesoPolicia, alturaPolicia, fotoPolicia, cargo) values(?,?,?,?,?)";
	
	/** The update. */
	private final String UPDATE = "UPDATE policia SET nomePolicia = ?, pesoPolicia = ?, alturaPolicia = ?, fotoPolicia = ?, cargo = ? WHERE idPolicia = ?";
	
	/** The remove. */
	private final String REMOVE = "DELETE FROM policia WHERE nomePolicia = ?";
	
	/** The selectall. */
	private final String SELECTALL = "SELECT * FROM policia";
	
	/** The selectid. */
	private final String SELECTID = "call selecionarUsuarioPolicial(?)";
	
	private final String SELECTUSERID = "SELECT nomePolicia FROM policia WHERE idPolicia = ?";
	
	private final String SELECTNOME = "SELECT * FROM policia WHERE nomePolicia = ?";
	
	private final String SETISADM = "UPDATE policia SET Policia_idPolicia = ? WHERE idPolicia = ?";
	
	private final String SETISNOTADM = "UPDATE policia SET Policia_idPolicia = NULL WHERE idPolicia = ?";
	
	private final String REMOVEPELOID = "DELETE FROM policia WHERE idPolicia = ?";
	
	private final String SELECTIDADMIN = "SELECT Policia_idPolicia FROM policia WHERE idPolicia = ?";
	
	private final String SETNUMPRIS = "UPDATE policia SET numPrisioneiros = ? WHERE idPolicia = ?";
	
	private final String SELECTIDPOLICIAIS = "SELECT idPolicia FROM policia";
	
	private final String PROXID = "SELECT proximoCodigo()";

	/**
	 * Instantiates a new policia dao.
	 */
	// Construtor privado. Suprime o construtor público padrao.
	private PoliciaDAO() {
	}

	/**
	 * Gets the single instance of PoliciaDAO.
	 *
	 * @return single instance of PoliciaDAO
	 */
	// Método público estático de acesso único ao objeto!
	public static PoliciaDAO getInstance() {
		if (INSTANCE == null) {
			inicializaInstancia();
			// O valor é retornado para quem está pedindo
		}
		return INSTANCE;
		// Retorna o a instância do objeto
	}

	/**
	 * Inicializa instancia.
	 */
	/*
	 * Este metodo é sincronizado para evitar que devido a concorrencia sejam
	 * criados mais de uma instancia.
	 */
	private static synchronized void inicializaInstancia() {
		if (INSTANCE == null) {
			INSTANCE = new PoliciaDAO();
		}
	}

	/**
	 * Cadastrar.
	 *
	 * @param policia the policia
	 * @return true, if successful
	 */
	public boolean cadastrar(Policia policia) {
		boolean salvou = false;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(INSERT);
			stmt.setString(1, policia.getNome());
			stmt.setInt(2, policia.getPeso());
			stmt.setInt(3, policia.getAltura());
			stmt.setBlob(4, policia.getFotoBytePolicia());
			stmt.setString(5, policia.getCargo());
			if (stmt.executeUpdate() == 1)
				salvou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar executar o cadastro policial!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return salvou;

	}

	/**
	 * Editar policia.
	 *
	 * @param policia the policia
	 */
	public boolean editarPolicia(Policia policia) {
		boolean editou = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setInt(6, policia.getId());
			stmt.setString(1, policia.getNome());
			stmt.setInt(2, policia.getPeso());
			stmt.setInt(3, policia.getAltura());
			stmt.setBlob(4, policia.getFotoBytePolicia());
			stmt.setString(5, policia.getCargo());
			if (stmt.executeUpdate() == 1)
				editou = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar editar o cadastro policial!");
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

	/**
	 * Remover policia.
	 *
	 * @param policia the policia
	 */
	public boolean removerPolicia(String nomePolicia) {
		boolean feito = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVE);
			stmt.setString(1, nomePolicia);
			if (stmt.executeUpdate() == 1)
				feito = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro policial!");
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
	
	public boolean removerPoliciaPeloId(int idPolicia) {
		boolean feito = false;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(REMOVEPELOID);
			stmt.setInt(1, idPolicia);
			if (stmt.executeUpdate() == 1)
				feito = true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar remover o cadastro policial!");
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

	/**
	 * Gets the todos.
	 *
	 * @return the todos
	 */
	public List<Policia> getTodos() {
		List<Policia> lista = null;
		Policia police = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTALL);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				police = new Policia();
				police.setId(rs.getInt("idPolicia"));
				police.setIdAdmin(rs.getInt("Policia_idPolicia"));
				police.setNome(rs.getString("nomePolicia"));
				police.setPeso(rs.getInt("pesoPolicia"));
				police.setAltura(rs.getInt("alturaPolicia"));
				police.setCargo(rs.getString("cargo"));
				police.setNumPrisioneiros(rs.getInt("numPrisioneiros"));
				lista.add(police);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar os cadastros policiais!");
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

	/**
	 * Gets the pelo id.
	 *
	 * @param id the id
	 * @return the pelo id
	 */
	public List<Policia> getPeloId(int id) {
		List<Policia> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Policia police = new Policia();
				police.setId(rs.getInt("idPolicia"));
				police.setIdAdmin(rs.getInt("Policia_idPolicia"));
				police.setNome(rs.getString("nomePolicia"));
				police.setPeso(rs.getInt("pesoPolicia"));
				police.setAltura(rs.getInt("alturaPolicia"));
				police.setFotoPolicia(rs.getBlob("fotoPolicia"));
				police.setCargo(rs.getString("cargo"));
				police.setNumPrisioneiros(rs.getInt("numPrisioneiros"));
				police.setUsuario(rs.getString("usuario"));
				police.setSenha(rs.getString("senha"));
				lista.add(police);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o Policial!");
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
	
	public String getNomeUsuario(int id) {
		String nomeUsuario = "";
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTUSERID);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				nomeUsuario = rs.getString("nomePolicia");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o Policial!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return nomeUsuario;
	}
	
	public int verificaAdmin(int idPolicia) {
		int idAdmin = 0;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTIDADMIN);
			stmt.setInt(1, idPolicia);
			rs = stmt.executeQuery();
			if (rs.next()) {
				idAdmin = rs.getInt("Policia_idPolicia");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o Policial!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
		return idAdmin;
	}
	
	public List<Policia> getPeloNome(String nome) {
		List<Policia> lista = null;

		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTNOME);
			stmt.setString(1, nome);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();

			if (rs.next()) {
				Policia police = new Policia();
				police.setId(rs.getInt("idPolicia"));
				police.setIdAdmin(rs.getInt("Policia_idPolicia"));
				police.setNome(rs.getString("nomePolicia"));
				police.setPeso(rs.getInt("pesoPolicia"));
				police.setAltura(rs.getInt("alturaPolicia"));
				police.setFotoPolicia(rs.getBlob("fotoPolicia"));
				police.setCargo(rs.getString("cargo"));
				police.setNumPrisioneiros(rs.getInt("numPrisioneiros"));
				lista.add(police);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar listar o Policial!");
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
	
	public void setIsAdmin(int idPolicia) {
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SETISADM);
			stmt.setInt(1, idPolicia);
			stmt.setInt(2, idPolicia);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar setar o administrador!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
	}
	
	public void setIsNotAdmin(int idPolicia) {
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SETISNOTADM);
			stmt.setInt(1, idPolicia);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar setar o administrador!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
	}
	
	public void setNumPrisioneiros(int numPrisioneiros, int idPolicia) {
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SETNUMPRIS);
			stmt.setInt(1, numPrisioneiros);
			stmt.setInt(2, idPolicia);
			stmt.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar atualizar o numero de prisioneiros!");
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar conectar com o Banco de Dados!");
			e.printStackTrace();
		} finally {
			Conexao.desconectar(conn, stmt, rs);
		}
	}
	
	public List<Policia> getAllPolice() {
		List<Policia> lista = null;
		try {
			conn = Conexao.conectar();
			stmt = conn.prepareStatement(SELECTIDPOLICIAIS);
			rs = stmt.executeQuery();
			lista = new ArrayList<>();
			while (rs.next()) {
				Policia police = new Policia();
				police.setId(rs.getInt("idPolicia"));
				lista.add(police);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar resgatar os id's de todos policiais!");
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
				proximoId = (rs.getInt("proximoCodigo()"));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Um erro ocorreu ao tentar pegar o proximo id policial!");
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
