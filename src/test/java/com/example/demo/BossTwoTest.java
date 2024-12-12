package com.example.demo;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class BossTwoTest  extends ApplicationTest {

    @Test
    void takeDamage() {
        BossTwo bossTwo = new BossTwo();
        int initial = bossTwo.getHealth();
        bossTwo.takeDamage();
        assertTrue(initial>bossTwo.getHealth());
    }

}