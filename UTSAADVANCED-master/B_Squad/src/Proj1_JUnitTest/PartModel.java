package Proj1_JUnitTest;
/**
 * This is the class a part record. It stores the data on a part.
 * @author snow
 *
 */
public class PartModel {
	
		//required are part#,part name, and quantity
		String partNum;
		String partName;
		String vendor;
		int quantity;
		public PartModel(String partNum, String partName, String vendor, int quantity)
		{
			this.partName = partName;
			this.partNum = partNum;
			this.vendor = vendor;
			this.quantity = quantity;
		}
		
		public String getPartNum() {return partNum;}
		public void setPartNum(String partNum) {this.partNum = partNum;}
		
		public String getPartName() {return partName;}
		public void setPartName(String partName) {this.partName = partName;}
		
		public String getVendor() {return vendor;}
		public void setVendor(String vendor) {this.vendor = vendor;}
		
		public int getQuantity() {return quantity;}
		public void setPartQuan(String quantity){
			int var = Integer.valueOf(quantity);
			this.quantity = var;
		}	
		public Object [] getPartInfo(){
			Object [] partInfo = {this.partName,this.partNum,this.quantity,this.vendor};
			
			return partInfo;
			
			
		}
}
