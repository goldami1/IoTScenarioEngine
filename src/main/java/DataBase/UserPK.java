package DataBase;

import java.io.Serializable;
import java.util.Objects;


public class UserPK implements Serializable
{	
	protected short id;
	protected String email;
	protected String userName;
	
	public UserPK()
	{
		
	}

	public UserPK(short id, String email, String userName) {
		super();
		this.id = id;
		this.email = email;
		this.userName = userName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserPK))
			return false;
		UserPK that = (UserPK) o;
		return	Objects.equals(this.id, that.id) ||
				Objects.equals(this.email, that.email) ||
				Objects.equals(this.userName, that.userName);
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.email, this.userName);
	}
	
}
