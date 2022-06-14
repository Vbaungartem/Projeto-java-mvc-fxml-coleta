/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.domain;

import java.io.Serializable;

/**
 *
 * @author 20191si020
 */
public class Motorista implements Serializable{
    
    private int id;
    private String nome;
    private String cpf;
    private String placaCaminhao;
    private float saldo;
    
    
    public Motorista() {
        
    }
    public Motorista(int id, String nome, String cpf, String placaCaminhao, float saldo){
        this.id = id;
        this.nome =nome;
        this.cpf = cpf;
        this.placaCaminhao = placaCaminhao;
        this.saldo = saldo;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getNome(){
        return this.nome;
    }
    public String getCpf(){
        return this.cpf;
    }    
    public String getPlacaCaminhao(){
        return this.placaCaminhao;
    }
    public float getSaldo(){
        return this.saldo;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }    
    public void setPlacaCaminhao(String placaCaminhao){
        this.placaCaminhao = placaCaminhao;
    }
    public void setSaldo(float saldo){
        this.saldo = saldo;
    }
    @Override
    public String toString() {
        return this.nome;
    }
    
}
