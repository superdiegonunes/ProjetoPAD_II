package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidade Carrinho.
 * 
 * @author guto
 */
@Entity
@Table(name = "carrinho")
public class Carrinho implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Temporal(TemporalType.DATE)
  private Date data;

  /**
   * Variável que irá representar os itens que estão armazenados
   * neste carrinho.
   */
  @OneToMany(cascade = CascadeType.PERSIST)
  private List<Item> itens = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public List<Item> getItens() {
    return itens;
  }

  public void setItens(List<Item> itens) {
    this.itens = itens;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not 
    // set
    if (!(object instanceof Carrinho)) {
      return false;
    }
    Carrinho other = (Carrinho) object;
    if ((this.id == null && other.id != null) || 
            (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "prjpadiijpa.entity.Carrinho[ id=" + id + " ]";
  }

}
