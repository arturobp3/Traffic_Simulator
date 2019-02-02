package principal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import excepciones.ErrorDeSimulacion;
import mvc.Controlador;
import mvc.modelo.SimuladorTrafico;
import mvc.vistaGUI.VentanaPrincipal;

public class Main {

// ATRIBUTOS:
	
	private final static Integer limiteTiempoPorDefecto = 10;
	private static Integer limiteTiempo = null;
	private static String ficheroEntrada = null;
	private static String ficheroSalida = null;
	private static ModoEjecucion modo = ModoEjecucion.GUI;
	
	private enum ModoEjecucion {
		BATCH("batch"), GUI("gui");
		
		private String descModo;
		
		private ModoEjecucion(String modeDesc) {
			descModo = modeDesc;
		}
		
		private String getModelDesc() {
			return descModo;
		}
	}


// METODOS:
	
	private static void ParseaArgumentos(String[] args) {

		// define the valid command line options
		//
		Options opcionesLineaComandos = Main.construyeOpciones();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine linea = parser.parse(opcionesLineaComandos, args);
			
			parseaOpcionHELP(linea, opcionesLineaComandos);
			parseaOpcionFicheroIN(linea);
			parseaOpcionFicheroOUT(linea);
			parseaOpcionSTEPS(linea);
			parseaOpcionModo(linea); // Nueva opcion
			

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] resto = linea.getArgs();
			if (resto.length > 0) {
				String error = "Illegal arguments:";
				for (String o : resto)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	// Creara las opciones disponibles a cargar en el main
	private static Options construyeOpciones() {
		Options opcionesLineacomandos = new Options();

		opcionesLineacomandos.addOption(Option.builder("h").longOpt("help").desc("Muestra la ayuda.").build());
		opcionesLineacomandos.addOption(Option.builder("i").longOpt("input").hasArg().desc("Fichero de entrada de eventos.").build());
		opcionesLineacomandos.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Fichero de salida, donde se escriben los informes.").build());
		opcionesLineacomandos.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Pasos que ejecuta el simulador en su bucle principal (el valor por defecto es " + Main.limiteTiempoPorDefecto + ").")
				.build());
		
		// Nueva opcion elegir modo de visionado:
		opcionesLineacomandos.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Muestra la sim. en modo consola o grafico").build());

		return opcionesLineacomandos;
	}

// Metodos de ejecucion:
	
	private static void iniciaModoEstandar() throws IOException, ErrorDeSimulacion {
		InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		SimuladorTrafico sim = new SimuladorTrafico();
		Controlador ctrl = new Controlador(sim, Main.limiteTiempo, is, os);
		ctrl.ejecuta();
		is.close();
		System.out.println("Done!");
	}
	
	private static void iniciaModoGrafico() throws InvocationTargetException, InterruptedException, ErrorDeSimulacion, IOException {
		
		SimuladorTrafico sim = new SimuladorTrafico();
		OutputStream os = Main.ficheroSalida == null ? System.out : new FileOutputStream(new File(Main.ficheroSalida));
		Controlador ctrl = new Controlador(sim, Main.limiteTiempoPorDefecto, null, os);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new VentanaPrincipal(Main.ficheroEntrada, ctrl);
					
				} catch (IOException e) {e.printStackTrace();}
			}
		});
		// Ejecutamos el fichero leido en los argumentos si lo hubiera
		if (Main.ficheroEntrada != null) {
			InputStream is = new FileInputStream(new File(Main.ficheroEntrada));
			ctrl.cargaEventos(is);
		}
	}
	
	private static void inicio() throws IOException, ErrorDeSimulacion, InvocationTargetException, InterruptedException {
		if (Main.modo.getModelDesc().equals(Main.ModoEjecucion.GUI.getModelDesc())) {
			Main.iniciaModoGrafico();
		}
		else {
			Main.iniciaModoEstandar();
		}
	}
	
// Metodos parseadores:
	
	private static void parseaOpcionHELP(CommandLine linea, Options opcionesLineaComandos) {
		if (linea.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), opcionesLineaComandos, true);
			System.exit(0);
		}
	}

	private static void parseaOpcionFicheroIN(CommandLine linea) throws ParseException {
		Main.ficheroEntrada = linea.getOptionValue("i");
		
		if (Main.ficheroEntrada == null) {
			throw new ParseException("El fichero de eventos no existe");
		}
	}

	private static void parseaOpcionFicheroOUT(CommandLine linea) throws ParseException {
		Main.ficheroSalida = linea.getOptionValue("o");
	}

	private static void parseaOpcionSTEPS(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("t", Main.limiteTiempoPorDefecto.toString());
		try {
			Main.limiteTiempo = Integer.parseInt(t);
			assert (Main.limiteTiempo < 0);
		} catch (Exception e) {
			throw new ParseException("Valor invalido para el limite de tiempo: " + t);
		}
	}

	private static void parseaOpcionModo(CommandLine linea) throws ParseException {
		String t = linea.getOptionValue("m");
		if (linea.hasOption("m")) {
			Main.modo = Main.ModoEjecucion.valueOf(t.toUpperCase()); // Obtenemos el valor del enumerado correspondiente
		}
		else{
			Main.modo = ModoEjecucion.BATCH;
		}
	}

	
// MAIN:
	
	public static void main(String[] args) throws IOException, ErrorDeSimulacion, InvocationTargetException, InterruptedException {
		
		// Ejemplo de commandLines:
		//
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/advanced/ex1.ini
		// --help
		//
		// Nuevos commandlines que tiene que admitir la P5:
		// -i resources/examples/events/basic/ex1.ini
		// -i resources/examples/events/advanced/ex1.ini -t 100
		// -m batch -i resources/examples/events/basic/ex1.ini -t 20
		// -m batch -i resources/examples/events/advanced/ex1.ini
		// -m gui 	-i resources/examples/events/basic/ex1.ini
		// -m gui 	-i resources/examples/events/advanced/ex1.ini
		
		//-o C:/Users/Arturo/Desktop/resources/examples/events/basic/ex1.ini.out
		
		/*
		 * Ejecuta varios archivos .ini a la vez. Para ello, poner la ruta donde estan almacenados esos archivos
		 * Dentro de esa carpeta se generarán los correspondientes archivos .out que habrá que comprobar con el 
		 * comprobador posteriormente
		 * Main.ejecutaFicheros(directorio);
		*/
		
//		Main.ParseaArgumentos(args); // Se parsean los argumentos introducidos
		//Main.ejecutaFicheros("C://Users//caram//Downloads//Practica5TP//examples//basic");
		Main.inicio(); // Se ejecuta el modo de visualizacion correspondiente
	}
	
	
	@SuppressWarnings("unused")
	private static void ejecutaFicheros(String path) throws IOException, ErrorDeSimulacion {

		File dir = new File(path);

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}
		
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			Main.ficheroEntrada = file.getAbsolutePath();
			Main.ficheroSalida = file.getAbsolutePath() + ".out";
			Main.limiteTiempo = 10;
			Main.iniciaModoEstandar();
		}

	}
	
}
