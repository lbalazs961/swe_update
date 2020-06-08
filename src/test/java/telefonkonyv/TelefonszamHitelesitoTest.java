package telefonkonyv;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TelefonszamHitelesitoTest {
    @Test
    public void test1(){
        assertTrue(TelefonszamHitelesito.ezHitelesTelefonszam("+36302765477"));
    }
    @Test
    public void test2(){
        assertFalse(TelefonszamHitelesito.ezHitelesTelefonszam("555"));
    }
    @Test
    public void test3(){
        assertFalse(TelefonszamHitelesito.ezHitelesTelefonszam("36302765477"));
    }
}
