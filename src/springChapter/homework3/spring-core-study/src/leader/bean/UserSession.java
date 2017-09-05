package leader.bean;

public class UserSession {
private String sessionId;
private long userId;
private String userName;
private long createTime;
private short validSeconds;
public String getSessionId() {
	return sessionId;
}
public boolean isValid()
{
	return sessionId!=null && ((createTime+validSeconds*1000)<System.currentTimeMillis());
}
public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
public long getUserId() {
	return userId;
}
public void setUserId(long userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public long getCreateTime() {
	return createTime;
}
public void setCreateTime(long createTime) {
	this.createTime = createTime;
}
public short getValidSeconds() {
	return validSeconds;
}
public void setValidSeconds(short validSeconds) {
	this.validSeconds = validSeconds;
}

}
