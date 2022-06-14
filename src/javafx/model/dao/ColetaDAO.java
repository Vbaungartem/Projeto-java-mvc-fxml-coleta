/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.dao;

import javafx.model.domain.Coleta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Baungartem
 */
public class ColetaDAO {
    
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Coleta coleta) {
     String sql = "INSERT INTO coleta(origem_leite, litragem, pesagem_entrada, nota_coleta, repasse_valor_litro, motorista_id, tanque_id, data) VALUES(?,?,?,?,?,?,?,?)";
     try {
         PreparedStatement stmt = connection.prepareStatement(sql);
         stmt.setString(1, coleta.getOrigemLeite());
         stmt.setInt(2, coleta.getLitragem());
         stmt.setInt(3, coleta.getPesagem());
         stmt.setString(4, coleta.getNotaColeta());
         stmt.setFloat(5, coleta.getValorRepasse());
         stmt.setInt(6, coleta.getMotoristaId());
         stmt.setInt(7, coleta.getTanqueId());
         coleta.setData(LocalDate.now());
         stmt.setDate(8, Date.valueOf(coleta.getData()));
         stmt.execute();
         return true;
     } catch (SQLException ex) {
         Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
         return false;
     }
 }

    public boolean alterar(Coleta coleta) {
        String sql = "UPDATE coleta SET origem_leite=?, litragem=?, pesagem_entrada=?, nota_coleta=?, repasse_valor_litro=?, motorista_id=?, tanque_id=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, coleta.getOrigemLeite());
            stmt.setInt(2, coleta.getLitragem());
            stmt.setInt(3, coleta.getPesagem());
            stmt.setString(4, coleta.getNotaColeta());
            stmt.setFloat(5, coleta.getValorRepasse());
            stmt.setInt(6, coleta.getMotoristaId());
            stmt.setInt(7, coleta.getTanqueId());
            stmt.setInt(8, coleta.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Coleta coleta) {
        String sql = "DELETE FROM coleta WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, coleta.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Coleta> listar() {
        
        String sql = "SELECT * FROM coleta";
        List<Coleta> list = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Coleta coleta = new Coleta();
                coleta.setId(resultado.getInt("id"));
                coleta.setOrigemLeite(resultado.getString("origem_leite"));
                coleta.setLitragem(resultado.getInt("litragem"));
                coleta.setPesagem(resultado.getInt("pesagem_entrada"));
                coleta.setNotaColeta(resultado.getString("nota_coleta"));
                coleta.setValorRepasse(resultado.getFloat("repasse_valor_litro"));
                coleta.setMotoristaId(resultado.getInt("motorista_id"));
                coleta.setTanqueId(resultado.getInt("tanque_id"));
                list.add(coleta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; 
    }
    public Coleta buscar(int id){
        String sql = "SELECT * FROM coleta WHERE id =?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                Coleta coleta = new Coleta();
                coleta.setId(resultado.getInt("id"));
                coleta.setOrigemLeite(resultado.getString("origem_leite"));
                coleta.setLitragem(resultado.getInt("litragem"));
                coleta.setPesagem(resultado.getInt("pesagem_entrada"));
                coleta.setNotaColeta(resultado.getString("nota_coleta"));
                coleta.setValorRepasse(resultado.getFloat("repasse_valor_litro"));
                coleta.setMotoristaId(resultado.getInt("motorista_id"));
                coleta.setTanqueId(resultado.getInt("tanque_id"));
                return coleta;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        public Map<Integer, ArrayList> listarQuantidadeColetasPorMes() {
        String sql = "SELECT COUNT(id), EXTRACT(year FROM data) AS ano, EXTRACT(month FROM data) AS mes FROM coleta GROUP BY ano, mes ORDER BY ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
