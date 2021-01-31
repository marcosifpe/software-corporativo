/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import exemplo.servico.VendedorServico;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author masc1
 */
public abstract class Teste {

    protected static EJBContainer container;
    protected VendedorServico vendedorServico;

    @BeforeClass
    public static void setUpClass() {
        container = EJBContainer.createEJBContainer();
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        container.close();
    }
    
    @Before
    public void setUp() throws NamingException {
        vendedorServico = (VendedorServico) container.getContext().lookup("java:global/classes/ejb/vendedorServico!exemplo.servico.VendedorServico");
    }
    
    @After
    public void tearDown() {
        vendedorServico = null;
    }    
}
