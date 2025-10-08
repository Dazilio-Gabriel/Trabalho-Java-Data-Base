package entidades;

import java.time.LocalDate;

public class Movimentacao {

    private int idMovimentacao;
    private Produtos produtos;
    private String tipoMovimentacao;
    private int quantidade;
    private LocalDate dataMov;
    private char sr_deleted;

    public Movimentacao() {

    }

    public Movimentacao(int idMovimentacao, Produtos produto, String tipoMovimentacao, int quantidade, LocalDate dataMov) {

        this.idMovimentacao = idMovimentacao;
        this.produtos = produtos;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.dataMov = dataMov;
        this.sr_deleted = 'F';

    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataMov() {
        return dataMov;
    }

    public void setDataMov(LocalDate dataMov) {
        this.dataMov = dataMov;
    }

    public char getSr_deleted() {
        return sr_deleted;
    }

    public void setSr_deleted(char sr_deleted) {
        this.sr_deleted = sr_deleted;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "idMovimentacao=" + idMovimentacao +
                ", produtos=" + produtos +
                ", tipoMovimentacao='" + tipoMovimentacao + '\'' +
                ", quantidade=" + quantidade +
                ", dataMov=" + dataMov +
                ", sr_deleted=" + sr_deleted +
                '}';
    }
}
