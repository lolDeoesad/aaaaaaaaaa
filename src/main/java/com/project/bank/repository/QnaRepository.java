package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.domain.Qna;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer>{
}
