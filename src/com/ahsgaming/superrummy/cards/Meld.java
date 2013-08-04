package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/17/13 by jami
 * ahsgaming.com
 */
public abstract class Meld extends CardCollection {
    final String id;
    public Meld(String id, Player owner) {
        super(owner);
        this.id = id;
    }

    public abstract boolean canAdd(Card card);
    public abstract boolean canSwap(Card card, Card joker);
    public abstract Card swap(Card card, Card joker);
    public abstract Values getValue();
    public abstract Suits getSuit();
    public abstract boolean isValid();

    @Override
    public String toString() {
        String returnVal = String.format("%s:", id);
        for (Card c: cards) {
            returnVal = String.format("%s [%s],", returnVal, c.toString());
        }
        return returnVal;
    }
}
