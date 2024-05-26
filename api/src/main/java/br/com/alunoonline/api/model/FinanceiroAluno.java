package br.com.alunoonline.api.model;

import br.com.alunoonline.api.Enums.FinanceiroStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FinanceiroAluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Aluno student;

    private Double discount;

    private Integer dueDate;

    @Enumerated(EnumType.STRING)
    private FinanceiroStatusEnum status;
}
