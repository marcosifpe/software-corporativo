package exemplo.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "V")
public class Vendedor extends Usuario implements Serializable {
    @Column(name = "NUM_VALOR_VENDAS", nullable = true)
    private Double valorVendas;
    @Column(name = "TXT_REPUTACAO", nullable = true)
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
