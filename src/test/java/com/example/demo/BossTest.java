package com.example.demo;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class BossTest extends ApplicationTest{

    @Test
    void takeDamage() {
        Boss boss = new Boss();
        int initialHealth = boss.getHealth();
        boss.updateActor();
        boss.takeDamage();
        assertEquals(boss.checkShield(), initialHealth == boss.getHealth());
    }

    @Test
    void updateShield() {
        Boss boss = new Boss();
        boss.activateShield();
        int n = 0;
        while (boss.checkShield()) {
            boss.updateActor();
            n++;
        }
        assertEquals(!(n == 100),boss.checkShield());
    }

}