package game.text;

import java.util.Collection;

public interface DataBucket {
	public Object getData(String key);
	public void setData(String key, Object object);
	public void removeData(String key);
	public void removeAllData();
	public Collection<String> listDataKeys();
}
