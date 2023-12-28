/**
 * This is the Cycling subclass of the app
 */
public class Cycling extends Aerobic{
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
	public Cycling(){
		super();
		this.musclesInExercise = new Muscle[] {Muscle.Glutes, Muscle.Cardio, Muscle.Legs};
		this.description = "Cycling";
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
	 */
	@Override
	public double calorieLoss(Intensity intensity, double weight, int duration){
		this.weight = weight;
		this.duration = duration;
		this.intensity = intensity;
		if ((this.intensity.getIntensityLevel()).equals("Low")){
			this.intensityConstant = 4.0;
			return 4.0 * this.weight * (this.duration / 60.0);
		}
		else if ((this.intensity.getIntensityLevel()).equals("Medium")){
			this.intensityConstant = 8.5;
			return 8.5 * this.weight * (this.duration / 60.0);
		}
		else {
			this.intensityConstant = 14.0;
			return 14.0 * this.weight * (this.duration / 60.0);
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
