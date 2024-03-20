package br.com.alunoonline.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;

    public void setName(String x){
        this.name = x;
    }
    public String getName(){
        return this.name;
    }
    public void setEmail(String x){
        this.email = x;
    }
    public String getEmail(){
        return this.email;
    }
}