/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mort.mercenaries.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Martin
 */
public class MercenaryNameRegistry {
    
    public ArrayList<String> firstNames;
    public ArrayList<String> lastNames;

    private int shuffleInterval = 10;
    private int lastShuffle = -1;
    private Random rnd = new Random();
    
    public MercenaryNameRegistry() {
	firstNames = new ArrayList<String>();
	lastNames = new ArrayList<String>();

	//this is an export from some name generator
	String somenames = "Asteropaeus Stoffels Cephissos Gilo Owyne Kartallozi Reynold Lieur Karopophores Arrizabalaga Hecataeus Thiel Wymond Busenello Liutprand Angermeier Larra Benichou Eutropios Desarthe Eskuin Lombard Authari Vladu Methodius Entremont Hilduin Geisheimer Ricard Painel Helgaud Tichelman Cordylion Serbanescu Sewell Etxenike Bernat Kirshbaum Wobias Kochiu Damasos Stortebeker Arsaphius Pollaiuolo Seward Ignatieva Garsille Secci Bobbie Travolta Ernaldus Buzoku Gylys Dieskau Nithard Acker Thrasius Corviser Landri Meverel Barrett Jolves Orges Brackmann Philkin Drafondre Kandaulo Ryckaert Philippus Grai Philolaos Veens Walaric Montfiquet Persefall Poussin Scyllias Ligonier Anaximenes Gobetti Lewis Gumy Anselmus Frondeville Retemeris Mihura Metron Clairmonte Laurencius Thielman Fulbertus Noue Adaldag Albani Garnier Pardieu Tyndarios Stötzer Estmond Cadeloro Aristaeus Magnoni Melanippos Demeuleneire Klaes Voe Dreux Cristostome Arbogast Haussmann Jollivet Fessler Heldebald Ramadani Rob Vanderzee Névelet Ahmetaj Tamás Albertazzi Merops Monteiro Aspar Rigaudias Gerulf Deutscher Endios Bigeois Sayer Bellemare Leudbald Mortier Stace Cellini Ilberd Brissaud Leofuuinus Bernardi Wilhelmus Lascaris Ligart Bordon Asopodoros Heidloff Kennard Zetterlund Guntramus Wilmetz Anko Cartellieri Luzea Cid Tomás Mendy Glycon Palagyi Antidoros Siantos Hankin Tourneville Lysias Rees Jeph Hodenc Marlo Jurcks Tinnelt Gambetta Gerould Waldenmaier Larra Bourdy Kandaulo Damme Stallo Barres Robion Hapsberg Izotz Loucelles Galfridus Zagreda Bertelis Sicho Harkaitz Pitje Widigast Cyper Symeon Trillo Lijart Kreissig Hermongenes Rainaducci Mantzio Mahlenkamp Wihtred Bracher";
	boolean first = true;
	for( String name : somenames.split(" ") ){
	    if(first)
			firstNames.add(name);
	    else
			lastNames.add(name);
	    first = !first;
	}
		
}
    
    public String randomName(){
		if( ( (++lastShuffle) % shuffleInterval) == 0 ){
		    lastShuffle = 0;
		    rnd.nextBoolean();
		    Collections.shuffle(firstNames,rnd);
		    rnd.nextBoolean();
		    Collections.shuffle(lastNames,rnd);
		}
		return firstNames.get(lastShuffle) + " " + lastNames.get(lastShuffle);
    }
    
}
