/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import javax.swing.JTree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import model.Contacto;
import model.InfoEmpresa;
import model.InfoTipoContacto;

import view.MainWindow;
import view.ShowContact;
import view.ShowEnterprise;
import view.ShowType;

/**
 *
 * @author INTAPSA
 */
public class TreeEvents {   
    private JTree busquedaTree;//arbol sobre el cual se controlan los eventos
    private BuildingTree bt;//objeto que me permite generar arbol de acuerdo a busquedas
    private MainWindow main;//objeto ventana principal
    private JButton button;
    private Object selected;//nodo seleccionado que puede ser contacto, tipo o compañia
    private DefaultMutableTreeNode last;//ultimo nodo seleccionado
    private JTextField message;//used in actionPerformed in InfoTipoContacto
    private String lastText;//ultimo texto escrito en crear comentario
    private JPopupMenu menuUtil;//popup utilitario que representa algun popup mostrado en pantalla


    public TreeEvents( JTree busquedaTree,BuildingTree bt,MainWindow main) {
        this.main = main;
        lastText = new String();
        message = new JTextField();
        button = new JButton();
        this.busquedaTree = busquedaTree;
        this.bt = bt;
        busquedaTree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                manejoSeleccionArbol(e);
            }
        });


        
    }

    public JTree getBusquedaTree() {
        return busquedaTree;
    }
    

    /*metodo que controla lo que debe suceder al seleccionar algun nodo del arbol*/
    public void manejoSeleccionArbol(TreeSelectionEvent e){


        try {
             last = (DefaultMutableTreeNode) busquedaTree.getLastSelectedPathComponent();//ontiene el ultimo nodo seleccionado
            
            if (last == null) {
                return;
            }
            if (last.isRoot()) {
                return;
            }
            selected = last.getUserObject();
            int tipoBusqueda = bt.getTipoBusquedaActual();
           
            switch (tipoBusqueda) {/*Actua dependiendo del criterio de busqueda*/
                case Consultas.BY_CONTACT_TYPE://por tipo de contacto
                    if(selected instanceof Contacto){

                        Contacto inf = (Contacto)selected;
                        if(!inf.isIsEmpleado())
                                showComments(inf.getPK(), inf.getId_empresa(), -1, inf);
                        
                        
                    }else if(selected instanceof InfoEmpresa){
                        InfoEmpresa em = (InfoEmpresa)selected;
                        showComments(-1, em.getId_empresa(), em.getId_tipo(), em);
                        
                    }else if(selected instanceof InfoTipoContacto){
                        InfoTipoContacto tip =  (InfoTipoContacto)selected;
                        showComments(-1, -1, tip.getPK(),tip);


                        if(tip.getDetalle().equals("Empleados")){
                            bt.completarHijosTipo(last,true);
                        }else{
                            bt.completarHijosTipo(last,false);
                        }

                        

                    }
                     
                    manejoPopupOpciones(selected);
                       

                    break;
                case Consultas.BY_ENTERPRISE: // por empresa
                     if(selected instanceof Contacto){

                      Contacto inf = (Contacto)selected;

                        showComments(inf.getPK(), inf.getId_empresa(), -1, inf);
                  

                    }else if(selected instanceof InfoEmpresa){

                        InfoEmpresa em = (InfoEmpresa)selected;
                        showComments(-1, em.getId_empresa(), em.getId_tipo(),em);
                        bt.completarHijosEmpresa(last,false);
                        manejoPopupOpciones(selected);
                   
                    }else if(selected instanceof InfoTipoContacto){
                        InfoTipoContacto tip =  (InfoTipoContacto)selected;
                        showComments(-1, -1, tip.getPK(),tip);
                      

                    }
                   manejoPopupOpciones(selected);

                    break;
                case Consultas.BY_NAME://por nombre de contacto
                     if(selected instanceof Contacto){

                         Contacto inf = (Contacto)selected;

                        showComments(inf.getPK(), inf.getId_empresa(), -1, inf);

                             bt.completarHijosContacto(last);
                        manejoPopupOpciones(selected);
                   

                    }else if(selected instanceof InfoEmpresa){

                        InfoEmpresa em = (InfoEmpresa)selected;
                        showComments(-1, em.getId_empresa(), em.getId_tipo(),em);
                      
                    }else if(selected instanceof InfoTipoContacto){
                        InfoTipoContacto tip =  (InfoTipoContacto)selected;
                        showComments(-1, -1, tip.getPK(),tip);
                       

                    }

                        manejoPopupOpciones(selected);
                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger(TreeEvents.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
/*Aqui se trata de limpiar el text pane donde se muestran los comentarios*/
    private void clear(){
        
      
        
    }
    /*Muestra en el text pane de comentarios los comentarios asociados a los parametros entregados*/
      private void showComments(int pkCOntacto, int pkCompañia, int pkTipoContacto, Object target){

          /* ArrayList<String> listaComentarios = new ArrayList<String>();
          if(target instanceof Contacto){
               Contacto contacto = (Contacto)selected;
                int cantidadComentarios =0;
                cantidadComentarios = bt.getCon().getCantidadComentarios(contacto.getPK(),contacto.getId_empresa(), -1);
                listaComentarios = bt.getCon().getComentarios(-1, -1, contacto.getPK());
          }else if(target instanceof InfoEmpresa){

               InfoEmpresa empresa = (InfoEmpresa)selected;
                    int cantidadComentarios =0;
                    cantidadComentarios =  bt.getCon().getCantidadComentarios(-1,empresa.getId_empresa(), empresa.getId_tipo());
                    listaComentarios = bt.getCon().getComentarios(-1, -1, empresa.getId_tipo());

          }else if(target instanceof InfoTipoContacto){
              InfoTipoContacto tipo = (InfoTipoContacto)selected;
                int cantidadComentarios =0;
                cantidadComentarios =  bt.getCon().getCantidadComentarios(-1, -1, tipo.getPK());
                 listaComentarios = bt.getCon().getComentarios(-1, -1, tipo.getPK());
          }
          Comentario  com;
        for(String a : listaComentarios){
            com = new Comentario();
            com.añadirComentario(a);
            comments.add(com);
        }
        main.getComentariosSeleccion().setViewportView(comments);*/
         }

    
/*Metodo que controla las opciones del popup mostrado en las opciones de cada seleccion en el arbol*/
    private void manejoPopupOpciones( final Object selected){
         
//maneja el popup cuando se ha seleccionado un tipo contacto
         if(selected instanceof InfoTipoContacto){

             InfoTipoContacto tipo = (InfoTipoContacto)selected;
             ShowType show = new ShowType(main, tipo);

             main.getContainerInfoSelection().setViewportView(show);
             show.setVisible(true);
             main.getContainerInfoSelection().setVisible(true);
             main.repaint();


       //Si seleccion es un contacto
         }else if(selected instanceof Contacto){
//se muestra el contacto
             Contacto contact = (Contacto) selected;
             
             ShowContact show = new ShowContact(contact, main);
             
             main.getContainerInfoSelection().setViewportView(show);
             show.setVisible(true);
             main.getContainerInfoSelection().setVisible(true);
             main.repaint();
            

//SI se selecciono una empresa
         }else if(selected instanceof InfoEmpresa){
              InfoEmpresa empresa = (InfoEmpresa)selected;

              ShowEnterprise show = new ShowEnterprise(main, empresa);
              main.getContainerInfoSelection().setViewportView(show);
              show.setVisible(true);
              main.getContainerInfoSelection().setVisible(true);
              main.repaint();

    }

    }




}
