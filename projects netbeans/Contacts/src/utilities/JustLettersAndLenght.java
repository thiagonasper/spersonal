/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;


import javax.swing.JLabel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author INTAPSA
 */
public class JustLettersAndLenght extends PlainDocument{
    private int lenght;

    public JustLettersAndLenght(int lenght) {
        this.lenght = lenght;
    }



    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        for(int i=0; i<str.length();i++){
            if(Character.isDigit(str.charAt(i))  || getLength()-1>lenght  ){
                return;
            }

            super.insertString(offs, str, a);
        }

        
    }

    



}
