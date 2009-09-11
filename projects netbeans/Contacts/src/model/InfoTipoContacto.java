/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author INTAPSA
 */
public class InfoTipoContacto {
    private String detalle = new String();
    private int PK = -1;
    private boolean isProveedor = false;

    public boolean isIsProveedor() {
        return isProveedor;
    }

    public void setIsProveedor(boolean isProveedor) {
        this.isProveedor = isProveedor;
    }

    

    public int getPK() {
        return PK;
    }

    public void setPK(int PK) {
        this.PK = PK;
    }

    public InfoTipoContacto() {
        isProveedor = false;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return detalle;
    }


    

}
