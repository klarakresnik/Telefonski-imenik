package si.src.naloga.imenik;

import si.src.naloga.kontakt.Kontakt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


public class TelefonskiImenik {

    private List<Kontakt> seznamKontaktov;
    private int id = 0;
//  uporabljam BufferReader, ker v zapisu ohrani presledke(ime, opis) in je mozno, da polje pustis prazno(informacije niso obvezne)
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private Pattern vzorecZaElektronskoPosto = Pattern.compile("@");
    private Pattern vzorecZaTelefonkoStevilko = Pattern.compile("\\+*\\d+\\d$");
   

    public TelefonskiImenik() {
        seznamKontaktov = new ArrayList<>();
    }

    /**
     * Metoda izpiše vse kontakte
     */
    public void izpisiVseKontakte() {
    	for (Kontakt kontakt : seznamKontaktov) {
    		System.out.println(kontakt);
    	}
    }

    /**
     * Metoda doda nov kontakt v imenik
     *
     * onemogočimo dodajanje dupliciranega kontakta, tako da 
     * se nova id stevilka vedno sama nastavi
     * @throws IOException 
     */
    public void dodajKontakt() throws IOException {
    	Kontakt nov_kontakt = new Kontakt();
    	nov_kontakt.setId(id);
    	id += 1;
//    	ime
    	System.out.println("Vnesite ime:");
    	String ime = in.readLine();
    	nov_kontakt.setIme(ime);
//    	priimek
    	System.out.println("Vnesite priimek:");
    	String priimek = in.readLine();
    	nov_kontakt.setPriimek(priimek);
//    	naslov
    	System.out.println("Vnesite naslov:");
    	String naslov = in.readLine();
    	nov_kontakt.setNaslov(naslov);
//    	elektronska pošta. Vnos je pravilen, če vsebuje znak '@'
    	System.out.println("Vnesite elektronsko pošto:");
    	String elekPosta = in.readLine();
    	Matcher matcherElekPosta = vzorecZaElektronskoPosto.matcher(elekPosta);
    	while (!matcherElekPosta.find() && elekPosta != "") {
    		System.out.println("Vnesli ste napačen elektronski naslov. Pravilen elektronski naslov vsebuje znak @. Če želite preskočiti ta korak, pritisnite enter.");
    		elekPosta = in.readLine();
        	matcherElekPosta = vzorecZaElektronskoPosto.matcher(elekPosta);
    	}
    	nov_kontakt.setElektronskaPosta(elekPosta);
//    	telefon. Vnos se lahko začne z znakom '+', slediti morajo številke in na koncu morajo biti številke.
    	System.out.println("Vnesite telefonsko številko:");
    	String telefon = in.readLine();
    	Matcher matcherTelefon = vzorecZaTelefonkoStevilko.matcher(telefon);
    	while (!matcherTelefon.find() && telefon != "") {
    		System.out.println("Vnesli ste napačeno telefonsko številko. Telefonsko številko morate zapisati s številkami. Če želite preskočiti ta korak, pritisnite enter.");
    		telefon = in.readLine();
    		matcherTelefon = vzorecZaTelefonkoStevilko.matcher(telefon);
    	}
    	nov_kontakt.setTelefon(telefon);
//    	mobilni telefon. Vnos se lahko začne z znakom '+', slediti morajo številke in na koncu morajo biti številke.
    	System.out.println("Vnesite številko mobilnega telefona:");
    	String mobilniTelefon = in.readLine();
    	Matcher matcherMobilniTelefon = vzorecZaTelefonkoStevilko.matcher(mobilniTelefon);
    	while (!matcherMobilniTelefon.find() && mobilniTelefon != "") {
    		System.out.println("Vnesli ste napačeno telefonsko številko. Številko zapišite brez znakov med števkami. Če želite preskočiti ta korak, pritisnite enter.");
    		mobilniTelefon = in.readLine();
    		matcherMobilniTelefon = vzorecZaTelefonkoStevilko.matcher(mobilniTelefon);
    	}
    	nov_kontakt.setMobilniTelefon(mobilniTelefon);
//    	opomba
    	System.out.println("Vnesite opombo:");
    	String opomba = in.readLine();
    	if (opomba != null) nov_kontakt.setOpomba(opomba);
    	seznamKontaktov.add(nov_kontakt);
    	System.out.println("Kontakt je dodan!");
    }

    /**
     * Metoda popravi podatke na obstoječem kontaktu
     * ID kontakta ni mogoče spreminjati
     * @throws IOException 
     */
    public void urediKontakt() throws IOException {
    	if (seznamKontaktov.size() != 0) {
    		System.out.println("Vpišite ID številko kontakta, ki ga želite urediti:");
    		String id_stevilka = in.readLine();
    		Kontakt kontakt = izberiKontaktIzIDStevilke(id_stevilka);
    		if (kontakt != null) {
    			System.out.println("Izberite kateri podatek želite urediti:");
    			izpisiPodatke();
    			String podatek = in.readLine();
    			switch (podatek) {
	    			case "1": //ime
	    				System.out.println("Napišite novo ime:");
	    				String ime = in.readLine();
	    				kontakt.setIme(ime);
	    				break;
	    			case "2": //priimek
	    				System.out.println("Napišite nov priimek:");
	    				String priimek = in.readLine();
	    				kontakt.setPriimek(priimek);
	    				break;
	    			case "3": //naslov
	    				System.out.println("Napišite nov naslov:");
	    				String naslov = in.readLine();
	    				kontakt.setNaslov(naslov);
	    				break;
	    			case "4": //elektronska posta
	    				System.out.println("Napišite novo elektronsko pošto:");
	    				String elektronskaPosta = in.readLine();
	    				Matcher matcherElekPosta = vzorecZaElektronskoPosto.matcher(elektronskaPosta);
	    				while (!matcherElekPosta.find() && elektronskaPosta != "") {
	    		    		System.out.println("Vnesli ste napačen elektronski naslov. Pravilen elektronski naslov vsebuje znak @. Če želite preskočiti ta korak, pritisnite enter.");
	    		    		elektronskaPosta = in.readLine();
	    		        	matcherElekPosta = vzorecZaElektronskoPosto.matcher(elektronskaPosta);
	    		    	} if (elektronskaPosta != "") kontakt.setElektronskaPosta(elektronskaPosta);
	    				break;
	    			case "5": //telefon. preverimo pravilnost vnosa enako kot pri novem vnosu
	    				System.out.println("Napišite novo telefonsko številko:");
	    				String telefon = in.readLine();
	    				Matcher matcherTelefon = vzorecZaTelefonkoStevilko.matcher(telefon);
	    				while (!matcherTelefon.find() && telefon != "") {
	    		    		System.out.println("Vnesli ste napačeno telefonsko številko. Telefonsko številko morate zapisati s številkami. Če želite preskočiti ta korak, pritisnite enter.");
	    		    		telefon = in.readLine();
	    		    		matcherTelefon = vzorecZaTelefonkoStevilko.matcher(telefon);
	    		    	} if (telefon != "") kontakt.setTelefon(telefon);
	    				break;
	    			case "6": //mobilni telefon. preverimo pravilnost vnosa enako kot pri novem vnosu
	    				System.out.println("Napišite novo številko mobilnega telefona:");
	    				String mobilniTelefon = in.readLine();
	    				Matcher matcherMobilniTelefon = vzorecZaTelefonkoStevilko.matcher(mobilniTelefon);
	    		    	while (!matcherMobilniTelefon.find() && mobilniTelefon != "") {
	    		    		System.out.println("Vnesli ste napačeno telefonsko številko. Številko zapišite brez znakov med števkami. Če želite preskočiti ta korak, pritisnite enter.");
	    		    		mobilniTelefon = in.readLine();
	    		    		matcherMobilniTelefon = vzorecZaTelefonkoStevilko.matcher(mobilniTelefon);
	    		    	} if (mobilniTelefon != "") kontakt.setMobilniTelefon(mobilniTelefon);
	    				break;
	    			case "7": //opomba
	    				System.out.println("Napišite novo opombo:");
	    				String opomba = in.readLine();
	    				kontakt.setOpomba(opomba);
	    				break;
    			} System.out.println("Kontakt je posodobljen!");
    		} else System.out.println("Niste vpisali pravilne ID številke.");
    	} else System.out.println("Seznam kontaktov je prazen!");
    }


	/**
     * Brisanje kontakta po ID-ju
	 * @throws IOException 
     */
    public void izbrisiKontaktPoId() throws IOException {
    	System.out.println("Vpišite ID številko kontakta, ki ga želite izbrisati:");
    	String id_stevilka = in.readLine();
    	Kontakt kontakt = izberiKontaktIzIDStevilke(id_stevilka);
    	if (kontakt != null) {
    		seznamKontaktov.remove(kontakt);
    		System.out.println("Uspešno ste izbrisali kontakt!");
    	} else System.out.println("Niste vpisali pravilne številke.");
    }

    /**
     * Izpis kontakta po ID-ju
     * @throws IOException 
     */
    public void izpisiKontaktZaId() throws IOException {
    	System.out.println("Vpišite ID številko kontakta, katerega želite izpisanega:");
    	String id_stevilka = in.readLine();
    	Kontakt kontakt = izberiKontaktIzIDStevilke(id_stevilka);
    	if (kontakt != null) {
    		System.out.println(kontakt);
    	} else System.out.println("Niste vpisali pravilne številke.");
    }

    /**
     * Izpis kontakta po ID-ju
     */
    public void izpisiSteviloKontaktov() {
        System.out.println("Število vseh kontaktov je " + seznamKontaktov.size() + ".");
    }

    /**
     * Serializiraj seznam kontoktov na disk.
     * Ime datoteke naj bo "kontakti.ser"
     * @throws IOException 
     */
    public void serializirajSeznamKontaktov() throws IOException {
    	FileOutputStream fos = new FileOutputStream("kontakti.ser");
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(seznamKontaktov);
    	fos.close();
    	oos.close();
        System.out.println("Uspešno ste shranili podatke!");
    }

    /**
     * Pereberi serializiran seznam kontakotv iz diska
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void naloziSerializiranSeznamKontakotv() throws IOException, ClassNotFoundException {
    	FileInputStream fis;
		try {
			fis = new FileInputStream("kontakti.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			seznamKontaktov = (List<Kontakt>) ois.readObject();
			fis.close();
			ois.close();
			System.out.println("Podatki so bili uspešno prebrani iz datoteke!");
		} catch (FileNotFoundException e) {
			System.out.println("Datoteka ni bila najdena.");
		}
    }

    /**
     * Izvozi seznam kontakov CSV datoteko.
     * Naj uporabnik sam izbere ime izhodne datoteke.
     * @throws IOException 
     */
    public void izvoziPodatkeVCsvDatoteko() throws IOException {
    	System.out.println("Vnesite ime datoteke:");
    	String imeDatoteke = in.readLine();
    	if (imeDatoteke == "") imeDatoteke = "kontakti.csv";
    	BufferedWriter writer = Files.newBufferedWriter(Paths.get(imeDatoteke));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("ID", "Ime", "Priimek", "Naslov", "ElektronskaPosta", "Telefon", "MobilniTelefon", "Opomba"));
        for (Kontakt kontakt : seznamKontaktov) {
        	csvPrinter.printRecord(Arrays.asList(kontakt));
        } csvPrinter.flush();
        csvPrinter.close();
        System.out.println("Uspešno ste izvozili kontakte v CSV-datoteki!");
    }
    
    public void isciPoImenu() throws IOException {
    	System.out.println("Vnesite iskalni niz:");
    	String niz = in.readLine();
    	for (Kontakt kontakt : seznamKontaktov) {
    		if (kontakt.getIme().matches("(.*)" + niz + "(.*)")) {
    			System.out.println("ID številka: " + kontakt.getId() + "  Ime:  " + kontakt.getIme() + "  Priimek:  " + kontakt.getPriimek());
    		}
    	}
    }
    
    public void isciPoPriimku() throws IOException {
    	System.out.println("Vnesite iskalni niz:");
    	String niz = in.readLine();
    	System.out.println("Kontakti, ki ustrezajo nizu:");
        System.out.println("-----------------------------------");
    	for (Kontakt kontakt : seznamKontaktov) {
    		if (kontakt.getPriimek().matches("(.*)" + niz + "(.*)")) {
    			System.out.println("ID številka: " + kontakt.getId() + " ; Ime:  " + kontakt.getIme() + " ; Priimek:  " + kontakt.getPriimek());
    		}
    	}
        System.out.println("-----------------------------------");
    }
    
    private Kontakt izberiKontaktIzIDStevilke(String id) {
    	if (id == "") return null;
    	int idInt = -1;
    	try {
    		idInt = Integer.parseInt(id);
    		for(Kontakt kontakt: seznamKontaktov) {
    			if (kontakt.getId() == idInt) return kontakt;
    		}
    	} catch (NumberFormatException e){
    		System.out.println("Niste vpisali številke.");    	
    	}
    	return null;
    }
    
    private void izpisiPodatke() {
		System.out.println("");
		System.out.println("1 - Ime");
		System.out.println("2 - Priimek");
		System.out.println("3 - Naslov");
		System.out.println("4 - Elektronska pošta");
		System.out.println("5 - Telefonska številka");
		System.out.println("6 - Številka mobilnega telefona");
		System.out.println("7 - Opomba");
        System.out.println("-----------------------------------");
	}
    
    public void nastaviIDStevilko() {
    	int trenuten_id = id;
    	for (Kontakt kontakt : seznamKontaktov) {
    		if (kontakt.getId() > trenuten_id) trenuten_id = kontakt.getId();
    	} id = trenuten_id + 1;
    }
}
