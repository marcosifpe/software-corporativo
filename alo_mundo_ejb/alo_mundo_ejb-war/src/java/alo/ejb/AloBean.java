package alo.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

@Stateless
public class AloBean implements Alo {
    private String mensagem;
    
    @PostConstruct
    public void inicializar() {
        this.mensagem = "Alô %s, bem vindo ao EJB 3!";
    }

    @Override
    public String getMensagem(String nome) {
        return String.format(mensagem, nome);
    }

    @PreDestroy
    public void destruir() {
        this.mensagem = null;
    }
}
