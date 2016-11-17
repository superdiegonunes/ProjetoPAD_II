package model;

import java.sql.Connection;
import control.ConnectionFactory;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guto
 */
public class CarrinhoDAO {

    /* Constantes que irão representar as operações para
     serem realizadas no banco. */
    private static final String OP_SQL_INSERIR
            = "insert into Carrinho (data) values (?)";
    private static final String OP_SQL_INSERIR_ITEM_CARRINHO
            = "insert into Itens_Carrinho "
            + "(Carrinho_id, Item_id, quantidade_item) "
            + "values (?, ?, ?)";
    private static final String OP_SQL_LISTAR
            = "select * from Carrinho order by data";
    private static final String OP_SQL_LISTAR_CARRINHOS_USUARIO
            = "select * from Carrinho "
            + "where id = ?";
    private static final String OP_SQL_EXCLUIR // Já excluí os itens do  
            = "delete from Carrinho, Itens_Carrinho " // também.
            + "where Carrinho.id = ? and"
            + "Itens_Carrinho.Carrinho_id = ?";
    private static final String[] OP_SQL_EXCLUIR_CARRINHOS_USUARIO
            = {
                "delete from Itens_Carrinho "
                + "where Itens_Carrinho.Carrinho_id in "
                + "(select Carrinho_id from Carrinhos_Usuario "
                + "where Carrinhos_Usuario.Usuario_id = ?)",
                "delete c, cu from Carrinhos_Usuario cu join Carrinho c "
                + "on(cu.Carrinho_id = c.id) "
                + "where cu.Usuario_id = ?;"};

    /* Conexão com o banco de dados. */
    private Connection conn;

    /**
     * Método que adiciona o carrinho ao banco de dados.
     *
     * @param carrinho Carrinho.
     */
    public void adicionar(Carrinho carrinho) throws SQLException, ClassNotFoundException {

        try {
            /* Tentando obter conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_INSERIR,
                                Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, carrinho.getData().toString());

                /* Executando a operação. */
                pStatement.execute();

                /* Inserindo o ID gerado na inserção
                no objeto.*/
                ResultSet rs = pStatement.getGeneratedKeys();
                rs.next();
                carrinho.setId(rs.getLong(1));


                /* Fechando o statement. */
                pStatement.close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Método que adiciona um item ao carrinho no banco de dados.
     *
     * @param item Item.
     * @param carrinho Carrinho.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void adicionarItemCarrinho(Item item, Carrinho carrinho)
            throws SQLException, ClassNotFoundException {
        try {
            /* Tentando obter conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_INSERIR_ITEM_CARRINHO);
                pStatement.setLong(1, carrinho.getId());
                pStatement.setLong(2, item.getId());
                pStatement.setLong(3, item.getQuantidade());

                /* Executando a operação. */
                pStatement.execute();
                pStatement.close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Método que retorna uma lista com os carrinhos cadastrados no banco com
     * seus itens.
     *
     * @return Lista com os carrinhos cadastrados no banco.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public List<Carrinho> listar() throws ClassNotFoundException, SQLException {
        List<Carrinho> carrinhos = new ArrayList<Carrinho>();

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_LISTAR);
                ResultSet resultSet = pStatement.executeQuery();

                ItemDAO itemDAO = new ItemDAO();
                /* Populando a lista "itens" com os itens obtidos
                do banco de dados.
                 */
                while (resultSet.next()) {
                    Carrinho c = new Carrinho();
                    c.setId(resultSet.getLong(1));
                    c.setData(resultSet.getDate(2));

                    /* Adicionando os itens do carrinho no
                    carrinho. */
                    List<Item> listaItens = itemDAO.listarItensCarrinho(c);
                    c.setItens(listaItens);

                    /* Adicionando o item na lista. */
                    carrinhos.add(c);
                }
                pStatement.close();
                resultSet.close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }

        /* Retornando a lista de itens. */
        return carrinhos;
    }

    /**
     * Método que retorna uma lista com os carrinhos cadastrados no banco com
     * seus itens de um usuário.
     *
     * @param usuario Usuário.
     *
     * @return Lista com os carrinhos cadastrados no banco do usuário.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public List<Carrinho> listarCarrinhosUsuario(Usuario usuario)
            throws ClassNotFoundException, SQLException {
        List<Carrinho> carrinhos = new ArrayList<Carrinho>();

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_LISTAR);
                pStatement.setLong(1, usuario.getId()); // Id do usuário
                ResultSet resultSet = pStatement.executeQuery();

                ItemDAO itemDAO = new ItemDAO();
                /* Populando a lista "itens" com os itens obtidos
                do banco de dados.
                 */
                while (resultSet.next()) {
                    Carrinho c = new Carrinho();
                    c.setId(resultSet.getLong(1));
                    c.setData(resultSet.getDate(2));

                    /* Adicionando os itens do carrinho no
                    carrinho. */
                    List<Item> listaItens = itemDAO.listarItensCarrinho(c);
                    c.setItens(listaItens);

                    /* Adicionando o item na lista. */
                    carrinhos.add(c);
                }
                pStatement.close();
                resultSet.close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }

        /* Retornando a lista de itens. */
        return carrinhos;
    }

    /**
     * Método que excluí um carrinho banco de dados.
     *
     * @param carrinho Carrinho.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void excluir(Carrinho carrinho) throws ClassNotFoundException, SQLException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {

                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_EXCLUIR);
                pStatement.setLong(1, carrinho.getId());
                pStatement.setLong(2, carrinho.getId());

                /* Executando... */
                pStatement.execute();
                pStatement.close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Método que excluí um carrinho banco de dados de um usuário.
     *
     * @param usuario Usuário.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void excluirCarrinhosUsuario(Usuario usuario) throws ClassNotFoundException, SQLException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando os comandos para serem executados no 
                banco de dados. */
                PreparedStatement pStatement[] = new PreparedStatement[2];
                pStatement[0] = conn.prepareStatement(OP_SQL_EXCLUIR_CARRINHOS_USUARIO[0]);
                pStatement[0].setLong(1, usuario.getId());
                pStatement[1] = conn.prepareStatement(OP_SQL_EXCLUIR_CARRINHOS_USUARIO[1]);
                pStatement[1].setLong(1, usuario.getId());

                /* Executando... */
                pStatement[0].execute();
                pStatement[1].execute();
                pStatement[0].close();
                pStatement[1].close();
            } finally {
                /* Fechando a conexão. */
                conn.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }

}
