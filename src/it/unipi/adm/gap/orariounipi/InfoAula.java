package it.unipi.adm.gap.orariounipi;

public class InfoAula {
	private String nomeaula;
	private String lezione;
	private String orarioinizio;
	private String orariofine;
	
	/*Constructor*/
	public InfoAula(String nomeaula,String lezione,String orarioinizio, String orariofine){
		this.setNomeaula(nomeaula);
		this.setLezione(lezione);
		this.setOrarioinizio(orarioinizio);
		this.setOrariofine(orariofine);
	}
	
	/*Setters and getters*/
	
	

	public String getNomeaula() {
		return nomeaula;
	}

	public void setNomeaula(String nomeaula) {
		this.nomeaula = nomeaula;
	}

	public String getLezione() {
		return lezione;
	}

	public void setLezione(String lezione) {
		this.lezione = lezione;
	}

	public String getOrarioinizio() {
		return orarioinizio;
	}

	public void setOrarioinizio(String orarioinizio) {
		this.orarioinizio = orarioinizio;
	}

	public String getOrariofine() {
		return orariofine;
	}

	public void setOrariofine(String orariofine) {
		this.orariofine = orariofine;
	}
}
