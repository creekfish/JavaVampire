package game.text.vampire.places;

import game.text.Item;
import game.text.PlaceGeneric;

import java.util.Collection;

public class Tower extends PlaceGeneric {
	public static final String name = "Tower";
	public static final String description = "Tower";
	
	public Tower() {
		super(Tower.name, Tower.description);
	}

	public Tower(Collection<Item> items) {
		super(Tower.name, Tower.description, items);
	}
}
