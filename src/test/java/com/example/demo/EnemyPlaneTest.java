package com.example.demo;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest extends ApplicationTest{

    @Test
    void updatePosition() {
        EnemyPlane plane = new EnemyPlane(1300, 500, -120);
        while (plane.getTranslateX() == 0) {
            plane.updateActor();
        }
        assertEquals(-2, plane.getTranslateX());
    }

    @Test
    void takeDamage() {
        EnemyPlane plane = new EnemyPlane(1300, 500, -120);
        plane.takeDamage();
        assertFalse(plane.isDestroyed());
    }

    @Test
    void takeTwoDamage() {
        EnemyPlane plane = new EnemyPlane(1300, 500, -120);
        plane.takeDamage();
        plane.takeDamage();
        assertTrue(plane.isDestroyed());
    }

}