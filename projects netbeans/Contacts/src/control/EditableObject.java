/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import view.CommentContainer;
import view.MainWindow;

/**
 *
 * @author INTAPSA
 */
public abstract  class EditableObject {

    protected JComponent component;
    
    protected  JPopupMenu menuUtil;//popup utilitario que representa algun popup mostrado en pantalla
    protected String lastText;
    protected MainWindow main;
    protected Object selected;
    protected  Consultas con;
    protected  TreeEvents treeEvents;
    protected CommentContainer comments;

    public EditableObject(JComponent component, MainWindow main, Object selected) {
        this.component = component;
        this.main = main;
        this.selected = selected;
        con = main.getB().getCon();
        treeEvents = main.getEventsForTrees();
        initializeMouseEvents();
    }


   

   

  

  
    private void initializeMouseEvents(){
        component.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt){
                if(evt.getButton() == MouseEvent.BUTTON3){

                    manejoPopupOpciones(selected, evt);

                }
            }

        });
    }

    protected abstract  void updateComments();


    
    

   
    

    protected  abstract void manejoPopupOpciones(final Object selected, MouseEvent evt);

}
