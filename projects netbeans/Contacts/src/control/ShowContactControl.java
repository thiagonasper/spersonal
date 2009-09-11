/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;

import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import model.ComentarioBean;
import model.Contacto;
import view.Comentario;
import view.CommentContainer;
import view.CreateContact;
import view.CreateEmpleado;
import view.MainWindow;

/**
 *
 * @author INTAPSA
 */
public class ShowContactControl extends EditableObject{

    public ShowContactControl(JComponent component, MainWindow main, Object selected) {
        super(component, main, selected);
        updateComments();
    }


    @Override
    public void manejoPopupOpciones(final Object selected, final MouseEvent evt) {
        JPopupMenu menu = new JPopupMenu("Opciones");
         JMenuItem edit = new JMenuItem("Editar");
         JMenuItem elim = new JMenuItem("Eliminar");
         JMenuItem insertCom = new JMenuItem("Insertar comentario");
         menu.add(edit);
         menu.add(elim);
         menu.add(insertCom);
         menu.show(component, evt.getX(), evt.getY());

          edit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Contacto contact = (Contacto)selected;
                    //StyledDocument s = main.getInfoSeleccionEnArbol().getStyledDocument();
                    if(!contact.isIsEmpleado()){
                        new CreateContact(main, true, contact).setVisible(true);
                    }else{
                        new CreateEmpleado(main, true, contact).setVisible(true);
                    }
                }
            });
//Eventos de eliminar
            elim.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Contacto contact = (Contacto)selected;
                   int r = JOptionPane.showConfirmDialog(null, "Confirma si deseas eliminar este contacto", "Eliminar contacto", JOptionPane.YES_NO_OPTION);
                   if(r == JOptionPane.OK_OPTION){
                       if(!contact.isIsEmpleado()){
                           if(!con.deleteUser(contact.getPK())){
                               JOptionPane.showMessageDialog(null, "Upss, ocurrió un problema al eliminar contacto, por favor inténtalo nuevamente.");
                           }
                       }else{
                           if(!con.deleteEmpleado(contact.getPK())){
                               JOptionPane.showMessageDialog(null, "Upss, ocurrió un problema al eliminar contacto, por favor inténtalo nuevamente.");
                           }
                       }
                   }else{

                   }
                }
            });
//Eventos de insertar comentarios
             insertCom.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    menuUtil = new JPopupMenu("Añadir comentario");
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setSize(800, 100);
                    //JTextArea text = new JTextArea();
                     final JTextPane t = new JTextPane();
                    t.requestFocus();
                    try {
                        t.getStyledDocument().insertString(t.getStyledDocument().getLength(), lastText, null);
                        t.setCaretPosition(t.getText().length());



                    } catch (BadLocationException ex) {
                        Logger.getLogger(TreeEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Eventos sobre el documento sobre el que se esta escribiendo el comentario a guardar
                            t.getDocument().addDocumentListener(new DocumentListener() {

                                public void insertUpdate(DocumentEvent e) {

                                    lastText=t.getText();

                                }

                                public void removeUpdate(DocumentEvent e) {
                                     lastText=t.getText();

                                }

                                public void changedUpdate(DocumentEvent e) {

                                }
                            });
                    t.setAutoscrolls(true);
                    t.setSize(200, 200);
                    panel.add(t,"Center");
                    panel.setPreferredSize(new Dimension(200, 200));

                  // boton para guardar comentario
                    JButton save = new JButton("Guardar");
                    menuUtil.add(panel);
                    menuUtil.add(save);

                    save.addActionListener(new ActionListener() {
//eventos para guardar comentario
                        public void actionPerformed(ActionEvent e) {
                          Contacto contacto = (Contacto)selected;
                          if(!con.insertComentario(contacto.getPK(), contacto.getId_empresa(), -1, lastText)){
                              JOptionPane.showMessageDialog(null, "Upss, ocurrió un error al guardar comentario.  Inténtalo nuevamente");


                          }else{
                           
                                  Calendar cal = Calendar.getInstance();
                                  cal.setTime(new Date());
                                  Comentario comentario = new Comentario(main,contacto,null);
                                  String fecha = cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+
                                          cal.get(Calendar.YEAR);
                                  comentario.añadirComentario(fecha +" \n "+lastText);
                                  updateComments();
                              menuUtil.setVisible(false);
                          }

                        }
                    });

                    menuUtil.show(component, evt.getX(), evt.getY());
                }
            });
    }

    @Override
    protected void updateComments() {
        Contacto contacto = (Contacto)selected;
        int cantidadComentarios =0;
        cantidadComentarios = con.getCantidadComentarios(contacto.getPK(),contacto.getId_empresa(), -1);
        comments = new CommentContainer(cantidadComentarios);
        ArrayList<ComentarioBean> listaComentarios = con.getComentarios(contacto.getPK(),contacto.getId_empresa(), -1);
        Comentario  com;
        for(ComentarioBean a : listaComentarios){
            com = new Comentario(main,contacto,a);
            com.añadirComentario(a.getTexto());
            comments.add(com);
        }
        main.getComentariosSeleccion().setViewportView(comments);
    }

   

   

    

   



    

}
