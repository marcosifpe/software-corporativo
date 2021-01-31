package exemplo.jpa;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author MASC
 */
public class ValidacaoVendedorTeste extends Teste {
    
    @Test(expected = EJBException.class)
    public void persistirVendedorInvalido() {
        Vendedor vendedor;
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
            endereco.setEstado("ZZ"); //Estado inválido
            endereco.setNumero(20);
            endereco.setComplemento("AP 301");
            endereco.setLogradouro("Av. Professor Moraes Rego");
            vendedorServico.persistir(vendedor);
        } catch (EJBException ex) {
            ConstraintViolationException violationException = (ConstraintViolationException) ex.getCausedByException();
            Set<ConstraintViolation<?>> constraintViolations = violationException.getConstraintViolations();
            assertEquals(9, constraintViolations.size());
            throw ex;
        }
    }

    @Test(expected = EJBException.class)
    public void atualizarVendedorInvalido() {
        Vendedor vendedor = vendedorServico.consultarEntidade(new Object[] {"484.854.847-03"}, "Vendedor.PorCPF");
        vendedor.setSenha("teste123456"); //Senha inválida

        try {
            vendedorServico.atualizar(vendedor);
        } catch (EJBException ex) {    
            ConstraintViolationException violationException = (ConstraintViolationException) ex.getCausedByException();
            assertEquals(1, violationException.getConstraintViolations().size());
            throw ex;
        }
    }
}
