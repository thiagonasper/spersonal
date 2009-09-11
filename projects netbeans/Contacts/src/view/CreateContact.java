/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CreateContact.java
 *
 * Created on 3/08/2009, 12:18:25 PM
 */

package view;

import com.toedter.calendar.JDateChooser;
import control.Consultas;
import java.awt.Toolkit;
import java.net.URL;

import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Contacto;
import model.InfoEmpresa;
import resources.Cargador;
import utilities.JustLettersAndLenght;
import utilities.JustNumberAndLenght;
import utilities.LettersNumbersAndLenght;
import utilities.LettersNumbersSpecialCharacterAndLength;

/**
 *
 * @author INTAPSA
 */
public class CreateContact extends javax.swing.JFrame {

    private MainWindow mw;
    private ArrayList<InfoEmpresa> empresas;
    private Consultas con;
   // private ArrayList<String> empString ;
   
    private Contacto info;
    private boolean isEditing;
    private JDateChooser ingreso;
    private JDateChooser nacimiento;

    /** Creates new form CreateContact */
    @SuppressWarnings("static-access")
    public CreateContact(MainWindow mw, boolean  isEditing, Contacto info) {
        this.isEditing = isEditing;
        this.info = info;
        if(!isEditing){
             info = new Contacto();
             
        }
        initComponents();
        
        //Se carga Logo INTAP
         URL logo;
         logo = Cargador.imageURL("INTAPLOGO.JPG");

         if(logo != null){

             setIconImage(Toolkit.getDefaultToolkit().getImage(logo));
         }
        this.mw = mw;
         con = new Consultas(mw.pool.getPool());
         this.empresas = mw.getEmpresas();
         
         empresasCombo.setModel(new DefaultComboBoxModel(empresas.toArray()));
         setLocationRelativeTo(null);
             
         if(!isEditing){

             empresasCombo.setSelectedItem(empresasCombo.getItemCount()-1);
             empresasCombo.setSelectedItem(empresas.get(empresas.size()-1));

         }
         if(isEditing){
                 empresasCombo.setSelectedItem(getEmpresa(info.getNombreEmpresa()));
                 nombreT.setText(info.getNombreContacto());
                 cargoT.setText(info.getCargo());
                 direccionT.setText(info.getDireccion());
                 indPrincipalT.setText(info.getIndicativo());
                 telefonoT.setText(info.getTelefono());
                 extensionT.setText(info.getExt());
                 indAuxT.setText(info.getIndicativo_aux());
                 telefonoAuxT.setText(info.getTelefono_opcional());
                 extAuxT.setText(info.getExt_opcional());
                 celularT.setText(info.getCelular());
                 celAuxT.setText(info.getCelular_opcional());
                 mailT.setText(info.getMail());
                 observacionesT.setText(info.getObservaciones());
                

         }
    }

    private InfoEmpresa getEmpresa(String nombre){
        for(InfoEmpresa emp: empresas){
            if(emp.getNombre().equals(nombre)){
                return emp;
            }
        }
        return null;
    }

    public boolean check(){

        if(nombreT.getText().trim().isEmpty()){//Nombre
            nomM.setVisible(true);

        }else{
            nomM.setVisible(false);
        }
        if(cargoT.getText().trim().isEmpty()){//cargo
            cargoM.setVisible(true);

        }else{
            cargoM.setVisible(false);
        }
         if(indPrincipalT.getText().trim().isEmpty()){//indicativo
            telM.setVisible(true);

        }else{

          telM.setVisible(false);

        }
          if(telefonoT.getText().trim().isEmpty()){//Telèfono
            telM.setVisible(true);
        }else{
            telM.setVisible(false);
        }

        if(extensionT.getText().trim().isEmpty()){//Extensión
            if(!opcionL.getText().isEmpty()){
                telM.setVisible(false);
            }else{
                telM.setVisible(true);
            }
        }else{
            telM.setVisible(false);
        }

         if(!opcionL.getText().isEmpty()){//Opción
            telM.setVisible(false);
        }else{
            if(extensionT.getText().trim().isEmpty()){
                    telM.setVisible(true);
            }else{
                telM.setVisible(false);
            }
        }

        if(celularT.getText().trim().isEmpty()){//Celular
            celM.setVisible(true);

        }else{
            celM.setVisible(false);
        }

         if(!validateEmail(mailT.getText().trim())){//mail
                    mailM.setVisible(true);
                }else{
                    mailM.setVisible(false);
                }

        if(direccionT.getText().trim().isEmpty()){//Direccion
            dirM.setVisible(true);

        }else{
            dirM.setVisible(false);
        }
        
        


       if(empM.isVisible() || nomM.isVisible() || cargoM.isVisible() || telM.isVisible()
               ||  celM.isVisible() ||  mailM.isVisible() || dirM.isVisible() ){
           return false;
       }

       

       return true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        container1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        empresasCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        direccionT = new javax.swing.JTextField();
        celAuxT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observacionesT = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancelar = new javax.swing.JButton();
        nombreT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        extAuxT = new javax.swing.JTextField();
        telefonoT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        mailT = new javax.swing.JTextField();
        extensionT = new javax.swing.JTextField();
        celularT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        indAuxT = new javax.swing.JTextField();
        telefonoAuxT = new javax.swing.JTextField();
        indPrincipalT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cargoT = new javax.swing.JTextField();
        empM = new javax.swing.JLabel();
        nomM = new javax.swing.JLabel();
        cargoM = new javax.swing.JLabel();
        dirM = new javax.swing.JLabel();
        telM = new javax.swing.JLabel();
        celM = new javax.swing.JLabel();
        mailM = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        opcionL = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nuevo contacto");
        setResizable(false);

        container1.setBorder(javax.swing.BorderFactory.createMatteBorder(30, 30, 30, 30, new java.awt.Color(204, 204, 204)));
        container1.setPreferredSize(new java.awt.Dimension(550, 600));
        container1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contacto", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 600));
        jPanel2.setRequestFocusEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Observaciones:");

        empresasCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        empresasCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresasComboActionPerformed(evt);
            }
        });

        jLabel4.setText("Ext:");

        //direccionT.setDocument(new LettersNumbersSpecialCharacterAndLength(200));
        direccionT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                direccionTFocusLost(evt);
            }
        });

        //celAuxT.setDocument( new JustLettersAndLenght(10));
        celAuxT.setColumns(10);

        jLabel13.setText("Ext:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel10.setText("Empresa:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Dirección:");

        observacionesT.setColumns(20);
        observacionesT.setLineWrap(true);
        observacionesT.setRows(5);
        observacionesT.setWrapStyleWord(true);
        jScrollPane1.setViewportView(observacionesT);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        save.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        save.setText("Guardar");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel1.add(save);

        cancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cancelar.setText("Cancelar");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jPanel1.add(cancelar);

        //nombreT.setDocument(new JustLettersAndLenght(50));
        nombreT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nombreTFocusLost(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Celular Auxiliar:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Cargo:");

        //extAuxT.setDocument(new LettersNumbersAndLenght(4));
        extAuxT.setColumns(4);

        //telefonoT.setDocument(new JustNumberAndLenght(7));
        telefonoT.setColumns(7);
        telefonoT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonoTActionPerformed(evt);
            }
        });
        telefonoT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                telefonoTFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Teléfono Principal:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Teléfono Auxiliar:");

        mailT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                mailTFocusLost(evt);
            }
        });

        //extensionT.setDocument(new JustNumberAndLenght(4));
        extensionT.setColumns(4);
        extensionT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                extensionTFocusLost(evt);
            }
        });

        //celularT.setDocument(new JustNumberAndLenght(10));
        celularT.setColumns(10);
        celularT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                celularTFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Mail:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Celular Principal:   ");

        //indAuxT.setDocument(new JustNumberAndLenght(4));
        indAuxT.setColumns(3);

        //telefonoAuxT.setDocument(new JustNumberAndLenght(7));
        telefonoAuxT.setColumns(7);

        //indPrincipalT.setDocument(new JustNumberAndLenght(4));
        indPrincipalT.setColumns(3);
        indPrincipalT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                indPrincipalTFocusLost(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Nombre:");

        //cargoT.setDocument(new JustLettersAndLenght(30));
        cargoT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cargoTFocusLost(evt);
            }
        });

        empM.setVisible(false);
        empM.setFont(new java.awt.Font("Tahoma", 1, 11));
        empM.setForeground(new java.awt.Color(255, 0, 0));
        empM.setText("*");

        nomM.setFont(new java.awt.Font("Tahoma", 1, 11));
        nomM.setForeground(new java.awt.Color(255, 0, 0));
        nomM.setText("*");
        nomM.setVisible(false);

        cargoM.setFont(new java.awt.Font("Tahoma", 1, 11));
        cargoM.setForeground(new java.awt.Color(255, 0, 0));
        cargoM.setText("*");
        cargoM.setVisible(false);

        dirM.setFont(new java.awt.Font("Tahoma", 1, 11));
        dirM.setForeground(new java.awt.Color(255, 0, 0));
        dirM.setText("*");
        dirM.setVisible(false);

        telM.setFont(new java.awt.Font("Tahoma", 1, 11));
        telM.setForeground(new java.awt.Color(255, 0, 0));
        telM.setText("*");
        telM.setVisible(false);

        celM.setFont(new java.awt.Font("Tahoma", 1, 11));
        celM.setForeground(new java.awt.Color(255, 0, 0));
        celM.setText("*");
        celM.setVisible(false);

        mailM.setFont(new java.awt.Font("Tahoma", 1, 11));
        mailM.setForeground(new java.awt.Color(255, 0, 0));
        mailM.setText("* Mail incorrecto");
        mailM.setVisible(false);

        jLabel16.setText("Opción:");

        //opcionL.setDocument(new JustLettersAndLenght(2));
        opcionL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                opcionLFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)))
                        .addGap(1582, 1582, 1582))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel11))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(indAuxT)
                                        .addComponent(indPrincipalT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(telefonoAuxT)
                                        .addComponent(telefonoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(extensionT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(4, 4, 4)
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(opcionL, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(telM, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(extAuxT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(50, 50, 50))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel12))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(celularT)
                                                        .addComponent(celAuxT, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(celM, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(mailT, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(direccionT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))))
                                        .addComponent(jLabel2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mailM)
                                        .addComponent(dirM))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel1))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(empresasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(empM))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(cargoT)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(cargoM))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(nombreT, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(nomM))))))
                        .addGap(1495, 1495, 1495))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(empresasCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cargoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cargoM))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(indPrincipalT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefonoT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(extensionT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(opcionL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(indAuxT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(telefonoAuxT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(extAuxT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(celularT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(celM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(celAuxT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(mailT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mailM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(dirM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        container1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(container1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_cancelarActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
        if(check()){
                InfoEmpresa empresa = (InfoEmpresa)empresasCombo.getSelectedItem();

                String stringSelected = empresa.getNombre();
                int pk = empresa.getId_empresa();

                if(!isEditing){

                    info = new Contacto();
                    String opcion = opcionL.getText().trim();

                   
                            if(con.insertUser(nombreT.getText().trim(),direccionT.getText().trim(),
                                    telefonoT.getText().trim(),extensionT.getText().trim(),
                                    celularT.getText().trim(),cargoT.getText().trim(),
                                    observacionesT.getText().trim(),mailT.getText().trim(),telefonoAuxT.getText().trim(),
                                    celAuxT.getText().trim(),indPrincipalT.getText().trim(),indAuxT.getText().trim(),
                                    extAuxT.getText().trim(),pk, opcion)){

                                info.setNombreContacto(nombreT.getText().trim());
                                info.setDireccion(direccionT.getText().trim());
                                    info.setTelefono(telefonoT.getText().trim());
                                    info.setExt(extensionT.getText().trim());
                                    info.setCelular(celularT.getText().trim());
                                    info.setCargo(cargoT.getText().trim());
                                    info.setObservaciones(observacionesT.getText().trim());
                                    info.setMail(mailT.getText().trim());
                                    info.setTelefono_opcional(telefonoAuxT.getText().trim());
                                    info.setCelular_opcional(celAuxT.getText().trim());
                                    info.setIndicativo(indPrincipalT.getText().trim());
                                    info.setIndicativo_aux(indAuxT.getText().trim());
                                    info.setExt_opcional(extAuxT.getText().trim());
                                    info.setPK(pk);

                                   mw.updateEmpresasTipos();
                                dispose();
                            }else{
                                JOptionPane.showMessageDialog(this, "Upss error al guardar, por favor inténtalo nuevamente.");
                            }
                   
                }else{
                    String opcion = opcionL.getText().trim();
                    
                        if(con.editarUsuario(info.getPK(), nombreT.getText().trim(), direccionT.getText().trim(),
                                telefonoT.getText().trim(), extensionT.getText().trim(), celularT.getText().trim(),
                                cargoT.getText().trim(),
                                  observacionesT.getText().trim(), mailT.getText().trim(), indPrincipalT.getText().trim(),
                                  indAuxT.getText().trim(),
                                  telefonoAuxT.getText().trim(), extAuxT.getText().trim(), celAuxT.getText().trim(),opcion)){

                              info.setTelefono(telefonoT.getText().trim());
                                info.setExt(extensionT.getText().trim());
                                info.setCelular(celularT.getText().trim());
                                info.setCargo(cargoT.getText().trim());
                                info.setObservaciones(observacionesT.getText().trim());
                                info.setMail(mailT.getText().trim());
                                info.setTelefono_opcional(telefonoAuxT.getText().trim());
                                info.setCelular_opcional(celAuxT.getText().trim());
                                info.setIndicativo(indPrincipalT.getText().trim());
                                info.setIndicativo_aux(indAuxT.getText().trim());
                                info.setExt_opcional(extAuxT.getText().trim());
                                info.setPK(pk);
                            dispose();
                        }else{
                             JOptionPane.showMessageDialog(this, "Upss error al guardar, por favor inténtalo nuevamente.");
                        }

                    
                }
        }
}//GEN-LAST:event_saveActionPerformed

    private void telefonoTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonoTActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_telefonoTActionPerformed

    private void empresasComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresasComboActionPerformed
        // TODO add your handling code here:
        String selected = String.valueOf(empresasCombo.getSelectedItem());
        if(selected.equals("--")){
            empM.setVisible(true);
            return;
        }
       
        empM.setVisible(false);
        InfoEmpresa empresa = empresas.get(empresasCombo.getSelectedIndex());
        direccionT.setText(empresa.getDireccion());
        indPrincipalT.setText(empresa.getIndicativo());
        telefonoT.setText(empresa.getTelefono());
        
        

    }//GEN-LAST:event_empresasComboActionPerformed

    private void nombreTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nombreTFocusLost
        // TODO add your handling code here:
        if(nombreT.getText().trim().isEmpty()){
            nomM.setVisible(true);

        }else{
            nomM.setVisible(false);
        }
        
    }//GEN-LAST:event_nombreTFocusLost


    private void cargoTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cargoTFocusLost
        // TODO add your handling code here:
        if(cargoT.getText().trim().isEmpty()){
            cargoM.setVisible(true);

        }else{
            cargoM.setVisible(false);
        }
    }//GEN-LAST:event_cargoTFocusLost

    private void direccionTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_direccionTFocusLost
        // TODO add your handling code here:
        if(direccionT.getText().trim().isEmpty()){
            dirM.setVisible(true);

        }else{
            dirM.setVisible(false);
        }
    }//GEN-LAST:event_direccionTFocusLost

    private void indPrincipalTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_indPrincipalTFocusLost
        // TODO add your handling code here:
        if(indPrincipalT.getText().trim().isEmpty()){
            telM.setVisible(true);

        }else{

          telM.setVisible(false);

        }
    }//GEN-LAST:event_indPrincipalTFocusLost

    private void telefonoTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_telefonoTFocusLost
        // TODO add your handling code here:
        if(telefonoT.getText().trim().isEmpty()){
            telM.setVisible(true);
        }else{
            telM.setVisible(false);
        }
    }//GEN-LAST:event_telefonoTFocusLost

    private void extensionTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_extensionTFocusLost
        // TODO add your handling code here:
        if(extensionT.getText().trim().isEmpty()){
            if(!opcionL.getText().isEmpty()){
                telM.setVisible(false);
            }else{
                telM.setVisible(true);
            }
        }else{
            telM.setVisible(false);
        }
    }//GEN-LAST:event_extensionTFocusLost

    private void celularTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_celularTFocusLost
        // TODO add your handling code here:
        if(celularT.getText().trim().isEmpty()){
            celM.setVisible(true);

        }else{
            celM.setVisible(false);
        }
    }//GEN-LAST:event_celularTFocusLost

    private void mailTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mailTFocusLost
        // TODO add your handling code here:
          
                if(!validateEmail(mailT.getText().trim())){
                    mailM.setVisible(true);
                }else{
                    mailM.setVisible(false);
                }
            
    }//GEN-LAST:event_mailTFocusLost

    private void opcionLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_opcionLFocusLost
        // TODO add your handling code here:
        if(!opcionL.getText().isEmpty()){
            telM.setVisible(false);
        }else{
            if(extensionT.getText().trim().isEmpty()){
                    telM.setVisible(true);
            }else{
                telM.setVisible(false);
            }
        }

     
    }//GEN-LAST:event_opcionLFocusLost

    private boolean finalValidate(){
        nombreT.requestFocus();
        cargoT.requestFocus();
        direccionT.requestFocus();
        indPrincipalT.requestFocus();
        telefonoT.requestFocus();
        extensionT.requestFocus();
        celularT.requestFocus();
        mailT.requestFocus();
        empresasCombo.requestFocus();
        if(nomM.isVisible()||cargoM.isVisible()||dirM.isVisible()||telM.isVisible()||mailM.isVisible()){
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email){
             Pattern p = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");//me gusta esta
            Matcher m = p.matcher(email);
        return m.matches();
    }
    private boolean containsNumericChar(JTextField text){
        char[] array = text.getText().toCharArray();  
        for(int i=0;i<array.length;i++){
            if(isInteger(array[i])){
                return true;
            }
        }
        return false;
    }

    private boolean onlyNumbers(JTextField text){
        String t = text.getText().trim();
        try{
            Integer.parseInt(t);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    private boolean isInteger(char a){
        try{
            Integer.parseInt(String.valueOf(a));
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

   

    /**
    * @param args the command line arguments
    */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelar;
    private javax.swing.JLabel cargoM;
    private javax.swing.JTextField cargoT;
    private javax.swing.JTextField celAuxT;
    private javax.swing.JLabel celM;
    private javax.swing.JTextField celularT;
    private javax.swing.JPanel container1;
    private javax.swing.JLabel dirM;
    private javax.swing.JTextField direccionT;
    private javax.swing.JLabel empM;
    private javax.swing.JComboBox empresasCombo;
    private javax.swing.JTextField extAuxT;
    private javax.swing.JTextField extensionT;
    private javax.swing.JTextField indAuxT;
    private javax.swing.JTextField indPrincipalT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mailM;
    private javax.swing.JTextField mailT;
    private javax.swing.JLabel nomM;
    private javax.swing.JTextField nombreT;
    private javax.swing.JTextArea observacionesT;
    private javax.swing.JTextField opcionL;
    private javax.swing.JButton save;
    private javax.swing.JLabel telM;
    private javax.swing.JTextField telefonoAuxT;
    private javax.swing.JTextField telefonoT;
    // End of variables declaration//GEN-END:variables

}
