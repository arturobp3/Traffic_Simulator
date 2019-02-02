package mvc.vistaGUI.barras;

import java.io.IOException;
import java.io.OutputStream;

import mvc.vistaGUI.VentanaPrincipal;

public class TextOutput extends OutputStream {
	
	VentanaPrincipal window;
	

	public TextOutput(VentanaPrincipal window) {
		super();
		
		this.window = window;
	}

	
	@Override
	public void write(byte[] b) throws IOException{
		
		String informe = new String(b);
		
		window.generaInformes(informe);
	}
	
	
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub
	}



}
