package br.com.alunoonline.api.dtos;

import br.com.alunoonline.api.Enums.MatriculaAlunoStatusEnum;
import lombok.Data;

@Data
public class DisciplinasAlunoResponse {
    private String subjectName;
    private String professorName;
    private Double grade1;
    private Double grade2;
    private Double average;
    private MatriculaAlunoStatusEnum status;
}
