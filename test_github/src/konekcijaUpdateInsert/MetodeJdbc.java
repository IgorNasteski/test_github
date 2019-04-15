package konekcijaUpdateInsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MetodeJdbc {

	//metoda za konekciju
	public static Connection konektujSe(String nazivBaze) {
		
		String url = "jdbc:mysql://localhost:3306/" + nazivBaze + "?useSSL=false";
		String user = "root";
		String password = "kosarkas92";
		
		try {
			System.out.println("Uspesna");
			return DriverManager.getConnection(url, user, password);	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Neuspesna");
			return null;
		}
	}
	
	//omogucen insert u bazu
	public static void insertUBazu(String nazivBaze) {
		Connection konekcija = null;
		PreparedStatement pst = null;//Koristim PreparedStatement umesto Statement-a jer mora(kad god prosledjujem neke argumente u query-ju
		 							 //moram koristiti PreparedStatement jer on vrsi proveru, i tako ne moze neko da presretne moje podatke)
		try {
			konekcija = konektujSe(nazivBaze);
			
			Scanner petlja1;
			
			String odgovor;
			do {
				petlja1 = new Scanner(System.in);
				Scanner ucitajInsert = new Scanner(System.in);
				
				System.out.println("Unesite ime ");
				String ime = ucitajInsert.nextLine();
				
				System.out.println("Unesite prezime ");
				String prezime = ucitajInsert.nextLine();
				
				System.out.println("Unesite mail ");
				String mail = ucitajInsert.nextLine();
				
				System.out.println("Unesite poziciju ");
				int pozicija = ucitajInsert.nextInt();
				
				ucitajInsert.close();
				
				//query mora biti pre statementa, jer ga prosledjujemo njegovoj metodi, i onda ga vidi jer je pre njega
				String query = "INSERT INTO entuzijasti VALUES(null,'" + ime + "','" + prezime + "','" + mail + "'," + pozicija + ")";
				
				//pripremio statement za query
				pst = konekcija.prepareStatement(query);
				
				pst.executeUpdate();
				System.out.println("Uspesan update!");
				
				System.out.println("Da li zelite da unesete novog korisnika? DA/NE");
				odgovor = petlja1.nextLine();
				
			}while(odgovor.equalsIgnoreCase("da"));
			petlja1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//finally ce se uvek izvrsiti, nevezano da li je usao u try ili catch
		finally {
			if(pst != null) {//zatvaramo konekciju ako je razlicit sa nulom, tj zatvaramo samo ako je otvoren
				try {
					pst.close();//prvo se zatvara onaj koji je poslednji otvoren pa unazad
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(konekcija != null) {
				try {
					konekcija.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//omogucen update baze
	public static void updateBaze(String nazivBaze) {
		Connection konekcija = null;
		PreparedStatement pst = null;//Koristim PreparedStatement umesto Statement-a jer mora(kad god prosledjujem neke argumente u query-ju
									 //moram koristiti PreparedStatement jer on vrsi proveru, i tako ne moze neko da presretne moje podatke)
		try {
			Scanner ucitaj1 = new Scanner(System.in);
			konekcija = konektujSe(nazivBaze);
			Scanner s;
			String pitanje;
			do {
				Scanner ucitaj2 = new Scanner(System.in);
				s = new Scanner(System.in);
				System.out.println("Koju kolonu zelite da UPDATE-ujete:");
				String kolona = ucitaj1.nextLine();
				
				System.out.println("Unesite vrednost za tu kolonu: ");
				String vrednost = ucitaj1.nextLine();
				
				ucitaj1.close();
				
				System.out.println("Unesite ID korisnika koga zelite da UPDATE-ujete: ");
				int id = ucitaj2.nextInt();
				
				ucitaj2.close();
				
				String query = "UPDATE entuzijasti SET " + kolona + " = '" + vrednost + "' WHERE id_entuzijasti = " + id;
				
				//pripremio statement za query
				pst = konekcija.prepareStatement(query);
				
				pst.executeUpdate();
				
				System.out.println("Uspesan UPDATE kolone!");
				
				System.out.println("Da li zelite ponovo da UPDATE-ujete? DA/NE");
				pitanje = s.nextLine();
				
			}while(pitanje.equalsIgnoreCase("da"));
			s.close();
		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(konekcija != null) {
				try {
					konekcija.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	//omoguceno brisanje kolona u bazi
	//omogucen delete u bazi
	public static void deletePodatakaUBazi(String nazivBaze) {
		Scanner scannerDelete = new Scanner(System.in);	
		
		Connection konekcija = null;
		PreparedStatement pst = null;//Koristim PreparedStatement umesto Statement-a jer mora(kad god prosledjujem neke argumente u query-ju
		 									  //moram koristiti PreparedStatement jer on vrsi proveru, i tako ne moze neko da presretne moje podatke)
		try {
			konekcija = konektujSe(nazivBaze);
			Scanner petljaScanner;
			String pitaj;
			do {
				petljaScanner = new Scanner(System.in);
				
				System.out.println("Unesite ID korisnika kojeg zelite da obrisete: ");
				int id_e = scannerDelete.nextInt();
			
				scannerDelete.close();
				
				String query = "DELETE FROM entuzijasti WHERE id_entuzijasti = " + id_e;
				//String query = "DELETE FROM entuzijasti WHERE ime = 'Karl'";
				
				pst = konekcija.prepareStatement(query);
				
				int rowAffected = pst.executeUpdate();
				
				System.out.println("Rows affected " + rowAffected);
				System.out.println("Uspesno brisanje!");
				
				System.out.println("Da li zelite da obrisete jos jednog korisnika? da/ne");
				pitaj = petljaScanner.nextLine();
				
			}while(pitaj.equalsIgnoreCase("da"));
			petljaScanner.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
		finally {
			if(pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(konekcija != null) {
				try {
					konekcija.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//glavni metod, u njemu su se nalaze prethodne 4 metode 
	//metoda za unos, dodavane 3 metode za (insert,update,delete)
	public static void insertUpdateIliDelete_Baza() {
		
		Scanner unesi = new Scanner(System.in);
		
		System.out.println("Unesite naziv baze koju zelite da koristite: ");
		String nazivBaze = unesi.nextLine();
		
				Scanner znj;
				String opet;
				do {
					znj = new Scanner(System.in);
					System.out.println("Ako zelite da insertujete pritisnite 'i', ako zelite da update-ujete pritisnite 'u', ako zelite da obrisete 'd'.");

					char izaberi = unesi.next().charAt(0);
					
					unesi.close();
					
					switch(izaberi) {
						case 'i':
							insertUBazu(nazivBaze);
								break;
						case 'u':
							updateBaze(nazivBaze);	
								break;
						case 'd':
							deletePodatakaUBazi(nazivBaze);
								break;	
							default: 
								System.out.println("Niste izabrali nista od ponudjenog. Pozdrav!");
								break;	
					}
					
					System.out.println("Hocete ponovo insert/delete/update? DA/NE ");
					opet = znj.nextLine();
				}while(opet.equalsIgnoreCase("da"));
				znj.close();
	}
	
}
