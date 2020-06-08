package telefonkonyv;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NevHitelesitoTest {

    @Test
    public void TestingNameIgazEredmeny(){

        assertTrue(NevHitelesito.ezHitelesNev("Péter"));
    }
    @Test
    public void TestingNameHamisEredmeny(){
        assertFalse(NevHitelesito.ezHitelesNev("Per$"));
    }
}
