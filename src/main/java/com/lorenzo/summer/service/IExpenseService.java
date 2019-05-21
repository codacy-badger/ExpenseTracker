package com.lorenzo.summer.service;

import com.lorenzo.summer.model.Expense;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface IExpenseService {

    Expense save(Expense toSave);

    Expense save(Date date, String vendor, Double price, int pay_method, byte[] scan);

    Optional<Expense> findById(int id);

    Collection<Expense> findAll();

    long count();

    void deleteById(int id);

    void delete(Expense toDelete);

    boolean existsById(int id);

    void deleteAll();

    @Transactional
    void deleteAll(Collection<Expense> toDelete);

    @Transactional
    Collection<Expense> findAllById(Collection<Integer> toFind);

    @Transactional
    Collection<Expense> saveAll(Collection<Expense> toSave);
}
