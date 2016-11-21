package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import util.jpa.JPAEntityManager;

/**
 * Classe de DAO genérica.
 *
 * @author ciro
 * @param <T> Classe.
 */
public class DAO<T> {

  private final Class<T> classe;
  protected EntityManager manager;

  public DAO(Class<T> classe) {
    this.classe = classe;
  }

  public void adicionar(T t) {
    manager = JPAEntityManager.getEntityManager();
    EntityTransaction et = manager.getTransaction();
    try {
      et.begin();
      manager.persist(t);
      et.commit();
    } catch (Exception e) {
      et.rollback();
      throw e;
    }
    manager.close();
  }

  public T consultar(Long id) {
    manager = JPAEntityManager.getEntityManager();
    T instancia = manager.find(classe, id);
    manager.close();
    return instancia;
  }
 

  public void alterar(T t) {
    manager = JPAEntityManager.getEntityManager();
    EntityTransaction et = manager.getTransaction();
    try {
      et.begin();
      manager.merge(t);
      et.commit();
    } catch (Exception e) {
      et.rollback();
      throw e;
    }
    manager.close();
  }

  public void excluir(Long id) {
    manager = JPAEntityManager.getEntityManager();
    EntityTransaction et = manager.getTransaction();
    T t = manager.find(classe, id);
    try {
      et.begin();
      manager.remove(t);
      et.commit();
    } catch (Exception e) {
      et.rollback();
      throw e;
    }
    manager.close();
  }
}
