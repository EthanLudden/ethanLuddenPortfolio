/**
 * If you look closely, Gender is an enum value, this method will take care of it.
 */
public enum Gender{
	MALE("MALE"), FEMALE("FEMALE"); 
	private String genderIdentity;
	Gender(String genderIdentity){
 		this.genderIdentity = genderIdentity;
 	}
 	//Since an attribute is private, a getter is needed.
 	public String getIdentity(){
 		return this.genderIdentity;
 	}
}