/**
 * @Author Ethan Ludden
 * This is the Aerobic class of the app
 */
public abstract class Aerobic implements Fitness{
	protected double weight;
	protected int duration;
	protected Intensity intensity;
	
	/**
	 * This string holds the description;
	 */
	protected String description = "Aerobic means \"with oxygen.\"";
	
	/**
	 * This is the abstract constructor;
	 */
	public Aerobic(){
		this.description = description;
		this.weight = weight;
		this.duration = duration;
		this.intensity = intensity;
	}
	
	@Override
	public abstract Muscle[] muscleTargeted();
	
	@Override
	public abstract double calorieLoss(Intensity intensity, double weight, int duration);

	/**
	 *This method overrides the abstract description
	 * @return String This exercise is done with oxygen
	 */
	@Override
	public String description(){
		return description;
	}
	
}
