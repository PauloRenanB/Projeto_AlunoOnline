package br.com.alunoonline.api.service;

import br.com.alunoonline.api.Enums.FinanceiroStatusEnum;
import br.com.alunoonline.api.dtos.CriarAlunoRequest;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.model.Curso;
import br.com.alunoonline.api.model.FinanceiroAluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.repository.CursoRepository;
import br.com.alunoonline.api.repository.FinanceiroAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    FinanceiroAlunoRepository financeiroAlunoRepository;

    @Autowired
    CursoRepository cursoRepository;

    public void create(CriarAlunoRequest criarAlunoRequest){
        Curso curso = cursoRepository.findById(criarAlunoRequest.getCourseId())
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado.")
                );

        Aluno alunoSaved = alunoRepository.save(
                new Aluno(
                        null,
                        criarAlunoRequest.getName(),
                        criarAlunoRequest.getEmail(),
                        curso
                )
        );

        createFinanceiroInformation(alunoSaved, criarAlunoRequest);
    }

    public List<Aluno> findAll(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> findById(Long id){
        return alunoRepository.findById(id);
    }

    public void update(Long id, Aluno aluno){
        Optional<Aluno>alunoFromDb = findById(id);

        if (alunoFromDb.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Aluno não encontrado no banco de dados");
        }

        Aluno alunoUpdated = alunoFromDb.get();

        alunoUpdated.setName(aluno.getName());
        alunoUpdated.setEmail(aluno.getEmail());

        alunoRepository.save(alunoUpdated);
    }

    public void deleteById(Long id){
        alunoRepository.deleteById(id);
    }

    public void createFinanceiroInformation(Aluno aluno, CriarAlunoRequest criarAlunoRequest){
        FinanceiroAluno financeiroAluno = new FinanceiroAluno(
                null,
                aluno,
                criarAlunoRequest.getDiscount(),
                criarAlunoRequest.getDueDate(),
                FinanceiroStatusEnum.EM_DIA
        );

        financeiroAlunoRepository.save(financeiroAluno);
    }
}
