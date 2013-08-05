package game.text.vampire;
import game.text.ActionInitial;
import game.text.Actor;
import game.text.DirectionGeneric;
import game.text.Place;
import game.text.PlayerGeneric;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGameSinglePlayer;
import game.text.exceptions.ActionException;
import game.text.vampire.items.Axe;
import game.text.vampire.items.Boat;
import game.text.vampire.items.Bookcase;
import game.text.vampire.items.BrickFireplace;
import game.text.vampire.items.Bucket;
import game.text.vampire.items.CheddarCheese;
import game.text.vampire.items.Coffin;
import game.text.vampire.items.CoilOfRope;
import game.text.vampire.items.Crate;
import game.text.vampire.items.FireInTheFireplace;
import game.text.vampire.items.FlaskOfOil;
import game.text.vampire.items.Holywater;
import game.text.vampire.items.Key;
import game.text.vampire.items.Nails;
import game.text.vampire.items.Oar;
import game.text.vampire.items.Parapets;
import game.text.vampire.items.ParchmentScroll;
import game.text.vampire.items.Rat;
import game.text.vampire.items.RustyDoor;
import game.text.vampire.items.Sign;
import game.text.vampire.items.SledgeHammer;
import game.text.vampire.items.Tapestry;
import game.text.vampire.items.Timepiece;
import game.text.vampire.items.Torch;
import game.text.vampire.items.Wine;
import game.text.vampire.places.AlchemistsLab;
import game.text.vampire.places.Antechamber;
import game.text.vampire.places.Armory;
import game.text.vampire.places.BoatPlace;
import game.text.vampire.places.Chapel;
import game.text.vampire.places.EntranceHall;
import game.text.vampire.places.Fireplace;
import game.text.vampire.places.Gallery;
import game.text.vampire.places.HiddenCorridor;
import game.text.vampire.places.Library;
import game.text.vampire.places.LowerTower;
import game.text.vampire.places.Overhang;
import game.text.vampire.places.SecretPassage;
import game.text.vampire.places.Storeroom;
import game.text.vampire.places.Study;
import game.text.vampire.places.TortureChamber;
import game.text.vampire.places.Tower;
import game.text.vampire.places.UndergroundLakeChamber;
import game.text.vampire.places.VampiresTomb;

import java.util.Collection;


public class Vampire extends TextGameSinglePlayer {
	private int gameTime = 0;
	
	
    public Vampire(Collection<Place> rooms) {
    	super(rooms);
    }
    
    public Vampire() {
    	super();
    }
    
    public String getName() {
    	return "Vampire!";
    }
    
	public void changeName(String newName) {
		// no effect
	}

	public String welcome() {
    	String out = "";
		out += "Welcome to the VAMPIRE'S CASTLE Adventure\n\n";
		out += "Do you need the instructions? ";
		return out;
	}
	
	public String instructions() {
		String out = "";
		out += "VAMPIRE'S CASTLE has a concealed goal.  You learn what the goal is\n";
		out += "by exploring your surroundings.  The computer will act as your eyes\n";
		out += "and hands.  It will accept short phrases as commands and assumes\n";
		out += "that the first word is a verb and the last word is the object.\n";
		out += "For example: READ THE SIGN.  The computer has a vocabulary of about\n";
		out += "70 words.  Some of the more important words you should know before\n";
		out += "you start playing are: GET <object>, DROP <object>, LOOK <object>\n";
		out += "or just LOOK, GO <direction> or <place>, and INVENTORY (tells what\n";
		out += "you are carrying).  The computer knows the abbreviations: E, W, N,\n";
		out += "S, U and D for GO EAST, GO WEST, etc.\n\n";
		out += "The computer's vocabulary is good, but limited.  If you are having\n";
		out += "trouble doing something, try re-phrasing the command or you may\n";
		out += "need some object to accomplish the task.  By the way, the computer\n";
		out += "only looks at the first 3 letters of each word.\n\n";
		return out;
	}
	
    @Override
    public void end(boolean won) {
    	super.end(won);
    	// rewind the clock 2 minutes so controller can replay the last move if desired
    	setTime(gameTime - 2);  
     }    


    @Override
	public boolean endTurn() {
    	if (!hasContinuedAction()) {  // if the action has completed (not needing more input)
			addTime(1);  // time keeps on slippin, slippin...
    	}
		return super.endTurn();		
	}

    @Override
    public Result takeTurn(String textInput) {
		if (gameTime >= 12*60) {
			this.end(false);
			return new ResultGeneric(false, " It's midnight: the Vampire is awake, He's at your neck \n");
		}
		return super.takeTurn(textInput);
	}

	public  String getTime() {
		return String.format("%02d",gameTime/60)+":"+String.format("%02d",gameTime%60);
	}
	
	public void setTime(int newTime) {
		gameTime = newTime;
	}

	public void addTime(int minutes) {
		gameTime += minutes;
	}
	
	
	@Override
	public void setDefaultActions() {
		super.setDefaultActions();
		
		// verb "row" always means row the boat (flaw in old game?)
		this.addAction(new ActionInitial("row") { 
				@Override
				public Result execute(Actor actor) {
					return Vampire.this.getPlace("boat").executeAction("row", Vampire.this.getPlayer());
				}						
			}
		);
		this.addAction(new ActionInitial("open") {
			@Override
			public Result execute(Actor actor) {
				return new ResultGeneric(false, "Nothing happened\n");
			}								
		});	
	}
	
	@Override
	public void initialize() {
		super.initialize();  // must do full parent initialization

		setTime(8*60+1); // set initial time to 8:01

		Vampire game = this;
		
		// Create a player
		
		game.addPlayer(new PlayerGeneric("P1", game)); 
		
		
		// Establish permissible directions for player to "go"

		game.addDirection(DirectionGeneric.North);
		game.addDirection(DirectionGeneric.East);
		game.addDirection(DirectionGeneric.South);
		game.addDirection(DirectionGeneric.West);
		game.addDirection(DirectionGeneric.Up);
		game.addDirection(DirectionGeneric.Down);
		// can add new directions like this:	game.addDirection(new DirectionGeneric("Sideways"));
		
		
		// Create all items, some with alias
		
		game.addItem(new Timepiece(game));
		game.addItem(new Sign(game));	
		game.addItem(new BrickFireplace(game));
		game.addItem("fireplace", game.getItem("brick fireplace"));  // "fire" = alias for "brick fireplace"
		game.addItem("_fire", new FireInTheFireplace(game));  // no direct reference to fire in the game
		game.addItem(new CheddarCheese(game));
		game.addItem("cheese", game.getItem("cheddar cheese"));    // alias for cheddar cheese is "cheese"
		game.addItem(new Wine(game));
		game.addItem(new ParchmentScroll(game));
		game.addItem("scroll", game.getItem("parchment scroll"));  // "scroll" = alias for "parchment scroll"
		game.addItem(new Bookcase(game));
		game.addItem(new Axe(game));
		game.addItem(new FlaskOfOil(game));
		game.addItem("oil", game.getItem("flask of oil"));    // alias for flask of oil is "oil"
		game.addItem(new Bucket(game));
		game.addItem(new Torch(game));
		game.addItem(new Oar(game));
		game.addItem(new Rat(game));
		game.addItem(new Key(game));
		game.addItem(new Holywater(game));
		game.addItem("_parapets", new Parapets(game));  // no direct reference to parapets as game item
		game.addItem(new SledgeHammer(game));
		game.addItem("rope", new CoilOfRope(game));
		game.addItem("_boat", new Boat(game));   // no direct reference to boat as game item ("boat" is a place)
		game.addItem(new Tapestry(game));
		game.addItem(new Nails(game));
		game.addItem(new Crate(game));
		game.addItem(new RustyDoor(game));
		game.addItem("door", game.getItem("rusty door"));  // alias for "rusty door" is "door"
		game.addItem(new Coffin(game));
		game.addItem("coffin", game.getItem("closed coffin"));
		
		
		// Create places populated with items in their initial positions
		
		try {
			game.addPlace(new EntranceHall(game));
			game.addPlace(new Study(game));
			game.addPlace(new Library(game));
			game.addPlace("_fireplace", new Fireplace(game));	
			game.addPlace(new AlchemistsLab(game));
			game.addPlace(new Storeroom(game));
			game.addPlace(new Armory(game));
			game.addPlace(new Chapel(game));
			game.addPlace(new Tower(game));
			game.addPlace(new LowerTower(game));
			game.addPlace(new SecretPassage(game));
			game.addPlace(new TortureChamber(game));
			game.addPlace(new HiddenCorridor(game));
			game.addPlace(new UndergroundLakeChamber(game));
			game.addPlace("lake", game.getPlace("underground lake chamber"));    // alias for "undergound lake chamber" is "lake"
			game.addPlace(new BoatPlace(game));
			game.addPlace(new Gallery(game));
			game.addPlace(new Overhang(game));
			game.addPlace(new Antechamber(game));
			game.addPlace(new VampiresTomb(game));

		} catch (ActionException e) {
			e.printStackTrace();
		}
		
		
		// Initial map of all of room connections (not counting hidden or created connections between rooms)
		
		game.getPlace("study").connect(game.getPlace("entrance hall"), game.getDirection("east"));
		game.getPlace("entrance hall").connect(game.getPlace("library"), game.getDirection("east"));		
		game.getPlace("library").connect(game.getPlace("armory"), game.getDirection("east"));
		game.getPlace("armory").connect(game.getPlace("tower"), game.getDirection("east"));
		game.getPlace("_fireplace").setConnection(game.getDirection("south"), game.getPlace("study"));
		game.getPlace("hidden corridor").connect(game.getPlace("alchemist's lab"), game.getDirection("north"));
		game.getPlace("alchemist's lab").connect(game.getPlace("storeroom"), game.getDirection("north"));
		game.getPlace("storeroom").setConnection(game.getDirection("up"), game.getPlace("study"));
		game.getPlace("lower tower").setConnection(game.getDirection("up"), game.getPlace("tower"));
		game.getPlace("lower tower").connect(game.getPlace("chapel"), game.getDirection("south"));
		game.getPlace("chapel").setConnection(game.getDirection("up"), game.getPlace("armory"));
		game.getPlace("secret passage").connect(game.getPlace("underground lake chamber"), game.getDirection("north"));
		game.getPlace("secret passage").connect(game.getPlace("torture chamber"), game.getDirection("west"));
		game.getPlace("secret passage").setConnection(game.getDirection("south"), game.getPlace("_fireplace"));
		game.getPlace("torture chamber").setConnection(game.getDirection("west"), game.getPlace("alchemist's lab"));
		game.getPlace("boat").setConnection(game.getDirection("south"), game.getPlace("lake"));
		game.getPlace("overhang").setConnection(game.getDirection("down"), game.getPlace("gallery"));


		// Initial player location	
		
		game.getPlayer().setLocation(game.getPlace("entrance hall"));

		
		
		
		
		/*
		 * CHEAT SHORTCUTS FOR DEVELOPMENT!
		 */
//		game.getItem("_fire").setData("out", new Boolean(true));
//		game.getItem("fireplace").setData("broken", new Boolean(true));
//		game.getPlace("lower tower").connect(game.getPlace("tower"), game.getDirection("up"));				
//		game.getPlace("_fireplace").setConnection(game.getDirection("north"), game.getPlace("secret passage")); 
//		try {
//			game.getItem("oar").move(game.getPlace("_fireplace"));
//		} catch (ActionException e) {
//			e.printStackTrace();
//		}

		
//		game.getPlayer().setLocation(game.getPlace("vampire's tomb"));
//		try {
//			game.getItem("torch").move(game.getPlayer());		
//			game.getItem("oar").move(game.getPlayer());		
//			game.getItem("hammer").move(game.getPlayer());
//			game.getItem("crate").move(game.getPlayer());	
//			game.getItem("axe").move(game.getPlayer());	
//			game.getItem("oil").move(game.getPlayer());	
//			game.getItem("key").move(game.getPlayer());	
//		} catch (ActionException e) {
//			e.printStackTrace();
//		}
		
	}
	
}

