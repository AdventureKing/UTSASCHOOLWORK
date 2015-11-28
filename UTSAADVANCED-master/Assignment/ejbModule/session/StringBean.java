package session;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/*
 * StringBean : a Singleton session bean that shares a String amongst clients
 */

//@Stateful(name="StateBeanJedis")
//@Stateless(name="StateBeanJedis")
@Singleton(name="StringBean")

public class StringBean implements StringBeanRemote {
	private String editText;
	
	private ArrayList<StringObserverRemote> observers;
	
	public StringBean() {
		editText = "";
		observers = new ArrayList<StringObserverRemote>();
	}
	
	public void setEditText(String txt) {
		editText = txt;
		notifyObservers();
	}
	
	private void notifyObservers() {
		try {
			for(int i = 0; i < observers.size(); i++) { 
				((StringObserverRemote) observers.get(i)).callback(editText);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
		
	@Lock(LockType.WRITE)
	public void registerObserver(StringObserverRemote o) {
		observers.add(o);
	}

	@Lock(LockType.WRITE)
	public void unregisterObserver(StringObserverRemote o) {
		observers.remove(o);
	}
}
