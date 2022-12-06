package br.com.treinaweb.agenda.fx.repository.interfaces;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AgendaRepository<T>   {
	
	List<T> selecionar() throws SQLException, Exception;
	void inserir(T entidade) throws IOException, SQLException;
	void atualizar(T entidade);
	void excluir(T entidade);

}
