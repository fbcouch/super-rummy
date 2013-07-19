package com.ahsgaming.superrummy.cards;

import com.ahsgaming.superrummy.RummyGame;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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

    Image frontImage, backImage;

    RummyGame game;

    boolean selected = false;
    boolean faceDown = false;
    boolean dirty = true;

    public Card(Values value, Suits suit) {
        super();
        this.value = value;
        this.suit = suit;
        this.game = RummyGame.instance;
        this.frontImage = new Image(game.textureService.createSprite(getCardString() + "_"));
        this.backImage = new Image(game.textureService.createSprite("back"));
        this.addActor(this.frontImage);
        this.setSize(this.frontImage.getWidth(), this.frontImage.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (dirty) {
            if (faceDown) {
                frontImage.remove();
                this.addActorAt(0, backImage);
            } else {
                backImage.remove();
                this.addActorAt(0, frontImage);
            }
        }
        dirty = false;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        dirty = true;
        this.selected = selected;
    }

    public void toggleSelected() {
        dirty = true;
        this.selected = !selected;
    }

    public boolean isFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        dirty = true;
        this.faceDown = faceDown;
    }

    public void removeListeners() {
        for (EventListener el: getListeners()) {
            removeListener(el);
        }
    }

    public void clearStates() {
        faceDown = false;
        selected = false;
        dirty = true;
    }

    public void addSelectionListener() {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                toggleSelected();

            }
        });
    }
}
