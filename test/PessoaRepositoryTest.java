package test;

import model.Pessoa;
import repository.PessoaRepository;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class PessoaRepositoryTest {
    private static PessoaRepository repo;

    // vai executar uma vez antes de todos os testes
    @BeforeClass
    public static void conectarAoBancoFake() {
        System.out.println("==> Simulando conexão com banco de dados...");
        repo = new PessoaRepository();
        repo.inserir(new Pessoa(999, "Pré-cadastrado", "pre@exemplo.com"));
    }

    // vai executar antes de cada teste individual
    @Before
    public void prepararAmbienteDeTeste() {
        System.out.println("-> Preparando ambiente limpo para novo teste");
        repo.limpar(); // limpa todos os dados
        repo.inserir(new Pessoa(999, "Pré-cadastrado", "pre@exemplo.com")); // reinserido
    }

    // vai executar depois de cada teste individual
    @After
    public void limparDadosDoTeste() {
        System.out.println("-> Limpando dados após teste");
        repo.limpar();
    }

    // vai executar uma vez depois de todos os testes
    @AfterClass
    public static void fecharConexaoFake() {
        System.out.println("==> Simulando fechamento da conexão com banco de dados");
        repo = null;
    }

    // TESTES
    @Test
    public void testInserirPessoa() {
        Pessoa p = new Pessoa(1, "Maria", "maria@email.com");
        repo.inserir(p);
        assertTrue(repo.existe(1));
    }

    @Test
    public void testBuscarPessoa() {
        Pessoa p = new Pessoa(2, "João", "joao@email.com");
        repo.inserir(p);
        Pessoa resultado = repo.buscarPorId(2);
        assertEquals("João", resultado.getNome());
    }

    @Test
    public void testEditarPessoa() {
        Pessoa p = new Pessoa(3, "Ana", "ana@email.com");
        repo.inserir(p);
        repo.editar(3, "Ana Silva", "ana.silva@email.com");
        Pessoa atualizada = repo.buscarPorId(3);
        assertEquals("Ana Silva", atualizada.getNome());
    }

    @Test
    public void testExcluirPessoa() {
        Pessoa p = new Pessoa(4, "Carlos", "carlos@email.com");
        repo.inserir(p);
        repo.excluir(4);
        assertFalse(repo.existe(4));
    }

    @Test
    public void testListarPessoas() {
        repo.inserir(new Pessoa(5, "A", "a@email.com"));
        repo.inserir(new Pessoa(6, "B", "b@email.com"));
        List<Pessoa> lista = repo.listarTodos();
        assertEquals(3, lista.size()); // 2 + o pré-cadastrado (999)
    }

    @Test
    public void testBuscarPessoaInexistente() {
        assertNull(repo.buscarPorId(888));
    }

    @Test
    public void testEditarPessoaInexistente() {
        repo.editar(100, "Novo Nome", "novo@email.com");
        assertNull(repo.buscarPorId(100));
    }

    @Test
    public void testExcluirPessoaInexistente() {
        repo.excluir(200);
        assertFalse(repo.existe(200));
    }

    @Test
    public void testInserirPessoaComMesmoIdSobeEscreve() {
        repo.inserir(new Pessoa(999, "Novo", "novo@email.com")); // sobrescreve o pré-cadastrado
        assertEquals("Novo", repo.buscarPorId(999).getNome());
    }

    @Test
    public void testLimparRepositorio() {
        repo.limpar();
        assertTrue(repo.listarTodos().isEmpty());
    }
}