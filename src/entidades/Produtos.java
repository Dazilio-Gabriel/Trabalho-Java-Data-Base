package entidades;

public class Produtos {

    private int idProduto;
    private String nome;
    private String descricao;
    private int estoque;
    private char sr_deleted;

    public Produtos() {

    }

    public Produtos(int idProduto, String nome, String descricao, int estoque) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.estoque = estoque;
        this.sr_deleted = 'F';
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public char getSr_deleted() {
        return sr_deleted;
    }

    public void setSr_deleted(char sr_deleted) {
        this.sr_deleted = sr_deleted;
    }

    @Override
    public String toString() {
        return "Produtos{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", estoque=" + estoque +
                ", sr_deleted=" + sr_deleted +
                '}';
    }
}