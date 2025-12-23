package com.gestorgastos.sistema_gestor_gastos.Utils;

import java.math.BigDecimal;

public class ValidationUtils {
    private ValidationUtils() {}

    public static boolean isNumeric(String text) {
        try {
            new BigDecimal(text.replace(",", "."));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
