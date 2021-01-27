/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa.teste;

import exemplo.jpa.CartaoCredito;
import exemplo.jpa.Endereco;
import exemplo.jpa.TipoUsuario;
import exemplo.jpa.Usuario;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author masc1
 */
public class UsuarioTeste {
    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("exemplo_04");
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
        et.commit();
        em.close();
    }

    @Test
    public void persistirUsuario() {
        Usuario usuario;
        usuario = criarUsuario();
        em.persist(usuario);
        em.flush(); //força que a persistência realizada vá para o banco neste momento.

        assertNotNull(usuario.getId());
        assertNotNull(usuario.getCartaoCredito().getId());
    }
    
    @Test
    public void consultarUsuario() {
        Usuario usuario = em.find(Usuario.class, 1L);
        assertEquals("808.257.284-10", usuario.getCpf());
        assertEquals("COMPRADOR", usuario.getTipo().toString());
        CartaoCredito cartaoCredito = usuario.getCartaoCredito();
        assertNotNull(cartaoCredito);
        assertEquals("VISA", cartaoCredito.getBandeira());
        assertEquals("4073020000000002", cartaoCredito.getNumero());
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Fulaninho da Silva");
        usuario.setEmail("fulano6@gmail.com");
        usuario.setLogin("fulano6");
        usuario.setSenha("teste");
        usuario.setCpf("118.729.500-00");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.addTelefone("(81) 3456-2525");
        usuario.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        usuario.setDataNascimento(c.getTime());
        criarEndereco(usuario);
        criarCartaoCredito(usuario);
        return usuario;
    }

    private void criarEndereco(Usuario usuario) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setCep("50690-220");
        endereco.setNumero(550);
        usuario.setEndereco(endereco);
    }

    private void criarCartaoCredito(Usuario usuario) {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("120000-100");
        usuario.setCartaoCredito(cartaoCredito);
    }
}
