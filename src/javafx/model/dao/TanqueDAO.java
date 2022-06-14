package javafx.model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Tanque;

/**
 * @author Baungartem
 */
public class TanqueDAO {
        
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public boolean inserir(Tanque tanque) {
     String sql = "INSERT INTO tanque(nome_tanque, capacidade) VALUES(?,?)";
     try {
         PreparedStatement stmt = connection.prepareStatement(sql);
         stmt.setString(1, tanque.getNomeTanque());
         stmt.setInt(2, tanque.getCapacidade());
         stmt.execute();
         return true;
     } catch (SQLException ex) {
         Logger.getLogger(TanqueDAO.class.getName()).log(Level.SEVERE, null, ex);
         return false;
     }
 }
    public boolean alterar(Tanque tanque) {
        String sql = "UPDATE tanque SET nome_tanque=?, capacidade=? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tanque.getNomeTanque());
            stmt.setInt(2, tanque.getCapacidade());
            stmt.setInt(3, tanque.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TanqueDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean remover(Tanque tanque) {
        String sql = "DELETE FROM tanque WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tanque.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TanqueDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public List<Tanque> listar() {
        String sql = "SELECT * FROM tanque";
        List<Tanque> list = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Tanque tanque = new Tanque();
                tanque.setId(resultado.getInt("id"));
                tanque.setNomeTanque(resultado.getString("nome_tanque"));
                tanque.setCapacidade(resultado.getInt("capacidade"));
                list.add(tanque);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TanqueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list; 
    }
}
