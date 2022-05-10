package Tests;


import Backend.Gener;
import FrontEnd.workactcreateController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    @Test
    void GettingIDFromDB() throws SQLException {
        var Gener = new Gener();
        Gener.conncetToDB();
        assertEquals(1, Gener.getIDbyUsername("admin"));
        Gener.disconnectDB();
    }

    @Test
    void GettingVatNumber() throws IOException, InterruptedException {
        var Gener = new Gener();
        assertEquals("LT100012822616", Gener.findVatByFirmID(305235418));
    }

    @Test
    void EncriptionTesting() {
        Gener gener = new Gener();
        assertEquals("21232f297a57a5a743894a0e4a801fc3", gener.encryption("admin"));
    }

    @Test
    void EmailValidationTesting() {
        Gener gener = new Gener();
        assertEquals(true, gener.isValidEmailAddress("Aurimas.Pavardenis@one.lt"));
        assertEquals(false, gener.isValidEmailAddress("Aurimas.Pavardenis.one.lt"));
        assertEquals(false, gener.isValidEmailAddress(".lt"));
        assertEquals(false, gener.isValidEmailAddress("@one.lt"));
        assertEquals(false, gener.isValidEmailAddress("Aurimas.Pavardenis@one"));
    }

    @Test
    void PhoneNumberValidationTesting() {
        Gener gener = new Gener();

        assertEquals(true, gener.isValidPhoneNumber("+37061195540"));
        assertEquals(false, gener.isValidPhoneNumber(" "));
        assertEquals(false, gener.isValidPhoneNumber("+3706554"));
        assertEquals(false, gener.isValidPhoneNumber("37061195540"));
        assertEquals(false, gener.isValidPhoneNumber("+37461195540"));

    }

    @Test
    void firmNameTextFieldTest() {
        Gener gener = new Gener();
        assertEquals(true, gener.isFirmNameValid("Argus"));
        assertEquals(false, gener.isFirmNameValid(""));
        assertEquals(false, gener.isFirmNameValid("              "));
    }

    @Test
    void firmNumberTextFieldTest() {
        Gener gener = new Gener();
        assertEquals(true, gener.isFirmNumberValid("123456789"));
        assertEquals(false, gener.isFirmNumberValid(""));
        assertEquals(false, gener.isFirmNumberValid("12345678942545"));
        assertEquals(false, gener.isFirmNumberValid("12345"));
        assertEquals(false, gener.isFirmNumberValid("dfsfsdfsdf56dsf6dsf"));
        assertEquals(false, gener.isFirmNumberValid("13asd25srg"));
    }

    @Test
    void isCorrectTimeFormatTest() {
        Gener gener = new Gener();
        assertEquals(true, gener.isValidTimeFormat("12:58"));
        assertEquals(false, gener.isValidTimeFormat(":"));
        assertEquals(true, gener.isValidTimeFormat("00:00"));
        assertEquals(false, gener.isValidTimeFormat("24:01"));
        assertEquals(false, gener.isValidTimeFormat("148:00"));
        assertEquals(false, gener.isValidTimeFormat(":00"));
    }

    @Test
    void isValidTimeandDate() throws ParseException {
        Gener gener = new Gener();

        assertEquals(1, gener.isValidDateAndTime(
                "12:12",LocalDate.parse("2022-04-04"), "16:49",LocalDate.parse("2022-04-04")));
        assertEquals(0, gener.isValidDateAndTime(
                "22:12",LocalDate.parse("2022-04-04"), "19:49",LocalDate.parse("2022-04-04")));
        assertEquals(2, gener.isValidDateAndTime(
                "12:12",LocalDate.parse("2022-04-04"), "12:12",LocalDate.parse("2022-04-04")));
        assertEquals(3, gener.isValidDateAndTime(
                ":12",LocalDate.parse("2022-04-04"), "16:49",LocalDate.parse("2022-04-04")));
    }

    @Test
    void  isValidDescription (){
        Gener gener = new Gener();
        assertEquals(true , gener.isValidDescription("Naktį pasnigo, miškas nubalo. Nendrės, juosiančios ežerėlį, buvo tarytum paslaptingi hieroglifai, kaligrafo įrėžti į tylą, neperskaitomi, todėl nebylūs. Tik du didžiuliai krankliai vartėsi, žaidė ore vis sugrumėdami toli girdimais balsais. Miškas stovėjo tarytum rikiuotė baltų ir juodų pėstininkų. Ežero vanduo buvo juodas ir slėpė bedugnę. Lygaus paviršiaus nežeidė net menkiausias vėjelis, tik tamsiajame veidrody atsispindėjo pakibęs (tiesiai pačiame paveikslo viduryje) plaštakos formos debesis."));
        assertEquals(false,gener.isValidDescription(""));
        assertEquals(false,gener.isValidDescription("s"));
        assertEquals(false,gener.isValidDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui. Aenean ut eros et nisl sagittis vestibulum. Nullam nulla eros, ultricies sit amet, nonummy id, imperdiet feugiat, pede. Sed lectus. Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis. Etiam imperdiet imperdiet orci. Nunc nec neque. Phasellus leo dolor, tempus non, auctor et, hendrerit quis, nisi. Curabitur ligula sapien, tincidunt non, euismod vitae, posuere imperdiet, leo. Maecenas malesuada. Praesent congue erat at massa. Sed cursus turpis vitae tortor. Donec posuere vulputate arcu. Phasellus accumsan cursus velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam, nisi quis porttitor congue, elit erat euismod orci, ac placerat dolor lectus quis orci. Phasellus consectetuer vestibulum elit. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc. Vestibulum fringilla pede sit amet augue. In turpis. Pellentesque posuere. Praesent turpis. Aenean posuere, tortor sed cursus feugiat, nunc augue blandit nunc, eu sollicitudin urna dolor sagittis lacus. Donec elit libero, sodales nec, volutpat a, suscipit non, turpis. Nullam sagittis. Suspendisse pulvinar, augue ac venenatis condimentum, sem libero volutpat nibh, nec pellentesque velit pede quis nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Ut varius tincidunt libero. Phasellus dolor. Maecenas vestibulum mollis diam. Pellentesque ut neque. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In dui magna, posuere eget, vestibulum et, tempor auctor, justo. In ac felis quis tortor malesuada pretium. Pellentesque auctor neque nec urna. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Aenean viverra rhoncus pede. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut non enim eleifend felis pretium feugiat. Vivamus quis mi. Phasellus a est. Phasellus magna. In hac habitasse platea dictumst. Curabitur at lacus ac velit ornare lobortis. Curabitur a felis in nunc fringilla tristique. Morbi mattis ullamcorper velit. Phasellus gravida semper nisi. Nullam vel sem. Pellentesque libero tortor, tincidunt et, tincidunt eget, semper nec, quam. Sed hendrerit. Morbi ac felis. Nunc egestas, augue at pellentesque laoreet, felis eros vehicula leo, at malesuada velit leo quis pede. Donec interdum, metus et hendrerit aliquet, dolor diam sagittis ligula, eget egestas libero turpis vel mi. Nunc nulla. Fusce risus nisl, viverra et, tempor et, pretium in, sapien. Donec venenatis vulputate lorem. Morbi nec metus. Phasellus blandit leo ut odio. Maecenas ullamcorper, dui et placerat feugiat, eros pede varius nisi, condimentum viverra felis nunc et lorem. Sed magna purus, fermentum eu, tincidunt eu, varius ut, felis. In auctor lobortis lacus. Quisque libero metus, condimentum nec, tempor a, commodo mollis, magna. Vestibulum ullamcorper mauris at ligul") );
    }

    @Test
    void getTime(){
        Gener gener = new Gener();
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        String date = String.valueOf(createDate).replace("T", " ");
        date = date.substring(0, date.length() - 3);
        assertEquals(date,gener.getDateAndTime());
    }

    @Test
    void getDiscouotedPrice(){
        Gener gener = new Gener();
        assertEquals(750, gener.getDiscuontedPrice(1000,25));
        assertEquals(1400, gener.getDiscuontedPrice(2000,30));
        assertEquals(100, gener.getDiscuontedPrice(200,50));
        assertEquals(100, gener.getDiscuontedPrice(100,0));
        assertEquals(0, gener.getDiscuontedPrice(0,-10));
    }
    @Test
    void isLetter()
    {
        Gener gener = new Gener();
        assertEquals(false,gener.isLetter('5'));
        assertEquals(false,gener.isLetter('-'));
        assertEquals(true,gener.isLetter('a'));
    }
    @Test
    void isNumeric()
    {
        Gener gener = new Gener();
        assertEquals(false,gener.isNumeric('a'));
        assertEquals(false,gener.isNumeric('-'));
        assertEquals(false,gener.isNumeric('A'));
        assertEquals(true,gener.isNumeric('0'));
    }
    @Test
    void isValidUserName()
    {
        Gener gener = new Gener();
        assertEquals(false,gener.isValidUsername("a"));
        assertEquals(true,gener.isValidUsername("aasdasč"));
        assertEquals(false,gener.isValidUsername("Ealsdiaskdnasodaoksdoasndoasndoasdojsdjasdknaosdnaosndaosndasndaksndaksndlalskndlaksndasndiasd"));
    }
    @Test
    void isValidPassword()
    {
        Gener gener = new Gener();
        assertEquals(false,gener.isvalidPassword("asdasd"));
        assertEquals(false,gener.isvalidPassword(""));
        assertEquals(false,gener.isvalidPassword("  "));
        assertEquals(true,gener.isvalidPassword("Slaptazodis5887"));
    }
    @Test
    void encription()
    {
        Gener gener = new Gener();
        assertEquals("21232f297a57a5a743894a0e4a801fc3",gener.encryption("admin"));

    }
    @Test
    void passwordGeneration()
    {
        Gener gener = new Gener();
        assertEquals(12,gener.generatePassword().length());
    }
    @Test
    void isVatNumberInDB() throws SQLException {
        Gener gener = new Gener();
        assertEquals(true,gener.isVatNumberInDb(304682359));
    }
    @Test
    void isValidTimeFormat ()
    {
        Gener gener = new Gener();
        assertEquals(true,gener.isValidTimeFormat("12:15"));
        assertEquals(false,gener.isValidTimeFormat("0-:15"));
        assertEquals(false,gener.isValidTimeFormat("25:15"));
    }

    @Test
    void isValidTimeAndDate() throws ParseException {
        LocalDate d1 = LocalDate.parse("2022-12-12");
        LocalDate d2 = LocalDate.parse("2022-12-12");
        Gener gener = new Gener();
        assertEquals(1,gener.isValidDateAndTime("12:15", d1,"12:45", d2));
    }
    @Test
    void getDateAndTime ()
    {Gener gener = new Gener();
        LocalDateTime createDate = LocalDateTime.now().withNano(0);
        String date = String.valueOf(createDate).replace("T", " ");
        date = date.substring(0, date.length() - 3);
        assertEquals(date,gener.getDateAndTime());

    }

    @Test
    void priceRound ()
    {
        Gener gener = new Gener();
        assertEquals( "12.46",gener.priceRound(12.459));
        assertEquals("12.45",gener.priceRound(12.454));
        assertEquals("0.44",gener.priceRound(000000.444));
    }
    @Test
    void isValidInvoicesSeries()
    {
        Gener gener = new Gener();
        assertEquals( false,gener.isValidInvoiceSeries("asd"));
        assertEquals( false,gener.isValidInvoiceSeries("ASD15"));
        assertEquals( false,gener.isValidInvoiceSeries("158"));
        assertEquals( true,gener.isValidInvoiceSeries("SGA"));
    }
    @Test
    void isValidWorkActSeries()
    {
        Gener gener = new Gener();
        assertEquals( false,gener.isValidWorkActSeries("asd"));
        assertEquals( false,gener.isValidWorkActSeries("ASD15"));
        assertEquals( false,gener.isValidWorkActSeries("158"));
        assertEquals( true,gener.isValidWorkActSeries("SG"));
    }
    @Test
    void isValidIBAN ()
    {
        Gener gener = new Gener();
        assertEquals(false,gener.isValidIBan("LT0270440600030877184"));
        assertEquals(false,gener.isValidIBan("LT02704406000308771"));
        assertEquals(true,gener.isValidIBan("LT027044060003087184"));
    }


}

