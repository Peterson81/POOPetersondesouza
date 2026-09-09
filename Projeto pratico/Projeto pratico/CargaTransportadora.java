package Projeto

public class CargaTransportadora implements Rastreavel {

    @Override
    public String getStatusRastreio() {
        return "Carga da transportadora em trânsito";
    }
}