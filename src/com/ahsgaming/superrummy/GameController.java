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
package com.ahsgaming.superrummy;

import com.ahsgaming.superrummy.cards.*;
import com.badlogic.gdx.utils.Array;

/**
 * @author jami
 *
 */
public class GameController {
	public String LOG = "GameController";

    static final int[][] roundDef = {
            {2, 0},         // 6 = 2 books
            {1, 1},         // 7 = 1 book, 1 run
            {0, 2},         // 8 = 2 runs
            {3, 0},         // 9 = 3 books
            {2, 1},         // 10 = 2 books, 1 run
            {1, 2},         // 11 = 1 book, 2 runs
            {0, 3}          // 12 = 3 runs
    };

    int currentRound = 0;

    CardCollection deck;
    CardCollection discards;

    Array<Meld> melds;

    Array<Player> players;

	/**
	 * Constructors
	 */
	
	public GameController(Array<Player> players) {
        deck = new CardStack(null);
        deck.setHidden(true);

        this.players = players;
	}

	/**
	 * Methods
	 */

    public void startRound(int round) {
        currentRound = round;
        deck.clear();

        deck.addAll(shuffle());

        for (Player p: players) {
            p.getHand().clear();
        }

        for (int i = 0; i < (currentRound <= 4 ? 10 : 12); i++) {
            for (Player p: players) {
                p.getHand().addCard(deck.pop());
            }
        }
    }

    public Array<Card> shuffle() {
        // two decks!
        Array<Card> available = new Array<Card>();
        Array<Card> shuffled = new Array<Card>();

        for (int s = 0; s < 4; s++) {
            Suits suit = Suits.values()[s];
            for (int v = Values.ACE.ordinal(); v <= Values.KING.ordinal(); v++) {
                Values value = Values.values()[v];
                available.add(new Card(value, suit));
                available.add(new Card(value, suit));
            }
        }

        available.add(new Card(Values.JOKER, Suits.NONE));
        available.add(new Card(Values.JOKER, Suits.NONE));
        available.add(new Card(Values.JOKER, Suits.NONE));
        available.add(new Card(Values.JOKER, Suits.NONE));

        while (available.size > 0) shuffled.add(available.removeIndex((int) (Math.random() * available.size)));
        return shuffled;
    }

    public CardCollection getDeck() {
        return deck;
    }

    public Array<Player> getPlayers() {
        return players;
    }
}
