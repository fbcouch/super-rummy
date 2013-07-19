package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/17/13 by jami
 * ahsgaming.com
 */
public class Run extends Meld {
    public static final String LOG = "Run";

    public Run(String id, Player owner) {
        super(id, owner);
    }

    @Override
    public boolean canAdd(Card card) {
        return false;
    }

    @Override
    public boolean canSwap(Card card, Card joker) {
        return false;
    }

    @Override
    public Card swap(Card card, Card joker) {
        return null;
    }

    @Override
    public Values getValueOf(Card card) {
        return null;
    }

    @Override
    public Suits getSuitOf(Card card) {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
