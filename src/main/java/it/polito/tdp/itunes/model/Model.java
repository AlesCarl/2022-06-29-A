package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private List <Album> allAlbum;
	private SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> graph;
	private ItunesDAO dao; 
	
	private List<Album> bestPercorso; 
	private int bestScore;   // best bilancio 
	
	
	public Model() {
		
		this.allAlbum= new ArrayList<>();
		this.graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao= new ItunesDAO(); 
	}
	
	
	public List<BilancioAlbum> getAdiacenti(Album a){
		
		List<Album> successori = Graphs.successorListOf(graph, a);
		List<BilancioAlbum> bilancioSuccessori= new ArrayList<>(); 
		
		for(Album aa: successori) {
			bilancioSuccessori.add(new BilancioAlbum(aa, getBilanci(aa)));
		}
		Collections.sort(bilancioSuccessori);
		//Graphs.neighborListOf(graph, a)
		return bilancioSuccessori; 
	}
	
	
	
	public void buildGraph(int n) {
		clearGraph(); 
		loadAllNodes(n); 
		
		/** VERTICI **/ 
		Graphs.addAllVertices(this.graph, this.allAlbum);
		
 

		for(Album a1: this.allAlbum) {
			for(Album a2 : this.allAlbum) {
				
				int peso = a1.getNumSongs()-a2.getNumSongs();
				
				if(peso > 0) {//nb: con il ">" l'arco va da a2 ad a1 (solo archi che ENTRANO in a1
					Graphs.addEdgeWithVertices(this.graph, a2, a1, peso);
					
				}
			}
		}
	
		System.out.println(this.graph.vertexSet().size());
	    System.out.println(this.graph.edgeSet().size());

		
	}
	
	
	//  BILANCIO:  sum( archi entranti ) - sum( archi uscenti ) 
	private int getBilanci(Album a) {
		int bilancio = 0; 
		
	List<DefaultWeightedEdge> edgeIn = new ArrayList<>(this.graph.incomingEdgesOf(a));
	List<DefaultWeightedEdge> edgeOut= new ArrayList<>(this.graph.outgoingEdgesOf(a));

	for(DefaultWeightedEdge edge: edgeIn) {
		bilancio+= this.graph.getEdgeWeight(edge);
	}
	for(DefaultWeightedEdge edge: edgeOut) {
		bilancio-= this.graph.getEdgeWeight(edge);
	}
		
	return bilancio;
		
	}
	
	// all vertex ordinati alfabeticamente
	public List<Album> getVertices() {
		List<Album> allVertices = new ArrayList<>(this.graph.vertexSet()); 
		Collections.sort(allVertices);
		return allVertices; 
	}       
	
	
	private void clearGraph() {
		this.allAlbum= new ArrayList<>();
		this.graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
	}
	
	
	
	/*****************  RICORSIONE:  ******************/ 
	
	/* 
         percorso da a1 verso a2, con: 
          -archi con peso >= x
          -tocca il maggior numero di archi con bilancio > del vertice a1.
     */
	public List<Album> getPercorso (Album a1, Album a2, int x) {
		List <Album> parziale = new ArrayList<>() ; 
		
		this.bestPercorso= new ArrayList<>() ; 
		this.bestScore = 0; 
		
		parziale.add(a1);
		ricorsione(parziale,a2, x ); 
		
		
		return this.bestPercorso;
		
	}
		
	
	private void ricorsione(List<Album> parziale, Album a2, int x) {
		
		Album current = parziale.get(parziale.size()-1);
		
		/** condizione uscita **/ 
		if(current.equals(a2)) {
			//Controllo se solz migliore del best (se tocca il maggior numero di vertici) 
			 if(getScore(parziale) > this.bestScore) {
				 this.bestScore= getScore(parziale);
				 this.bestPercorso= new ArrayList<>(parziale);  //con la new ho una NUOVA COPIA
				
			 }
			 return; 
		}
		
		
		/** continuo ad aggiungere elementi in parziale **/ 
	     List<Album> successori= Graphs.successorListOf(graph, current);
	     
	     for(Album a: successori ) {
	    	 
	    	// il peso dell'arco .... tra "current" e "a"
	    	if(graph.getEdgeWeight(graph.getEdge(current, a)) >= x) { 
	    	 parziale.add(a);
	    	 ricorsione(parziale,a2,x);
	    	 parziale.remove(a);   // backTracking
	       }
	     }
	}
	
	
	private int getScore(List<Album> parziale) {
		
		int score = 0; 
		Album source = parziale.get(0); // nodo iniziale
		
		for(Album aa: parziale) {  //ocho
			if(getBilanci(aa) > getBilanci(source)) {
				score+=1;
			}
		}
		

		return score;
	}

	
	
	

	//* metodo di servizio per farsi dare i nodi **/ 
	public void loadAllNodes(int n) {
	
		if(this.allAlbum.isEmpty())
		allAlbum= dao.getFilteredAlbums(n);
	}

	public int getNumVertices() {
		return graph.vertexSet().size();
		
	}

	public int getNumEdges() {
       return graph.edgeSet().size(); 
     
	}
	
	
	
	
	
	
	
	
	
	
}
