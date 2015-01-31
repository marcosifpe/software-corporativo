package exemplo.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(UsuarioId.class)
@Table(name = "TB_USUARIO")
@Access(AccessType.FIELD)
public class Usuario implements Serializable {
    @Id
    @Column(name = "TXT_CPF", nullable = false, length = 14, unique = true)
    private String cpf;
    @Id
    @Column(name = "TXT_LOGIN", nullable = false, length = 50, unique = true)
    private String login;
    @Column(name = "TXT_NOME", nullable = false, length = 255)
    private String nome;    
    @Column(name = "TXT_EMAIL", nullable = false, length = 50)
    private String email;
    @Column(name = "TXT_SENHA", nullable = false, length = 20)
    private String senha;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    private Date dataNascimento;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
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

    @Override
    public String toString() {
        return "exemplo.jpa.Usuario[ cpf=" + cpf + ", login = " + login + ", senha = " + senha + " ]";
    }
}
