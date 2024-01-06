/**
 * If you look closely, intensity is an enum value, this method will take care of it.
 */
public enum Intensity{
	LOW("Low"), MEDIUM("Medium"), HIGH("High"); 
	protected String intensityLevel;
	Intensity(String intensityLevel){
 		this.intensityLevel = intensityLevel;
 	}
 	public String getIntensityLevel(){
 		return this.intensityLevel;
 	}
}