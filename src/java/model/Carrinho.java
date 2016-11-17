package model;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author guto
 */
public class Carrinho {

    /* --- Atributos --- */
    private Long id;
    private Date data;
    private List<Item> itens;

    /**
     *
     * @return Id.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return Itens.
     */
    public List<Item> getItens() {
        return itens;
    }

    /**
     *
     * @param itens Itens.
     */
    public void setItens(List<Item> itens) {
        this.itens = itens;
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
     * @return Data.
     */
    public Date getData() {
        return data;
    }

    /**
     *
     * @param data Data.
     */
    public void setData(Date data) {
        this.data = data;
    }

}
