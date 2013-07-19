package com.ahsgaming.superrummy;

import com.ahsgaming.superrummy.cards.Card;
import com.ahsgaming.superrummy.cards.CardCollection;
import com.badlogic.gdx.utils.Array;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/16/13 by jami
 * ahsgaming.com
 */
public class Player {
    public static final String LOG = "Player";

    final String id;
    String name;

    CardCollection hand;

    public Player(String id, String name, boolean controllable) {
        this.id = id;
        this.name = name;

        hand = new CardCollection(this, controllable);
    }

    public CardCollection getHand() {
        return hand;
    }
}
