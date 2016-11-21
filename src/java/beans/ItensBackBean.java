/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *
 *
 * @author pedro.oliveira
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entity.Item;
import dao.DAO;
import dao.ItemDAO;
import java.util.Collection;
import java.util.Collections;

@ManagedBean
@SessionScoped
public class ItensBackBean {

    /* Atributos. */
    private static ItemDAO iDAO = new ItemDAO();
    private Item item = new Item();
    private String message;
    private Collection<Item> itens = new ArrayList<>();

    /**
     * Adiciona o item ao banco de dados (para tela de cadastro de itens).
     *
     * @return Action.
     */
    public String adicionar() {
        /* Cadastrando o item. */
        iDAO.adicionar(item);
        message = "Item cadastrado com sucesso!";

        /* Retornando para a página de cadastro. */
        return "/cadastroItem";
    }

    /**
     * Método que preenche a lista de itens e retorna a referência a mesma.
     *
     * @return A lista de itens.
     * @throws java.lang.ClassNotFoundException
     */
    public Collection<Item> listaItens() throws ClassNotFoundException, SQLException {
        itens = iDAO.findAllItens();

        /* Retornando a lista. */
        return itens;
    }

    public ItensBackBean() {
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Collection<Item> getItens() {
        return itens;
    }
}
