package alo.ejb;

import javax.ejb.Remote;

@Remote
public interface Alo {
    String getMensagem(String nome);  
}
