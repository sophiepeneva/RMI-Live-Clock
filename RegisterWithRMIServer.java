# RMI-Live-Clock
final exam preparation

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterWithRMIServer {
    public static void main(String[] args) {
        try {
            TimeServerInterface timeServerInterface = new TimeServerInterfaceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Time Server Interface",timeServerInterface);
            System.out.println("Time Server " + timeServerInterface + " registered");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
