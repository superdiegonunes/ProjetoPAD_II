package beans;

import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import entity.Usuario;
import dao.UsuarioDAO;

@ManagedBean(name = "usuarioBackBean")
@SessionScoped
public class UsuarioBackBean {
    
    private static UsuarioDAO uDAO = new UsuarioDAO();
    private String message;
    private Usuario usuario = new Usuario();
    
    public UsuarioBackBean() {}
    
    public String logar() throws SQLException, ClassNotFoundException {
        
        /* Tentando logar. */
        String login = usuario.getLogin();
        String senha = usuario.getSenha();
        Usuario usuario = uDAO.autenticarUsuario(login, senha);
        
        if (usuario != null) {
<<<<<<< HEAD
            if (usuario.isAdministrador() == true){
=======
          
            if (usuario.isAdministrador()) {
>>>>>>> origin/master
                return "/cadastroItem";
            }
            
            return "/carrinho";
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
        return "/index";
    }
    
    public String sair() {
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        return "/index";
    }
    
    public String voltar() {
        return "/index";
    }
}
