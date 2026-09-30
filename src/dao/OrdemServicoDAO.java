package dao;

import model.ClienteOficina;
import model.OrdemServico;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

    private static final String SELECT_JOIN =
            "SELECT os.id, os.numero_os, os.data_abertura, os.descricao_defeito, os.valor_total, "
          + "       c.id AS cliente_id, c.nome, c.telefone, c.cpf "
          + "FROM ordem_servico os "
          + "INNER JOIN cliente_oficina c ON c.id = os.cliente_id ";

    // CREATE
    public void inserir(OrdemServico os) {
        String sql = "INSERT INTO ordem_servico (numero_os, data_abertura, descricao_defeito, valor_total, cliente_id) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, os.getNumeroOS());
            ps.setDate(2, Date.valueOf(os.getDataAbertura()));
            ps.setString(3, os.getDescricaoDefeito());
            ps.setDouble(4, os.getValorTotal());
            ps.setInt(5, os.getCliente().getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) os.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir OS: " + e.getMessage());
        }
    }

    // READ (por id, com dados do cliente via INNER JOIN)
    public OrdemServico buscarPorId(int id) {
        String sql = SELECT_JOIN + "WHERE os.id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar OS: " + e.getMessage());
        }
        return null;
    }

    // READ (todas) - consulta com INNER JOIN trazendo dados combinados das duas tabelas
    public List<OrdemServico> listarTodasComCliente() {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "ORDER BY os.data_abertura, os.id";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar OS: " + e.getMessage());
        }
        return lista;
    }

    // READ (por cliente) - INNER JOIN filtrado
    public List<OrdemServico> listarPorCliente(int clienteId) {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = SELECT_JOIN + "WHERE c.id = ? ORDER BY os.data_abertura, os.id";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar OS do cliente: " + e.getMessage());
        }
        return lista;
    }

    // UPDATE
    public boolean atualizar(OrdemServico os) {
        String sql = "UPDATE ordem_servico SET numero_os = ?, data_abertura = ?, descricao_defeito = ?, "
                   + "valor_total = ?, cliente_id = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, os.getNumeroOS());
            ps.setDate(2, Date.valueOf(os.getDataAbertura()));
            ps.setString(3, os.getDescricaoDefeito());
            ps.setDouble(4, os.getValorTotal());
            ps.setInt(5, os.getCliente().getId());
            ps.setInt(6, os.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar OS: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deletar(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar OS: " + e.getMessage());
            return false;
        }
    }

    private OrdemServico mapear(ResultSet rs) throws SQLException {
        ClienteOficina c = new ClienteOficina(rs.getInt("cliente_id"), rs.getString("nome"),
                rs.getString("telefone"), rs.getString("cpf"));
        OrdemServico os = new OrdemServico(rs.getInt("id"), rs.getString("numero_os"),
                rs.getDate("data_abertura").toLocalDate(), rs.getString("descricao_defeito"),
                rs.getDouble("valor_total"), c);
        c.getOrdens().add(os);
        return os;
    }
}
