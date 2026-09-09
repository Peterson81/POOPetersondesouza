package Projeto

public class PacoteCorreios implements Rastreavel {

    @Override
    public String getStatusRastreio() {
        return "Pacote dos Correios em trânsito";
    }
}