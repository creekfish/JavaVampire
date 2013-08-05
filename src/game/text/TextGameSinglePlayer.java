package game.text;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public abstract class TextGameSinglePlayer extends ActionableGeneric implements TextGame {
    private Grammar grammar;

    private Player player;
    private Map<String, Direction> directions = new HashMap<String, Direction>();
    private Map<String, Item> items = new HashMap<String, Item>();
    private Map<String, Place> places =  new HashMap<String, Place>();
    
    private boolean isGameStarted = false;
    private boolean isGameEnded = false;
    private boolean isGameWon = false;
    
    private ActionContinued continuedAction = null;
    
	private int turns = 0;
    
	
    public TextGameSinglePlayer(Collection<Place> places) {
    	super();
		for (Place place : places) {
			this.places.put(place.getName(), place);
		}
		grammar = new GrammarSimple(this);  // default grammar
		resetContinuedAction();
    }
    
    public TextGameSinglePlayer() {
    	super();
		grammar = new GrammarSimple(this);  // default grammar    	
		resetContinuedAction();
    }
    
	@Override
	public void setDefaultActions() {
		this.addAction(new ActionInitial("look") {
				@Override
				public Result execute(Actor actor) {
					this.incrementCount();  // no limit on how many times this can be executed
					// call "look" action, not just look() method on player location, so the actor (player) can be passed along
					return new ResultGeneric(true, TextGameSinglePlayer.this.getPlayer().getLocation().getInitialAction("look").execute(actor).getMessage());
				}						
			}
		);

		this.addAction(new ActionInitial("read") {
				@Override
				public Result execute(Actor actor) { 
					// alias to "look"
					return TextGameSinglePlayer.this.getInitialAction("look").execute(actor);
				}
			}
		);
			
		this.addAction(new ActionInitial("inventory") {
				@Override
				public Result execute(Actor object) {
					this.incrementCount();
					// no limit on how many times this can be executed
					return new ResultGeneric(true, TextGameSinglePlayer.this.getPlayer().basketContentsLook());
				}						
			}
		);
	}
    
	/**
	 * Initialize all of the game objects and configuration.
	 */
	public void initialize()
	{
    	isGameStarted = true;
    	isGameEnded = false;
    	isGameWon = false;
    	turns = 0;		
	}
    
    public boolean startTurn() 
    {
    	// nothing to do here for this implementation
    	if (isGameEnded) {
    		return false;  // return false if end of game
    	}
    	return true;
    }
    
    public Result takeTurn(String textInput)
    {
		// parse input string
		if (hasContinuedAction() || grammar.parse(textInput)) {
			Result result;
			if (!hasContinuedAction()) {
	    		// take the action
				result = grammar.getVerb().execute(getPlayer());  //TODO: need to throw exceptions from here too				
			} else {
				result = getContinuedAction().execute(getPlayer(), textInput);
				resetContinuedAction();
			}
			if (result instanceof ResultPartial) {
				setContinuedAction(((ResultPartial) result).getContinuedAction());
			}
			if (result != null) {
				return result;
			}
			return new ResultGeneric(false, "ERROR: Action failed to produce a result!");
		} else {
			if (grammar.getObject() != null) {
				return new ResultGeneric(false, "I don't know how to do that.");
			} else {
				
				// TODO: need to check against entire vocabulary!
				// and output things like 
				/*
				 * 	I don't know that word (known verb, unknown object), 
				 * I don't know how to do that (unknown verb, known object) 
				 * You can't do that, etc.) as exceptions
				 */

				return new ResultGeneric(false, "I don't know that word.");
			}
		}
    	
    }
    
    public boolean endTurn() {
    	if (!hasContinuedAction()) {
    		// completes a turn if the action is not being continued, getting more input
    		turns++;
    	}
    	if (isGameEnded) {
    		return false;  // return false if end of game
    	}
    	return true;
    }

    public void end(boolean won) {
    	isGameEnded = true;
    	isGameWon = won;
    }

    public boolean isEnded() {
    	return isGameEnded;
    }
    
    public void resetEnded() {
    	isGameEnded = false;
    }
    
    public int getTurnCount() {
    	return turns;
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
	
	public void setGrammar(Grammar grammar) {
		this.grammar = grammar;
	}

	public String getWordPrefix(String word) {
		return word.substring(0, 2);  // default is first three letters
	}

	private ActionContinued getContinuedAction() {
		return continuedAction;
	}

	private void setContinuedAction(ActionContinued continuedAction) {
		this.continuedAction = continuedAction;
	}
	
	private void resetContinuedAction() {
		this.continuedAction = null;
	}

	public boolean hasContinuedAction() {
		return (this.continuedAction != null);
	}
	
	
}

