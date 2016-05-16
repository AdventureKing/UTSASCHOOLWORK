package Assignment2;

import java.util.ArrayList;
import java.util.List;

public class set {


	private block[] blocks;
	private String replacementPolicy;
	private int numOfBlocksInSet;
	
	public set(int assoc,String replacementPolicy) {
		super();
		this.replacementPolicy = replacementPolicy;
		this.numOfBlocksInSet = (int) (Math.pow(2,assoc));
		blocks = new block[numOfBlocksInSet];
		for (int i = 0; i < numOfBlocksInSet; i++) {

			blocks[i] = new block();
			blocks[i].setValid(false);
			
			
		}
	}

	public List<Integer> listTags(){
		List<Integer> listOfTags = new ArrayList<Integer>();
		for(int i = 0; i < numOfBlocksInSet; i++){
			if(blocks[i].isValid()){
				listOfTags.add(blocks[i].getTag());
			}
		}
		return listOfTags;
	}
	
	public void write(int tag, int age){
		int replace = 0;
		for(int i = 0; i < blocks.length; i++){
			if(blocks[i].isValid() == false){
				blocks[i].setTag(tag);
				blocks[i].setValid(true);
				blocks[i].setAge(age);
				return;
			}else{
				if(blocks[i].getAge() < blocks[replace].getAge()){
					replace = i;
				}
			}
		}
		blocks[replace].setTag(tag);
		blocks[replace].setAge(age);
		return;
	}
	public List<String> getTags(){
		List<String> tagsInBlock = new ArrayList<String>();
		for(int i = 0; i < blocks.length; i++){
			if(blocks[i].isValid()){
				tagsInBlock.add(Integer.toHexString(blocks[i].getTag()) + "(" + 
						Integer.toString(blocks[i].getAge())+ ")");
			}
		}
		return tagsInBlock;
	}
	public void read(int tag, int accesses){
		if(replacementPolicy.equalsIgnoreCase("lru")){
			for(int i = 0; i < blocks.length; i++){
				if(blocks[i].getTag() == tag){
					blocks[i].setAge(accesses);
					return;
				}
			}
		}
	}
	
	public List<block> getBlocks() {
		List<block> blocksInSet = new ArrayList<block>();
		for(int i = 0; i < blocks.length; i++){
			if(blocks[i].isValid()){
				blocksInSet.add(blocks[i]);
			}
		}
		return blocksInSet;
	}

	public void setBlocks(block[] blocks) {
		this.blocks = blocks;
	}

}
