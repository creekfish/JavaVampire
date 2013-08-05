package game.text.vampire.places;

import game.text.ActionInitial;
import game.text.Actor;
import game.text.Game;
import game.text.PlaceGeneric;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.exceptions.ActionException;

public class Overhang extends PlaceGeneric {	
	private Game game;
	
	public Overhang(Game game)  throws ActionException {
		super("Overhang", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("nails"));
		// map over the normal "go"
		this.addAction(new ActionInitial("go") {
			@Override
			public Result execute(Actor actor) {
				if (actor instanceof Player && Overhang.this.game.getPlace("gallery").hasOne("crate")) {
					((Player) actor).setLocation(Overhang.this);
					return new ResultGeneric(true, Overhang.this.look());

				} else {
					return new ResultGeneric(false, "It's a little too high");
				}
			}						
		});
	}
}
