package main.com.leader.service;

import java.io.*;
import java.util.Date;
import java.util.List;

import main.com.leader.bean.User;
import main.com.leader.bean.UserSession;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;


public class UserService {

	@Autowired
	public Environment environment;

	public boolean createUser(User user)
	{
		return true;
	}
	public boolean deleteUser(long userId)
	{
		return true;
	}
	public boolean disableUser(long userId)
	{
		return true;
	}
	public List<User> queryUsers(String userNamePrex,boolean onlyValidUser)
	{
		return null;
	}
	/**
	 * ???????????????UserSession??????sessionId?????????????????ж??????UserSession.isValid????
	 * @param userName
	 * @param md5EncodedPassword
	 * @return
	 */
	public UserSession login(String userName, String md5EncodedPassword){
		UserSession session = new UserSession();
		if(validateUserInfo(userName, md5EncodedPassword)){
			try {
				FileWriter fw=new FileWriter(System.getProperty("user.dir").concat("/sessionInfo.txt"),true);
				session.setUserName(userName);
				session.setCreateTime(System.currentTimeMillis());
				session.setSessionId(userName.concat(new Date().toString()));
				session.setUserId((int)(Math.random()*100));
				session.setValidSeconds((short) 3000);
				fw.write(session.toJsonString());
				fw.write(System.getProperty("line.separator", "\n"));
				fw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return session;
	}



	private List<UserSession> getALLSessionFromDisk(){
		File file = new File(System.getProperty("user.dir").concat("/sessionInfo.txt"));
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String lineStr=null;
				while ((lineStr=br.readLine())!=null){

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

	private boolean validateUserInfo(String userName,String md5EncodedPassword){
		File file = new File(System.getProperty("user.dir").concat("/userInfo.txt"));
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String lineStr=null;
			while ((lineStr=br.readLine())!=null){
				lineStr=lineStr.replace("{","").replace("}","");
				String[] fields = lineStr.split(",");
				boolean isUserName = false;
				boolean isPassword = false;
				for(String str : fields){
					String[] obj = str.split(":");
					String field = obj[0].replace("\"", "");
					if("userName".equals(field)&&userName.equals(obj[1])){
						isUserName = true;
					}
					if("password".equals(field)&&md5EncodedPassword.equals(obj[1])){
						isPassword= true;
					}
				}
				if(isPassword&&isUserName){
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		UserService us=new UserService();
		System.out.println(us.getClass().getResource("/").getPath());
		System.out.println(System.getProperty("user.dir"));

//		----------------

		System.out.println(us.login("chenhaiyan9", "1a2w2e3r8").toJsonString());
		System.out.println(us.login("chenhaiyan9", "1a2w2e3r9").toJsonString());
		System.out.println(us.login("chenhaiyan8", "1a2w2e3r8").toJsonString());
		System.out.println(us.login("chenhaiyan3", "1a2w2e3r3").toJsonString());
	}

	private static void initUserInfoToDisk(){
		try {
			FileWriter fw=new FileWriter(System.getProperty("user.dir").concat("/userInfo.txt"));
			for (int i = 0; i <10 ; i++) {
				User user=new User();
				user.setUserName("chenhaiyan"+i);
				user.setUserId(100+i);
				user.setPassword("1a2w2e3r"+i);
				user.setRegDate(new Date());
				user.setEnabled(true);
				fw.write(user.toJsonString());
				fw.write(System.getProperty("line.separator", "\n"));
			}
			fw.flush();
			fw.close();
			System.out.println("finished write userInfo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
