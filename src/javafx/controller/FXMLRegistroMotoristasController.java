package javafx.controller;

import java.io.IOException;
import javafx.model.dao.MotoristaDAO;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.domain.Motorista;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Baungartem
 */
public class FXMLRegistroMotoristasController implements Initializable {
    @FXML
    private TableView<Motorista> tableViewMotorista;
    @FXML
    private TableColumn<Motorista, String> tableColumnMotoristaNome;
    @FXML
    private TableColumn<Motorista, String> tableColumnMotoristaCpf;
    @FXML
    private TableColumn<Motorista, String> tableColumnMotoristaPCaminhao;
    @FXML
    private TableColumn<Motorista, Float> tableColumnMotoristaSaldo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCpf;
    @FXML
    private TextField textFieldPlacaVeiculo;
    @FXML
    private TextField textFieldSaldo;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonDeletar;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    
    private List<Motorista> listMotorista = new ArrayList<>();
    
    private ObservableList<Motorista> observableListMotorista; 
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnMotoristaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnMotoristaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnMotoristaPCaminhao.setCellValueFactory(new PropertyValueFactory<>("placaCaminhao"));
        tableColumnMotoristaSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        
        motoristaDAO.setConnection(connection);
        carregarTableViewMotorista();
        /**
         * Listener
         */
        tableViewMotorista.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewMotorista(newValue));
    }    

    private void carregarTableViewMotorista() {
        listMotorista = motoristaDAO.listar();
        observableListMotorista = FXCollections.observableArrayList(listMotorista);
        tableViewMotorista.setItems(observableListMotorista);    
    }
    
    private void selecionarItemTableViewMotorista(Motorista motorista) {
        if (motorista != null) {
            textFieldNome.setText(String.valueOf(motorista.getNome()));
            textFieldCpf.setText(String.valueOf(motorista.getCpf()));
            textFieldPlacaVeiculo.setText(String.valueOf(motorista.getPlacaCaminhao()));
            textFieldSaldo.setText(String.valueOf(motorista.getSaldo()));
        } else {
            textFieldNome.setText("");
            textFieldCpf.setText("");
            textFieldPlacaVeiculo.setText("");
            textFieldSaldo.setText("");
        }
    }
    @FXML
    public void handleButtonInserir() throws IOException {
        Motorista motorista = new Motorista();

        if(validarEntradaDeDados()){
            
            motorista.setNome(textFieldNome.getText());
            motorista.setCpf(textFieldCpf.getText());
            motorista.setPlacaCaminhao(textFieldPlacaVeiculo.getText());
            motorista.setSaldo(Float.valueOf(textFieldSaldo.getText()));
     
            motoristaDAO.inserir(motorista);
            carregarTableViewMotorista();
        }
    }
    @FXML
    public void handleButtonAlterar() throws IOException {
        Motorista motorista = tableViewMotorista.getSelectionModel().getSelectedItem();
        
        if (motorista!=null) {
            motorista.setNome(textFieldNome.getText());
            motorista.setCpf(textFieldCpf.getText());
            motorista.setPlacaCaminhao(textFieldPlacaVeiculo.getText());
            motorista.setSaldo(Float.valueOf(textFieldSaldo.getText()));
            
            motoristaDAO.alterar(motorista);
            carregarTableViewMotorista();
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um Motorista!");
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Motorista motorista = tableViewMotorista.getSelectionModel().getSelectedItem();
        if (motorista != null) {
            motoristaDAO.remover(motorista);
            carregarTableViewMotorista();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um motorista na Tabela!");
            alert.show();
        }
    }
    private boolean validarEntradaDeDados(){
        String errorMessage = "";

        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldCpf.getText() == null || textFieldCpf.getText().length() == 0) {
            errorMessage += "CPF inválido!\n";
        }
        if (textFieldPlacaVeiculo.getText() == null || textFieldPlacaVeiculo.getText().length() == 0) {
            errorMessage += "Placa inválida!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Registro");
            alert.setHeaderText("Campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    
    
    
}
