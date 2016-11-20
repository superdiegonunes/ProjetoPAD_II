package dao;

import entity.Usuario;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import util.jpa.JPAEntityManager;

/**
 * DAO para a entidade Usuário utilizando a classe de DAO genérica como base.
 *
 * @author guto
 */
public class UsuarioDAO extends DAO {

  public UsuarioDAO() {
    super(Usuario.class);
  }

  /**
   * Método que tem o objetivo de realizar a autênticação de um usuário através
   * do login e senha do mesmo.
   *
   * @param login Login.
   * @param senha Senha.
   *
   * @return Usuário com os parâmetros acima (ou nulo caso não exista).
   */
  public Usuario autenticarUsuario(String login, String senha) {

    /* Montando a query para que seja realizada a consulta. */
    manager = JPAEntityManager.getEntityManager();
    Query query = manager.createQuery("select u from Usuario u"
            + " where u.login = ?1 and u.senha = ?2");
    query.setParameter(1, login);
    query.setParameter(2, senha);

    try {

      /* Por fim, realizando a consulta e retornando seu resultado. */
      return (Usuario) query.getSingleResult();

    } catch (NoResultException e) {

      /* Caso não haja nenhum usuário com o login e senha informados,
       * a função retorna nulo.
       */
      return null;
    }
  }
}
