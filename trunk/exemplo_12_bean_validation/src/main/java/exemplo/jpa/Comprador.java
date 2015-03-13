package exemplo.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="TB_COMPRADOR") 
@DiscriminatorValue(value = "C")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Comprador extends Usuario implements Serializable {
    @Valid
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "ID_CARTAO_CREDITO", referencedColumnName = "ID")
    private CartaoCredito cartaoCredito;
    @Valid
    @OneToMany(mappedBy = "comprador", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Oferta> ofertas;
    
    public Comprador() {
        super();
        this.ofertas = new ArrayList<>();
    }
            
    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
        this.cartaoCredito.setDono(this);
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public boolean adicionar(Oferta oferta) {
        oferta.setComprador(this);
        return ofertas.add(oferta);
    }

    @Override
    public String toString() {
        return "exemplo.jpa.Comprador[ id=" + id + " ]";
    }

}
