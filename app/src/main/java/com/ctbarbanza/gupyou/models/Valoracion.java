package com.ctbarbanza.gupyou.models;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Valoracion extends Dato{

    public enum TIPO_VALORACION{
        GENERAL,
        DINERO,
        POSTUREO,
        HIGIENE;

        private static final List<TIPO_VALORACION> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();
        public static TIPO_VALORACION getRandom(){
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }


    private int valor = 0;
    private TIPO_VALORACION key = TIPO_VALORACION.GENERAL;

    public TIPO_VALORACION getKey() {
        return key;
    }

    public void setKey(TIPO_VALORACION key) {
        this.key = key;
    }

    /**
     *
     * @return <strong>1 y 2 son valoraciones negativas; y 3,4 son valoraciones positivas</strong>
     */
    public int getValor() {
        return valor;
    }

    /**
     * <strong>Si el valor es menor a 1 se pondrá 1; si es mayor a 4, será 4</strong>
     *
     * @param valor Tiene que ser un valor entre 1-4.
     */
    public void setValor(int valor) {
        if (valor <= 0){
            valor = 1;
        }
        if (valor > 4){
            valor = 4;
        }
        this.valor = valor;
    }
}
