package game.text;

import java.util.Collection;

public interface Game extends Thing, Actionable {

	public void addPlayer(Player player);
	public void removePlayer(Player player);
	public Player getPlayer();
	
	public void addDirection(String key, Direction direction);
	public void removeDirection(String key);
	public void removeDirection(Direction direction);
	public Direction getDirection(String key);	
	public Direction matchDirection(String keyStartsWith);
	public Collection<Direction> getDirections();
	
	public void addItem(String key, Item item);
	public void removeItem(String key);	
	public void removeItem(Item item);	
	public Item getItem(String key);
	public Item matchItem(String keyStartsWith);
	public Collection<Item> getItems();

	public void addPlace(String key, Place place);
	public void removePlace(String key);
	public void removePlace(Place place);
	public Place getPlace(String key);
	public Place matchPlace(String keyStartsWith);
	public Collection<Place> getPlaces();
	
	public String getTime();
}
