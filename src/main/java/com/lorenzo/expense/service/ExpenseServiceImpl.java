package com.lorenzo.expense.service;

import com.lorenzo.expense.model.Expense;
import com.lorenzo.expense.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.util.StreamUtils.createStreamFromIterator;

@Service
public class ExpenseServiceImpl implements IExpenseService {

    private ExpenseRepository repository;

    public ExpenseServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Expense save(Expense toSave) {
        return repository.save(toSave);
    }

    @Override
    @Transactional
    public Expense save(Date date, String vendor, Double price, int pay_method, byte[] scan) {
        Expense expense = new Expense(date, vendor, price, pay_method, scan);
        return repository.save(expense);
    }

    @Override
    @Transactional
    public Optional<Expense> findById(int id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Collection<Expense> findAll() {
        final Iterator<Expense> iterator = repository.findAll().iterator();
        return createStreamFromIterator(iterator).collect(toList());
    }

    @Override
    @Transactional
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Expense toDelete) {
        repository.delete(toDelete);
    }

    @Override
    @Transactional
    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAll(Collection<Expense> toDelete) {
        repository.deleteAll(toDelete);
    }

    @Override
    @Transactional
    public Collection<Expense> findAllById(Collection<Integer> toFind) {
        return createStreamFromIterator(repository.findAllById(toFind).iterator()).collect(toList());
    }

    @Override
    @Transactional
    public Collection<Expense> saveAll(Collection<Expense> toSave) {
        return createStreamFromIterator(repository.saveAll(toSave).iterator()).collect(toList());
    }
}
