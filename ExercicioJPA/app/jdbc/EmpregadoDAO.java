package br.com.empresa.jdbc;

import br.com.empresa.modelo.Empregado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de persistência (DAO) de empregados. Usa PreparedStatement com
 * marcadores posicionais (?) para prevenir SQL Injection.
 */
public class EmpregadoDAO {

    public boolean inserir(Empregado empregado) {
        String sql = "INSERT INTO empregado (cpf, nome, departamento_sigla) "
                + "VALUES (?, ?, ?)";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, empregado.getCpf());
            comando.setString(2, empregado.getNome());
            comando.setString(3, empregado.getDepartamentoSigla());
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao inserir empregado: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarNome(String cpf, String novoNome) {
        String sql = "UPDATE empregado SET nome = ? WHERE cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, novoNome);
            comando.setString(2, cpf);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao atualizar empregado: " + e.getMessage());
            return false;
        }
    }

    public boolean transferirDepartamento(String cpf, String novaSigla) {
        String sql = "UPDATE empregado SET departamento_sigla = ? WHERE cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, novaSigla);
            comando.setString(2, cpf);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao transferir empregado: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String cpf) {
        String sql = "DELETE FROM empregado WHERE cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cpf);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao excluir empregado: " + e.getMessage());
            return false;
        }
    }

    public Empregado buscarPorCpf(String cpf) {
        String sql = "SELECT e.cpf, e.nome, e.departamento_sigla, d.nome "
                + "FROM empregado e "
                + "JOIN departamento d ON e.departamento_sigla = d.sigla "
                + "WHERE e.cpf = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cpf);
            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao buscar empregado: " + e.getMessage());
        }
        return null;
    }

    public List<Empregado> listarTodos() {
        String sql = "SELECT e.cpf, e.nome, e.departamento_sigla, d.nome "
                + "FROM empregado e "
                + "JOIN departamento d ON e.departamento_sigla = d.sigla "
                + "ORDER BY e.nome ASC";
        return executarConsulta(sql, null);
    }

    public List<Empregado> listarPorDepartamento(String sigla) {
        String sql = "SELECT e.cpf, e.nome, e.departamento_sigla, d.nome "
                + "FROM empregado e "
                + "JOIN departamento d ON e.departamento_sigla = d.sigla "
                + "WHERE d.sigla = ? "
                + "ORDER BY e.nome ASC";
        return executarConsulta(sql, sigla);
    }

    private List<Empregado> executarConsulta(String sql, String parametro) {
        List<Empregado> empregados = new ArrayList<>();
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            if (parametro != null) {
                comando.setString(1, parametro);
            }
            try (ResultSet rs = comando.executeQuery()) {
                while (rs.next()) {
                    empregados.add(mapear(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao consultar empregados: " + e.getMessage());
        }
        return empregados;
    }

    private Empregado mapear(ResultSet rs) throws SQLException {
        Empregado e = new Empregado();
        e.setCpf(rs.getString(1));
        e.setNome(rs.getString(2));
        e.setDepartamentoSigla(rs.getString(3));
        e.setDepartamentoNome(rs.getString(4));
        return e;
    }
}