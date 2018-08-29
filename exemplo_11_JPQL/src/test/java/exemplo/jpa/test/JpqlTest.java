package exemplo.jpa.test;

import exemplo.jpa.CartaoCredito;
import exemplo.jpa.Categoria;
import exemplo.jpa.Comprador;
import exemplo.jpa.DatasLimite;
import exemplo.jpa.Item;
import exemplo.jpa.Oferta;
import exemplo.jpa.Reputacao;
import exemplo.jpa.Usuario;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MASC
 */
public class JpqlTest extends GenericTest {

    @Test
    public void categoriaPorNome() {
        logger.info("Executando categoriaPorNome()");
        TypedQuery<Categoria> query = em.createQuery(
                "SELECT c FROM Categoria c WHERE c.nome LIKE :nome",
                Categoria.class);
        query.setParameter("nome", "Instrumentos%");
        List<Categoria> categorias = query.getResultList();

        for (Categoria categoria : categorias) {
            assertTrue(categoria.getNome().startsWith("Instrumentos"));
        }

        assertEquals(2, categorias.size());
    }

    @Test
    public void categoriaPorNomeNamedQuery() {
        logger.info("Executando categoriaPorNomeNamedQuery()");
        TypedQuery<Categoria> query = em.createNamedQuery("Categoria.PorNome", Categoria.class);
        query.setParameter("nome", "Instrumentos%");
        List<Categoria> categorias = query.getResultList();

        for (Categoria categoria : categorias) {
            assertTrue(categoria.getNome().startsWith("Instrumentos"));
        }

        assertEquals(2, categorias.size());
    }

    @Test
    public void quantidadeCategoriasFilhas() {
        logger.info("Executando quantidadeCategoriasFilhas()");
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(c) FROM Categoria c WHERE c.mae IS NOT NULL", Long.class);
        Long resultado = query.getSingleResult();
        assertEquals(new Long(3), resultado);
    }

    @Test
    public void maximaEMinimaDataNascimento() {
        logger.info("Executando maximaEMinimaDataNascimento()");
        Query query = em.createQuery(
                "SELECT MAX(c.dataNascimento), MIN(c.dataNascimento) FROM Comprador c");
        Object[] resultado = (Object[]) query.getSingleResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String maiorData = dateFormat.format((Date) resultado[0]);
        String menorData = dateFormat.format((Date) resultado[1]);
        assertEquals("21-12-1999", maiorData);
        assertEquals("11-08-1973", menorData);
    }

    @Test
    public void maximaEMinimaDataNascimentoQueryObjeto() {
        logger.info("Executando maximaEMinimaDataNascimentoQueryObjeto()");
        TypedQuery<DatasLimite> query = em.createQuery(
                "SELECT NEW exemplo.jpa.DatasLimite(MAX(c.dataNascimento), MIN(c.dataNascimento)) FROM Comprador c",
                DatasLimite.class);
        DatasLimite datasLimite = query.getSingleResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataMaxima = dateFormat.format((Date) datasLimite.getDataMaxima());
        String dataMinima = dateFormat.format((Date) datasLimite.getDataMinima());
        assertEquals("21-12-1999", dataMaxima);
        assertEquals("11-08-1973", dataMinima);
    }

    @Test
    public void categoriasMaes() {
        logger.info("Executando categoriasMaes()");
        TypedQuery<Categoria> query;
        query = em.createQuery(
                "SELECT c FROM Categoria c WHERE c.filhas IS NOT EMPTY",
                Categoria.class);
        List<Categoria> categorias = query.getResultList();

        for (Categoria categoria : categorias) {
            assertTrue(!categoria.getFilhas().isEmpty());
        }

        assertEquals(1, categorias.size());
    }

    @Test
    public void compradoresVisa() {
        logger.info("Executando compradoresVisa()");
        TypedQuery<Comprador> query;
        query = em.createQuery(
                "SELECT c FROM Comprador c WHERE c.cartaoCredito.bandeira like ?1",
                Comprador.class);
        query.setParameter(1, "VISA"); //Setando parâmetro posicional.
        query.setMaxResults(20); //Determinando quantidade máxima de resultados.
        List<Comprador> compradores = query.getResultList();

        for (Comprador comprador : compradores) {
            assertEquals("VISA", comprador.getCartaoCredito().getBandeira());
        }

        assertEquals(2, compradores.size());
    }

    @Test
    public void compradoresVisaMastercard() {
        logger.info("Executando compradoresVisaMastercard()");
        TypedQuery<Comprador> query;
        query = em.createQuery(
                "SELECT c FROM Comprador c "
                + "WHERE c.cartaoCredito.bandeira LIKE ?1 "
                + "OR c.cartaoCredito.bandeira LIKE ?2",
                Comprador.class);
        query.setParameter(1, "VISA"); //Setando parâmetro posicional.
        query.setParameter(2, "MASTERCARD"); //Setando parâmetro posicional.        
        List<Comprador> compradores = query.getResultList();

        for (Comprador comprador : compradores) {
            assertThat(comprador.getCartaoCredito().getBandeira(),
                    CoreMatchers.anyOf(
                            startsWith("MAESTRO"),
                            startsWith("MASTERCARD")));
        }

        assertEquals(3, compradores.size());
    }

    @Test
    public void compradoresMastercardMaestro() {
        logger.info("Executando compradoresMastercardMaestro()");
        TypedQuery<Comprador> query;
        query = em.createQuery(
                "SELECT c FROM Comprador c "
                + "WHERE c.cartaoCredito.bandeira IN ('MAESTRO', 'MASTERCARD')",
                Comprador.class);
        List<Comprador> compradores = query.getResultList();

        for (Comprador comprador : compradores) {
            assertThat(comprador.getCartaoCredito().getBandeira(),
                    CoreMatchers.anyOf(
                            startsWith("MAESTRO"),
                            startsWith("MASTERCARD")));
        }

        assertEquals(2, compradores.size());
    }

    @Test
    public void usuariosPorDataNascimento() {
        logger.info("Executando usuariosPorDataNascimento()");
        TypedQuery<Usuario> query;
        query = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.dataNascimento BETWEEN ?1 AND ?2",
                Usuario.class);
        query.setParameter(1, getData(21, Calendar.FEBRUARY, 1980));
        query.setParameter(2, getData(1, Calendar.DECEMBER, 1990));
        List<Usuario> usuarios = query.getResultList();
        assertEquals(3, usuarios.size());
    }

    @Test
    public void categoriaMaePorFilha() {
        logger.info("Executando categoriaMaePorFilha()");
        Categoria categoria = em.find(Categoria.class, new Long(2));
        TypedQuery<Categoria> query;
        query = em.createQuery(
                "SELECT c FROM Categoria c WHERE :categoria MEMBER OF c.filhas",
                Categoria.class);
        query.setParameter("categoria", categoria);
        categoria = query.getSingleResult();
        assertEquals("Instrumentos Musicais", categoria.getNome());
    }

    @Test
    public void cartoesExpirados() {
        logger.info("Executando cartoesExpirados()");
        TypedQuery<CartaoCredito> query = em.createQuery("SELECT c FROM CartaoCredito c WHERE c.dataExpiracao < CURRENT_TIMESTAMP", CartaoCredito.class);
        List<CartaoCredito> cartoesExpirados = query.getResultList();
        assertEquals(4, cartoesExpirados.size());
    }

    @Test
    public void categoriasPorQuantidadeFilhas() {
        logger.info("Executando categoriasPorQuantidadeFilhas()");
        TypedQuery<Categoria> query;
        query = em.createQuery(
                "SELECT c FROM Categoria c WHERE SIZE(c.filhas) >= ?1",
                Categoria.class);
        query.setParameter(1, 3);
        Categoria categoria = query.getSingleResult();
        assertEquals("Instrumentos Musicais", categoria.getNome());
    }

    @Test
    public void bandeirasDistintas() {
        logger.info("Executando bandeirasDistintas()");
        TypedQuery<String> query
                = em.createQuery("SELECT DISTINCT(c.bandeira) FROM CartaoCredito c ORDER BY c.bandeira", String.class);
        List<String> bandeiras = query.getResultList();
        assertEquals(4, bandeiras.size());
    }

    @Test
    public void ordenacaoCartao() {
        logger.info("Executando ordenacaoCartao()");
        TypedQuery<CartaoCredito> query;
        query = em.createQuery(
                "SELECT c FROM CartaoCredito c ORDER BY c.bandeira DESC",
                CartaoCredito.class);
        List<CartaoCredito> cartoes = query.getResultList();
        assertEquals(5, cartoes.size());
        assertEquals("VISA", cartoes.get(0).getBandeira());
        assertEquals("VISA", cartoes.get(1).getBandeira());
        assertEquals("MASTERCARD", cartoes.get(2).getBandeira());
        assertEquals("MAESTRO", cartoes.get(3).getBandeira());
        assertEquals("HIPERCARD", cartoes.get(4).getBandeira());
    }

    @Test
    public void ordenacaoCartaoNome() {
        logger.info("Executando ordenacaoCartaoNome()");
        TypedQuery<Object[]> query;
        query = em.createQuery(
                "SELECT c.bandeira, c.dono.nome FROM CartaoCredito c ORDER BY c.bandeira DESC, c.dono.nome ASC",
                Object[].class);
        List<Object[]> cartoes = query.getResultList();
        assertEquals(5, cartoes.size());
        assertEquals("VISA:Beltrano Silva", cartoes.get(0)[0] + ":" + cartoes.get(0)[1]);
        assertEquals("VISA:Fulano Silva", cartoes.get(1)[0] + ":" + cartoes.get(1)[1]);
        assertEquals("MASTERCARD:Sicrano Silva", cartoes.get(2)[0] + ":" + cartoes.get(2)[1]);
        assertEquals("MAESTRO:José da Silva", cartoes.get(3)[0] + ":" + cartoes.get(3)[1]);
        assertEquals("HIPERCARD:Fulano Silva", cartoes.get(4)[0] + ":" + cartoes.get(4)[1]);

    }

    @Test
    public void itensPorReputacaoVendedor() {
        logger.info("Executando itensPorReputacaoVendedor()");
        TypedQuery<Item> query;
        query = em.createQuery(
                "SELECT i FROM Item i WHERE i.vendedor IN (SELECT v FROM Vendedor v WHERE v.reputacao = :reputacao)",
                Item.class);
        query.setParameter("reputacao", Reputacao.EXPERIENTE);
        List<Item> itens = query.getResultList();
        assertEquals(3, itens.size());
    }

    @Test
    public void itensVendidos() {
        logger.info("Executando itensVendidos()");
        TypedQuery<Item> query;
        query = em.createQuery(
                "SELECT i FROM Item i WHERE EXISTS (SELECT o FROM Oferta o WHERE o.item = i AND o.vencedora = true)",
                Item.class);
        List<Item> itens = query.getResultList();
        assertEquals(3, itens.size());
        for (Item item : itens) {
            assertThat(item.getTitulo(), CoreMatchers.anyOf(
                    startsWith("boss DD-7"),
                    startsWith("Gibson SG Standard"),
                    startsWith("Flauta Doce")));
        }
    }

    @Test
    public void ultimaOferta() {
        logger.info("Executando ultimaOferta()");
        TypedQuery<Oferta> query;
        query = em.createQuery(
                "SELECT o FROM Oferta o WHERE o.data >= ALL (SELECT o1.data FROM Oferta o1)",
                Oferta.class);
        Oferta oferta = query.getSingleResult();
        assertEquals("Mon Jan 12 12:41:10 BRT 2015", oferta.getData().toString());
    }

    @Test
    public void todasOfertasExcetoAMaisAntiga() {
        logger.info("Executando todasOfertasExcetoAMaisAntiga()");
        TypedQuery<Oferta> query;
        query = em.createQuery(
                "SELECT o FROM Oferta o WHERE o.data > ANY (SELECT o1.data FROM Oferta o1)",
                Oferta.class);
        List<Oferta> ofertas = query.getResultList();
        assertEquals(7, ofertas.size());
    }

    @Test
    public void compradoresComCartao() {
        logger.info("Executando compradoresComCartao()");
        TypedQuery<Comprador> query = em.createQuery(
                "SELECT c FROM Comprador c JOIN c.cartaoCredito cc ORDER BY c.dataCriacao DESC",
                Comprador.class);
        List<Comprador> compradores = query.getResultList();
        assertEquals(5, compradores.size());

        for (Comprador comprador : compradores) {
            assertNotNull(comprador.getCartaoCredito());
        }
    }

    @Test
    public void compradoresCartoesLeftJoin() {
        logger.info("Executando compradoresCartoesLeftJoin()");
        TypedQuery<Object[]> query;
        query = em.createQuery(
                "SELECT c.cpf, cc.bandeira FROM Comprador c LEFT JOIN c.cartaoCredito cc ORDER BY c.cpf",
                Object[].class);
        List<Object[]> compradores = query.getResultList();
        assertEquals(6, compradores.size());
    }

    @Test
    public void compradorPorLogin() {
        logger.info("Executando compradorPorLogin()");
        TypedQuery<Comprador> query;
        query = em.createQuery(
                "SELECT c FROM Comprador c JOIN c.cartaoCredito cc WHERE c.login = ?1",
                Comprador.class);
        query.setParameter(1, "zesilva");
        Comprador comprador = query.getSingleResult();
        assertEquals("zesilva", comprador.getLogin());
        assertNotNull(comprador.getCartaoCredito());
    }

    @Test
    public void timestampSQL() {
        logger.info("Executando timestampSQL()");
        Query query;
        query = em.createNativeQuery(
                "SELECT current_timestamp() FROM DUAL");
        Date dataCorrente = (Date) query.getSingleResult();
        assertNotNull(dataCorrente);
    }

    @Test
    public void categoriaSQL() {
        logger.info("Executando categoriaSQL()");
        Query query;
        query = em.createNativeQuery(
                "SELECT ID_CATEGORIA, TXT_NOME, ID_CATEGORIA_MAE FROM TB_CATEGORIA WHERE ID_CATEGORIA_MAE is null",
                Categoria.class);
        List<Categoria> categorias = query.getResultList();
        assertEquals(3, categorias.size());
    }

    @Test
    public void categoriaSQLNomeada() {
        logger.info("Executando categoriaSQLNomeada()");
        Query query;
        query = em.createNamedQuery("Categoria.PorNomeSQL");
        query.setParameter(1, "Guitarras");
        List<Categoria> categorias = query.getResultList();
        assertEquals(1, categorias.size());
    }

    @Test
    public void categoriaQuantidadeItensSQL() {
        logger.info("Executando categoriaQuantidadeItensSQL()");
        Query query;
        query = em.createNamedQuery("Categoria.QuantidadeItensSQL");
        query.setParameter(1, "Instrumentos Musicais");
        Object[] resultado = (Object[]) query.getSingleResult();
        assertEquals("Instrumentos Musicais", ((Categoria) resultado[0]).getNome());
        assertEquals(5L, resultado[1]);
    }

    private String getCategoriaQuantidade(Object[] resultado) {
        Categoria categoria = (Categoria) resultado[0];
        Long quantidade = (Long) resultado[1];
        return categoria.getNome() + ": " + quantidade;
    }

    @Test
    public void categoriaQuantidadeItens() {
        logger.info("Executando categoriaQuantidadeItens()");
        Query query = em.createQuery("SELECT c, COUNT(i) FROM Categoria c, Item i WHERE c MEMBER OF i.categorias GROUP BY c");
        List<Object[]> resultados = query.getResultList();
        assertEquals(4, resultados.size());
        assertEquals("Instrumentos Musicais: 5", getCategoriaQuantidade(resultados.get(0)));
        assertEquals("Guitarras: 1", getCategoriaQuantidade(resultados.get(1)));
        assertEquals("Pedais: 2", getCategoriaQuantidade(resultados.get(2)));
        assertEquals("Instrumentos de Sopro: 2", getCategoriaQuantidade(resultados.get(3)));
    }

    @Test
    public void categoriaQuantidadeItensInstrumentos() {
        logger.info("Executando categoriaQuantidadeItensInstrumentos()");
        Query query = em.createQuery("SELECT c, COUNT(i) FROM Categoria c, Item i WHERE c MEMBER OF i.categorias GROUP BY c HAVING COUNT(i) >= ?1");
        query.setParameter(1, 3);
        Object[] resultado = (Object[]) query.getSingleResult();
        assertEquals("Instrumentos Musicais", ((Categoria) resultado[0]).getNome());
        assertEquals(5L, resultado[1]);
    }
}
