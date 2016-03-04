package com.apps.alvarobanofos.presentview.Models;

/**
 * Created by alvarobanofos on 26/2/16.
 */
public class Municipio {

    private int CPRO, CMUN;
    private String NOMBRE;

    public Municipio(int CPRO, int CMUN, String NOMBRE) {
        this.CPRO = CPRO;
        this.CMUN = CMUN;
        this.NOMBRE = NOMBRE;
    }

    public int getCPRO() {
        return CPRO;
    }

    public void setCPRO(int CPRO) {
        this.CPRO = CPRO;
    }

    public int getCMUN() {
        return CMUN;
    }

    public void setCMUN(int CMUN) {
        this.CMUN = CMUN;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Municipio municipio = (Municipio) o;

        if (CPRO != municipio.CPRO) return false;
        if (CMUN != municipio.CMUN) return false;
        return !(NOMBRE != null ? !NOMBRE.equals(municipio.NOMBRE) : municipio.NOMBRE != null);

    }

    @Override
    public int hashCode() {
        int result = CPRO;
        result = 31 * result + CMUN;
        result = 31 * result + (NOMBRE != null ? NOMBRE.hashCode() : 0);
        return result;
    }
}
