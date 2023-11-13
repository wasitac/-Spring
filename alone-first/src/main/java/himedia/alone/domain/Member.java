package himedia.alone.domain;

public class Member {
	//field
	private String userName;
	private int userAge;
	
	//constructor
//	public Member() {}
//	
//	public Member(Member member) {
//		this.name = member.getName();
//		this.age = member.getAge();
//	}
	
	// getter/setter
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	
}
