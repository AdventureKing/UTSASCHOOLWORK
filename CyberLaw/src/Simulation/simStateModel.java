package Simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import SimObjects.simPersonModel;

public class simStateModel {

	// sim out put comming from the driver
	private ArrayList<LinkedList> simGovernmentData = new ArrayList<LinkedList>();
	private ArrayList<LinkedList> simGovernmentPunishmentData = new ArrayList<LinkedList>();
	

	// copy of original data
	private ArrayList<LinkedList> simOriginalData;

	// list of number of selfish and alutristic users per Linked List
	private ArrayList<simGovernmentScoreModel> runSums = new ArrayList<simGovernmentScoreModel>();

	private Boolean altruistic = true;
	private Boolean selfish = false;

	public simStateModel(ArrayList<LinkedList> simOutPutList) {
		// TODO Auto-generated constructor stub

		simOriginalData = new ArrayList<LinkedList>(simOutPutList);
	}

	public void generateGovernmentScore() {

		for (int i = 0; i < simOriginalData.size(); i++) {
			ListIterator<simPersonModel> listIterator = simOriginalData.get(i).listIterator();
			int altruisticCount = 0;
			int selfishCount = 0;
			while (listIterator.hasNext()) {
				simPersonModel current = listIterator.next();
				if (current.getPersonality() == selfish) {
					selfishCount = selfishCount + 1;
				} else if (current.getPersonality() == altruistic) {
					altruisticCount = altruisticCount + 1;
				} else {
					System.out.println("Major Error Exit program");
				}
			}
			//System.out.println("altruisticCount: " + altruisticCount + "Selfish Count:" + selfishCount);
			simGovernmentScoreModel tempSum = new simGovernmentScoreModel(i, altruisticCount, selfishCount);
			runSums.add(tempSum);

			//System.out.println("Run Data:");
			//System.out.println(tempSum.toString());

		}
		for (int i = 0; i < simOriginalData.size(); i++) {
			LinkedList<simPersonModel> simCycleTempList = new LinkedList<simPersonModel>();
			ListIterator<simPersonModel> listIterator = simOriginalData.get(i).listIterator();
			int score = runSums.get(i).getScore();
			while (listIterator.hasNext()) {
				int listCount = 0;
				simPersonModel temp = listIterator.next();
				int newHappiness = temp.getHappinesssScore() + score;
				simPersonModel currentNewUser = new simPersonModel(temp.getID(), newHappiness, temp.getPersonality(),
						temp.getPersonalityOriginal());

				simCycleTempList.add(currentNewUser);

			}
			simGovernmentData.add(simCycleTempList);
		}

	}

	public void generatePunishmentScore() {

		for (int i = 0; i < simOriginalData.size(); i++) {
			LinkedList<simPersonModel> simCycleTempList = new LinkedList<simPersonModel>();
			ListIterator<simPersonModel> listIterator = simGovernmentData.get(i).listIterator();
			int score = runSums.get(i).getScore();
			while (listIterator.hasNext()) {
				simPersonModel tempObject = listIterator.next();
				//if the gove score for that run is neg and a user is selfish do some math to figure out if they get punished
				if (score > 0 && tempObject.getPersonality() == selfish) {
					//random seed
					Double randomSeed = Math.random();
					//if a user is unlucky and goes to jail
					if (randomSeed < .1) {
						//90% of the time run this
						//System.out.println("Punishment");
						int newHappiness = tempObject.getHappinesssScore() - 50;
						simPersonModel currentNewUser = new simPersonModel(tempObject.getID(), newHappiness, tempObject.getPersonality(),
								tempObject.getPersonalityOriginal());

						simCycleTempList.add(currentNewUser);
					} else {
						//10% of the time run this
						//System.out.println("No Punishment");
						simPersonModel tempSelfishNoPunishment = new simPersonModel(tempObject.getID(), tempObject.getHappinesssScore(), tempObject.getPersonality(),
								tempObject.getPersonalityOriginal());
						simCycleTempList.add(tempSelfishNoPunishment);
					}
						
					}else if (score < 0 && tempObject.getPersonality() == selfish) {
						//random seed
						Double randomSeed1 = Math.random();
						//if a user is unlucky and goes to jail
						if (randomSeed1 < .5) {
							//90% of the time run this
							//System.out.println("Punishment");
							int newHappiness = tempObject.getHappinesssScore() - 75;
							simPersonModel currentNewUser = new simPersonModel(tempObject.getID(), newHappiness, tempObject.getPersonality(),
									tempObject.getPersonalityOriginal());

							simCycleTempList.add(currentNewUser);
							
						} else {
						//10% of the time run this
						//System.out.println("No Punishment");
						simPersonModel tempSelfishNoPunishment = new simPersonModel(tempObject.getID(), tempObject.getHappinesssScore(), tempObject.getPersonality(),
								tempObject.getPersonalityOriginal());
						simCycleTempList.add(tempSelfishNoPunishment);
					}

				}else{
					//if not selfish just insert
					simPersonModel tempNonSelfish = new simPersonModel(tempObject.getID(), tempObject.getHappinesssScore(), tempObject.getPersonality(),
							tempObject.getPersonalityOriginal());
					simCycleTempList.add(tempNonSelfish);
				}
			}
			simGovernmentPunishmentData.add(simCycleTempList);
		}

	}
	public static ArrayList<LinkedList> generateData(ArrayList<LinkedList> simOutPutList, int simCount, int cycles) {

		Boolean altruistic = true;
		Boolean selfish = false;
		Boolean personality = null;
		int simId = 1;
		// users in the study
		LinkedList<simPersonModel> simCycleUserList = new LinkedList<simPersonModel>();
		for (int i = 0; i < simCount; i++) {

			int id = -1;
			// select id and
			id = idChoice(id);
			//pick a personality
			int personalityOriginal = id;
			personality = personalityChoice(personality, id);

			// fill the model to be editied by the program
			simPersonModel simUser = new simPersonModel(simId, 20, personality, personalityOriginal);

			// test print
			// System.out.println(simUser.toString() + "idChoice:" + id)
			// add user to linked list for that cycle
			simCycleUserList.add(simUser);
			// simId++
			simId++;

		}

		// test print of filling users list
		//System.out.println("TEST PRINT AFTER LIST GETS FRIST CREATED" +
		 //simCycleUserList);
		// System.out.println(simCycleUserList);

		// create a edit list so that way the original data is kept
		LinkedList<simPersonModel> simCycleEditList = new LinkedList<simPersonModel>(simCycleUserList);

		for (int i = 0; i < cycles; i++) {

			// create a temp list to store this cycle and a temp iterator to go
			// over the list
			ListIterator<simPersonModel> listIterator = simCycleEditList.listIterator();
			LinkedList<simPersonModel> simCycleTempList = new LinkedList<simPersonModel>();
			for (simPersonModel sim : simCycleEditList) {

				simPersonModel temp = sim;
				// System.out.println("SYSTEM TEST BEFORE EDITING HAPPINESS : "
				// + temp.getHappinesssScore());
				if (temp.getPersonalityOriginal() == 2) {
					temp.setPersonality(personalityChoice(temp.getPersonality(), 2));
				}
				// edit the happiness of the user for this cycle
				if (temp.getPersonality() == selfish) {
					int newHappiness = temp.getHappinesssScore() + 5;
					temp.setHappinesssScore(newHappiness);
				} else if (temp.getPersonality() == altruistic) {
					int newHappiness = temp.getHappinesssScore() - 2;
					temp.setHappinesssScore(newHappiness);
				}
				// System.out.println(listIterator.next());

				// copy the new user to a temp location in case i need to store
				// the previous run some time
				simPersonModel copyTemp = new simPersonModel(temp.getID(), temp.getHappinesssScore(),
						temp.getPersonality(), temp.getPersonalityOriginal());

				// copy new cycle user to temp list
				simCycleTempList.add(copyTemp);

				// System.out.println(copyTemp.toString());
			}

			// add cycle to the return data
			simOutPutList.add(simCycleTempList);
		}

		return simOutPutList;
	}
	//randomize choice for a user
	public static int idChoice(int id) {

		Random rand = new Random();
		id = rand.nextInt(3);
		return id;

	}
	//pick a personality for a simModel object
	public static Boolean personalityChoice(Boolean personality, int id) {

		Boolean altruistic = true;
		Boolean selfish = false;

		Random rand = new Random();
		switch (id) {
		case 0:
			personality = selfish;
			break;
		case 1:
			personality = altruistic;
			break;
		case 2:
			personality = rand.nextBoolean();
			break;
		default:
			personality = rand.nextBoolean();
			break;
		}
		return personality;
	}

	public ArrayList<simGovernmentScoreModel> getRunSums() {
		return runSums;
	}

	public void setRunSums(ArrayList<simGovernmentScoreModel> runSums) {
		this.runSums = runSums;
	}

	public ArrayList<LinkedList> getSimGovernmentData() {
		return simGovernmentData;
	}

	public void setGovernmentSimData(ArrayList<LinkedList> simData) {
		this.simGovernmentData = simData;
	}

	public ArrayList<LinkedList> getSimOriginalData() {
		return simOriginalData;
	}

	public void setSimOriginalData(ArrayList<LinkedList> simOriginalData) {
		this.simOriginalData = simOriginalData;
	}
	public ArrayList<LinkedList> getSimGovernmentPunishmentData() {
		return simGovernmentPunishmentData;
	}

	public void setSimGovernmentPunishmentData(ArrayList<LinkedList> simGovernmentPunishmentData) {
		this.simGovernmentPunishmentData = simGovernmentPunishmentData;
	}

	public void setSimGovernmentPunishmentDataClear() {
		// TODO Auto-generated method stub
		this.simGovernmentPunishmentData.clear();
	}

	public void setGovernmentSimDataClear() {
		// TODO Auto-generated method stub
		this.simGovernmentData.clear();
	}

}
