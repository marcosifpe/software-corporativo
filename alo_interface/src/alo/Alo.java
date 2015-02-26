package alo;

import javax.ejb.Remote;

@Remote
public interface Alo {
    public String getMensagem(String nome);
}
