package model;

import control.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author guto
 */
public class ItemDAO {

    /* Constantes que irão representar as operações para
    serem realizadas no banco. */
    private static final String OP_SQL_INSERIR
            = "insert into Item (descricao, preco) "
            + "values (?, ?)";
    private static final String OP_SQL_EXCLUIR
            = "delete from Item where "
            + "id = ?";
    private static final String OP_SQL_LISTAR
            = "select * from Item";
    private static final String OP_SQL_LISTAR_ITENS_CARRINHO
            = "select i.id, i.descricao, i.preco, ic.quantidade  "
            + "from Itens_Carrinho ic right join Item i on (i.id = ic.Item_id) "
            + "where ic.Carrinho_id = ?;";

    /* Conexão com o banco de dados. */
    private Connection conn;

    /**
     * Método que adiciona o item ao banco de dados.
     *
     * @param item item.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void adicionar(Item item) throws SQLException, ClassNotFoundException {

        try {
            /* Tentando obter conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_INSERIR,
                                Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, item.getDescricao());
                pStatement.setDouble(2, item.getPreco());

                /* Executando a operação. */
                pStatement.execute();

                /* Inserindo o ID gerado na inserção
                no objeto.*/
                ResultSet rs = pStatement.getGeneratedKeys();
                rs.next();
                item.setId(rs.getLong(1));

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
     * Método que retorna uma lista dos itens cadastrados no banco.
     *
     * @return Lista com os itens cadastrados no banco.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Item> listar() throws ClassNotFoundException, SQLException {
        List<Item> itens = new ArrayList<Item>();

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_LISTAR);
                ResultSet resultSet = pStatement.executeQuery();

                /* Populando a lista "itens" com os itens obtidos
                do banco de dados.
                 */
                while (resultSet.next()) {
                    Item i = new Item();
                    i.setId(resultSet.getLong(1));
                    i.setDescricao(resultSet.getString(2));
                    i.setPreco(resultSet.getDouble(3));

                    /* Adicionando o item na lista. */
                    itens.add(i);
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
        return itens;

    }

    /**
     * Método que retorna uma lista dos itens cadastrados no banco que sejam do
     * carrinho passado por parêmtro..
     *
     * @param carrinho Carrinho.
     *
     * @return Lista com os itens cadastrados no banco do carrinho passado por
     * parâmetro..
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public List<Item> listarItensCarrinho(Carrinho carrinho)
            throws ClassNotFoundException, SQLException {
        List<Item> itens = new ArrayList<Item>();

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_LISTAR_ITENS_CARRINHO);
                pStatement.setLong(1, carrinho.getId()); // Id do carrinho 
                ResultSet resultSet = pStatement.executeQuery();

                /* Populando a lista "itens" com os itens obtidos
                do banco de dados. */
                while (resultSet.next()) {
                    Item i = new Item();
                    i.setId(resultSet.getLong(1));
                    i.setDescricao(resultSet.getString(2));
                    i.setPreco(resultSet.getDouble(3));
                    i.setQuantidade(resultSet.getInt(4));

                    /* Adicionando o item na lista. */
                    itens.add(i);
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
        return itens;

    }

    /**
     * Método que excluí um item do banco de dados.
     *
     * @param item Item.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void excluir(Item item) throws ClassNotFoundException, SQLException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {

                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_EXCLUIR);
                pStatement.setLong(1, item.getId());

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

}
