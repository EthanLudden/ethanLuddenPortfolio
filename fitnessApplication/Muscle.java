/**
 *This is an enum of muscles or body parts that get impacted
 * Abs The Abs
 * Back The Back
 * Biceps The Biceps
 * Chest The Chest
 * Arms The Arms
 * Glutes The Glutes
 * Shoulders The Shoulders
 * Triceps The Triceps
 * Legs The Legs
 * Cardio The Heart
 * Once an enum is passed it's set.
 */
public enum Muscle {
 	Abs("Abs"), Back("Back"), Biceps("Biceps"), Chest("Chest"), Arms("Arms"),
 	Glutes("Glutes"), Shoulders("Shoulders"), Triceps("Triceps"), Legs("Legs"),
 	Cardio("Cardio"); 
 	private String muscleUsed;
 	Muscle(String muscleUsed){
 		this.muscleUsed = muscleUsed;
 	}
 	public String getMuscleUsed(){
 		return this.muscleUsed;
 	}
}
