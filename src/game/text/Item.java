package game.text;

import game.text.exceptions.ActionException;

public interface Item extends Actionable, Lookable, DataBucket, Thing {
		
	public String getDescription();
	public void changeDescription(String changeDescription);
	public String look();

	public Container<Item> getLocation();
	public void move(Container<Item> destination) throws ActionException;
	
}
