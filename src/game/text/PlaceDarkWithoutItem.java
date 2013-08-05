package game.text;
import java.util.Collection;


public class PlaceDarkWithoutItem extends PlaceGeneric {
	private Item providesLight;
	
	public PlaceDarkWithoutItem(String name, String description, Item providesLight) {
		super(name, description);
		this.providesLight = providesLight;
		addLightToLook();		
	}

	public PlaceDarkWithoutItem(String name, String description, Collection<Item> items, Item providesLight) {
    	super(name, description, items);
		this.providesLight = providesLight;
		addLightToLook();
	}

	// NOTE: must make the change to check for lighted item in actions list
	// not override look(), since must check actor for possession of the item
	public void addLightToLook() {
		final ActionInitial lightedLook = this.getInitialAction("look");  
		this.addAction(new ActionInitial("look") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).hasOne(providesLight)) {
						this.incrementCount();
						return lightedLook.execute(actor);
					} else {
						return new ResultGeneric(true, "You are in the "+PlaceDarkWithoutItem.this.getName()+"\nIt's Dark! You can't see");						
					}
				}						
			}
		);
	}	

}
