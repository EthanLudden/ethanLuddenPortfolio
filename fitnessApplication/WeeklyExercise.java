//This class is the planner
public class WeeklyExercise{
	//This is the list of exercises
	private Fitness[] exerciseList;
	//This is the weekly kcal target.
	private double weeklyCalorieTarget;
	//This is the days
	private int days;
	//This is the profile
	private Profile profile;
	//This list is a helper list which is to store the values not removed.
	private Fitness[] helperList;
	//This boolean value is just to be used when removing an exercise
	private boolean exerciseremoved = false;
	//This will be the outer array for all exercises of a specific muscle.
	private Fitness[] exercisesOfMuscles;
	//This counter is to help with storing the exercises.
	private int counter = 0;
	//This array is to help out with an outer array for the daily tasks
	private DailyExercise[] dailyRoutines;
	//This is for the daily calorie target
	private double dailyCalorieTarget;
	//This is for the intensity
	private Intensity intensity;
	//This is the duration
	private int duration;
	//This is the target weight
	private double targetWeight;
	//This is the time frame in days for advice
	private int withInDays;
	//This is the advice for calories to burn
	private double caloriesToBurn;
	//This is the exercise that will be added
	private Fitness ex;
	//This will be the denominator of the ratio between burned calorie values.
	private double minimumOutput;
	//This is the proportion constant used to help find the duration.
	private double proportion;
	//This is where the daily exercise goes
	private DailyExercise exerciseOfDay;
	//This is the calorie intake holder
	private double currentCalorieIntake;
	//This is the expected intake
	private double expectedCalorieIntake;
	
	//This is the constructor with a 1 hour time limit and a 500 kilocalorie target
	public WeeklyExercise(Fitness[] exerciseList, Profile profile){
		//Note: a cascaded constructor is used.
		this(exerciseList, 7, 3500, profile);
	}
	
	//This is the constructor with a custom days
	public WeeklyExercise(Fitness[] exerciseList, int days, double weeklyCalorieTarget, Profile profile){
		this.exerciseList = exerciseList;
		this.weeklyCalorieTarget = weeklyCalorieTarget;
		this.days = days;
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
	
	//This method is used to set the days of the workout in minutes
	public void setDays(int days){
		this.days = days;
	}
	
	//This method returns the number of daily exercises
	public int getSize(){
		return this.exerciseList.length;
	}
	
	//This method sets the target
	public void setWeeklyCalorieTarget(double target){
		this.weeklyCalorieTarget = target;
	}
	
	//This method sets the profile
	public void setProfile(Profile profile){
		this.profile = profile;
	}
	
	//This method gets the exercise list
	public Fitness[] getExerciseList() {
		return this.exerciseList;
	}
	
	//This method gets the days
	public int getDays(){
		return this.days;
	}
	
	//This method gets the kilocalorie target for the day. I will have a big one the next time I dance.
	public double getWeeklyCalorieTarget(){
		return this.weeklyCalorieTarget;
	}
	
	//This method returns the profile
	public Profile getProfile(){
		return this.profile;
	}
	
	//Now for the weekly exercises.
	public DailyExercise[] getWeeklyExercises(Intensity intensity){
		//The array is set It has to be a new array or else a null array is set.
		this.dailyRoutines = new DailyExercise[this.days];
		this.exerciseList = getExerciseList();
		this.intensity = intensity;
		//Again the 1.0 helps me ensure that I have a double.
		this.dailyCalorieTarget = (1.0 * getWeeklyCalorieTarget() / this.days);
		this.profile = getProfile();
		//This counter will help estimate
		this.counter = 0;
		//The for loop will help determine the minimum duration needed to meet the requirement. 
		for (int i = 0; i < this.days; ++i){
			//This do while will be used to help find the minimum exact duration to burn at least the daily target.
			do{
				this.minimumOutput = (this.exerciseList[i]).calorieLoss(this.intensity, (this.profile.getWeight()), this.counter++);
			} while ((this.exerciseList[i]).calorieLoss(this.intensity, (this.profile.getWeight()), this.counter) <= this.dailyCalorieTarget);
			//Decrementing ensures that the correct number is used.
			--this.counter;
			//This is the ratio from counter duration to expected duration.
			this.proportion = this.minimumOutput/this.dailyCalorieTarget;
			//Explicit casting is required to convert the denominator to an int.
			//Also, a reciporacle of the proportion is required to compute the unknown duration.
			this.duration = (int) (this.counter/this.proportion);
			this.counter = 0;
			this.ex = this.exerciseList[i];
			//A new daily exercise is set. A new fitness array is set or otherwise the value is null.
			this.exerciseOfDay = new DailyExercise(new Fitness[] {this.exerciseList[i]}, this.duration, this.dailyCalorieTarget, this.profile);
			this.dailyRoutines[i] = (this.exerciseOfDay);
		}
		//The length of the list will indicate the number of days to be set.
		return this.dailyRoutines;
		
	}
	
	//Now for the weekly exercises.
	public DailyExercise[] getWeeklyExercises(){
		//The array is set It has to be a new array or else a null array is set.
		this.dailyRoutines = new DailyExercise[this.days];
		this.exerciseList = getExerciseList();
		this.intensity = Intensity.LOW;
		//Again the 1.0 helps me ensure that I have a double.
		this.dailyCalorieTarget = (1.0 * getWeeklyCalorieTarget() / this.days);
		this.profile = getProfile();
		//This counter will help estimate
		this.counter = 0;
		//The for loop will help determine the minimum duration needed to meet the requirement. 
		for (int i = 0; i < this.days; ++i){
			//This do while will be used to help find the minimum exact duration to burn at least the daily target.
			do{
				this.minimumOutput = (this.exerciseList[i]).calorieLoss(this.intensity, (this.profile.getWeight()), this.counter++);
			} while ((this.exerciseList[i]).calorieLoss(this.intensity, (this.profile.getWeight()), this.counter) <= this.dailyCalorieTarget);
			//Decrementing ensures that the correct number is used.
			--this.counter;
			//This is the ratio from counter duration to expected duration.
			this.proportion = this.minimumOutput/this.dailyCalorieTarget;
			//Explicit casting is required to convert the denominator to an int.
			//Also, a reciporacle of the proportion is required to compute the unknown duration.
			this.duration = (int) (this.counter/this.proportion);
			this.counter = 0;
			this.ex = this.exerciseList[i];
			//A new daily exercise is set. A new fitness array is set or otherwise the value is null.
			this.exerciseOfDay = new DailyExercise(new Fitness[] {this.exerciseList[i]}, this.duration, this.dailyCalorieTarget, this.profile);
			this.dailyRoutines[i] = (this.exerciseOfDay);
		}
		//The length of the list will indicate the number of days to be set.
		return this.dailyRoutines;
		
	}
	//This is the advice method for losing weight within days.
	public String targetedCalorieLoss(double targetWeight, int withInDays){
		this.targetWeight = targetWeight;
		//The profile must be recieved to compare
		this.profile = getProfile();
		if (this.targetWeight >= this.profile.getWeight()){
			return "Only works to lose weight";
		}
		this.withInDays = withInDays;
		this.currentCalorieIntake = this.profile.dailyCalorieIntake();
		/*To Find the number of kilocalories to be burned, the weight to lose is computed.
		Then the next setp is to multiply by 7000 if it takes that many kilocalories to lose
		a kilogram of weight. And then everything is divided evenly by the amount of days.*/
		this.caloriesToBurn = (this.profile.getWeight() - this.targetWeight) * 7000 / this.withInDays;
		//Now to find the expected calorie intake.
		this.expectedCalorieIntake = this.currentCalorieIntake - this.caloriesToBurn;
		//The string is returned truncating to the nearest hundredth.
		return "You need to lose " + String.format("%.2f", this.caloriesToBurn) + " calories per day or decrease your intake from " + String.format("%.2f", this.currentCalorieIntake) + " to " + String.format("%.2f", this.expectedCalorieIntake) + " in order to lose " + String.format("%.2f", (this.profile.getWeight() - this.targetWeight)) + " kg of weight";
	}
}