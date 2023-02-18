package xmlutils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import models.CollectionRecord;

import java.io.File;


public class XmlUtils {
    public static void recordToXml(CollectionRecord record, String filePath){
        try {
            
            JAXBContext context = JAXBContext.newInstance(CollectionRecord.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
            // маршаллинг объекта в файл
            marshaller.marshal(record, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        // JAXBContext context = JAXBContext.newInstance(CollectionRecord.class);
        // Marshaller marshaller = context.createMarshaller();
        // // устанавливаем флаг для читабельного вывода XML в JAXB
        // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // // маршаллинг объекта в файл
        // marshaller.marshal(record, new File(filePath));
    }

    public static CollectionRecord xmlToRecord(String filePath){
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(CollectionRecord.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
 
            return (CollectionRecord) unmarshaller.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            // e.printStackTrace();
            System.out.println("fuck");

        }
        // JAXBContext jaxbContext = JAXBContext.newInstance(CollectionRecord.class);
        // Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // return (CollectionRecord) unmarshaller.unmarshal(new File(filePath));
        return null;
    }

    
}
