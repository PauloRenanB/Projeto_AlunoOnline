package br.com.alunoonline.api.repository;

import br.com.alunoonline.api.model.Fatura;
import br.com.alunoonline.api.model.FinanceiroAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    boolean existsByStudentFinancialAndDueDate(FinanceiroAluno studentFinancial, LocalDateTime dueDate);
}
