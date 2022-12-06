package br.com.treinaweb.agenda.fx.repository.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.treinaweb.agenda.entidades.Contato;
//import br.com.treinaweb.agenda.fx.entidades.Contat;
import br.com.treinaweb.agenda.fx.repository.interfaces.AgendaRepository;

// metodo foi feito pra armazenar sem o bd
public class ContatoRepository implements AgendaRepository<Contato> {

	private static List<Contato> contatos = new ArrayList<Contato>();

	@Override
	public List<Contato> selecionar() {
		// TODO Auto-generated method stub
		return contatos;
	}

	@Override
	public void inserir(Contato entidade) {

		contatos.add(entidade);
	}

	@Override
	public void atualizar(Contato entidade) {
		var original = contatos.stream().filter(contato -> contato.getNome().equals(entidade.getNome())).findFirst();
		if (original.isPresent()) {
			original.get().setIdade(entidade.getIdade());
			original.get().setTelefone(entidade.getTelefone());
		}

	}

	@Override
	public void excluir(Contato entidade) {

		contatos.remove(entidade);
	}

}
