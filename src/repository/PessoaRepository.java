package repository;

import model.Pessoa;

import java.util.*;

public class PessoaRepository {
    private Map<Integer, Pessoa> pessoas = new HashMap<>();

    public void inserir(Pessoa p) {
        pessoas.put(p.getId(), p);
    }

    public Pessoa buscarPorId(int id) {
        return pessoas.get(id);
    }

    public void editar(int id, String novoNome, String novoEmail) {
        Pessoa p = pessoas.get(id);
        if (p != null) {
            p.setNome(novoNome);
            p.setEmail(novoEmail);
        }
    }

    public void excluir(int id) {
        pessoas.remove(id);
    }

    public List<Pessoa> listarTodos() {
        return new ArrayList<>(pessoas.values());
    }

    public boolean existe(int id) {
        return pessoas.containsKey(id);
    }

    public void limpar() {
        pessoas.clear();
    }
}