package br.com.treinaweb.agenda.fx.repository.interfaces;

import java.util.List;

public interface AgendaRepository<T>   {
	
	List<T> selecionar();
	void inserir(T entidade);
	void atualizar(T entidade);
	void excluir(T entidade);

}
