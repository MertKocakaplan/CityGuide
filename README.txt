## Proje Raporu: City Guide Uygulaması

### 1. Proje Tanımı
City Guide, kullanıcıların şehirdeki önemli yerleri keşfetmelerine yardımcı olan bir Android uygulamasıdır. Uygulama, popüler turistik yerler, restoranlar ve diğer ilgi çekici mekanlar hakkında bilgi sağlar. Kullanıcılar harita üzerinde bu yerleri görebilir, favori yerlerini kaydedebilir ve yer detaylarını inceleyebilirler.

### 2. Proje Özellikleri ve İsterlerin Karşılanması

#### En az 5 farklı ön yüz tasarımı gerçekleştirme (20 puan)
City Guide uygulaması, aşağıdaki farklı ön yüz tasarımlarına sahiptir:
1. **Home (Ana Sayfa)**: Şehirdeki önemli yapıtları ve mekanları tanıtan blog tarzında bir ekran.
2. **Map (Harita)**: Kullanıcıların şehirdeki önemli yerleri harita üzerinde görmesini sağlayan ekran.
3. **Places (Yerler)**: Kullanıcıların arama yaparak yerleri keşfetmelerini sağlayan ekran.
4. **Favorites (Favoriler)**: Kullanıcıların favori yerlerini listelediği ekran.
5. **Popular (Popüler)**: Şehirdeki popüler yerleri listeleyen ekran.

#### Hazırlanan ön yüzlerde derste işlenen layout ve UI elemanlarından en az 5’inin kullanımı (20 puan)
Uygulamada kullanılan layout ve UI elemanları:
1. **RecyclerView**: Yerler listesini göstermek için.
2. **CardView**: Yer detaylarını göstermek için.
3. **BottomNavigationView**: Sayfalar arasında geçiş yapmak için.
4. **Toolbar**: Sayfa başlıklarını göstermek ve arama yapmak için.
5. **FloatingActionButton**: Harita üzerinde kullanıcının konumunu göstermek için.

#### Bildirim, harita gibi ileri özelliklerin kullanımı (20 puan)
Uygulamada kullanılan ileri özellikler:
1. **Bildirim**: Konum izni reddedildiğinde kullanıcılara bildirim gönderilir.
2. **Harita**: Google Maps API kullanılarak harita üzerinde yerler gösterilir.

#### Bir servisten veri alarak ön yüzde görüntüleme (20 puan)
Uygulama, Google Places API kullanarak yer verilerini çeker ve kullanıcı arayüzünde gösterir.

#### Aşağıdaki 5 maddeden 2’sinin kullanımı (2x10 puan)
1. **Yerel cihazda veri depolama ve ekranlar arası veri aktarımı**: SharedPreferences kullanılarak kullanıcı favorileri yerel olarak saklanır.
2. **Cihazın konum, takvim, rehber gibi özelliklerine erişim**: Kullanıcının mevcut konumu harita üzerinde gösterilir.

### 3. Kullanılan Teknolojiler ve Araçlar
- **Kotlin**: Uygulama geliştirme dili.
- **Android Studio**: Geliştirme ortamı.
- **Google Maps API**: Harita ve konum özellikleri için.
- **Google Places API**: Yer verilerini çekmek için.
- **SharedPreferences**: Yerel veri depolama için.
- **Retrofit**: HTTP isteklerini yapmak için.
- **Material Design Components**: UI bileşenleri için.

### 4. Ekran Görüntüleri
- **Home (Ana Sayfa)**: Ünlü yapıtların ve mekanların tanıtıldığı blog tarzı ekran.
- **Map (Harita)**: Şehirdeki önemli yerlerin harita üzerinde gösterildiği ekran.
- **Places (Yerler)**: Arama yaparak yerlerin keşfedildiği ekran.
- **Favorites (Favoriler)**: Kullanıcıların favori yerlerini listelediği ekran.
- **Popular (Popüler)**: Şehirdeki popüler yerlerin listelendiği ekran.

### 5. Sonuç
City Guide uygulaması, verilen tüm gereksinimleri karşılayan, kullanıcı dostu ve işlevsel bir şehir rehberi uygulamasıdır. Kullanıcılar, şehirdeki önemli yerleri keşfedebilir, favorilerine ekleyebilir ve harita üzerinde konumlarını görebilirler. Bu özellikler, uygulamanın hem yerel hem de turist kullanıcılar için değerli bir kaynak olmasını sağlar.