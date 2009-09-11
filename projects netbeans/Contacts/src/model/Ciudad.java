/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author INTAPSA
 */
public class Ciudad {

     private String nombre;
    private int indicativo;

    public Ciudad() {
        nombre = new String();
        indicativo = -1;
    }

    public int getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(int indicativo) {
        this.indicativo = indicativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString(){
        return nombre;
    }

 

}
