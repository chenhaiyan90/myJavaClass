package leader.bean;

import java.util.Date;

public class User {
private long userId;
private String userName;
private String password;
private boolean enabled;
private Date regDate;
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
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
public Date getRegDate() {
	return regDate;
}
public void setRegDate(Date regDate) {
	this.regDate = regDate;
}

}
