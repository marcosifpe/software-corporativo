package exemplo.jpa;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteJPA {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo_09");
    private static final Logger logger = Logger.getGlobal();
    
    static {
        logger.setLevel(Level.INFO);
    }
    
    public static void main(String[] args) {
        try {
            Long id = inserirUsuario();
            //consultarUsuario(id);
        } finally {
            emf.close();
        }
    }
    
    private static void consultarUsuario(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            System.out.println("Consultando usuário na base...");
            Usuario usuario = em.find(Usuario.class, id);
            System.out.println("Imprimindo usuário (telefones serão recuperados agora)...");
            System.out.println(usuario.toString());
        } finally {
            if (em != null) {
                em.close();
            }            
        }
    }
    
    public static Long inserirUsuario() {
        Usuario comprador = criarComprador();
        Usuario vendedor = criarVendedor();
        
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(comprador);
            em.persist(vendedor);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                logger.log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                logger.info("Transação cancelada.");
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return comprador.getId();
    }
    
    private static Comprador criarComprador() {
        Comprador comprador = new Comprador();
        comprador.setNome("Sicrano da Silva");
        comprador.setEmail("sicrano@gmail.com");
        comprador.setLogin("sicrano");
        comprador.setSenha("sicrano123");
        comprador.setCpf("534.585.764-40");
        comprador.addTelefone("(81) 3456-2525");
        comprador.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        comprador.setDataNascimento(c.getTime());
        criarEndereco(comprador);
        CartaoCredito cartaoCredito = criarCartaoCredito();
        comprador.setCartaoCredito(cartaoCredito);
        
        return comprador;
    }
    
    private static Vendedor criarVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Fulano da Silva");
        vendedor.setEmail("fulano@gmail.com");
        vendedor.setLogin("fulano");
        vendedor.setSenha("teste");
        vendedor.setCpf("534.585.764-45");
        vendedor.addTelefone("(81) 3456-2525");
        vendedor.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        vendedor.setDataNascimento(c.getTime());
        criarEndereco(vendedor);
        vendedor.setReputacao("Vendedor Ouro");
        vendedor.setValorVendas(50000.00);
        
        return vendedor;
    }
    
    public static void criarEndereco(Usuario usuario) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setCep("50690-220");
        endereco.setNumero(550);
        usuario.setEndereco(endereco);
    }
    
    public static CartaoCredito criarCartaoCredito() {
        CartaoCredito cartaoCredito = new CartaoCredito();
        cartaoCredito.setBandeira("VISA");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);
        cartaoCredito.setDataExpiracao(c.getTime());
        cartaoCredito.setNumero("120000-100");
        return cartaoCredito;
    }
}
