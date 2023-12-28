/**
 * This is the Weightlifting subclass of the app
 */
public class WeightLifting extends Anaerobic{
	/**
	 * This attribute is the stroke
	 */
	private Muscle[] musclesInExercise;
	protected String description;
	protected double weight;
	protected int duration;
	protected Intensity intensity;
	protected double intensityConstant;
	
	/**
	 * This constructor sets the muscles and other values
	 */
	public WeightLifting(){
		super();
		this.musclesInExercise = new Muscle[] {Muscle.Shoulders, Muscle.Legs, Muscle.Arms, Muscle.Triceps};
		this.description = "WeightLifting";
	} 
	
	/**
	 * Once the stroke is called, the muscles can be overwritten
	 */
	@Override
	public Muscle[] muscleTargeted(){
		return this.musclesInExercise;
	}
	
	/**
	 * The branch in the method will select the intensity level to be multiplied by.
	 * Doubles are used to make sure that when converted to hours, there is no truncation.
	 */
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration){
		this.weight = weight;
		this.duration = duration;
		this.intensity = intensity;
		if ((this.intensity.getIntensityLevel()).equals("High")){
			this.intensityConstant = 6.0;
			return 6.0 * this.weight * this.duration / 60.0;
		}
		else if ((this.intensity.getIntensityLevel()).equals("Medium")){
			this.intensityConstant = 5.0;
			return 5.0 * this.weight * (this.duration / 60.0);
		}
		else {
			this.intensityConstant = 3.5;
			return 3.5 * this.weight * (this.duration / 60.0);			
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
