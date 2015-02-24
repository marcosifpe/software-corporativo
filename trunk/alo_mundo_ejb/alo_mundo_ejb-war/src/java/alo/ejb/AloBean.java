package alo.ejb;

import javax.ejb.Stateless;

@Stateless
public class AloBean {

    public String getMensagem(String nome) {
        return String.format("Alô %s, bem vindo ao EJB 3!", nome);
    }

}
