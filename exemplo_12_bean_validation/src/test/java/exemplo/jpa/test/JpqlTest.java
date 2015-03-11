package exemplo.jpa.test;

import exemplo.jpa.Endereco;
import exemplo.jpa.Reputacao;
import exemplo.jpa.Vendedor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author MASC
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JpqlTest {

    private static EntityManagerFactory emf;
    private static final Logger logger = Logger.getGlobal();
    private EntityManager em;
    private EntityTransaction et;

    public JpqlTest() {
        Logger.getGlobal().setLevel(Level.INFO);
    }

    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("exemplo_12");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        try {
            et.commit();
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
        }
    }

    @Test
    public void t01_criarVendedorValido() {
        Vendedor vendedor = new Vendedor();
        vendedor.addTelefone("(81)234-5678");
        vendedor.setCpf("158.171.482-34");
        vendedor.setDataCriacao(new Date());

        try {
            vendedor.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("22/10/1980"));
        } catch (ParseException ex) {
            assertFalse(false);
        }

        vendedor.setEmail("fulano@gmail.com");
        vendedor.setLogin("fulano_silva");
        vendedor.setPrimeiroNome("Fulano");
        vendedor.setUltimoNome("Silva");
        vendedor.setReputacao(Reputacao.NOVATO);
        vendedor.setSenha("m1nhAs3nh4.");
        vendedor.setValorVendas(0.0);
        Endereco endereco = vendedor.criarEndereco();
        endereco.setBairro("CDU");
        endereco.setCep("50670230");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setNumero(20);
        endereco.setComplemento("AP 301");
        endereco.setLogradouro("Av. Professor Moraes Rego");
        em.persist(vendedor);
        assertTrue(true);
    }

    @Test
    public void t02_criarVendedorInvalido() {
        try {
            Vendedor vendedor = new Vendedor();
            vendedor.addTelefone("(81)234-5678");
            vendedor.setCpf("258.171.482-34"); //CPF inválido
            vendedor.setDataCriacao(new Date());
            //Data de nascimento inválida
            vendedor.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse("22/10/2020"));
            vendedor.setEmail("email_invalido@"); //E-mail inválido
            vendedor.setLogin("fulano_silva");
            vendedor.setPrimeiroNome("Fulano");
            vendedor.setUltimoNome("Silva");
            vendedor.setReputacao(Reputacao.NOVATO);
            vendedor.setSenha("testando1234."); //Senha inválida
            vendedor.setValorVendas(0.0);
            vendedor.addTelefone("(81)92345675"); //Quantidade inválida de telefones
            vendedor.addTelefone("(81)92345676");
            vendedor.addTelefone("(81)92345677");
            vendedor.addTelefone("(81)92345678");
            Endereco endereco = vendedor.criarEndereco();
            endereco.setBairro("CDU");
            endereco.setCep("50670-230"); //CEP inválido
            endereco.setCidade("Recife");
            endereco.setEstado("Pernambuco");
            endereco.setNumero(20);
            endereco.setComplemento("AP 301");
            endereco.setLogradouro("Av. Professor Moraes Rego");
            em.persist(vendedor);
            assertTrue(false);
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            final Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().info(violation.getMessage());
            }

            assertEquals(6, constraintViolations.size());
            assertTrue(true);
        } catch (ParseException ex) {
            assertTrue(false);
        }
    }
}
