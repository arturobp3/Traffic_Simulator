package mvc.vistaGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;
import mvc.vistaGUI.barras.BarraMenu;
import mvc.vistaGUI.barras.PanelBarraEstado;
import mvc.vistaGUI.barras.ToolBar;
import mvc.vistaGUI.diagInformes.DialogoInformes;
import mvc.vistaGUI.panelInferior.ComponenteMapa;
import mvc.vistaGUI.panelSuperior.PanelAreaTexto;
import mvc.vistaGUI.panelSuperior.PanelEditorEventos;
import mvc.vistaGUI.panelSuperior.PanelInformes;
import mvc.vistaGUI.tablas.ModeloTablaCarreteras;
import mvc.vistaGUI.tablas.ModeloTablaCruces;
import mvc.vistaGUI.tablas.ModeloTablaEventos;
import mvc.vistaGUI.tablas.ModeloTablaVehiculos;
import mvc.vistaGUI.tablas.PanelTabla;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico {

// ATRIBUTOS:
	
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);
	
	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
	private PanelAreaTexto panelEditorEventos;
	private PanelAreaTexto panelInformes;
	private PanelTabla<Evento> panelColaEventos;
	// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;
	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;
	// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;

	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Vel.", "Km", "Tiempo. Ave.", "Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen", "Destino", "Longitud", "Vel. Max", "Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;
	// REPORT DIALOG
	private DialogoInformes dialogoInformes; // opcional
	// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;
	private Controlador controlador;
	
// CONSTRUCTORA:
	
	public VentanaPrincipal(String ficheroEntrada, Controlador ctrl) throws FileNotFoundException {

		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		this.initGUI();
		// Añadimos la ventana principal como observadora
		ctrl.addObserver(this);
		
		if (ficheroEntrada != null) {
			String s = leeFichero(ficheroActual);
			this.panelEditorEventos.setTexto(s);
			this.panelEditorEventos.setBorde("Eventos: " + this.ficheroActual.getName());
			this.panelBarraEstado.setMensaje("Fichero " + ficheroActual.getName() + " de eventos cargado en el editor");
		}
	}
	

	private void initGUI() {
		
		//Permite poner la apariencia a la ventana del sistema operativo en el que se ejecute el programa
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//------------------------------------------------------------------------
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocation(100, 0);
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {
			}
			
		});
		
		// PANEL PRINCIPAL
		JPanel panelPrincipal = this.creaPanelPrincipal();
		this.setContentPane(panelPrincipal);
		
		// BARRA DE ESTADO INFERIOR
		// (contiene una JLabel para mostrar el estado del simulador)
		this.addBarraEstado(panelPrincipal);
		
		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		
		// PANEL SUPERIOR
		this.createPanelSuperior(panelCentral);
		
		
		// MENU (Mencionado en Diap. 39)
		BarraMenu menubar = new BarraMenu(this, this.controlador);
		this.setJMenuBar(menubar);

		// PANEL INFERIOR
		this.createPanelInferior(panelCentral);
		
		// FILE CHOOSER
		this.fc = new JFileChooser();
		
		// BARRA DE HERRAMIENTAS
		this.addToolBar(panelPrincipal); 
	
		// REPORT DIALOG (OPCIONAL)
		this.dialogoInformes = new DialogoInformes(this, this.controlador);

		this.pack();
		this.setVisible(true);
	}
	

// METODOS:
	
	private JPanel creaPanelPrincipal() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		return panelPrincipal;
	}
	
	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador", this.controlador);
		panelPrincipal.add(this.panelBarraEstado, BorderLayout.PAGE_END);
	}
	
	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		// Para colocar el panel superior e inferior
		panelCentral.setLayout(new GridLayout(2,1));
		return panelCentral;
	}
	
	private void createPanelSuperior(JPanel panelCentral) {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
		
		this.panelEditorEventos = new PanelEditorEventos("Editor de Eventos:", null, true, this);
		panelSuperior.add(panelEditorEventos);
		
		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ", new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		panelSuperior.add(panelColaEventos);
		
		this.panelInformes = new PanelInformes("Informes: ", false, this.controlador);
		panelSuperior.add(panelInformes);

		panelCentral.add(panelSuperior);
	}

	private void createPanelInferior(JPanel panelCentral) {
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
		
		JPanel tablas = new JPanel();
		tablas.setLayout(new GridLayout(3,1));
		
		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos", new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));
		tablas.add(panelVehiculos);
		
		this.panelCarreteras = new PanelTabla<Carretera>("Carreteras", new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera,this.controlador));
		tablas.add(panelCarreteras);
		
		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces", new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));
		tablas.add(panelCruces);
		
		panelInferior.add(tablas); // Añadimos las tres tablas al panel
		
		this.componenteMapa = new ComponenteMapa(this.controlador);
		// Añadir un ScrollPane al panel inferior donde se coloca la componente.
		panelInferior.add(new JScrollPane(componenteMapa));
		
		panelCentral.add(panelInferior);
	}
	
	private void addToolBar(JPanel panelPrincipal) {
		this.toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);
	}


// Metodos implementadores de ObservadorSimuladorTrafico:
	
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		this.muestraDialogoError("Ha ocurrido un error en la simulacion");
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		JOptionPane.showMessageDialog(this, "Se ha añadido un evento", "Eventos", JOptionPane.INFORMATION_MESSAGE, null);
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.panelEditorEventos.setBorde("Eventos: ");
		JOptionPane.showMessageDialog(this, "Se ha reiniciado el simulador", "Estado del simulador", JOptionPane.INFORMATION_MESSAGE, null);
	}

// Metodos utilizados en otras clases de GUI:
	
 // Utilizados en BarraMenu
	public void cargaFichero() {
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			try {
				String s = leeFichero(fichero);
				this.controlador.reinicia();
				this.ficheroActual = fichero;
				this.panelEditorEventos.setTexto(s);
				this.panelEditorEventos.setBorde("Eventos: " + this.ficheroActual.getName());
				this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() + " de eventos cargado en el editor");
			}
			catch (FileNotFoundException e) {
				this.muestraDialogoError("Error durante la lectura del fichero: " + fichero.getName());
			}
		}
	}
	
	public void guardaEvento() {
		
		int returnVal = this.fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			this.escribeFichero(fichero, this.getTextoEditorEventos());
			this.ficheroActual = fichero;
			this.panelBarraEstado.setMensaje("Eventos guardados en el fichero " + fichero.getName());
			this.panelEditorEventos.setBorde("Eventos: " + this.ficheroActual.getName());

		}
	}
	
	public void salvarInformes() {
		
		int returnVal = this.fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			this.escribeFichero(fichero, this.panelInformes.getTexto());
			this.panelBarraEstado.setMensaje("Informes guardados en el fichero " + fichero.getName());
		}
	}
	
	private void escribeFichero(File fich, String text) {
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(fich);
            pw = new PrintWriter(fichero);
            
            pw.print(text);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public void salir() {
		
		// Al salir pide confirmación:
		int n = JOptionPane.showOptionDialog(new JFrame(), "¿Seguro que quieres salir?", "Quit", 
				 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) { System.exit(0); }
	}
	
	public void limpiarInformes() {
		this.panelInformes.limpiar();
	}

	public int getSteps() {
		return this.toolbar.getSteps();
	}
	
	public int getTime(){
		return this.toolbar.getTime();
	}

	
	public void muestraDialogoError(String string) {
		JOptionPane.showMessageDialog(this, string, "Error", JOptionPane.ERROR_MESSAGE, null);
	}
	
	public void muestraDialogoAdvertencia(String string) {
		JOptionPane.showMessageDialog(this, string, "Warning", JOptionPane.WARNING_MESSAGE, null);
	}

	// Este metodo lo llama cargaFichero(), y a este lo llama creaMenuFicheros() de BarraMenu
	private String leeFichero(File fichero) {
		
		String lectura = "";
        try {
        	String palabra;
            FileReader f = new FileReader(fichero);
            BufferedReader b = new BufferedReader(f);
            while((palabra = b.readLine()) != null) {
                lectura += palabra + '\n';
            }
            b.close();
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
        
		return lectura;
	}

	public void generaInformes(String report) {
		this.panelInformes.setTexto(report);
	}

	//Añadido por la clase PopUpMenu
	public void inserta(String text){
		String editor = this.getTextoEditorEventos();
		
		if(editor.equals("")){
			this.panelEditorEventos.setTexto(editor + text);
		}
		else{
			this.panelEditorEventos.setTexto(editor + "\n" + text);
		}
	}
	
	// Añadido por clase ToolBar
	public String getTextoEditorEventos() {
		return this.panelEditorEventos.getTexto();
	}

	// Añadido por ToolBar
	public void setMensaje(String mensaje) {
		this.panelBarraEstado.setMensaje(mensaje);
		
	}

	// Usado por ToolBar
	public void limpiarEditor() {
		this.panelEditorEventos.limpiar();
	}
	
	//Usado por el JDialog
	
	public void abrirDialogoInformes(){
		this.dialogoInformes.abrir();
	}
}
