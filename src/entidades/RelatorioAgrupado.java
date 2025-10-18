package entidades;


public class RelatorioAgrupado {

    private String tipoMovimentacao;
    private int totalQuantidade;

    public RelatorioAgrupado() {

    }


    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }


    public int getTotalQuantidade() {
        return totalQuantidade;
    }

    public void setTotalQuantidade(int totalQuantidade) {
        this.totalQuantidade = totalQuantidade;
    }
}