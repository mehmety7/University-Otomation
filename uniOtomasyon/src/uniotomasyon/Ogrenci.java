
package uniotomasyon;
import java.io.*;
import java.util.Scanner;

public class Ogrenci extends Kullanici implements DersSecimi {
    private String ogrenciNo, bolum;
    private int dersSayisi;
    private final static String okul = "Istanbul Universitesi Cerrahpasa";
    private String[] dersler;

    @Override
    boolean girisYap()
    {
        /* kullanici.txt den input alınan tc ile ilgili satir verisi cekildi ve bu satirdaki bilgiler ust class ve bu classtaki field larda
        saklandi. Yine bu satirdaki sifre hucresiyle input alinan sifre eslestirmesi oldugu takdirde method boolean true dondurdu */

        /*Tc no 11 haneli mi kontrolu de ayrıca yapildi*/

        /* main de olusturulan nesne ile bilgiler dosyadan programa cekildi */

        dersler = new String[50];
        try {
            Scanner input = new Scanner(System.in);
            String words[], line, sifre, tc;
            String sentence, ogrenciWords[];

            File kullanici = new File("textFiles/kullanici.txt");
            File ogrenci = new File("textFiles/ogrenci.txt");
            if (!kullanici.exists() || !ogrenci.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return false;
            }
            boolean control = true;
            int a = 0;
            while(control){
                FileReader kullaniciOku = new FileReader(kullanici);
                BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku);
                FileReader ogrenciOku = new FileReader(ogrenci);
                BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku);
                System.out.print("Lutfen TC kimlik numaranızı tuslayiniz: ");
                tc = input.next();
                System.out.print("Lutfen sifrenizi tuslayiniz: ");
                sifre = input.next();
                sifre = sifre.trim();
                if(tc.length() == 11){
                    while ((line = kullaniciTampon.readLine()) != null) {
                        words = line.split(" ");
                        if(words[0].equalsIgnoreCase(tc)){
                            a++;
                            if(!(words[1].equals(sifre))){
                                System.out.print("Hatali sifre girisi, tekrar deneyiniz\n");
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
                                while((sentence = ogrenciTampon.readLine()) != null){
                                    ogrenciWords = (sentence.trim()).split(" ");
                                    if(ogrenciWords[0].equals(tcNo)){
                                        if(ogrenciWords.length > 3){
                                            for(int i = 4; i < 3 + Integer.parseInt(ogrenciWords[3]); i++){
                                                dersler[i-4] = ogrenciWords[i];
                                            }
                                            dersSayisi = Integer.parseInt(ogrenciWords[3]);
                                        }
                                        ogrenciNo = ogrenciWords[1];
                                        bolum = ogrenciWords[2];
                                    }
                                }
                                ogrenciTampon.close();
                                kullaniciTampon.close();
                                System.out.println("Hos geldin " + ad + " " + soyad);
                                return true;
                            }
                        }
                    }
                    if(a == 0){
                        System.out.print("Kimlik numarası hatalı, tekrar deneyiniz\n");
                    }
                }else
                    System.out.print("Kimlik numarası 11 haneli olmalıdır, tekrar deneyiniz\n");
            }
        }catch (IOException e) { System.out.println("Hata!");}
        return false;
    }

    @Override
    void kayitOl()
    {
        /*kullanicidan field lara atama seklinde input stringler alindi daha sonra tek bir string halinde kayit satiri
        olusturuldu. Ardından bu satir append modda kullanici.txt ve ogrenci.txt ye eklendi*/


        String kullanici, ogrenci, yoklamaKontrol;
        int secim;
        Scanner input = new Scanner(System.in);
        System.out.print("Lutfen ad giriniz(Bosluk birakmayiniz!): ");
        ad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen soyad giriniz(Bosluk bırakmayiniz): ");
        soyad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen mail giriniz(Bosluk bırakmayiniz): ");
        mail = ((input.next()).toLowerCase()).trim();
        System.out.print("Lutfen sifre giriniz: ");
        sifre = (input.next()).trim();
        do{
            System.out.print("Lutfen TC kimlik numarasi giriniz: ");
            tcNo = input.next();
            if(tcNo.length() != 11)
                System.out.println("Kimlik numarasi 11 haneli olmalidir!\n");
        }while(tcNo.length() != 11);
        System.out.print("Lutfen cep telefonu giriniz: ");
        gsmNo = input.next();
        boolean control = true;
        while(control){
            System.out.print("Lutfen cinsiyet seciniz 1 - KADIN 2 - ERKEK : ");
            secim = input.nextInt();
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
            secim = input.nextInt();
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
        System.out.print("Lutfen ogrenciNo giriniz: ");
        ogrenciNo = (input.next()).trim();
        System.out.print("Lutfen bolum giriniz(Bosluk bırakmayiniz): ");
        bolum = ((input.next()).toUpperCase()).trim();

        kullanici = tcNo + " " + sifre + " " + gorev + " " + ad + " "  + soyad + " " + mail + " " + gsmNo + " " + cinsiyet + "\n";
        ogrenci = tcNo + " " + ogrenciNo + " " + bolum;
        yoklamaKontrol = tcNo;

        try{
            File fileK = new File("textFiles/kullanici.txt");
            File fileO = new File("textFiles/ogrenci.txt");
            File yoklama = new File("textFiles/yoklama.txt");

            if (!fileK.exists())
                fileK.createNewFile();
            if(!fileO.exists())
                fileO.createNewFile();
            if(!yoklama.exists())
                yoklama.createNewFile();

            FileWriter fileWriter_K = new FileWriter(fileK, true);
            FileWriter fileWriter_O = new FileWriter(fileO, true);
            BufferedWriter bWriter_Y;
            try (BufferedWriter bWriter_K = new BufferedWriter(fileWriter_K); 
                    BufferedWriter bWriter_O = new BufferedWriter(fileWriter_O)) {
                FileWriter fileWriter_Y = new FileWriter(yoklama, true);
                bWriter_Y = new BufferedWriter(fileWriter_Y);
                bWriter_K.write(kullanici);
                bWriter_O.write(ogrenci + "\n");
                bWriter_Y.write(yoklamaKontrol + "\n");
            }
            bWriter_Y.close();

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    void bilgiGuncelle()
    {
        /* kayıt ol methodunda birkaç input alımı eksik hale getirildi, bolum ve ogrenci no degistirilebilir yapıldi
        temp dosyasına islem gören satir disinda diğer satirlar aynen yazildi yeni satir if karar yapısıyla tc
        eslestirilmesi ile eskisinin yerine yazildi ve temp dosyasinin adi degistirildi ogernci txt seklinde eski dosyalar silindi*/

        Scanner input = new Scanner(System.in);
        String line, ogrenciWords[], kullaniciWords[];

        try
        {
            File ogrenci = new File("textFiles/ogrenci.txt");
            File guncelOgrenci = new File("textFiles/temp.txt");
            File kullanici = new File("textFiles/kullanici.txt");
            File guncelKullanici = new File("textFiles/temp2.txt");

            if (!ogrenci.exists() || !kullanici.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }
            if(!guncelOgrenci.exists())
                guncelOgrenci.createNewFile();
            
            if(!guncelKullanici.exists())
                guncelKullanici.createNewFile();

            System.out.print("Lutfen yeni ad giriniz: ");
            ad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni soyad giriniz: ");
            soyad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni mail giriniz: ");
            mail = ((input.next()).toLowerCase()).trim();
            System.out.print("Lutfen yeni cep telefonu giriniz: ");
            gsmNo = input.next();
            System.out.print("Lutfen yeni cinsiyet seciniz 1 - KADIN 2 - ERKEK : ");
            int secim = input.nextInt();
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
            
            System.out.print("Lutfen yeni ogrenci numarasini giriniz: ");
            ogrenciNo = (input.next()).trim();
            System.out.print("Lutfen yeni bolumunuzu giriniz: ");
            bolum = ((input.next()).toUpperCase()).trim();
            


            String kullaniciSatiri = tcNo + " " + sifre + " " + gorev + " " + ad + " "  + soyad + " " + mail + " " + gsmNo + " " + cinsiyet;

            FileReader kullaniciOku = new FileReader(kullanici);
            BufferedWriter temp2Yaz;
            try (BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku)) {
            FileWriter fileWriter = new FileWriter(guncelKullanici);
            temp2Yaz = new BufferedWriter(fileWriter);
           
                while ((line = kullaniciTampon.readLine()) != null) {
                    kullaniciWords = line.split(" ");
                    if(kullaniciWords[0].equals(tcNo))
                        temp2Yaz.write(kullaniciSatiri + "\n");
                    else
                        temp2Yaz.write(line + "\n");
                }
            }
            temp2Yaz.close();

            kullanici.delete();

            guncelKullanici.renameTo(kullanici);

            FileReader ogrenciOku = new FileReader(ogrenci);
            BufferedWriter tempYaz;
            try (BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku)) {
                FileWriter fileWriter2 = new FileWriter(guncelOgrenci, false);
                tempYaz = new BufferedWriter(fileWriter2);
                while ((line = ogrenciTampon.readLine()) != null) {
                    ogrenciWords = (line.trim()).split(" ");
                    if(ogrenciWords[0].equals(tcNo)){
                        ogrenciWords[1] = ogrenciNo;
                        ogrenciWords[2] = bolum;
                        line = tcNo + " " + ogrenciNo + " " + bolum + " ";
                        if(ogrenciWords.length > 3){
                            for (int i = 3; i < ogrenciWords.length; i++) {
                                line = line + ogrenciWords[i] + " ";
                            }
                        }
                        line = (line.trim()) + "\n";
                    }
                    tempYaz.write(line);
                }
            }
            tempYaz.close();

            ogrenci.delete();

            guncelOgrenci.renameTo(ogrenci);

        }catch(IOException e){System.out.println("Hata!");}
    }


    @Override
    void bilgiGoruntule()
    {
        /*kullanici ve ogrenci txt dosyalarindan uygun satirlardan cekilen bilgiler ekrana gösterildi */

        String line, kullaniciWords[], ogrenciWords[];

        try
        {
            File ogrenci = new File("textFiles/ogrenci.txt");
            File kullanici = new File("textFiles/kullanici.txt");

            if (!kullanici.exists() || !ogrenci.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }

            FileReader kullaniciOku = new FileReader(kullanici);
            BufferedReader ogrenciTampon;
            BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku);
            FileReader ogrenciOku = new FileReader(ogrenci);
            ogrenciTampon = new BufferedReader(ogrenciOku);
            while ((line = kullaniciTampon.readLine()) != null) {
                kullaniciWords = (line.trim()).split(" ");
                if(kullaniciWords[0].equals(tcNo))
                    System.out.print
                        (
                        "TC kimlik no : " + kullaniciWords[0]
                        + "\nAd Soyad : " + kullaniciWords[3] + " " + kullaniciWords[4]
                        + "\nMail adresi : " + kullaniciWords[5]
                        + "\nCep Telefonu : " + kullaniciWords[6]
                        + "\nCinsiyet : " + kullaniciWords[7]
                        );
                }   while ((line = ogrenciTampon.readLine()) != null) {
                    ogrenciWords = (line.trim()).split(" ");
                    if(ogrenciWords[0].equals(tcNo)){
                        System.out.print
                            (
                            "\nOkulNo : " + ogrenciWords[1]
                            + "\nBolum adi : " + ogrenciWords[2]
                            );
                        if(ogrenciWords.length > 3){
                            System.out.print("\nAldigi Dersler : ");
                            for(int i = 4; i < 3 + Integer.parseInt(ogrenciWords[3]) ; i++){
                                System.out.print(ogrenciWords[i] + " ");
                            }
                        }
                    System.out.println();
                    }
                }
            ogrenciTampon.close();
            kullaniciTampon.close();

        }catch(IOException e){System.out.println("Hata!");}
    }

    void programGoruntule()
    {
        /*
            idare tarafından olusturulması gereken yoklama.txt nin olusmus olma durumunu sorgular.
            Varsa read modunda dosya isleme acilir ve her kayıt satırı bosluklardan split edilerek
            ders adı gunu ve saati olarak ekrana yazdırılır.
            Her ogrenci sectigi derslerin bilgisinin oldugu ayrı ayrı ders programını gorur
            ders arrayinin elemanı mıdır kontroluyle bu detaya yer verilmistir
        */

        String line, programWords[];

        try
        {
            File program = new File("textFiles/program.txt");

            if (!program.exists()){
                System.out.print("Henuz ders programi bulunmamaktadir. Lutfen idare ile gorusun!\n");
                return;
            }

            FileReader programOku = new FileReader(program);
            try (BufferedReader programTampon = new BufferedReader(programOku)) {
                System.out.println("DERS PROGRAMI");
                for(int i = 0; i < dersSayisi; i++){
                    while ((line = programTampon.readLine()) != null) {
                        programWords = line.split(" ");
                        if(programWords[0].equalsIgnoreCase(dersler[i])){
                            System.out.print
                                (
                                        "Ders : " + programWords[0]
                                                + " Gun : " + programWords[1]
                                                + " Saat : " + programWords[2] + "\n"
                                );
                            break;
                        }
                    }
                }
            }

        }catch(IOException e){System.out.println("Hata!");}
    }

    void notListele()
    {
        /*
            Ogrenci.txt de ders adları ve sonrasında vize1 vize2 ... final1 final2 .. seklinde formatlandıgından
            bu dosyayı read modda acıp dogru kayıt satırını bulup hucrelere bolerek vize notu final notu vs ayrı ayrı
            ekrana yazdırıldı
        */

                String line, ogrenciWords[];

        try
        {
            File ogrenci = new File("textFiles/ogrenci.txt");

            if (!ogrenci.exists()){
                System.out.print("Henuz ogrenci kaydi bulunmamaktadir. Lutfen kayit olun!\n");
                return;
            }

            FileReader ogrenciOku = new FileReader(ogrenci);
                    try (BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku)) {
                        System.out.println("Sınav Notları");
                        while ((line = ogrenciTampon.readLine()) != null) {
                            ogrenciWords = (line.trim()).split(" ");
                            if(ogrenciWords[0].equalsIgnoreCase(tcNo)){
                                if(ogrenciWords.length == 3){
                                    System.out.println("Henuz ders secimi yapilmamis. Not goruntulenemez!");
                                    return;
                                }
                                for(int j = 4; j < dersSayisi + 3; j++){
                                    System.out.print
                                (
                                        "Ders adi : " + ogrenciWords[j]
                                                + " Vize notu : " + ogrenciWords[j+dersSayisi]
                                                + " Final notu : " + ogrenciWords[j+(dersSayisi*2)]
                                                + " Butunleme notu : " + ogrenciWords[j+(dersSayisi*3)]
                                                + " Harf notu : " +ogrenciWords[j+(dersSayisi*4)]
                                                + "\n"
                                );
                                }
                            }
                        }       }

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    public void dersSec()
    {
        /*
            Her ogrenci ders secimi yapar. Bunun icin dersler txt den dersleri listeledim read mod ile.
            Ogrenci txt de aldıgı dersleri kayıt satırlarına ekelemek icin ders isimlerinin de oldugu aldıgım yeni
            kayıt satırını yeni bir kayıt satırı stringi yaptım ve temp dosyasına o satır yeni olacak sekilde write mod ile
            yazdırdım. Yoklama.txt ye de o ogrencinin o derse dair yoklama satirini olusturma islemini de gerceklestirdim.

            Interface implementi ile 21 kredilik ders limitini de kontrol etmis oldum.
        */

        Scanner input = new Scanner(System.in);
        String yeniKayit = "", line, ogrenciWords[], derslerWords[];

        try{
            File derslertxt = new File("textFiles/dersler.txt");
            File ogrenci = new File("textFiles/ogrenci.txt");
            File guncelOgrenci = new File("textFiles/temp.txt");
            File yoklama = new File("textFiles/yoklama.txt");


            if (!derslertxt.exists()){
                System.out.print("Henuz sistemde kayitli ders bulunmamaktadir. Lutfen once ders kaydi yapin!\n");
                return;
            }
            if (!ogrenci.exists()){
                System.out.print("Henuz sistemde kayitli ogrenci bulunmamaktadir. Lutfen once ogrenci kaydi yapin!\n");
                return;
            }
            if(!guncelOgrenci.exists())
                guncelOgrenci.createNewFile();

            FileReader derslerOku = new FileReader(derslertxt);
            BufferedReader derslerTampon = new BufferedReader(derslerOku);

            System.out.print("Dersler asagidadir ve yanlarinda kredisi yazmaktadir. 21 kredi donemlik limit bulunmaktadir. Almak istediginiz dersin basindaki sayiyi tek tek giriniz\n");
            int i = 1;
            while((line = derslerTampon.readLine()) != null){
                derslerWords = (line.trim()).split(" ");
                System.out.println(i + "- " + derslerWords[0] + "(" + derslerWords[1] + " kredi)");
                i++;
            }

            int sumKredi = toplamKredi;
            int count = 0;
            i = 0;
            dersler = new String[50];

            while(true){
                derslerTampon.close();
                derslerTampon = new BufferedReader(new FileReader(derslertxt));
                System.out.print(i+1 +  ". dersi sec: ");
                int secim = input.nextInt();
                while((line = derslerTampon.readLine()) != null){
                    derslerWords = (line.trim()).split(" ");
                    count++;
                    if(count == secim){
                        dersler[i] = derslerWords[0];
                        sumKredi = sumKredi - Integer.parseInt(derslerWords[1]);
                        System.out.println("Kalan kredi : " + sumKredi);
                        break;
                    }
                }
                count = 0;
                i++;
                if(sumKredi == 0){
                    System.out.println("21 krediyi tamamladiniz. Ders secimi tamamlandi..");
                    dersSayisi = i;
                    break;
                }

                if(sumKredi < 0){
                    System.out.println("21 krediyi astiniz. Tekrar bastan secim yapin : ");
                    i = 0;
                    sumKredi = toplamKredi;
                    count = 0;
                }

            }
            for(i = 0; i < dersSayisi; i++){
                yeniKayit += dersler[i] + " ";
            }

            yeniKayit = Integer.toString(dersSayisi) + " " + yeniKayit.trim() + " ";

            for(i = 0; i < dersSayisi*4; i++){
                yeniKayit = yeniKayit + "Girilmedi" + " ";
            }

            yeniKayit = " " + yeniKayit.trim() + "\n";

            FileReader ogrenciOku = new FileReader(ogrenci);
            BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku);
            FileWriter fileWriter = new FileWriter(guncelOgrenci);
            BufferedWriter tempYaz = new BufferedWriter(fileWriter);
            FileWriter yoklamaYaz = new FileWriter(yoklama);
            BufferedWriter yoklamaTampon = new BufferedWriter(yoklamaYaz);

            while ((line = ogrenciTampon.readLine()) != null) {
                ogrenciWords = (line.trim()).split(" ");
                yoklamaTampon.write(ogrenciWords[0] + "\n");
                if(ogrenciWords[0].equals(tcNo)){
                    line = line.trim() + yeniKayit;
                    for(i = 0; i < dersSayisi; i++){
                        yoklamaTampon.write(dersler[i] + "\n");
                    }
                }
                tempYaz.write(line + "\n");
            }


            tempYaz.close();
            derslerTampon.close();
            ogrenciTampon.close();
            yoklamaTampon.close();

            ogrenci.delete();

            guncelOgrenci.renameTo(ogrenci);

        }catch(IOException e){System.out.println("Hata!");}
    }
}

interface DersSecimi {
    int toplamKredi = 21;
    void dersSec();
}
