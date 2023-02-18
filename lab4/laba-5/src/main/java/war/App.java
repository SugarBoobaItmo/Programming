package war;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import collection_manager.AbstractManager;
import collection_manager.LocalManager;
import models.Coordinates;
import models.Person;
import models.Semester;
import models.StudyGroup;
import xmlutils.XmlUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {   
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Africa/Cairo"));
        String zd = String.valueOf(zdt);

        AbstractManager manager = new LocalManager("file.xml");
        manager.add(new StudyGroup(1l, "name", new Coordinates(1, 2), zdt, 1l, 1l, Semester.SEVENTH, new Person("name", new Date(), 10, 20l, "name")));
        manager.load();
        System.out.println(zd);
        


    }
}
