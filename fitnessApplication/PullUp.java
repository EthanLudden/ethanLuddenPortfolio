/**
 * This is the Pull-Up subclass of the app
 */
public class PullUp extends Endurance{
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
	public PullUp(){
		super();
		this.musclesInExercise = new Muscle[] {Muscle.Biceps, Muscle.Arms};
		this.description = "PullUp";
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
		if ((this.intensity.getIntensityLevel()).equals("Low")){
			this.intensityConstant = 4.8;
			return 4.8 * weight * (this.duration / 60.0);
		}
		else if ((this.intensity.getIntensityLevel()).equals("Medium")){
			this.intensityConstant = 6.0;
			return 6.0 * this.weight * (this.duration / 60.0);
		}
		else {
			this.intensityConstant = 7.5;
			return 7.5 * this.weight * (this.duration / 60.0);
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
