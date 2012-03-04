package game.text;
import java.util.Collection;


public interface Container<T extends Thing> {
	public void addOne(T thing);

	public void addOne(String key, T thing);

	public T getOne(String key);

	public T matchOne(String keyStartsWith);

	public boolean hasOne(T thing);

	public boolean hasOne(String key);

	public int countAll();

	public Collection<T> getAll();
	
	public void removeOne(T thing);

	public void removeOne(String key);

	public void removeAll();
}
