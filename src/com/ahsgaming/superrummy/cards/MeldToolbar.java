package com.ahsgaming.superrummy.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created with IntelliJ IDEA.
 * User: jami
 * Date: 8/4/13
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MeldToolbar extends Table {
    /**
     * MeldToolbar
     * - widget
     * - valid/not valid indicator
     * - add/remove buttons
     * - show tiny version of cards
     * - book/run indicators of value/suit?
     */
    public static final String LOG = "MeldToolbar";

    private Meld meld = null;
    private Skin skin = null;
    private TextButton add;
    private TextButton remove;

    public MeldToolbar(Meld meld, Skin skin) {
        super(skin);
        this.meld = meld;
        this.skin = skin;
        initialize();

        setWidth(getPrefWidth());
        setHeight(getPrefHeight());

    }

    public void initialize() {
        setTouchable(Touchable.enabled);
        add = new TextButton("+", skin);
        remove = new TextButton("-", skin);

        if (meld instanceof Book) {
            add("BOOK");
        } else {
            add("RUN");
        }
        add(add);
        add(remove);
        row();
        add(meld).colspan(3).height(42);
    }


}
