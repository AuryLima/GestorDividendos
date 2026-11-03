import java.io.*;
import java.util.*;

public class GestorDividendos {
    // Lista para manter os ativos na memória durante a execução
    static List<String> carteira = new ArrayList<>();

    public static void main(String[] args) {
        carregarDados(); // Lê o arquivo ao iniciar

        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n=== GESTOR DE DIVIDENDOS ===");
            System.out.println("1. Adicionar Ativo");
            System.out.println("2. Ver Tabela de Ativos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = 0;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Ativo (ex: PETR4): ");
                    String nome = scanner.nextLine().toUpperCase();
                    
                    System.out.print("Quantidade: ");
                    String qtd = scanner.nextLine();
                    
                    System.out.print("Preço Pago: ");
                    String preco = scanner.nextLine();
                    
                    System.out.print("Dividendo por Ação: ");
                    String div = scanner.nextLine();

                    // Formata os dados separados por ponto e vírgula para salvar no arquivo
                    String dadosFormatados = nome + ";" + qtd + ";" + preco + ";" + div;
                    
                    // Salva no arquivo e também adiciona na memória para visualização imediata
                    salvarNoArquivo(dadosFormatados);
                    carteira.add(dadosFormatados);
                    
                    System.out.println("Ativo adicionado com sucesso!");
                    break;

                case 2:
                    System.out.println("\n=================== CARTEIRA DE ATIVOS ===================");
                    if (carteira.isEmpty()) {
                        System.out.println("Nenhum ativo cadastrado ainda.");
                    } else {
                        System.out.printf("%-10s | %-10s | %-12s | %-10s\n", "ATIVO", "QTD", "PREÇO", "DIVIDENDO");
                        System.out.println("----------------------------------------------------------");
                        for (String linha : carteira) {
                            String[] partes = linha.split(";");
                            if (partes.length == 4) {
                                System.out.printf("%-10s | %-10s | R$ %-9s | R$ %-10s\n", partes[0], partes[1], partes[2], partes[3]);
                            }
                        }
                    }
                    System.out.println("==========================================================");
                    break;

                case 3:
                    System.out.println("Saindo do programa. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
        
        scanner.close();
    }

    public static void carregarDados() {
        File arquivo = new File("carteira.txt");
        
        // Se o arquivo ainda não existir, não faz nada
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    carteira.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    public static void salvarNoArquivo(String dados) {
        try (FileWriter fw = new FileWriter("carteira.txt", true)) {
            fw.write(dados + "\n");
        } catch (IOException e) { 
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}
