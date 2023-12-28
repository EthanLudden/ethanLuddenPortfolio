/**
 * This is the Tai Chi class of the app
 */
public class TaiChi extends Flexibility{
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
	public TaiChi(){
		super();
		this.musclesInExercise = new Muscle[] {Muscle.Arms, Muscle.Legs};
		this.description = "TaiChi";
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
			this.intensityConstant = 1.5;
			return 1.5 * this.weight * (this.duration / 60.0);
		}
		else if ((this.intensity.getIntensityLevel()).equals("Medium")){
			this.intensityConstant = 3;
			return 3 * this.weight * (this.duration / 60.0);
		}
		else {
			this.intensityConstant = 5;
			return 5 * this.weight * (this.duration / 60.0);			
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
