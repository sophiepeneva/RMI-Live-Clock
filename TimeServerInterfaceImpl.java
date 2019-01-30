# RMI-Live-Clock
final exam preparation

import java.util.Map;

public class TimeServerInterfaceImpl extends UnicastRemoteObject implements TimeServerInterface {

    private Map<String, Clock> clientClocks;

    public TimeServerInterfaceImpl() throws RemoteException {
        clientClocks = new HashMap<>();
    }

    @Override
    public void addClocks(String name, Clock clock){
        clientClocks.put(name,clock);
    }

    @Override
    public Time setupClock(String clockName) {
        if(clientClocks.get(clockName)==null){
            System.out.println("The clock wasn't found");
            return null;
        }
        clientClocks.get(clockName).setCurrentTime();
        System.out.println("Clock setup completed");
        return clientClocks.get(clockName).getTime();
    }
}
