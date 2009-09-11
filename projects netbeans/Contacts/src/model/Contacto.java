/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author INTAPSA
 */
public class Contacto {
    private String nombreContacto = new String();
    private String direccion = new String();
    private String telefono = new String();
    private String telefono_opcional = new String();
    private String ext = new String();
    private String ext_opcional = new String();
    private String celular = new String();
    private String celular_opcional = new String();
    private String indicativo = new String();
    private String indicativo_aux = new String();
    private String mail = new String();
    private String obsevaciones = new String();
    private String cargo = new String();
    private String nombreEmpresa = new String();
    private String opcion = new String();
    //private String detalle;//nombre del tipo de contacto
    private int PK = -1;
    private int id_empresa = -1;
    private String barrio = new String();//este campo solo aplica para empleados
    private boolean isEmpleado ;
    private String fechaNacimiento = new String();
    private String fechaIngreso = new String();
    private String telefonoCasa = new String();
    private String telefonoCasa_aux = new String();
    private String indicativo_casa = new String();
    private String indicativo_casa_aux = new String();
    private String cedula = new String();

    public String getIndicativo_casa() {
        return indicativo_casa;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }



    public void setIndicativo_casa(String indicativo_casa) {
        this.indicativo_casa = indicativo_casa;
    }

    public String getIndicativo_casa_aux() {
        return indicativo_casa_aux;
    }

    public void setIndicativo_casa_aux(String indicativo_casa_aux) {
        this.indicativo_casa_aux = indicativo_casa_aux;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoCasa_aux() {
        return telefonoCasa_aux;
    }

    public void setTelefonoCasa_aux(String telefonoCasa_aux) {
        this.telefonoCasa_aux = telefonoCasa_aux;
    }




    
    public boolean isIsEmpleado() {
        return isEmpleado;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    

    public void setIsEmpleado(boolean isEmpleado) {
        this.isEmpleado = isEmpleado;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    

    public int getPK() {
        return PK;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
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

    
    public void setPK(int PK) {
        this.PK = PK;
    }

    public String getCelular_opcional() {
        return celular_opcional;
    }

    public void setCelular_opcional(String celular_opcional) {
        this.celular_opcional = celular_opcional;
    }

    public String getExt_opcional() {
        return ext_opcional;
    }

    public void setExt_opcional(String ext_opcional) {
        this.ext_opcional = ext_opcional;
    }

    public String getTelefono_opcional() {
        return telefono_opcional;
    }

    public void setTelefono_opcional(String telefono_opcional) {
        this.telefono_opcional = telefono_opcional;
    }
    

    public Contacto() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

   

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getObservaciones() {
        return obsevaciones;
    }

    public void setObservaciones(String observaciones) {
        this.obsevaciones = observaciones;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString(){
        return nombreContacto;
    }

}
