package com.ahsgaming.superrummy.tests;

import com.ahsgaming.superrummy.RummyGame;
import com.ahsgaming.superrummy.cards.Book;
import com.ahsgaming.superrummy.cards.Card;
import com.ahsgaming.superrummy.cards.Suits;
import com.ahsgaming.superrummy.cards.Values;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * super-rummy
 * (c) 2013 Jami Couch
 * Created on 7/28/13 by jami
 * ahsgaming.com
 */
public class BookTest extends RummyGame {
    public static final String LOG = "BookTest";
    int joker = Values.JOKER.ordinal();

    TestSuite testSuite;

    @Override
    public void create() {
        testSuite = new TestSuite(true);

        super.create();
        testCanAdd();
        testAddCard();
        testCanSwap();
        testSwap();
        testGetValue();
        testGetSuit();
        testIsValid();

        testSuite.report();
        Gdx.app.exit();
    }

    public void testCanAdd() {
        Gdx.app.log(LOG, "--testCanAdd()--");

        for (int i = 0; i < 14; i++) {
            Book b = new Book("", null);
            b.addCard(new Card(Values.values()[i], Suits.CLUBS));
            for (int j = 0; j < 14; j++) {
                testSuite.assertEquals((i == j || i == 0 || j == 0), b.canAdd(new Card(Values.values()[j], Suits.CLUBS)));
            }
        }
    }

    public void testAddCard() {
        Gdx.app.log(LOG, "--testAddCard()--");

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                Book b = new Book("", null);
                b.addCard(new Card(Values.values()[i], Suits.CLUBS));
                testSuite.assertEquals(1, b.getCards().size);
                b.addCard(new Card(Values.values()[j], Suits.CLUBS));
                testSuite.assertEquals((i == j || i == 0 || j == 0) ? 2 : 1, b.getCards().size);
            }
        }
    }

    public void testCanSwap() {
        Gdx.app.log(LOG, "--testCanSwap()--");

        for (int i=0; i<14; i++) {
            for (int j=0; j<14; j++) {
                for (int k=0; k<14; k++) {
                    Book b = new Book("", null);
                    Card ic = new Card(Values.values()[i], Suits.CLUBS);
                    b.addCard(ic);

                    Card jc = new Card(Values.values()[j], Suits.DIAMONDS);
                    b.addCard(jc);

                    Card kc = new Card(Values.values()[k], Suits.SPADES);
                    b.addCard(kc);

                    for (int l=0; l<14; l++) {
                        Card lc = new Card(Values.values()[l], Suits.HEARTS);
                        // test swap for i
                        testSuite.assertEquals(i == joker && l != joker && lc.getValue() == b.getValue(), b.canSwap(lc, ic));

                        // test swap for j
                        testSuite.assertEquals(j == joker && l != joker && lc.getValue() == b.getValue(), b.canSwap(lc, jc));

                        // test swap for k
                        testSuite.assertEquals(k == joker && l != joker && lc.getValue() == b.getValue(), b.canSwap(lc, kc));
                    }
                }
            }
        }
    }

    public void testSwap() {
        Gdx.app.log(LOG, "--testSwap()--");

        for (int i=0; i<14; i++) {
            for (int j=0; j<14; j++) {
                for (int k=0; k<14; k++) {
                    Book b = new Book("", null);
                    Card ic = new Card(Values.values()[i], Suits.CLUBS);
                    b.addCard(ic);

                    Card jc = new Card(Values.values()[j], Suits.DIAMONDS);
                    b.addCard(jc);

                    Card kc = new Card(Values.values()[k], Suits.SPADES);
                    b.addCard(kc);

                    for (int l=0; l<14; l++) {
                        Card lc = new Card(Values.values()[l], Suits.HEARTS);

                        if (b.canSwap(lc, ic)) {
                            Card out = b.swap(lc, ic);
                            testSuite.assertEquals(ic, out);
                            testSuite.assertMeldContains(b, lc);
                            b.removeCard(lc);
                            b.addCard(ic);
                        } else {
                            Card out = b.swap(lc, ic);
                            testSuite.assertMeldNotContains(b, lc);
                        }

                        if (b.canSwap(lc, jc)) {
                            Card out = b.swap(lc, jc);
                            testSuite.assertEquals(jc, out);
                            testSuite.assertMeldContains(b, lc);
                            b.removeCard(lc);
                            b.addCard(jc);
                        } else {
                            Card out = b.swap(lc, jc);
                            testSuite.assertMeldNotContains(b, lc);
                        }

                        if (b.canSwap(lc, kc)) {
                            Card out = b.swap(lc, kc);
                            testSuite.assertEquals(kc, out);
                            testSuite.assertMeldContains(b, lc);
                            b.removeCard(lc);
                            b.addCard(kc);
                        } else {
                            Card out = b.swap(lc, kc);
                            testSuite.assertMeldNotContains(b, lc);
                        }
                    }
                }
            }
        }
    }

    public void testGetValue() {
        Gdx.app.log(LOG, "--testGetValue()--");
        Book b = new Book("", null);

        testSuite.assertEquals(Values.NONE, b.getValue());

        b.addCard(new Card(Values.JOKER, Suits.NONE));

        testSuite.assertEquals(Values.NONE, b.getValue());

        for (int i = 1; i <= 13; i ++) {
            b = new Book("", null);

            b.addCard(new Card(Values.values()[i], Suits.CLUBS));

            testSuite.assertEquals(Values.values()[i], b.getValue());
        }


    }

    public void testGetSuit() {
        Gdx.app.log(LOG, "--testGetSuit()--");
        for (int i=0; i<14; i++) {
            for (int j=0; j<4; j++) {
                Book b = new Book("", null);
                b.addCard(new Card(Values.values()[i], Suits.values()[j]));
                testSuite.assertEquals(Suits.NONE, b.getSuit());
            }
        }
    }

    public void testIsValid() {
        Gdx.app.log(LOG, "--testIsValid()--");

        for (int i=0; i<14; i++) {
            for (int j=0; j<14; j++) {
                for (int k=0; k<14; k++) {
                    Book b = new Book("", null);
                    b.addCard(new Card(Values.values()[i], Suits.CLUBS));
                    testSuite.assertEquals(false, b.isValid());

                    b.addCard(new Card(Values.values()[j], Suits.DIAMONDS));
                    testSuite.assertEquals(false, b.isValid());

                    b.addCard(new Card(Values.values()[k], Suits.SPADES));
                    testSuite.assertEquals(
                            (
                                    (i == j || i == joker || j == joker) &&
                                            (j == k || j == joker || k == joker) &&
                                            (i == k || i == joker || k == joker)
                            ),
                            b.isValid());
                }
            }
        }
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Super Rummy (c) 2013 Jami Couch";
        cfg.useGL20 = false;
        cfg.width = 1280;
        cfg.height = 768;
        cfg.fullscreen = false;
        cfg.resizable = true;

        new LwjglApplication(new BookTest(), cfg);
    }
}
