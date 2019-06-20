package com.lorenzo.summer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SummerController.class)
//Tests only the web layer, not the whole context
@Profile("test")
public class SummerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IExpenseService expenseService;
    //without this mock the applicationContext won't start (the controller would lack of service dependency)

    @Test
    public void noExpensesSaved_findById_expenseNotFound() throws Exception {
        this.mockMvc.perform(get("/findById").param("expenseId", "5")).andExpect(status().isNotFound());
    }

    @Test
    public void anExpenseExists_findById_expenseReturned() throws Exception {
        //GIVEN
        final Expense expense = new Expense();
        when(expenseService.findById(5)).thenReturn(of(expense));

        //WHEN_THEN
        this.mockMvc.perform(get("/findById").param("expenseId", "5")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void anExpenseExists_deleteById_expenseDeleted() throws Exception {
        //GIVEN
        doNothing().when(expenseService).deleteById(5);

        //WHEN_THEN
        this.mockMvc.perform(get("/deleteById").param("expenseId", "5")).andExpect(status().isOk());
    }

    @Test
    public void expenseDoesNotExists_deleteById_exceptionThrown() throws Exception {
        //GIVEN
        doThrow(EmptyResultDataAccessException.class).when(expenseService).deleteById(5);

        //WHEN
        this.mockMvc.perform(get("/deleteById").param("expenseId", "5")).andExpect(status().isNotFound());
    }

    @Test
    public void findAll() throws Exception {
        //WHEN
        final List<Expense> expenses = asList(new Expense(new Date(), "VENDOR_1", 1d, 1, new byte[1]),
                new Expense(new Date(), "VENDOR_2", 2d, 2, new byte[2]),
                new Expense(new Date(), "VENDOR_3", 3d, 3, new byte[3]));
        when(expenseService.findAll()).thenReturn(expenses);

        //THEN
        this.mockMvc
                .perform(get("/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(mvcResult -> {
                    final String resultAsString = mvcResult.getResponse().getContentAsString();
                    final ObjectMapper objectMapper = new ObjectMapper();
                    Expense[] results = objectMapper.readValue(resultAsString, Expense[].class);
                    assertEquals(expenses, asList(results));
                });
    }
}