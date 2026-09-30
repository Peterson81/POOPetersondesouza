package app;

import dao.ClienteOficinaDAO;
import dao.OrdemServicoDAO;
import model.ClienteOficina;
import model.OrdemServico;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ClienteOficinaDAO clienteDAO = new ClienteOficinaDAO();
        OrdemServicoDAO osDAO = new OrdemServicoDAO();

        // ---------- INSERÇÃO ----------
        System.out.println("=== 1) INSERINDO CLIENTES E ORDENS DE SERVIÇO ===");
        ClienteOficina joao = new ClienteOficina("João Silva", "(21) 99999-1111", "111.111.111-11");
        ClienteOficina maria = new ClienteOficina("Maria Souza", "(21) 98888-2222", "222.222.222-22");
        clienteDAO.inserir(joao);
        clienteDAO.inserir(maria);

        OrdemServico os1 = new OrdemServico("OS-0001", LocalDate.now(), "Barulho na suspensão dianteira", 450.00, joao);
        OrdemServico os2 = new OrdemServico("OS-0002", LocalDate.now(), "Troca de óleo e filtros", 280.50, joao);
        OrdemServico os3 = new OrdemServico("OS-0003", LocalDate.now(), "Freio com ruído ao frear", 620.00, maria);
        osDAO.inserir(os1);
        osDAO.inserir(os2);
        osDAO.inserir(os3);
        System.out.println("Inseridos: " + joao + " | " + maria);

        // ---------- LISTAGEM COM INNER JOIN ----------
        System.out.println("\n=== 2) LISTAGEM DE OS COM DADOS DO CLIENTE (INNER JOIN) ===");
        List<OrdemServico> todas = osDAO.listarTodasComCliente();
        for (OrdemServico os : todas) {
            System.out.printf("%s | %s | R$ %.2f | Cliente: %s (CPF %s, Tel %s)%n",
                    os.getNumeroOS(), os.getDescricaoDefeito(), os.getValorTotal(),
                    os.getCliente().getNome(), os.getCliente().getCpf(), os.getCliente().getTelefone());
        }

        System.out.println("\n--- OS apenas do cliente " + joao.getNome() + " ---");
        osDAO.listarPorCliente(joao.getId()).forEach(System.out::println);

        // ---------- ATUALIZAÇÃO ----------
        System.out.println("\n=== 3) ATUALIZANDO ===");
        joao.setTelefone("(21) 97777-0000");
        System.out.println("Cliente atualizado? " + clienteDAO.atualizar(joao));

        os1.setValorTotal(520.00);
        os1.setDescricaoDefeito("Barulho na suspensão dianteira - troca de amortecedores");
        System.out.println("OS atualizada? " + osDAO.atualizar(os1));
        System.out.println(osDAO.buscarPorId(os1.getId()));
        System.out.println(clienteDAO.buscarPorId(joao.getId()));

        // ---------- REMOÇÃO ----------
        System.out.println("\n=== 4) REMOVENDO ===");
        System.out.println("Tentando remover cliente com OS (deve falhar pela FK):");
        System.out.println("Removido? " + clienteDAO.deletar(maria.getId()));

        System.out.println("Removendo OS do cliente e depois o cliente:");
        System.out.println("OS removida? " + osDAO.deletar(os3.getId()));
        System.out.println("Cliente removido? " + clienteDAO.deletar(maria.getId()));

        System.out.println("\n=== ESTADO FINAL ===");
        clienteDAO.listarTodos().forEach(System.out::println);
        osDAO.listarTodasComCliente().forEach(System.out::println);

        // ---------- LIMPEZA (permite rodar o Main novamente) ----------
        osDAO.deletar(os1.getId());
        osDAO.deletar(os2.getId());
        clienteDAO.deletar(joao.getId());
        System.out.println("\nDados de demonstração removidos. Fim.");
    }
}
