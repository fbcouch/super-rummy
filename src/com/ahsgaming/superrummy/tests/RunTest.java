package com.ahsgaming.superrummy.tests;

import com.ahsgaming.superrummy.RummyGame;
import com.ahsgaming.superrummy.cards.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * super-rummy
 * (c) 2013 Jami Couch
 * Created on 7/28/13 by jami
 * ahsgaming.com
 */
public class RunTest extends RummyGame {
    public static final String LOG = "RunTest";
    int joker = Values.JOKER.ordinal();

    TestSuite testSuite;

    @Override
    public void create() {
        testSuite = new TestSuite(true);

        super.create();
        testCanAdd();
        testAddCard();
        testCanSwap();
        testGetValue();
        testGetSuit();
        testIsValid();

        testSuite.report();
        Gdx.app.exit();
    }

    public void testCanAdd() {
        Gdx.app.log(LOG, "--testCanAdd()--");

        for (int val = 0; val < 14; val++) {
            for (int suit = 0; suit < 4; suit++) {
                Run r = new Run("", null);
                Card c = new Card(Values.values()[val], Suits.values()[suit]);
                testSuite.assertEquals(true, r.canAdd(c));

                r.addCard(c);   // the run is now locked to this suit (assuming not a joker!)

                for (int cards = 0; cards < 5; cards ++) {      // going to test adding up to 5 cards
                    Card toAdd = null;
                    for (int val2 = 0; val2 < 14; val2++) {
                        for (int suit2 = 0; suit2 < 4; suit2++) {
                            Card c2 = new Card(Values.values()[val2], Suits.values()[suit2]);
                            boolean shouldAdd = false;

                            if (val2 == joker || r.getSuit() == Suits.NONE) {
                                shouldAdd = true;
                            } else {
                                if (r.getSuit().ordinal() == suit2) { // is the suit correct?
                                    if (val2 == r.getCards().first().getValue().ordinal() - 1 ||
                                            val2 == r.getCards().peek().getValue().ordinal() + 1 ||
                                            (val2 == Values.ACE.ordinal() && r.getCards().peek().getValue() == Values.KING)) {
                                        shouldAdd = true;
                                    }
                                }
                            }
                            if (shouldAdd != r.canAdd(c2)) {
                                Gdx.app.log(LOG, String.format("c1: %s; c2: %s; r: %s", c.toString(), c2.toString(), r));
                            }
                            testSuite.assertEquals(shouldAdd, r.canAdd(c2));
                            if (shouldAdd) toAdd = c2;
                        }
                    }
                    if (toAdd != null) r.addCard(toAdd);
                }
            }
        }
    }


    public void testAddCard() {
        Gdx.app.log(LOG, "--testAddCard()--");

        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                for (int k = 0; k < 4; k++) {
                    Run r = new Run("", null);
                    r.addCard(new Card(Values.values()[i], Suits.values()[k]));
                    testSuite.assertEquals(1, r.getCards().size);
                    int expected = r.getCards().size;
                    for (int l = 0; l < 4; l++) {
                        Card c = new Card(Values.values()[j], Suits.values()[l]);
                        if (r.canAdd(c)) {
                            r.addCard(c);
                            testSuite.assertEquals(++expected, r.getCards().size);
                        } else {
                            r.addCard(c);
                            testSuite.assertEquals(expected, r.getCards().size);
                        }
                    }
                }
            }
        }
    }

    public void testCanSwap() {
        Gdx.app.log(LOG, "--testCanSwap()--");

        for (int suit = 0; suit < 4; suit++) {
            Run r = new Run("", null);

            r.addCard(new Card(Values.TWO, Suits.values()[suit]));
            r.addCard(new Card(Values.THREE, Suits.values()[suit]));
            r.addCard(new Card(Values.FOUR, Suits.values()[suit]));
            r.addCard(new Card(Values.FIVE, Suits.values()[suit]));

            for (int value = 1; value < 14; value++) {
                for (int suit2 = 0; suit2 < 4; suit2++) {
                    for (int card = 0; card < r.getCards().size; card++) {
                        testSuite.assertEquals(false, r.canSwap(new Card(Values.values()[value], Suits.values()[suit2]), r.getCards().get(card)));
                    }
                }
            }
        }

        for (int suit = 0; suit < 4; suit++) {
            Run r = new Run("", null);

            r.addCard(new Card(Values.JOKER, Suits.values()[suit]));
            r.addCard(new Card(Values.THREE, Suits.values()[suit]));
            r.addCard(new Card(Values.FOUR, Suits.values()[suit]));
            r.addCard(new Card(Values.FIVE, Suits.values()[suit]));

            for (int value = 1; value < 14; value++) {
                for (int suit2 = 0; suit2 < 4; suit2++) {
                    for (int card = 0; card < r.getCards().size; card++) {
                        testSuite.assertEquals(
                                (r.getCards().get(card).getValue() == Values.JOKER &&
                                        value == Values.TWO.ordinal() && suit2 == suit),
                                r.canSwap(
                                        new Card(Values.values()[value], Suits.values()[suit2]), r.getCards().get(card))
                        );
                    }
                }
            }
        }

        for (int suit = 0; suit < 4; suit++) {
            Run r = new Run("", null);

            r.addCard(new Card(Values.TWO, Suits.values()[suit]));
            r.addCard(new Card(Values.JOKER, Suits.values()[suit]));
            r.addCard(new Card(Values.FOUR, Suits.values()[suit]));
            r.addCard(new Card(Values.FIVE, Suits.values()[suit]));

            for (int value = 1; value < 14; value++) {
                for (int suit2 = 0; suit2 < 4; suit2++) {
                    for (int card = 0; card < r.getCards().size; card++) {
                        testSuite.assertEquals(
                                (r.getCards().get(card).getValue() == Values.JOKER &&
                                        value == Values.THREE.ordinal() && suit2 == suit),
                                r.canSwap(
                                        new Card(Values.values()[value], Suits.values()[suit2]), r.getCards().get(card))
                        );
                    }
                }
            }
        }

        for (int suit = 0; suit < 4; suit++) {
            Run r = new Run("", null);

            r.addCard(new Card(Values.TWO, Suits.values()[suit]));
            r.addCard(new Card(Values.THREE, Suits.values()[suit]));
            r.addCard(new Card(Values.JOKER, Suits.values()[suit]));
            r.addCard(new Card(Values.FIVE, Suits.values()[suit]));

            for (int value = 1; value < 14; value++) {
                for (int suit2 = 0; suit2 < 4; suit2++) {
                    for (int card = 0; card < r.getCards().size; card++) {
                        testSuite.assertEquals(
                                (r.getCards().get(card).getValue() == Values.JOKER &&
                                        value == Values.FOUR.ordinal() && suit2 == suit),
                                r.canSwap(
                                        new Card(Values.values()[value], Suits.values()[suit2]), r.getCards().get(card))
                        );
                    }
                }
            }
        }

        for (int suit = 0; suit < 4; suit++) {
            Run r = new Run("", null);

            r.addCard(new Card(Values.TWO, Suits.values()[suit]));
            r.addCard(new Card(Values.THREE, Suits.values()[suit]));
            r.addCard(new Card(Values.FOUR, Suits.values()[suit]));
            r.addCard(new Card(Values.JOKER, Suits.values()[suit]));

            for (int value = 1; value < 14; value++) {
                for (int suit2 = 0; suit2 < 4; suit2++) {
                    for (int card = 0; card < r.getCards().size; card++) {
                        testSuite.assertEquals(
                                (r.getCards().get(card).getValue() == Values.JOKER &&
                                        value == Values.FIVE.ordinal() && suit2 == suit),
                                r.canSwap(
                                        new Card(Values.values()[value], Suits.values()[suit2]), r.getCards().get(card))
                        );
                    }
                }
            }
        }
    }

    // not testing swap for now, since it is the same as for Book (if can swap, then swap!)

    public void testGetValue() {
        Gdx.app.log(LOG, "--testGetValue()--");
        Run r = new Run("", null);

        testSuite.assertEquals(Values.NONE, r.getValue());

        r.addCard(new Card(Values.JOKER, Suits.NONE));

        testSuite.assertEquals(Values.NONE, r.getValue());

        for (int i = 1; i <= 13; i ++) {
            r = new Run("", null);

            r.addCard(new Card(Values.values()[i], Suits.CLUBS));

            testSuite.assertEquals(Values.NONE, r.getValue());
        }


    }

    public void testGetSuit() {
        Gdx.app.log(LOG, "--testGetSuit()--");
        for (int i=0; i<14; i++) {
            for (int j=0; j<4; j++) {
                Run r = new Run("", null);
                r.addCard(new Card(Values.values()[i], Suits.values()[j]));
                testSuite.assertEquals((i == joker ? Suits.NONE : Suits.values()[j]), r.getSuit());
            }
        }
    }

    public void testIsValid() {
        Gdx.app.log(LOG, "--testIsValid()--");

        Run run = new Run("", null);

        run.addCard(new Card(Values.TEN, Suits.SPADES));
        testSuite.assertEquals(false, run.isValid());
        run.addCard(new Card(Values.JACK, Suits.SPADES));
        testSuite.assertEquals(false, run.isValid());
        run.addCard(new Card(Values.QUEEN, Suits.SPADES));
        testSuite.assertEquals(false, run.isValid());
        run.addCard(new Card(Values.KING, Suits.SPADES));
        testSuite.assertEquals(true, run.isValid());
        run.addCard(new Card(Values.ACE, Suits.SPADES));
        testSuite.assertEquals(true, run.isValid());
        run.getCards().add(new Card(Values.TWO, Suits.SPADES));  // no wraparounds allowed on ace
        testSuite.assertEquals(false, run.isValid());

        run = new Run("", null);

        run.addCard(new Card(Values.JOKER, Suits.NONE));
        run.addCard(new Card(Values.JOKER, Suits.NONE));
        run.addCard(new Card(Values.JOKER, Suits.NONE));
        run.addCard(new Card(Values.JOKER, Suits.NONE));
        testSuite.assertEquals(false, run.isValid());
        run.addCard(new Card(Values.TWO, Suits.CLUBS));
        testSuite.assertEquals(false, run.isValid());

    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Super Rummy (c) 2013 Jami Couch";
        cfg.useGL20 = false;
        cfg.width = 1280;
        cfg.height = 768;
        cfg.fullscreen = false;
        cfg.resizable = true;

        new LwjglApplication(new RunTest(), cfg);
    }
}
