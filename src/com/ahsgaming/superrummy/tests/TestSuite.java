package com.ahsgaming.superrummy.tests;

import com.ahsgaming.superrummy.cards.Card;
import com.ahsgaming.superrummy.cards.Meld;
import com.badlogic.gdx.Gdx;

public class TestSuite {

    int passed, failed;
    boolean failedOnly;

    public TestSuite(boolean failedOnly) {
        this.failedOnly = failedOnly;
        passed = 0;
        failed = 0;
    }

    public void assertEquals(Object expected, Object actual) {
        if (expected.equals(actual)) {
            passed += 1;
            if (!failedOnly) Gdx.app.log("OK", String.format("%s == %s", expected.toString(), actual.toString()));
            return;
        }
        failed += 1;
        Gdx.app.log("**FAIL**", String.format("Expected %s, actual %s", expected.toString(), actual.toString()));
    }

    public void assertMeldContains(Meld meld, Card card) {
        for (Card c: meld.getCards()) {
            if (c == card) {
                passed ++;
                if (!failedOnly) Gdx.app.log("OK", String.format("Meld [%s] contains %s", meld.toString(), card.toString()));
                return;
            }
        }
        failed++;
        Gdx.app.log("**FAIL**", String.format("Meld [%s] does not contain %s", meld.toString(), card.toString()));
    }

    public void assertMeldNotContains(Meld meld, Card card) {
        for (Card c: meld.getCards()) {
            if (c == card) {
                failed ++;
                Gdx.app.log("**FAILED**", String.format("Meld [%s] contains %s", meld.toString(), card.toString()));
                return;
            }
        }
        passed++;
        if (!failedOnly) Gdx.app.log("OK", String.format("Meld [%s] does not contain %s", meld.toString(), card.toString()));
    }

    public void report() {
        Gdx.app.log("TestReport", String.format("Tests run: %d, failed: %d", passed + failed, failed));
    }
}