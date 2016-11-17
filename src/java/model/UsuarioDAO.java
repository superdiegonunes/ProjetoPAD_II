/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class UsuarioDAO {

    /* Constantes que irão representar as operações para
     serem realizadas no banco. */
    private static final String OP_SQL_INSERIR
            = "insert into Usuario (login, senha, nome, administrador) "
            + "values (?, ?, ?, ?)";
    private static final String OP_SQL_INSERIR_CARRINHO_USUARIO
            = "insert into Carrinhos_Usuario (Usuario_id, Carrinho_id) "
            + "values (?,?)";
    private static final String OP_SQL_LISTAR
            = "select * from Usuario order by nome";
    private static final String OP_SQL_SELECIONAR_PELO_LOGIN_SENHA
            = "select * from Usuario where login = ? "
            + "and senha = ?";
    private static final String OP_SQL_ATUALIZAR
            = "update Usuario set "
            + "login =  ?, "
            + "senha =  ?, "
            + "nome = ? "
            + "autorizado = ? "
            + "where  id =  ?";
    private static final String OP_SQL_EXCLUIR
            = "delete from Usuario "
            + "where id = ?";

    /* Conexão com o banco de dados. */
    private Connection conn;

    /**
     * Método que autêntica um usuário através do login e senha.
     *
     *
     * @param login Login.
     * @param senha Senha.
     * 
     * @return Retorna um objeto do tipo Usuário preenchido com os
     * dados coletados do banco de dados, OU caso seja inválido o login
     * e\ou senha retorna NULL.
     * 
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Usuario autenticarUsuario(String login, String senha) throws
            SQLException, ClassNotFoundException {
        Usuario usuario = null;

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_SELECIONAR_PELO_LOGIN_SENHA);
                pStatement.setString(1, login);
                pStatement.setString(2, senha);
                ResultSet resultSet = pStatement.executeQuery();

                /* Verificando se há um usuário com essas
                informações de login e senha. */
                if (resultSet.next() != false) {
                    /* Caso haja, populando o objeto da classe
                    usuário. */
                    usuario = new Usuario();
                    usuario.setId(resultSet.getLong(1));
                    usuario.setLogin(resultSet.getString(2));
                    usuario.setSenha(resultSet.getString(3));
                    usuario.setNome(resultSet.getString(4));
                    usuario.setAdministrador(resultSet.getBoolean(5));
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

        /* Retornando o objeto usuário. */
        return usuario;
    }

    /**
     * Método que adiciona o usuário ao banco de dados.
     *
     * @param usuario Usuário.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void adicionar(Usuario usuario) throws SQLException, ClassNotFoundException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_INSERIR,
                                Statement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, usuario.getLogin());
                pStatement.setString(2, usuario.getSenha());
                pStatement.setString(3, usuario.getNome());
                pStatement.setBoolean(4, usuario.getAdministrador());

                /* Executando... */
                pStatement.execute();

                /* Inserindo o ID gerado na inserção
                no objeto.*/
                ResultSet rs = pStatement.getGeneratedKeys();
                rs.next();
                usuario.setId(rs.getLong(1));

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
     * Método que vincula um carrinho à um usuário.
     *
     * @param usuario Usuário.
     * @param carrinho Carrinho.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public void adicionarCarrinhoUsuario(Usuario usuario, Carrinho carrinho) throws SQLException, ClassNotFoundException {
        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement
                        = conn.prepareStatement(OP_SQL_INSERIR_CARRINHO_USUARIO);
                pStatement.setLong(1, usuario.getId());
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
     * Método que atualiza os dados de um usuário do banco de dados.
     *
     * @param usuario Usuário.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void atualizar(Usuario usuario) throws ClassNotFoundException, SQLException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_ATUALIZAR);
                pStatement.setString(1, usuario.getLogin());
                pStatement.setString(2, usuario.getSenha());
                pStatement.setString(3, usuario.getNome());
                pStatement.setBoolean(4, usuario.getAdministrador());
                pStatement.setLong(5, usuario.getId());

                /* Executando... */
                pStatement.execute();

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
     * Método que excluí um usuário e todos seus carrinhos do banco de dados.
     *
     * @param usuario Usuário.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void excluir(Usuario usuario) throws ClassNotFoundException, SQLException {

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Excluindo os carrinhos do usuário. */
                CarrinhoDAO cDAO = new CarrinhoDAO();
                cDAO.excluirCarrinhosUsuario(usuario);

                /* Preparando o comando para ser executado no 
                banco de dados. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_EXCLUIR);
                pStatement.setLong(1, usuario.getId());

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
     * Método que retorna uma lista dos usuários cadastrados no banco.
     *
     * @return Lista com os usuários cadastrados no banco.
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public List<Usuario> listar() throws ClassNotFoundException, SQLException {

        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            /* Tentando obter a conexão. */
            conn = new ConnectionFactory().getConnection();

            try {
                /* Executando a consulta. */
                PreparedStatement pStatement = conn.prepareStatement(OP_SQL_LISTAR);
                ResultSet resultSet = pStatement.executeQuery();

                /* Populando a lista "usuarios" com os usuários obtidos
                do banco de dados.
                 */
                while (resultSet.next()) {
                    Usuario u = new Usuario();
                    u.setId(resultSet.getLong(1));
                    u.setLogin(resultSet.getString(2));
                    u.setSenha(resultSet.getString(3));
                    u.setNome(resultSet.getString(4));

                    /* Adicionando o usuário na lista. */
                    usuarios.add(u);
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

        /* Retornando a lista de usuários. */
        return usuarios;
    }

}
