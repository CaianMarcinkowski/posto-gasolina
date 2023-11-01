import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {

    // Constantes
    private static final String ARQUIVO_MODELO = "./files/modelos.csv";
    private static final String ARQUIVO_VEICULOS = "./files/veiculos.csv";
    private static final double VALOR_GASOLINA = 2.90;
    private static final double VALOR_ETANOL = 2.27;
    private static final double VALOR_DIESEL = 3.00;

    private static final String CSV_DIVISOR = ";";

    public static void main(String[] args) throws Exception {

        // Variaveis para armazenar a quantidade de combustivel que vai ser colocado em
        // cada bomba
        int bomba_01 = 0;
        int bomba_02 = 0;
        int bomba_03 = 0;
        int bomba_04 = 0;

        // Variaveis para obter o valor total de cada combustivel em L
        int total_etanol = 0;
        int total_gasolina = 0;
        int total_diesel = 0;

        // BufferedReader para ler as informações dos arquivos .csv
        BufferedReader br_veiculos = null;
        BufferedReader br_modelo = null;

        String linha = "";

        try {
            br_veiculos = new BufferedReader(new FileReader(ARQUIVO_VEICULOS));
            // Contador para não imprimir os titulos das colunas (Primeira linha), assim ele
            // nunca imprimi a
            // primeira linha do arquivo .csv
            int cont_linha_veiculo = 0;
            // valor para alternar entre as bombas que abastecem gasolina, quando a bomba
            // numero 1 abastece ela seta esta variavel para o valor da proxima bomba que
            // deve se usada
            int alternar_bomba = 01;
            while ((linha = br_veiculos.readLine()) != null) {
                // Validando se estamos passando pela primeira linha do arquivo .csv
                if (cont_linha_veiculo != 0) {
                    String[] veiculo = linha.split(CSV_DIVISOR);

                    br_modelo = new BufferedReader(new FileReader(ARQUIVO_MODELO));

                    while ((linha = br_modelo.readLine()) != null) {
                        String[] modelo = linha.split(CSV_DIVISOR);

                        if (modelo[modelo.length - 5].equalsIgnoreCase(veiculo[veiculo.length - 2])) {

                            String tipo_combustivel = "";
                            // Armazenando km por litro de cada veiculo com seus respectivos combustiveis
                            double km_por_litro_etanol, km_por_litro_gasolina, km_por_litro_diesel;
                            // Validando se o valores que esta na coluna Consumo Etanol (Km/L) esta vazio
                            if (!modelo[modelo.length - 4].isEmpty()) {
                                km_por_litro_etanol = Double
                                        .parseDouble(modelo[modelo.length - 4].replaceAll(",", "."));
                            } else {
                                // Caso esteja vazio ou for nulo no arquivo .csv ele define o valor da variavel
                                // em 0
                                km_por_litro_etanol = 0;
                            }

                            // Validando se o valores que esta na coluna Consumo Gasolina (Km/L) esta vazio
                            if (!modelo[modelo.length - 3].isEmpty()) {
                                km_por_litro_gasolina = Double
                                        .parseDouble(modelo[modelo.length - 3].replaceAll(",", "."));
                            } else {
                                // Caso esteja vazio ou for nulo no arquivo .csv ele define o valor da variavel
                                // em 0
                                km_por_litro_gasolina = 0;
                            }

                            if (!modelo[modelo.length - 2].isEmpty()) {
                                km_por_litro_diesel = Double
                                        .parseDouble(modelo[modelo.length - 2].replaceAll(",", "."));
                            } else {
                                km_por_litro_diesel = 0;
                            }

                            // Calculando quanto cada veiculo faz por litro para validar qual o melhor para
                            // abastecer
                            double preco_por_km_etanol = VALOR_ETANOL / km_por_litro_etanol;
                            double preco_por_km_gasolina = VALOR_GASOLINA / km_por_litro_gasolina;
                            double preco_por_km_diesel = VALOR_DIESEL / km_por_litro_diesel;

                            if (preco_por_km_etanol < preco_por_km_gasolina) {
                                if (km_por_litro_etanol == 0.0) {
                                    tipo_combustivel = "GASOLINA";
                                    if (alternar_bomba == 01) {
                                        bomba_01 = bomba_01
                                                + Integer.parseInt(modelo[modelo.length - 1]);
                                        alternar_bomba = 04;
                                    } else {
                                        bomba_04 = bomba_04 + Integer.parseInt(modelo[modelo.length - 1]);
                                        alternar_bomba = 01;
                                    }
                                } else {
                                    tipo_combustivel = "ETANOL";
                                    bomba_02 = bomba_02
                                            + Integer.parseInt(modelo[modelo.length - 1]);
                                }
                            } else if (preco_por_km_gasolina < preco_por_km_diesel) {
                                tipo_combustivel = "GASOLINA";
                                if (alternar_bomba == 01) {
                                    bomba_01 = bomba_01
                                            + Integer.parseInt(modelo[modelo.length - 1]);
                                    alternar_bomba = 04;
                                } else {
                                    bomba_04 = bomba_04 + Integer.parseInt(modelo[modelo.length - 1]);
                                    alternar_bomba = 01;
                                }
                            } else {
                                tipo_combustivel = "DIESEL";
                                bomba_03 = bomba_03 + Integer.parseInt(modelo[modelo.length - 1]);
                            }

                            System.out.println(
                                    "O Veiculo modelo " + veiculo[veiculo.length - 2] +
                                            ", placa " + veiculo[veiculo.length - 1] +
                                            " foi abastecido com " + modelo[modelo.length - 1] +
                                            " litros de " + tipo_combustivel);

                        }

                    }

                }
                cont_linha_veiculo++;
            }

            total_etanol = bomba_02;
            total_gasolina = bomba_01 + bomba_04;
            total_diesel = bomba_03;

            System.out.println("\n\nRelatório: \n-------------------------- \n" +
                    "Valores: \n" +
                    "Total abastecido na bomba 1 (GASOLINA): " + bomba_01 + " litros\n" +
                    "Total abastecido na bomba 2 (ETANOL): " + bomba_02 + " litros\n" +
                    "Total abastecido na bomba 3 (DIESEL): " + bomba_03 + " litros\n" +
                    "Total abastecido na bomba 4 (GASOLINA): " + bomba_04 + " litros\n" +
                    "Total geral abastecido de GASOLINA: " + total_gasolina + " litros\n" +
                    "Total geral abastecido de ETANOL: " + total_etanol + " litros\n" +
                    "Total geral abastecido de DIESEL: " + total_diesel + " litros\n" +
                    "Total abastecido: " + (total_gasolina + total_etanol + total_diesel) + " litros\n");

            System.out.println("Tempos: \n" +
                    "Tempo de abastecimento da bomba 01 (GASOLINA): " + bomba_01 / 10 + " minutos\n" +
                    "Tempo de abastecimento da bomba 02 (ETANOL): " + bomba_02 / 12 + " minutos\n" +
                    "Tempo de abastecimento da bomba 02 (DIESEL): " + bomba_03 / 15 + " minutos\n" +
                    "Tempo de abastecimento da bomba 04 (GASOLINA): " + bomba_04 / 10 + " minutos\n");

            System.out.printf("Lucros: \n" +
                    "Valor total de abastecimento da bomba 01 (GASOLINA): R$ %.2f \n", bomba_01 * VALOR_GASOLINA);
            System.out.printf("Valor total de abastecimento da bomba 02 (ETANOL): R$ %.2f \n",
                    bomba_02 * VALOR_ETANOL);
            System.out.printf("Valor total de abastecimento da bomba 03 (DIESEL): R$ %.2f \n",
                    bomba_03 * VALOR_DIESEL);
            System.out.printf("Valor total de abastecimento da bomba 04 (GASOLINA): R$ %.2f \n",
                    bomba_04 * VALOR_GASOLINA);
            double valor_total_abastecido = (bomba_01 * VALOR_GASOLINA) + (bomba_02 * VALOR_ETANOL)
                    + (bomba_03 * VALOR_DIESEL) + (bomba_04 * VALOR_GASOLINA);
            System.out.printf("Valor total abastecido: R$ %.2f \n", valor_total_abastecido);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br_veiculos.close();
            br_modelo.close();
        }

    }
}
