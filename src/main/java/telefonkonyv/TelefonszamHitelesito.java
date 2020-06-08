package telefonkonyv;

public class TelefonszamHitelesito {

    public static boolean ezHitelesTelefonszam(String phoneNumber){

        if(!(phoneNumber.startsWith("+36")))
            return false;
        else if(!(phoneNumber.length() == 12))
            return false;
        else
            return true;

    }
}
