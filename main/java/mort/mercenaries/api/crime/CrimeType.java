package mort.mercenaries.api.crime;

import java.util.HashSet;

/**
 * "Enum" for crime types - you can add custom crime types
 * @author Martin Labuť <martin.labut@volny.cz>
 */
public class CrimeType {
    
    public static CrimeType assault;
    public static CrimeType vandalism;
    public static CrimeType theft;
    public static CrimeType trespassing;
    
    public static HashSet<CrimeType> crimeRegistry;
    
    static{
	crimeRegistry = new HashSet<CrimeType>();
	assault = new CrimeType(1);
	vandalism = new CrimeType(2);
	theft = new CrimeType(3);
	trespassing = new CrimeType(4);
    };
    
    int typeID;

    public CrimeType(int typeID) {
	this.typeID = typeID;
	if( !crimeRegistry.add( this ) ){
	    System.err.println( "CrimeTypeID already taken ( "+typeID+") when adding " + this );
	}
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final CrimeType other = (CrimeType) obj;
	if (this.typeID != other.typeID) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 59 * hash + this.typeID;
	return hash;
    }
	  
    
    
}
