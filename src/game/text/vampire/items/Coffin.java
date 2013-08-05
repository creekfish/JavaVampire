package game.text.vampire.items;

import game.text.ActionContinued;
import game.text.ActionInitial;
import game.text.Actor;
import game.text.ItemFixedInPlace;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.ResultPartial;
import game.text.TextGame;

public class Coffin extends ItemFixedInPlace {
	private TextGame game;
	
	public Coffin(TextGame _game) {
		super("Closed Coffin", "");
		this.game = _game;
		this.addAction(new ActionInitial("open") {
			@Override
			public Result execute(Actor actor) {
				if (Coffin.this.getData("unlocked") == null) {
					if (((Player) actor).hasOne("key")) {
						// transform the coffin into vampire
						Coffin.this.changeName("Vampire in the Coffin");
						// change coffin into vampire in the game items map
						game.addItem("vampire", Coffin.this);  
						game.removeItem("coffin"); 
						game.removeItem("closed coffin"); 
						// change coffin in room to vampire
						((Player) actor).getLocation().addOne("vampire", Coffin.this); 
						((Player) actor).getLocation().removeOne("closed coffin");
						// mark crate as being wooden stakes now
						Coffin.this.setData("open", new Boolean(true));  
						return new ResultGeneric(true, ((Player) actor).getLocation().look());
					} else {
						// coffin won't open
						return new ResultGeneric(false, "The coffin is locked ... \n"+"Nothing happened\n");					
					}
				} else {
					return new ResultGeneric(false, "Nothing happened\n");					
				}
			}								
		});
		this.addAction(new ActionInitial("kill") {
			@Override
			public Result execute(Actor actor) {
				if (((Player) actor).getLocation().equals(game.getPlace("vampire's tomb")) && 
						Coffin.this.getData("open") != null) {
					return new ResultPartial(true, "      -- With what? ", (ActionContinued) Coffin.this.getAction("kill_with"));
				}
				game.end(false);
				return new ResultGeneric(false, "You Failed!  The Vampire awakes and sucks your Blood!\n");
			}								
		});
		this.addAction(new ActionContinued("kill_with") {
			@Override
			public Result execute(Actor actor, String feedback) {
				if (((Player) actor).getLocation().equals(game.getPlace("vampire's tomb")) && 
						Coffin.this.getData("open") != null) {
					if (game.matchItem(feedback) == game.getItem("wooden stakes") && 
							((Player) actor).hasOne("wooden stakes")) {
						game.end(true);
						return new ResultGeneric(true, "Congratulations!  You have killed the Vampire\n");
					}
				}
				game.end(false);
				return new ResultGeneric(false, "You Failed!  The Vampire awakes and sucks your Blood!\n");
			}								
		});
	}
}
