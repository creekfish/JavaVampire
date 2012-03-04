package game.text;

import game.text.exceptions.ActionException;


public class PlayerGeneric extends ActionableContainerGeneric<Item> implements Player {
	private String name;
	private Game game;
	private Place location;
	private int maxItems = 4;    // maximum number of items allowed in basket
	
	public PlayerGeneric(String name, Game game) {
		super();
		this.name = name;
		this.game = game;
	}

	@Override
	public void setDefaultActions() {
		this.addAction(new ActionGeneric("look") {
				@Override
				public Result execute(Actor actor) {
					if (actor instanceof Player) {
						this.incrementCount();
						// no limit on how many times this can be executed
						if (actor != PlayerGeneric.this) {
							return new ResultGeneric(true, "What are you looking at "+((Player) actor).getName() + "?!");
						} else {
							return new ResultGeneric(true, "It's like looking in a mirror!");							
						}
					}
					return null;
				}						
			}
		);
	}		
	
	public boolean go(Direction dir) {
		Place destination = location.getConnection(dir);
		if (destination != null) {
			location = destination;
			return true;
		}
		return false;
	}

	public Place getLocation() {
		return this.location;
	}

	public void setLocation(Place place) {
		this.location = place;
	}

	public Game getGame() {
		return this.game;
	}

	public void pickUp(Item item) throws ActionException {
		if (location.hasOne(item)) {
			if (!basketIsFull()) {
				item.move(this);
			} else {
				throw new ActionException("You can't carry any more");
			}
		} else {
			if (item.getName() != null) {
				throw new ActionException("I don't see any "+item.getName());
			} else {
				throw new ActionException("I don't see one of those");							
			}
				
		}
	}
	
	public void drop(Item item) throws ActionException {
		if (this.hasOne(item)) {
			item.move(location);
		} else {			
			throw new ActionException("You don't have it");
		}
	}

	public void addItem(Item item) {
		if (countAll() < maxItems) {
			super.addOne(item);
		}
	}
	
	public boolean basketIsFull() {
		if (countAll() < maxItems) {
			return false;
		}
		return true;
	}

	public boolean basketIsEmpty() {
		if (countAll() > 0) {
			return false;
		}
		return true;
	}

	public String basketContentsLook() {
		String ret = "You are carrying: ";
		if (this.countAll() < 1) {
			ret += "nothing";
			return ret;
		}
		for (Item item : this.getAll()) {
			ret += item.getName() + ", ";
		}
		return ret;
	}

	public String getName() {
		return name;
	}

	public void changeName(String newName) {
		this.name = newName;
	}

	@Override
	public String look() {
		return "It's a person playing a game.";
	}
}
