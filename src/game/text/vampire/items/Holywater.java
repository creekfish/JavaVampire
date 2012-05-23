package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.ItemGeneric;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class Holywater extends ItemGeneric {
	private TextGame game;
	
	public Holywater(TextGame _game) {
		super("Holywater", "");
		this.game = _game;
		this.addAction(new ActionGeneric("get") {
				@Override
				public Result execute(Actor actor) {
	
					if (((Player) actor).getLocation().hasOne("holywater")) {
						game.output("      -- In what? ");
						String input = game.input();
						if (game.matchItem(input) == game.getItem("bucket") && 
								((Player) actor).hasOne("bucket")) {
							try {
								((Player) actor).pickUp(game.getItem("holywater"));
								this.incrementCount();
								return new ResultGeneric(true, "You got the "+game.getItem("holywater").getName());
							} catch (ActionException e) {
								return new ResultGeneric(false, e.getMessage());
							}
						}
						return new ResultGeneric(false, "You can't do that\n");	
					} else {
						return new ResultGeneric(false, "I don't see any "+game.getItem("holywater").getName());									
					}
				}					
			});
		ActionGeneric dropAction = new ActionGeneric("drop") {
			@Override
			public Result execute(Actor actor) {
				if (((Player) actor).hasOne("holywater") &&
						((Player) actor).getLocation() == game.getPlace("study")) {
					// in the study, holy water puts out the fire!
					game.getItem("_fire").changeName("Smoldering Ashes");
					game.getItem("_fire").setData("out", new Boolean(true));
					((Player) actor).removeOne("holywater");  // holywater is consumed				
					return new ResultGeneric(true, ((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
				} else {
					// normal drop
					try {
						((Player) actor).drop(game.getItem("holywater"));
						this.incrementCount();
						return new ResultGeneric(true, "The "+game.getItem("holywater").getName()+" is on the "+((Player) actor).getLocation().getName()+" floor");
					} catch (ActionException e) {
						return new ResultGeneric(false, e.getMessage());
					}
				}								
			}
		};
		this.addAction(dropAction);  
		this.addAction("throw", dropAction);  // set "throw" as alias for "drop"
	}
}
