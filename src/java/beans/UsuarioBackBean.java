package beans;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import entity.Usuario;
import dao.UsuarioDAO;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "usuarioBackBean")
@SessionScoped
public class UsuarioBackBean {

    private static UsuarioDAO uDAO = new UsuarioDAO();
    private String message;
    private Usuario usuario = new Usuario();

    public UsuarioBackBean() {
    }

    public String logar() throws SQLException, ClassNotFoundException {

        /* Tentando logar. */
        String login = usuario.getLogin();
        String senha = usuario.getSenha();
        Usuario usuario = uDAO.autenticarUsuario(login, senha);

        if (usuario != null) {

            /* Inserindo as informações do usuário na sessão. */
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) 
                    fc.getExternalContext().getSession(true);
            session.setAttribute("usuario", usuario);

            if (usuario.isAdministrador()) {
                return "/cadastroitem.xhtml";
            }

            return "/carrinho.xhtml";
        }

        message = "Login/senha inválidos!";
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método que cadastra o usuário no banco de dados.
     *
     * @return Action.
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public String cadastrarUsuarioBanco() throws SQLException, ClassNotFoundException {
        /* Cadastrando o usuário. */
        uDAO.adicionar(usuario);

        /* Retornando para a página principal. */
        return "/index.xhtml";
    }

    public String sair() {
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        return "/index.xhtml";
    }

    public String voltar() {
        return "/index.xhtml";
    }
}
