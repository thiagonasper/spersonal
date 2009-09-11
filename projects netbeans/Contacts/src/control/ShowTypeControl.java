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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import model.ComentarioBean;
import model.InfoTipoContacto;
import view.Comentario;
import view.CommentContainer;
import view.MainWindow;

/**
 *
 * @author INTAPSA
 */
public class ShowTypeControl extends EditableObject{

    private JTextField message;


    public ShowTypeControl(JComponent component, MainWindow main, Object selected) {
        super(component, main, selected);
        updateComments();
        message = new JTextField();

    }

    public int getCantidad(){
        InfoTipoContacto tipo = (InfoTipoContacto)selected;
        if(tipo.isIsProveedor()){
            return con.getCantidadAlgunProveedor(tipo.getPK());
        }else{
            return con.getCantidadAlgunTipoContacto(tipo.getPK());
        }
    }

    

    @Override
    protected void manejoPopupOpciones(final Object selected, final MouseEvent evt) {
        JPopupMenu menu = new JPopupMenu("Opciones");
         JMenuItem edit = new JMenuItem("Editar");
         JMenuItem elim = new JMenuItem("Eliminar");
         JMenuItem insertCom = new JMenuItem("Insertar comentario");
         
         menu.add(edit);
         menu.add(elim);
         menu.add(insertCom);
         menu.show(component, evt.getX(), evt.getY());

        
            edit.addActionListener(new ActionListener() {
            //controla boton editar
                public void actionPerformed(ActionEvent e) {
                    InfoTipoContacto tipo = (InfoTipoContacto)selected;
                                menuUtil = new JPopupMenu("Edición");

                                JLabel label = new JLabel("Nuevo nombre:");
                                message= new JTextField(tipo.getDetalle(), 40);
                                JButton save = new JButton("Guardar");
                                menuUtil.add(label);
                                menuUtil.add(message);
                                menuUtil.add(save);
                                save.addActionListener(new ActionListener() {
                                    //maneja el guardado de la edicion en curso a traves del boton guardar
                        public void actionPerformed(ActionEvent e) {
                            InfoTipoContacto tipo = (InfoTipoContacto)selected;


                            if(!tipo.isIsProveedor()){
                                    if(con.editarTipoContacto(tipo.getPK(), message.getText().trim())){
                                        tipo.setDetalle( message.getText().trim());
                                        main.getComentariosSeleccion().requestFocus();
                                        menuUtil.setVisible(false);
                                    }else{
                                        menuUtil .setVisible(false);
                                        JOptionPane.showMessageDialog(null, "Upss, ocurrió un error al editar este campo.  Inténtalo nuevamente");
                                    }
                            }else{

                                if(con.editarProveedor(tipo.getPK(), message.getText().trim())){
                                        tipo.setDetalle( message.getText().trim());
                                        main.getComentariosSeleccion().requestFocus();
                                        menuUtil.setVisible(false);
                                    }else{
                                        menuUtil .setVisible(false);
                                        JOptionPane.showMessageDialog(null, "Upss, ocurrió un error al editar este campo.  Inténtalo nuevamente");
                                    }

                            }
                        }
                    });


                                menuUtil.show(component, evt.getX(), evt.getY());


                }
            });
            //manejo de eventos de eliminar

            elim.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    InfoTipoContacto tipo = (InfoTipoContacto)selected;
                    int r = JOptionPane.showConfirmDialog(null, "Confirma eliminación de Tipo", "Eliminación", JOptionPane.YES_NO_OPTION);
                    if(r == JOptionPane.OK_OPTION){

                        if(!tipo.isIsProveedor()){
                            if(!con.deleteTipoContacto(tipo.getPK())){
                                JOptionPane.showMessageDialog(null, "Upss, error al eliminar contacto.  Inténtalo nuevamente.");
                            }else{

                                treeEvents.manejoSeleccionArbol(null);
                            }
                        }else{
                            if(!con.deleteProveedor(tipo.getPK())){
                                JOptionPane.showMessageDialog(null, "Upss, error al eliminar contacto.  Inténtalo nuevamente.");
                            }else{

                                treeEvents.manejoSeleccionArbol(null);
                                
                            }
                        }
                        main.updateEmpresasTipos();

                    }
                }
            });

            //Manejo de la opcion insertar comentario
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
                    }   //controla cambios sobre el documento que contiene el comentario a agragarse
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

//boton de guardar el comentario
                    JButton save = new JButton("Guardar");
                    menuUtil.add(panel);
                    menuUtil.add(save);

                    save.addActionListener(new ActionListener() {
//evento guardar comentario
                        public void actionPerformed(ActionEvent e) {
                          InfoTipoContacto tipo = (InfoTipoContacto)selected;
                          if(!lastText.isEmpty()){
                              if(!con.insertComentario(-1, -1, tipo.getPK(), lastText)){
                                  JOptionPane.showMessageDialog(null, "Upss, ocurrió un error al guardar comentario.  Inténtalo nuevamente");


                              }else{
                                  Calendar cal = Calendar.getInstance();
                                  cal.setTime(new Date());
                                  Comentario comentario = new Comentario(main,tipo,null);
                                  String fecha = cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+
                                          cal.get(Calendar.YEAR);
                                  comentario.añadirComentario(fecha +" \n "+lastText);
                                  updateComments();
                                  menuUtil.setVisible(false);
                              }

                        }
                        }
                    });

                    menuUtil.show(component, evt.getX(), evt.getY());
                }
            });



    }

    @Override
    protected void updateComments() {
        InfoTipoContacto tipo = (InfoTipoContacto)selected;
        int cantidadComentarios =0;
        cantidadComentarios = con.getCantidadComentarios(-1, -1, tipo.getPK());
        comments = new CommentContainer(cantidadComentarios);
        ArrayList<ComentarioBean> listaComentarios = con.getComentarios(-1, -1, tipo.getPK());
        Comentario  com;
        for(ComentarioBean a : listaComentarios){
            com = new Comentario(main,tipo,a);
            com.añadirComentario(a.getTexto());
            comments.add(com);
        }
        main.getComentariosSeleccion().setViewportView(comments);
    }



}
