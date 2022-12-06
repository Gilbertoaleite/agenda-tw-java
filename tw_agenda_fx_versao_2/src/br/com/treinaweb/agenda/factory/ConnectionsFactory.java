package br.com.treinaweb.agenda.factory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionsFactory {

	public static Connection createConnection() throws Exception {
		 
		InputStream is = ConnectionsFactory.class
				.getClassLoader().getResourceAsStream("application.properties");
		if(is == null) {
			throw new FileNotFoundException("O arquivo de configuração do banco de dados não foi encontrado.");
		}
		
			
		Properties props = new Properties();
		props.load(is);
		Connection connection = DriverManager.getConnection(
				props.getProperty("urlConnection"),
				props.getProperty("userConnection"),
				props.getProperty( "senhaConnection"));
		return connection;
		
	}
}
