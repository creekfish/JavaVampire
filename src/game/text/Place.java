package game.text;

import java.util.Collection;

/**
 * A node in the game that represents a place (room, area, location, corridor, path, etc.).
 * A place connects to other places in different directions and may contain items.
 * @author bherring
 *
 */
public interface Place extends Container<Item>, Actionable, Lookable, Thing {
	/**
	 * Connect this place to another place that lies in the specified direction.
	 * Both places will have new connections to each other, so that traveling in
	 * the specified direction from this place will lead to the other place,
	 * and then traveling in the opposite direction will lead back. Note that
	 * the specified direction MUST have an opposite direction defined for 
	 * this to work correctly!
	 * @param place the place to connect this place to
	 * @param dir the direction that leads to the specified place (must have
	 * an opposite)
	 */
	public void connect(Place place, Direction dir);

	/**
	 * Disconnect this place from the specified place (in any directions they
	 * are connected, but typically only one). This operation is the inverse
	 * of connect() and will also remove the connection from the opposite
	 * direction in the other place. Note that any affected directions MUST 
	 * have an opposite direction defined for this to work correctly!
	 * @param place the place to disconnect from this one
	 */
	public void disconnect(Place place);
	
	/**
	 * Set a connection between this place and the other specified place
	 * in the given direction. The other place is not affected (one-way
	 * connection).
	 * @param dir the direction of the connection to the other place
	 * @param place the other place the connection leads to
	 */
	public void setConnection(Direction dir, Place place);
	
	/**
	 * Return the place that lies in the specified direction
	 * from this place, or null if nothing lies in that direction.
	 * @param dir the direction to retrieve the place from
	 * @return the place that lies in this direction, or null if no place exists
	 */
	public Place getConnection(Direction dir);

	/**
	 * Remove the connection that lies in the specified direction.
	 * @param dir the direction to remove connection from
	 */
	public void removeConnection(Direction dir);
	
	/**
	 * Return the name of this place.
	 */
	public String getName();

	/**
	 * Return the "static" description of this place.
	 */
	public String getDescription();
	
	/**
	 * Return list of directions from which this place can be exited.
	 */
	public Collection<Direction> listExits();

	/**
	 * Return true if the specified direction is an exit from this place.
	 */
	public boolean isExit(Direction dir);

	/**
	 * Return true if this place is dark or false if it's illuminated.
	 */
	public boolean isDark();

	/**
	 * Default "look" action from this place, returns a string that represents
	 * what the player "sees" looking around this place.
	 */
	public String look();
}
