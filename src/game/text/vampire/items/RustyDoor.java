package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.ItemFixedInPlace;
import game.text.Place;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;

public class RustyDoor extends ItemFixedInPlace {
	private TextGame game;
	
	public RustyDoor(TextGame _game) {
		super("Rusty Door", "");
		this.game = _game;
		this.addAction(new ActionGeneric("open") {
			@Override
			public Result execute(Actor actor) {
				if (RustyDoor.this.getData("oiled") != null) {
					// transform the rusty door into open door
					RustyDoor.this.changeName("Open Door"); 
					((Place) RustyDoor.this.getLocation()).connect(game.getPlace("vampire's tomb"), game.getDirection("north")); // open door leads to tomb
					return new ResultGeneric(true, ((Player) actor).getLocation().look());
				} else {
					// door won't open
					return new ResultGeneric(true, "Too much rust ... \n"+"Nothing happened\n");					
				}
			}								
		});
		this.addAction(new ActionGeneric("oil") {
			@Override
			public Result execute(Actor actor) {
				if (RustyDoor.this.getData("oiled") == null) {
					if (((Player) actor).hasOne("flask of oil")) {
						// mark door as oiled
						RustyDoor.this.setData("oiled", new Boolean(true));  
						return new ResultGeneric(true, ((Player) actor).getLocation().look());
					}
				}
				return new ResultGeneric(false, "You can't do that\n");
			}								
		});
	}
}
