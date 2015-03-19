package gustavoamigo.testes.unitario;

import gustavoamigo.endereco.entitdades.Cep;
import gustavoamigo.endereco.persistencia.Persistence;
import gustavoamigo.endereco.servicos.CepServicos;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Gustavo
 */
public class CepServicosTest {
    
    static CepServicos cepServicos;
    static Persistence persistence;
    static Cep testCep = new Cep("99999000", "Rua Teste", "Teste", "Teste", "SP");
    
    public CepServicosTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
        persistence = Persistence.getInstance();
        cepServicos = new CepServicos(persistence);
        
        persistence.command(em -> {
            em.persist(testCep); 
        });
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
        persistence.command(em -> {
            em.remove(em.find(Cep.class, testCep.getCep()));
        });
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void givenExistingCep_WhenSearched_ThenItShouldWorkTest() {
        Cep cep = cepServicos.find(testCep.getCep());
        assertEquals(cep.getCep(), testCep.getCep());
    }
    
    @Test
    public void givenExistingCepWithDiferentExtension_WhenSearched_ThenItShouldWorkTest() {
        Cep cep = cepServicos.find("99999999");
        assertEquals(cep.getCep(), testCep.getCep());
    }   
}
