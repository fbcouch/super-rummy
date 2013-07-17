package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.RummyGame;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * valley-of-bones
 * (c) 2013 Jami Couch
 * Created on 7/16/13 by jami
 * ahsgaming.com
 */
public class Card extends Group {
    public static final String LOG = "Card";

    Values value;
    Suits suit;

    Image image;

    RummyGame game;

    public Card(Values value, Suits suit) {
        this.value = value;
        this.suit = suit;
        this.game = RummyGame.instance;
        this.image = new Image(game.textureService.createSprite(getCardString() + "_"));
        this.addActor(this.image);
        this.setSize(this.image.getWidth(), this.image.getHeight());
    }

    public String getCardString() {                  // TODO handle NONE values (face down card)
        String returnVal = suit.toString().toLowerCase() + "_";
        switch(value) {
            case JOKER:
                returnVal = "joker";
                break;
            case ACE:
                returnVal += "A";
                break;
            case JACK:
                returnVal += "J";
                break;
            case QUEEN:
                returnVal += "Q";
                break;
            case KING:
                returnVal += "K";
                break;
            default:
                returnVal += value.ordinal();
        }
        return returnVal;
    }
}
