package com.lorenzo.summer;

import com.lorenzo.summer.controller.SummerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SummerApplication.class)
public class ApplicationSmokeTest {

    @Autowired
    private SummerController summerController;

    @Test
    public void contextLoads() {
        assertNotNull(summerController);
    }
}
