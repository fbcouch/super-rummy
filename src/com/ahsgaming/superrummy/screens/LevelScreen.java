/**
 * Copyright 2012 Jami Couch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This project uses:
 * 
 * LibGDX
 * Copyright 2011 see LibGDX AUTHORS file
 * Licensed under Apache License, Version 2.0 (see above).
 * 
 */
package com.ahsgaming.superrummy.screens;

import com.ahsgaming.superrummy.GameController;
import com.ahsgaming.superrummy.Player;
import com.ahsgaming.superrummy.RummyGame;
import com.ahsgaming.superrummy.cards.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * @author jami
 *
 */
public class LevelScreen extends AbstractScreen {
	public String LOG = "LevelScreen";

    static LevelScreen instance;

	Group grpLevel;
	
	GameController gameController = null;

    Player currentPlayer;
	
	/**
	 * @param game
	 */
	public LevelScreen(RummyGame game, GameController gameController, Player currentPlayer) {
	    super(game);
	    instance = this;
        this.gameController = gameController;
        this.currentPlayer = currentPlayer;
	}
	
	/**
	 * Methods
	 */

	/**
	 * Implemented methods
	 */
	
	@Override
	public void show() {
		super.show();
		Gdx.app.log(RummyGame.LOG, "LevelScreen#show");
        grpLevel = new Group();

        for (Player p: gameController.getPlayers()) {
            grpLevel.addActor(p.getHand());
            p.getHand().setHidden(true);
            p.getHand().setCompressed(true);
        }
        currentPlayer.getHand().setHidden(false);
        currentPlayer.getHand().setCompressed(false);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		stage.addActor(grpLevel);

	}

	@Override
	public void render(float delta) {
		super.render(delta);

        int offset = gameController.getPlayers().indexOf(currentPlayer, true);
        for (int i = 0; i < gameController.getPlayers().size; i++) {
            Player p = gameController.getPlayers().get((i + offset) % gameController.getPlayers().size);
            p.getHand().setPosition(
                    i == 0 ? (stage.getWidth() - p.getHand().getWidth()) * 0.5f : ((i - 0.5f) * stage.getWidth() / (gameController.getPlayers().size - 1)) - p.getHand().getWidth() * 0.5f,
                    i == 0 ? 0 : stage.getHeight() - p.getHand().getHeight()
            );
        }
	}
	
	public void addFloatingLabel(String text, float x, float y) {
		Gdx.app.log(LOG, "Floating Label!");
		Label lbl = new Label(text, new LabelStyle(getSmallFont(), new Color(1,1,1,1)));
		lbl.setPosition(x - lbl.getWidth() * 0.5f, y - lbl.getHeight() * 0.5f);
		lbl.addAction(Actions.parallel(Actions.fadeOut(1f), Actions.moveBy(0, 64f, 1f)));
		grpLevel.addActor(lbl);
	}

    public Group popupMessage(String text, String icon, float duration) {
        Gdx.app.log(LOG, "Popup message!");
        Group popup = new Group();
        Label lbl = new Label(text, getSkin(), "medium");
        Image img = new Image(game.textureService.createSprite(icon));
        popup.addActor(img);
        popup.addActor(lbl);
        lbl.setPosition(img.getRight(), (img.getHeight() - lbl.getHeight()) * 0.5f);
        popup.setSize(lbl.getRight(), img.getTop());
        popup.setPosition((stage.getWidth() - popup.getWidth()) * 0.5f, (stage.getHeight() - popup.getHeight()) * 0.5f);
        popup.setColor(popup.getColor().r, popup.getColor().g, popup.getColor().b, 0);
        stage.addActor(popup);
        popup.addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(duration), Actions.fadeOut(0.5f)));
        return popup;
    }

    /*
     * Static methods
     */

    public static LevelScreen getInstance() {
        return instance;
    }
}
