package Simulation;
//this class is for to store the number of people who are selfish not selfish in a run
public class simGovernmentScoreModel {
	//cycle number 
	int runNumber;
	//total count from a sim run
	int totalSelfish;
	int totalAltruistic;
	//the score to be applied to each individuals happiness
	int score;
	
	

	public simGovernmentScoreModel(int runNumber,int numberOfAltruistic, int numberOfSelfish) {
		super();
		this.runNumber = runNumber;
		this.totalSelfish = numberOfSelfish;
		this.totalAltruistic = numberOfAltruistic;
		if(totalSelfish >= totalAltruistic){
			this.score = -(totalSelfish - totalAltruistic);
			
		}else{
			this.score = totalAltruistic - totalSelfish;
		}
	}
	
	public int getRunNumber() {
		return runNumber;
	}


	public void setRunNumber(int runNumber) {
		this.runNumber = runNumber;
	}



	public int getTotalSelfish() {
		return totalSelfish;
	}



	public void setTotalSelfish(int totalSelfish) {
		this.totalSelfish = totalSelfish;
	}

	public int getTotalAltruistic() {
		return totalAltruistic;
	}


	public void setTotalAltruistic(int totalAltruistic) {
		this.totalAltruistic = totalAltruistic;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public String toString() {
		return "[runNumber=" + runNumber + ", totalSelfish=" + totalSelfish
				+ ", totalAltruistic=" + totalAltruistic + ", score=" + score + "]";
	}
	
}
