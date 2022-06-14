/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.model.domain;

import java.io.Serializable;

/**
 * @author Baungartem
 */
public class Tanque implements Serializable{
    private int id;
    private String nomeTanque;
    private int capacidade;

    public Tanque() {      
    }
    public Tanque(int id, String nomeTanque, int capacidade){
        this.id = id;
        this.nomeTanque = nomeTanque;
        this.capacidade = capacidade;
    }
    public int getId(){
        return this.id;
    }
    public String getNomeTanque(){
        return this.nomeTanque;
    }
    public int getCapacidade(){
        return this.capacidade;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setNomeTanque(String nomeTanque){
        this.nomeTanque = nomeTanque;
    }
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
    
    @Override
    public String toString(){
        return nomeTanque;
    }
}
