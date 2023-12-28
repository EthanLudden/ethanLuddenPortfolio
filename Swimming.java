/**
 * This is the Swimming subclass of the app
 */
public class Swimming extends Aerobic{
	/**
	 * This attribute is the stroke
	 */
	private SwimmingType type;
	
	protected double intensityConstant;
	
	/**
	 * This constructor sets swimming style to freestyle
	 * A cascaded constructor is used.
	 */
	public Swimming(){
		this(SwimmingType.Freestyle);
	} 
	
	/**
	 * This is the constructor of the class;
	 * @param type This indicates what stroke will be used
	 */
	public Swimming(SwimmingType type){
		super();
		this.type = type;
		this.description = "Swimming";
	}

	/**
	 * This method sets the swimming type
	 * @param type This is the stroke's name
	 */
	public void setSwimmingType(SwimmingType type){
		this.type = type;
	}


	/**
	 * This method returns the swimming type
	 * @return SwimmingType this is the name of the stroke returned.
	 */
	public SwimmingType getSwimmingType(){
		return this.type;
	}
	
	/**
	 * Once the stroke is called, the muscles can be overwritten
	 */
	@Override
	public Muscle[] muscleTargeted(){
		return this.type.musclesInExercise;
	}
	
	/**
	 * The branch in the method will select the intensity level to be multiplied by.
	 */
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration){
		this.weight = weight;
		this.duration = duration;
		this.intensity = intensity;
		if ((this.intensity.getIntensityLevel()).equals("Low")){
			this.intensityConstant = 6.0;
			return 6.0 * this.weight * (this.duration / 60.0);
		}
		else if ((this.intensity.getIntensityLevel()).equals("Medium")){
			this.intensityConstant = 8.3;
			return 8.3 * this.weight * (this.duration / 60.0);
		}
		else {
			this.intensityConstant = 10.0;
			return 10.0 * this.weight * (this.duration / 60.0);
		}	
	}
	
	/**
	 *This method overrides the abstract description
	 * @return String This exercise is done with oxygen
	 */
	@Override
	public String description(){
		return this.description;
	}
	
}
