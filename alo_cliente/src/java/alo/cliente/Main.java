package alo.cliente;

import alo.Alo;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author MASC
 */
public class Main {
    @EJB(name = "AloMundoBean")
    private static Alo aloBean;
    /**
     * @param args the command line arguments
     * @throws javax.naming.NamingException
     */
    public static void main(String[] args) throws NamingException {
        System.out.println(aloBean.getMensagem("Testando"));

        Context context = new InitialContext();
        aloBean = (Alo) context.lookup("java:global/alo_ejb/AloMundoBean!alo.Alo");
        System.out.println(aloBean.getMensagem("Teste"));
    }
    
}
