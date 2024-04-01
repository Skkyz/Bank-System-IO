package view;

import entities.Conta;
import services.OperacoesService;
import utils.LeitorDadosUsuario;

import java.math.BigDecimal;
import java.util.InputMismatchException;


public class MenuBanco {

    public LeitorDadosUsuario leitorDadosUsuario = new LeitorDadosUsuario();


    static Conta conta = new Conta();
    static OperacoesService operacoes = new OperacoesService(conta);

    // método factory
    public static MenuBanco iniciar (){
        MenuBanco menuBanco = new MenuBanco();
        menuBanco.iniciarBanco();
        return menuBanco;
    }

    public void iniciarBanco() {
        System.out.println(CoresANSI.YELLOW + "=== BEM-VINDO AO BANCO ===" + CoresANSI.RESET + "\n");

        System.out.println("Digite seu nome:");
        conta.setNomeDoTitular(leitorDadosUsuario.lerString());

        System.out.println("Digite o número da sua conta:");
        conta.setNumeroDaConta(leitorDadosUsuario.lerInteiro());

        statusCliente();


    }

    public void sairBanco() {
        System.out.println("\n" +CoresANSI.GREEN + conta.getNomeDoTitular() + ", você está saindo... " +
                "\nAté a próxima ;D\n"+ CoresANSI.RESET);
        System.exit(0);
    }

    public void statusCliente() {
        System.out.println("\n" + CoresANSI.CYAN + "=================================" + CoresANSI.RESET);
        System.out.println("Bem-vindo(a): " + CoresANSI.GREEN + conta.getNomeDoTitular() + CoresANSI.RESET);
        System.out.println("Número do conta: " + CoresANSI.PURPLE + conta.getNumeroDaConta() + CoresANSI.RESET);
        System.out.println("Saldo atual: " + CoresANSI.YELLOW + "R$" + conta.getSaldo() + CoresANSI.RESET);
        System.out.println(CoresANSI.CYAN + "=================================" + CoresANSI.RESET + "\n");

        menuSelecao();

    }

    public void menuSelecao() {
        int valor = 0;

        do {
            try {
                System.out.println(CoresANSI.GREEN + "==== OPERAÇÕES ====" + CoresANSI.RESET);
                System.out.println("1 - " + CoresANSI.CYAN + "Depositar" + CoresANSI.RESET);
                System.out.println("2 - " + CoresANSI.CYAN + "Sacar" + CoresANSI.RESET);
                System.out.println("3 - " + CoresANSI.CYAN + "Transferir" + CoresANSI.RESET);
                System.out.println("4 - " + CoresANSI.CYAN + "Historico" + CoresANSI.RESET);
                System.out.println("5 - " + CoresANSI.RED + "Sair" + CoresANSI.RESET);
                System.out.println("Digite sua opção:");

                valor = leitorDadosUsuario.lerInteiro();
                leitorDadosUsuario.lerString(); // consumir o enter

                switch (valor) {
                    case 1:
                        operacaoDepositar();
                        break;
                    case 2:
                        operacaoSacar();
                        break;
                    case 3:
                        transferirBanco();
                        break;
                    case 4:
                        historicoBanco();
                        break;
                    case 5:
                        sairBanco();
                    default:
                        System.out.println(CoresANSI.RED + "\nOpção inválida. Por favor, escolha uma das opções disponíveis." + CoresANSI.RESET + "\n");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(CoresANSI.RED + "\nOpção inválida. Por favor, digite um número correspondente à operação desejada." + CoresANSI.RESET + "\n");
                leitorDadosUsuario.lerString();
            }

        } while (valor != 5);
    }

    public void operacaoDepositar() {

        try {
            System.out.println("\n" + CoresANSI.YELLOW + "=== DEPOSITAR ===" + CoresANSI.RESET);
            System.out.println("Digite o valor a ser depositado:");

            BigDecimal valorDeposito = new BigDecimal(leitorDadosUsuario.lerString());
            operacoes.depositar(valorDeposito);

            System.out.println(CoresANSI.GREEN + "Depósito de " + valorDeposito + " R$ realizado com sucesso." + CoresANSI.RESET);
            System.out.println("Novo saldo: " + conta.getSaldo() + " R$");
            leitorDadosUsuario.lerString();

            statusCliente();

        } catch (NumberFormatException e) {
            System.out.println(CoresANSI.RED + "\nValor inválido. Por favor, insira um valor numérico válido." + CoresANSI.RESET + "\n");
        }


    }

    public void operacaoSacar() {
        System.out.println("\n" + CoresANSI.YELLOW + "=== SACAR ===" + CoresANSI.RESET);
        System.out.println("Digite o valor a ser sacado:");

        try {
            BigDecimal valorSaque = new BigDecimal(leitorDadosUsuario.lerString());
            operacoes.sacar(valorSaque);
            statusCliente();
        } catch (NumberFormatException e) {
            System.out.println(CoresANSI.RED + "\nValor inválido. Por favor, insira um valor numérico válido." + CoresANSI.RESET + "\n");
        }
    }

    public void transferirBanco() {
        System.out.println("\n" + CoresANSI.YELLOW + "=== TRANSFERIR ===" + CoresANSI.RESET);

        try {
            System.out.println("Digite o número da conta de destino:");
            int numConta = leitorDadosUsuario.lerInteiro();
            leitorDadosUsuario.lerString(); // consumir o enter

            System.out.println("Digite o valor a ser transferido:");
            BigDecimal valorTransferencia = new BigDecimal(leitorDadosUsuario.lerString());
            leitorDadosUsuario.lerString(); // consumir o enter

            operacoes.transferir(numConta, valorTransferencia);

        } catch (NumberFormatException e) {
            System.out.println(CoresANSI.RED + "\nValor inválido. Por favor, insira um número de conta e um valor numérico válido." + CoresANSI.RESET + "\n");
        }
    }


    public void historicoBanco() {
        operacoes.historico();
    }
}
