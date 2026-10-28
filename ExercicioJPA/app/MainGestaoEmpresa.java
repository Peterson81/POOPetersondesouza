package br.com.empresa.app;

import br.com.empresa.jdbc.DepartamentoDAO;
import br.com.empresa.jdbc.EmpregadoDAO;
import br.com.empresa.modelo.Departamento;
import br.com.empresa.modelo.Empregado;

import java.util.Scanner;

/**
 * Aplicação principal: sistema de gestão de empregados e departamentos.
 *
 * Requer o banco "bddepartamentos" criado conforme sql/bddepartamentos.sql
 * e o PostgreSQL acessível em localhost:5432.
 */
public class MainGestaoEmpresa {

    private static final Scanner SC = new Scanner(System.in);
    private static final EmpregadoDAO empregadoDAO = new EmpregadoDAO();
    private static final DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            System.out.print("Opção: ");
            opcao = lerInteiro();
            SC.nextLine();

            switch (opcao) {
                case 1 -> cadastrarEmpregado();
                case 2 -> listarEmpregados();
                case 3 -> buscarEmpregado();
                case 4 -> alterarNomeEmpregado();
                case 5 -> transferirEmpregado();
                case 6 -> excluirEmpregado();
                case 7 -> listarEmpregadosDoDepartamento();
                case 8 -> cadastrarDepartamento();
                case 9 -> listarDepartamentos();
                case 10 -> buscarDepartamento();
                case 11 -> alterarNomeDepartamento();
                case 12 -> alocarGerente();
                case 13 -> removerGerente();
                case 14 -> excluirDepartamento();
                case 0 -> System.out.println("Encerrando aplicação...");
                default -> System.out.println("Opção inválida!");
            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                SC.nextLine();
            }
        }
        SC.close();
    }

    private static void exibirMenu() {
        System.out.println("\n============================================================");
        System.out.println("  SISTEMA DE GESTÃO - EMPREGADOS E DEPARTAMENTOS (JDBC)");
        System.out.println("============================================================");
        System.out.println("  EMPREGADOS");
        System.out.println("    [1]  Cadastrar empregado");
        System.out.println("    [2]  Listar todos os empregados");
        System.out.println("    [3]  Buscar empregado por CPF");
        System.out.println("    [4]  Alterar nome do empregado");
        System.out.println("    [5]  Transferir empregado para outro departamento");
        System.out.println("    [6]  Excluir empregado");
        System.out.println("    [7]  Listar empregados de um departamento");
        System.out.println("  DEPARTAMENTOS");
        System.out.println("    [8]  Cadastrar departamento");
        System.out.println("    [9]  Listar departamentos");
        System.out.println("    [10] Buscar departamento por sigla");
        System.out.println("    [11] Alterar nome do departamento");
        System.out.println("    [12] Alocar gerente a um departamento");
        System.out.println("    [13] Remover gerente do departamento");
        System.out.println("    [14] Excluir departamento");
        System.out.println("------------------------------------------------------------");
        System.out.println("    [0]  Sair");
    }

    // ---------------- EMPREGADOS ----------------

    private static void cadastrarEmpregado() {
        String cpf = lerCpf();
        System.out.print("Nome: ");
        String nome = SC.nextLine();
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();

        if (departamentoDAO.buscarPorSigla(sigla) == null) {
            System.out.println(">>> Departamento inexistente. Cadastre-o primeiro (opção 8).");
            return;
        }
        boolean ok = empregadoDAO.inserir(new Empregado(cpf, nome, sigla));
        System.out.println(ok ? ">>> Empregado cadastrado com sucesso."
                : ">>> Falha ao cadastrar empregado (CPF já existente?).");
    }

    private static void listarEmpregados() {
        System.out.println("\n--- Todos os empregados (ordem alfabética) ---");
        empregadoDAO.listarTodos().forEach(System.out::println);
    }

    private static void buscarEmpregado() {
        String cpf = lerCpf();
        Empregado e = empregadoDAO.buscarPorCpf(cpf);
        System.out.println(e != null ? e : ">>> Empregado não encontrado.");
    }

    private static void alterarNomeEmpregado() {
        String cpf = lerCpf();
        if (empregadoDAO.buscarPorCpf(cpf) == null) {
            System.out.println(">>> Empregado não encontrado.");
            return;
        }
        System.out.print("Novo nome: ");
        String nome = SC.nextLine();
        boolean ok = empregadoDAO.atualizarNome(cpf, nome);
        System.out.println(ok ? ">>> Nome atualizado." : ">>> Falha ao atualizar.");
    }

    private static void transferirEmpregado() {
        String cpf = lerCpf();
        if (empregadoDAO.buscarPorCpf(cpf) == null) {
            System.out.println(">>> Empregado não encontrado.");
            return;
        }
        System.out.print("Nova sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        if (departamentoDAO.buscarPorSigla(sigla) == null) {
            System.out.println(">>> Departamento inexistente.");
            return;
        }
        boolean ok = empregadoDAO.transferirDepartamento(cpf, sigla);
        System.out.println(ok ? ">>> Empregado transferido." : ">>> Falha na transferência.");
    }

    private static void excluirEmpregado() {
        String cpf = lerCpf();
        if (ehGerente(cpf)) {
            System.out.println(">>> Empregado é gerente de um departamento. "
                    + "Remova-o da gerência antes (opção 13).");
            return;
        }
        boolean ok = empregadoDAO.excluir(cpf);
        System.out.println(ok ? ">>> Empregado excluído." : ">>> Empregado não encontrado.");
    }

    private static void listarEmpregadosDoDepartamento() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        if (departamentoDAO.buscarPorSigla(sigla) == null) {
            System.out.println(">>> Departamento inexistente.");
            return;
        }
        System.out.println("\n--- Empregados do departamento " + sigla + " ---");
        empregadoDAO.listarPorDepartamento(sigla).forEach(System.out::println);
    }

    // ---------------- DEPARTAMENTOS ----------------

    private static void cadastrarDepartamento() {
        System.out.print("Sigla (ex.: VEN): ");
        String sigla = SC.nextLine().trim().toUpperCase();
        System.out.print("Nome: ");
        String nome = SC.nextLine();

        boolean ok = departamentoDAO.inserir(new Departamento(sigla, nome));
        System.out.println(ok ? ">>> Departamento cadastrado com sucesso."
                : ">>> Falha ao cadastrar departamento (sigla já existente?).");
    }

    private static void listarDepartamentos() {
        System.out.println("\n--- Todos os departamentos (ordem alfabética) ---");
        departamentoDAO.listarTodos().forEach(System.out::println);
    }

    private static void buscarDepartamento() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        Departamento d = departamentoDAO.buscarPorSigla(sigla);
        System.out.println(d != null ? d : ">>> Departamento não encontrado.");
    }

    private static void alterarNomeDepartamento() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        if (departamentoDAO.buscarPorSigla(sigla) == null) {
            System.out.println(">>> Departamento não encontrado.");
            return;
        }
        System.out.print("Novo nome: ");
        String nome = SC.nextLine();
        boolean ok = departamentoDAO.atualizarNome(sigla, nome);
        System.out.println(ok ? ">>> Nome atualizado." : ">>> Falha ao atualizar.");
    }

    private static void alocarGerente() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        if (departamentoDAO.buscarPorSigla(sigla) == null) {
            System.out.println(">>> Departamento inexistente.");
            return;
        }
        String cpfGerente = lerCpf();
        if (empregadoDAO.buscarPorCpf(cpfGerente) == null) {
            System.out.println(">>> Empregado (CPF) inexistente.");
            return;
        }
        boolean ok = departamentoDAO.alocarGerente(sigla, cpfGerente);
        System.out.println(ok
                ? ">>> Gerente alocado ao departamento " + sigla + "."
                : ">>> Falha ao alocar gerente.");
    }

    private static void removerGerente() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        boolean ok = departamentoDAO.removerGerente(sigla);
        System.out.println(ok
                ? ">>> Gerente removido do departamento " + sigla + "."
                : ">>> Departamento não encontrado.");
    }

    private static void excluirDepartamento() {
        System.out.print("Sigla do departamento: ");
        String sigla = SC.nextLine().trim().toUpperCase();
        if (!empregadoDAO.listarPorDepartamento(sigla).isEmpty()) {
            System.out.println(">>> Departamento possui empregados. "
                    + "Transfira ou exclua-os antes (opções 5/6).");
            return;
        }
        boolean ok = departamentoDAO.excluir(sigla);
        System.out.println(ok ? ">>> Departamento excluído." : ">>> Departamento não encontrado.");
    }

    // ---------------- UTILITÁRIOS ----------------

    /** Verifica se o CPF é gerente de algum departamento. */
    private static boolean ehGerente(String cpf) {
        return departamentoDAO.listarTodos().stream()
                .anyMatch(d -> cpf.equals(d.getGerenteCpf()));
    }

    /** Lê CPF e normaliza para apenas dígitos (aceita 123.456.789-01). */
    private static String lerCpf() {
        while (true) {
            System.out.print("CPF (somente dígitos ou formatado): ");
            String cpf = SC.nextLine().replaceAll("\\D", "");
            if (cpf.length() == 11) {
                return cpf;
            }
            System.out.println(">>> CPF inválido. Informe 11 dígitos.");
        }
    }

    private static int lerInteiro() {
        while (!SC.hasNextInt()) {
            System.out.print("Valor inválido. Digite um inteiro: ");
            SC.next();
        }
        return SC.nextInt();
    }
}