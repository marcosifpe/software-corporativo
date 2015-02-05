package exemplo.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_USUARIO")
@Access(AccessType.FIELD)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_CARTAO_CREDITO", referencedColumnName = "ID", nullable = false, unique = true)
    private CartaoCredito cartaoCredito;
    
    @Embedded
    private Endereco endereco;
    @ElementCollection //O atributo será armazenado em uma tabela que representará a coleção.
    @CollectionTable(name = "TB_TELEFONE", //nome da tabela que representa a coleção.
            //atributo na tabela que faz referência à chave primária de TB_USUARIO
            joinColumns = @JoinColumn(name = "ID_USUARIO", nullable = false))
    @Column(name = "TXT_NUM_TELEFONE", nullable = false, length = 20)
    private Collection<String> telefones;
    @Enumerated(EnumType.STRING) //Use EnumType.ORDINAL para armazenar a enumeração como inteiro.
    @Column(name = "TXT_TIPO_USUARIO", nullable = false, length = 20)
    private TipoUsuario tipo;
    @Column(name = "TXT_CPF", nullable = false, length = 14, unique = true)
    private String cpf;
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

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Collection<String> getTelefones() {
        return telefones;
    }

    public void addTelefone(String telefone) {
        if (telefones == null) {
            telefones = new HashSet<>();
        }
        telefones.add(telefone);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Usuario[ id=" + id + " ]";
    }
}
