package exemplo.jpa;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TesteJPA {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo_07");

    static {
        Logger.getGlobal().setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        inserirUsuario();
        //consultarUsuario();
    }

    private static void consultarUsuario() {
        EntityManager em = emf.createEntityManager();
        System.out.println("Consultando usuário na base...");
        Usuario usuario = em.find(Usuario.class, new Long(1));
        System.out.println("Imprimindo usuário (telefones serão recuperados agora)...");
        System.out.println(usuario.toString());
        em.close();
        emf.close();
    }

    public static void inserirUsuario() {
        Usuario usuario = criarUsuario();
        CartaoCredito cartaoCredito = criarCartaoCredito();
        usuario.setCartaoCredito(cartaoCredito);

        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = emf.createEntityManager();
            et = em.getTransaction();
            et.begin();
            em.persist(usuario);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                Logger.getGlobal().log(Level.SEVERE,
                        "Cancelando transação com erro. Mensagem: {0}", ex.getMessage());
                et.rollback();
                Logger.getGlobal().info("Transação cancelada.");
            }
        } finally {
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
        }
    }

    private static Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Fulano da Silva");
        usuario.setEmail("fulano@gmail.com");
        usuario.setLogin("fulano");
        usuario.setSenha("teste");
        usuario.setCpf("534.585.764-45");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.addTelefone("(81) 3456-2525");
        usuario.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        usuario.setDataNascimento(c.getTime());
        criarEndereco(usuario);
        return usuario;
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
