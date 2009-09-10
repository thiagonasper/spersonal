/*@(#)ClipboardSupport
 *Esta clase provee soporte para que un JTextComponent o un JLabel ejecuten las acciones
 * Cortar, Copiar y Pegar desde y hacia el portapapeles del sistema operativo.
 * Para posteriores modificaciones, se podría tener en cuenta la adición de otros DataFlavor en 
 * los métodos cut, copy y paste, para soportar algún otro tipo de objeto como imágenes, etc.
 */

/**
 *
 * @author David Steven Rojas
 * @empresa INTAP S.A
 * @Fecha 2 Septiembre 2009
 */

package portapapeles;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;


public class ClipboardSupport {
    private JPopupMenu optionsMenu;
    private JMenuItem copy;
    private JMenuItem cut;
    private JMenuItem paste;
    private JTextComponent textComponent;
    private static Clipboard clipBoard  = Toolkit.getDefaultToolkit().getSystemClipboard();

    public ClipboardSupport(JTextComponent textComponent,final JComponent parent) {
        this.textComponent = textComponent;
        optionsMenu = new JPopupMenu();
         copy = new JMenuItem("Copiar");
         cut = new JMenuItem("Cortar");
         paste = new JMenuItem("Pegar");
        copy.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                copy();
            }
        });

        cut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cut();
            }
        });

        paste.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                paste();
            }
        });

        optionsMenu.add(cut);
        optionsMenu.add(copy);
        optionsMenu.add(paste);
        parent.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() == MouseEvent.BUTTON3){
                    optionsMenu.show(parent, e.getX(), e.getY());
                }

            }


        });

    }

    /*
     * Este método provee soporte a algún componente de texto o a un JLabel
     * de acceso (Cortar, Copiar y Pegar texto) desde y hacia el portapapeles
     * del sistema operativo.
     *  By: David Steven Rojas
     */
    public static void addClipboardSupport(final JComponent parent) {
        JTextComponent textComponent;
         try{
             if(parent instanceof JLabel){
                 JTextField text = new JTextField(((JLabel)parent).getText());
                 textComponent = (JTextComponent)text;
                 textComponent.selectAll();
             }else{
                textComponent = (JTextComponent)parent;
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "El JComponent "+parent.getName()+" no es un componente valido.");
            return;
        }
         new ClipboardSupport(textComponent, parent);
    }



    /*Este método copia el contenido texto seleccionado en el componente de Texto*/
    private  void copy(){
        String text = textComponent.getSelectedText();
        StringSelection stringSelection = new StringSelection(text);
        clipBoard.setContents(stringSelection, stringSelection);
    }
    /*Este método se comporta de la siguiente manera:
        <OL>
     *      <LI> Si el componente es un JTextDocument este método cortará el texto seleccionado del componente de texto.
     *      <LI> Si el componente es un JLabel los métodos copy() y cut() se comportan de manera similar.
     * </OL>
     */
    private  void cut(){
        String text = textComponent.getSelectedText();
        StringSelection stringSelection = new StringSelection(text);
        clipBoard.setContents(stringSelection, stringSelection);

        StringBuffer allText;
        int ini = textComponent.getSelectionStart(), fin= textComponent.getSelectionEnd();
        allText = new StringBuffer(textComponent.getText());
        allText.delete(ini, fin);
        text = new String(allText);
        textComponent.setText(text);
    }
    /*Este método trae texto copiado al portapapeles del sistema operativo y lo pega en el componente
     de texto donde se seleccione el menu pegar.
     */
    private  void paste(){
        try {
            Transferable fromClipboard = clipBoard.getContents(this);
            DataFlavor incomingString = new DataFlavor("application/x-java-serialized-object; class=java.lang.String");
            if(fromClipboard.isDataFlavorSupported(incomingString)){
                try {
                    String text = (String) fromClipboard.getTransferData(incomingString);
                    Document doc = textComponent.getDocument();
                    try {
                        doc.insertString(textComponent.getCaretPosition(), text, null);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(ClipboardSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedFlavorException ex) {
                    Logger.getLogger(ClipboardSupport.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClipboardSupport.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClipboardSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
