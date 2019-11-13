package de.demmer.dennis.odrascrapercontroller.services.parser;


import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateParser {


    public Date parseStringToDate(String dateString){

        dateString = dateString.replaceAll("Montag | Dienstag | Mittwoch | Donnerstag", "");
        System.out.println(dateString);
        return new Date();
    }

}
