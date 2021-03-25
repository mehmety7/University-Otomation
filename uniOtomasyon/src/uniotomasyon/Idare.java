
package uniotomasyon;

import java.io.*;
import java.util.Scanner;


public class Idare extends Calisan implements DersProgrami {
    private String personelNo;

    @Override
    boolean girisYap() //İdare icin giris yap implemente edildi
    {
        /* kullanici.txt den input alınan tc ile ilgili satir verisi cekildi ve bu satirdaki bilgiler ust class ve bu classtaki field larda
        saklandi. Yine bu satirdaki sifre hucresiyle input alinan sifre eslestirmesi oldugu takdirde method boolean true dondurdu */

        /*Tc no 11 haneli mi kontrolu de ayrıca yapildi*/

        /* main de olusturulan nesne ile bilgiler dosyadan programa cekildi */
        try {
            Scanner input = new Scanner(System.in);
            String words[], line, sifre, tc;
            String sentence, idrWords[];
            File kullanici = new File("textFiles/kullanici.txt");
            File idr = new File("textFiles/idare.txt");
            if (!kullanici.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return false;
            }
            boolean control = true;
            int a = 0;
            while(control){
                FileReader kullaniciOku = new FileReader(kullanici);
                BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku);
                FileReader idrOku = new FileReader(idr);
                BufferedReader idrTampon = new BufferedReader(idrOku);
                System.out.print("Lutfen TC kimlik numaranızı tuslayiniz: ");
                tc = input.next();
                System.out.print("Lutfen sifrenizi tuslayiniz: ");
                sifre = input.next().trim();
                if(tc.length() == 11){
                    while ((line = kullaniciTampon.readLine()) != null) {
                        words = line.split(" ");
                        if(words[0].equalsIgnoreCase(tc)){
                            a++;
                            if(!(words[1].equals(sifre))){
                                System.out.print("Hatali sifre girisi, tekrar deneyiniz\n ");
                                kullaniciTampon.close();
                                break;
                            }else{
                                System.out.println("Giris islemi basarili. Lutfen bekleyiniz..");
                                this.tcNo = tc.trim();
                                this.sifre = sifre.trim();
                                this.gorev = words[2].trim();
                                this.ad = words[3].trim();
                                this.soyad = words[4].trim();
                                this.mail = words[5].trim();
                                this.gsmNo = words[6].trim();
                                this.cinsiyet = words[7].trim();
                                control = false;
                                while((sentence = idrTampon.readLine()) != null){
                                    idrWords = (sentence.trim()).split(" ");
                                    if(idrWords[0].equals(tcNo)){
                                        unvan = idrWords[2];
                                        personelNo = idrWords[1];
                                    }
                                }
                                idrTampon.close();
                                kullaniciTampon.close();
                                System.out.println("Hos geldin " + ad + " " + soyad);
                                return true;
                            }
                        }
                    }
                    if(a == 0)
                        System.out.print("Kimlik numarası hatalı, tekrar deneyiniz\n");
                }else
                    System.out.print("Kimlik numarası 11 haneli olmalıdır, tekrar deneyiniz\n");
            }
        }catch (IOException e) { System.out.println("Hata!");}
        return false;
    }

    void dersOlustur() // sisteme ders ekleme methodu yazildi.
    {
        /* dersler.txt varsa append turunde yoksa yeniden olusturma usuluyle implemente edildi
        icine string turde ders ve kredisini yerlesitrecek sekilde kullanicidan istendi
        dosyaya "ders1 kredi1" satir duzeniyle yazıldı */

        Scanner input = new Scanner(System.in);
        System.out.print("Dersin adini giriniz (Bosluk bırakmayiniz!)");
        String ders = ((input.next()).toUpperCase()).trim();
        System.out.print("Dersin kredisini giriniz : ");
        int kredi = input.nextInt();
        String satir = ders + " " + Integer.toString(kredi);
        try {
            File fileD = new File("textFiles/dersler.txt");
            if (!fileD.exists())
                fileD.createNewFile();

            FileWriter fileWriter_D = new FileWriter(fileD, true);
            try (BufferedWriter bWriter_D = new BufferedWriter(fileWriter_D)) {
                bWriter_D.write(satir + "\n");
            }
        } catch (IOException ex) { System.out.println("Hata!"); }
    }

    @Override
    void kayitOl() //idare kayıt olusturma methodu implementasyonu
    {
        /*kullanicidan field lara atama seklinde input stringler alindi daha sonra tek bir string halinde kayit satiri
        olusturuldu. Ardından bu satir append modda kullanici.txt ve idare.txt ye eklendi*/

        String kullanici, idariMemur, line;
        Scanner input = new Scanner(System.in);

        System.out.print("Lutfen adinizi giriniz(Bosluk bırakmayiniz) : ");
        ad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen soy adinizi giriniz(Bosluk bırakmayiniz) : ");
        soyad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen mail adresinizi giriniz(Bosluk bırakmayiniz) : ");
        mail = ((input.next()).toLowerCase()).trim();
        System.out.print("Lutfen sifre olusturunuz(Bosluk bırakmayiniz) : ");
        sifre = (input.next()).trim();
        do{
            System.out.print("Lutfen TC kimlik numarasi giriniz(Bosluk bırakmayiniz): ");
            tcNo = input.next();
            if(tcNo.length() != 11)
                System.out.println("Kimlik numarasi 11 haneli olmalidir!\n");
        }while(tcNo.length() != 11);
        System.out.print("Lutfen cep telefonu giriniz(Bosluk bırakmayiniz) : ");
        gsmNo = input.next();
        boolean control = true;
        while(control){
            System.out.print("Lutfen cinsiyet seciniz 1 - KADIN 2 - ERKEK : ");
            int secim = input.nextInt();
            switch(secim){
                case 1 :
                    cinsiyet = "KADIN";
                    control = false;
                    break;
                case 2 :
                    cinsiyet = "ERKEK";
                    control = false;
                    break;
                default :
                    System.out.print("1 ya da 2 degerlerini giriniz!\n");
                }
        }
        control = true;
        while(control){
            System.out.print("Lutfen gorev seciniz 1 - Ogrenci 2 - Ogretim Uyesi 3 - İdari Memur : ");
            int secim = input.nextInt();
            switch(secim){
                case 1 :
                    gorev = "ogrenci";
                    control = false;
                    break;
                case 2 :
                    gorev = "akademisyen";
                    control = false;
                    break;
                case 3 :
                    gorev = "idare";
                    control = false;
                    break;
                default :
                    System.out.print("1 , 2 ya da 3 degerlerini giriniz!\n");
            }
        }
        System.out.print("Lutfen personel numarasi giriniz : ");
        personelNo = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen statunuzu(is pozisyonu) belirtiniz (Bosluk bırakmayiniz): ");
        this.unvan = ((input.next()).toUpperCase()).trim();

        kullanici = tcNo + " " + sifre + " " + gorev + " " + ad + " "  + soyad + " " + mail + " " + gsmNo + " " + cinsiyet;
        idariMemur = tcNo + " " + unvan + " " + personelNo;


        try
        {
            File fileK = new File("textFiles/kullanici.txt");
            File fileI = new File("textFiles/idare.txt");

            if (!fileK.exists())
                fileK.createNewFile();
            if(!fileI.exists())
                fileI.createNewFile();

            FileWriter fileWriter_K = new FileWriter(fileK, true);
            FileWriter fileWriter_I = new FileWriter(fileI, true);
            BufferedWriter bWriter_I;
            try (BufferedWriter bWriter_K = new BufferedWriter(fileWriter_K)) {
                bWriter_I = new BufferedWriter(fileWriter_I);
                bWriter_K.write(kullanici + "\n");
                bWriter_I.write(idariMemur + "\n");
            }
            bWriter_I.close();

        } catch (IOException ex) { System.out.println("Hata!"); }
    }

    @Override
    void bilgiGuncelle()
    {
        /* kayıt ol methodunda birkaç input alımı eksik hale getirildi, degismemesi gereken seyler TC sifre vs.
        temp dosyasına islem gören satir disinda diğer satirlar aynen yazildi yeni satir if karar yapısıyla tc
        eslestirilmesi ile eskisinin yerine yazildi ve temp dosyalarının adi degistirildi kullanici ve idare txt seklinde eski dosyalar silindi*/

        Scanner input = new Scanner(System.in);
        String line, kullaniciWords[];
        String kullaniciSatiri;
        int secim;

        try
        {
            File kullanici = new File("textFiles/kullanici.txt");
            File guncelKullanici = new File("textFiles/temp.txt");

            if (!kullanici.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }
            if(!guncelKullanici.exists())
                guncelKullanici.createNewFile();

            System.out.print("Lutfen yeni ad giriniz(Bosluk bırakmayiniz): ");
            ad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni soyad giriniz(Bosluk bırakmayiniz): ");
            soyad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni mail giriniz(Bosluk bırakmayiniz): ");
            mail = ((input.next()).toLowerCase()).trim();
            System.out.print("Lutfen yeni cep telefonu giriniz(Bosluk bırakmayiniz): ");
            gsmNo = input.next();
            System.out.print("Lutfen yeni cinsiyet seciniz 1 - KADIN 2 - ERKEK : ");
            secim = input.nextInt();
            switch(secim){
                case 1 :
                    cinsiyet = "KADIN";
                    break;
                case 2 :
                    cinsiyet = "ERKEK";
                    break;
                default :
                    System.out.print("1 ya da 2 degerlerini giriniz!\n");
            }

            kullaniciSatiri = tcNo + " " + sifre + " " + gorev + " " + ad + " "  + soyad + " " + mail + " " + gsmNo + " " + cinsiyet + "\n";

            FileReader kullaniciOku = new FileReader(kullanici);
            BufferedWriter tempYaz;
            try (BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku)) {
                FileWriter fileWriter = new FileWriter(guncelKullanici);
                tempYaz = new BufferedWriter(fileWriter);
                while ((line = kullaniciTampon.readLine()) != null) {
                    kullaniciWords = line.split(" ");
                    if(kullaniciWords[0].equals(tcNo))
                        tempYaz.write(kullaniciSatiri);
                    else
                        tempYaz.write(line + "\n");
                }
            }
            tempYaz.close();

            kullanici.delete();

            guncelKullanici.renameTo(kullanici);

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    void bilgiGoruntule()
    {
        /*kullanici ve idare txt dosyalarindan uygun satirlardan cekilen bilgiler ekrana gösterildi */

        String line, kullaniciWords[], idareWords[];

        try
        {
            File idare = new File("textFiles/idare.txt");
            File kullanici = new File("textFiles/kullanici.txt");

            if (!kullanici.exists() || !idare.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }

                FileReader kullaniciOku = new FileReader(kullanici);
                BufferedReader idareTampon;
                try (BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku)){
                FileReader akademisyenOku = new FileReader(idare);
                idareTampon = new BufferedReader(akademisyenOku);
                    while ((line = kullaniciTampon.readLine()) != null) {
                        kullaniciWords = line.split(" ");
                        if(kullaniciWords[0].equals(tcNo))
                            System.out.print
                                (
                                "TC kimlik no : " + kullaniciWords[0]
                                + "\nAd Soyad : " + kullaniciWords[3] + " " + kullaniciWords[4]
                                + "\nMail adresi : " + kullaniciWords[5]
                                + "\nCep Telefonu : " + kullaniciWords[6]
                                + "\nCinsiyet : " + kullaniciWords[7]
                                );
                    }   
                }
                while ((line = idareTampon.readLine()) != null) {
                    idareWords = (line.trim()).split(" ");
                    if(idareWords[0].equals(tcNo)){
                        System.out.print
                            (
                            "\nPersonel Numarasi : " + idareWords[1]
                            + "\nStatu(Pozisyon) : " + idareWords[2]
                            );
                        System.out.println();
                    }
                }
            idareTampon.close();

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    void unvanDegistir()
    {
        /* kayıt satırındaki uygun hucrenin yerine yeni input alındı ve temp dosyasına yeni haliyle yazılması adının degistirilmesi ve
        eskisinin silinmesi yoluyla guncelleme yapıldı */

        Scanner input = new Scanner(System.in);
        String line, idareWords[];

        try
        {
            File fileI = new File("textFiles/idare.txt");
            File guncelFileI = new File("textFiles/temp.txt");

            if (!fileI.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }
            if(!guncelFileI.exists())
                guncelFileI.createNewFile();

            System.out.print("Lutfen yeni unvani giriniz(Bosluk bırakmayiniz): ");
            this.unvan = ((input.next()).toUpperCase()).trim();

                FileReader idareOku = new FileReader(fileI);
            try (BufferedReader idareTampon = new BufferedReader(idareOku)) {
                FileWriter fileWriter = new FileWriter(guncelFileI);
                try (BufferedWriter tempYaz = new BufferedWriter(fileWriter)) {
                    while ((line = idareTampon.readLine()) != null) {
                        idareWords = line.split(" ");
                        if(idareWords[0].equals(tcNo)){
                            idareWords[2] = this.unvan;
                            line = "";
                            for (String idareWord : idareWords) {
                                line += idareWord + " ";
                            }
                        }
                        tempYaz.write(line.trim() + "\n");
                    }
                }
            }

            fileI.delete();

            guncelFileI.renameTo(fileI);

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    public void programHazirla()
    {
        /* program.txt dosyasının yoksa olusturulması yapıldı. Kullanıcıdan interfacede belirtilen final elementlerin
        secimi sonucu gün saat aralıgı ve dersler txt den cekilen derslerin secimi istendi. Ders programı olusturuldu */

        Scanner input = new Scanner(System.in);
        String line, dersWords[], gun = ".", saat = ".", kayit, ders = ".";
        boolean control = true;

        try
        {
            File program = new File("textFiles/program.txt");
            File dersler = new File("textFiles/dersler.txt");
            if(!program.exists())
                program.createNewFile();


            FileReader dersOku = new FileReader(dersler);
            BufferedReader dersTampon = new BufferedReader(dersOku);
            FileWriter fileWriter = new FileWriter(program, false);
            BufferedWriter programYaz = new BufferedWriter(fileWriter);
            while(control){
                System.out.println("1- Pazartesi \n2- Salı \n3- Carsamba \n4- Persembe \n5- Cuma");
                System.out.print("Lutfen gun seciniz: ");
                int gunNo = input.nextInt();
                System.out.println("1- 08:30-09:20 \n2- 09:25-10:15 \n3- 10:20-11:10 \n4- 12:10-13:00 \n5- 13:05-13:55 \n6- 14:55-15:45 \n7- 15:50-16:40");
                System.out.print("Lutfen saat seciniz: ");
                int saatNo = input.nextInt();
                int i = 1;
                while((line = dersTampon.readLine()) != null){
                    dersWords = (line.trim()).split(" ");
                    System.out.println(i + "- " + dersWords[0]);
                    i++;
                }
                System.out.print("Lutfen ders ismini listedeki gibi giriniz: ");
                ders = input.next().toUpperCase();
                switch(gunNo){
                    case 1 :
                        gun = gunler[0];
                            switch(saatNo){
                                case 1 :
                                    saat = saatler[0];
                                    break;
                                case 2 :
                                    saat = saatler[1];
                                    break;
                                case 3 :
                                    saat = saatler[2];
                                    break;
                                case 4 :
                                    saat = saatler[3];
                                    break;
                                case 5 :
                                    saat = saatler[4];
                                    break;
                                case 6 :
                                    saat = saatler[5];
                                    break;
                            }
                        break;
                    case 2 :
                        gun = gunler[1];
                        switch(saatNo){
                            case 1 :
                                saat = saatler[0];
                                break;
                            case 2 :
                                saat = saatler[1];
                                break;
                            case 3 :
                                saat = saatler[2];
                                break;
                            case 4 :
                                saat = saatler[3];
                                break;
                            case 5 :
                                saat = saatler[4];
                                break;
                            case 6 :
                                saat = saatler[5];
                                break;
                        }
                        break;
                    case 3 :
                        gun = gunler[2];
                        switch(saatNo){
                            case 1 :
                                saat = saatler[0];
                                break;
                            case 2 :
                                saat = saatler[1];
                                break;
                            case 3 :
                                saat = saatler[2];
                                break;
                            case 4 :
                                saat = saatler[3];
                                break;
                            case 5 :
                                saat = saatler[4];
                                break;
                            case 6 :
                                saat = saatler[5];
                                break;
                        }
                        break;
                    case 4 :
                        gun = gunler[3];
                        switch(saatNo){
                            case 1 :
                                saat = saatler[0];
                                break;
                            case 2 :
                                saat = saatler[1];
                                break;
                            case 3 :
                                saat = saatler[2];
                                break;
                            case 4 :
                                saat = saatler[3];
                                break;
                            case 5 :
                                saat = saatler[4];
                                break;
                            case 6 :
                                saat = saatler[5];
                                break;
                        }
                        break;
                    case 5 :
                        gun = gunler[4];
                        switch(saatNo){
                            case 1 :
                                saat = saatler[0];
                                break;
                            case 2 :
                                saat = saatler[1];
                                break;
                            case 3 :
                                saat = saatler[2];
                                break;
                            case 4 :
                                saat = saatler[3];
                                break;
                            case 5 :
                                saat = saatler[4];
                                break;
                            case 6 :
                                saat = saatler[5];
                                break;
                        }
                        break;
                }
                kayit = ders + " " + gun + " " + saat + "\n";
                programYaz.write(kayit);
                kayit = ".";
                System.out.print("0- Bitir 1- Devam et \n");
                System.out.print("İslemi bitirmek ister misiniz? : ");
                int ctrl = input.nextInt();
                if(ctrl == 0)
                    control = false;
            }
            programYaz.close();
            dersTampon.close();

        }catch(IOException e){System.out.println("Hata!");}
    }

   /* void kullaniciSil()
    {
        Scanner input = new Scanner(System.in);
        String line, kullaniciWords[];
        String kullaniciSatiri;
        int secim;

        try
        {
            File kullanici = new File("kullanici.txt");
            File guncelKullanici = new File("tempK.txt");
            File ogrenci = new File("ogrenci.txt");
            File guncelOgrenci = new File("tempO.txt");
            File yoklama = new File("yoklama.txt");
            File guncelYoklama = new File("tempY.txt");

            if (!kullanici.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }
            if(!guncelKullanici.exists())
                guncelKullanici.createNewFile();

            System.out.print("Lutfen TcNo giriniz: ");
            tcNo = ((input.next()).toUpperCase()).trim();

            FileReader kullaniciOku = new FileReader(kullanici);
            BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku);
            FileWriter fileWriter = new FileWriter(guncelKullanici);
            BufferedWriter tempYaz = new BufferedWriter(fileWriter);
            while ((line = kullaniciTampon.readLine()) != null) {
                kullaniciWords = line.split(" ");
                if(kullaniciWords[0].equals(tcNo))
                    continue;
                else
                    tempYaz.write(line);
            }
            tempYaz.close();
            kullaniciTampon.close();

            kullanici.delete();

            guncelKullanici.renameTo(kullanici);

        }catch(IOException e){System.out.println("Hata!");}     */

}

interface DersProgrami
{
    String [] gunler = {"Pazartesi" , "Sali" , "Carsamba" , "Persembe" , "Cuma"};
    String [] saatler = {"08:30-09:20" , "09:25-10:15" , "10:20-11:10" , "12:10-13:00" , "13:05-13:55" , "14:55-15:45 " , "15:50-16:40" };
    void programHazirla();
}
