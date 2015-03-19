package gustavoamigo.testes.unitario;

import gustavoamigo.endereco.entitdades.Cep;
import gustavoamigo.endereco.entitdades.Endereco;
import gustavoamigo.endereco.persistencia.Persistence;
import gustavoamigo.endereco.servicos.CepNotFoundException;
import gustavoamigo.endereco.servicos.CepServicos;
import gustavoamigo.endereco.servicos.EnderecoServicos;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Gustavo
 */
public class EnderecoServicoTest {
    
    static Persistence persistence;
    static EnderecoServicos enderecoServicos;
    
    public EnderecoServicoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        persistence = Persistence.getInstance();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void givenANewAddress_WhenCreated_ThenItShouldCrate() {
        CepServicos cepServicosMock = mock(CepServicos.class);
        when(cepServicosMock.find("99999999")).thenReturn(new Cep("99999999", "", "", "", ""));
        
        EnderecoServicos enderecoServicos = new EnderecoServicos(persistence, cepServicosMock);
        Endereco endereco = createTestEndereco();

        try {
            enderecoServicos.create(endereco);
            assertNotNull(endereco.getId());
        } catch (CepNotFoundException ex) {
            fail();
        } finally {
            enderecoServicos.remove(endereco.getId());
        }
        
        
    }
    
    @Test(expectedExceptions = {CepNotFoundException.class})
    public void givenANewAddressWithBadCep_WhenCreated_ThenItShouldFaild() throws CepNotFoundException  {
        
        CepServicos cepServicosMock = mock(CepServicos.class);
        when(cepServicosMock.find("99999999")).thenReturn(null);
        
        EnderecoServicos enderecoServicos = new EnderecoServicos(persistence, cepServicosMock);
        Endereco endereco = createTestEndereco();


        enderecoServicos.create(endereco);
        enderecoServicos.remove(endereco.getId());
    }
    
    private Endereco createTestEndereco() {
        Endereco endereco = new Endereco();
        endereco.setBairro(null);
        endereco.setCep("99999999");
        endereco.setCidade("SP");
        endereco.setComplemento(null);
        endereco.setEstado("SP");
        endereco.setNumero(123);
        endereco.setRua("Rua Teste");
        return endereco;
    }
}
