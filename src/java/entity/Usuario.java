package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidade Usuário.
 *
 * @author guto
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String login;
  private String senha;
  private String nome;
  private boolean administrador;

  /**
   * Variável que irá armazenar os carrinhos vinculados a este usuário.
   */
  @OneToMany(cascade = CascadeType.PERSIST)
  private List<Carrinho> carrinhos = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public boolean isAdministrador() {
    return administrador;
  }

  public void setAdministrador(boolean administrador) {
    this.administrador = administrador;
  }

  public List<Carrinho> getCarrinhos() {
    return carrinhos;
  }

  public void setCarrinhos(List<Carrinho> carrinhos) {
    this.carrinhos = carrinhos;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are 
    // not set
    if (!(object instanceof Usuario)) {
      return false;
    }
    Usuario other = (Usuario) object;
    if ((this.id == null && other.id != null)
            || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "prjpadiijpa.entity.Usuario[ id=" + id + " ]";
  }

}
