package exemplo.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author MASC
 */
public class VendedorValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirVendedorInvalido() {
        Vendedor vendedor = null;
        Calendar calendar = new GregorianCalendar();
        try {
            vendedor = new Vendedor();
            vendedor.setCpf("258.171.482-34"); //CPF inválido
            calendar.set(2025, Calendar.JANUARY, 1);
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
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
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
            });

            assertEquals(10, constraintViolations.size());
            assertNull(vendedor.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarVendedorInvalido() {
        TypedQuery<Vendedor> query = em.createQuery("SELECT v FROM Vendedor v WHERE v.cpf like :cpf", Vendedor.class);
        query.setParameter("cpf", "484.854.847-03");
        Vendedor vendedor = query.getSingleResult();
        vendedor.setSenha("teste123456");

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
