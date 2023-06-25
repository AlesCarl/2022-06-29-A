/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.BilancioAlbum;
import it.polito.tdp.itunes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenze"
    private Button btnAdiacenze; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA1"
    private ComboBox<Album> cmbA1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbA2"
    private ComboBox<Album> cmbA2; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    
    
    @FXML
    void doCalcolaAdiacenze(ActionEvent event) {
    	
    	Album alb  = this.cmbA1.getValue();
    	if(alb== null) {
    		txtResult.setText("valore selezionato NULLO" );
    	return; 
    }
    	List<BilancioAlbum>bilanci= model.getAdiacenti(alb);
		txtResult.appendText("Stampo i successori del nodo "+alb+"\n" );
		
		for(BilancioAlbum bb: bilanci) {
			txtResult.appendText(""+bb+"\n");
		}

    	
    	
    	
    }
   
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	String input= txtX.getText();
    	

    	if(input=="") {
    		txtResult.setText("INPUT vuoto");
    		return; 
    	}
    	
    	
    	try {
    		int inputNum= Integer.parseInt(input); 
    		
    		Album a1= this.cmbA1.getValue();
    		Album a2= this.cmbA2.getValue();
    		
    		if(a1== null || a2==null) {
    			txtResult.setText("valori cmbox vuoti");
    			return;
    			
    		}
    		
    		List<Album> path = model.getPercorso(a1, a2, inputNum);
    		
    		if(path.isEmpty()) {
    			txtResult.setText(" \n Nessuno percorso trovato");
    			return; 
    		}
    		
    		txtResult.setText(" \n Percorso tra "+a1+" e "+ a2);
    		for(Album aa: path ) {
    			txtResult.appendText("\n"+aa);
    		}

    		
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("inserisci un numero");

    	}
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	//IN MODEL HO MESSO IL METODO CLEAR GRAPH
    	
    	String input = txtN.getText();
    	if(input=="") {
    		txtResult.setText("INPUT vuoto");
    	return;
    }
    	
    	
    	try {
    		int inputName= Integer.parseInt(input); 
    		model.buildGraph(inputName);
    		int numV= model.getNumVertices() ;
    		int numE= model.getNumEdges() ;
    		
    		txtResult.setText("grafo creato correttamente \n");
    		txtResult.appendText("Num vertici: "+numV+"\n");
    		txtResult.appendText("Num edges: "+numE +"\n");

    		// popolo la tendina 
    		this.cmbA1.getItems().setAll(model.getVertices());
    		this.cmbA2.getItems().setAll(model.getVertices());


    		
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("inserisci un numero");

    	}
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenze != null : "fx:id=\"btnAdiacenze\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA1 != null : "fx:id=\"cmbA1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbA2 != null : "fx:id=\"cmbA2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    
    public void setModel(Model model) {
    	this.model = model;
    }
}
