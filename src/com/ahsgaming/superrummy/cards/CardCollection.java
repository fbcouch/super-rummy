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

    boolean dirty = true;

    boolean compressed = false;
    boolean hidden = false;

    public CardCollection(Player owner) {
        super();
        this.owner = owner;
        cards = new Array<Card>();
    }

    @Override
    public void act(float delta) {
        for (Card c: cards) if (c.dirty) dirty = true;
        super.act(delta);

        if (dirty) {
            if (cards.size == 0) {
                setSize (0, 0);
                return;
            }

            int x = 0;
            for (Card c: cards) {
                c.setFaceDown(hidden);
                c.setPosition(x, (c.isSelected() ? c.getHeight() * 0.25f : 0));
                x += c.getWidth() * (compressed ? 0.25f : 0.75f);
            }

            setSize(x + cards.get(0).getWidth() * (compressed ? 0.75f : 0.25f), cards.get(0).getHeight());
        }
        dirty = false;
    }

    public void addCard(Card card) {
        cards.add(card);
        this.addActor(card);
        this.dirty = true;
    }

    public void addAll(Array<Card> cards) {
        this.cards.addAll(cards);
        for (Card c: cards) {
            this.addActor(c);
        }
        this.dirty = true;
    }

    public void addAll(CardCollection cards) {
        this.cards.addAll(cards.getCards());
        for (Card c: cards.getCards()) {
            this.addActor(c);
        }
        this.dirty = true;
    }

    public int indexOf(Card card) {
        return cards.indexOf(card, true);
    }

    public boolean removeCard(Card card) {
        card.remove();
        this.dirty = true;
        return cards.removeValue(card, true);
    }

    public Card removeCard(int index) {
        Card c = cards.removeIndex(index);
        c.remove();
        this.dirty = true;
        return c;
    }

    public Card pop() {
        this.dirty = true;
        return (cards.size > 0 ? cards.pop() : null);
    }

    public Card peek() {
        return (cards.size > 0 ? cards.peek() : null);
    }

    public Array<Card> getCards() {
        return cards;
    }
}
