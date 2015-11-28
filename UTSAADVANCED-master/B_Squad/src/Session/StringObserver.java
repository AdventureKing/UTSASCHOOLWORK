package Session;

import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

/*
 * StateObserver: client-side implementation of the observer
 * 				when remote EJB executes callback, 
 * 				the observer updates master's status bar
 * WARNING: be careful not to have EJB calls in the observer callback
 *	as EJB is blocked until callback completes.
 *	an EJB call in callback will Deadlock the client
 */
public class StringObserver implements StringObserverRemote {
	//master is the real consumer of the notification
	private Cabinetron.MasterFrame master = null;
	
	//exportObject turns this class into a little client-side server 
	//so that the remote EJB can call it back
	public StringObserver(Cabinetron.MasterFrame mp) throws RemoteException {
		PortableRemoteObject.exportObject(this);
		master = mp;
	}

	//callback the method that the EJB remotely calls
	@Override
	public void callback(String data) throws RemoteException {
		//master.updateTextBox(data);
	}
	
}
