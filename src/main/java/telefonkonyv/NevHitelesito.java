package telefonkonyv;

public class NevHitelesito {

    private static Character[] illegalisKarakterek = {'@','€','$','%','='};

    public static boolean ezHitelesNev(String name){

        for(int i = 0; i < name.length(); i++ ){
           for(var c : illegalisKarakterek){
               if(c == name.charAt(i))
                   return false;
           }
        }
        return true;
    }
}
