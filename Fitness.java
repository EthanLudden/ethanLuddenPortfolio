/**
 * This interface is for the fitness app
 */
public interface Fitness{
	//All attributes need to be public, static, and constant.
	
	/**
	 * Since all methods are abstract, they cannot have instances.
	 */
	 
	 /**
	  * This method is used to return the type of muscle being used in the workout.
	  */
	public Muscle[] muscleTargeted();
	 
	 /**
	  * @param intensity This is the intensity of the exercise. I.E. Dancing to PoPiPo would be high
	  * @param weight This is the given weight for the person
	  * @param duration This is the duration of the exercise
	  * @return double This datatype represents the number of kiloCalories burned from the exercise.
	  */
	 public double calorieLoss(Intensity intensity, double weight, int duration);
	 
	 /**
	  * This method is used to describe the type of workout
	  */
	 public String description();
}