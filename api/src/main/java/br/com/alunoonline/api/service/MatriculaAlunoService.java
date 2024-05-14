package br.com.alunoonline.api.service;

import br.com.alunoonline.api.Enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.alunoonline.api.dtos.DisciplinasAlunoResponse;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatriculaAlunoService {

    public static final double GRADE_AVG_TO_APPROVE = 7.0;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;
    public void create(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void updateGrades(Long matriculaAlunoId, AtualizarNotasRequest atualizarNotasRequest){
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaAlunoId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula não encontrada.")
        );
        updateStudentGrades(matriculaAluno, atualizarNotasRequest);
        updateStudentStatus(matriculaAluno);

        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void updateStudentGrades(MatriculaAluno matriculaAluno, AtualizarNotasRequest atualizarNotasRequest){
        if (atualizarNotasRequest.getGrade1() != null){
            matriculaAluno.setGrade1(atualizarNotasRequest.getGrade1());
        }

        if (atualizarNotasRequest.getGrade2() != null){
            matriculaAluno.setGrade2(atualizarNotasRequest.getGrade2());
        }
    }

    public void updateStudentStatus(MatriculaAluno matriculaAluno){
        Double nota1 = matriculaAluno.getGrade1();
        Double nota2 = matriculaAluno.getGrade2();

        if (nota1 != null && nota2 != null){
            double average = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(average >= GRADE_AVG_TO_APPROVE ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }
    }

    public void updateStatusToBreak(Long matriculaId){
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(matriculaId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula não encontrada.")
        );

        if (!MatriculaAlunoStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possivel trancar uma matricula com o status MATRICULADO");
        }
        changeStatus(matriculaAluno, MatriculaAlunoStatusEnum.TRANCADO);
    }

    public void changeStatus(MatriculaAluno matriculaAluno, MatriculaAlunoStatusEnum matriculaAlunoStatusEnum){
        matriculaAluno.setStatus(matriculaAlunoStatusEnum);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public HistoricoAlunoResponse getAcademicTranscript(Long studentId){
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findByStudentId(studentId);

        if (matriculasDoAluno.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Esse aluno não possui matriculas");
        }

        HistoricoAlunoResponse historico = new HistoricoAlunoResponse();
        historico.setStudentName(matriculasDoAluno.get(0).getStudent().getName());
        historico.setStudentEmail(matriculasDoAluno.get(0).getStudent().getEmail());

        List<DisciplinasAlunoResponse> disciplinasList = new ArrayList<>();

        for (MatriculaAluno matricula : matriculasDoAluno) {
            DisciplinasAlunoResponse disciplinasAlunoResponse = new DisciplinasAlunoResponse();
            disciplinasAlunoResponse.setSubjectName(matricula.getSubject().getNome());
            disciplinasAlunoResponse.setProfessorName(matricula.getSubject().getProfessor().getName());
            disciplinasAlunoResponse.setGrade1(matricula.getGrade1());
            disciplinasAlunoResponse.setGrade2(matricula.getGrade2());

            //FAZER CALCULO DA MEDIA EM OUTRO METODO
            if (matricula.getGrade1() != null && matricula.getGrade2() != null){
                disciplinasAlunoResponse.setAverage((matricula.getGrade1() + matricula.getGrade2()) / 2);
            } else {
                disciplinasAlunoResponse.setAverage(null);
            }

            disciplinasAlunoResponse.setStatus(matricula.getStatus());
            disciplinasList.add(disciplinasAlunoResponse);
        }

        historico.setStudentSubjectsResponseList(disciplinasList);

        return historico;
    }

//    public List<MatriculaAluno> findAll(){
//        return matriculaAlunoRepository.findAll();
//    }
//
//    public Optional<MatriculaAluno> findById(Long id){
//        return matriculaAlunoRepository.findById(id);
//    }
//
//    public void update(Long id, MatriculaAluno matriculaAluno){
//        Optional<MatriculaAluno>matriculaAlunoFromDb = findById(id);
//
//        if(matriculaAlunoFromDb.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula nao encontrada no banco de dados");
//        }
//
//        MatriculaAluno matriculaAlunoUpdated = matriculaAlunoFromDb.get();
//
//        matriculaAlunoUpdated.setNota1(matriculaAluno.getNota1());
//        matriculaAlunoUpdated.setNota2(matriculaAluno.getNota2());
//        matriculaAlunoUpdated.setStatus(matriculaAluno.getStatus());
//
//        matriculaAlunoRepository.save(matriculaAlunoUpdated);
//    }
//
//    public void deleteById(Long id){
//        matriculaAlunoRepository.deleteById(id);
//    }
}
