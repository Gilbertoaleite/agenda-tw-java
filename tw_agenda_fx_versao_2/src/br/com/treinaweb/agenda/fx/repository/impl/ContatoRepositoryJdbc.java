package br.com.treinaweb.agenda.fx.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.factory.ConnectionsFactory;
import br.com.treinaweb.agenda.fx.repository.interfaces.AgendaRepository;

public class ContatoRepositoryJdbc implements AgendaRepository<Contato> {
	
	
	@Override
	public List<Contato> selecionar() throws Exception {
		List<Contato> contatos = new ArrayList<Contato>();
		try(Connection connection =  ConnectionsFactory.createConnection()){
			Statement stm = connection.createStatement(); 
			ResultSet rst = stm.executeQuery("SELECT * FROM contatos");
			while (rst.next()) {
			Contato contato = new Contato();
				contato.setId(rst.getInt("id"));
				contato.setNome(rst.getString("nome"));
				contato.setIdade(rst.getInt("idade"));
				contato.setTelefone(rst.getString("telefone"));
				contatos.add(contato);
			}
		}
		return contatos;
		

		}
	
	@Override
	public void inserir(Contato entidade) throws IOException, SQLException {
			try(Connection connection =  ConnectionsFactory.createConnection()){
			PreparedStatement pstm = connection.prepareStatement("INSERT INTO contatos (nome, idade, telefone) " 
			+ " VALUES (?, ?, ?) ");
			
			pstm.setString(1, entidade.getNome());
			pstm.setInt(2, entidade.getIdade());
			pstm.setString(3, entidade.getTelefone());
			pstm.execute();
			
			
//				transformaResultSetEmContato(contatos, pstm);
			} catch (Exception e) {
				throw new RuntimeException("Erro ao buscar contatos no banco de dados.");
			}
//			return entidade;

		}
	
	

	@Override
	public void atualizar(Contato entidade) {
		
			try(Connection connection =  ConnectionsFactory.createConnection()){
			PreparedStatement stm = connection.prepareStatement("UPDATE contatos SET nome = ?, idade = ?, telefone = ? WHERE (id = ?)");
			
			stm.setString(1, entidade.getNome());
			stm.setInt(2, entidade.getIdade());
			stm.setString(3, entidade.getTelefone());
			stm.setInt(4, entidade.getId());
			stm.execute();
			
			
			} catch (Exception e) {
				throw new RuntimeException("Erro ao atualizar o contato no banco de dados.");
			}

		}


	@Override
	public void excluir(Contato entidade) {
			try(Connection connection =  ConnectionsFactory.createConnection()){
			PreparedStatement stm = connection.prepareStatement("DELETE FROM contatos WHERE (id  = ?)");
			
			stm.setInt(1, entidade.getId());
			stm.execute();
			} catch (Exception e) {
				throw new RuntimeException("Erro ao excluir o contato no banco de dados.");
			}
		}
	

}
