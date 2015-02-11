package exemplo.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria implements Serializable {
    @Id
    private Long id;
    @Column(name = "TXT_NOME")
    private String nome;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_CATEGORIA_MAE", referencedColumnName = "ID")
    private Categoria mae;
    @OneToMany(mappedBy = "mae", orphanRemoval = false)
    private List<Categoria> filhas;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getMae() {
        return mae;
    }

    public void setMae(Categoria mae) {
        this.mae = mae;
    }

    public List<Categoria> getFilhas() {
        return filhas;
    }

    public boolean adicionar(Categoria categoria) {
        return filhas.add(categoria);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Tag[ id=" + id + " ]";
    }
    
}
