package br.com.alunoonline.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    public void setName(String x){
        this.name = x;
    }
    public String getName(){
        return this.name;
    }

    @ManyToOne
    @JoinColumn(name="professor_id")
    private Professor professor;




}
