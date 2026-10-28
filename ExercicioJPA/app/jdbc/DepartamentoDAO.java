package br.com.empresa.jdbc;

import br.com.empresa.modelo.Departamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de persistência (DAO) de departamentos, incluindo a alocação de
 * gerentes (departamento.gerente_cpf -> empregado.cpf).
 */
public class DepartamentoDAO {

    public boolean inserir(Departamento departamento) {
        String sql = "INSERT INTO departamento (sigla, nome) VALUES (?, ?)";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, departamento.getSigla());
            comando.setString(2, departamento.getNome());
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao inserir departamento: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarNome(String sigla, String novoNome) {
        String sql = "UPDATE departamento SET nome = ? WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, novoNome);
            comando.setString(2, sigla);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao atualizar departamento: " + e.getMessage());
            return false;
        }
    }

    public boolean alocarGerente(String sigla, String cpfGerente) {
        String sql = "UPDATE departamento SET gerente_cpf = ? WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, cpfGerente);
            comando.setString(2, sigla);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            // A FK fk_departamento_gerente impede um CPF inexistente
            System.err.println("Falha ao alocar gerente: " + e.getMessage());
            return false;
        }
    }

    public boolean removerGerente(String sigla) {
        String sql = "UPDATE departamento SET gerente_cpf = NULL WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, sigla);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao remover gerente: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String sigla) {
        String sql = "DELETE FROM departamento WHERE sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, sigla);
            return comando.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            // A FK fk_empregado_departamento impede excluir depto com empregados
            System.err.println("Falha ao excluir departamento: " + e.getMessage());
            return false;
        }
    }

    public Departamento buscarPorSigla(String sigla) {
        String sql = "SELECT d.sigla, d.nome, d.gerente_cpf, e.nome "
                + "FROM departamento d "
                + "LEFT JOIN empregado e ON d.gerente_cpf = e.cpf "
                + "WHERE d.sigla = ?";
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, sigla);
            try (ResultSet rs = comando.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao buscar departamento: " + e.getMessage());
        }
        return null;
    }

    public List<Departamento> listarTodos() {
        String sql = "SELECT d.sigla, d.nome, d.gerente_cpf, e.nome "
                + "FROM departamento d "
                + "LEFT JOIN empregado e ON d.gerente_cpf = e.cpf "
                + "ORDER BY d.nome ASC";
        List<Departamento> departamentos = new ArrayList<>();
        try (Connection conexao = FabricaConexao.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                departamentos.add(mapear(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao listar departamentos: " + e.getMessage());
        }
        return departamentos;
    }

    private Departamento mapear(ResultSet rs) throws SQLException {
        Departamento d = new Departamento();
        d.setSigla(rs.getString(1));
        d.setNome(rs.getString(2));
        d.setGerenteCpf(rs.getString(3));
        d.setGerenteNome(rs.getString(4));
        return d;
    }
}