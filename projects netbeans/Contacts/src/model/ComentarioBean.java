/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author INTAPSA
 */
public class ComentarioBean {

    private String texto;
    private int pk;

    public ComentarioBean() {
        texto = new String();
        pk =-1;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    
    

}
