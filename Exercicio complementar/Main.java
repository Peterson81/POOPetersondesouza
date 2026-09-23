import java.util.*;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // 1 - SORTEDSET - CONSULTAS POR FAIXA
        // ==========================================

        SortedSet<Double> precos = new TreeSet<>();

        precos.add(10.0);
        precos.add(25.0);
        precos.add(40.0);
        precos.add(55.0);
        precos.add(70.0);
        precos.add(90.0);
        precos.add(120.0);

        System.out.println("Todos os preços:");
        System.out.println(precos);

        // Preços entre 25 e 70
        SortedSet<Double> faixa = precos.subSet(25.0, 70.0);
        System.out.println("Preços entre 25 e 70:");
        System.out.println(faixa);

        // Preços menores que 55
        SortedSet<Double> teto = precos.headSet(55.0);
        System.out.println("Preços menores que 55:");
        System.out.println(teto);

        // Preços maiores ou iguais a 70
        SortedSet<Double> piso = precos.tailSet(70.0);
        System.out.println("Preços a partir de 70:");
        System.out.println(piso);


        // ==========================================
        // 2 - QUEUE - FILA DE PEDIDOS
        // ==========================================

        Queue<String> fila = new LinkedList<>();

        fila.offer("Pedido 001");
        fila.offer("Pedido 002");
        fila.offer("Pedido 003");

        System.out.println("\nPróximo pedido:");
        System.out.println(fila.peek());

        System.out.println("Pedido atendido:");
        System.out.println(fila.poll());

        System.out.println("Próximo pedido:");
        System.out.println(fila.peek());

        System.out.println("Fila restante:");
        System.out.println(fila);


        // ==========================================
        // 3 - COLLECTIONS - MÉTODOS UTILITÁRIOS
        // ==========================================

        List<String> produtos = new ArrayList<>();

        produtos.add("Notebook");
        produtos.add("Mouse");
        produtos.add("Teclado");
        produtos.add("Monitor");
        produtos.add("Headset");

        System.out.println("\nLista original:");
        System.out.println(produtos);

        // Embaralhar
        Collections.shuffle(produtos);

        System.out.println("Lista embaralhada:");
        System.out.println(produtos);

        // Inverter
        Collections.reverse(produtos);

        System.out.println("Lista invertida:");
        System.out.println(produtos);

        // Menor elemento
        String menor = Collections.min(produtos);

        System.out.println("Menor produto:");
        System.out.println(menor);

        // Lista sincronizada
        List<String> listaSincronizada =
                Collections.synchronizedList(new ArrayList<>());

        listaSincronizada.add("Produto A");
        listaSincronizada.add("Produto B");
        listaSincronizada.add("Produto C");

        System.out.println("Lista sincronizada:");
        System.out.println(listaSincronizada);


        // ==========================================
        // 4 - HASHTABLE + ENUMERATION
        // ==========================================

        Hashtable<Integer, String> sessoes = new Hashtable<>();

        sessoes.put(1, "Sessão do usuário João");
        sessoes.put(2, "Sessão do usuário Maria");
        sessoes.put(3, "Sessão do usuário Pedro");

        System.out.println("\nSessões ativas:");

        Enumeration<Integer> chaves = sessoes.keys();

        while (chaves.hasMoreElements()) {

            Integer chave = chaves.nextElement();

            System.out.println(
                    chave + " - " + sessoes.get(chave)
            );
        }
    }
}