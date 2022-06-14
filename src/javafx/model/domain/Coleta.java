
package javafx.model.domain;
import java.time.LocalDate;
import java.io.Serializable;

/**
 *
 * @author Baungartem
 */
public class Coleta implements Serializable{
    
    private int id;
    private String origemLeite;
    private int pesagem;
    private int litragem;
    private String notaColeta;
    private float valorRepasse;
    private int motoristaId;
    private int tanqueId;
    private LocalDate data;

    public Coleta() {      
    }
    
    public Coleta(int id, String origemLeite, int pesagem, String notaColeta, float valorRepasse, int motoristaId, int tanqueId, LocalDate data){
        this.id = id;
        this.origemLeite = origemLeite;
        this.pesagem = pesagem;
        this.notaColeta = notaColeta;
        this.valorRepasse = valorRepasse;
        this.motoristaId = motoristaId;
        this.tanqueId = tanqueId;
        this.data = data;
    }
    public int getId(){
        return this.id;
    }
    public int getLitragem(){
        return this.litragem;
    }
    public String getOrigemLeite(){
        return this.origemLeite;
    }
    public int getPesagem(){
        return this.pesagem;
    }    
    public String getNotaColeta(){
        return this.notaColeta;
    }
    public float getValorRepasse(){
        return this.valorRepasse;
    }
    public int getMotoristaId(){
        return this.motoristaId;
    }
    public int getTanqueId(){
        return this.tanqueId;
    }
    public LocalDate getData(){
        return this.data;
    }
    // ==================================================== \\
    
    public void setId(int id){
        this.id = id;
    }
    public void setLitragem(int litragem){
        this.litragem = litragem;
    }
    public void setOrigemLeite(String origemLeite){
        this.origemLeite = origemLeite;
    }
    public void setPesagem(int pesagem){
        this.pesagem = pesagem;
    }    
    public void setNotaColeta(String notaColeta){
        this.notaColeta = notaColeta;
    }
    public void setValorRepasse(float valorRepasse){
        this.valorRepasse = valorRepasse;
    }
    public void setMotoristaId(int motoristaId){
        this.motoristaId = motoristaId;
    }
    public void setTanqueId(int tanqueId){
        this.tanqueId = tanqueId;
    }
    public void setData(LocalDate data){
        this.data = data;
    }
    @Override
    public String toString(){
        return origemLeite;
    }
}
