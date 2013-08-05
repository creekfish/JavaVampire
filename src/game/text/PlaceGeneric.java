package game.text;
import game.text.exceptions.ActionException;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


public class PlaceGeneric extends ActionableContainerGeneric<Item> implements Place {
	private String name;
	private String description;   // user readable description of this place
	private Map<Direction,Place> connections = new HashMap<Direction,Place>();   // where we can "go" from here
	
	public PlaceGeneric(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public PlaceGeneric(String name, String description, Collection<Item> items) {
    	super();
		this.name = name;
		this.description = description;
		for (Item item : items) {
			this.addOne(item);
		}
	}

	@Override
	public void setDefaultActions() {
		this.addAction(new ActionInitial("look") {
				@Override
				public Result execute(Actor actor) {
					this.incrementCount();
					// no limit on how many times this can be executed
					return new ResultGeneric(true, PlaceGeneric.this.look());
				}						
			}
		);

		this.addAction(new ActionInitial("go") {
				@Override
				public Result execute(Actor actor) {
					if (actor instanceof Player) {
						this.incrementCount();
						// no limit on how many times this can be executed
						// find the direction of this place relative to the actor
						Direction goDir = null;
						for (Direction dir : ((Player) actor).getGame().getDirections()) {
							if (((Player) actor).getLocation().getConnection(dir) == PlaceGeneric.this) {
								goDir = dir;
								break;
							}
						}
						if (goDir != null) {
							((Player) actor).go(goDir);
							return new ResultGeneric(true, null);
						} else {
							return new ResultGeneric(false, "You can't go there");							
						}
					}
					return null;
				}						
			}
		);
	}	
	
	public void setConnection(Direction dir, Place place) {
		connections.put(dir, place);
	}

	public Place getConnection(Direction dir) {
		return connections.get(dir);
	}

	public void removeConnection(Direction dir) {
		connections.remove(dir);
	}

	public void connect(Place place, Direction dir) {
		connections.put(dir, place);
		place.setConnection(dir.getOpposite(), this);  // set the opposite direction in the other place
	}

	public void disconnect(Place place) {
		Iterator<Map.Entry<Direction,Place>> iter = this.connections.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Direction,Place> e = iter.next();
			if (place == e.getValue()) {
				iter.remove();
				place.removeConnection(e.getKey().getOpposite()); // remove the opposite direction as well from the other place
			}
		}
	}
	
	public Collection<Direction> listExits() {
		Collection<Direction> dirs = new HashSet<Direction>();
		for (Direction d : this.connections.keySet()) {
			dirs.add(d);
		}
		return dirs;
	}
	
	public boolean isExit(Direction dir) {
		return (this.connections.get(dir) != null);
	}

	public boolean isDark() {
		return false; // default is not dark
	}

	public String getName() {
		return name;
	}

	public void changeName(String newName) {
		this.name = newName;
	}
	
	public String getDescription() {
		return description;
	}

	public String look() {
		String ret;
		ret = "You are in the "+getName();
		if (getDescription() != null && getDescription().length() > 0) {
			ret += " " + getDescription();
		}
		ret += "\nYou see ";
		if (this.countAll() > 0) {	
			boolean first = true;
			for (Item i : this.getAll()) {
				if (i.getName() != null) {  // skip any we can't see
					if (!first) {
						ret += ", ";
					}
					ret += i.getName();
					first = false;
				}
			}
		} else {
			ret += "nothing interesting.";
		}
		ret += "\nObvious exits are: ";
		for (Direction d : this.listExits()) {
			ret += d.getName() + " ";
		}
		return ret;
	}
	
	public void moveItemHere(Item item) throws ActionException {
		item.move(this);
	}
}
