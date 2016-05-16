package SimObjects;

public class simPersonModel {

	private Boolean altruistic = true;
	private Boolean selfish = false;
	private int ID;
	private int happinesssScore;
	private Boolean personality;
	int personalityOriginal;


	public simPersonModel(int iD, int happinesssScore, Boolean personality,int personalityOriginal) {
		super();

		ID = iD;
		this.happinesssScore = happinesssScore;
		this.personalityOriginal = personalityOriginal;
		this.personality = personality;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getHappinesssScore() {
		return happinesssScore;
	}

	public void setHappinesssScore(int happinesssScore) {
		this.happinesssScore = happinesssScore;
	}

	public Boolean getPersonality() {
		return personality;
	}

	public void setPersonality(Boolean personality) {
		this.personality = personality;
	}
	
	public int getPersonalityOriginal() {
		return personalityOriginal;
	}

	public void setPersonalityOriginal(int personalityOriginal) {
		this.personalityOriginal = personalityOriginal;
	}

	public String toString() {
		if (personality == altruistic) {
			if(personalityOriginal == 2){
			return "ID=" + ID + ", happinesssScore=" + happinesssScore + ", personality= altruistic "
					+ "PersonalityOriginal = random" + "]\n";
			}else{
				return "ID=" + ID + ", happinesssScore=" + happinesssScore + ", personality= altruistic "
						+ "PersonalityOriginal = altruistic"  + "]\n";
			}
		} else if(personality == selfish){
			if(personalityOriginal == 2){
			return "ID=" + ID + ", happinesssScore=" + happinesssScore + ", personality= selfish "
					+ "PersonalityOriginal = random"  + "]\n";
			}else{
				return "ID=" + ID + ", happinesssScore=" + happinesssScore + ", personality= selfish "
						+ "PersonalityOriginal = selfish"  + "]\n";
			}
		}else
			return "ID=" + ID + ", happinesssScore=" + happinesssScore + ", personality= selfish "
			+ "PersonalityOriginal = "  + "]\n";
	}
	
	
	

}
