package session;

import javax.ejb.Remote;

@Remote
public interface StringBeanRemote {
	public void registerObserver(StringObserverRemote o);
	public void unregisterObserver(StringObserverRemote o);
	public void setEditText(String txt);
}
