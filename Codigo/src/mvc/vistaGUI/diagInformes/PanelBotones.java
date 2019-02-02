package mvc.vistaGUI.diagInformes;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import entidades.carreteras.Carretera;
import entidades.cruces.CruceGenerico;
import entidades.vehiculos.Vehiculo;
import mvc.Controlador;
import mvc.vistaGUI.VentanaPrincipal;

public class PanelBotones extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton generar, cancelar;
	private List<Vehiculo> selectedVehicles;
	private List<Carretera> selectedRoads;
	private List<CruceGenerico<?>> selectedJunctions;

	public PanelBotones(DialogoInformes diag, VentanaPrincipal mainWindow, Controlador controller){
		super();
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 70, 15));
		
		generar = new JButton("Generar");
		cancelar = new JButton("Cancelar");
		
		
		generar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				selectedVehicles = diag.getVehiculosSeleccionados();
				selectedRoads = diag.getCarreterasSeleccionadas();
				selectedJunctions = diag.getCrucesSeleccionados();
	
				if(!diag.panelesEmpty()){
					if(selectedVehicles.isEmpty() && selectedRoads.isEmpty() && selectedJunctions.isEmpty()){
						mainWindow.muestraDialogoAdvertencia("Selecciona primero unos items.");
					
					}
					else{
						mainWindow.generaInformes(PanelBotones.this.informesSeleccionados(mainWindow));
						
						diag.cerrar();
					}
				}

				else{
					diag.cerrar();
					mainWindow.muestraDialogoError("Ejecuta primero el simulador.");
				}

			}
			
		});
		
		cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				diag.cerrar();
			}
			
		});
		
		this.add(cancelar);
		this.add(generar);
		
	}
	
	
	private String informesSeleccionados(VentanaPrincipal mainWindow){
		
		String informes = "";
		
		for(int i = 0; i < selectedVehicles.size(); i++){
			informes += selectedVehicles.get(i).generaInforme(mainWindow.getTime());
			informes += "\n";
		}
		
		for(int i = 0; i < selectedRoads.size(); i++){
			informes += selectedRoads.get(i).generaInforme(mainWindow.getTime());
			informes += "\n";
		}
		
		for(int i = 0; i < selectedJunctions.size(); i++){
			informes += selectedJunctions.get(i).generaInforme(mainWindow.getTime());
			informes += "\n";
		}
		
		
		return informes;
	}
}
