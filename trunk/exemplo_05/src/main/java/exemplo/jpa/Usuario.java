package exemplo.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USUARIO")
@Access(AccessType.FIELD)
public class Usuario implements Serializable {
    @EmbeddedId
    private UsuarioId id;
    @Column(name = "TXT_NOME", nullable = false, length = 255)
    private String nome;    
    @Column(name = "TXT_EMAIL", nullable = false, length = 50)
    private String email;
    @Column(name = "TXT_SENHA", nullable = false, length = 20)
    private String senha;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO", nullable = true)
    private Date dataNascimento;

    public Usuario() {
        this.id = new UsuarioId();
    }
    
    public String getCpf() {
        return id.getCpf();
    }

    public void setCpf(String cpf) {
        this.id.setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return id.getLogin();
    }

    public void setLogin(String login) {
        this.id.setLogin(login);
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
        return id.hashCode();
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        } else if (getClass() != objeto.getClass()) {
            return false;
        } else {
            return (id.equals(((Usuario)objeto).id));
        }
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Usuario[ cpf=" + getCpf() + ", login = " + getLogin() + " ]";
    }
}
