package com.selecao.leilao.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static String formataData(LocalDateTime localDateTime){
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return localDateTime.format(dateTime);
    }
}
