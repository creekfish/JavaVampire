package game.text.vampire.places;

import game.text.ActionInitial;
import game.text.Actor;
import game.text.PlaceGeneric;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class BoatPlace extends PlaceGeneric {	
	private TextGame game;
	
	public BoatPlace(TextGame _game)  throws ActionException {
		super("Boat", "");
		this.game = _game;
		this.addAction(new ActionInitial("row") {
			@Override
			public Result execute(Actor actor) {
				if (((Player) actor).getLocation() == game.getPlace("boat")) {
					if (((Player) actor).hasOne(game.getItem("oar"))) {
						if (game.getItem("_boat").getLocation() == game.getPlace("lake")) {
							((Player) actor).setLocation(game.getPlace("gallery"));
							try {
								game.getItem("_boat").move(game.getPlace("gallery"));
							} catch (ActionException e) {
								return new ResultGeneric(false, e.getMessage());
							}
						} else {								
							((Player) actor).setLocation(game.getPlace("lake"));
							try {
								game.getItem("_boat").move(game.getPlace("lake"));
							} catch (ActionException e) {
								return new ResultGeneric(false, e.getMessage());
							}
						}
						return new ResultGeneric(true, "You have rowed to the "+
								((Player) actor).getLocation().getName()+"\n\n"+
								((Player) actor).getLocation().getInitialAction("look").execute(actor).getMessage());
					} else {
						return new ResultGeneric(false, "You can't do that");
					}						
				} else {
					return new ResultGeneric(false, "You can't do that");
				}								
			}					
		});
		// map over the normal "go"
		this.addAction(new ActionInitial("go") {
			@Override
			public Result execute(Actor actor) {
				if (((Player) actor).getLocation().hasOne("boat")) {  // if the boat is in the same room
					((Player) actor).setLocation(game.getPlace("boat"));
					return new ResultGeneric(true, ((Player) actor).getLocation().getInitialAction("look").execute(actor).getMessage());
				} else {
					return new ResultGeneric(false, "You can't go there");
				}
			}								
		});
		// map over the normal "get"
		this.addAction(new ActionInitial("get") {
			@Override
			public Result execute(Actor actor) {
				return new ResultGeneric(false, "You can't get it");
			}								
		});
	}
}
