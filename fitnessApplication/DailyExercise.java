//This class is the planner
public class DailyExercise{
	//This is the list of exercises
	private Fitness[] exerciseList;
	//This is the kcal target.
	private double calorieTarget;
	//This is the duration
	private int duration;
	//This is the profile
	private Profile profile;
	//This list is a helper list which is to store the values not removed.
	private Fitness[] helperList;
	//This boolean value is just to be used when removing an exercise
	private boolean exerciseremoved;
	//This will be the outer array for all exercises of a specific muscle.
	private Fitness[] exercisesOfMuscles;
	//This counter is to help with storing the exercises.
	private int counter;
	//This is another helper array
	private Fitness[] exercisesOfMusclesFinal;
	//This is the exercise that will be added
	private Fitness ex;
	//This is the calorie intake holder
	private double currentCalorieIntake;
	//This will be the nonNull value counter
	private int nonNullCounter;
	//This will be the index for the final array value counter
	private int finalArrayIndex;
	//This is the list for a targetMuscle;
	private Muscle[] targetMuscle;
	//This is the size of the
	
	
	//This is the constructor with a 1 hour time limit and a 500 kilocalorie target
	public DailyExercise(Fitness[] exerciseList, Profile profile){
		//Note: a cascaded constructor is used.
		this(exerciseList, 60, 500, profile);
	}
	
	//This is the constructor with a custom duration
	public DailyExercise(Fitness[] exerciseList, int duration, double calorieTarget, Profile profile){
		this.exerciseList = exerciseList;
		this.calorieTarget = calorieTarget;
		this.duration = duration;
		this.profile = profile;
	}
	
	//This method adds exercises to the list
	public void addExercise(Fitness ex){
		this.ex = ex;
		//This will be the exercise list to be updated.
		this.exerciseList = new Fitness[((getExerciseList()).length + 1)];
		//The For loop will add the same exercises from the original list to the same spots.
		for (int i = 0; i < (getExerciseList()).length; ++i){
			//The same exercise is placed in the same spot.
			this.exerciseList[i] = (getExerciseList())[i];
		}
		this.exerciseList[this.exerciseList.length - 1] = this.ex;
		setExerciseList(this.exerciseList);
		
	}
	
	//This method removes exercises from the array list
	public void removeExercise(Fitness ex){
		//The value needs to first be set to false.
		this.exerciseremoved = false;
		//This list is just used to store items.
		this.helperList = new Fitness[getExerciseList().length];
		for (int i = 0; i < (getExerciseList()).length; ++i){
			//Because only the first case should be removed, both statements must be true.
			if (ex.equals((getExerciseList())[i]) && (!(this.exerciseremoved))){
				this.exerciseremoved = true;
			}
			//Once an exercise is removed, then the remaining exercises are slid.
			else if (this.exerciseremoved){
				this.helperList[i-1] = (getExerciseList())[i];
			}
			//Otherwise transferring remains as normal;
			else{
				this.helperList[i] = (getExerciseList())[i];
			}
		}
		//After construction, the exercise list is updated.
		setExerciseList(this.helperList);
	}
	
	//This method sets the array of workouts
	public void setExerciseList(Fitness[] list) {
		this.exerciseList = list;
	}
	
	//This method is used to set the duration of the workout in minutes
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	//This method sets the target
	public void setCalorieTarget(double target){
		this.calorieTarget = target;
	}
	
	//This method sets the profile
	public void setProfile(Profile profile){
		this.profile = profile;
	}
	
	//This method gets the exercise list
	public Fitness[] getExerciseList() {
		return this.exerciseList;
	}
	
	//This method gets the duration
	public int getDuration(){
		return this.duration;
	}
	
	//This method gets the kilocalorie target for the day. I will have a big one the next time I dance.
	public double getCalorieTarget(){
		return this.calorieTarget;
	}
	
	//This method returns the profile
	public Profile getProfile(){
		return this.profile;
	}
	
	//This method returns the number of daily exercises
	public int getSize(){
		return (getExerciseList()).length;
	}
	
	/* Last but not least comes the method that targets muscle groups and lists exercises*/
	public Fitness[] getExercises(Muscle[] targetMuscle){
		boolean containsduplicates = true;
		//The counters are set.
		this.counter = 0;
		this.nonNullCounter = 0;
		this.finalArrayIndex = 0;
		//So is the target Muscle array
		this.targetMuscle = targetMuscle;
		//I need to get the exercise list too
		this.exerciseList = getExerciseList();
		//This will be the rough array that is returned
		this.exercisesOfMuscles = new Fitness[getSize()];
		for (int i = 0; i < this.exerciseList.length; ++i){
			for (int j = 0; j < this.targetMuscle.length; ++j){
				for (int k = 0; k < (this.exerciseList[i].muscleTargeted()).length; ++k){
					if (((this.exerciseList[i].muscleTargeted())[k].getMuscleUsed()).equals(this.targetMuscle[j].getMuscleUsed())){
						++this.counter;
						//No need to iterate further from that point.
						break;
					}
				}
			}
			//This is to make sure the minimium muscle requirement has been met
			if (this.counter >= this.targetMuscle.length){
				this.exercisesOfMuscles[i] = this.exerciseList[i];
			}
			//The standard counter is reset.
			this.counter = 0;
		}
		//This loop counts the number of null values.	
		for (int m = 0; m < this.exercisesOfMuscles.length; ++m) {
			if (this.exercisesOfMuscles[m] != null){
				++this.nonNullCounter;
			}
		}
		//Now the final array is ready to be set
		this.exercisesOfMusclesFinal = new Fitness[this.nonNullCounter];
		//A similar for loop is used. The only difference now is that
		for (int m = 0; m < this.exercisesOfMuscles.length; ++m) {
			if (this.exercisesOfMuscles[m] != null){
				//The pre incrementer is used to make sure the correct index is used.
				this.exercisesOfMusclesFinal[this.finalArrayIndex++] = this.exercisesOfMuscles[m];
			}
		}
		if (this.exercisesOfMusclesFinal.length == 0){
			return null;
		}
		return this.exercisesOfMusclesFinal;
	}
}