package br.com.alunoonline.api.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistoricoAlunoResponse {

    private String nameAluno;
    private String emailAluno;
    private List<DisciplinasAlunoResponse> disciplinasAlunoResponseList;
}
