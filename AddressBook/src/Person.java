import java.io.Serializable;
/**
 * PersonÀà
 * @author Yannis
 * @since jdk 1.8
 * @version 1.0
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String birthDate;
	private String phone;
	private String mail;
	private String memo;  //±¸×¢
	public Person(){
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", birthDate=" + birthDate + ", phone=" + phone + ", mail=" + mail + ", memo="
				+ memo + "]";
	}
	public Person(String name, String birthDate, String phone, String mail, String memo) {
		super();
		this.name = name;
		this.birthDate = birthDate;
		this.phone = phone;
		this.mail = mail;
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone2) {
		this.phone = phone2;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
