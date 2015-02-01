package exemplo.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioId implements Serializable {
    @Column(name = "TXT_CPF", nullable = false, length = 14, unique = true)
    private String cpf;
    @Column(name = "TXT_LOGIN", nullable = false, length = 50, unique = true)    
    private String login;

    @Override
    public int hashCode() {
        return cpf.hashCode() + login.hashCode();
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        } else if (getClass() != objeto.getClass()) {
            return false;
        } else {
            return (cpf.equals(((UsuarioId)objeto).getCpf()) && login.equals(((UsuarioId)objeto).getCpf()));
        }
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }    
}
