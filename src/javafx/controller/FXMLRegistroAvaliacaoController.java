/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.ColetaDAO;
import javafx.model.dao.MotoristaDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Coleta;
import javafx.model.domain.Motorista;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author vitorio.silva
 */
public class FXMLRegistroAvaliacaoController implements Initializable {

    @FXML
    private TableView<Coleta> tableViewAvaliacao;
    @FXML
    private TextField textFieldNotaColeta;
    @FXML
    private Label labelColetaId;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private TableColumn<Coleta, String> tableColumnAvaliacaoOrigem;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnAvaliacaoLitragem;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnAvaliacaoPesagem;
    @FXML
    private TableColumn<Coleta, String> tableColumnAvaliacaoNota;
    @FXML
    private TableColumn<Coleta, Float> tableColumnAvaliacaoRemuneracao;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnAvaliacaoMotorista;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnAvaliacaoTanque;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    //private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    private final ColetaDAO coletaDAO = new ColetaDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    
    private List<Coleta> listColeta = new ArrayList<>(); 
    private ObservableList<Coleta> observableListColeta;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Bom dia1");
        tableColumnAvaliacaoOrigem.setCellValueFactory(new PropertyValueFactory<>("origemLeite"));
        tableColumnAvaliacaoLitragem.setCellValueFactory(new PropertyValueFactory<>("litragem"));
        tableColumnAvaliacaoPesagem.setCellValueFactory(new PropertyValueFactory<>("pesagem"));
        tableColumnAvaliacaoNota.setCellValueFactory(new PropertyValueFactory<>("notaColeta"));
        tableColumnAvaliacaoRemuneracao.setCellValueFactory(new PropertyValueFactory<>("valorRepasse"));
        tableColumnAvaliacaoMotorista.setCellValueFactory(new PropertyValueFactory<>("motoristaId"));
        tableColumnAvaliacaoTanque.setCellValueFactory(new PropertyValueFactory<>("tanqueId"));
        
        coletaDAO.setConnection(connection);
        motoristaDAO.setConnection(connection);
        carregarTableViewAvaliacao();
        
        /**
         * Listener
         */
        tableViewAvaliacao.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewAvaliacao(newValue));
    }    
        private void carregarTableViewAvaliacao() {
        System.out.println("Bom dia2");
        listColeta = coletaDAO.listar();
        System.out.println("Bom dia3");
        observableListColeta = FXCollections.observableArrayList(listColeta);
        tableViewAvaliacao.setItems(observableListColeta);       
    }

    private void selecionarItemTableViewAvaliacao(Coleta coleta) {
        if (coleta != null) {
        textFieldNotaColeta.setText(String.valueOf(coleta.getNotaColeta()));
        labelColetaId.setText(String.valueOf(coleta.getId()));
        }else{
        textFieldNotaColeta.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException, SQLException {
        Coleta coleta = tableViewAvaliacao.getSelectionModel().getSelectedItem();
        connection.setAutoCommit(false);
        if(validarEntradaDeDados()){
            
            coleta.setNotaColeta(textFieldNotaColeta.getText());
            
            switch (coleta.getNotaColeta()){
                case "A":
                    float valor = coleta.getLitragem();
                    valor *= 3;
                    coleta.setValorRepasse(valor);
                    break;
                case "B":
                    valor = coleta.getLitragem();
                    valor *= 2.25;
                    coleta.setValorRepasse(valor);
                    break;
                case "C":
                    valor = coleta.getLitragem();
                    valor *= 1.50;
                    coleta.setValorRepasse(valor);
                    break;
                case "D":
                    valor = coleta.getLitragem();
                    valor *= 0.75;
                    coleta.setValorRepasse(valor);
                    break;
            }
            Motorista motorista = motoristaDAO.buscar(coleta.getMotoristaId());
            motorista.setSaldo(coleta.getValorRepasse() + motorista.getSaldo());
            coletaDAO.alterar(coleta);
            motoristaDAO.inserir(motorista);
            carregarTableViewAvaliacao();
            connection.commit();
        }
    }
        @FXML
    public void handleButtonAlterar() throws IOException, SQLException {
        float valor = 0;

        Coleta coleta = tableViewAvaliacao.getSelectionModel().getSelectedItem();
        
        connection.setAutoCommit(false);
        if(coleta != null && validarEntradaDeDados()){
            Motorista motorista = motoristaDAO.buscar(coleta.getMotoristaId());
            valor = coleta.getValorRepasse();
            motorista.setSaldo(motorista.getSaldo() - valor);
            coleta.setNotaColeta(textFieldNotaColeta.getText());
            
            switch (coleta.getNotaColeta()){
                case "A":
                    valor = coleta.getLitragem();
                    valor *= 3;
                    coleta.setValorRepasse(valor);
                    break;
                case "B":
                    valor = coleta.getLitragem();
                    valor *= 2.25;
                    coleta.setValorRepasse(valor);
                    break;
                case "C":
                    valor = coleta.getLitragem();
                    valor *= 1.50;
                    coleta.setValorRepasse(valor);
                    break;
                case "D":
                    valor = coleta.getLitragem();
                    valor *= 0.75;
                    coleta.setValorRepasse(valor);
                    break;
            }
            
            motorista.setSaldo(coleta.getValorRepasse() + motorista.getSaldo());
            coletaDAO.alterar(coleta);
            motoristaDAO.alterar(motorista);
            carregarTableViewAvaliacao();
            connection.commit();
        }
    }
    private boolean validarEntradaDeDados(){
        String errorMessage = "";
        
        switch (textFieldNotaColeta.getText()){
            case "A": 
                break;
            case "B":
                break;
            case "C":
                break;
            case "D":
                break;
            default:
                errorMessage += "Nota Inválida! Selecione de A a D!\n";
                break;
        }
        
        if (textFieldNotaColeta.getText() == null || textFieldNotaColeta.getText().length() == 0) {
            errorMessage += "Nota Inválida! Selecione de A a D!\n";
        }if (errorMessage.length() == 0) {
            return true;
        }else {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Registro");
            alert.setHeaderText("Campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
