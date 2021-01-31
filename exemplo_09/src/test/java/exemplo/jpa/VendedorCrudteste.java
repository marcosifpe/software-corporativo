/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplo.jpa;

import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masc1
 */
public class VendedorCrudteste extends Teste {

    @Test
    public void persistirVendedor() {
        Vendedor vendedor = criarVendedor();
        vendedorServico.persistir(vendedor);
        assertNull(vendedor.getId());
    }

    private Vendedor criarVendedor() {
        Vendedor vendedor = new Vendedor();
        vendedor.setPrimeiroNome("Sicrano");
        vendedor.setUltimoNome("Silva");
        vendedor.setEmail("sicrano_da_silva@gmail.com");
        vendedor.setLogin("sicrano_da_silva");
        vendedor.setSenha("fR$k*m324!A");
        vendedor.setCpf("201.706.760-13");
        vendedor.addTelefone("(81) 3456-2525");
        vendedor.addTelefone("(81) 9122-4528");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1981);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.DAY_OF_MONTH, 25);
        vendedor.setDataNascimento(c.getTime());
        vendedor.setReputacao(Reputacao.NOVATO);
        vendedor.setValorVendas(Double.parseDouble("0"));

        criarEndereco(vendedor);

        return vendedor;
    }

    private void criarEndereco(Vendedor vendedor) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Iolanda Rodrigues Sobral");
        endereco.setBairro("Iputinga");
        endereco.setCidade("Recife");
        endereco.setEstado("PE");
        endereco.setCep("50.690-220");
        endereco.setNumero(550);
        vendedor.setEndereco(endereco);
    }
}
