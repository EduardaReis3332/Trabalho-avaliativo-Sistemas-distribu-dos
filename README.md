# Trabalho Avaliativo Sistemas Distribuídos
Professor : Claudio Martins

Alunos : Eduarda Reis, Eduardo Cordovil e Marcelo Faro

# - Atividade 1

# Calculadora Remota em UDP

Esta atividade consite em uma implementação de uma calculadora remota usando Java + Sockets, onde essa calculadora realiza as operações de soma, subtração, multiplicação e divisão com dois valores recebidos.

# Passo a passo para iniciar o programa:
1. Execute o Servidor, aguarde-o Inicializar.
2. Execute o Cliente, aguarde-o conectar ao servidor.
3. Usar.

# Modo de uso:
Para realizar uma operação, digite normalmente o primeiro valor, seguido do sinal da operação e do segundo valor, separando os operadores do operando por espaços em brancos. Exemplo: 10 * 5

# - Atividade 2

# Classificado de Veículos

Esse programa adiciona e busca informações sobre veículos. O programa é dividido em duas partes: a parte que envia informações para um servidor que vai guardar essas informações (denominada "VeiculoCliente") e o servidor (denominado "VeiculoServer") que recebe as informações enviadas pelo "VeículoCliente".

## VeiculoInterface

Esse código é uma interface chamada "VeiculoInterface" que estende a interface "Remote" do pacote "java.rmi". Essa interface tem dois métodos:

1 - search2Ano, que recebe um inteiro representando um ano de um veículo e retorna uma lista de resultados de busca. Esse método pode lançar uma exceção do tipo "RemoteException".

2 - add, que recebe um objeto do tipo "Veiculo" e não retorna nenhum valor. Esse método pode lançar uma exceção do tipo "RemoteException".

Esses métodos são declarados na interface para serem implementados por uma classe que vai realizar a comunicação remota entre duas aplicações usando o RMI (Remote Method Invocation).
```java
public interface VeiculoInterface extends Remote {

    public List search2Ano(int anoVeiculo) throws RemoteException;

    public void add(Veiculo v) throws RemoteException;
}
```
## Veiculo

Esse código usa uma classe chamada "Serializable" para tornar esse objeto "Veículo" possível de ser salvo em um arquivo e depois lido novamente.
```java
public class Veiculo implements Serializable {...}
```

Esse código é sobre um tipo de objeto chamado "Veículo". Ele tem algumas características como nome do veículo, marca, valor de venda e ano.
```java
private static final long serialVersionUID = 1L;

private String nomeVeiculo;
private String marcaVeiculo;
private double valorVenda;
private int ano;

public Veiculo(String nomeVeiculo, String marcaVeiculo, double valorVenda, int ano) {
    this.nomeVeiculo = nomeVeiculo;
    this.marcaVeiculo = marcaVeiculo;
    this.valorVenda = valorVenda;
    this.ano = ano;
```

Também possui algumas funções para acessar e modificar essas características, como por exemplo, "getNomeVeiculo" que retorna o nome do veículo, "setNomeVeiculo" que modifica o nome do veículo e assim por diante.
```java
public String getNomeVeiculo() {
        return nomeVeiculo;
    }

    public void setNomeVeiculo(String nomeVeiculo) {
        this.nomeVeiculo = nomeVeiculo;
    }

    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
```

## Uso/Exemplos - VeiculoServer

A classe VeiculoServer implementa a interface VeiculoInterface, que define os métodos que podem ser chamados remotamente pelos clientes. O método search2Ano recebe um parâmetro anoVeiculo e retorna uma lista de veículos que possuem o mesmo ano informado. Já o método add recebe um objeto Veiculo e o adiciona à lista de veículos registrados no servidor.
```java
public class VeiculoServer implements VeiculoInterface {...}
```

O método main é responsável por iniciar o servidor. Ele cria um objeto VeiculoServer, exporta esse objeto como um stub (um objeto que pode ser acessado remotamente), registra esse stub com um nome específico (ClassificadosVeiculos) e imprime uma mensagem indicando que o servidor está online.
```java
public static void main(String[] args) throws MalformedURLException, AlreadyBoundException {
        try {
            LocateRegistry.createRegistry(1099);
            VeiculoServer obj = new VeiculoServer();
            VeiculoInterface stub = (VeiculoInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ClassificadosVeiculos", stub);

            System.out.println("Servidor online!");
        } catch (RemoteException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
```
## Uso/Exemplos - VeiculoCliente

No começo do programa, é verificado se o servidor está funcionando. Caso esteja, o programa registra todas as funções que estão disponíveis no servidor.
```java
   Registry registry = LocateRegistry.getRegistry();
        VeiculoInterface stub = (VeiculoInterface) registry.lookup("ClassificadosVeiculos");

```

Ao iniciar a interação com o usuário, é dado um menu, que exibe três opções: adicionar um veículo, buscar por veículos a partir de um ano específico ou sair do programa.
```java
  System.out.println("\nDigite a opção desejada:");
            System.out.println("1 - Adicionar novo veículo");
            System.out.println("2 - Buscar veículos a partir de um ano");
            System.out.println("3 - Sair");
```

Se a opção selecionada for "adicionar um veículo", o programa vai pedir informações sobre o modelo, a marca, o valor e o ano do veículo. Essas informações são então usadas para criar um objeto "veículo" e salvá-lo no servidor.
```java
  System.out.println("\nDigite o modelo do carro: ");
                    String nomeCliente = input.nextLine();
                    System.out.println("Digite a marca do veículo: ");
                    String marcaVeiculo = input.nextLine();
                    System.out.println("Digite o valor de venda do veículo: ");
                    double valorVenda = input.nextDouble();
                    System.out.println("Digite o ano do veículo: ");
                    int ano = input.nextInt();
```

Se a opção selecionada for "buscar por veículos a partir de um ano específico", o programa vai pedir uma informação sobre o ano que deve ser buscado. Depois disso, ele irá buscar todos os veículos que possuem esse ano como informação e apresentar esses veículos na tela do usuário.
```java
case "2":
                    System.out.println("\nDigite o ano desejado: ");
                    int anoBusca = input.nextInt();

                    input.nextLine(); // Limpa o buffer do teclado

                    List<Veiculo> resultado = stub.search2Ano(anoBusca);

                    if (!resultado.isEmpty()) {
                        System.out.println("\nVeículos encontrados:");
                        for (Veiculo veiculo : resultado) {
                            System.out.println(veiculo.getNomeVeiculo());
```

# - Atividade 3
