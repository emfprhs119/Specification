package Inheritance;

public interface List_Interface<T> extends GetResultSet {
	public boolean addQuery(T o);
	public boolean isHasQuery(T o);
	public boolean removeQuery(int i);
	public T get(int i);
	public int size();
	public void loadList(String search);
	public T loadObject(String id);
	
}
