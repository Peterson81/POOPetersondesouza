import java.util.*;

public class Main {

    public static void main(String[] args) {


        System.out.println("===== MISSÃO 1 =====");

        PacoteCorreios pacote = new PacoteCorreios();
        CargaTransportadora carga = new CargaTransportadora();
        EntregaExpressa entrega = new EntregaExpressa();

        CentralRastreamento.inspecionarItem(pacote);
        CentralRastreamento.inspecionarItem(carga);
        CentralRastreamento.inspecionarItem(entrega);

        String produtoQualquer = "Produto comum";

        CentralRastreamento.inspecionarItem(produtoQualquer);



        System.out.println("\n===== MISSÃO 2 =====");

        List<String> fila = new LinkedList<>();

        fila.add("Pedido 001");
        fila.add("Pedido 002");
        fila.add("Pedido 003");

        fila.add(0, "Pedido Prioritário");

        System.out.println("Fila de pedidos:");

        for (String pedido : fila) {
            System.out.println(pedido);
        }


        System.out.println("\n===== VECTOR =====");

        Vector<String> logs = new Vector<>();

        logs.add("Usuário realizou login");
        logs.add("Produto cadastrado");
        logs.add("Pedido realizado");

        for (String log : logs) {
            System.out.println(log);
        }




        System.out.println("\n===== MISSÃO 3 =====");

        Set<CupomDesconto> conjuntoCupons = new HashSet<>();

        CupomDesconto cupom1 =
                new CupomDesconto("DESCONTO10", 10);

        CupomDesconto cupom2 =
                new CupomDesconto("DESCONTO10", 20);

        CupomDesconto cupom3 =
                new CupomDesconto("DESCONTO20", 20);

        boolean primeiro = conjuntoCupons.add(cupom1);
        boolean segundo = conjuntoCupons.add(cupom2);
        boolean terceiro = conjuntoCupons.add(cupom3);

        System.out.println("Primeiro cupom adicionado: " + primeiro);
        System.out.println("Segundo cupom adicionado: " + segundo);
        System.out.println("Terceiro cupom adicionado: " + terceiro);

        System.out.println("\nCupons cadastrados:");

        for (CupomDesconto cupom : conjuntoCupons) {
            System.out.println(cupom);
        }




        CupomDesconto cupomZero =
                new CupomDesconto("ZERODESCONTO", 0);

        conjuntoCupons.add(cupomZero);

        Iterator<CupomDesconto> it =
                conjuntoCupons.iterator();

        while (it.hasNext()) {

            CupomDesconto cupom = it.next();

            if (cupom.getPorcentagem() == 0) {
                it.remove();
            }
        }

        System.out.println("\nApós limpeza:");

        for (CupomDesconto cupom : conjuntoCupons) {
            System.out.println(cupom);
        }


        System.out.println("\n===== MISSÃO 4 =====");

        TreeSet<Produto> catalogoNatural =
                new TreeSet<>();

        Produto produto1 =
                new Produto("001", "Teclado", 150.00);

        Produto produto2 =
                new Produto("002", "Mouse", 80.00);

        Produto produto3 =
                new Produto("003", "Monitor", 900.00);

        Produto produto4 =
                new Produto("004", "Headset", 250.00);

        catalogoNatural.add(produto1);
        catalogoNatural.add(produto2);
        catalogoNatural.add(produto3);
        catalogoNatural.add(produto4);

        System.out.println("Produtos ordenados por nome:");

        for (Produto produto : catalogoNatural) {
            System.out.println(produto);
        }

        TreeSet<Produto> catalogoPreco =
                new TreeSet<>(new ComparadorPorPreco());

        catalogoPreco.add(produto1);
        catalogoPreco.add(produto2);
        catalogoPreco.add(produto3);
        catalogoPreco.add(produto4);

        System.out.println("\nProdutos ordenados por preço:");

        for (Produto produto : catalogoPreco) {
            System.out.println(produto);
        }



        System.out.println("\n===== MISSÃO 5 =====");

        Map<String, Produto> mapaEstoque =
                new HashMap<>();

        mapaEstoque.put("P001",
                new Produto("P001", "Notebook", 3500.00));

        mapaEstoque.put("P002",
                new Produto("P002", "Celular", 2000.00));

        mapaEstoque.put("P003",
                new Produto("P003", "Mouse", 80.00));

        mapaEstoque.put("P004",
                new Produto("P004", "Teclado", 150.00));



        Scanner scanner = new Scanner(System.in);

        System.out.print("\nDigite o código do produto: ");

        String codigo = scanner.nextLine();

        Produto produtoEncontrado =
                mapaEstoque.get(codigo);

        if (produtoEncontrado != null) {

            System.out.println(
                    "Produto encontrado: " +
                            produtoEncontrado
            );

        } else {

            System.out.println(
                    "Produto não encontrado."
            );
        }



        System.out.println("\nCódigos cadastrados:");

        Set<String> chaves =
                mapaEstoque.keySet();

        for (String chave : chaves) {
            System.out.println(chave);
        }


        System.out.println("\nProdutos cadastrados:");

        Collection<Produto> produtos =
                mapaEstoque.values();

        for (Produto produto : produtos) {
            System.out.println(produto);
        }

        scanner.close();
    }

    }