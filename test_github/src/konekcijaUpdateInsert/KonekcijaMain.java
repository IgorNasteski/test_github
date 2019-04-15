package konekcijaUpdateInsert;

import java.sql.SQLException;

public class KonekcijaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//MetodeJdbc.insertUpdateIliDelete_Baza();
		//System.out.println("Dodata nova linija koda za git");
		
		int demoNiz[] = {1,4,5,2,5,4,1}; 
		uporediNizUnazad(demoNiz);
		
	}
	
	public static void uporediNizUnazad(int niz[]) {
		
		String odgovor = "";
		int unazad = niz.length - 1;
		for (int i=0; i<niz.length; i++) {
			if(niz[i] != niz[unazad]) {
				odgovor = "Nije";
				break;
			}
			else {
				odgovor = "Jeste";
			}
			unazad--;
		}
		
		System.out.println(odgovor);
	} 

}
