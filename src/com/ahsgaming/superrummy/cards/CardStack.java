package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;
import com.ahsgaming.superrummy.RummyGame;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

/**
 * super-rummy
 * (c) 2013 Jami Couch
 * Created on 7/18/13 by jami
 * ahsgaming.com
 */
public class CardStack extends CardCollection {
    public static final String LOG = "CardStack";

    public CardStack(Player owner) {
        super(owner);
    }

    @Override
    public void arrange() {
        if (cards.size == 0) { setSize(0, 0); return; }

        clear(); // remove all actors
        Array<Card> cardArray = new Array<Card>(8);
        for (int i = 0; i < (cards.size < 5 ? cards.size : 5); i++) {
            Card c = cards.get(cards.size - 1 - i);
            c.setFaceDown(isHidden());
            cardArray.add(c);
            c.setPosition(((cards.size < 5 ? cards.size : 5) - i - 1) * c.getWidth() * 0.05f, 0);
        }

        while(cardArray.size > 0) addActor(cardArray.pop());

        setSize(cards.get(cards.size - 1).getX() + cards.get(0).getWidth(), cards.get(0).getHeight());

        dirty = false;
    }
}
