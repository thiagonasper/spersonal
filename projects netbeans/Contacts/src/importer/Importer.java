package importer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author INTAPSA
 */
public class Importer {
  /*  private JFileChooser fileChooser;
    private File file;
    private Workbook doc;
    private Sheet sheet;
    private HashSet<String> tiposContactoHallados;
    private HashSet<String> empresasHalladas;
    private Log log;
     WorkbookSettings w
             = new WorkbookSettings();
     private DbConnectionBroker pool;
     private String lastTipo;
   

    public Importer() {

        DBHandler.initialize();
        pool = DBHandler.getPool();
        try {
            log = new Log("C:/essay.txt");
        } catch (IOException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        }
        tiposContactoHallados = new HashSet<String>();
        empresasHalladas = new HashSet<String>();
        DBHandler.initialize();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fileChooser = new JFileChooser(new File("C:/Documents and Settings/INTAPSA/Mis Documentos/Descargas"));
        int option =0;
        option = fileChooser.showOpenDialog(null);
        switch(option){
            case JFileChooser.APPROVE_OPTION:
                file = fileChooser.getSelectedFile();
        
           
            w.setCharacterSet(3);
           //w.getCharacterSet()setCharacterSet(option);
                    try {
                        doc = Workbook.getWorkbook(file,w);
                    } catch (IOException ex) {
                        Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (BiffException ex) {
                        Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
                    }
   
                break;
            case JFileChooser.CANCEL_OPTION:
                System.exit(0);

                break;

        }
        exe();
    }

    public void exe(){
        
        if(doc != null){
            sheet = doc.getSheet("Hoja2");
            if(sheet == null)
                JOptionPane.showMessageDialog(null, "la hoja no existe");

            Cell cell;

            String tm = new String();
            for(int j=2; j<26;j++){
                for(int i =0; i<11;i++){
                    cell = sheet.getCell(i, j);
                    String tmp = cell.getContents().trim();
                   if(i==5 || i==6 ){
                       Tupla t = new  Tupla(tmp, "-");
                       tm = tm+"["+t.getFirst();
                       tm = tm+"["+t.getSecond();
                       continue;
                   }
                    if(tmp.isEmpty())
                        tmp = "nothing";

                    tm = tm+"["+tmp;
                }
                tm = tm.substring(1);
                populate(tm);
                //System.out.println(tm);
                log.appendInNewLine(tm);
                tm = new String();
            }
        
            doc.close();
            log.finalize();
  
        }

    }

    private int getPKparaTipo(String tipo){
        try {
            String query = "select id_proveedor from contactos_intap.proveedores where detalle = '" + tipo + "';";
            Connection con = pool.getConnection();
            java.sql.PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            rs.first();
            int PK = rs.getInt("id_proveedor");
            pool.freeConnection(con);
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    private int getPKparaEmpresa(String empresa){
        try {
            String query = "select id_empresa from contactos_intap.empresa where nombre = '" + empresa + "';";
            Connection con = pool.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            rs.first();
            int PK = rs.getInt("id_empresa");
            pool.freeConnection(con);
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }

    private int getPKparaContacto(String contacto){
        try {
            String query = "select id_contacto from contactos_intap.contacto where nombre_contacto = '" + contacto + "';";
            Connection con = pool.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
             rs.first();
            int PK = rs.getInt("id_contacto");
            return PK;
        } catch (SQLException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }


    }

    private boolean insert(String query){
        Connection con = pool.getConnection();
        try {
            
            PreparedStatement stm = con.prepareStatement(query);
            stm.executeUpdate();
            pool.freeConnection(con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Importer.class.getName()).log(Level.SEVERE, null, ex);
            pool.freeConnection(con);
            return  false;
        }
    }





    private void populate(String at){

        final String sqlInsertEmpresa = "insert into contactos_intap.empresa(direccion,telefono,id_tipo,nombre,fax,id_proveedor) values(";
        final String sqlInsertCliente = "insert into contactos_intap.proveedores(detalle) values(";
        final String sqlInsertContacto = "insert into contactos_intap.contacto(nombre_contacto,direccion,telefono,ext," +
                    "celular,cargo,mail,observaciones,id_empresa,opcion) values(";

        StringTokenizer st = new StringTokenizer(at,"[");
        String nombre,cedula, ubicacion, telefono_oficina, extension,celular, telefono_casa, fecha_nacimiento,
                fecha_ingreso, direccion,barrio, ext_auxiliar = new String("0"), opcion = new String("0"), cel_aux,indica_ofi = new String("0"), indica_casa = new String("0"),tel_aux, indica_casa_aux = new String("0");
        
        nombre = st.nextToken().trim().replace("'", "\"");
        cedula=st.nextToken().trim().replace(",", "");
        ubicacion=st.nextToken().trim().replace("'", "\"");
        telefono_oficina = st.nextToken().trim().replace("'", "\"");
        extension= st.nextToken().trim().replace("'", "\"");
        celular= st.nextToken().trim().replace("'", "\"");
        cel_aux= st.nextToken().trim().replace("nothing", "0");
        telefono_casa= st.nextToken().trim().replace("'", "\"");
        tel_aux= st.nextToken().trim().replace("nothing", "0");
        fecha_nacimiento= st.nextToken().trim().replace("'", "\"");
        fecha_ingreso= st.nextToken().trim().replace("'", "\"");
        direccion= st.nextToken().trim().replace("'", "\"");
        barrio= st.nextToken().trim().replace("'", "\"");

insertEmpleado(nombre, cedula, ubicacion, telefono_oficina, extension, celular, telefono_casa, fecha_nacimiento, fecha_ingreso, direccion, barrio, ext_auxiliar, opcion, cel_aux, indica_ofi, indica_casa, tel_aux, indica_casa_aux);

     
    }

    private void insertEmpleado(String nombre,String cedula, String ubicacion, String telefono_oficina, String extension,String celular, String telefono_casa, String fecha_nacimiento,
                String fecha_ingreso, String direccion,String barrio,String opcion, String cel_aux,String indica_ofi,String indica_casa,String tel_aux, String indica_casa_aux, String mail){
        String query = "insert into contactos_intap.empleados(nombre_empleado, cedula , ubicacion, telefono_oficina" +
                ",extension, celular, telefono_casa, fecha_nacimiento, fecha_ingreso, direccion_residencia, " +
                "barrio, opcion, celular_aux, indicativo_ofi, indicativo_casa, telefono_casa_aux," +
                "indicativo_casa_aux,mail) values('"+nombre+"',"+cedula+",'"+ubicacion+"',"+telefono_oficina+"," +
                extension+","+celular+","+telefono_casa+",'"+fecha_nacimiento+"','"+fecha_ingreso+"','" +
                direccion+"','"+barrio+"',"+opcion+","+(cel_aux.equals("nothing")?new String("0"):cel_aux)+","+indica_ofi+","+indica_casa+","+(tel_aux.equals("nothing")?new String("0"):tel_aux)+","+indica_casa_aux+",'"+mail+"')";
System.out.println(query);
        //return insert(query);
    }


    public static void main(String[] args) {
        new Importer();
        System.exit(0);
    }


}

class Tupla{
    String first, second;
    String input;
    StringTokenizer st;
    String delimiter;

    public Tupla(String input, String delimiter) {
        this.input = input;
        this.delimiter = delimiter;
        st = new StringTokenizer(input, delimiter);
         try{
            first = st.nextToken();
        }catch(NoSuchElementException ex){
            first = "nothing";
        }
        try{
            second = st.nextToken();
        }catch(NoSuchElementException ex){
            second = "nothing";
        }
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
*/
}

