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

import com.ahsgaming.superrummy.cards.Card;
import com.ahsgaming.superrummy.cards.Suits;
import com.ahsgaming.superrummy.cards.Values;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * @author jami
 *
 */
public class GameController {
	public String LOG = "GameController";

    Array<Card> deck;

	/**
	 * Constructors
	 */
	
	public GameController() {
        deck = shuffle();
	}
	
	/**
	 * Methods
	 */

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

    public Array<Card> getDeck() {
        return deck;
    }
}
