package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

/**
 * super-rummy
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

    boolean controllable = false;

    public CardCollection() {
        this(null, false);
    }

    public CardCollection(Player owner) {
        this(owner, false);
    }

    public CardCollection(Player owner, boolean controllable) {
        super();

        this.owner = owner;
        cards = new Array<Card>();

        this.controllable = controllable;
        if (controllable) { /* add event listener for dragging cards around? */ }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        for (Card c: cards) if (c.dirty) dirty = true;
        super.draw(batch, parentAlpha);

        if (dirty) arrange();
    }

    public void arrange() {
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

        dirty = false;
    }

    public void addCard(Card card) {
        cards.add(card);
        this.addActor(card);
        this.dirty = true;

        card.removeListeners();

        if (controllable) {
            card.addSelectionListener();
        }
    }

    public void addAll(Array<Card> cards) {
        for (Card c: cards) this.addCard(c);
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

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        dirty = true;
        this.compressed = compressed;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        dirty = true;
        this.hidden = hidden;
    }
}
