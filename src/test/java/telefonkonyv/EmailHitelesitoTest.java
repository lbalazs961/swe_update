package telefonkonyv;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailHitelesitoTest {

    @Test
    public void TestingEmailHitelesitoIgazEredmeny(){
        assertTrue(EmailHitelesito.ezHitelesEmail("asd@asd.hu"));
    }
    @Test
    public void TestingEmailHitelesitoHamisEredmenyNincsAtChar(){
        assertFalse(EmailHitelesito.ezHitelesEmail("asdasd.hu"));
    }
    @Test
    public void TestingEmailHitelesitoHamisEredmenyNincsDotChar(){
        assertFalse(EmailHitelesito.ezHitelesEmail("asdasd@hu"));
    }
    @Test
    public void TestingEmailHitelesitoHamisEredmenyNincsMegEggyikKövetelménySem(){
        assertFalse(EmailHitelesito.ezHitelesEmail("asdasdhu"));
    }
}
