package br.com.alunoonline.api.service;

import br.com.alunoonline.api.Enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    }

    public void updateStudentGrades(MatriculaAluno matriculaAluno, AtualizarNotasRequest atualizarNotasRequest){
        if (atualizarNotasRequest.getNota1() != null){
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }

        if (atualizarNotasRequest.getNota2() != null){
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }
    }

    public void updateStudentStatus(MatriculaAluno matriculaAluno){
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();

        if (nota1 != null && nota2 != null){
            double average = (nota1 + nota2) / 2;
            matriculaAluno.setStatus(average >= GRADE_AVG_TO_APPROVE ? MatriculaAlunoStatusEnum.APROVADO : MatriculaAlunoStatusEnum.REPROVADO);
        }
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
