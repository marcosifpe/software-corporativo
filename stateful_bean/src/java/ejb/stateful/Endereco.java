package ejb.stateful;

import java.io.Serializable;

/**
 *
 * @author MASC
 */
public class Endereco implements Serializable {
    private String cep;
    private String logradouro;
    private Integer numero;

    public Endereco(String cep, String logradouro, Integer numero) {
        this.setCep(cep);
        this.setLogradouro(logradouro);
        this.setNumero(numero);
    }
    
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    
}
