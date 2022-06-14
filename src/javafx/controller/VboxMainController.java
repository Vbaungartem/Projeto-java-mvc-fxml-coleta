package javafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Baungartem
 */
public class VboxMainController implements Initializable {

    @FXML
    private MenuItem menuItemRegistroMotoristas;
    @FXML
    private MenuItem menuItemRegistroColeta;
    @FXML
    private MenuItem menuItemRegistroAvaliacao;
    @FXML
    private AnchorPane anchorPane;  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    @FXML
    private void handlemenuItemRegistroMotoristas(ActionEvent event) throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRegistroMotoristas.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    private void handleMenuItemRegistroColeta(ActionEvent event) throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRegistroColeta.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    private void handleMenuItemRegistroAvaliacao(ActionEvent event) throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLRegistroAvaliacao.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemGraficosColetasPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLGraficosColetasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemImprimirRelatorio() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/view/FXMLImprimirRelatorio.fxml"));
        anchorPane.getChildren().setAll(a);
    }
}
