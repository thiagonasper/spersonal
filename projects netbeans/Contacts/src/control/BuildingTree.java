/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import com.javaexchange.dbConnectionBroker.DbConnectionBroker;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.Contacto;
import model.InfoEmpresa;
import model.InfoTipoContacto;
import view.MainWindow;

/**
 *
 * @author INTAPSA
 */
public class BuildingTree {

    private DbConnectionBroker pool;
    public static final int CONTACTO = 0;
    public static final int EMPRESA =1;
    public static final int TIPO_CONTACTO =2;
    private int tipoBusquedaActual=Consultas.BY_ENTERPRISE;
  private MainWindow mw;
    private Consultas con;
   

    public BuildingTree(DbConnectionBroker pool, MainWindow mw) {
        this.mw = mw;
        this.pool = pool;
      
        con = new Consultas(pool);
    }

    public void setTipoBusquedaActual(int tipoBusquedaActual) {
        this.tipoBusquedaActual = tipoBusquedaActual;
    }

    
    /*Genera un arbol completo con la opcion de busqueda*/
    public JTree generarArbolDesdeDBFull(int opcion,JTree old, int aPk, boolean isProveedor, boolean isEmpleado){

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Contactos_Intap");
        DefaultTreeModel model = new DefaultTreeModel(root);
        old.setModel(model);
        tipoBusquedaActual = opcion;

        switch(opcion){
            case Consultas.BY_ENTERPRISE:
                if(!isProveedor){
                   ArrayList<InfoEmpresa> info = con.getInfoToEnterprises(aPk);
                   for(InfoEmpresa a:info){
                       DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a);
                        //hijo.add(new DefaultMutableTreeNode(a.getNombre()));
                        root.add(hijo);
                   }
                }else{//es proveedor
                    if(aPk==0){
                                ArrayList<InfoTipoContacto> info = con.getProviderName();
                                 for(InfoTipoContacto a:info){
                                     DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a);
                                    //hijo.add(new DefaultMutableTreeNode(a.getNombre()));
                                    root.add(hijo);
                                    }
                    }else{
                         ArrayList<InfoEmpresa> info = con.getInfoToProviderEnterprises(aPk);
                                 for(InfoEmpresa a:info){
                                     DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a);
                                    //hijo.add(new DefaultMutableTreeNode(a.getNombre()));
                                    root.add(hijo);
                                    }
                    }
                }

                break;
            case Consultas.BY_NAME:

                if(!isEmpleado){
                            ArrayList<Contacto> info2 = con.getInfoToContacts();
                            for(Contacto a2 : info2){
                                DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a2);
                                //hijo.add(new DefaultMutableTreeNode(a2.getNombreEmpresa()));
                               // hijo.add(new DefaultMutableTreeNode(a2.getDetalle()));
                                root.add(hijo);
                            }
                }else{
                    
                    ArrayList<Contacto> info2 = con.getEmpleados();
                            for(Contacto a2 : info2){
                                DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a2);
                                //hijo.add(new DefaultMutableTreeNode(a2.getNombreEmpresa()));
                               // hijo.add(new DefaultMutableTreeNode(a2.getDetalle()));
                                root.add(hijo);
                            }
                }
                break;
            case Consultas.BY_CONTACT_TYPE:

                ArrayList<InfoTipoContacto> info3 = con.getInfoToTipoContacto(aPk);
               for(InfoTipoContacto a3:info3){
                   DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(a3);
                    hijo.add(new DefaultMutableTreeNode(a3.getDetalle()));
                    root.add(hijo);
               }
                
                break;
        }
        return old;
    }

    public int getTipoBusquedaActual() {
        return tipoBusquedaActual;
    }
    /*Completa los datos correspondientes al los hijos de determinada empresa.*/
    public void completarHijosEmpresa(DefaultMutableTreeNode node, boolean isEmpleado){
        InfoEmpresa empresa;
        ArrayList<Contacto> contactos;
        if(!isEmpleado){
             empresa = (InfoEmpresa)node.getUserObject();
             if(!empresa.isIsProvider()){
                    contactos = con.getInfoContactsForEnterprise(empresa.getId_empresa());
                 }else{
                     contactos = con.getContactosFromProviderEnterprise(empresa.getId_empresa());
                }
        }else{
        empresa= new InfoEmpresa();
        contactos = con.getEmpleados();
        
        }

        
        

        for(Contacto cont : contactos){
            DefaultMutableTreeNode tmp = new DefaultMutableTreeNode();
            tmp.setUserObject(cont);
            node.add(tmp);

            if(!cont.isIsEmpleado()){
                InfoTipoContacto tipo = con.getTipoContactoByEmpresa(empresa.getId_tipo());
                DefaultMutableTreeNode tmp2 = new DefaultMutableTreeNode();
                tmp2.setUserObject(tipo);
                tmp.add(tmp2);
            }
        }
    }
    /*Completar  los hijos correspondientes a un tipo de contacto*/
    public void completarHijosTipo(DefaultMutableTreeNode node, boolean isEmpleado){
        InfoTipoContacto tipo = (InfoTipoContacto) node.getUserObject();
        ArrayList<InfoEmpresa> empresas;
        if(isEmpleado){
            completarHijosEmpresa(node,true);
        }
        if(!tipo.isIsProveedor()){
             empresas = con.getInfoEnterprisesforType(tipo.getPK());
        }else{
            empresas = con.getInfoToProviderEnterprises(tipo.getPK());
        }



        for(InfoEmpresa emp : empresas){
            DefaultMutableTreeNode child = new DefaultMutableTreeNode();
            child.setUserObject(emp);
            ArrayList<Contacto> contac = con.getInfoContactsForEnterprise(emp.getId_empresa());
            node.add(child);
             for(Contacto c : contac){
                DefaultMutableTreeNode ch = new DefaultMutableTreeNode();
                ch.setUserObject(c);   
                child.add(ch);
            }

        }


    }

    /*Completar los hijos correspondientes del contacto seleccionado*/
    public void completarHijosContacto(DefaultMutableTreeNode node){
            Contacto contacto = (Contacto)node.getUserObject();
           InfoEmpresa empresa = con.getEmpresaByContact(contacto.getId_empresa());
           DefaultMutableTreeNode n = new DefaultMutableTreeNode();
           n.setUserObject(empresa);
           node.add(n);
           InfoTipoContacto tipo;
           if(!contacto.isIsEmpleado()){
             tipo = con.getTipoContactoByEmpresa(empresa.getId_tipo());
           }else{
             tipo = con.getTipoContactoForProviderEnterprise(empresa.getId_tipo());
           }

           DefaultMutableTreeNode n2 = new DefaultMutableTreeNode();
           n2.setUserObject(tipo);
           node.add(n2);

    }

    public Consultas getCon() {
        return con;
    }

   
    





}
