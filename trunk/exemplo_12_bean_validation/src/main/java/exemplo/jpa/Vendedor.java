package exemplo.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TB_VENDEDOR") 
@DiscriminatorValue(value = "V")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Vendedor extends Usuario implements Serializable {
    @Column(name = "NUM_VALOR_VENDAS")
    private Double valorVendas;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_REPUTACAO", length = 20, nullable = false)
    private Reputacao reputacao;
    @OneToMany(mappedBy = "vendedor", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> itens;
    
    public Double getValorVendas() {
        return valorVendas;
    }

    public void setValorVendas(Double valorVendas) {
        this.valorVendas = valorVendas;
    }

    public Reputacao getReputacao() {
        return reputacao;
    }

    public void setReputacao(Reputacao reputacao) {
        this.reputacao = reputacao;
    }

    public boolean adicionar(Item item) {
        return itens.add(item);
    }
    
    public List<Item> getItens() {
        return itens;
    }

    public boolean remover(Item item) {
        if (!item.temOfertas())
            return itens.remove(item);
        else
            return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("exemplo.jpa.Vendedor[");
        sb.append(super.toString());
        sb.append(", ");
        sb.append(valorVendas);
        sb.append(", ");
        sb.append(reputacao);        
        sb.append("]");
        return sb.toString();
    }    
}
