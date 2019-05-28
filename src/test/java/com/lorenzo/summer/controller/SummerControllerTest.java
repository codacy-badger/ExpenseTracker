package com.lorenzo.summer.controller;

import com.lorenzo.summer.model.Expense;
import com.lorenzo.summer.service.IExpenseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Optional.of;
import static org.mockito.Mockito.when;
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
}