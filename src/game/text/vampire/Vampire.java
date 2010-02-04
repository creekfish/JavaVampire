package game.text.vampire;
import game.text.Action;
import game.text.ActionGeneric;
import game.text.Actor;
import game.text.Container;
import game.text.DirectionGeneric;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.PlaceDarkWithoutItem;
import game.text.PlaceGeneric;
import game.text.Player;
import game.text.PlayerGeneric;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGameSinglePlayer;
import game.text.exceptions.ActionException;
import game.text.vampire.items.Axe;
import game.text.vampire.items.Bookcase;
import game.text.vampire.items.BrickFireplace;
import game.text.vampire.items.CheddarCheese;
import game.text.vampire.items.CoilOfRope;
import game.text.vampire.items.FireInTheFireplace;
import game.text.vampire.items.Parapets;
import game.text.vampire.items.ParchmentScroll;
import game.text.vampire.items.Sign;
import game.text.vampire.items.SledgeHammer;
import game.text.vampire.items.Timepiece;
import game.text.vampire.items.Wine;
import game.text.vampire.places.Armory;
import game.text.vampire.places.EntranceHall;
import game.text.vampire.places.HiddenCorridor;
import game.text.vampire.places.Library;
import game.text.vampire.places.Study;
import game.text.vampire.places.Tower;

import java.util.ArrayList;
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
		out += "and hands.  It will accepts short phrases as commands and assumes\n";
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
	public void start() {
		setTime(8*60+1);
		super.start();
	}
	
	@Override
	public void moveDone() {
		addTime(1);
		super.moveDone();
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
		
		// verb "row" always means row the boat
		this.addAction(new ActionGeneric("row") { 
				@Override
				public Result execute(Actor actor) {
					return Vampire.this.getPlace("boat").executeAction("row", Vampire.this.getPlayer());
				}						
			}
		);
	}
	
	
	/**
	 * @param args
	 */	
	public static void main(String[] args) {
		final Vampire game = new Vampire();
		
		// create a player
		game.addPlayer(new PlayerGeneric("P1", game)); 
		
		// establish permissible directions for player to "go"
		game.addDirection(DirectionGeneric.North);
		game.addDirection(DirectionGeneric.East);
		game.addDirection(DirectionGeneric.South);
		game.addDirection(DirectionGeneric.West);
		game.addDirection(DirectionGeneric.Up);
		game.addDirection(DirectionGeneric.Down);
// can add new directions like this:	game.addDirection(new DirectionGeneric("Sideways"));
		
		// create items
		
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
		
		game.addItem("_parapets", new Parapets(game));  // no direct reference to parapets as game item
		game.addItem(new SledgeHammer(game));

		game.addItem("rope", new CoilOfRope(game));

		
		// create places populated with items in their initial positions
		
		try {
			game.addPlace(new EntranceHall(game));
			game.addPlace(new Study(game));
			game.addPlace(new Library(game));
			game.addPlace(new Armory(game));
			game.addPlace(new Tower(game));
			
			
			game.addPlace(new HiddenCorridor(game));

		} catch (ActionException e) {
			e.printStackTrace();
		}
		
		// Or anonymous class definitions can be used for items to override methods (since only
		// one specific item instance exists).

		game.addPlace(new PlaceGeneric("Lower Tower", ""));
		game.addItem(new ItemGeneric("Oar", ""));
		
		game.addPlace(new PlaceGeneric("Chapel", ""));
		game.addItem(new ItemGeneric("Holywater", "",
				new ArrayList<Action>(){{
					add(new ActionGeneric("get") {
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
						}
					);

					add(new ActionGeneric("drop") {
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
						}
					);
				}}
			));
		// set "throw" as alias for "drop"
		game.getItem("holywater").addAction("throw", game.getItem("holywater").getAction("drop"));
		game.addItem("water", game.getItem("holywater"));  // "water" = alias for "holywater"

		game.addPlace("_fireplace", new PlaceGeneric("Brick Fireplace", ""));	
		game.addItem(new ItemGeneric("Torch", ""));
		
//		game.addPlace(new PlaceGeneric("Hidden Corridor", ""));
//		game.addItem("rope", new CoilOfRope(game));

		game.addPlace(new PlaceDarkWithoutItem("Secret Passage", "", game.getItem("torch")));

		// 11

		game.addPlace(new PlaceDarkWithoutItem("Underground Lake Chamber", "", game.getItem("torch")));
		game.addPlace("lake", game.getPlace("underground lake chamber"));    // alias for flask of oil is "oil"		
		game.getPlace("lake").addAction(new ActionGeneric("swim") {
				@Override
				public Result execute(Actor actor) {
					if (game.getPlayer().getLocation() == game.getPlace("lake")) {
						// game over
						game.end(false, " You have Drowned in the ice cold water");
						return null;
					} else {
						return new ResultGeneric(false, "I don't know how to do that");
					}								
				}					
			}
		);
		
		game.addItem("_boat", new ItemGeneric("Boat", "") {
				@Override
				public void move(Container<Item> destination) throws ActionException {
					// disable player moving this item
					if (destination instanceof Place) {
						super.move(destination);
					} else {
						throw new ActionException("You can't get it");
					}
				}	
			}
		);
		
		game.addPlace(new PlaceGeneric("Boat", ""));
		game.getPlace("boat").addAction(new ActionGeneric("row") {
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
									((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
						} else {
							return new ResultGeneric(false, "You can't do that");
						}						
					} else {
						return new ResultGeneric(false, "You can't do that");
					}								
				}					
			}
		);
		game.getPlace("boat").addAction(new ActionGeneric("go") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).getLocation().hasOne("boat")) {  // if the boat is in the same room
						((Player) actor).setLocation(game.getPlace("boat"));
						return new ResultGeneric(true, ((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
					} else {
						return new ResultGeneric(false, "You can't go there");
					}
				}								
			}
		);
		game.getPlace("boat").addAction(new ActionGeneric("get") {
			@Override
			public Result execute(Actor actor) {
				return new ResultGeneric(false, "You can't get it");
			}								
		}
	);
		
		
		game.addPlace(new PlaceGeneric("Alchemist's Lab", ""));
		game.addItem(new ItemGeneric("Flask of Oil", ""));
		game.addItem("oil", game.getItem("flask of oil"));    // alias for flask of oil is "oil"

		game.addPlace(new PlaceGeneric("Storeroom", ""));
		game.addItem(new ItemGeneric("Crate", ""));
		game.addItem(new ItemGeneric("Bucket", ""));

		game.addPlace(new PlaceGeneric("Overhang", ""));
		game.addItem(new ItemGeneric("Nails", ""));

		// 16
		
		game.addPlace(new PlaceGeneric("Gallery", ""));
		game.addItem(new ItemGeneric("Tapestry", ""));

		game.addPlace(new PlaceGeneric("Antechamber", ""));
		game.addItem(new ItemGeneric("Rusty Door", ""));

		game.addPlace(new PlaceGeneric("Vampire's Tomb", ""));
		game.addItem(new ItemGeneric("Closed Coffin", ""));

		game.addPlace(new PlaceGeneric("Torture Chamber", ""));
		game.addItem(new ItemGeneric("Rat", "") {
				@Override
				public void move(Container<Item> destination) throws ActionException {
					// disable player moving this item
					if (destination instanceof Place) {  
						super.move(destination);
					} else {
						throw new ActionException("You can't get it");
					}
				}	

				@Override
				public String look() {
					if (this.getLocation() != game.getPlayer().getLocation()) {
						return "I don't see any "+this.getName();
					}

					if (this.getData("has_key") != null) {   // if the rat still has the key
						game.getItem("key").setData("can_see", new Boolean(true));  // looking at it makes the key visible in the room
					}
					// looking at the rat when it does or doesn't have the key
					// causes the key to jump to the same room as the rat (bug in the old game!)
					try {
						game.getItem("key").move(game.getItem("rat").getLocation());
					} catch (ActionException e) {
						// if this doesn't work, ignore it - it's a bug anyway!
					}
					return "A Key in it's mouth!";
				}	
			}
		);		
		game.getItem("rat").setData("has_key", new Boolean(true));   // the rat has the key in it's mouth to start the game
		game.addItem(new ItemGeneric("Key", "") {
				@Override
				public void move(Container<Item> destination) throws ActionException {
					if (destination instanceof Place) {
						super.move(destination);  // always allow key to be dropped any place
					} else {
						// player tries to pick up the key
						if (this.getData("can_see") != null) {  // player must "look" at the rat first to see the key 
							Place keyPlace = (Place) this.getLocation();
							if (game.getItem("rat").getLocation() != keyPlace) {
								// if the key is in a room without the rat and the player tries to pick it up,
								// the rat moves to that room and has the key again!
								game.getItem("rat").move(keyPlace);
								throw new ActionException("The Rat has it");
							}
							if (((game.getItem("rat").getLocation() == keyPlace) && (game.getItem("cheese").getLocation() == keyPlace))) {
								// key, rat, and cheese must all be in the room together to pick up the key!
								super.move(destination);
							} else {
								throw new ActionException("The Rat has it");
							}
						}
					}
				}	
	
				@Override
				public String look() {
					if (this.getData("can_see") == null) {   // if the player hasn't looked at the key yet
						return null;     // not visible in the room 
					} else {
						return this.getName();
					}
				}	
			}
		);		
		
		
		// put items in their initial places
	
		try {
			// 1
//			game.getItem("timepiece").move(game.getPlace("entrance hall"));
//			game.getItem("sign").move(game.getPlace("entrance hall"));

//			game.getItem("fireplace").move(game.getPlace("study"));
//			game.getItem("_fire").move(game.getPlace("study"));
//			game.getItem("cheddar cheese").move(game.getPlace("study"));
//			game.getItem("wine").move(game.getPlace("study"));

//			game.getItem("parchment scroll").move(game.getPlace("library"));
//			game.getItem("bookcase").move(game.getPlace("library"));
			
//			game.getItem("axe").move(game.getPlace("armory"));

//			game.getItem("_parapets").move(game.getPlace("tower"));
//			game.getItem("sledge hammer").move(game.getPlace("tower"));
			
			// 6
			
			game.getItem("oar").move(game.getPlace("lower tower"));
			
			game.getItem("holywater").move(game.getPlace("chapel"));

			game.getItem("rope").move(game.getPlace("hidden corridor"));

			game.getItem("torch").move(game.getPlace("_fireplace")); 

			// nothing in secret passage

			// 11
			
			game.getItem("_boat").move(game.getPlace("underground lake chamber"));
			
			// nothing in boat
			
			game.getItem("flask of oil").move(game.getPlace("alchemist's lab"));

			game.getItem("crate").move(game.getPlace("storeroom"));
			game.getItem("bucket").move(game.getPlace("storeroom"));

			game.getItem("nails").move(game.getPlace("overhang"));

			// 16
			
			game.getItem("tapestry").move(game.getPlace("gallery"));

			game.getItem("rusty door").move(game.getPlace("antechamber"));

			game.getItem("closed coffin").move(game.getPlace("vampire's tomb"));

			game.getItem("rat").move(game.getPlace("torture chamber"));

		} catch (ActionException e) {}	
		
		
		// Initial map of all of rooms
		
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
		
		// initial player location	
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

		
		game.getPlayer().setLocation(game.getPlace("lake"));
		try {
			game.getItem("torch").move(game.getPlayer());		
			game.getItem("oar").move(game.getPlayer());		
		} catch (ActionException e) {
			e.printStackTrace();
		}
		
		
		// start the game
		game.start();
		
	}

}


/*
110 PRINT , "Welcome to the VAMPIRE'S CASTLE Adventure": PRINT : PRINT
112 INPUT "Do you need the instructions"; A$: A$ = LEFT$(A$ + " ", 1)
114 IF A$ = "y" OR A$ = "Y" THEN GOSUB 2000:  ELSE PRINT
120 DIM D$(19), O$(31), L(25), P(19, 6): L = 1

Room Names: D(room#)

130 FOR x = 1 TO 19: READ D$(x): NEXT x
140 DATA Entrance Hall,Study,Library,Armory,Tower  5
150 DATA Lower Tower,Chapel,Brick Fireplace  8
160 DATA Hidden Corridor,Secret Passage  10
170 DATA Underground Lake Chamber,Boat,Alchemist's Lab  13
180 DATA Storeroom,Overhang,Gallery,Antechamber,Vampire's Tomb  18
190 DATA Torture Chamber 19

Things (first 6 are directions): O$(Item#)
Location of things: L(Item#-6)

200 FOR x = 1 TO 31: READ O$(x): IF x > 6 THEN READ L(x - 6)
220 NEXT: T2 = 8: R = 11: WS$ = "Wooden Stakes"
230 DATA North,South,East,West,"Up ",Down,Sledge Hammer,5
240 DATA Timepiece,1,Coil of Rope,9,Parchment Scroll,3,Axe,4,Oar,6,Key,99
250 DATA Holywater,7,Flask of Oil,13,Crate,14,Bucket,14
260 DATA Torch,8,Nails,15,Tapestry,16,Boat,11,Rusty Door,17
270 DATA Closed Coffin,18,Fire in the Fireplace,2
280 DATA Bookcase,3,Sign,1,Parapets,5,Brick Fireplace,2
290 DATA Rat,19,Wine,2,Cheddar Cheese,2

Directions from Rooms to other rooms: P(room#, direction#)

300 FOR Y = 1 TO 19: FOR x = 1 TO 6: READ P(Y, x): NEXT x, Y
310 DATA ,,3,2,,,,,1,,,,,,4,1,,,,,5,3,,,,,,4,,,,7,,,5,,6,,,,4,
320 DATA ,2,,,,,13,,,,3,,11,8,,19,,,,10,,,,,,11,,,,,14,9,,,,
330 DATA ,13,,,2,,,,,,,16,,,,,,,,16,,,,,,17,,,,,,,10,13,,

Verb lookup to get # of action:

340 W$ = "GO GETLOODROHITOPETIEPUSKILOILROWCLITAKREATHRBRE"

???

350 N$ = "N  01S  02E  03W  04U  05D  06HAM07SCR10AX 11WAT14OIL15DOO22COF23VAM23FIR28"

Thing prefix to get number of thing:

355 O$ = "NORSOUEASWESUP DOWSLETIMROPPARAXEOARKEYHOLFLACRABUCTORNAITAPBOARUSCLOFIRBOOSIGPARBRIRATWINCHE"


L = current room player is in

A$ = the verb (three letter code)

S = object of the verb (either a direction 0-6 or an item)


360 COLOR 2, 0: PRINT "You are in the "; D$(L): Y = 0: L2 = L
370 IF L(12) = 0 OR L < 10 OR (L > 12 AND L <> 18) THEN 390
380 PRINT "It's Dark! you can't see": GOTO 440
390 PRINT "You see "; : FOR x = 1 TO 25: IF L(x) = L2 THEN PRINT O$(x + 6); ", "; : Y = Y + 1
400 NEXT: IF Y = 0 THEN PRINT "nothing interesting." ELSE PRINT CHR$(29); CHR$(29); " "
420 PRINT "Obvious exits are: "; : FOR x = 1 TO 6: IF P(L, x) > 0 THEN PRINT O$(x); " ";
430 NEXT: PRINT
440 S = 0: F = 0: COLOR 10, 0: PRINT : INPUT "What do you want to do"; A$: PRINT : COLOR 2
450 A$ = A$: B$ = "   ": FOR x = 1 TO LEN(A$)
460 IF MID$(A$, x, 1) = " " THEN B$ = MID$(A$ + "   ", x + 1, 3)
490 NEXT: IF LEN(A$) = 1 THEN B$ = A$ + "  ": A$ = "GO "
500 GOSUB 1800: L2 = L: T1 = T1 + 1: IF T1 = 60 THEN T1 = 0: T2 = T2 + 1
510 IF T2 = 12 THEN COLOR 7, 0: PRINT " It's midnight: the Vampire is awake, He's at your neck "; : GOTO 1750
520 IF A$ = "SWI" AND L = 11 THEN COLOR 7, 0: PRINT " You have Drowned in the ice cold water "; : GOTO 1750

look to find object in N$

530 x = INSTR(N$, B$): IF x MOD 5 = 1 THEN S = VAL(MID$(N$, x + 3, 2)): GOTO 590

try to find object in O$
  
560 x = INSTR(O$, B$): IF x MOD 3 = 1 THEN S = (x + 2) / 3

find verb is W$

590 x = INSTR(W$, A$): IF x MOD 3 = 1 THEN F = (x + 2) / 3
620 IF F > 11 THEN F = F - 11
630 IF A$ <> "INV" THEN 680

Inventory

640 PRINT "You are carrying: "; : A = 0: FOR x = 1 TO 25
650 IF L(x) = 0 THEN PRINT O$(x + 6); ", "; : A = A + 1
660 NEXT: IF A = 0 THEN PRINT "nothing";
670 PRINT : GOTO 440

branch to verb "handlers"

			  GO   GET  LOO   DRO   HIT   OPE   TIE   PUS   KIL   OIL   ROW   CLITAKREATHRBRE

680 ON F GOTO 720, 890, 1100, 1240, 1300, 1390, 1480, 1560, 1610, 1680, 1720
690 PRINT "I don't know how to do that": GOTO 440
700 PRINT "I don't know that word": GOTO 440


710 REM  -GO-
720 IF S < 1 OR S > 6 THEN 760
730 IF P(L, S) > 0 THEN L = P(L, S): GOTO 360
740 PRINT "You can't go that way": GOTO 440
750 PRINT "You see nothing special": GOTO 440
760 IF S <> 28 OR L <> 2 THEN 790
770 IF FI = 0 THEN COLOR 0, 7: PRINT " You have Burned to Death "; : GOTO 1750
780 L = 8: GOTO 360
790 IF S <> 27 OR L <> 5 THEN 820
800 IF LEFT$(O$(27), 1) = "R" THEN L = 6: PRINT "Climbed down rope": PRINT : GOTO 360
810 COLOR 0, 7: PRINT " You fell and Died "; : GOTO 1750
820 IF S = 21 AND L(15) = L THEN L = 12: GOTO 360
830 IF L = 16 AND B$ = "OVE" THEN 860
840 IF S < 1 THEN 700
850 PRINT "You can't go there": GOTO 440
860 IF L(10) = L AND O$(16) = "Crate" THEN L = 15: GOTO 360
870 PRINT "It's a little too high": GOTO 440


880 REM  -GET-
890 IF S < 7 THEN GOTO 700
900 IF C > 6 THEN PRINT "You can't carry any more": GOTO 440
910 IF L(S - 6) <> L THEN 1070
920 IF S <> 14 THEN 960
940 V$ = "In": GOSUB 1850: IF A$ = "BUC" AND L(11) = 0 THEN 1080
950 PRINT "You can't do that": GOTO 440
960 IF S <> 20 THEN 990
970 IF TA = 0 THEN PRINT "It's nailed to an overhang": GOTO 440
980 P(16, 1) = 17: PRINT "AHA! - A hole in the wall": PRINT : GOTO 1080
990 IF S <> 19 THEN 1020
1000 IF L(1) <> 0 THEN PRINT "You have no hammer": GOTO 440
1010 IF L(13) = 15 THEN TA = 1: PRINT "The tapestry is loose": GOTO 1080
1020 IF S <> 13 THEN 1050
1030 L(23) = L: IF L(25) = L THEN 1080
1040 PRINT "The Rat has it": GOTO 440
1050 IF S > 20 AND S < 30 THEN PRINT "You can't get it": GOTO 440 ELSE 1080
1070 PRINT "I don't see any "; O$(S): GOTO 440
1080 C = C + 1: L(S - 6) = 0: PRINT "You got the "; O$(S): GOTO 440


1090 REM  -LOOK-
1100 IF S < 7 THEN 360
1120 IF S = 26 AND L = 1 THEN PRINT "'The Vampire Wakes at Midnight'": GOTO 440
1130 IF S <> 29 THEN 1160
1140 IF L(23) <> L THEN 1070
1150 PRINT "A Key is in it's mouth!": L(7) = L: GOTO 440
1160 IF S <> 10 THEN 1200
1170 IF L(4) = 0 THEN 1190
1180 PRINT "You don't have it": GOTO 440
1190 PRINT "The Scroll reads: 'Not all exits are obvious.'": GOTO 440
1200 IF S <> 8 THEN 750
1210 IF L(2) <> 0 THEN 1180
1220 PRINT USING "The time is now ##:##"; T2; T1: GOTO 440


1230 REM  -DROP-
1240 IF S < 7 THEN GOTO 700    if object is a direction
1250 IF L(S - 6) <> 0 THEN 1180     if the object is not held by player
1260 IF S <> 14 OR L <> 2 THEN 1280   if (object is bucket and room is Study) 
1270 O$(24) = "Smoldering Ashes": FI = 1: L(8) = 99: C = C - 1: GOTO 360
1280 PRINT "Okay, the "; O$(S); " is on the "; D$(L); " floor": L(S - 6) = L: C = C - 1: GOTO 440


1290 REM  -HIT-
1300 IF S < 7 THEN 700
1310 IF L(S - 6) <> L AND (L <> 8 OR S <> 28) THEN 1070
1330 V$ = "With": GOSUB 1850: IF A$ <> "AXE" OR S <> 16 OR L(5) <> 0 THEN 1350
1340 O$(16) = WS$: N$ = N$ + "WOO16STA16": GOTO 360
1350 IF (A$ <> "SLE" AND A$ <> "HAM") OR S <> 28 OR L(1) <> 0 THEN 1370
1360 O$(28) = "Broken Fireplace": P(8, 1) = 10: D$(8) = O$(28): GOTO 360
1370 PRINT "Nothing happened": GOTO 440



1380 REM  -OPEN-
1390 IF S < 7 THEN 700
1400 IF L(S - 6) <> L THEN 1070
1410 IF S <> 23 THEN 1440
1420 IF L(7) <> 0 THEN PRINT "The coffin is locked ... "; : GOTO 1370
1430 O$(23) = "Vampire in the Coffin": GOTO 360
1440 IF S <> 22 THEN 1370
1450 IF OI = 0 THEN PRINT "Too much rust ... "; : GOTO 1370
1460 O$(22) = "Open Door": P(17, 1) = 18: GOTO 360


1470 REM  -TIE-
1480 IF S < 7 THEN 700
1490 IF L(S - 6) <> 0 THEN 1180
1500 IF S <> 9 THEN 950
1520 V$ = "To": GOSUB 1850: IF A$ <> "PAR" THEN 950
1530 L(3) = 99: C = C - 1: O$(27) = "Rope tied to the Parapet": N$ = N$ + "ROP27"
1540 PRINT O$(27); "!": GOTO 440



1550 REM  -PUSH-
1560 IF S < 7 THEN 700
1570 IF L(S - 6) <> L THEN 1070
1580 IF S <> 25 THEN 1370
1590 PRINT "Aha! - You have revealed a Doorway": PRINT : P(L, 6) = 9: GOTO 360



1600 REM  -KILL-
1610 IF S < 7 THEN 700
1620 IF S <> 23 OR LEFT$(O$(23), 1) <> "V" THEN 950
1630 V$ = "With": GOSUB 1850
1640 IF (A$ = "WOO" OR A$ = "STA") AND L(10) = 0 AND O$(16) = WS$ THEN 1660
1650 COLOR 0, 7: PRINT " You Failed!  The Vampire awakes and sucks your Blood! "; : GOTO 1750
1660 COLOR 26: PRINT "Congratulations!  You have killed the Vampire": GOTO 1750



1670 REM  -OIL-
1680 IF L(9) <> 0 OR L <> 17 OR S <> 22 THEN 950
1690 PRINT "The Door squeaks Open": PRINT
1700 OI = 1: O$(22) = "Open Door": P(17, 1) = 18: GOTO 360



1710 REM  -ROW-
// must be in the boat and oar must be picked up (L(6)==0)
1720 IF L <> 12 OR L(6) <> 0 THEN 950    
// R = 11 initially, then R = 16, then next time R = 11.  This alternates between lake chamber and gallery in 
 * 														  terms of player's position
1730 R = 27 - R: L = R: PRINT "You have rowed to the "; D$(L)
// move location of boat to players new location
1740 PRINT : L(15) = L: GOTO 360  



1750 COLOR 10, 0: PRINT : PRINT : INPUT "Would you like to try again"; A$: GOSUB 1800
1760 IF LEFT$(A$, 1) = "Y" THEN RUN 100
1770 IF LEFT$(A$, 1) = "R" THEN T1 = T1 - 2: GOTO 440
1780 KEY ON: END


1800 REM  - MAKE INPUT U/C -
1810 A$ = LEFT$(A$ + "   ", 3): FOR I = 1 TO 3
1820 CH = ASC(MID$(A$, I, 1)): IF CH > 96 THEN MID$(A$, I, 1) = CHR$(CH - 32)
1830 CH = ASC(MID$(B$, I, 1)): IF CH > 96 THEN MID$(B$, I, 1) = CHR$(CH - 32)
1840 NEXT: RETURN


1850 REM - GET "WHAT" -
1860 COLOR 10: PRINT TAB(40); CHR$(30); CHR$(30); : PRINT "-- "; V$; " what"; : INPUT A$
1870 COLOR 2: PRINT : GOSUB 1800: RETURN
2000 PRINT : PRINT : COLOR 2



2010 PRINT "VAMPIRE'S CASTLE has a concealed goal.  You learn what the goal is"
2020 PRINT "by exploring your surroundings.  The computer will act as your eyes"
2030 PRINT "and hands.  It will accepts short phrases as commands and assumes"
2040 PRINT "that the first word is a verb and the last word is the object."
2050 PRINT "For example: READ THE SIGN.  The computer has a vocabulary of about"
2060 PRINT "70 words.  Some of the more important words you should know before"
2070 PRINT "you start ðlaying are: GET <object>, DROP <object>, LOOK <object>"
2080 PRINT "or just LOOK, GO <direction> or <place>, and INVENTORY (tells what"
2084 PRINT "you are carrying).  The computer knows the abbreviations: E, W, N,"
2086 PRINT "S, U and D for GO EAST, GO WEST, etc.": PRINT
2090 PRINT "The computer's vocabulary is good, but limited.  If you are having"
2100 PRINT "trouble doing something, try re-phrasing the command or you may"
2110 PRINT "need some object to accomplish the task.  By the way, the computer"
2120 PRINT "only looks at the first 3 letters of each word.": PRINT
2130 RETURN

 
 */
