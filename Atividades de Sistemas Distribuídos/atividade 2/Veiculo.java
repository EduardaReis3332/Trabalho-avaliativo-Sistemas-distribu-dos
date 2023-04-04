package veiculos;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeVeiculo;
    private String marcaVeiculo;
    private double valorVenda;
    private int ano;

    public Veiculo(String nomeVeiculo, String marcaVeiculo, double valorVenda, int ano) {
        this.nomeVeiculo = nomeVeiculo;
        this.marcaVeiculo = marcaVeiculo;
        this.valorVenda = valorVenda;
        this.ano = ano;
    }

    public String getNomeVeiculo() {
        return nomeVeiculo;
    }

    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
