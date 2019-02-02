package mvc.vistaGUI.barras;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.vistaGUI.VentanaPrincipal;

@SuppressWarnings("serial")
public class BarraMenu extends JMenuBar {
	
	private JMenu menuFicheros, menuSimulador, menuReport;
	

	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		// MANEJO DE FICHEROS
		menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);
		// SIMULADOR
		menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, controlador, mainWindow);
		// INFORMES
		menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow, controlador);
		
	}
	
	private void creaMenuFicheros(JMenu menu, VentanaPrincipal mainWindow) {
		// Opcion cargar:
		JMenuItem cargar = new JMenuItem("Cargar Eventos");
		cargar.setMnemonic(KeyEvent.VK_C);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		
		// Opcion salvar:
		JMenuItem salvar = new JMenuItem("Guardar Eventos");
		salvar.setMnemonic(KeyEvent.VK_G);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaEvento();
			}
		});
		
		// Opcion salvarInformes:
		JMenuItem salvarInformes = new JMenuItem("Guardar Informes");
		salvarInformes.setMnemonic(KeyEvent.VK_I);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvarInformes();
			}
		});
		
		// Opcion salir:
		JMenuItem salir = new JMenuItem("Salir");
		salir.setMnemonic(KeyEvent.VK_S);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salir();
			}
		});
		
		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(salvarInformes);
		menu.addSeparator();
		menu.add(salir);
	}
	
	private void creaMenuSimulador(JMenu menuSimulador, Controlador controlador, VentanaPrincipal mainWindow) {
		// Opcion ejecutar simulacion:
		JMenuItem ejecuta = new JMenuItem("Ejecutar");
		ejecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*if (mainWindow.getHebra() == null){
					
					mainWindow.crearHebra();
					mainWindow.start();
					mainWindow.deshabilitaInterfaz();
				}*/
				try {
					int pasos = mainWindow.getSteps();
					controlador.ejecuta(pasos);
				} catch (ErrorDeSimulacion | IOException e1) {
					mainWindow.muestraDialogoError("Ha ocurrido un error en la ejecucion");
				}
			}
		});
		
		// Opcion reiniciar simulacion:
		JMenuItem reinicia = new JMenuItem("Reiniciar");
		reinicia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Opcion insertar evento en la simulacion:
		JMenuItem insertarEvento = new JMenuItem("Insertar Evento");
		insertarEvento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Enunciado P5, pag.3. "Interactuando con el simulador"
				String s = mainWindow.getTextoEditorEventos(); // Obtenemos un string del texto que hay en el panel
				InputStream is = new ByteArrayInputStream(s.getBytes()); // Lo transformamos
				try {
					controlador.reinicia();
					controlador.cargaEventos(is); // Cargamos el evento
					
				} catch (ErrorDeSimulacion | FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		JCheckBoxMenuItem redirigir = new JCheckBoxMenuItem("Redirigir Salida");
		redirigir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(redirigir.getState() == true){
					controlador.redirigeSalida(mainWindow);
				}
				else{
					controlador.corrigeSalida();
				}
			}
		});
		
		menuSimulador.add(insertarEvento);
		menuSimulador.add(ejecuta);
		menuSimulador.add(reinicia);
		menuSimulador.addSeparator();
		menuSimulador.add(redirigir);
	}
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow, Controlador controlador) {
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// OPCIONAL
				mainWindow.abrirDialogoInformes();
			}
		});
		menuReport.add(generaInformes);
		JMenuItem limpiaAreaInformes = new JMenuItem("Clear");
		limpiaAreaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarInformes();
			}
		});
		menuReport.add(limpiaAreaInformes);
	}
	
	
	public void disableMenu(){
		menuFicheros.setEnabled(false);
		menuSimulador.setEnabled(false);
		menuReport.setEnabled(false);
	}
	
	public void enableMenu(){
		menuFicheros.setEnabled(true);
		menuSimulador.setEnabled(true);
		menuReport.setEnabled(true);
	}
	
	
	
}
