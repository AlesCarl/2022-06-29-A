package it.polito.tdp.itunes.model;

public class BilancioAlbum  implements Comparable<BilancioAlbum>{

	private Album a; 
	private Integer bilancio;
	
	public BilancioAlbum(Album a, Integer bilancio) {
		
		this.a = a;
		this.bilancio = bilancio;
	}

	public Album getA() {
		return a;
	}

	public void setA(Album a) {
		this.a = a;
	}

	public Integer getBilancio() {
		return bilancio;
	}

	public void setBilancio(Integer bilancio) {
		this.bilancio = bilancio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((bilancio == null) ? 0 : bilancio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BilancioAlbum other = (BilancioAlbum) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (bilancio == null) {
			if (other.bilancio != null)
				return false;
		} else if (!bilancio.equals(other.bilancio))
			return false;
		return true;
	}

	@Override
	public int compareTo(BilancioAlbum o) {
		return o.getBilancio().compareTo(bilancio);
	}

	@Override
	public String toString() {
		return  a + ",  bilancio= " + bilancio;
	} 
	
	
	
	
	
	
	
}
