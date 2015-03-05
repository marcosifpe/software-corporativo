package ejb.stateful;

import java.io.Serializable;

/**
 *
 * @author MASC
 */
public class Usuario implements Serializable {
    private String login;
    private String senha;
    private String email;
    private Endereco endereco;

    public Usuario() {
        
    }
    
    public Usuario(String login, String senha, String email) {
        this.setLogin(login);
        this.setSenha(senha);
        this.setEmail(email);
    }
    
    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }    

    public Endereco getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public void criarEndereco(String cep, String logradouro, Integer numero) {
        setEndereco(new Endereco(cep, logradouro, numero));
    }
}
