package si.src.naloga;

import si.src.naloga.imenik.TelefonskiImenik;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TelefonskiImenik telefonskiImenik = new TelefonskiImenik();

        try {
			telefonskiImenik.naloziSerializiranSeznamKontakotv();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Napaka!");
		}
        telefonskiImenik.nastaviIDStevilko();
        
        izpisiMenu();

        Scanner in = new Scanner(System.in);
        String akcija = "";

        // zanka za izris menija
        while (!"0".equals(akcija)) {
            akcija = in.next();

            switch (akcija) {
                case "1":
                    telefonskiImenik.izpisiVseKontakte();
                    break;
                case "2":
				try {
					telefonskiImenik.dodajKontakt();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "3":
				try {
					telefonskiImenik.urediKontakt();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "4":
				try {
					telefonskiImenik.izbrisiKontaktPoId();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "5":
				try {
					telefonskiImenik.izbrisiKontaktPoId();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "6":
                    telefonskiImenik.izpisiSteviloKontaktov();
                    break;
                case "7":
				try {
					telefonskiImenik.serializirajSeznamKontaktov();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "8":
				try {
					telefonskiImenik.naloziSerializiranSeznamKontakotv();
				} catch (ClassNotFoundException e) {
					System.out.println("ClassNotFoundException!");
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "9":
				try {
					telefonskiImenik.izvoziPodatkeVCsvDatoteko();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                    break;
                case "10":
				try {
					telefonskiImenik.isciPoImenu();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                	break;
                case "11":
				try {
					telefonskiImenik.isciPoPriimku();
				} catch (IOException e) {
					System.out.println("IOException!");
				}
                	break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Napačna izbira!!!");
                    break;
            }

            izpisiMenu();
        }
    }

    /**
     * Uporabniku izpišemo menu
     */
    public static void izpisiMenu() {

        System.out.println("");
        System.out.println("");
        System.out.println("Aplikacija telefonski imenik:");
        System.out.println("-----------------------------------");
        System.out.println("Akcije:");
        System.out.println("1 - izpiši vse kontakte v imeniku");
        System.out.println("2 - dodaj kontakt v imenik");
        System.out.println("3 - uredi obstoječi kontakt");
        System.out.println("4 - briši kontakt po ID-ju");
        System.out.println("5 - izpiši kontakt po ID-ju");
        System.out.println("6 - izpiši število vseh kontaktov");
        System.out.println("7 - Shrani kontakte na disk (serializacija)");
        System.out.println("8 - Preberi kontake iz serializirane datoteke");
        System.out.println("9 - Izvozi kontakte v csv");
        System.out.println("10 - Iskanje kontaktov po imenu");
        System.out.println("11 - Iskanje kontaktov po priimku");
        System.out.println("");
        System.out.println("0 - Izhod iz aplikacije");
        System.out.println("----------------------------------");
        System.out.println("Akcija: ");


    }
}
