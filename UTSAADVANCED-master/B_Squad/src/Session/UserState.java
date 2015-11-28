package Session;

import java.util.Hashtable;

public class UserState {
//holds a key manager gets key 1
	
	private int allow = 0;
	private int decline = 1;
	
	private int productionManager = 0;
	private int inventoryManager = 1;
	
	private int admin = -1;
	
	private int stateKey = -100;
	private String loginName = null;
	private String password = null;
	private Hashtable<String,String> logins = new Hashtable<String,String>();
	
	public int getStateKey() {
		return stateKey;
	}

	public int checkLogin(){
		if(logins.containsKey(loginName) && logins.containsValue(password)){
			return 0;
			
		}else{
			return 1;
		}
	}

	public UserState(int stateKey, String loginName, String password){
		logins.put("BiG98928","ILoveLucy123");
		logins.put("Papi","IhateMyJob" );
		logins.put("TheBestEver", "wutangKillaBees");
		this.stateKey = stateKey;
		
		this.setLoginName(loginName);
		this.setPassword(password);
	
	}
	
	public int canViewProductTemplates(){
		if(this.stateKey == admin || this.stateKey == productionManager){
			return allow;
		}else
			return decline;
	}
	
	public int canAddProductTemplates(){
		if(this.stateKey == admin || this.stateKey == productionManager){
			return allow;
		}else
			return decline;
		
	}
	
	public int canDeleteProductTemplates(){
		if(this.stateKey == admin || this.stateKey == productionManager){
			return allow;
		}else
			return decline;
	}
	
	public int canCreateProducts(){
		if(this.stateKey == admin || this.stateKey == productionManager){
			return allow;
		}else
			return decline;
	}
	
	public int canViewInventory(){
		return allow;
	}
	
	public int canAddInventory(){
		if(this.stateKey == admin || this.stateKey == inventoryManager){
			return allow;
		}else
			return decline;
	}
	
	public int canViewParts(){
		
			return allow;
	}
	
	public int canAddParts(){
		if(this.stateKey == admin || this.stateKey == inventoryManager){
			return allow;
		}else
			return decline;
	}
	
	public int canDeleteParts(){
		if(this.stateKey == admin){
			return allow;
		}else
			return decline;
	}
	
	public int canDeleteInventory(){
		if(this.stateKey == admin){
			return allow;
		}else
			return decline;	
	}



	public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Hashtable<String,String> getLogins() {
		return logins;
	}



	public void setLogins(Hashtable<String,String> logins) {
		this.logins = logins;
	}
	
	
}
