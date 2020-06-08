package telefonkonyv;

public class EmailHitelesito {

    public static boolean ezHitelesEmail(String email){

        return email.contains("@") && email.contains(".");
    }
}
