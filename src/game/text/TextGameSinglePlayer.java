package game.text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class TextGameSinglePlayer extends ActionableGeneric implements TextGame {
    private InputStream in = System.in;
    private OutputStream out = System.out;
    private Grammar grammar;

    private Player player;
    private Map<String, Direction> directions = new HashMap<String, Direction>();
    private Map<String, Item> items = new HashMap<String, Item>();
    private Map<String, Place> places =  new HashMap<String, Place>();
    
    private boolean isStarted = false;
    private boolean isEnded = false;
    private boolean isWon = false;
    
	private int moves = 0;
    
	
    public TextGameSinglePlayer(Collection<Place> places) {
    	super();
		for (Place place : places) {
			this.places.put(place.getName(), place);
		}
		grammar = new GrammarSimple(this);  // default grammar
    }
    
    public TextGameSinglePlayer() {
    	super();
		grammar = new GrammarSimple(this);  // default grammar    	
    }
    
	@Override
	public void setDefaultActions() {
		this.addAction(new ActionGeneric("look") {
				@Override
				public Result execute(Actor actor) {
					this.incrementCount();  // no limit on how many times this can be executed
					// call "look" action, not just look() method on player location, so the actor (player) can be passed along
					return new ResultGeneric(true, TextGameSinglePlayer.this.getPlayer().getLocation().getAction("look").execute(actor).getMessage());
				}						
			}
		);

		this.addAction(new ActionGeneric("read") {
				@Override
				public Result execute(Actor actor) { 
					// alias to "look"
					return TextGameSinglePlayer.this.getAction("look").execute(actor);
				}
			}
		);
			
		this.addAction(new ActionGeneric("inventory") {
			@Override
			public Result execute(Actor object) {
				this.incrementCount();
				// no limit on how many times this can be executed
				return new ResultGeneric(true, TextGameSinglePlayer.this.getPlayer().basketContentsLook());
			}						
		}
	);

		this.addAction(new ActionGeneric("quit") {
				@Override
				public Result execute(Actor actor) {
					output("\nAre you sure you want to quit and exit the game (y or n)? ");
					String input = input();
					if (input.startsWith("y")) {
						// quit the game here
						TextGameSinglePlayer.this.end(false, "\nOK, goodbye!\n");
					} else {
						return new ResultGeneric(true, "\nOK, let's keep exploring!\n");
					}
					return null;
				}						
			}
		);
	}
    
    public void start() {
    	isStarted = true;
    	moves = 0;
    	mainEventLoop();
    }
    
    public void end(boolean won, String message) {
    	isEnded = true;
    	isWon = won;
    	output("\n"+message+"\n");
    }

    public boolean ended() {
    	return isEnded;
    }

    protected void mainEventLoop() {
    	String input;
    	output(welcome());
    	input = input().toLowerCase();
    	if (input.startsWith("y")) {
    		output(instructions());
    	}
    	output("\n");
    	output(player.getLocation().getAction("look").execute(player).getMessage());
    	while (!ended()) {
    		output("\n\nWhat do you want to do? ");
    		input = input();
    		// parse input string
    		if (grammar.parse(input)) {
        		// take the action
    			Result result = grammar.getVerb().execute(getPlayer());  //TODO: need to throw exceptions from here too
    			if (result != null) {
    				output("\n"+result.getMessage());
    			}
    		} else {
    			if (grammar.getObject() != null) {
    				output("I don't know how to do that.");
    			} else {
    				
    				// TODO: need to check against entire vocabulary!
    				// and output things like 
    				/*
    				 * 	I don't know that word (known verb, unknown object), 
    				 * I don't know how to do that (unknown verb, known object) 
    				 * You can't do that, etc.) as exceptions
    				 */

    				output("I don't know that word.");    				
    			}
    		}
    		moveDone();
    	}
    }
    
    public void moveDone() {
    	moves++;
    }

    public int getMoveCount() {
    	return moves;
    }

    public void addPlayer(Player player) {
		this.player = player;
	}

	public void removePlayer(Player player) {
		this.player = null;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public void addDirection(String key, Direction direction) {
		this.directions.put(key, direction);
	}

	public void addDirection(Direction direction) {
		this.directions.put(direction.getName().toLowerCase(), direction);
	}

	public Direction getDirection(String key) {
		return this.directions.get(key);
	}

	public Direction matchDirection(String keyStartsWith) {
		for (String dirName : directions.keySet()) {
			if (dirName.startsWith(keyStartsWith)) {
				return directions.get(dirName);
			}
		}
		return null;
	}

	public void removeDirection(String key) {
		this.directions.remove(key);
	}
	
	public void removeDirection(Direction direction) {
		this.directions.remove(direction.getName().toLowerCase());
		
	}

	public Collection<Direction> getDirections() {
		return this.directions.values();
	}	
	
	public void addItem(String key, Item item) {
		this.items.put(key, item);
	}
	
	public void addItem(Item item) {
		addItem(item.getName().toLowerCase(), item);
	}
	
	public Item getItem(String key) {
		return this.items.get(key);		
	}

	public Item matchItem(String keyStartsWith) {
		for (String itemName : items.keySet()) {
			if (itemName.startsWith(keyStartsWith)) {
				return items.get(itemName);
			}
		}
		return null;
	}
	
	public Collection<Item> getItems() {
		return this.items.values();
	}
	
	public void removeItem(String key) {
		this.items.remove(key);
	}

	public void removeItem(Item item) {
		this.items.remove(item.getName().toLowerCase());
	}
	

	public void addPlace(String key, Place place) {
		this.places.put(key, place);
	}
	
	public void addPlace(Place place) {
		this.places.put(place.getName().toLowerCase(), place);
	}
	
	public Place getPlace(String key) {
		return this.places.get(key);
		
	}

	public Place matchPlace(String keyStartsWith) {
		for (String placeName : places.keySet()) {
			if (placeName.startsWith(keyStartsWith)) {
				return places.get(placeName);
			}
		}
		return null;
	}

	public Collection<Place> getPlaces() {
		return this.places.values();
	}
	
	public void removePlace(String key) {
		this.places.remove(key);
	}

	public void removePlace(Place place) {
		this.places.remove(place.getName().toLowerCase());
	}

	public void output(String msg) {
		OutputStreamWriter output = new OutputStreamWriter(this.out);
		BufferedWriter writer = new BufferedWriter(output);
		try {
			writer.write(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public String input() {
		String string = "";
		InputStreamReader input = new InputStreamReader(this.in);
		BufferedReader reader = new BufferedReader(input);

		try	{
			string = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;		
	}
	
	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
	}

	public String getWordPrefix(String word) {
		return word.substring(0, 2);  // default is first three letters
	}
	
}

