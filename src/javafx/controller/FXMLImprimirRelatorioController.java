/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Baungartem
 */
public class FXMLImprimirRelatorioController implements Initializable {

    @FXML
    private Button buttonGerarRelatorio;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void handleButtonImprimir() throws JRException{
        
        URL url = getClass().getResource("/javafx/relatorios/relatorioColetas.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        System.out.println("teste");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setVisible(true);
    }
}
