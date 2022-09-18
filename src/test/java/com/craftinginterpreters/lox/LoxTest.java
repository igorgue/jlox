package com.craftinginterpreters.lox;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoxTest {

    @Test
    public void testThatTrueIsTrue() {
        assertThat(true).isEqualTo(true);
    }

}

