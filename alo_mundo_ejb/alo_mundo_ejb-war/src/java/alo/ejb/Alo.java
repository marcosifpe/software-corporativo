package alo.ejb;

import javax.ejb.Local;

@Local
public interface Alo {

    String getMensagem(String nome);
    
}
