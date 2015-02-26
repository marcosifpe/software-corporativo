package alo;

import javax.ejb.Stateless;

/**
 * Observe que o demos um nome ao bean.
 * Se não fosse dado esse nome, 
 * o nome default seria "AloBean".
 */
@Stateless(name = "AloMundoBean")
public class AloBean implements Alo {
    @Override
    public String getMensagem(String nome) {
        return String.format("Alô %s, bem vindo ao EJB 3!", nome);
    }
}
