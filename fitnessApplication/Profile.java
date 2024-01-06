/**
 * This is the Profile class of the app
 */
public class Profile {
	/**
	 * This attribute is the statistics
	 * Age, height in meters, weight in kg, and gender
	 */
	protected int age;
	protected double height;
	protected double weight;
	protected int duration;
	protected Gender gender;
	
	//This constructor sets everything up
	public Profile(int age, double height, double weight, Gender gender){
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
	}
	
	//The next methods set the height and weight of the user
	public void setHeight(double height){
		this.height = height;
	}
	public void setWeight(double weight){
		this.weight = weight;
	}
	
	//This method sets the age
	public void setAge(int age){
		this.age = age;
	}
	
	//This method sets the gender
	public void setGender(Gender gender){
		this.gender = gender;
	}
	
	//These are the getter methods for the age, height, weight, and gender.
	public int getAge(){
		return this.age;
	}
	
	public double getHeight(){
		return this.height;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	public Gender getGender(){
		return this.gender;
	}
	
	//This is the description of the profile.
	@Override
	public String toString(){
		//Note how the "%.1f" is used to specify only one decimal point to the right. Also I need to use the String.format Method which is allowed.
		return "Age "  + this.age + ", Weight " + String.format("%.1f", this.weight) + "kg, Height " + String.format("%.1f", this.height) + "m, Gender " + (this.gender.getIdentity());
	}
	
	//Now to calculate the BMI. 
	public double calcBMI(){
		//No need to use Math even though I cannot import it.
		//The 1.0 ensures that a double will be returned.
		return 1.0 * this.weight / (this.height * this.height);
		//Order of operations apply here so squaring the height should be done in parenthases.
	}
	
	/*This method keeps track of the number of kilocalories the user eats.
	The branch will select which formula to use based on gender.*/
	public double dailyCalorieIntake(){
		if ((this.gender.getIdentity()).equals("MALE")){
			//Order of operations apply, so no need to use parenthases here.
			//Since height is given in meters, dimensional analysis is used to multiply by 100
			return (66.47 + this.weight * 13.75 + 5.003 * (this.height * 100) - 6.755 * this.age);
		}
		else{
			return (655.1 + this.weight * 9.563 + 1.85 * (this.height * 100) - 4.676 * this.age);
		}
	}
}
