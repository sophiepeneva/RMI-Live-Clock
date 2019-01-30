# RMI-Live-Clock
final exam preparation

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TimeServerInterface extends Remote {
    Time setupClock(String clockName) throws RemoteException;
    void addClocks(String name, Clock clock) throws  RemoteException;
}
