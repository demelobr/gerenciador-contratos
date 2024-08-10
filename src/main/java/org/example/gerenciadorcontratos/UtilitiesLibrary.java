package org.example.gerenciadorcontratos;

import javax.mail.internet.ParseException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Locale;

public class UtilitiesLibrary {
    public static String capitalizeWords(String str) {
        // Transforma a string toda em minúsculas
        str = str.toLowerCase();
        // Separa a string em palavras
        String[] words = str.split(" ");
        StringBuilder capitalized = new StringBuilder();

        // Itera sobre cada palavra e capitaliza a primeira letra
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        // Remove o espaço extra no final da string
        return capitalized.toString().trim();
    }

    public static String getQueryOfSearch(String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryMinValue, String queryMaxValue, String type){
        String sql = "";

        switch (type){
            case "complete":
            case "finances":
                sql = "SELECT * FROM finances WHERE finances.`cpfCollaborator` = $P{queryCpfCollaborator}";
                if(queryStartDateTimePeriod != null){
                    sql = sql + " AND finances.`financeDateTime` >= $P{queryStartDateTime}";
                }
                if(queryEndDateTimePeriod != null){
                    sql = sql + " AND finances.`financeDateTime` <= $P{queryEndDateTime}";
                }
                if(queryContract != null){
                    if(!queryContract.equals("----------")){
                        sql = sql + " AND finances.`nameContract` = $P{queryNameContract}";
                    }
                }
                if(!queryMinValue.isEmpty()){
                    sql = sql + " AND finances.`value` >= $P{queryMinValue}";
                }
                if(!queryMaxValue.isEmpty()){
                    sql = sql + " AND finances.`value` <= $P{queryMaxValue}";
                }
            case "presences":
                sql = "SELECT * FROM presences WHERE presences.`cpfCollaborator` = $P{queryCpfCollaborator}";
                if(queryRecord != null){
                    if(!queryRecord.equals("----------")){
                        sql = sql + " AND presences.`record` = $P{queryRecord}";
                    }
                }
                if(queryStatus != null){
                    if(!queryStatus.equals("----------")){
                        sql = sql + " AND presences.`status` = $P{queryStatus}";
                    }
                }
                if(queryContract != null){
                    if(!queryContract.equals("----------")){
                        sql = sql + " AND presences.`nameContract` = $P{queryNameContract}";
                    }
                }
                if(queryStartDateTimePeriod != null){
                    sql = sql + " AND presences.`presenceDateTime` >= $P{queryStartDateTime}";
                }
                if(queryEndDateTimePeriod != null){
                    sql = sql + " AND presences.`presenceDateTime` <= $P{queryEndDateTime}";
                }
        }

        return sql;
    }

    public static double formatValue(String value) throws NumberFormatException, ParseException {
        // Configurar símbolos de formatação para aceitar vírgula como separador decimal
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator(',');

        // Criar DecimalFormat com os símbolos configurados
        DecimalFormat decimalFormat = new DecimalFormat("#.00", symbols);
        decimalFormat.setParseBigDecimal(true);

        // Substituir vírgula por ponto para garantir a consistência na formatação
        String normalizedValue = value.replace(',', '.');

        // Converte a string para um BigDecimal e em seguida para double
        try {
            return decimalFormat.parse(normalizedValue).doubleValue();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
