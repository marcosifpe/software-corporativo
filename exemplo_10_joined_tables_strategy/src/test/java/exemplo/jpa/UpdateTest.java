/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MASC
 */
public class UpdateTest {

    private static EntityManagerFactory emf;
    private static final Logger logger = Logger.getGlobal();
    private EntityManager em;

    public UpdateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("exemplo_10");
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }

    @Test
    public void UpdateTest() {
        Long id = new CompradorUtil().inserirComprador(em).getId();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Comprador comprador = em.find(Comprador.class, id);
            comprador.setEmail("novo_email@gmail.com");
            CartaoCredito cartaoCredito = comprador.getCartaoCredito();
            cartaoCredito.setBandeira("MASTERCARD");
            et.commit();
            assertEquals(true, true); //Considere o teste bem sucedido se não houver exceções.
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                logger.log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                logger.info("Transação cancelada.");
            }
        }
    }

    @Test
    public void MergeTest() {
        Comprador comprador = new CompradorUtil().inserirComprador(em);
        //Limpar o contexto de persistência, todas as entidades gerenciaas passam a ser desanexadas.
        em.clear();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            comprador.setEmail("novo_email2@gmail.com");
            CartaoCredito cartaoCredito = comprador.getCartaoCredito();
            cartaoCredito.setBandeira("HIPERCARD");
            em.merge(comprador);
            et.commit();
            assertEquals(true, true); //Considere o teste bem sucedido se não houver exceções.
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                logger.log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                logger.info("Transação cancelada.");
            }
        }
    }

}
