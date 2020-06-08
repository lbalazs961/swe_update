package telefonkonyv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Létrehozza az adatbázis kapcsolatot.
 */
public class DB {

    private static Logger logger = LoggerFactory.getLogger("DB");

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public static void closeEmf(){
        emf.close();
    }

    /**
     * Feltölt egy új kontaktot az adatbázisba.
     * @param person Az aktuális kontakt amit feltöltünk.
     */
    public static void create(Person person){

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        }catch (Exception ex){
            logger.info("Nem sikerült a kontakt létrehozása.");

        }finally {
            em.close();
        }
    }

    /**
     * Frissítjük az adott kontaktot.
     * @param person A frissítésre kijelölt kontaktot tartalmazza.
     */
    public static void edit(Person person){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        }catch (Exception ex){
            logger.info("Nem sikerült a kontakt frissítése.");

        }finally {
            em.close();
        }
    }

    /**
     * Adatbázisból való törlés.
     * @param person Az adott kontakt amit törölni szeretnénk.
     */
    public static void delete(Person person){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(person));
            em.getTransaction().commit();
        }catch (Exception ex){
            logger.info("Nem sikerült a kontakt törlése.");

        }finally {
            em.close();
        }
    }

    /**
     * Kilistázza az adatbázisban szereplő kontaktokat.
     * @return Visszatér az adatbázisban található objektumokkal.
     */
    public static ArrayList<Person> getAllAsList(){
        EntityManager em = DB.getEntityManager();
        try{
            Query q = em.createQuery("SELECT e from Person e", Person.class);
            return (ArrayList<Person>) q.getResultList();
        }catch (Exception ex){
            logger.info("Nem sikerült az összes adatot lekérni.");
            return new ArrayList<Person>();
        }finally {
            em.close();
        }
    }
}
