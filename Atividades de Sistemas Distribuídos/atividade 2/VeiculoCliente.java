package veiculos;

import java.util.List;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;

public class VeiculoCliente {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        try {
            Registry registry = LocateRegistry.getRegistry();
            VeiculoInterface stub = (VeiculoInterface) registry.lookup("ClassificadosVeiculos");

            Scanner input = new Scanner(System.in);
            String opcao = "";

            while (!opcao.equals("3")) {
                System.out.println("\nDigite a opção desejada:");
                System.out.println("1 - Adicionar novo veículo");
                System.out.println("2 - Buscar veículos a partir de um ano");
                System.out.println("3 - Sair");

                opcao = input.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.println("\nDigite o modelo do carro: ");
                        String nomeCliente = input.nextLine();
                        System.out.println("Digite a marca do veículo: ");
                        String marcaVeiculo = input.nextLine();
                        System.out.println("Digite o valor de venda do veículo: ");
                        double valorVenda = input.nextDouble();
                        System.out.println("Digite o ano do veículo: ");
                        int ano = input.nextInt();

                        input.nextLine(); // Limpa o buffer do teclado

                        Veiculo v = new Veiculo(nomeCliente, marcaVeiculo, valorVenda, ano);
                        stub.add(v);

                        System.out.println("\nVeículo adicionado com sucesso!");
                        break;
                    case "2":
                        System.out.println("\nDigite o ano desejado: ");
                        int anoBusca = input.nextInt();

                        input.nextLine(); // Limpa o buffer do teclado

                        List<Veiculo> resultado = stub.search2Ano(anoBusca);

                        if (!resultado.isEmpty()) {
                            System.out.println("\nVeículos encontrados:");
                            for (Veiculo veiculo : resultado) {
                                System.out.println(veiculo.getNomeVeiculo());

                            }
                        }
                }
            }
        } catch (RemoteException e) {
            System.err.println("Erro de comunicação com o servidor: " + e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("Não foi possível encontrar o registro do objeto remoto: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}
