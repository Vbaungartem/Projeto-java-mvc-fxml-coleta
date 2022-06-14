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
import javafx.model.dao.TanqueDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Coleta;
import javafx.model.domain.Motorista;
import javafx.model.domain.Tanque;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Baungartem
 */
public class FXMLRegistroColetaController implements Initializable {

    @FXML
    private TableView<Coleta> tableViewColeta;
    @FXML
    private TableColumn<Coleta, String> tableColumnColetaOrigem;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnColetaLitragem;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnColetaPesagem;
    @FXML
    private TableColumn<Coleta, String> tableColumnColetaNota;
    @FXML
    private TableColumn<Coleta, Float> tableColumnColetaRemuneracao;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnColetaMotorista;
    @FXML
    private TableColumn<Coleta, Integer> tableColumnColetaTanque;
    @FXML
    private TextField textFieldColetaOrigem;
    @FXML
    private TextField textFieldColetaLitragem;
    @FXML
    private TextField textFieldColetaPesagem;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    private final ColetaDAO coletaDAO = new ColetaDAO();
    private final TanqueDAO tanqueDAO = new TanqueDAO();
    private final MotoristaDAO motoristaDAO = new MotoristaDAO();
    
    private List<Coleta> listColeta = new ArrayList<>();
    private List<Motorista> listMotorista = new ArrayList<>();
    private List<Tanque> listTanque = new ArrayList<>();
    
    private ObservableList<Motorista> observableListMotorista; 
    private ObservableList<Coleta> observableListColeta;
    private ObservableList<Tanque> observableListTanque; 
    @FXML
    private ComboBox<Tanque> comboBoxTanque;
    @FXML
    private ComboBox<Motorista> comboBoxMotorista;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnColetaOrigem.setCellValueFactory(new PropertyValueFactory<>("origemLeite"));
        tableColumnColetaLitragem.setCellValueFactory(new PropertyValueFactory<>("litragem"));
        tableColumnColetaPesagem.setCellValueFactory(new PropertyValueFactory<>("pesagem"));
        tableColumnColetaNota.setCellValueFactory(new PropertyValueFactory<>("notaColeta"));
        tableColumnColetaRemuneracao.setCellValueFactory(new PropertyValueFactory<>("valorRepasse"));
        tableColumnColetaMotorista.setCellValueFactory(new PropertyValueFactory<>("motoristaId"));
        tableColumnColetaTanque.setCellValueFactory(new PropertyValueFactory<>("tanqueId"));
        
        coletaDAO.setConnection(connection);
        motoristaDAO.setConnection(connection);
        tanqueDAO.setConnection(connection);
        carregarTableViewColeta();
        carregarComboBoxMotorista();
        carregarComboBoxTanque();
       
        /**
         * Listener
         */
        tableViewColeta.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewColeta(newValue));
    }    
    private void carregarTableViewColeta() {
        listColeta = coletaDAO.listar();
        observableListColeta = FXCollections.observableArrayList(listColeta);
        tableViewColeta.setItems(observableListColeta);       
    }
    private void selecionarItemTableViewColeta(Coleta coleta) {
        if (coleta != null) {
            textFieldColetaOrigem.setText(String.valueOf(coleta.getOrigemLeite()));
            textFieldColetaLitragem.setText(String.valueOf(coleta.getLitragem()));
            textFieldColetaPesagem.setText(String.valueOf(coleta.getPesagem()));      
        } else {
            textFieldColetaOrigem.setText("");
            textFieldColetaLitragem.setText("");
            textFieldColetaPesagem.setText("");  
        }
    }
    @FXML
    public void handleButtonInserir() throws IOException {
        Coleta coleta = new Coleta();

        if(validarEntradaDeDados()){
            
            coleta.setOrigemLeite(textFieldColetaOrigem.getText());
            coleta.setLitragem(Integer.parseInt(textFieldColetaLitragem.getText()));
            coleta.setPesagem(Integer.parseInt(textFieldColetaPesagem.getText()));
            Motorista motorista = comboBoxMotorista.getSelectionModel().getSelectedItem();
            coleta.setMotoristaId(motorista.getId());
            Tanque tanque = comboBoxTanque.getSelectionModel().getSelectedItem();
            coleta.setTanqueId(tanque.getId());
            
            if(verificarCapacidadeArmazenamento(coleta, tanque, 1)){
                coletaDAO.inserir(coleta);
                carregarTableViewColeta();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Capacidade Excedida!");
                alert.setContentText("O tanque que você selecionou possui capacidade menor do que o valor que deseja inserir!");
                alert.show();
            }
        }
    }
    @FXML
    public void handleButtonAlterar() throws IOException, SQLException {
        int caseTrigger = 0;
        connection.setAutoCommit(false);
        Coleta coleta = tableViewColeta.getSelectionModel().getSelectedItem();//Obtendo coleta selecionada
        
        if (coleta != null) {
            Coleta coletaOld = coletaDAO.buscar(coleta.getId());
            Motorista novoMotorista = comboBoxMotorista.getSelectionModel().getSelectedItem();
            Motorista antigoMotorista = motoristaDAO.buscar(coletaOld.getMotoristaId());
            Tanque tanque = comboBoxTanque.getSelectionModel().getSelectedItem();

            if (novoMotorista != null && tanque != null) {
                float novoSaldo;
                /* RETIRA SALDO DO MOTORISTA ANTIGO*/
                novoSaldo = antigoMotorista.getSaldo();
                novoSaldo -= coletaOld.getValorRepasse();
                antigoMotorista.setSaldo(novoSaldo);

                /* ALTERA OBJETO COLETA E MOTORISTA NOVOS*/
                coleta.setOrigemLeite(textFieldColetaOrigem.getText());
                coleta.setLitragem(Integer.parseInt(textFieldColetaLitragem.getText()));
                coleta.setPesagem(Integer.parseInt(textFieldColetaPesagem.getText()));
                coleta.setMotoristaId(novoMotorista.getId());
                coleta.setTanqueId(tanque.getId());
                if (verificarCapacidadeArmazenamento(coleta, tanque, 2)) {
                    System.out.println(verificarCapacidadeArmazenamento(coleta, tanque, 2));
                    novoSaldo = coleta.getValorRepasse();
                    novoMotorista.setSaldo(novoSaldo);
                    
                    /*ALTERANDO REMUNERAÇÃO EM FUNÇÃO DA LITRAGEM*/
                    if(coleta.getLitragem() != coletaOld.getLitragem()){
                        switch(coleta.getNotaColeta()){
                            case "A":
                                float valor = coleta.getLitragem();
                                valor *= 3;
                                coleta.setValorRepasse(valor);
                                novoMotorista.setSaldo(novoMotorista.getSaldo() + valor);
                                break;
                            case "B":
                                valor = coleta.getLitragem();
                                valor *= 2.25;
                                coleta.setValorRepasse(valor);
                                novoMotorista.setSaldo(novoMotorista.getSaldo() + valor);
                                break;
                            case "C":
                                valor = coleta.getLitragem();
                                valor *= 1.50;
                                coleta.setValorRepasse(valor);
                                novoMotorista.setSaldo(novoMotorista.getSaldo() + valor);
                                break;
                            case "D":
                                valor = coleta.getLitragem();
                                valor *= 0.75;
                                coleta.setValorRepasse(valor);
                                novoMotorista.setSaldo(novoMotorista.getSaldo() + valor);
                                break;
                            default: 
                                break;
                        }
                    }
                    /* ALTERAÇÃO NO BANCO*/
                    motoristaDAO.alterar(novoMotorista);
                    motoristaDAO.alterar(antigoMotorista);
                    coletaDAO.alterar(coleta);
                    connection.commit();
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro, quantidade de Leite excede a capacidade do Tanque!");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro! Selecione um Motorista e um Tanque!");
                alert.show();
            }
            carregarTableViewColeta();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma coleta na Tabela!");
            alert.show();
        }
    }
    @FXML
    public void handleButtonRemover() throws IOException, SQLException {
        connection.setAutoCommit(false);
        
        Coleta coleta = tableViewColeta.getSelectionModel().getSelectedItem();
        if (coleta != null) {
            Motorista motorista = motoristaDAO.buscar(coleta.getMotoristaId());
            motorista.setSaldo(motorista.getSaldo() - coleta.getValorRepasse());
            
            motoristaDAO.alterar(motorista);
            coletaDAO.remover(coleta);
            connection.commit();
            
            carregarTableViewColeta();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma coleta na Tabela!");
            alert.show();
        }
    }
    
    public void carregarComboBoxMotorista() {
        
        listMotorista = motoristaDAO.listar();
        observableListMotorista = FXCollections.observableArrayList(listMotorista);
        comboBoxMotorista.setItems(observableListMotorista);
    }
    private boolean validarEntradaDeDados(){
        String errorMessage = "";

        if (textFieldColetaOrigem.getText() == null || textFieldColetaOrigem.getText().length() == 0) {
            errorMessage += "Origem do leite inválida!\n";
        }
        if (textFieldColetaLitragem.getText() == null || textFieldColetaLitragem.getText().length() == 0) {
            errorMessage += "Litragem inválida!\n";
        }
        if (textFieldColetaPesagem.getText() == null || textFieldColetaPesagem.getText().length() == 0) {
            errorMessage += "Pesagem inválida!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Registro");
            alert.setHeaderText("Campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    private void carregarComboBoxTanque() {
    
        listTanque = tanqueDAO.listar();
        observableListTanque = FXCollections.observableArrayList(listTanque);
        comboBoxTanque.setItems(observableListTanque);
    }

    private boolean verificarCapacidadeArmazenamento(Coleta coleta, Tanque tanque, int tipoOperacao) {
        listColeta = coletaDAO.listar();
        int limit = tanque.getCapacidade();
        int count = 0;
        if(tipoOperacao == 2){
            Coleta coletaOld = coletaDAO.buscar(coleta.getId());
            int alter =  coleta.getLitragem();
            alter -= coletaOld.getLitragem();
            count = listColeta.stream().filter((coletass) -> (coletass.getTanqueId() == tanque.getId())).map((coletass) -> coletass.getLitragem()).reduce(count, Integer::sum);
            if(count + alter <= limit){
                return true;
            }else{
                return false;
            }
        }else{
            count = listColeta.stream().filter((coletas) -> (coletas.getTanqueId() == tanque.getId())).map((coletas) -> coletas.getLitragem()).reduce(count, Integer::sum);
            System.out.println(count);
            return count + coleta.getLitragem() <= limit; 
        }
    }
}
