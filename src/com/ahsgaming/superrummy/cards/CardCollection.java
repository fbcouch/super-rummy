package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/17/13 by jami
 * ahsgaming.com
 */
public class CardCollection extends Group {
    public static final String LOG = "CardCollection";

    Array<Card> cards;
    Player owner;

    public CardCollection(Player owner) {
        super();
        this.owner = owner;
        cards = new Array<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);
        this.addActor(card);
    }

    public void addAll(Array<Card> cards) {
        this.cards.addAll(cards);
        for (Card c: cards) {
            this.addActor(c);
        }
    }

    public void addAll(CardCollection cards) {
        this.cards.addAll(cards.getCards());
        for (Card c: cards.getCards()) {
            this.addActor(c);
        }
    }

    public int indexOf(Card card) {
        return cards.indexOf(card, true);
    }

    public boolean removeCard(Card card) {
        card.remove();
        return cards.removeValue(card, true);
    }

    public Card removeCard(int index) {
        Card c = cards.removeIndex(index);
        c.remove();
        return c;
    }

    public Card pop() {
        return (cards.size > 0 ? cards.pop() : null);
    }

    public Card peek() {
        return (cards.size > 0 ? cards.peek() : null);
    }

    public Array<Card> getCards() {
        return cards;
    }
}
