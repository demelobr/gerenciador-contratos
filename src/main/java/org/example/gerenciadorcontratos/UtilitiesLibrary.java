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

    public static String getQueryOfSearch(boolean withCollaborator, boolean financeReportComplete, String queryRecord, String queryStatus, String queryContract, LocalDate queryStartDateTimePeriod, LocalDate queryEndDateTimePeriod, String queryMinValue, String queryMaxValue, String typeOfReport){
        String sql = "";
        int count = 0;

        if(typeOfReport.equals("complete")){

        }else if(typeOfReport.equals("finances")){
            if(withCollaborator) sql = "SELECT * FROM finances WHERE finances.`collaboratorCpf` = $P{queryCpfCollaborator}";
            else{
                if(financeReportComplete){
                    sql = "SELECT * FROM finances WHERE";
                }else{
                    sql = "SELECT * FROM finances WHERE finances.`type` = $P{queryFinanceType}";
                }
            }

            if(queryStartDateTimePeriod != null){
                if(withCollaborator || !financeReportComplete) sql = sql + " AND STR_TO_DATE(finances.`date`, '%d/%m/%Y') >= STR_TO_DATE($P{queryStartDateTime}, '%d/%m/%Y')";
                else sql = sql + " STR_TO_DATE(finances.`date`, '%d/%m/%Y') >= STR_TO_DATE($P{queryStartDateTime}, '%d/%m/%Y')";
                count++;
            }
            if(queryEndDateTimePeriod != null){
                if(withCollaborator || !financeReportComplete || count > 0) sql = sql + " AND STR_TO_DATE(finances.`date`, '%d/%m/%Y') <= STR_TO_DATE($P{queryEndDateTime}, '%d/%m/%Y')";
                else sql = sql + " STR_TO_DATE(finances.`date`, '%d/%m/%Y')  <= STR_TO_DATE($P{queryEndDateTime}, '%d/%m/%Y')";
                count++;
            }
            if(queryContract != null){
                if(!queryContract.equals("----------")){
                    if(withCollaborator || !financeReportComplete || count > 0) sql = sql + " AND finances.`contractName` = $P{queryNameContract}";
                    else sql = sql + " finances.`contractName` = $P{queryNameContract}";
                    count++;
                }
            }
            if(!queryMinValue.isEmpty()){
                if(withCollaborator || !financeReportComplete || count > 0) sql = sql + " AND finances.`value` >= $P{queryMinValue}";
                else sql = sql + " finances.`value` >= $P{queryMinValue}";
                count++;
            }
            if(!queryMaxValue.isEmpty()){
                if(withCollaborator || !financeReportComplete ||  count > 0) sql = sql + " AND finances.`value` <= $P{queryMaxValue}";
                else sql = sql + " finances.`value` <= $P{queryMaxValue}";
                count++;
            }

            if(count == 0 && financeReportComplete) sql = "SELECT * FROM finances";

        }else if(typeOfReport.equals("presences")){
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
                sql = sql + " AND STR_TO_DATE(presences.`presenceDateTime`, '%d/%m/%Y - %H:%i')  >= STR_TO_DATE($P{queryStartDateTime}, '%d/%m/%Y - %H:%i')";
            }
            if(queryEndDateTimePeriod != null){
                sql = sql + " AND STR_TO_DATE(presences.`presenceDateTime`, '%d/%m/%Y - %H:%i') <= STR_TO_DATE($P{queryEndDateTime}, '%d/%m/%Y - %H:%i')";
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
