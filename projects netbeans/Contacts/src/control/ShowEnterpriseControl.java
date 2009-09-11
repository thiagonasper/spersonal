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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import model.ComentarioBean;
import model.InfoEmpresa;
import view.Comentario;
import view.CommentContainer;
import view.CreateEnterprise;
import view.MainWindow;

/**
 *
 * @author INTAPSA
 */
public class ShowEnterpriseControl extends EditableObject{

    public ShowEnterpriseControl(JComponent component, MainWindow main, Object selected) {
        super(component, main, selected);
        updateComments();
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
         //elim.setEnabled(false);
//eventos para editar empresa
             edit.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    InfoEmpresa empresa = (InfoEmpresa)selected;
//                    StyledDocument s = main.getInfoSeleccionEnArbol().getStyledDocument();
                    new CreateEnterprise(main, true, empresa).setVisible(true);
                }
            });

//eventos eliminar empresa
            elim.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    InfoEmpresa empresa = (InfoEmpresa)selected;
                    int r = JOptionPane.showConfirmDialog(null, "Confirma que deseas eliminar esta compañía","Eliminar Contacto",JOptionPane.YES_NO_OPTION);
                    if(r == JOptionPane.OK_OPTION){
                        if(!con.deleteEmpresa(empresa.getId_empresa())){
                            JOptionPane.showMessageDialog(null, "Upss, ocurrió u problema al eliminar esta compañía");

                        }
                    }
                }
            });
//evento insertar comentario empresa
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
                    //eventos sobre docuemento que contiene comentario a guardar
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
//Eventos guardar cometnario para empresa

                    JButton save = new JButton("Guardar");
                    menuUtil.add(panel);
                    menuUtil.add(save);

                    save.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                          InfoEmpresa empresa = (InfoEmpresa)selected;
                          if(!con.insertComentario(-1, empresa.getId_empresa(), empresa.getId_tipo(), lastText)){
                              JOptionPane.showMessageDialog(null, "Upss, ocurrió un error al guardar comentario.  Inténtalo nuevamente");
                          }else{
                             Calendar cal = Calendar.getInstance();
                                  cal.setTime(new Date());
                                  Comentario comentario = new Comentario(main,empresa,null);
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
        InfoEmpresa empresa = (InfoEmpresa)selected;
        int cantidadComentarios =0;
        cantidadComentarios = con.getCantidadComentarios(-1,empresa.getId_empresa(), empresa.getId_tipo());
        comments = new CommentContainer(cantidadComentarios);
        ArrayList<ComentarioBean> listaComentarios = con.getComentarios(-1,empresa.getId_empresa(), empresa.getId_tipo());
        Comentario  com;
        for(ComentarioBean a : listaComentarios){
            com = new Comentario(main,empresa,a);
            com.añadirComentario(a.getTexto());
            comments.add(com);
        }
        main.getComentariosSeleccion().setViewportView(comments);
    }

    

}
