/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.dao;

import javafx.model.domain.Motorista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Baungartem
 */
public class MotoristaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Motorista motorista) {
        String sql = "INSERT INTO motorista(nome, cpf, placa_caminhao, saldo) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getPlacaCaminhao());
            stmt.setFloat(4, motorista.getSaldo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Motorista motorista) {
        
        String sql = "UPDATE motorista SET nome=?, cpf=?, placa_caminhao=?, saldo=? WHERE id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, motorista.getNome());
            stmt.setString(2, motorista.getCpf());
            stmt.setString(3, motorista.getPlacaCaminhao());
            stmt.setFloat(4, motorista.getSaldo());
            stmt.setInt(5, motorista.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Motorista motorista) {
        String sql = "DELETE FROM motorista WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, motorista.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Motorista> listar() {
        String sql = "SELECT * FROM motorista";
        List<Motorista> list = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Motorista motorista = new Motorista();
                motorista.setId(resultado.getInt("id"));
                motorista.setNome(resultado.getString("nome"));
                motorista.setCpf(resultado.getString("cpf"));
                motorista.setPlacaCaminhao(resultado.getString("placa_caminhao"));
                motorista.setSaldo(resultado.getFloat("saldo"));
                list.add(motorista);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MotoristaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public Motorista buscar(int id){
        String sql = "SELECT * FROM motorista WHERE id =?";
        Motorista motorista = new Motorista();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                
                motorista.setId(resultado.getInt("id"));
                motorista.setNome(resultado.getString("nome"));
                motorista.setCpf(resultado.getString("cpf"));
                motorista.setPlacaCaminhao(resultado.getString("placa_caminhao"));
                motorista.setSaldo(resultado.getFloat("saldo"));
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ColetaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return motorista;
    }
}
