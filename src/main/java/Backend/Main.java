package Backend;

import FrontEnd.InvoiceCreateController;
import FrontEnd.workactcreateController;
import com.itextpdf.text.DocumentException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, InterruptedException, DocumentException, ParseException {
        Random rand = new Random();
        Gener gener = new Gener();
        User user = new User();
       // gener.conncetToDB();
        //gener.vatNumber(141237452);


        //gener.upploadAllUsersFromDataBase();
        //gener.uploadUserdataToDB("loals","polas","lolas@gmail.com","123456789");
        gener.login("admin", "admin"); //prisijungimas


        String rand_int1 = String.valueOf(rand.nextInt(789789797));
       String rand_int2 = String.valueOf(rand.nextInt(789789797));
       // gener.registration(rand_int1, rand_int2, "Lapas", "Lapuotas", "lapas@gmail.com", "+37068842009", "Technikas"); //registracija
        //gener.createLogin("labas","labas");
        //gener.registration("admin","admin","Lapas", "Lapuotas", "lapas@gmail.com", "+37068842009");
        //gener.addMaterial("Varžtas", 0,1);
       //gener.deleteMaterial("Varžtas");
       // gener.updateMaterial(9,"varzdas", 1.11,1111);
        //gener.createClient("Jonsa","Jonelis","jonas@jonas.lt","+37065581044");
        //gener.createFirm(121,"test","adres","asdasd",12);
       // System.out.println(gener.findVatByFirmID(303065376));
        //gener.updateVatNumber(305863120);
       // System.out.println(gener.isVatNumberInDb(305863120));
       // gener.registration("test","test","test","testt","admin@admin.lt","+37062512345");
       // gener.login("admin","admin");
      //  System.out.println(gener.isvalidPassword("12347AA"));

        //gener.findFirmByNamefromDb("argus");

       /* String description = "Naktį pasnigo, miškas nubalo. Nendrės, juosiančios ežerėlį, " +
                "buvo tarytum paslaptingi hieroglifai, kaligrafo įrėžti į tylą, neperskaitomi, " +
                "todėl nebylūs. Tik du didžiuliai krankliai vartėsi, žaidė ore vis sugrumėdami toli ";
        WorkAct workAct = new WorkAct();

        String date = gener.getDateAndTime();
        String actNumber="DA12345678";

        workAct.addWorkAct(2,actNumber,description,"2022-01-12 12:15","2022-1-12 16:45",date,"path",1,1,1 );
        gener.createWorkActPDF(workAct);*/

        //gener.uploadUsedMaterialsToDb(12,workAct.getId(),16);

       // System.out.println(gener.uploadWorkActFromDbByWorkActNumber(workAct.getNumber()));
       // System.out.println(gener.findMaterialByID(16));

      //  System.out.println(gener.uploadUsedMaterialsFromDBByWorkActNr(workAct.getNumber()));

        //gener.openWorkAct("DA12345678");
       // gener.isValidVatNumber("LT100012822616");


      /* Invoice invoice=  gener.getInvoiceFromDBByID(1);
       System.out.println(invoice.getNumber().substring(0,3));
       gener.createInvoicePDF(invoice);
       gener.openInvoicePDF(invoice.getNumber());*/
        //int inr[] = gener.getLast6MonthsWorkactNumbers();
      // gener.sendUserPassword("kamile.biciunaite@gmail.com",gener.generatePassword());
       System.out.println((double) 50/100+1);



}
}
