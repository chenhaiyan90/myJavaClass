package leader.reflect;

import java.util.Collection;

public class MyCompBean<T>  {
private int id;
private Integer level;
private String[] recentRecords;
private Object[] objRecords;
private T[] genelrecentRecords;
private Collection<String>  wortItems;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Integer getLevel() {
	return level;
}
public void setLevel(Integer level) {
	this.level = level;
}
public String[] getRecentRecords() {
	return recentRecords;
}
public void setRecentRecords(String[] recentRecords) {
	this.recentRecords = recentRecords;
}
public Collection<String> getWortItems() {
	return wortItems;
}
public void setWortItems(Collection<String> wortItems) {
	this.wortItems = wortItems;
}
public Object[] getObjRecords() {
	return objRecords;
}
public void setObjRecords(Object[] objRecords) {
	this.objRecords = objRecords;
}
public T[] getGenelrecentRecords() {
	return genelrecentRecords;
}
public void setGenelrecentRecords(T[] genelrecentRecords) {
	this.genelrecentRecords = genelrecentRecords;
}
 

}
