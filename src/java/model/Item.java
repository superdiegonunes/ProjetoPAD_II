package model;

/**
 *
 * @author guto
 */
public class Item {

    /* --- Atributos --- */
    private Long id;
    private String descricao;
    private int quantidade;
    private double preco;

    /**
     * 
     * @return Id. 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id Id. 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return Descrição.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * 
     * @param descricao Descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * 
     * @return Quantidade. 
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * 
     * @param quantidade Quantidade. 
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * 
     * @return Preço. 
     */
    public double getPreco() {
        return preco;
    }
    
    /**
     * 
     * @param preco Preço. 
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    
}
