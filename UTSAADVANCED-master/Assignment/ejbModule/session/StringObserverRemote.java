package session;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringObserverRemote extends Remote {
	public void callback(String data) throws RemoteException; 
}
