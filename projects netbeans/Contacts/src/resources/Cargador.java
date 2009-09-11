/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.net.URL;

/**
 *
 * @author INTAPSA
 */
public class Cargador {

    public static URL imageURL(String nameImage){

        URL imageUrl = Cargador.class.getResource(nameImage);
        if(imageUrl == null){
            System.out.println("NO existe "+nameImage);
        }

        return imageUrl;
        
    }

}
