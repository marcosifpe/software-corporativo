package alo.ejb;

import javax.ejb.Stateless;

@Stateless(name = "AloMundoBean")
public class AloBean implements Alo {

    @Override
    public String getMensagem(String nome) {
        return String.format("Alô %s, bem vindo ao EJB 3!", nome);
    }

}
