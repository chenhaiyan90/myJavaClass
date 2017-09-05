package main.com.leader.bean;

public class MyDataSourceParamBean {

	private String url;

	private String userName;

	private String password;

	private String databases;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getDatabases() {
		return databases;
	}

	public void setDatabases(String databases) {
		this.databases = databases;
	}

	@Override
	public String toString() {
		return "MyDataSourceParamBean{" +
				"url='" + url + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", databases='" + databases + '\'' +
				'}';
	}
}
