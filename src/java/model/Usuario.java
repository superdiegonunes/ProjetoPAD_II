package model;

import java.util.List;

/**
 *
 * @author guto
 */
public class Usuario {

    /* --- Atributos --- */
    private long id;
    private String login;
    private String senha;
    private String nome;
    private Boolean administrador;
    private List<Carrinho> carrinhos;

    /**
     * 
     * @return Carrinhos.
     */
    public List<Carrinho> getCarrinhos() {
        return carrinhos;
    }

    /**
     * 
     * @param carrinhos Carrinhos.
     */
    public void setCarrinhos(List<Carrinho> carrinhos) {
        this.carrinhos = carrinhos;
    }
    
    /**
     *
     * @return Id.
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id Id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return Login.
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login Login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return Senha.
     */
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha Senha.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     *
     * @return Nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @param nome Nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * 
     * @return Administrador.
     */
    public Boolean getAdministrador() {
        return administrador;
    }

    /**
     * 
     * @param administrador Administrador. 
     */
    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }
    
}
