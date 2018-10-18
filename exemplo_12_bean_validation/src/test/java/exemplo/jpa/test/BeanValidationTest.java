package exemplo.jpa.test;

import exemplo.jpa.CartaoCredito;
import exemplo.jpa.Comprador;
import exemplo.jpa.Endereco;
import exemplo.jpa.Item;
import exemplo.jpa.Oferta;
import exemplo.jpa.Reputacao;
import exemplo.jpa.Usuario;
import exemplo.jpa.Vendedor;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author MASC
 */
public class BeanValidationTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
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
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistirVendedorInvalido() {
        Vendedor vendedor = null;
        Calendar calendar = new GregorianCalendar();
        try {
            vendedor = new Vendedor();
            vendedor.setCpf("258.171.482-34"); //CPF inválido
            calendar.set(2020, Calendar.JANUARY, 1);
            //Data de nascimento inválida            
            vendedor.setDataNascimento(calendar.getTime());
            vendedor.setEmail("email_invalido@"); //E-mail inválido
            vendedor.setLogin("fulano_silva");
            vendedor.setPrimeiroNome("FULANO"); //Nome inválido
            vendedor.setUltimoNome("silva"); //Nome inválido
            vendedor.setReputacao(Reputacao.NOVATO);
            vendedor.setSenha("testando1234."); //Senha inválida
            vendedor.setValorVendas(0.0);
            vendedor.addTelefone("(81)9234-5675"); //Quantidade inválida de telefones
            vendedor.addTelefone("(81)9234-5676");
            vendedor.addTelefone("(81)9234-5677");
            vendedor.addTelefone("(81)9234-5678");
            Endereco endereco = vendedor.criarEndereco();
            endereco.setBairro("CDU");
            endereco.setCep("50670-230"); //CEP inválido
            endereco.setCidade("Recife");
            endereco.setEstado("Pernambuco"); //Estado inválido
            endereco.setNumero(20);
            endereco.setComplemento("AP 301");
            endereco.setLogradouro("Av. Professor Moraes Rego");
            em.persist(vendedor);
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            for (ConstraintViolation violation : constraintViolations) {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class exemplo.jpa.Vendedor.email: Não é um endereço de e-mail"),
                                startsWith("class exemplo.jpa.Vendedor.endereco.estado: Estado inválido"),
                                startsWith("class exemplo.jpa.Vendedor.senha: A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número"),
                                startsWith("class exemplo.jpa.Vendedor.endereco.estado: tamanho deve estar entre 2 e 2"),
                                startsWith("class exemplo.jpa.Vendedor.telefones: tamanho deve estar entre 0 e 3"),
                                startsWith("class exemplo.jpa.Vendedor.ultimoNome: Deve possuir uma única letra maiúscula, seguida por letras minúsculas"),
                                startsWith("class exemplo.jpa.Vendedor.cpf: CPF inválido"),
                                startsWith("class exemplo.jpa.Vendedor.dataNascimento: deve estar no passado"),
                                startsWith("class exemplo.jpa.Vendedor.primeiroNome: Deve possuir uma única letra maiúscula, seguida por letras minúsculas"),
                                startsWith("class exemplo.jpa.Vendedor.endereco.cep: CEP inválido. Deve estar no formado NN.NNN-NNN, onde N é número natural")
                        )
                );
            }

            assertEquals(10, constraintViolations.size());
            assertNull(vendedor.getId());
            throw ex;
        }
    }

    @Test //Comprador, CartaoCredito
    public void criarCompradorInvalido() {
        Comprador comprador = new Comprador();
        CartaoCredito cartaoCredito = new CartaoCredito();
        Calendar calendar = GregorianCalendar.getInstance();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        //CPF inválido
        comprador.setCpf("453.123.472-11");
        calendar.set(1985, Calendar.JANUARY, 1);
        comprador.setDataNascimento(calendar.getTime());
        comprador.setEmail("comprador@gmail.com");
        //Primeiro nome inválido
        comprador.setPrimeiroNome("maria");
        comprador.setUltimoNome("Silva");
        comprador.setLogin("comprador_bom");
        comprador.setSenha("m1nhAs3nh4.");
        Endereco endereco = comprador.criarEndereco();
        endereco.setBairro("CDU");
        endereco.setCep("50.670-230");
        endereco.setCidade("Recife");
        endereco.setEstado("AA");
        endereco.setNumero(20);
        endereco.setComplemento("AP 301");
        endereco.setLogradouro("Av. Professor Moraes Rego");
        //Nº inválido do cartão de crédito
        cartaoCredito.setNumero("1929293458709012");
        //Bandeira inválida
        cartaoCredito.setBandeira(null);
        //Data de expiração inválida (deveria ser data passada).
        calendar.set(2014, Calendar.DECEMBER, 1);
        cartaoCredito.setDataExpiracao(calendar.getTime());
        comprador.setCartaoCredito(cartaoCredito);
        Set<ConstraintViolation<Comprador>> constraintViolations = validator.validate(comprador);

        if (logger.isLoggable(Level.INFO)) {
            for (ConstraintViolation violation : constraintViolations) {
                Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
            }
        }

        assertEquals(6, constraintViolations.size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarCompradorInvalido() {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf like :cpf", Usuario.class);
        query.setParameter("cpf", "787.829.223-06");
        Usuario usuario = query.getSingleResult();
        usuario.setSenha("testando1234");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {           
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
