package com.example.demo;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTwoTest extends ApplicationTest {

    @Test
    void updatePosition() {
        EnemyPlaneTwo plane = new EnemyPlaneTwo(1300, 500, -120);
        while (plane.getTranslateX() == 0) {
            plane.updateActor();
        }
        assertEquals(-1, plane.getTranslateX());
    }

    @Test
    void takeDamage() {
        EnemyPlaneTwo plane = new EnemyPlaneTwo(1300, 500, -120);
        plane.takeDamage();
        assertFalse(plane.isDestroyed());
    }

    @Test
    void takeSixDamage() {
        EnemyPlaneTwo plane = new EnemyPlaneTwo(1300, 500, -120);
        for (int i = 0; i < 6; i++) {
            plane.takeDamage();
        }
        assertTrue(plane.isDestroyed());
    }

}