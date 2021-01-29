package alo.ejb;

import alo.Alo;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(name = "AloMundoBean")
@Remote(Alo.class)
public class AloBean implements Alo {

    @Override
    public String getMensagem(String nome) {
        return String.format("Alô %s, bem vindo ao EJB 3!", nome);
    }

}
