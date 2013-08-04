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

    public static final int MIN_VALID_SIZE = 4;
    Suits suit;

    public Run(String id, Player owner) {
        super(id, owner);
        suit = Suits.NONE;
    }

    @Override
    public boolean canAdd(Card card) {
        if (card.getValue() == Values.JOKER || suit == Suits.NONE) return true;
        if (card.getSuit() == suit) {
            if (cards.first().getValue().ordinal() - 1 == card.getValue().ordinal() ||
                    cards.peek().getValue().ordinal() + 1 == card.getValue().ordinal() ||
                    (cards.peek().getValue() == Values.KING && card.getValue() == Values.ACE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addCard(Card card) {
        if (canAdd(card)) {
            super.addCard(card);
            if (suit == Suits.NONE && card.getValue() != Values.JOKER) {
                suit = card.getSuit();
            }
        }
    }

    @Override
    public boolean canSwap(Card card, Card joker) {
        return (joker.getValue() == Values.JOKER &&
                cards.contains(joker, true) &&
                card.getSuit() == getSuit() &&
                card.getValue() == getApparentValue(joker));
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
        return Values.NONE;
    }

    @Override
    public Suits getSuit() {
        return suit;
    }

    @Override
    public boolean isValid() {
        if (cards.size < MIN_VALID_SIZE) return false;

        int valueOrdinal = 0;
        boolean notAllJokers = false;
        for (int i = 0; i < cards.size; i++) {
            Card card = cards.get(i);
            if (card.value == Values.JOKER) {
                if (i == 0) {
                    valueOrdinal = getApparentValue(card).ordinal();
                } else {
                    valueOrdinal += 1;
                }
            } else {
                if (card.getSuit() != getSuit()) return false;
                notAllJokers = true;
                if (i == 0 || card.getValue().ordinal() == valueOrdinal + 1) {
                    valueOrdinal = card.getValue().ordinal();
                } else if (card.getValue() == Values.ACE && valueOrdinal == Values.KING.ordinal()) {
                    if (i + 1 != cards.size) return false;
                } else {
                    return false;
                }
            }
        }

        return notAllJokers;
    }

    protected Values getApparentValue(Card joker) {
        if (!cards.contains(joker, true)) return Values.NONE;

        int firstNonJoker = 0;
        while (firstNonJoker < cards.size && cards.get(firstNonJoker).getValue() == Values.JOKER) {
            firstNonJoker++;
        }
        if (firstNonJoker == cards.size) return Values.NONE;

        int jokerIndex = cards.indexOf(joker, true);

        int valueIndex = (cards.get(firstNonJoker).getValue().ordinal() - (firstNonJoker - jokerIndex));

        return (valueIndex >= 0 && valueIndex <= Values.values().length ? Values.values()[valueIndex] : Values.NONE);
    }
}
