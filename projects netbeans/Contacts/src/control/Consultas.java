/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import com.javaexchange.dbConnectionBroker.DbConnectionBroker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.Ciudad;
import model.ComentarioBean;
import model.Contacto;
import model.InfoEmpresa;
import model.InfoTipoContacto;



/**
 *
 * @author INTAPSA
 */
public class Consultas {

    public static final int BY_NAME = 1;
    public static final int BY_ENTERPRISE = 2;
    public static final int BY_CONTACT_TYPE = 3;
    private DbConnectionBroker pool;

    public Consultas(DbConnectionBroker pool) {
        this.pool = pool;
    }

   

   
/*Obtiene la informacion para contactos*/
    public ArrayList<Contacto> getInfoToContacts(){
        String query = "select c.*, e.*, t.* from contactos_intap.contacto " +
                    "as c inner join contactos_intap.empresa as e on c.id_empresa = e.id_empresa " +
                    "inner join contactos_intap.tipo_contacto as t on e.id_tipo = t.id_tipo " +
                    "order by c.nombre_contacto;";
         Contacto info;
            ArrayList<Contacto> resultado = new ArrayList<Contacto>();
            Connection con = pool.getConnection();
        try {
            

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                info = new Contacto();
                info.setCargo(rs.getString("cargo"));
                info.setCelular(rs.getString("celular"));
                info.setDireccion(rs.getString("direccion"));
                info.setExt(rs.getString("ext"));
                info.setMail(rs.getString("mail"));
                info.setNombreContacto(rs.getString("nombre_contacto"));
                info.setObservaciones(rs.getString("observaciones"));
                info.setTelefono(rs.getString("telefono"));
                info.setPK(rs.getInt("id_contacto"));
                info.setId_empresa(rs.getInt("id_empresa"));
                info.setCelular_opcional(rs.getString("celular_aux"));
                info.setExt_opcional(rs.getString("ext_aux"));
                info.setIndicativo_aux(rs.getString("indicativo_aux"));
                info.setIndicativo(rs.getString("indicativo"));
                info.setNombreEmpresa(rs.getString("nombre"));
                info.setTelefono_opcional(rs.getString("telefono_aux"));
                info.setOpcion(rs.getString("opcion"));
                resultado.add(info);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
    }

    /*Obtiene la informacion para empresas*/
   public ArrayList<InfoEmpresa> getInfoToEnterprises(int aPk){
       InfoEmpresa empresa;
            ArrayList<InfoEmpresa> resultado = new ArrayList<InfoEmpresa>();
            Connection con = pool.getConnection();
        try {
            String query = "SELECT e.*, tc.detalle FROM contactos_intap.empresa e inner join contactos_intap.tipo_contacto tc on e.id_tipo = tc.id_tipo  ";
             if(aPk>0){
                query += " where e.id_proveedor = -1 and id_empresa = "+ aPk+" order by e.nombre;";
            }else{
                query+= " where e.id_proveedor = -1 order by e.nombre;";
            }
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                empresa = new InfoEmpresa();
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setId_empresa(rs.getInt("id_empresa"));
                empresa.setId_tipo(rs.getInt("id_tipo"));
                empresa.setNit(rs.getString("nit"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCiudad(rs.getString("ciudad"));
                empresa.setIndicativo(rs.getString("indicativo"));
                empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                empresa.setTipoContacto(rs.getString("detalle"));
                empresa.setTelefono_aux(rs.getString("telefono_aux"));
                empresa.setFax(rs.getString("fax"));
                empresa.setMail(rs.getString("mail"));
                resultado.add(empresa);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

   }
/*Obtiene la informacion para tipos de contacto*/
   public ArrayList<InfoTipoContacto> getInfoToTipoContacto(int aPk){
       InfoTipoContacto tipo;
            ArrayList<InfoTipoContacto> resultado = new ArrayList<InfoTipoContacto>();
            Connection con = pool.getConnection();
        try {

            String query = "SELECT * FROM contactos_intap.tipo_contacto t";
            if(aPk>0){
                query+= " where id_tipo = "+aPk+ " ;";

            }else{
                query+=" ;";
            }
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                tipo = new InfoTipoContacto();
                tipo.setDetalle(rs.getString("detalle"));
                tipo.setPK(rs.getInt("id_tipo"));
                resultado.add(tipo);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

   }
/*Obtiene las empresas que pertenecen son un tipo de contacto*/
   public ArrayList<InfoEmpresa> getInfoEnterprisesforType(int pk){
       InfoEmpresa empresa;
            ArrayList<InfoEmpresa> resultado = new ArrayList<InfoEmpresa>();
            Connection con = pool.getConnection();
        try {

            String sql = " select e.* , t.detalle from contactos_intap.empresa e inner join contactos_intap.tipo_contacto t " +
                    "on e.id_tipo = t.id_tipo " +
                    " where e.id_tipo =  " + pk+" order by e.nombre;";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                empresa = new InfoEmpresa();
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setId_empresa(rs.getInt("id_empresa"));
                empresa.setId_tipo(rs.getInt("id_tipo"));
                empresa.setNit(rs.getString("nit"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCiudad(rs.getString("ciudad"));
                empresa.setIndicativo(rs.getString("indicativo"));
                empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                empresa.setTipoContacto(rs.getString("detalle"));
                empresa.setTelefono_aux(rs.getString("telefono_aux"));
                empresa.setFax(rs.getString("fax"));
                empresa.setMail(rs.getString("mail"));
                resultado.add(empresa);
            }
            pool.freeConnection(con);
            return resultado;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

   }

   /*Obtiene los contactos que pertencen a una empresa*/
   public ArrayList<Contacto> getInfoContactsForEnterprise(int pk){
       Contacto info;
            ArrayList<Contacto> resultado = new ArrayList<Contacto>();
            String query = "select c.* , e.nombre from contactos_intap.contacto c " +
                    " inner join contactos_intap.empresa as e on c.id_empresa=e.id_empresa" +
                    " where e.id_empresa =" + pk +" ;";
            Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                info = new Contacto();
                info.setCargo(rs.getString("cargo"));
                info.setCelular(rs.getString("celular"));
                info.setDireccion(rs.getString("direccion"));
                info.setExt(rs.getString("ext"));
                info.setMail(rs.getString("mail"));
                info.setNombreContacto(rs.getString("nombre_contacto"));
                info.setObservaciones(rs.getString("observaciones"));
                info.setTelefono(rs.getString("telefono"));
                info.setPK(rs.getInt("id_contacto"));
                info.setId_empresa(rs.getInt("id_empresa"));
                info.setCelular_opcional(rs.getString("celular_aux"));
                info.setExt_opcional(rs.getString("ext_aux"));
                info.setIndicativo_aux(rs.getString("indicativo_aux"));
                info.setIndicativo(rs.getString("indicativo"));
                info.setNombreEmpresa(rs.getString("nombre"));
                info.setTelefono_opcional(rs.getString("telefono_aux"));
                info.setOpcion(rs.getString("opcion"));
                resultado.add(info);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

       
   }
/*Obtiene el tipo de contacto de una empresa*/
   public InfoTipoContacto getTipoContactoByEmpresa(int pk){
        Connection con = pool.getConnection();
         InfoTipoContacto tipo = new InfoTipoContacto();
        try {

            String query = "select * from contactos_intap.tipo_contacto where id_tipo = " + pk;

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                tipo = new InfoTipoContacto();
                tipo.setDetalle(rs.getString("detalle"));
                tipo.setPK(rs.getInt("id_tipo"));
            }
            pool.freeConnection(con);
            return tipo;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
   }
/*Obtiene la empresa a las que pertence un contacto*/
   public InfoEmpresa getEmpresaByContact(int pkEmpresa){
       InfoEmpresa empresa = new InfoEmpresa();
            Connection con = pool.getConnection();
        try {

            String query = "select * from contactos_intap.empresa e " +
                    " inner join contactos_intap.tipo_contacto t on e.id_tipo = e.id_tipo" +
                    " where e.id_empresa = " + pkEmpresa;
            PreparedStatement stm = con.prepareStatement(query);

            int pkProveedor;
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                empresa = new InfoEmpresa();
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setId_empresa(rs.getInt("id_empresa"));
                empresa.setId_tipo(rs.getInt("id_tipo"));
                empresa.setNit(rs.getString("nit"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCiudad(rs.getString("ciudad"));
                empresa.setIndicativo(rs.getString("indicativo"));
                empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                empresa.setTipoContacto(rs.getString("detalle"));
                empresa.setTelefono_aux(rs.getString("telefono_aux"));
                empresa.setFax(rs.getString("fax"));
                empresa.setMail(rs.getString("mail"));
                pkProveedor= rs.getInt("id_proveedor");
                if(pkProveedor!=-1){
                    empresa.setIsProvider(true);
                    empresa.setId_proveedor(pkProveedor);
                }
            }
            pool.freeConnection(con);
            return empresa;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

   }
   /*Otiene la empresa a la que */
  /* public InfoEmpresa getEmpresaByTipoContacto(int pkEmpresa){
       InfoEmpresa empresa = new InfoEmpresa();
            Connection con = pool.getConnection();
        try {

            String query = "select * from contactos_intap.empresa e " +
                    " inner join contactos_intap.tipo_contacto t on e.id_tipo = t.id_tipo " +
                    " where e.id_tipo = " + pkEmpresa;
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                empresa = new InfoEmpresa();
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setId_empresa(rs.getInt("id_empresa"));
                empresa.setId_tipo(rs.getInt("id_tipo"));
                empresa.setNit(rs.getString("nit"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCiudad(rs.getString("ciudad"));
                empresa.setIndicativo(rs.getString("indicativo"));
                empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                empresa.setTipoContacto(rs.getString("detalle"));
                empresa.setTelefono_aux(rs.getString("telefono_aux"));
            }
            pool.freeConnection(con);
            return empresa;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
   }*/

   /*public Contacto getContactoByEmpresa(int pkEmpresa){
       String query = "select * from contactos_intap.contacto where = id_empresa =" + pkEmpresa;
            Connection con = pool.getConnection();
            Contacto info = new Contacto();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                info = new Contacto();
                info.setCargo(rs.getString("cargo"));
                info.setCelular(rs.getString("celular"));
                //info.setDetalle(rs.getString("detalle"));
                info.setDireccion(rs.getString("direccion"));
                info.setExt(rs.getString("ext"));
                info.setMail(rs.getString("mail"));
                info.setNombreContacto(rs.getString("nombre_contacto"));
                //info.setNombreEmpresa(rs.getString("nombre"));
                info.setObservaciones(rs.getString("observaciones"));
                info.setTelefono(rs.getString("telefono"));
                info.setPK(rs.getInt("id_contacto"));
                info.setId_empresa(rs.getInt("id_empresa"));
            }
            pool.freeConnection(con);
            return info;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }

   }*/

   /*public  int getPKparaTipo(String tipo){
        try {
            String query = "select id_tipo from contactos_intap.tipo_contacto where detalle = '" + tipo + "';";
            Connection con = pool.getConnection();
            java.sql.PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            rs.first();
            int PK = rs.getInt("id_tipo");
            pool.freeConnection(con);
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }*/

    public  int getPKparaEmpresa(String empresa){
        try {
            String query = "select id_empresa from contactos_intap.empresa where nombre = '" + empresa + "';";
            Connection con = pool.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            rs.first();
            int PK = rs.getInt("id_empresa");
            pool.freeConnection(con);
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    public int getPKparaContacto(String contacto){
        try {
            String query = "select id_contacto from contactos_intap.contacto where nombre_contacto = '" + contacto + "';";
            Connection con = pool.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
             rs.first();
            int PK = rs.getInt("id_contacto");
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }


    }
/*parametros
 * Resumen: este metodo es complejo, debido a que fue readaptado a partir de una adicion al modelo
 * de busquedas, este metodo deberia reescribirse posteriormente para mejorar su diseño y comprension.
 *
 * opcion es el tipo de busqueda
 * match es ua palabra a matchear
 * b el arbol objeto
 * aPk, es usada para pasar id´s de empresas o tipos
 * bt conserva la logica del tipo de busqueda actual
 * isProveedor especifica si la busqueda actual es de un proveedor
 * isEmpleado especifica si la busqueda actual es de un empleado
 *
 */
    public void busqueda(int opcion, String match, JTree b, int aPk, BuildingTree bt, boolean isProveedor, boolean isEmpleado, int pkProveedor){
        DefaultMutableTreeNode root ;
        boolean fromType = false;
        boolean fromEnterprise = false;
        DefaultTreeModel model ;
        Connection con = pool.getConnection();
        String query = "";
        PreparedStatement stm;
        switch(opcion){
            case Consultas.BY_CONTACT_TYPE:
                root = new DefaultMutableTreeNode("INTAP");
                model = new DefaultTreeModel(root);
               query = "select * from contactos_intap.tipo_contacto where detalle like '%"+match+"%' ";
               if(aPk>0){
                   opcion = Consultas.BY_ENTERPRISE;
                   bt.setTipoBusquedaActual(opcion);
                   fromType = true;
               }else{
                   query+=" order by detalle;";
                   try {
                    stm = con.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                    InfoTipoContacto tipo;
                    while(rs.next()){
                        tipo = new InfoTipoContacto();
                        tipo.setDetalle(rs.getString("detalle"));
                        tipo.setPK(rs.getInt("id_tipo"));
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                        node.setUserObject(tipo);
                        root.add(node);
                    }
                    pool.freeConnection(con);
                    b.setModel(model);
                    break;

                } catch (SQLException ex) {
                    Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
                    pool.freeConnection(con);
                    break;
                }
               }
                

                
            case Consultas.BY_ENTERPRISE:
                 root = new DefaultMutableTreeNode("INTAP");
                model = new DefaultTreeModel(root);
                boolean continuar = false;
                if(!isProveedor){
                        query = "select * from contactos_intap.empresa e " +
                                " inner join contactos_intap.tipo_contacto t on e.id_tipo = t.id_tipo " +
                                "where e.id_proveedor=-1 and e.nombre like '%"+match+"%' ";
                        if(aPk>0){
                            if(!fromType){
                                opcion = Consultas.BY_NAME;
                                bt.setTipoBusquedaActual(opcion);
                            }else{
                                continuar = true;
                            }
                       }else{
                            continuar = true;
                       }
                        if(continuar){

                            if(aPk>0){
                                query+=" and e.id_tipo = "+aPk+" order by e.nombre;";
                            }else{

                                query+=" order by e.nombre;";
                            }
                                   try {
                                stm = con.prepareStatement(query);
                                ResultSet rs = stm.executeQuery();
                                InfoEmpresa empresa;
                                while(rs.next()){
                                    empresa = new InfoEmpresa();
                                    empresa.setDireccion(rs.getString("direccion"));
                                    empresa.setId_empresa(rs.getInt("id_empresa"));
                                    empresa.setId_tipo(rs.getInt("id_tipo"));
                                    empresa.setNit(rs.getString("nit"));
                                    empresa.setNombre(rs.getString("nombre"));
                                    empresa.setTelefono(rs.getString("telefono"));
                                    empresa.setCiudad(rs.getString("ciudad"));
                                    empresa.setIndicativo(rs.getString("indicativo"));
                                    empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                                    empresa.setTipoContacto(rs.getString("detalle"));
                                    empresa.setTelefono_aux(rs.getString("telefono_aux"));
                                    empresa.setFax(rs.getString("fax"));
                                    empresa.setMail(rs.getString("mail"));
                                    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                                    node.setUserObject(empresa);
                                    root.add(node);
                                }
                                pool.freeConnection(con);
                                b.setModel(model);
                                break;

                            } catch (SQLException ex) {
                                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
                                pool.freeConnection(con);
                                break;
                            }
                       }
             
                }else{//si es proveedor
                    
                    query = "select t.detalle , e.*, p.detalle as detalle_proveedor from contactos_intap.empresa e " +
                                " inner join contactos_intap.tipo_contacto t on e.id_tipo = t.id_tipo " +
                                " inner join contactos_intap.proveedores p on p.id_proveedor = e.id_proveedor  "+
                                " where e.id_proveedor<>-1 and e.nombre like '%"+match+"%' and e.id_proveedor = "+ pkProveedor+" ";
                        if(aPk>0){
                            if(!fromType){
                                opcion = Consultas.BY_NAME;
                                bt.setTipoBusquedaActual(opcion);
                            }else{
                                continuar = true;
                            }
                       }else{
                            continuar = true;
                       }
                        if(continuar){

                            if(aPk>0){
                                query+=" and e.id_tipo = "+aPk+" order by e.nombre;";
                            }else{

                                query+=" order by e.nombre;";
                            }
                                   try {
                                stm = con.prepareStatement(query);
                                ResultSet rs = stm.executeQuery();
                                InfoEmpresa empresa;
                                while(rs.next()){
                                    empresa = new InfoEmpresa();
                                    empresa.setDireccion(rs.getString("direccion"));
                                    empresa.setId_empresa(rs.getInt("id_empresa"));
                                    empresa.setId_tipo(rs.getInt("id_tipo"));
                                    empresa.setNit(rs.getString("nit"));
                                    empresa.setNombre(rs.getString("nombre"));
                                    empresa.setTelefono(rs.getString("telefono"));
                                    empresa.setCiudad(rs.getString("ciudad"));
                                    empresa.setIndicativo(rs.getString("indicativo"));
                                    empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                                    empresa.setTipoContacto(rs.getString("detalle"));
                                    empresa.setTelefono_aux(rs.getString("telefono_aux"));
                                    empresa.setFax(rs.getString("fax"));
                                    empresa.setIsProvider(true);
                                    empresa.setId_proveedor(rs.getInt("id_proveedor"));
                                    empresa.setNombre_proveedor(rs.getString("detalle_proveedor"));
                                    empresa.setMail(rs.getString("mail"));
                                    DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                                    node.setUserObject(empresa);
                                    root.add(node);
                                }
                                pool.freeConnection(con);
                                b.setModel(model);
                                break;

                            } catch (SQLException ex) {
                                Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
                                pool.freeConnection(con);
                                break;
                            }
                       }//fin de si es proveedor

                }

               
            case Consultas.BY_NAME:
                 root = new DefaultMutableTreeNode("INTAP (Contactos)");
                model = new DefaultTreeModel(root);
                if(!isEmpleado){
                        query = "select * from contactos_intap.contacto c" +
                                " inner join contactos_intap.empresa e on c.id_empresa = e.id_empresa " +
                                " where c.nombre_contacto like '%"+match+"%' ";
                        if(aPk>0){
                            query+=" and c.id_empresa = "+aPk+" order by c.nombre_contacto;";
                        }else{
                            query+= " order by c.nombre_contacto;";
                        }
                         try {
                    stm = con.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                   Contacto info;
                    while(rs.next()){
                         info = new Contacto();
                        info.setCargo(rs.getString("cargo"));
                        info.setCelular(rs.getString("celular"));
                        info.setDireccion(rs.getString("direccion"));
                        info.setExt(rs.getString("ext"));
                        info.setMail(rs.getString("mail"));
                        info.setNombreContacto(rs.getString("nombre_contacto"));
                        info.setObservaciones(rs.getString("observaciones"));
                        info.setTelefono(rs.getString("telefono"));
                        info.setPK(rs.getInt("id_contacto"));
                        info.setId_empresa(rs.getInt("id_empresa"));
                        info.setCelular_opcional(rs.getString("celular_aux"));
                        info.setExt_opcional(rs.getString("ext_aux"));
                        info.setIndicativo_aux(rs.getString("indicativo_aux"));
                        info.setIndicativo(rs.getString("indicativo"));
                        info.setNombreEmpresa(rs.getString("nombre"));
                        info.setTelefono_opcional(rs.getString("telefono_aux"));
                        info.setOpcion(rs.getString("opcion"));

                        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                        node.setUserObject(info);
                        root.add(node);
                    }
                    pool.freeConnection(con);
                    b.setModel(model);
                    break;

                } catch (SQLException ex) {
                    Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
                    pool.freeConnection(con);
                    break;
                }

                }else{//si es empleado
                    query = "SELECT e.`nombre_empleado` FROM contactos_intap.empleados e where nombre_empleado like '%"+match+"%';";

                    try {
                    stm = con.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                   Contacto info;
                    while(rs.next()){
                         info = new Contacto();
                        info.setCargo(rs.getString("cargo"));
                        info.setCelular(rs.getString("celular"));
                        info.setDireccion(rs.getString("direccion_residencia"));
                        info.setExt(rs.getString("extension"));
                        info.setMail(rs.getString("mail"));
                        info.setNombreContacto(rs.getString("nombre_empleado"));
                        info.setCedula(rs.getString("cedula"));
                        info.setTelefono(rs.getString("telefono_oficina"));
                        info.setPK(rs.getInt("id_empleado"));
                        info.setTelefonoCasa(rs.getString("telefono_casa"));
                        info.setCelular_opcional(rs.getString("celular_aux"));
                        info.setIndicativo_casa(rs.getString("indicativo_casa"));
                        info.setIndicativo_casa_aux(rs.getString("indicativo_casa_aux"));
                        info.setIndicativo(rs.getString("indicativo_ofi"));
                        info.setNombreEmpresa(rs.getString("ubicacion"));
                        info.setTelefonoCasa_aux(rs.getString("telefono_casa_aux"));
                        info.setOpcion(rs.getString("opcion"));
                        info.setBarrio(rs.getString("barrio"));
                        info.setIsEmpleado(true);

                        DefaultMutableTreeNode node = new DefaultMutableTreeNode();
                        node.setUserObject(info);
                        root.add(node);
                    }
                    pool.freeConnection(con);
                    b.setModel(model);
                    break;

                } catch (SQLException ex) {
                    Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
                    pool.freeConnection(con);
                    break;
                }
                }
                 


                

        }
    }
//de aqui en adelante falta testear queries
    public boolean editarUsuario(int pk, String nombre, String direccion, String telefono, String ext, String celular, 
            String cargo,String observaciones,String mail, String indicativo, String indicativo_aux, String telefono_aux
            ,String extension_aux, String celular_aux, String opcion){
        Connection con = pool.getConnection();
         String query = "update contactos_intap.contacto set nombre_contacto = '"
                 + nombre + "', direccion = '" + direccion + "', ext = '" + ext + "',celular = '"
                 + celular + "', cargo = '" + cargo + "'," +
                 " observaciones = '" + observaciones + "' , " +
                 "telefono = '"+telefono +"', mail = '"+mail+"', indicativo ="+ indicativo+", indicativo_aux ="+(indicativo_aux.isEmpty()?"0":indicativo_aux)+","+
                 "telefono_aux = " +(telefono_aux.isEmpty()?"0":telefono_aux)+", ext_aux = "+(extension_aux.isEmpty()?"0":extension_aux)+", celular_aux ="+(celular_aux.isEmpty()?"0":celular_aux)+
                 " ,opcion = "+(opcion.isEmpty()?"0":opcion)+" where id_contacto = " + pk + " ;";
        try {
           

            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }


    }
    public boolean editarTipoContacto(int pk, String detalle){
        Connection con = pool.getConnection();
        String query= "update contactos_intap.tipo_contacto set detalle = '"+detalle+"' where id_tipo = "+pk+";";
         try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }

    }

    public boolean deleteTipoContacto(int pk){
        Connection con = pool.getConnection();
        String query = "delete from contactos_intap.tipo_contacto where id_tipo = "+pk+";";

        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public boolean deleteEmpresa(int pk){
        String query = "delete from contactos_intap.empresa where id_empresa = "+pk+";";
        Connection con = pool.getConnection();
         try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public boolean editarEmpresa(int pk, String nit, String direccion, String telefono, String nombre
            ,String telefono_aux, String indicativo, String indicativo_aux, String ciudad, String fax, String mail){
         Connection con = pool.getConnection();
        String query = "update contactos_intap.empresa set nit = '"+nit+"', direccion = '"+direccion+"', telefono = '"+telefono+"', nombre = '"+nombre+"', " +
                " telefono_aux = " +(telefono_aux.trim().isEmpty()?"0":telefono_aux)+", indicativo = "+indicativo+", indicativo_aux = "+(indicativo_aux.trim().isEmpty()?"0":indicativo_aux)+"," +
                " ciudad = '"+ciudad+"', fax =  "+(fax.isEmpty()?"0":fax)+ ",mail ='"+mail+"' "+
                " where id_empresa = "+pk+";";
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }


    }

    public boolean deleteUser(int pk){
        String query = "delete from contactos_intap.contacto where id_contacto =" + pk + ";";
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }


    }

    public ArrayList<ComentarioBean> getComentarios(int pkContacto, int pkEmpresa,int pkTipo){
        ArrayList<ComentarioBean> comentarios = new ArrayList<ComentarioBean>();
         String where = "where 1=1";
            if (pkContacto != -1) {
                where += " and id_contacto=" + pkContacto;
            }
            if (pkEmpresa != -1) {
                where += " and id_empresa = " + pkEmpresa;
            }
            if (pkTipo != -1) {
                where += " and id_tipo_contacto = " + pkTipo;
            }
            where += ";";
            String query = "select * from contactos_intap.comentarios ";
            query += where;
            Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            ComentarioBean com ;
            while(rs.next()){
                com = new ComentarioBean();
                com.setPk(rs.getInt("id_com"));
                com.setTexto(rs.getString("comentario"));
                comentarios.add(com);
            }

            pool.freeConnection(con);
            return comentarios;


        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return new ArrayList<ComentarioBean>();
        }
    }

    public boolean insertComentario(int pkContacto, int pkEmpresa,int pkTipo, String comentar){
        boolean exito=true;       
        Connection c1 = pool.getConnection();

          //create comentario
      
        String create = "insert into contactos_intap.comentarios(id_contacto,id_empresa,id_tipo_contacto,comentario) values(" +
                 pkContacto+",  "+pkEmpresa +" , " +pkTipo+
                ", '"+comentar+"');";
       ResultSet rs ;
        Statement stm;
        try {
            stm = c1.createStatement();
             stm.executeUpdate(create);
            pool.freeConnection(c1);
        } catch (SQLException ex) {
            exito = false;
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        return exito;
    }


    public ArrayList<InfoTipoContacto> getTipoContactos(){
        ArrayList<InfoTipoContacto> nombres = new ArrayList<InfoTipoContacto>();
        String query = "select * from contactos_intap.tipo_contacto order by detalle;";
        Connection con = pool.getConnection();
        InfoTipoContacto tipo;
        
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
          
           while(rs.next()){
             tipo = new InfoTipoContacto();
                        tipo.setDetalle(rs.getString("detalle"));
                        tipo.setPK(rs.getInt("id_tipo"));
            nombres.add(tipo);
           }
            pool.freeConnection(con);
           return nombres;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public boolean insertEmpleado(String nombre,String cedula, String ubicacion, String telefono_oficina, String extension,String celular, String telefono_casa, String fecha_nacimiento,
                String fecha_ingreso, String direccion,String barrio,String opcion, String cel_aux,String indica_ofi,String indica_casa,String tel_aux, String indica_casa_aux, String mail, String cargo){
        Connection con = pool.getConnection();
        String query = "insert into contactos_intap.empleados(nombre_empleado, cedula , ubicacion, telefono_oficina" +
                ",extension, celular, telefono_casa, fecha_nacimiento, fecha_ingreso, direccion_residencia, " +
                "barrio, opcion, celular_aux, indicativo_ofi, indicativo_casa, telefono_casa_aux," +
                "indicativo_casa_aux,mail,cargo) values('"+nombre+"',"+cedula+",'"+ubicacion+"',"+telefono_oficina+"," +
                extension+","+celular+","+telefono_casa+",'"+fecha_nacimiento+"','"+fecha_ingreso+"','" +
                direccion+"','"+barrio+"',"+(opcion.isEmpty()?"0":opcion)+","+(cel_aux.isEmpty()?"0":cel_aux)+","+indica_ofi+","+indica_casa+","+(tel_aux.isEmpty()?"0":tel_aux)+","+(indica_casa_aux.isEmpty()?"0":indica_casa_aux)+",'"+mail+"','"+cargo+"')";
         try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
        //return insert(query);
    }

    public boolean insertUser(String nombre, String direccion, String telefono, String ext, String celular, String cargo,
            String observaciones,String mail,String telefono_aux,String celular_aux, String indicativo,
            String indicativo_aux,String ext_aux, int pkEmpresa, String opcion){
        Connection con = pool.getConnection();
         String query = "insert into contactos_intap.contacto(nombre_contacto,direccion,ext,celular,cargo,observaciones, id_empresa" +
                 ",telefono, mail, telefono_aux, ext_aux, celular_aux, indicativo, indicativo_aux, opcion) values ('" + nombre + "','" + direccion + "','" + (ext.isEmpty()?"0":ext) + "','" + celular + "','" + cargo + "'," +
                 "'" + (observaciones.isEmpty()?"0":observaciones) + "',"+pkEmpresa+", '"+telefono+"','"+mail+"', "+(telefono_aux.isEmpty()?"0":telefono_aux)+","+(ext_aux.isEmpty()?"0":ext_aux)+"" +
                 ","+(celular_aux.isEmpty()?"0":celular_aux)+","+indicativo+","+(indicativo_aux.isEmpty()?"0":indicativo_aux)+ ", "+ (opcion.isEmpty()?"0":opcion) +");";
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public boolean deleteComment(int pkContacto, int pkEmpresa, int pkTipo){
        String query = "delete from contactos_intap.comentarios where id_contacto =" +
                ""+ pkContacto+" and id_empresa = " + pkEmpresa +" and id_tipo_contacto = "+ pkTipo+";" ;

        Connection con = pool.getConnection();
        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }
    

    public boolean editComentario(int pkContacto, int pkEmpresa, int pkTipo, String newComment, int pkComen){
        String query="update contactos_intap.comentarios set comentario = '"+newComment+"' where " +
                "id_contacto = "+pkContacto+" and id_empresa = " + pkEmpresa +" and id_tipo_contacto = "+ pkTipo+"" +
                " and id_com = "+pkComen +" ;";
        Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public boolean insertEmpresa(String nit,String direccion, String telefono,int pkTipo, String nombre
           , String ciudad, String telefono_aux, String indicativo, String indicativo_aux, String fax,
           String id_proveedor, String mail){
        String query = "insert into contactos_intap.empresa(nit,direccion,telefono,id_tipo,nombre," +
                "ciudad, telefono_aux,indicativo, indicativo_aux,fax, id_proveedor,mail)" +
                " values ('"+ nit +"','"+ direccion +"','"+telefono+"',"+pkTipo+",'"+nombre+"'" +
                ",'"+ciudad+"',"+(telefono_aux.isEmpty()?"0":telefono_aux)+","+indicativo+","+(indicativo_aux.isEmpty()?"0":indicativo_aux)+", '"+(fax.isEmpty()?"0":fax)+"',"+id_proveedor+", '"+mail+"');";

        Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }

    }

    public boolean insertTipo(String detalle){
        String query= "insert into contactos_intap.tipo_contacto(detalle) values('"+ detalle + "');" ;

        Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }


    public ArrayList<InfoEmpresa> getEmpresas(){
        ArrayList<InfoEmpresa> empresas = new ArrayList<InfoEmpresa>();
        InfoEmpresa empresa;
            String query = "select * from contactos_intap.empresa e " +
                    "inner join contactos_intap.tipo_contacto t on e.id_tipo=t.id_tipo " +
                    " " +
                    " order by nombre;";
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                 empresa = new InfoEmpresa();
                            empresa.setDireccion(rs.getString("direccion"));
                            empresa.setId_empresa(rs.getInt("id_empresa"));
                            empresa.setId_tipo(rs.getInt("id_tipo"));
                            empresa.setNit(rs.getString("nit"));
                            empresa.setNombre(rs.getString("nombre"));
                            empresa.setTelefono(rs.getString("telefono"));
                            empresa.setCiudad(rs.getString("ciudad"));
                            empresa.setIndicativo(rs.getString("indicativo"));
                            empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                            empresa.setTipoContacto(rs.getString("detalle"));
                            empresa.setTelefono_aux(rs.getString("telefono_aux"));
                            empresa.setFax(rs.getString("fax"));
                            empresa.setMail(rs.getString("mail"));
                        empresas.add(empresa);
            }
            pool.freeConnection(con);
            return empresas;
        } catch (SQLException ex) {
            pool.freeConnection(con);
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean insertCiudad(String ciudad, int indicativo){
        String query = "insert into contactos_intap.ciudades(nombre, indicativo) " + "values('" + ciudad + "', "+indicativo+");";
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public ArrayList<Ciudad> getCiudades(){
        String query = "select * from contactos_intap.ciudades order by nombre";
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            ArrayList<Ciudad> resultado = new ArrayList<Ciudad>();
            Ciudad tmp ;
            while(rs.next()){
                tmp = new Ciudad();
                tmp.setNombre( rs.getString("nombre"));
                tmp.setIndicativo(rs.getInt("indicativo"));
                resultado.add(tmp);

            }
            pool.freeConnection(con);
            return resultado;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getCantidadTotalProveedores(){
         String query = "select count(id_proveedor) as TOTAL from contactos_intap.proveedores ";
            Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            int total = rs.getInt("TOTAL");
            pool.freeConnection(con);
            return total;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return -1;
        }

    }

    public int getCantidadAlgunProveedor(int pk){
        String query = "select count(id_proveedor) as TOTAL from contactos_intap.empresa" +
                " where id_proveedor =  "+pk;
        Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            int total = rs.getInt("TOTAL");
            pool.freeConnection(con);
            return total;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return -1;
        }


    }

    public int getCantidadAlgunTipoContacto(int pk){
        String query = "select count(id_tipo) as TOTAL from contactos_intap.empresa" +
                " where id_tipo =  "+pk;
        Connection con = pool.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            int total = rs.getInt("TOTAL");
            pool.freeConnection(con);
            return total;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return -1;
        }  
    }

    public ArrayList<InfoTipoContacto> getProveedores(){
        ArrayList<InfoTipoContacto> nombres = new ArrayList<InfoTipoContacto>();
        String query = "select * from contactos_intap.proveedores order by detalle;";
        Connection con = pool.getConnection();
        InfoTipoContacto tipo;
 
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

           while(rs.next()){
             tipo = new InfoTipoContacto();
                        tipo.setDetalle(rs.getString("detalle"));
                        tipo.setPK(rs.getInt("id_proveedor"));
                        tipo.setIsProveedor(true);
            nombres.add(tipo);
           }
            pool.freeConnection(con);
           return nombres;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public ArrayList<Contacto> getEmpleados(){

        Contacto info;
            ArrayList<Contacto> resultado = new ArrayList<Contacto>();
            Connection con = pool.getConnection();
        try {
            String query = "select  id_empleados as id_contacto ,nombre_empleado as nombre_contacto, cedula, ubicacion as nombre,telefono_oficina as telefono" +
                    ", extension as ext, celular, telefono_casa , fecha_nacimiento, fecha_ingreso," +
                    "direccion_residencia as direccion , barrio, opcion, celular_aux, indicativo_ofi as indicativo," +
                    "indicativo_casa, telefono_casa_aux as telcasa ,indicativo_casa_aux as ind_casa_aux, cargo ,mail" +
                    "  from contactos_intap.empleados order by nombre_contacto;";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                info = new Contacto();
                info.setCargo(rs.getString("cargo"));
                info.setCelular(rs.getString("celular"));
                info.setDireccion(rs.getString("direccion"));
                info.setExt(rs.getString("ext"));
                info.setMail(rs.getString("mail"));
                info.setNombreContacto(rs.getString("nombre_contacto"));
                info.setTelefono(rs.getString("telefono"));
                info.setPK(rs.getInt("id_contacto"));
                info.setCelular_opcional(rs.getString("celular_aux"));
                info.setIndicativo_aux(rs.getString("ind_casa_aux"));
                info.setIndicativo(rs.getString("indicativo"));
                info.setNombreEmpresa(rs.getString("nombre"));
                info.setTelefonoCasa(rs.getString("telefono_casa"));
                info.setTelefonoCasa_aux(rs.getString("telcasa"));
                info.setOpcion(rs.getString("opcion"));
                info.setIndicativo_casa(rs.getString("indicativo_casa"));
                info.setIndicativo_casa_aux(rs.getString("ind_casa_aux"));
                info.setBarrio(rs.getString("barrio"));
                info.setFechaIngreso(rs.getString("fecha_ingreso"));
                info.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                info.setCedula(rs.getString("cedula"));
                info.setIsEmpleado(true);
                resultado.add(info);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
        
    }

    public boolean editarEmpleado(String nombre,String cedula, String ubicacion, String telefono_oficina, String extension,String celular, String telefono_casa, String fecha_nacimiento,
                String fecha_ingreso, String direccion,String barrio,String opcion, String cel_aux,String indica_ofi,String indica_casa,String tel_aux, String indica_casa_aux, String mail, int pkEmpleado, String cargo){

        String query = "update contactos_intap.empleados set nombre_empleado = '"+nombre+"', cedula = "+cedula+", ubicacion = '"+ubicacion+"' , telefono_oficina = "+telefono_oficina+", " +
                "extension = "+extension+", celular= "+celular+", telefono_casa = "+telefono_casa+", fecha_nacimiento = '"+fecha_nacimiento+"', fecha_ingreso = '"+fecha_ingreso+"', direccion_residencia = '"+direccion+"'," +
                "barrio = '"+barrio+"' , opcion = "+opcion+", celular_aux ="+cel_aux+" , indicativo_ofi ="+indica_ofi+" , indicativo_casa = "+indica_casa+", telefono_casa_aux = "+tel_aux+", " +
                "indicativo_casa_aux = "+indica_casa_aux+",  cargo = '"+cargo+"', mail = '"+mail+"' where id_empleados = " +pkEmpleado;

         Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

    public ArrayList<InfoEmpresa> getEmpresasProveedoras(){

        ArrayList<InfoEmpresa> empresas = new ArrayList<InfoEmpresa>();
        InfoEmpresa empresa;
        String query = "select e.*, p.detalle as detalle_proveedor, t.detalle from contactos_intap.empresa e " +
                    "inner join contactos_intap.tipo_contacto t on e.id_tipo=t.id_tipo " +
                    " inner join contactos_intap.proveedores p on e.id_proveedor = p.id_proveedor " +
                    " where e.id_proveedor <> -1" +
                    " order by nombre;";
        
         Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                 empresa = new InfoEmpresa();
                            empresa.setDireccion(rs.getString("direccion"));
                            empresa.setId_empresa(rs.getInt("id_empresa"));
                            empresa.setId_tipo(rs.getInt("id_tipo"));
                            empresa.setNit(rs.getString("nit"));
                            empresa.setNombre(rs.getString("nombre"));
                            empresa.setTelefono(rs.getString("telefono"));
                            empresa.setCiudad(rs.getString("ciudad"));
                            empresa.setIndicativo(rs.getString("indicativo"));
                            empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                            empresa.setTipoContacto(rs.getString("detalle"));
                            empresa.setTelefono_aux(rs.getString("telefono_aux"));
                            empresa.setFax(rs.getString("fax"));
                            empresa.setIsProvider(true);
                            empresa.setId_proveedor(rs.getInt("id_proveedor"));
                            empresa.setNombre_proveedor(rs.getString("detalle_proveedor"));
                            empresa.setMail(rs.getString("mail"));
                        empresas.add(empresa);
            }
            pool.freeConnection(con);
            return empresas;
        } catch (SQLException ex) {
            pool.freeConnection(con);
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    public ArrayList<InfoEmpresa> getInfoToProviderEnterprises(int aPk){
        InfoEmpresa empresa;
            ArrayList<InfoEmpresa> resultado = new ArrayList<InfoEmpresa>();
            Connection con = pool.getConnection();
        try {
            String query = "SELECT e.*, tc.detalle, p.detalle as detalle_proveedor FROM contactos_intap.empresa e inner join contactos_intap.tipo_contacto tc on e.id_tipo = tc.id_tipo " +
                    " inner join contactos_intap.proveedores p on p.id_proveedor = e.id_proveedor  ";
             if(aPk>0){
                query += " where e.id_proveedor = "+ aPk+" order by e.nombre;";
            }else{
                query+= " where e.id_proveedor<> -1 order by e.nombre;";
            }
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                empresa = new InfoEmpresa();
                empresa.setDireccion(rs.getString("direccion"));
                empresa.setId_empresa(rs.getInt("id_empresa"));
                empresa.setId_tipo(rs.getInt("id_tipo"));
                empresa.setNit(rs.getString("nit"));
                empresa.setNombre(rs.getString("nombre"));
                empresa.setTelefono(rs.getString("telefono"));
                empresa.setCiudad(rs.getString("ciudad"));
                empresa.setIndicativo(rs.getString("indicativo"));
                empresa.setIndicativo_aux(rs.getString("indicativo_aux"));
                empresa.setTipoContacto(rs.getString("detalle"));
                empresa.setTelefono_aux(rs.getString("telefono_aux"));
                empresa.setFax(rs.getString("fax"));
                empresa.setIsProvider(true);
                empresa.setId_proveedor(rs.getInt("id_proveedor"));
                empresa.setNombre_proveedor(rs.getString("detalle_proveedor"));
                empresa.setMail(rs.getString("mail"));
                resultado.add(empresa);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
    }

     public ArrayList<Contacto> getContactosFromProviderEnterprise(int pk){

         String query = "select * from contactos_intap.contacto c " +
                 "inner join contactos_intap.empresa e on c.id_empresa = e.id_empresa " +
                 " where e.id_empresa = "+pk;

          Contacto info;
            ArrayList<Contacto> resultado = new ArrayList<Contacto>();
            Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                info = new Contacto();
                info.setCargo(rs.getString("cargo"));
                info.setCelular(rs.getString("celular"));
                info.setDireccion(rs.getString("direccion"));
                info.setExt(rs.getString("ext"));
                info.setMail(rs.getString("mail"));
                info.setNombreContacto(rs.getString("nombre_contacto"));
                info.setObservaciones(rs.getString("observaciones"));
                info.setTelefono(rs.getString("telefono"));
                info.setPK(rs.getInt("id_contacto"));
                info.setId_empresa(rs.getInt("id_empresa"));
                info.setCelular_opcional(rs.getString("celular_aux"));
                info.setExt_opcional(rs.getString("ext_aux"));
                info.setIndicativo_aux(rs.getString("indicativo_aux"));
                info.setIndicativo(rs.getString("indicativo"));
                info.setNombreEmpresa(rs.getString("nombre"));
                info.setTelefono_opcional(rs.getString("telefono_aux"));
                info.setOpcion(rs.getString("opcion"));
                
                resultado.add(info);
            }
            pool.freeConnection(con);
            return resultado;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
         
    }
     public InfoTipoContacto getTipoContactoForProviderEnterprise(int pk){

         Connection con = pool.getConnection();
         InfoTipoContacto tipo = new InfoTipoContacto();
        try {

            String query = "select * from contactos_intap.proveedores where id_proveedor = " + pk;

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                tipo = new InfoTipoContacto();
                tipo.setDetalle(rs.getString("detalle"));
                tipo.setPK(rs.getInt("id_proveedor"));
                tipo.setIsProveedor(true);
            }
            pool.freeConnection(con);
            return tipo;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return null;
        }
     }
     
public int getIndicativo(String ciudad){
      String query = "select indicativo from contactos_intap.ciudades  where nombre ='" + ciudad + "';";
            Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            pool.freeConnection(con);
            return rs.getInt("indicativo");
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return -1;
        }
}

public int getCantidadComentarios(int pkContacto, int pkEmpresa,int pkTipo){

   int quantity=-1;
         String where = "where 1=1";
            if (pkContacto != -1) {
                where += " and id_contacto=" + pkContacto;
            }
            if (pkEmpresa != -1) {
                where += " and id_empresa = " + pkEmpresa;
            }
            if (pkTipo != -1) {
                where += " and id_tipo_contacto = " + pkTipo;
            }
            where += ";";
            String query = "select count(*) cantidad from contactos_intap.comentarios ";
            query += where;
            Connection con = pool.getConnection();
        try {

            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            String com = new String();
            while(rs.next()){
                quantity = rs.getInt("cantidad");

            }

            pool.freeConnection(con);
            return quantity;


        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return -1;
        }

}

public boolean deleteEmpleado(int pk){

    String query = "delete from contactos_intap.empleados where id_empleados =" + pk + ";";
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    
}

public String existsNit(String nit){
     String query = "(select t.detalle as detalle, e.nombre as name ,'ignore' as tipo  from contactos_intap.empresa e inner join contactos_intap.tipo_contacto t on e.id_tipo = t.id_tipo where e.nit = '" + nit + "')"+
"union"+
"(select p.detalle as detalle , e.nombre as name, t.detalle as tipo  from contactos_intap.empresa e inner join contactos_intap.proveedores p on e.id_proveedor = p.id_proveedor inner join contactos_intap.tipo_contacto  t on t.id_tipo = e.id_tipo where e.nit = '" + nit + "')"+
"; ";

     String detalle, name, tipo;
            Connection con = pool.getConnection();
        try {

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

        int times = rs.getFetchSize();
        
           /* while(rs.next()){
                times = rs.getInt("times");
            }
            pool.freeConnection(con);
            if(times != )*/
            return "";

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

}

public boolean insertProveedor(String detalle){
    String query= "insert into contactos_intap.proveedores(detalle) values('"+ detalle + "');" ;

        Connection con = pool.getConnection();
        try {


            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
}
    public ArrayList<InfoTipoContacto> getProviderName(){
        ArrayList<InfoTipoContacto> nombres = new ArrayList<InfoTipoContacto>();
        String query = "select * from contactos_intap.proveedores order by detalle;";
        Connection con = pool.getConnection();
        InfoTipoContacto tipo;

        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

           while(rs.next()){
             tipo = new InfoTipoContacto();
                        tipo.setDetalle(rs.getString("detalle"));
                        tipo.setPK(rs.getInt("id_proveedor"));
                        tipo.setIsProveedor(true);
            nombres.add(tipo);
           }
            pool.freeConnection(con);
           return nombres;

        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

     public boolean editarProveedor(int pk, String detalle){
        Connection con = pool.getConnection();
        String query= "update contactos_intap.proveedores set detalle = '"+detalle+"' where id_proveedor = "+pk+";";
         try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }

    }

      public boolean deleteProveedor(int pk){
        Connection con = pool.getConnection();
        String query = "delete from contactos_intap.proveedores where id_proveedor = "+pk+";";

        try {
            Statement stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            query = "delete from contactos_intap.empresa where id_proveedor = "+pk+"";
            stm = con.createStatement();
            stm.executeUpdate(query);
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return false;
        }
    }

}


