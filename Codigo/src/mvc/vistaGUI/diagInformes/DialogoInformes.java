package mvc.vistaGUI.diagInformes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;
import mvc.vistaGUI.VentanaPrincipal;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico {

// ATRIBUTOS:
	
	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;
	//...
	public static final char TECLALIMPIAR = 99; // Añadido por PanelObjSim, Letra "c"
	public static final char TECLA_SELECCIONAR_TODO = 97; //Letra "a" 
	
// CONSTRUCTORA:
	
	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		controlador.addObserver(this);
		this.initGUI(ventanaPrincipal, controlador);
		
	}


	private void initGUI(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());
		
		this.setTitle("Generar reportes");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLocation(450, 150);
		
		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");
		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");
		
		this.panelBotones = new PanelBotones(this, ventanaPrincipal, controlador);
		InformationPanel panelInfo = new InformationPanel();
		
		JPanel panelCentral = this.creaPanelCentral();
		
		panelPrincipal.add(panelInfo, BorderLayout.PAGE_START);
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);
		panelPrincipal.add(panelBotones, BorderLayout.PAGE_END);
		
		this.add(panelPrincipal);
		this.pack();
		this.setVisible(false);
	}
	

// METODOS:
	
	private JPanel creaPanelCentral(){
		
		JPanel principal = new JPanel();
		GridLayout layout = new GridLayout(1,3);
		layout.setHgap(8);
		principal.setLayout(layout);
		
		principal.add(this.panelVehiculos);
		principal.add(this.panelCarreteras);
		principal.add(this.panelCruces);

		return principal;
	}
	
	
	public void abrir() { this.setVisible(true); }
	
	public void cerrar(){ this.setVisible(false);}
	
	
	private void setMapa(MapaCarreteras mapa) {
		this.panelVehiculos.setList(mapa.getVehiculos());
		this.panelCarreteras.setList(mapa.getCarreteras());
		this.panelCruces.setList(mapa.getCruces());
	}
	
	
	public List<Vehiculo> getVehiculosSeleccionados() {
		return this.panelVehiculos.getSelectedItems();
	}
	
	
	public List<Carretera> getCarreterasSeleccionadas() {
		return this.panelCarreteras.getSelectedItems();
	}
	
	
	public List<CruceGenerico<?>> getCrucesSeleccionados() {
		return this.panelCruces.getSelectedItems();
	}
	
	public boolean panelesEmpty(){
		return (panelVehiculos.isEmpty() && panelCarreteras.isEmpty() && panelCruces.isEmpty());
	}
	
	
	
	// Metodos notificadores
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setMapa(mapa);
			}
		});
		
	}
	
	
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setMapa(mapa);
			}
		});
	}
	
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				setMapa(mapa);
			}
		});
	}


	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		
	}
}
