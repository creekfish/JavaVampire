package game.text.vampire;
import game.text.ActionGeneric;
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
	public void restart() {
		setTime(8*60+1);
		super.restart();
	}
	
    @Override
    public void end(boolean won, String message) {
    	super.end(won, message); 
    	output("Would you like to try again? ");
    	String choice = input().toLowerCase().substring(0, 1);
    	if (choice.equals("y")) {
    		this.restart();
    	}
    	if (choice.equals("r")) {
    		this.setTime(this.gameTime-2);
    	}
     }    


    @Override
	public void moveDone() {
		addTime(1);
		if (gameTime >= 12*60) {
			this.end(false, " It's midnight: the Vampire is awake, He's at your neck \n");
		}
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
		
		// verb "row" always means row the boat (flaw in old game?)
		this.addAction(new ActionGeneric("row") { 
				@Override
				public Result execute(Actor actor) {
					return Vampire.this.getPlace("boat").executeAction("row", Vampire.this.getPlayer());
				}						
			}
		);
		this.addAction(new ActionGeneric("open") {
			@Override
			public Result execute(Actor actor) {
				return new ResultGeneric(false, "Nothing happened\n");
			}								
		});	
	}
	
	@Override
	public void initialize() {
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
	
	public static void main(String[] args) {
		final Vampire game = new Vampire();
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
1 Entrance Hall
2 Study
3 Library
4 Armory
5Tower  
6 Lower Tower
7 Chapel 
8 Brick Fireplace
9 Hidden Corridor
10 Secret Passage
11 Underground Lake Chamber
12 Boat
13 Alchemist's Lab 
14 Storeroom
15 Overhang
16 Gallery
17 Antechamber
18 Vampire's Tomb
19 Torture Chamber

130 FOR x = 1 TO 19: READ D$(x): NEXT x
140 DATA Entrance Hall,Study,Library,Armory,Tower  5
150 DATA Lower Tower,Chapel,Brick Fireplace  8
160 DATA Hidden Corridor,Secret Passage  10
170 DATA Underground Lake Chamber,Boat,Alchemist's Lab  13
180 DATA Storeroom,Overhang,Gallery,Antechamber,Vampire's Tomb  18
190 DATA Torture Chamber 19

Things (first 6 are directions): O$(Item#)
Location of things: L(Item#-6)

L(X)/S
1/7 Sledge Hammer,5
2/8 Timepiece,1,
3/9 Coil of Rope,9,
4/10 Parchment Scroll,3,
5/11 Axe,4,
6/12 Oar,6,
7/13 Key,99
8/14 Holywater,7,
9/15 Flask of Oil,13,
10/16 Crate,14,
11/17 Bucket,14
12/18 Torch,8,
13/19 Nails,15,
14/20 Tapestry,16,
15/21 Boat,11,
16/22 Rusty Door,17
17/23 Closed Coffin,18,
18/24 Fire in the Fireplace,2
19/25 Bookcase,3,
20/26 Sign,1,
21/27 Parapets,5,
22/28 Brick Fireplace,2
23/29 Rat,19,
24/30 Wine,2,
25/31 Cheddar Cheese,2


200 FOR x = 1 TO 31: READ O$(x): IF x > 6 THEN READ L(x - 6)
220 NEXT: T2 = 8: R = 11: WS$ = "Wooden Stakes"
230 DATA North,South,East,West,"Up ",Down,Sledge Hammer,5
240 DATA Timepiece,1,Coil of Rope,9,Parchment Scroll,3,Axe,4,Oar,6,Key,99
250 DATA Holywater,7,Flask of Oil,13,Crate,14,Bucket,14
260 DATA Torch,8,Nails,15,Tapestry,16,Boat,11,Rusty Door,17
270 DATA Closed Coffin,18,Fire in the Fireplace,2
280 DATA Bookcase,3,Sign,1,Parapets,5,Brick Fireplace,2
290 DATA Rat,19,Wine,2,Cheddar Cheese,2

Directions Mapped from Rooms to other rooms: P(room#, direction#)

Direction #s:
1 = N
2 = S
3 = E
4 = W
5 = U
6 = D

300 FOR Y = 1 TO 19: FOR x = 1 TO 6: READ P(Y, x): NEXT x, Y
1 -,-,3,2,-,-,			Entrance Hall
2 -,-,1,-,-,-,			Study
3 -,-,4,1,-,-,			Library
4 -,-,5,3,-,-,			Armory
5 -,-,-,4,-,-,			Tower
6 -,7,-,-,5,-,			Lower Tower
7 6,-,-,-,4,-,			Chapel
8 -,2,-,-,-,-,			Brick Fireplace
9 13,-,-,-,3,-,			Hidden Corridor
10 11,8,-,19,-,-,		Secret Passage
11 -,10,-,-,-,-,		Underground Lake Chamber
12 -,11,-,-,-,-,		Boat
13 14,9,-,-,-,-,		Alchemist's Lab
14 -,13,-,-,2,-,		Storerooom
15 -,-,-,-,-,16,		Overhang
16 -,-,-,-,-,-,			Gallery
17 -,16,-,-,-,-,		Antechamber
18 -,17,-,-,-,-,		Vampire's Tomb
19 -,-,10,13,-,-		Torture Chamber


Verb lookup to get # of action:

340 W$ = "GO GETLOODROHITOPETIEPUSKILOILROWCLITAKREATHRBRE"

???

350 N$ = "N  01S  02E  03W  04U  05D  06HAM07SCR10AX 11WAT14OIL15DOO22COF23VAM23FIR28"

Thing prefix to get number of thing:

355 O$ = "NORSOUEASWESUP DOWSLETIMROPPARAXEOARKEYHOLFLACRABUCTORNAITAPBOARUSCLOFIRBOOSIGPARBRIRATWINCHE"


L = current room player is in

A$ = the verb (three letter code)

S = object of the verb (either a direction 0-6 or an item)


Look around:

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
		// in the tower and go -> parapets
800 IF LEFT$(O$(27), 1) = "R" THEN L = 6: PRINT "Climbed down rope": PRINT : GOTO 360  // if the rope is tied to parapets, climb down to lower tower, else fell and died
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
960 IF S <> 20 THEN 990      // item 20 is "tapestry"
970 IF TA = 0 THEN PRINT "It's nailed to an overhang": GOTO 440
980 P(16, 1) = 17: PRINT "AHA! - A hole in the wall": PRINT : GOTO 1080
990 IF S <> 19 THEN 1020      // item 19 is "nails"
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
// crate with axe = wooden stakes
1330 V$ = "With": GOSUB 1850: IF A$ <> "AXE" OR S <> 16 OR L(5) <> 0 THEN 1350
1340 O$(16) = WS$: N$ = N$ + "WOO16STA16": GOTO 360
// fire place with sledge hammer = broken fireplace and new passageway
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
1610 IF S < 7 THEN 700   "I don't know that word"
1620 IF S <> 23 OR LEFT$(O$(23), 1) <> "V" THEN 950     /// "You can't do that"
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
