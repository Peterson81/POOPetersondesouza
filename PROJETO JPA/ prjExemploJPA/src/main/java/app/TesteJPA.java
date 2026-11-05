package app;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Empregado;

public class TesteJPA {

    public static void main(String[] args) {
        // 1. Inicializa a Fábrica de Gerenciadores com a Persistence Unit definida
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PuMvcJpa");
        EntityManager em = emf.createEntityManager();

        try {
            // ==========================================
            // OPERAÇÃO 1: Inserção de Registro (Create)
            // ==========================================
            em.getTransaction().begin();
            
            Empregado novoFuncionario = new Empregado("Carlos Eduardo", "Analista de Sistemas", 6500.00);
            em.persist(novoFuncionario);
            
            em.getTransaction().commit();
            System.out.println("Registro persistido com sucesso! ID gerado: " + novoFuncionario.getId());

            // ==========================================
            // OPERAÇÃO 2: Busca por Chave Primária (Read)
            // ==========================================
            Long idPesquisa = novoFuncionario.getId();
            Empregado registroEncontrado = em.find(Empregado.class, idPesquisa);
            System.out.println("Consulta por ID: " + registroEncontrado);

            // ==========================================
            // OPERAÇÃO 3: Consulta Customizada com JPQL
            // ==========================================
            String jpql = "SELECT e FROM Empregado e WHERE e.salario >= :minSalario";
            TypedQuery<Empregado> query = em.createQuery(jpql, Empregado.class);
            query.setParameter("minSalario", 5000.00);

            List<Empregado> listaFiltrada = query.getResultList();
            System.out.println("--- Funcionários com Salário >= 5000 ---");
            for (Empregado emp : listaFiltrada) {
                System.out.println(emp.getNome() + " | Cargo: " + emp.getCargo() + " | R$ " + emp.getSalario());
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Fechamento obrigatório dos recursos
            em.close();
            emf.close();
        }
    }
}
