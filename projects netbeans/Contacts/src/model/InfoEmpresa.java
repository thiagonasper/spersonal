/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author INTAPSA
 */
public class InfoEmpresa {
    private String nit = new String();
    private String direccion = new String();
    private String telefono = new String();
    private String telefono_aux = new String();
    private String nombre = new String();
    private String indicativo = new String();
    private String indicativo_aux = new String();
    private String ciudad = new String();
    private String tipoContacto = new String();
    private String fax = new String();
    private int id_empresa = -1;
    private int id_tipo = -1;
    private boolean isProvider = false;
    private String nombre_proveedor = new String();
    private int id_proveedor = -1;
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }




    
    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    

    public boolean isIsProvider() {
        return isProvider;
    }

    public void setIsProvider(boolean isProvider) {
        this.isProvider = isProvider;
    }


    
    public InfoEmpresa() {
        isProvider = false;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTipoContacto() {
        return tipoContacto;
    }

    public void setTipoContacto(String tipoContacto) {
        this.tipoContacto = tipoContacto;
    }


    public int getId_empresa() {
        return id_empresa;
    }

    public String getIndicativo() {
        return indicativo;
    }

    public void setIndicativo(String indicativo) {
        this.indicativo = indicativo;
    }

    public String getIndicativo_aux() {
        return indicativo_aux;
    }

    public void setIndicativo_aux(String indicativo_aux) {
        this.indicativo_aux = indicativo_aux;
    }

    public String getTelefono_aux() {
        return telefono_aux;
    }

    public void setTelefono_aux(String telefono_aux) {
        this.telefono_aux = telefono_aux;
    }

    
    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString(){
        return nombre;
    }
    

}
