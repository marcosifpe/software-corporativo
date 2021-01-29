/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import ejb.stateful.CadastroBeanRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author MASC
 */
public class Main {
    @EJB
    private static CadastroBeanRemote cadastroBean;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        cadastroBean.criarUsuario("fulano", "teste123", "masc073@gmail.com");
        cadastroBean.criarEnderecoUsuario("50.250-120", "Avenida Beberibe", 100);
        cadastroBean.cadastrarUsuario();
    }
    
}
