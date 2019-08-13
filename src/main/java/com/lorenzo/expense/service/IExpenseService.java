package com.lorenzo.expense.service;

import com.lorenzo.expense.model.Expense;

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

    void deleteAll(Collection<Expense> toDelete);

    Collection<Expense> findAllById(Collection<Integer> toFind);

    Collection<Expense> saveAll(Collection<Expense> toSave);
}
