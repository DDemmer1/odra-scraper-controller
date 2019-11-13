package de.demmer.dennis.odrascrapercontroller;

import de.demmer.dennis.odrascrapercontroller.services.parser.DateParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@SpringBootTest
class OdraScraperControllerApplicationTests {


    @Test
    void contextLoads() throws ParseException {
        String dateString = "Donnerstag, 07.11.2019 15:22 Uhr";
        dateString = dateString.replaceAll("(?<=2019).*$","");
        dateString = dateString.replaceAll("Montag|Dienstag|Mittwoch|Donnerstag|Freitag|Samstag|Sonntag|,|\\s", "");
        System.out.println(dateString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = dateFormat.parse(dateString);
        System.out.println(new Date().toString());
        System.out.println(date);
    }

}
