package br.com.treinaweb.agenda.fx;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.treinaweb.agenda.entidades.Contato;
//import br.com.treinaweb.agenda.fx.entidades.Contat;
import br.com.treinaweb.agenda.fx.repository.impl.ContatoRepository;
import br.com.treinaweb.agenda.fx.repository.interfaces.AgendaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

	@FXML
	private TableView<Contato> tabelaContatos;

	@FXML
	private Button botaoInserir;

	@FXML
	private Button botaoAlterar;

	@FXML
	private Button botaoExcluir;

	@FXML
	private Button botaoSalvar;

	@FXML
	private Button botaoCancelar;

	@FXML
	private TextField txNome;

	@FXML
	private TextField txIdade;
	@FXML
	private TextField txTelefone;
	
	private Boolean ehInserir;
	private Contato contatoSelecionado;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.tabelaContatos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		habilitarEdicaoAgenda(false);
//		tabelaContatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contato>() {
//
//			@Override
//			public void changed(ObservableValue<? extends Contato> observable, Contato oldValue, Contato newValue) {
//				if (newValue != null) {
//					txNome.setText(newValue.getNome());
//					txIdade.setText(String.valueOf(newValue.getIdade()));
//					txTelefone.setText(newValue.getTelefone());
//				}
//			}
//		});
		
		tabelaContatos.getSelectionModel().selectedItemProperty().addListener((observador, contatoAntigo, contatoNovo)-> {
			if (contatoNovo != null) {
				txNome.setText(contatoNovo.getNome());
				txIdade.setText(String.valueOf(contatoNovo.getIdade()));
				txTelefone.setText(contatoNovo.getTelefone());
				contatoSelecionado = contatoNovo;
			}
				
		});
		preencherTabela();
	}
	
	public void botaoInserir_Action(){
		this.ehInserir = true;
		txNome.setText("");
		txIdade.setText("");
		txTelefone.setText("");
		habilitarEdicaoAgenda(true);
		
	}
	
	public void botaoAlterar_Action(){
		habilitarEdicaoAgenda(true);
		ehInserir = false;
		txNome.setText(contatoSelecionado.getNome());
		txIdade.setText(Integer.toString(contatoSelecionado.getIdade()));
		txTelefone.setText(contatoSelecionado.getTelefone());
	}
	
	public void botaoExcluir_Action(){
		Alert confirmacao = new Alert(AlertType.CONFIRMATION);
		confirmacao.setTitle("Confirmação");
		confirmacao.setHeaderText("Confirmação de exclusão do contato");
		confirmacao.setContentText("Tem certeza que deseja excluir este contato? ");
		Optional<ButtonType> resultadoConfirmacao = confirmacao.showAndWait();
		if(resultadoConfirmacao.isPresent() && resultadoConfirmacao.get() == ButtonType.OK) {
		
		
		AgendaRepository<Contato> repositoryContato  = new ContatoRepository();
		repositoryContato.excluir(contatoSelecionado);
		preencherTabela();
		tabelaContatos.getSelectionModel().selectFirst();
		}
	}
	
	public void botaoCancelar_Action() {
		habilitarEdicaoAgenda(false);
		tabelaContatos.getSelectionModel().selectFirst();
	}
	
	public void botaoSalvar_Action(){
		AgendaRepository<Contato> repositorioContato = new ContatoRepository();
		Contato contato = new Contato();
		contato.setNome(txNome.getText());
		contato.setIdade(Integer.parseInt(txIdade.getText()));
		contato.setTelefone(txTelefone.getText());
		if(ehInserir) {
			repositorioContato.inserir(contato);
		}else {
			repositorioContato.atualizar(contato);
		}
		habilitarEdicaoAgenda(false);
		preencherTabela();
		tabelaContatos.getSelectionModel().selectFirst();
		}		

	private void preencherTabela() {
		AgendaRepository<Contato> repositoryContato = new ContatoRepository();
		List<Contato> contatos = repositoryContato.selecionar();
		if (contatos.isEmpty()) {
			Contato contato = new Contato();
			contato.setNome("Teste Nome");
			contato.setIdade(22);
			contato.setTelefone("11233335552");
			contatos.add(contato);
		}

		ObservableList<Contato> contatosObservableList = FXCollections.observableArrayList(contatos);
		tabelaContatos.getItems().setAll(contatosObservableList);

	}

	private void habilitarEdicaoAgenda(Boolean edicaoHabilitado) {
		txNome.setDisable(!edicaoHabilitado);
		txIdade.setDisable(!edicaoHabilitado);
		txTelefone.setDisable(!edicaoHabilitado);
		botaoSalvar.setDisable(!edicaoHabilitado);
		botaoCancelar.setDisable(!edicaoHabilitado);
		botaoInserir.setDisable(edicaoHabilitado);
		botaoAlterar.setDisable(edicaoHabilitado);
		botaoExcluir.setDisable(edicaoHabilitado);
		tabelaContatos.setDisable(edicaoHabilitado);
	}

}
