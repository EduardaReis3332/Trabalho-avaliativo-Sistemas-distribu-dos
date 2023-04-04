package veiculos;

import java.util.List;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class VeiculoServer implements VeiculoInterface {

    private final List<Veiculo> veiculos;

    public VeiculoServer() {
        veiculos = new ArrayList<>();
    }

    @Override
    public List<Veiculo> search2Ano(int anoVeiculo) throws RemoteException {
        List<Veiculo> resultado = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (v.getAno() == anoVeiculo) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    @Override
    public void add(Veiculo v) throws RemoteException {
        veiculos.add(v);
    }

    public static void main(String[] args) throws MalformedURLException, AlreadyBoundException {
        try {
            //VeiculoServer server = new VeiculoServer();
            LocateRegistry.createRegistry(1099);

            //Naming.rebind("VeiculoServer", server);
            //System.out.println("Servidor RMI iniciado");

            VeiculoServer obj = new VeiculoServer();
            VeiculoInterface stub = (VeiculoInterface) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ClassificadosVeiculos", stub);

            System.out.println("Servidor online!");
        } catch (RemoteException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
