package mvc.vistaGUI.barras;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import eventos.Evento;
import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.ObservadorSimuladorTrafico;
import mvc.vistaGUI.VentanaPrincipal;
import principal.MapaCarreteras;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {

// ATRIBUTOS:
	
	private JSpinner steps;
	private JSpinner delay;
	private JTextField time;
	private ArrayList<JComponent> listaComponentes; // Contiene todos los componentes de la toolbar
													// para deshabilitarlos o habilitarlos
	
	
	
// CONSTRUCTORA:
	
	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		controlador.addObserver(this);
		
		listaComponentes = new ArrayList<JComponent>();
		
		// Cargar eventos:
		JButton botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de eventos");
		botonCargar.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/open.png")));
		listaComponentes.add(botonCargar);
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		this.add(botonCargar);
		
		// Guardar eventos:
		JButton botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guarda los eventos en un fichero");
		botonGuardar.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/save.png")));
		listaComponentes.add(botonGuardar);
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardaEvento();
			}
		});
		this.add(botonGuardar);
		
		// Limpiar eventos:
		JButton botonLimpiarEventos = new JButton();
		botonLimpiarEventos.setToolTipText("Limpia la seccion de eventos");
		botonLimpiarEventos.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/clear.png")));
		listaComponentes.add(botonLimpiarEventos);
		botonLimpiarEventos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarEditor();
			}
		});
		this.add(botonLimpiarEventos);
		this.addSeparator();
		
		// Check-in:
		JButton botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		botonCheckIn.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/events.png")));
		listaComponentes.add(botonCheckIn);
		botonCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
					byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));
					mainWindow.setMensaje("�Eventos cargados al simulador!");
				} 
				catch (ErrorDeSimulacion | FileNotFoundException err) {
					mainWindow.muestraDialogoError("El fichero de eventos que se ha cargado no es v�lido. Pruebe otro.");
				}	
			}
		});
		this.add(botonCheckIn);

		// Ejecutar:
		JButton botonPlay = new JButton();
		botonPlay.setToolTipText("Ejecuta la simulacion");
		botonPlay.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/play.png")));
		listaComponentes.add(botonPlay);
		botonPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (mainWindow.getHebra() == null){
				
					mainWindow.crearHebra();
					mainWindow.start();
					mainWindow.deshabilitaInterfaz();
				}
				try {
					int pasos = mainWindow.getSteps();
					controlador.ejecuta(pasos);
				} catch (ErrorDeSimulacion | IOException e1) {
					mainWindow.muestraDialogoError("Ha ocurrido un error en la ejecucion");
				}
			}
		});
		this.add(botonPlay);
		
		// Stop:
		JButton botonStop = new JButton();
		botonStop.setToolTipText("Detiene la simulacion");
		botonStop.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/stop.png")));
		botonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Detener la simulacion interrumpiendo el hilo por el que se ejecuta el metodo run del simulador/controlador
				if (mainWindow.getHebra() != null) { mainWindow.interrupt(); }
				
			}
		});
		this.add(botonStop);
		
		// Reiniciar:
		JButton botonReset = new JButton();
		botonReset.setToolTipText("Reinicia la simulacion");
		botonReset.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/reset.png")));
		listaComponentes.add(botonReset);
		botonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.limpiarEditor();
					mainWindow.limpiarInformes();
					controlador.reinicia();
				} catch (FileNotFoundException e1) {
					mainWindow.muestraDialogoError("Error al reiniciar el simulador");
				}
			}
		});
		this.add(botonReset);
		this.addSeparator();
		
		
		// Delay:
		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 5000, 1));
		this.delay.setToolTipText("tiempo de retraso: 1-5000");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(1);
		listaComponentes.add(delay);
		this.add(delay);
		
		// Spinner:
		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		listaComponentes.add(steps);
		this.add(steps);
		
		// Tiempo:
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		listaComponentes.add(time);
		this.add(this.time);
		this.addSeparator();
		
		// Informes (OPCIONAL):
		JButton botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Generar informes");
		botonGeneraReports.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/report.png")));
		listaComponentes.add(botonGeneraReports);
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.abrirDialogoInformes();
			}
		});
		this.add(botonGeneraReports);
		
		// Limpiar informes:
		JButton botonLimpiar = new JButton();
		botonLimpiar.setToolTipText("Limpia la seccion de informes");
		botonLimpiar.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/delete_report.png")));
		listaComponentes.add(botonLimpiar);
		botonLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarInformes();
			}
		});
		this.add(botonLimpiar);
		
		// Guardar informes:
		JButton botonGuardarInfo = new JButton();
		botonGuardarInfo.setToolTipText("Guarda la seccion de informes en un fichero");
		botonGuardarInfo.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/save_report.png")));
		listaComponentes.add(botonGuardarInfo);
		botonGuardarInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salvarInformes();
			}
		});
		this.add(botonGuardarInfo);
		this.addSeparator();
		
		// Salir:
		JButton botonSalir = new JButton();
		botonSalir.setToolTipText("Salir del simulador");
		botonSalir.setIcon(new ImageIcon(ToolBar.loadImage("./iconos/exit.png")));
		listaComponentes.add(botonSalir);
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salir();
			}
		});
		this.add(botonSalir);
	}

	// Reaccionando como observador:
	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.time.setText("" + tiempo);
	}
	
	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {}
	
	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.steps.setValue(1);
		this.time.setText("0");
	}
	
	// Metodo para cargar una imagen:
	public static Image loadImage(String path) {
		   return Toolkit.getDefaultToolkit().createImage(path);
	}
	
	public int getSteps() {
		
		return (int) this.steps.getValue();
	}
	
	public Integer getTime() {
		return Integer.parseInt(time.getText());
	}
	
	public int getDelay() {
		
		return (int) this.delay.getValue();
	}
	
	
	public void disableButtons(VentanaPrincipal mainWindow) {
		if (mainWindow.getHebra().getState() == Thread.State.RUNNABLE) {
			// Deshabilitar todos los botones
			for(JComponent i : listaComponentes){
				i.setEnabled(false);
			}
		}
	}
	public void enableButtons() {
			// Deshabilitar todos los botones
		for(JComponent i : listaComponentes){
			i.setEnabled(true);
		}
	}
	
}
