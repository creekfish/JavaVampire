package game.text;

public class DirectionGeneric extends ActionableGeneric implements Direction {
	public final static Direction North = new DirectionGeneric("North", "N");
	public final static Direction East = new DirectionGeneric("East", "E");
	public final static Direction South = new DirectionGeneric("South", "S", North);
	public final static Direction West = new DirectionGeneric("West", "W", East);
	public final static Direction Up = new DirectionGeneric("Up", "U");
	public final static Direction Down = new DirectionGeneric("Down", "D", Up);
	private String name;
	private String abbr;
	private Direction opposite;
	
	public DirectionGeneric(String name) {
		super();
		this.name = name;
		this.abbr = name.substring(0, 1);
	}

	public DirectionGeneric(String name, String abbr) {
		super();
		this.name = name;
		this.abbr = abbr;
	}

	public DirectionGeneric(String name, String abbr, Direction opposite) {
		super();
		this.name = name;
		this.abbr = abbr;
		makeOpposites(opposite);
	}
	
	@Override
	public void setDefaultActions() {
		this.addAction(new ActionGeneric("look") {
				@Override
				public Result execute(Actor actor) {
					this.incrementCount();
					if (actor instanceof Player) {
						return new ResultGeneric(true, ((Player) actor).getLocation().look());
					 } else {
						 return null;
					 }
				}						
			}
		);

		this.addAction(new ActionGeneric("go") {
				@Override
				public Result execute(Actor actor) {
					if (actor instanceof Player) {
						this.incrementCount();
						// no limit on how many times this can be executed
						if (((Player) actor).getLocation().isExit(DirectionGeneric.this)) {
							((Player) actor).go(DirectionGeneric.this);
							return new ResultGeneric(true, ((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
						} else {
							return new ResultGeneric(false, "You can't go there");
						}
					}
					return null;
				}						
			}
		);	
	}	

	public Result executeAction(String actionKey, Actor actor) {
		if (this.getAction(actionKey) != null) {
			Action action = this.getAction(actionKey);
			return action.execute(actor);
		}
		return null;
	}	

	public String getName() {
		return this.name;
	}
	
	public void changeName(String newName) {
		this.name = newName;
	}

	public String getAbbreviation() {
		return this.abbr;
	}

	public void setOpposite(Direction opposite) {
		this.opposite = opposite;
	}

	public void makeOpposites(Direction opposite) {
		this.setOpposite(opposite);
		opposite.setOpposite(this);
	}

	public Direction getOpposite() {
		return opposite;
	}
}
