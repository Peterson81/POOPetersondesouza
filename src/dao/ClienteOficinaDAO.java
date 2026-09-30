package dao;

import model.ClienteOficina;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteOficinaDAO {

    // CREATE
    public void inserir(ClienteOficina c) {
        String sql = "INSERT INTO cliente_oficina (nome, telefone, cpf) VALUES (?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getCpf());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    // READ (por id)
    public ClienteOficina buscarPorId(int id) {
        String sql = "SELECT id, nome, telefone, cpf FROM cliente_oficina WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // READ (todos)
    public List<ClienteOficina> listarTodos() {
        List<ClienteOficina> lista = new ArrayList<>();
        String sql = "SELECT id, nome, telefone, cpf FROM cliente_oficina ORDER BY nome";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public boolean atualizar(ClienteOficina c) {
        String sql = "UPDATE cliente_oficina SET nome = ?, telefone = ?, cpf = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getCpf());
            ps.setInt(4, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // DELETE (falha se o cliente ainda possuir OS - FK RESTRICT)
    public boolean deletar(int id) {
        String sql = "DELETE FROM cliente_oficina WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }

    private ClienteOficina mapear(ResultSet rs) throws SQLException {
        return new ClienteOficina(rs.getInt("id"), rs.getString("nome"),
                rs.getString("telefone"), rs.getString("cpf"));
    }
}
