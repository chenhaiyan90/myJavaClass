package main.com.leader.bean;

import org.springframework.stereotype.Repository;

@Repository
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
	public String toJsonString(){
		StringBuilder str=new StringBuilder()
				.append("{\"userName\":").append(userName).append(",")
				.append("\"userId\":").append(userId).append(",")
				.append("\"sessionId\":").append(sessionId).append(",")
				.append("\"createTime\":").append(createTime).append(",")
				.append("\"validSeconds\":").append(validSeconds).append('}');
		return str.toString();
	}
}
