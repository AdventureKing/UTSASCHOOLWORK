package Assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cache {

	private int blksz;
	private int originalBlkSz;
	private int nsets;
	private set[] sets;
	private int numHits;
	private int numWrites;
	private int numRead;
	private int numMisses;
	private int numOfBlocks;
	private int cacheSize;
	private int offset;
	private int index;
	private int numberOfSets;
	private int accesses = 0;
	private String replacement;
	private int Assoc;

	public cache(int cacheSize, int blockSize, ArrayList<Long> memoryAddress, String replacementPolicy, int assoc_of_cache) {
		super();
		this.cacheSize = cacheSize;
		this.blksz = (int) Math.pow(2, blockSize);
		this.originalBlkSz = blockSize;
		this.numOfBlocks = (int) (Math.pow(2, this.cacheSize) / Math.pow(2, this.originalBlkSz));
		this.offset = this.originalBlkSz;
		this.index = (int) (Math.log(numOfBlocks) / Math.log(2));
		this.numberOfSets = (int) (numOfBlocks /Math.pow(2, assoc_of_cache)); 
		this.Assoc = assoc_of_cache;
		sets = new set[numberOfSets];
		for (int i = 0; i < numberOfSets; i++) {
			sets[i] = new set(Assoc,replacementPolicy);
		}
				
	}

	public String getStoredTag(int setNumber){
		List<block> setList = sets[setNumber].getBlocks();
		Collections.sort(setList);
		String tempString = "";
		for(int i = 0; i < setList.size(); i++){
			if(i == setList.size() - 1){
				tempString = tempString + setList.get(i);
			}else{
				tempString = tempString + setList.get(i) + ",";
			}
		}
		return tempString;
		
	}

	public String Access(Long memoryAddress_Current) {
		// System.out.println(address);
		int setNumber = getBlockPosition(memoryAddress_Current);
		// System.out.println(address);
		int tag = getTag(memoryAddress_Current);

		if(sets[setNumber].listTags().contains(tag)){
			numHits++;
			accesses++;
			sets[setNumber].read(tag,accesses);
			return "hit";
		} else {
			numMisses++;
			accesses++;
			sets[setNumber].write(tag,accesses);
			return "miss";
		}

	}

	public int getTag(Long memoryAddress) {

		return (getBlockAddr(memoryAddress) / numberOfSets);
	}

	public int getBlockPosition(Long memory_Address) {
		return (getBlockAddr(memory_Address) % numberOfSets);
		

	}
	public int getBlockAddr(Long dec) {
		return (int) (dec / Math.pow(2, originalBlkSz));
	}

	public set[] getSets() {
		return sets;
	}

	public set getSetatIndex(int index) {
		return sets[index];
	}

	public void setSet(set[] set) {
		this.sets = set;
	}

	public int getNumHits() {
		return numHits;
	}

	public void setNumHits(int numHits) {
		this.numHits = numHits;
	}

	public int getNumWrites() {
		return numWrites;
	}

	public void setNumWrites(int numWrites) {
		this.numWrites = numWrites;
	}

	public int getNumRead() {
		return numRead;
	}

	public void setNumRead(int numRead) {
		this.numRead = numRead;
	}

	public int getNumMisses() {
		return numMisses;
	}

	public void setNumMisses(int numMisses) {
		this.numMisses = numMisses;
	}

	public int getBlksz() {
		return blksz;
	}

	public void setBlksz(int blksz) {
		this.blksz = blksz;
	}

	public int getNsets() {
		return nsets;
	}

	public void setNsets(int nsets) {
		this.nsets = nsets;
	}
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	public void setNumOfBlocks(int numOfBlocks) {
		this.numOfBlocks = numOfBlocks;
	}

	public int getAccesses() {
		return accesses;
	}

	public void setAccesses(int accesses) {
		this.accesses = accesses;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

}
