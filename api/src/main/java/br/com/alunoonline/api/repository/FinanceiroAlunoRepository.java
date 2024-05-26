package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.FinanceiroAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceiroAlunoRepository extends JpaRepository<FinanceiroAluno, Long> {

}
