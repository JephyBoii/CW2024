package com.example.demo;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest extends ApplicationTest {

    /*
    @Override
    public void start(Stage stage) {

    }
    */

    @Test
    void incrementKillCount() {
        UserPlane plane = new UserPlane(1);
        plane.incrementKillCount();
        assertEquals(1,plane.getNumberOfKills());
    }

    @Test
    void getNumberOfKills() {
        UserPlane plane = new UserPlane(1);
        int n = 0;
        for (int i = 0; i < 10; i++) {
            plane.incrementKillCount();
            n++;
        }
        assertEquals(n,plane.getNumberOfKills());
    }

    @Test
    void moveUp() {
        UserPlane plane = new UserPlane(1);
        plane.moveUp();
        plane.updateActor();
        assertEquals(-8, plane.getTranslateY());
    }

}