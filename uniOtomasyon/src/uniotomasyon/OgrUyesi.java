
package uniotomasyon;

import java.io.*;
import java.util.Scanner;



public class OgrUyesi extends Calisan implements SınavOran
{
    /*akademisyenin verdigi derslerin tutuldugu statik dizi ve bunun int tipte sayisi field olarak olusturuldu.
    Harfnotu icin lazım ola sınav notu oranlarının tutuldugu interface implemente edildi
    Üst soyut sınıf calisandan extends edildi
    Giris yap, bilgi güncelle, bilgi görüntüle dosya islemleri (okuma yazma goruntuleme) ile override edildi*/

    private String[] ders;
    private int dersSayisi;

    @Override
    boolean girisYap()
    {
        /* kullanici.txt den input alınan tc ile ilgili satir verisi cekildi ve bu satirdaki bilgiler ust class ve bu classtaki field larda
        saklandi. Yine bu satirdaki sifre hucresiyle input alinan sifre eslestirmesi oldugu takdirde method boolean true dondurdu */

        /*Tc no 11 haneli mi kontrolu de ayrıca yapildi*/

        /* main de olusturulan nesne ile bilgiler dosyadan programa cekildi */

        try {
            Scanner input = new Scanner(System.in);
            String words[], line, sifre, tc;
            String sentence, ogrWords[];
            ders = new String[20];
            File kullanici = new File("textFiles/kullanici.txt");
            if (!kullanici.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return false;
            }
            boolean control = true;
            int a = 0;
            while(control){
                FileReader kullaniciOku = new FileReader(kullanici);
                BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku);
                File ogr = new File("textFiles/akademisyen.txt");
                FileReader ogrOku = new FileReader(ogr);
                BufferedReader ogrTampon = new BufferedReader(ogrOku);
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
                                while((sentence = ogrTampon.readLine()) != null){
                                    ogrWords = (sentence.trim()).split(" ");
                                    if(ogrWords[0].equals(tcNo)){
                                        dersSayisi = Integer.parseInt(ogrWords[2]);
                                        for(int i = 3; i < 3 + Integer.parseInt(ogrWords[2]); i++){
                                            ders[i-3] = ogrWords[i];
                                        }
                                        unvan = ogrWords[1];
                                    }
                                }
                                ogrTampon.close();
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


    @Override
    void kayitOl()
    {
        /*kullanicidan field lara atama seklinde input stringler alindi daha sonra tek bir string halinde kayit satiri
        olusturuldu. Ardından bu satir append modda kullanici.txt ve akademisyen.txt ye eklendi*/

        int secim;
        String kullanici, akademisyen, line;
        Scanner input = new Scanner(System.in);
        System.out.print("Lutfen gorevlisi oldugunuz ders sayisini giriniz: ");
        dersSayisi = input.nextInt();
        ders = new String[20];

        try{
            File fileD = new File("textFiles/dersler.txt");
            if(!fileD.exists()){
                System.out.print("Ders atama islemi icin once derslerin kaydini giriniz!\n");
                return;
            }

            FileReader fileReader_D = new FileReader(fileD);
            BufferedReader bReader_D = new BufferedReader(fileReader_D);

            while((line = bReader_D.readLine()) != null){
                System.out.print(line.trim() + " ");
            }

        }catch (IOException ex) { System.out.println("Hata!");}

        System.out.print("\nDersler yukarida listelenmistir. İsimlerini listedeki gibi sirayla giriniz:\n");

        int i = 0;
        while(i < dersSayisi){
            String a = input.next();
            this.ders[i] = a.toUpperCase();
            i++;
            if(dersSayisi == i)
                System.out.println("Ders kaydi basarili");
        }

        System.out.print("Lutfen ad giriniz(Bosluk bırakmayiniz): ");
        ad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen soyad giriniz(Bosluk bırakmayiniz): ");
        soyad = ((input.next()).toUpperCase()).trim();
        System.out.print("Lutfen mail giriniz(Bosluk bırakmayiniz): ");
        mail = ((input.next()).toLowerCase()).trim();
        System.out.print("Lutfen sifre giriniz(Bosluk bırakmayiniz): ");
        sifre = (input.next()).trim();
        do{
            System.out.print("Lutfen TC kimlik numarasi giriniz(Bosluk bırakmayiniz): ");
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
        System.out.print("Lutfen unvaninizi giriniz(Bosluk bırakmayiniz): ");
        this.unvan = ((input.next()).toUpperCase()).trim();

        kullanici = tcNo + " " + sifre + " " + gorev + " " + ad + " "  + soyad + " " + mail + " " + gsmNo + " " + cinsiyet + "\n";
        akademisyen = tcNo + " " + this.unvan + " " + Integer.toString(dersSayisi);

        for(i = 0; i < dersSayisi; i++){
            akademisyen = akademisyen +  " " + ders[i];
        }
        akademisyen = akademisyen + "\n";

        try
        {

            File fileK = new File("textFiles/kullanici.txt");
            File fileA = new File("textFiles/akademisyen.txt");

            if (!fileK.exists())
                fileK.createNewFile();
            if(!fileA.exists())
                fileA.createNewFile();

            FileWriter fileWriter_K = new FileWriter(fileK, true); // append mod
            FileWriter fileWriter_A = new FileWriter(fileA, true); // append mod
            BufferedWriter bWriter_A;
            try (BufferedWriter bWriter_K = new BufferedWriter(fileWriter_K)) {
                bWriter_A = new BufferedWriter(fileWriter_A);
                bWriter_K.write(kullanici);
                bWriter_A.write(akademisyen);
            }
            bWriter_A.close();

        } catch (IOException ex) { System.out.println("Hata!");}
    }

    @Override
    void bilgiGuncelle()
    {
        /* kayıt ol methodunda birkaç input alımı eksik hale getirildi, degismemesi gereken seyler TC sifre vs.
        temp dosyasına islem gören satir disinda diğer satirlar aynen yazildi yeni satir if karar yapısıyla tc
        eslestirilmesi ile eskisinin yerine yazildi ve temp dosyalarının adi degistirildi kullanici ve akademisyen txt seklinde eski dosyalar silindi*/

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

            System.out.print("Lutfen yeni ad giriniz: ");
            ad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni soyad giriniz: ");
            soyad = ((input.next()).toUpperCase()).trim();
            System.out.print("Lutfen yeni mail giriniz: ");
            mail = ((input.next()).toLowerCase()).trim();
            System.out.print("Lutfen yeni cep telefonu giriniz: ");
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
        /*kullanici ve akademisyen txt dosyalarindan uygun satirlardan cekilen bilgiler ekrana gösterildi */

        String line, kullaniciWords[], akademisyenWords[];

        try
        {
            File akademisyen = new File("textFiles/akademisyen.txt");
            File kullanici = new File("textFiles/kullanici.txt");

            if (!kullanici.exists() || !akademisyen.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }

            FileReader kullaniciOku = new FileReader(kullanici);
            BufferedReader akademisyenTampon;
            try (BufferedReader kullaniciTampon = new BufferedReader(kullaniciOku)) {
                FileReader akademisyenOku = new FileReader(akademisyen);
                akademisyenTampon = new BufferedReader(akademisyenOku);
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
                }   while ((line = akademisyenTampon.readLine()) != null) {
                    akademisyenWords = (line.trim()).split(" ");
                    if(akademisyenWords[0].equals(tcNo)){
                        System.out.print
                            (
                            "\nUnvan : " + akademisyenWords[1]
                            + "\nGorevli Oldugu Dersler : "
                            );
                        for(int i = 3; i < akademisyenWords.length ; i++){
                            System.out.print(akademisyenWords[i]);
                        }
                        System.out.println();
                    }
                }
            }
            akademisyenTampon.close();

        }catch(IOException e){System.out.println("Hata!");}
    }

    @Override
    void unvanDegistir()
    {
        /* kayıt satırındaki uygun hucrenin yerine yeni input alındı ve temp dosyasına yeni haliyle yazılması adının degistirilmesi ve
        eskisinin silinmesi yoluyla guncelleme yapıldı */

        Scanner input = new Scanner(System.in);
        String line, akademisyenWords[];

        try
        {
            File fileA = new File("textFiles/idare.txt");
            File guncelFileA = new File("textFiles/temp.txt");

            if (!fileA.exists()){
                System.out.print("Henuz kayit bulunmamaktadir. Lutfen once kisi kaydi yapin!\n");
                return;
            }
            if(!guncelFileA.exists())
                guncelFileA.createNewFile();

            System.out.print("Lutfen yeni unvani giriniz: ");
            this.unvan = ((input.next()).toUpperCase()).trim();

            FileReader akademisyenOku = new FileReader(fileA);
            BufferedWriter tempYaz;
            try (BufferedReader akademisyenTampon = new BufferedReader(akademisyenOku)) {
                FileWriter fileWriter = new FileWriter(guncelFileA);
                tempYaz = new BufferedWriter(fileWriter);
                while ((line = akademisyenTampon.readLine()) != null) {
                    akademisyenWords = (line.trim()).split(" ");
                    if(akademisyenWords[0].equals(tcNo)){
                        akademisyenWords[1] = this.unvan;
                        line = "";
                        for (String akademisyenWord : akademisyenWords) {
                            line += akademisyenWord + " ";
                        }
                    tempYaz.write(line.trim() + "\n");
                    }
                }
            }
            tempYaz.close();

            fileA.delete();

            guncelFileA.renameTo(fileA);

        }catch(IOException e){System.out.println("Hata!");}
    }


    @Override
    public void HarfNotuOlustur()
    {
        /* girisin ardından field da saklanan ders arrayinden akademisyenin gorevli oldugu dersler goruntulendi
        dersin adi ve ogrencinin okul numarasi (ogrenci txt read modunda acildi) alındi eslestirildi
        harf notu matematiksel islemlerle olusturuldu cıkan nota gore
        dosyaya yazıldı
        Ekrana gecti / kaldi uyarisi verildi
        Kayıt son haliyle ogrenci txt ye temp dosyasına sadece o satir degisecek sekilde yeniden yazildi eskisi silindi
        yenisinin adı (temp --> ogrenci seklinde)degistrildi  */


        int vizeNotu = -1 , finalNotu = -1 , butNotu = -1;
        double ortalama;
        Scanner input = new Scanner(System.in);
        String harfNotu = "Girilmedi", line, ogrenciWords[];
        File ogrenci = new File("textFiles/ogrenci.txt");
        File guncelOgrenci = new File("textFiles/temp.txt");

        try{

            if (!ogrenci.exists()){
                System.out.print("Henuz sistemde kayitli ogrenci bulunmamaktadir. Lutfen once ogrenci kaydi yapin!\n");
                return;
            }
            if(!guncelOgrenci.exists())
                guncelOgrenci.createNewFile();

        }catch(IOException e){System.out.println("Hata!");}

        System.out.print("Gorevli Oldugunuz Dersler : ");
        for(int i = 0; i < dersSayisi; i++){
            System.out.print(ders[i] + " ");
        }
        System.out.println();
        System.out.print("Harf notu belirleme islemi yapacaginiz dersin adini giriniz: ");
        String notDersi = input.next();
        System.out.print("Harf notu islemi yapacaginiz ogrencinin okul numarasini giriniz: ");
        String okulNo = (input.next()).trim();

        String kayitSatiri = "";
        try{
            FileReader ogrenciOku = new FileReader(ogrenci);
            try (BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku)) {
                FileWriter fileWriter = new FileWriter(guncelOgrenci);
                try (BufferedWriter tempYaz = new BufferedWriter(fileWriter)) {
                    int ogrenciDersSayisi = 0;
                    while ((line = ogrenciTampon.readLine()) != null) {
                        ogrenciWords = (line.trim()).split(" ");
                        if(ogrenciWords[1].equals(okulNo)){
                            if(ogrenciWords.length > 3)
                                ogrenciDersSayisi = Integer.parseInt(ogrenciWords[3]);
                            else{
                                System.out.println("Ogrenci bu dersin secimini yapmamistir!");
                                return;
                            }
                            for(int i = 4; i < ogrenciDersSayisi; i++){
                                if(ogrenciWords[i].equalsIgnoreCase(notDersi)){
                                    if(!ogrenciWords[i+ogrenciDersSayisi].equalsIgnoreCase("Girilmedi"))
                                        vizeNotu = Integer.parseInt(ogrenciWords[i+ogrenciDersSayisi]);
                                    if(!ogrenciWords[i+(2*ogrenciDersSayisi)].equalsIgnoreCase("Girilmedi"))
                                        finalNotu = Integer.parseInt(ogrenciWords[i+(2 * ogrenciDersSayisi)]);
                                    if(!ogrenciWords[i+(3*ogrenciDersSayisi)].equalsIgnoreCase("Girilmedi"))
                                        butNotu = Integer.parseInt(ogrenciWords[i+(3 * ogrenciDersSayisi)]);
                                    if(vizeNotu == -1){
                                        System.out.println("Henuz harf notu olusacak sekilde not girisi tamamlanmamistir!");
                                        return;
                                    }
                                    if(butNotu == -1)
                                        ortalama = finalNotu*bitirmeOran + vizeNotu*vizeOran;
                                    else
                                        ortalama = butNotu*butOran + vizeNotu*vizeOran;
                                    
                                    if(ortalama >= gecmeNotu)
                                        System.out.println("Ogrencinin bu ders icin donem notu : " + 
                                                ortalama + "\nTebrikler, ogrenci bu dersten gecti!");
                                    else
                                        System.out.println("Ogrencinin bu ders icin donem notu : " + 
                                                ortalama + "Ogrenci bu dersten gecemedi!");
                                    
                                    if(ortalama >= 94)
                                        harfNotu = "AA";
                                    if(ortalama >= 88 && ortalama < 93)
                                        harfNotu = "BA";
                                    if(ortalama >= 77 && ortalama < 88)
                                        harfNotu = "BB";
                                    if(ortalama >= 65 && ortalama < 77)
                                        harfNotu = "BC";
                                    if(ortalama >= 54 && ortalama < 65)
                                        harfNotu = "CC";
                                    if(ortalama >= 42 && ortalama < 54)
                                        harfNotu = "DC";
                                    if(ortalama >= 30 && ortalama < 42)
                                        harfNotu = "DD";
                                    if(ortalama >= 0 && ortalama < 30)
                                        harfNotu = "FF";
                                    
                                    ogrenciWords[i + (4 * ogrenciDersSayisi)] = harfNotu;
                                    break;
                                }
                            }
                            System.out.println("Ogrencinin bu ders icin harf notu : " + harfNotu);
                            
                            for(int i = 0; i < ogrenciWords.length; i++){
                                kayitSatiri += ogrenciWords[i] + " ";
                            }
                        }
                        tempYaz.write(kayitSatiri.trim() + "\n");
                    }
                }
            }

            ogrenci.delete();

            guncelOgrenci.renameTo(ogrenci);

        }catch(IOException e){System.out.println("Hata!");}
    }


    void notGiris()
    {
        /* ogrenci txt de girilmedi yazan her hucre not hucreleridir. Uygun ders ve ogrenci no seslestirmesinin ardindan
        alinan degerler okunan satirin split ile bölünmesiyle uygun olan girilmedi yazan hucreye atandi
        yeni kayit satiri olusturuldu bu hucrelerin art arda eklenmesiyle ve temp dosyası yöntemi ile dosya guncellendi*/

        int vizeNotu = -1 , finalNotu = -1 , butNotu = -1;
        Scanner input = new Scanner(System.in);
        String line, ogrenciWords[];
        File ogrenci = new File("textFiles/ogrenci.txt");
        File guncelOgrenci = new File("textFiles/temp.txt");

        try{

            if (!ogrenci.exists()){
                System.out.print("Henuz sistemde kayitli ogrenci bulunmamaktadir. Lutfen once ogrenci kaydi yapin!\n");
                return;
            }
            if(!guncelOgrenci.exists())
                guncelOgrenci.createNewFile();

        }catch(IOException e){System.out.println("Hata!");}

        System.out.print("Gorevli Oldugunuz Dersler : ");
        for(int i = 0; i < dersSayisi; i++){
            System.out.print(ders[i] + " ");
        }
        System.out.println();
        System.out.print("Not islemi yapacaginiz dersin adini giriniz: ");
        String notDersi = input.next();
        System.out.print("Not islemi yapacaginiz ogrencinin okul numarasini giriniz: ");
        String okulNo = input.next().trim();
        System.out.print("Sinav turunu seciniz 1 - Vize 2 - Final 3 - Butunleme 0 - Cikis : ");
        int sinavTürü = input.nextInt();
        switch(sinavTürü){
            case 1 :
                System.out.print("Vize notunu giriniz : ");
                vizeNotu = input.nextInt();
                break;
            case 2 :
                System.out.print("Final notunu giriniz : ");
                finalNotu = input.nextInt();
                break;
            case 3 :
                System.out.print("Butunleme notunu giriniz : ");
                butNotu = input.nextInt();
                break;
            case 0 :
                break;
            default :
                System.out.println("Hatali giris yaptiniz, tekrar deneyiniz!");
                break;
        }

        int ogrenciDersSayisi = 0;
        String kayitSatiri = "";
        try{
            FileReader ogrenciOku = new FileReader(ogrenci);
            try (BufferedReader ogrenciTampon = new BufferedReader(ogrenciOku)) {
                FileWriter fileWriter = new FileWriter(guncelOgrenci);
                try (BufferedWriter tempYaz = new BufferedWriter(fileWriter)) {
                    while ((line = ogrenciTampon.readLine()) != null) {
                        ogrenciWords = line.split(" ");
                        if(ogrenciWords[1].equals(okulNo)){
                            System.out.println("Okul no yu buldum");
                            if(ogrenciWords.length > 3)
                                ogrenciDersSayisi = Integer.parseInt(ogrenciWords[3]);
                            else{
                                tempYaz.close();
                                ogrenciTampon.close();
                                System.out.println("Ogrenci bu dersin secimini yapmamistir!");
                                return;
                            }
                            for(int i = 4; i < 4 + ogrenciDersSayisi; i++){
                                if(ogrenciWords[i].equalsIgnoreCase(notDersi)){
                                    if(!(vizeNotu == -1)){
                                        System.out.println("vizeye girdim");
                                        ogrenciWords[i+ogrenciDersSayisi] = Integer.toString(vizeNotu);
                                    }
                                    if(!(finalNotu == -1))
                                        ogrenciWords[i+(2 * ogrenciDersSayisi)] = Integer.toString(finalNotu);
                                    if(!(butNotu == -1))
                                        ogrenciWords[i+(3 * ogrenciDersSayisi)] = Integer.toString(butNotu);
                                    break;
                                }
                            }
                            for(int i = 0; i < ogrenciWords.length; i++){
                                kayitSatiri += ogrenciWords[i] + " ";
                            }
                        }
                        tempYaz.write(kayitSatiri.trim() + "\n");
                    }
                }
            }

            ogrenci.delete();

            guncelOgrenci.renameTo(ogrenci);

        }catch(IOException e){System.out.println("Hata!");}
    }

    void yoklamaGir()
    {
        /* yoklama.txt dosyasında ogrenci tc ve alt tarafında sıralanan her satırda aldıgı dersler satirlari bulunmaktadır
        Buna gore kullanıcıdan alininan yoklama dersi , tcNo ve katılım durumuna dair bilgiler yoklama txt ye islendi
        Once tcNo kontrolu ile okuyucu imleç o kişinin dosyadaki ders bölümüne gitti ve sonra da dogru ders eslesmesini bulmak icin
        if kontrolu yaptı en son katılım durumunu stringe append ettik ve o sekilde yeni dosyaya ekledik. temp dosyası yeni yoklama txt dosyamız oldu
        */

        Scanner input = new Scanner(System.in);
        String line, katilim, yoklamaWords[];
        
        try
        {
            File yoklama = new File("textFiles/yoklama.txt");
            File guncelYoklama = new File("textFiles/temp.txt");

            if (!yoklama.exists() || yoklama.length() == 0){
                System.out.println("Henuz yoklama kaydina islenmis ogrenci yok!");
            }
            if(!guncelYoklama.exists())
                guncelYoklama.createNewFile();

            System.out.print("Gorevli Oldugunuz Dersler : ");
            for(int i = 0; i < dersSayisi; i++){
                System.out.print(ders[i] + " ");
            }
            System.out.println();
            System.out.print("Yoklama islemi yapacaginiz dersin adini giriniz: ");
            String yoklamaDersi = input.next();
            System.out.print("Yoklama islemi yapacaginiz ogrencinin TC numarasini giriniz: ");
            String tcNo = (input.next()).trim();
            do{
                System.out.print("Geldiyse 'K' gelmediyse 'G' yaziniz ");
                katilim = input.next().toUpperCase();
            }while(!katilim.equalsIgnoreCase("G") && !katilim.equalsIgnoreCase("K"));



            FileReader yoklamaOku = new FileReader(yoklama);
            try (BufferedReader yoklamaTampon = new BufferedReader(yoklamaOku)) {
                FileWriter fileWriter = new FileWriter(guncelYoklama);
                try (BufferedWriter tempYaz = new BufferedWriter(fileWriter)) {
                    boolean ok = false;
                    
                    while ((line = yoklamaTampon.readLine()) != null) {
                        if(line.trim().equals(tcNo)){
                            ok = true;
                        }
                        if(ok){
                            yoklamaWords = line.split(" ");
                            if(yoklamaDersi.equalsIgnoreCase(yoklamaWords[0])){
                                line = line + " " + katilim;
                                ok = false;
                            }
                        }
                        tempYaz.write(line + "\n");
                    }
                }
            }

            yoklama.delete();

            guncelYoklama.renameTo(yoklama);

        }catch(IOException e){System.out.println("Hata!");} 
    }

    void yoklamaGoruntule()
    {
        /* yoklama.txt dosyasında ogrenci tc ve alt tarafında sıralanan her satırda aldıgı dersler satirlari bulunmaktadır
        Buna gore kullanıcıdan alininan yoklama dersi , tcNo ve katılım durumuna dair bilgiler yoklama txt ye islendi
        Once tcNo kontrolu ile okuyucu imleç o kişinin dosyadaki ders bölümüne gitti ve sonra da dogru ders eslesmesini bulmak icin
        if kontrolu yaptı en son geldigimiz bu satiri yazdırdık
        */

        Scanner input = new Scanner(System.in);
        String line, yoklamaWords[];
        
        try
        {
            File yoklama = new File("textFiles/yoklama.txt");

            if (!yoklama.exists() || yoklama.length() == 0){
                System.out.println("Henuz yoklama kaydina islenmis ogrenci yok!");
            }

            System.out.print("Gorevli Oldugunuz Dersler : ");
            for(int i = 0; i < dersSayisi; i++){
                System.out.print(ders[i] + " ");
            }
            System.out.println();
            System.out.print("Yoklama durumunu goruntuleyeceginiz dersin adini giriniz: ");
            String yoklamaDersi = input.next();
            System.out.print("Yoklama durumunu goruntuleyeceginiz ogrencinin TC numarasini giriniz: ");
            String tcNo = (input.next()).trim();


            FileReader yoklamaOku = new FileReader(yoklama);
            try (BufferedReader yoklamaTampon = new BufferedReader(yoklamaOku)) {
                boolean ok = false;
                while ((line = yoklamaTampon.readLine()) != null) {
                    if(line.trim().equals(tcNo)){
                        ok = true;
                    }
                    if(ok){
                        yoklamaWords = line.split(" ");
                        if(yoklamaDersi.equalsIgnoreCase(yoklamaWords[0])){
                                System.out.println(tcNo + "TC numarali ogrencinin " + yoklamaDersi + 
                                        " dersine devam durumu; (K - katildi G - gelmedi)\n" + line);
                                ok = false;
                            }
                        }                    }
                }

        }catch(IOException e){System.out.println("Hata!");} 
    }
}


interface SınavOran
{
    double vizeOran = 0.5;
    double bitirmeOran = 0.5;
    double butOran = 0.5;
    int gecmeNotu = 50;
    void HarfNotuOlustur ();
}

