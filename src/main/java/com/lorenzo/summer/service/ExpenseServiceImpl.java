package com.lorenzo.summer.service;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.repository.ExpenseRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import static java.util.Optional.ofNullable;
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
        final Expense expense = ofNullable(toSave).orElseThrow(IllegalArgumentException::new);
        return repository.save(expense);
    }

    @Override
    @Transactional
    public Expense save(Date date, String vendor, Double price, int pay_method, byte[] scan) {
        Expense expense = new Expense(date, vendor, price, pay_method, scan);
        return repository.save(expense);
    }

    @Override
    @Transactional
    public Expense findById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
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
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found, cannot delete");
        }
    }

    @Override
    @Transactional
    public void delete(Expense toDelete) {
        ofNullable(toDelete).orElseThrow(IllegalArgumentException::new);
        if (existsById(toDelete.getId())) {
            repository.delete(toDelete);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found, cannot delete");
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
        ofNullable(toDelete).orElseThrow(IllegalArgumentException::new);
        repository.deleteAll(toDelete);
    }

    @Override
    @Transactional
    public Collection<Expense> findAllById(Collection<Integer> toFind) {
        ofNullable(toFind).orElseThrow(IllegalArgumentException::new);
        return createStreamFromIterator(repository.findAllById(toFind).iterator()).collect(toList());
    }

    @Override
    @Transactional
    public Collection<Expense> saveAll(Collection<Expense> toSave) {
        ofNullable(toSave).orElseThrow(IllegalArgumentException::new);
        return createStreamFromIterator(repository.saveAll(toSave).iterator()).collect(toList());
    }
}
