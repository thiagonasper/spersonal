/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on 8/07/2009, 07:53:02 AM
 */

package view;



import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import control.BuildingTree;
import control.Consultas;
import control.DBHandler;
import control.TreeEvents;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import model.InfoEmpresa;
import model.InfoTipoContacto;
import resources.Cargador;


/**
 *
 * @author INTAPSA
 */
public class MainWindow extends javax.swing.JFrame {


    public static DBHandler pool;//manejo de conexiones a la BD
    private Image icon;//Icono del frame principal
    private TreeEvents eventsForTrees;//Objeto que maneja los eventos
    private BuildingTree b;//Objeto para regenrar arbol en cada evento
    private boolean isLookingFor;//Se esta buscando algo?
    private String match;//Criterio de busqueda
    private String filtro;//Filtro actual de busqueda
    private int opcionBusqueda;//Nuemro que representa la opcion de busqueda
    private ArrayList<InfoTipoContacto> tipos;//Lista de tipos de contacto
    private ArrayList<InfoTipoContacto> proveedores;
    private ArrayList<InfoEmpresa> empresas;
    private ArrayList<String> empString ;
    private boolean showEmpresaFilter;
    private boolean showTipoFilter;
    private boolean alreadyShowingEmpresaFilter;//Me dice si actualmente estoy mostrando el combo del segundo filtro empresa(secondCriteria)
    private boolean alreadyShowingTipoFilter;//Me dice si actualmente estoy mostrando el combo del segundo

    private boolean isLookingForEmpleado;
    private boolean isLookingForProvider;

    public static final int SECOND_CRITERIA_COMBO =0;
    public static final int THIRD_CRITERIA_COMBO =1;


    /** Creates new form MainWindow */
    @SuppressWarnings("static-access")
    public MainWindow() {
         isLookingForEmpleado = false;
         isLookingForProvider = false;
        InfoTipoContacto tipoAux = new InfoTipoContacto();//variable de utilidad para agregar una opcion "--"
        //al secondCriteriaCombo
        InfoEmpresa empresaAux = new InfoEmpresa();
        empString = new ArrayList<String>();
        alreadyShowingEmpresaFilter = false;
        alreadyShowingTipoFilter = false;
        showEmpresaFilter = true;
        showTipoFilter = false;
        match = new String();
        tipos = new ArrayList<InfoTipoContacto>();
       
        opcionBusqueda = Consultas.BY_ENTERPRISE;
        DBHandler.initialize();
        isLookingFor = false;
        b = new BuildingTree(pool.getPool(),this);
        
      
          try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());

        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
          initComponents();
          updateEmpresasTipos();
        secondCriteriasBox.setModel(new DefaultComboBoxModel(empresas.toArray()));
        secondCriteriasBox.setSelectedIndex(secondCriteriasBox.getItemCount()-1);

        thirdCriteriaBox.setModel(new DefaultComboBoxModel(proveedores.toArray()));
        thirdCriteriaBox.setSelectedIndex(thirdCriteriaBox.getItemCount()-1);
        
          if(tipos == null)
              JOptionPane.showMessageDialog(this, "Problema consulta");
       
       
         //Se carga Logo INTAP
         URL logo;
         logo = Cargador.imageURL("INTAPLOGO.JPG");

         if(logo != null){
             icon = Toolkit.getDefaultToolkit().getImage(logo);
             setIconImage(icon);
         }


        filtro = String.valueOf(busquedaPor.getSelectedItem());
        eventsForTrees = new TreeEvents( vistaTree, b, this);

    }

     public void updateEmpresasTipos(){
          InfoTipoContacto tipoAux = new InfoTipoContacto();//variable de utilidad para agregar una opcion "--"
        //al secondCriteriaCombo
         InfoEmpresa empresaAux = new InfoEmpresa();
         tipoAux.setDetalle("--");
         empresaAux.setNombre("--");
         tipos = b.getCon().getTipoContactos();//obtengo los tipos
         empresas = b.getCon().getEmpresas();//obtengo empresas
         proveedores = b.getCon().getProveedores();
         if(!tipos.get(tipos.size()-1).getDetalle().equals("--"))
            tipos.add(tipoAux);
         if(!proveedores.get(proveedores.size()-1).getDetalle().equals("--"))
            proveedores.add(tipoAux);
         if(!empresas.get(empresas.size()-1).getNombre().equals("--"))
            empresas.add(empresaAux);

      

        thirdCriteriaBox.setModel(new DefaultComboBoxModel(proveedores.toArray()));
        thirdCriteriaBox.setSelectedIndex(thirdCriteriaBox.getItemCount()-1);
     }

   

    public ArrayList<InfoEmpresa> getEmpresas() {
        return empresas;
    }

   

    public JScrollPane getContainerInfoSelection() {
        return containerInfoSelection;
    }
    



    public static DBHandler getPool() {
        return pool;
    }

    public JScrollPane getComentariosSeleccion() {
        return comentariosSeleccion;
    }

   


    private Dimension getSizeOfScreen(){
         return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ver = new javax.swing.JPanel();
        vistaPanel = new javax.swing.JPanel();
        busquedaPor = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        busquedaText = new javax.swing.JTextField();
        secondCriteriasBox = new javax.swing.JComboBox();
        arrow = new javax.swing.JLabel();
        proveedorL = new javax.swing.JLabel();
        thirdCriteriaBox = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        izquierdo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Contactos INTAP S.A.");
        vistaTree = new javax.swing.JTree(root);
        derecho = new javax.swing.JPanel();
        splitInfoContacto = new javax.swing.JSplitPane();
        comentarios = new javax.swing.JPanel();
        comentariosSeleccion = new javax.swing.JScrollPane();
        containerInfoSelection = new javax.swing.JScrollPane();
        barraMenu = new javax.swing.JMenuBar();
        archivoMenu = new javax.swing.JMenu();
        salirBoton = new javax.swing.JMenuItem();
        contactosMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        contactoAdd = new javax.swing.JMenuItem();
        empresaAdd = new javax.swing.JMenuItem();
        empleadoButton = new javax.swing.JMenuItem();
        tipoAdd = new javax.swing.JMenuItem();
        ayudaMenu = new javax.swing.JMenu();
        acercaBoton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Contactos INTAP S.A.");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        ver.setLayout(new java.awt.BorderLayout());

        vistaPanel.setPreferredSize(new java.awt.Dimension(760, 60));

        busquedaPor.setFont(new java.awt.Font("Tahoma", 1, 12));
        busquedaPor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compañía", "Contacto", "Tipo Contacto" }));
        busquedaPor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedaPorActionPerformed(evt);
            }
        });
        busquedaPorActionPerformed(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Filtrar por:");

        jLabel2.setText("Buscar:");

        busquedaText.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent evt){

            }
            public void insertUpdate(DocumentEvent evt){
                handlingTextfieldChanges();
            }
            public void removeUpdate(DocumentEvent evt){
                handlingTextfieldChanges();
            }
        });

        secondCriteriasBox.setFont(new java.awt.Font("Tahoma", 1, 12));
        secondCriteriasBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        secondCriteriasBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secondCriteriasBoxActionPerformed(evt);
            }
        });

        arrow.setFont(new java.awt.Font("Tahoma", 1, 11));
        arrow.setText("Empresa:");

        proveedorL.setFont(new java.awt.Font("Tahoma", 1, 11));
        proveedorL.setText("Proveedor:");

        thirdCriteriaBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        thirdCriteriaBox.setVisible(false);
        proveedorL.setVisible(false);
        thirdCriteriaBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirdCriteriaBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout vistaPanelLayout = new javax.swing.GroupLayout(vistaPanel);
        vistaPanel.setLayout(vistaPanelLayout);
        vistaPanelLayout.setHorizontalGroup(
            vistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(busquedaText, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(busquedaPor, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(arrow, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(secondCriteriasBox, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(proveedorL)
                .addGap(18, 18, 18)
                .addComponent(thirdCriteriaBox, 0, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        vistaPanelLayout.setVerticalGroup(
            vistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vistaPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(vistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(busquedaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(vistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(busquedaPor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(vistaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(secondCriteriasBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(arrow)
                        .addComponent(proveedorL)
                        .addComponent(thirdCriteriaBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        vistaTree.expandRow(1);

        ver.add(vistaPanel, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(250);

        izquierdo.setLayout(new javax.swing.BoxLayout(izquierdo, javax.swing.BoxLayout.LINE_AXIS));

        DefaultTreeCellRenderer defaultTree = (DefaultTreeCellRenderer)vistaTree.getCellRenderer();
        defaultTree.setLeafIcon(new ImageIcon(Cargador.imageURL("flecha.png")));
        defaultTree.setClosedIcon(new ImageIcon(Cargador.imageURL("Contact.JPG")));
        defaultTree.setOpenIcon(new ImageIcon(Cargador.imageURL("main.JPG")));
        vistaTree.setFont(new java.awt.Font("Tahoma", 1, 11));
        jScrollPane1.setViewportView(vistaTree);

        izquierdo.add(jScrollPane1);

        jSplitPane1.setLeftComponent(izquierdo);

        derecho.setLayout(new javax.swing.BoxLayout(derecho, javax.swing.BoxLayout.LINE_AXIS));

        splitInfoContacto.setDividerLocation(400);
        splitInfoContacto.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        splitInfoContacto.setResizeWeight(0.4);

        comentarios.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comentarios", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        comentarios.setLayout(new javax.swing.BoxLayout(comentarios, javax.swing.BoxLayout.LINE_AXIS));
        comentarios.add(comentariosSeleccion);

        splitInfoContacto.setBottomComponent(comentarios);

        containerInfoSelection.setBackground(new java.awt.Color(255, 255, 255));
        splitInfoContacto.setLeftComponent(containerInfoSelection);

        derecho.add(splitInfoContacto);

        jSplitPane1.setRightComponent(derecho);

        ver.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(ver);

        archivoMenu.setText("Archivo");

        salirBoton.setText("Salir");
        salirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirBotonActionPerformed(evt);
            }
        });
        archivoMenu.add(salirBoton);

        barraMenu.add(archivoMenu);

        contactosMenu.setText("Contactos");

        jMenu1.setText("Añadir");

        contactoAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        contactoAdd.setText("Contacto");
        contactoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactoAddActionPerformed(evt);
            }
        });
        jMenu1.add(contactoAdd);

        empresaAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        empresaAdd.setText("Empresa");
        empresaAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaAddActionPerformed(evt);
            }
        });
        jMenu1.add(empresaAdd);

        empleadoButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        empleadoButton.setText("Empleado");
        empleadoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empleadoButtonActionPerformed(evt);
            }
        });
        jMenu1.add(empleadoButton);

        tipoAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        tipoAdd.setText("Tipo contacto");
        tipoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoAddActionPerformed(evt);
            }
        });
        jMenu1.add(tipoAdd);

        contactosMenu.add(jMenu1);

        barraMenu.add(contactosMenu);

        ayudaMenu.setText("Ayuda");

        acercaBoton.setText("Acerca");
        acercaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaBotonActionPerformed(evt);
            }
        });
        ayudaMenu.add(acercaBoton);

        barraMenu.add(ayudaMenu);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirBotonActionPerformed
        // TODO add your handling code here:
        System.exit(0); 
    }//GEN-LAST:event_salirBotonActionPerformed


/*Este metodo me fija los detalles que se deben activar al buscar por empresa. Tener presente que
 model del combo se carga con un array de strings*/
    private void setEmpresaFilter(){
        
        
            showEmpresaFilter = true;
            showTipoFilter = false;
            arrow.setText("Empresa:");
            if(empresas!=null){
                secondCriteriasBox.setModel(new DefaultComboBoxModel(empresas.toArray()));
                secondCriteriasBox.setSelectedIndex(secondCriteriasBox.getItemCount()-1);
            }
            secondCriteriasBox.setVisible(true);
            arrow.setVisible(true);
            alreadyShowingEmpresaFilter = true;
        
    }
/*Este metodo fija los detalles que deben activarse al buscar por tipo.  Tener presente que el
 model se carga cono un array de InfoTipoContacto, quien sobreescribe toString mostrando el detalle*/
    private void setTipoFilter(){
        
     
            showTipoFilter = true;
            showEmpresaFilter = false;
            arrow.setText("Tipo:");
            if(tipos!=null){
                secondCriteriasBox.setModel(new DefaultComboBoxModel(tipos.toArray()));
                secondCriteriasBox.setSelectedIndex(secondCriteriasBox.getItemCount()-1);
            }
            secondCriteriasBox.setVisible(true);
            arrow.setVisible(true);
            alreadyShowingTipoFilter = true;
        
    }
/*Desactiva los detalles al no estar buscando ni por Tipo de contacto ni por Compañia,
 en este momento la opcion que quedaria es por "contacto".*/
    private void disableSecondFilters(){
        alreadyShowingEmpresaFilter = false;
        alreadyShowingTipoFilter = false;
        showEmpresaFilter = false;
        showTipoFilter = false;
        secondCriteriasBox.setVisible(false);
        arrow.setVisible(false);
    }


   
    private void busquedaPorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busquedaPorActionPerformed
        // TODO add your handling code here:
        String selected = String.valueOf(busquedaPor.getSelectedItem());
        String selectedAtSecondCriteria = String.valueOf(secondCriteriasBox.getSelectedItem());
        int pK;//usada para entregar la pk que profundiza las busquedas de tipo y empresa en caso de necesitarse
        int pkProvider=0;//usada para entregar la pk del provider
        filtro = String.valueOf(busquedaPor.getSelectedItem());
        String match = busquedaText.getText();
        isLookingForEmpleado = false;
        isLookingForProvider = false;

        if(selected.equals("Empleados")){
            isLookingForEmpleado = true;
        }


        if(selected.equals("Compañía")){
            
            if(!alreadyShowingEmpresaFilter){
                disableSecondFilters();
                setEmpresaFilter();
            }

            opcionBusqueda = Consultas.BY_ENTERPRISE;

            if(selectedAtSecondCriteria.equals("--")|| selectedAtSecondCriteria.equals("null")){
                    pK = 0;
                }else{
                    InfoEmpresa empresa = (InfoEmpresa)secondCriteriasBox.getSelectedItem();
                    pK = empresa.getId_empresa();

                }
            if(!isLookingFor){
                b.generarArbolDesdeDBFull(Consultas.BY_ENTERPRISE, vistaTree,pK,false ,false);
            }else{
                b.getCon().busqueda(Consultas.BY_ENTERPRISE, match, vistaTree,pK,b,false,isLookingForEmpleado,0);
            }

        }else if(selected.equals("Contacto")){

           
             disableSecondFilters();
            opcionBusqueda = Consultas.BY_NAME;
            if(!isLookingFor){
                b.generarArbolDesdeDBFull(Consultas.BY_NAME, vistaTree,0,false,false);

            }else{
                b.getCon().busqueda(Consultas.BY_NAME, match, vistaTree,0,b,false, isLookingForEmpleado,0);

            }





        }else if(selected.equals("Tipo Contacto")){
            if(!alreadyShowingTipoFilter){
                disableSecondFilters();
                setTipoFilter();
            }
            thirdCriteriaBox.setVisible(false);
            proveedorL.setVisible(false);
            opcionBusqueda = Consultas.BY_CONTACT_TYPE;
            if(selectedAtSecondCriteria.equals("--")|| selectedAtSecondCriteria.equals("null")){
                    pK = 0;
                }else{
                InfoTipoContacto tipo = (InfoTipoContacto)secondCriteriasBox.getSelectedItem();
                    pK = tipo.getPK();
                    InfoTipoContacto provider = (InfoTipoContacto)thirdCriteriaBox.getSelectedItem();
                    pkProvider = provider.getPK();

                }
            if(!isLookingFor){
                
                b.generarArbolDesdeDBFull(Consultas.BY_CONTACT_TYPE, vistaTree,pK,false,false);

            }else{

                if(!isLookingForProvider){
                    b.getCon().busqueda(Consultas.BY_CONTACT_TYPE, match, vistaTree,pK,b,false, isLookingForEmpleado,0);
                }else{
                    b.getCon().busqueda(Consultas.BY_CONTACT_TYPE, match, vistaTree,pK,b,true, isLookingForEmpleado,pkProvider);
                }

            }
        }
}//GEN-LAST:event_busquedaPorActionPerformed

    private void acercaBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaBotonActionPerformed
        // TODO add your handling code here:
        Acerca acerca = new Acerca();
        acerca.setVisible(true);
    }//GEN-LAST:event_acercaBotonActionPerformed

    private void contactoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactoAddActionPerformed
        // TODO add your handling code here:+
        CreateContact create = new CreateContact(this,false,null);
        create.setVisible(true);
    }//GEN-LAST:event_contactoAddActionPerformed



    private void empresaAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaAddActionPerformed
        // TODO add your handling code here:
        CreateEnterprise create = new CreateEnterprise(this,false,null);
        create.setVisible(true);
    }//GEN-LAST:event_empresaAddActionPerformed

    private void tipoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoAddActionPerformed
        // TODO add your handling code here:
        CreateType create = new CreateType(this);
        create.setVisible(true);
    }//GEN-LAST:event_tipoAddActionPerformed

    private void secondCriteriasBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondCriteriasBoxActionPerformed
        // TODO add your handling code here:
       
        String selected = String.valueOf(secondCriteriasBox.getSelectedItem());
        if(selected.equals("Proveedores")){
            updateEmpresasTipos();
            thirdCriteriaBox.setVisible(true);
            proveedorL.setVisible(true);
            isLookingForProvider = true;
            String selectedInTh = String.valueOf(thirdCriteriaBox.getSelectedItem());

            int pK ;
            if(selectedInTh.equals("--")){
                pK =0;

            }else{

                InfoTipoContacto empresa = (InfoTipoContacto)thirdCriteriaBox.getSelectedItem();
                pK = empresa.getPK();
            }
            if(!isLookingFor){
                                b.generarArbolDesdeDBFull(Consultas.BY_ENTERPRISE, vistaTree,pK,true ,false);
                     }else{
                                b.getCon().busqueda(Consultas.BY_ENTERPRISE, match, vistaTree,pK,b,true, isLookingForEmpleado,0);
                    }

        }else{
            isLookingForProvider = false;
            busquedaPorActionPerformed(evt);
            thirdCriteriaBox.setVisible(false);
            proveedorL.setVisible(false);
        }
    }//GEN-LAST:event_secondCriteriasBoxActionPerformed

    private void thirdCriteriaBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirdCriteriaBoxActionPerformed
        // TODO add your handling code here:
        String selected = String.valueOf(thirdCriteriaBox.getSelectedItem());
        int pK;
        int pkProvider=0;
        if(selected.equals("--")){
            pK =0;

            }else{
            InfoTipoContacto ti = (InfoTipoContacto)secondCriteriasBox.getSelectedItem();
                 pK = ti.getPK();
                InfoTipoContacto tipo = (InfoTipoContacto)thirdCriteriaBox.getSelectedItem();
                pkProvider = tipo.getPK();
            }

         if(!isLookingFor){

                 b.generarArbolDesdeDBFull(Consultas.BY_ENTERPRISE, vistaTree,pkProvider,true ,false);
         }else{
                 b.getCon().busqueda(Consultas.BY_ENTERPRISE, match, vistaTree,pK,b,true, isLookingForEmpleado,pkProvider);
         }

    }//GEN-LAST:event_thirdCriteriaBoxActionPerformed

    private void empleadoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadoButtonActionPerformed
        // TODO add your handling code here:
        new CreateEmpleado(this, false, null) ;
    }//GEN-LAST:event_empleadoButtonActionPerformed


    public void clearComentarios(){

        
    }

    public TreeEvents getEventsForTrees() {
        return eventsForTrees;
    }


    private void handlingTextfieldChanges(){
         String text = busquedaText.getText();
         String textSecondCriteria= String.valueOf(secondCriteriasBox.getSelectedItem());
         int pK;
         int pkProvider=0;
           if(textSecondCriteria.equals("--")){//se escoge la pk adecuada para la busqueda
                pK=0;
            }else{

                switch(opcionBusqueda){
                    case Consultas.BY_CONTACT_TYPE:
                        InfoTipoContacto tipo = (InfoTipoContacto)secondCriteriasBox.getSelectedItem();
                        pK = tipo.getPK();
                        InfoTipoContacto provider = (InfoTipoContacto)thirdCriteriaBox.getSelectedItem();
                    pkProvider = provider.getPK();

                        
                        break;
                    case Consultas.BY_ENTERPRISE:
                        InfoEmpresa empresa = (InfoEmpresa)secondCriteriasBox.getSelectedItem();
                        pK = empresa.getId_empresa();
                        break;
                    default:
                        pK=0;
                        break;
                }
            }
        if(text.trim().isEmpty()){//Si la caja de busqueda esta vacia
          
            isLookingFor = false;
            match = new String();
            b.generarArbolDesdeDBFull(opcionBusqueda, vistaTree,pK, false, false);
        }else{
            isLookingFor = true;
            match = busquedaText.getText();
            if(!isLookingForProvider){
                b.getCon().busqueda(opcionBusqueda, match, vistaTree,pK,b,false, isLookingForEmpleado,0);
            }else{
                b.getCon().busqueda(opcionBusqueda, match, vistaTree,pK,b,true, isLookingForEmpleado,pkProvider);
            }
        }
    }

    public void updateAnyCombo(int option){
        switch(option){
            
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainWindow mw = new MainWindow();
                mw.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mw.setVisible(true);

            }
        });
    }

    public JTree getJTree1() {
        return vistaTree;
    }

    public BuildingTree getB() {
        return b;
    }


    public ArrayList<InfoTipoContacto> getTipos() {
        return tipos;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaBoton;
    private javax.swing.JMenu archivoMenu;
    private javax.swing.JLabel arrow;
    private javax.swing.JMenu ayudaMenu;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JComboBox busquedaPor;
    private javax.swing.JTextField busquedaText;
    private javax.swing.JPanel comentarios;
    private javax.swing.JScrollPane comentariosSeleccion;
    private javax.swing.JMenuItem contactoAdd;
    private javax.swing.JMenu contactosMenu;
    private javax.swing.JScrollPane containerInfoSelection;
    private javax.swing.JPanel derecho;
    private javax.swing.JMenuItem empleadoButton;
    private javax.swing.JMenuItem empresaAdd;
    private javax.swing.JPanel izquierdo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel proveedorL;
    private javax.swing.JMenuItem salirBoton;
    private javax.swing.JComboBox secondCriteriasBox;
    private javax.swing.JSplitPane splitInfoContacto;
    private javax.swing.JComboBox thirdCriteriaBox;
    private javax.swing.JMenuItem tipoAdd;
    private javax.swing.JPanel ver;
    private javax.swing.JPanel vistaPanel;
    private javax.swing.JTree vistaTree;
    // End of variables declaration//GEN-END:variables

}
