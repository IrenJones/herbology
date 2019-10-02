package com.example.herbology.experiments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class TestFirst {

    private int a;
    private int b;
    private int c;

    public TestFirst(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {1, 1, 2},
                {2, 1, 3}
        });
    }

    @Test
    public void test(){
        assertThat(a+b, is(c));
    }
}
