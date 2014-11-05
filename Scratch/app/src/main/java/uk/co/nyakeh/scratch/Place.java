package uk.co.nyakeh.scratch;

/**
 * Created by Nyakeh on 05/11/2014.
 */
public class Place {
    public String mNameOfPlace;
    public int mZipCode;
    public String mNameOfImage;
    public String mPopup;

    public Place (String startNameOfPlace, int startZipCode,String startNameOfImage, String startPopup ){

        this.mNameOfPlace = startNameOfPlace;
        this.mZipCode = startZipCode;
        this.mNameOfImage = startNameOfImage;
        this.mPopup = startPopup;
    }
}
