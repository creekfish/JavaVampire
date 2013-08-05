package game.text;
import game.text.exceptions.ActionException;

import java.util.Collection;


public class ItemGeneric extends ActionableContainerGeneric<Item> implements Item {
	private String name;
	private String description;
	private Container<Item> location;
	private DataBucket data = new DataBucketGeneric();
	
	public ItemGeneric(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		setDefaultActions();
	}

	public ItemGeneric(String name, String description, Collection<Action> actions) {
		super();
		this.name = name;
		this.description = description;
		setDefaultActions();
		for (Action action : actions) {
			addAction(action);
		}
	}
	
	@Override
	public void setDefaultActions() {
		this.addAction(new ActionInitial("look") {
				@Override
				public Result execute(Actor actor) {
					this.incrementCount();
					// no limit on how many times this can be executed
					return new ResultGeneric(true, ItemGeneric.this.look());
				}						
			}
		);

		this.addAction(new ActionInitial("read") {
				@Override
				public Result execute(Actor actor) { 
					// alias to "look"
					return ItemGeneric.this.getInitialAction("look").execute(actor);
				}
			}
		);

		this.addAction(new ActionInitial("get") {
			@Override
			public Result execute(Actor actor) {
				// no limit on how many times this can be executed
				if (actor instanceof Player) {
					try {
						((Player) actor).pickUp(ItemGeneric.this);
						this.incrementCount();
						return new ResultGeneric(true, "OK, you got the "+ItemGeneric.this.getName());
					} catch (ActionException e) {
						return new ResultGeneric(false, e.getMessage());
					}
				} else {
					return new ResultGeneric(false, "You can't pick up an item!");						
				}
			}
		});

		this.addAction(new ActionInitial("drop") {
			@Override
			public Result execute(Actor actor) {
				// no limit on how many times this can be executed
				if (actor instanceof Player) {
					try {
						((Player) actor).drop(ItemGeneric.this);
						this.incrementCount();
						return new ResultGeneric(true, "The "+ItemGeneric.this.getName()+" is on the "+((Player) actor).getLocation().getName()+" floor");
					} catch (ActionException e) {
						return new ResultGeneric(false, e.getMessage());
					}
				} else {
					return new ResultGeneric(false, "You can't drop an item!");						
				}
			}
		});
		
		this.addAction("throw", this.getAction("drop"));  // "throw" = alias for "drop"

		this.addAction(new ActionInitial("hit") {
			@Override
			public Result execute(Actor actor) {				
				if (actor instanceof Player) {
					if (((Player) actor).getLocation().hasOne(ItemGeneric.this)) {
						// default for hitting something is that nothing happens
						return new ResultGeneric(false, "Nothing happened");
					} else {
						if (ItemGeneric.this.getName() != null) {
							return new ResultGeneric(false, "I don't see any "+ItemGeneric.this.getName());
						} else {
							return new ResultGeneric(false, "I don't see one of those");							
						}
					}
				} else {
					return new ResultGeneric(false, "You can't hit an item!");						
				}
			}
		});
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

	public void changeDescription(String newDescription) {
		this.description = newDescription;
	}

	public void move(Container<Item> destination) throws ActionException {
		if (location != null) {
			location.removeOne(this);
		} else {
			// exception must be resolved by caller
		}
		if (destination != null) {
			destination.addOne(this);
		} else {
			// exception must be resolved by caller
		}
		location = destination;
	}
	
	public Container<Item> getLocation() {
		return location;
	}
	
	public String look() {
		return "You see nothing special";
	}
	
	public Object getData(String key) {
		return data.getData(key);
	}

	public Collection<String> listDataKeys() {
		return data.listDataKeys();
	}

	public void removeAllData() {
		data.removeAllData();
	}

	public void removeData(String key) {
		data.removeData(key);
	}

	public void setData(String key, Object object) {
		data.setData(key, object);
	}
	
}
