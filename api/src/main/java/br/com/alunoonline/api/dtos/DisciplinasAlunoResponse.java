package br.com.alunoonline.api.dtos;

import br.com.alunoonline.api.Enums.MatriculaAlunoStatusEnum;
import lombok.Data;

@Data
public class DisciplinasAlunoResponse {
    private String disciplinaName;
    private Double nota1;
    private Double nota2;
    private Double media;
    private MatriculaAlunoStatusEnum status;
}
