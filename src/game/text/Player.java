package game.text;

import game.text.exceptions.ActionException;

public interface Player extends Actor, Container<Item>, Actionable, Lookable, Thing {
	public boolean go(Direction dir);
	public Game getGame();
	public Place getLocation();
	public void setLocation(Place place);
	public void pickUp(Item item) throws ActionException;
	public void drop(Item item) throws ActionException;
	public boolean basketIsEmpty();
	public boolean basketIsFull();
	public String basketContentsLook();
	public String getName();
	
}
