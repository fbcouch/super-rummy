package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/17/13 by jami
 * ahsgaming.com
 */
public class Book extends Meld {
    public static final String LOG = "Book";

    public static final int MIN_VALID_SIZE = 3;

    Values value;

    public Book(String id, Player owner) {
        super(id, owner);
        value = Values.NONE; // hasn't been assigned yet
    }

    @Override
    public boolean canAdd(Card card) {
        return (value == Values.NONE || value == card.getValue() || card.getValue() == Values.JOKER);
    }

    @Override
    public void addCard(Card card) {
        if (canAdd(card)) {
            super.addCard(card);
            if (value == Values.NONE && card.getValue() != Values.JOKER)
                value = card.getValue();
        }
    }

    @Override
    public boolean canSwap(Card card, Card joker) {
        return (card.getValue() == value && joker.getValue() == Values.JOKER && card.getValue() != Values.JOKER);
    }

    @Override
    public Card swap(Card card, Card joker) {
        if (canSwap(card, joker)) {
            int i = cards.indexOf(joker, true);
            cards.insert(i, card);
            cards.removeValue(joker, true);
            return joker;
        }
        return null;
    }

    @Override
    public Values getValue() {
        return value;
    }

    @Override
    public Suits getSuit() {
        return Suits.NONE;
    }

    @Override
    public boolean isValid() {
        if (cards.size < MIN_VALID_SIZE) return false;

        for (Card c: cards)
            if (c.getValue() != value && c.getValue() != Values.JOKER)
                return false;

        return true;
    }

    @Override
    public String toString() {
        return String.format("Book: %s", super.toString());
    }
}
