package veiculos;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VeiculoInterface extends Remote {

    public List search2Ano(int anoVeiculo) throws RemoteException;

    public void add(Veiculo v) throws RemoteException;
}
