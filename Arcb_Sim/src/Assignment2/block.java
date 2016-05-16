package Assignment2;

public class block implements Comparable<block>{
	
	private boolean isValid;
    private boolean isDirty;
	private int offset;
	private int tag;
	private int dirty;
	private long seq;
	private int age;

	
	public block() {
		super();
		
		// this offset this.offset = blockSize;
	}
	
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public boolean isDirty() {
		return isDirty;
	}
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTag() {
		
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public int getDirty() {
		return dirty;
	}
	public void setDirty(int dirty) {
		this.dirty = dirty;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}
	
	  
	public String toString() {
		return Integer.toHexString(tag) + "(" + age + ")";
	}
	public int compareTo(block o){
		if(this.getTag() >= o.getTag()){
			return 1;
		}else{
			return -1;
			}
		}
	

}
