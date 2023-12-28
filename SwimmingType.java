//This enum lets the user know which muscles are used in each stroke.
enum SwimmingType{
	//A new array must be created for each stroke.
	Butterflystroke(new Muscle[] {Muscle.Abs, Muscle.Back, Muscle.Shoulders, Muscle.Biceps, Muscle.Triceps}),
	Freestyle(new Muscle[] {Muscle.Arms, Muscle.Legs, Muscle.Cardio}),
	Breaststroke(new Muscle[] {Muscle.Glutes, Muscle.Cardio});
	protected Muscle[] musclesInExercise;
	SwimmingType(Muscle[] musclesInExercise){
		this.musclesInExercise = musclesInExercise;
	}
}