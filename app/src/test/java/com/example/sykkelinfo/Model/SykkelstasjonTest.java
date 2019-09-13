package com.example.sykkelinfo.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class SykkelstasjonTest {

    @Test
    public void getName() {
       String input = "Per";
       String output;
       String expected = "Per";

       Sykkelstasjon s1 = new Sykkelstasjon(input);
       output = s1.getName();
       assertEquals(expected, output);
    }

}