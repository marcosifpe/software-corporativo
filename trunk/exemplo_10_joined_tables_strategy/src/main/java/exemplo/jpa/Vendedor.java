package exemplo.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_VENDEDOR") 
@DiscriminatorValue(value = "V")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Vendedor extends Usuario implements Serializable {
    @Column(name = "NUM_VALOR_VENDAS")
    private Double valorVendas;
    @Column(name = "TXT_REPUTACAO")
    private String reputacao;

    public Double getValorVendas() {
        return valorVendas;
    }

    public void setValorVendas(Double valorVendas) {
        this.valorVendas = valorVendas;
    }

    public String getReputacao() {
        return reputacao;
    }

    public void setReputacao(String reputacao) {
        this.reputacao = reputacao;
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
