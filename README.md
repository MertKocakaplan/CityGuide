# City Guide Uygulaması

## Tanım
City Guide, kullanıcıların şehirdeki önemli yerleri keşfetmelerine yardımcı olan bir Android uygulamasıdır. Uygulama, popüler turistik yerler, restoranlar ve diğer ilgi çekici mekanlar hakkında bilgi sağlar. Kullanıcılar harita üzerinde bu yerleri görebilir, favori yerlerini kaydedebilir ve yer detaylarını inceleyebilirler.

## Uygulama Özellikleri

### Front-end Tasarımları 

1. **Home (Ana Sayfa)**: Şehirdeki önemli yapıtları ve mekanları tanıtan blog tarzında bir ekran.
2. **Map (Harita)**: Kullanıcıların şehirdeki önemli yerleri harita üzerinde görmesini sağlayan ekran.
3. **Places (Yerler)**: Kullanıcıların arama yaparak yerleri keşfetmelerini sağlayan ve detaylarına ulaşabilecekleri ekran.
4. **Favorites (Favoriler)**: Kullanıcıların favori yerlerini listelediği ekran.
5. **Popular (Popüler)**: Şehirdeki popüler yerleri listeleyen ekran.

### Layout ve UI elemanları
Uygulamada kullanılan layout ve UI elemanları:
#### Uygulamada Kullanılan Layout ve UI Elemanları

#### 1. RecyclerView
- **Kullanım Amacı**: Yerler listesini göstermek.
- **Kullanıldığı Ekranlar**: HomeScreen, PlacesScreen, FavoritesScreen, PopularScreen

#### 2. CardView
- **Kullanım Amacı**: Yer detaylarını ve liste elemanlarını görsel olarak ayırt edilebilir kartlar içinde sunmak.
- **Kullanıldığı Ekranlar**: HomeScreen, PlaceDetailScreen

#### 3. BottomNavigationView
- **Kullanım Amacı**: Farklı sayfalar arasında geçiş yapmayı sağlamak.
- **Kullanıldığı Ekranlar**: Tüm ekranlarda (CityGuideApp)

#### 4. Toolbar
- **Kullanım Amacı**: Sayfa başlıklarını göstermek ve arama yapmak.
- **Kullanıldığı Ekranlar**: PlaceDetailScreen

#### 5. FloatingActionButton
- **Kullanım Amacı**: Harita üzerinde kullanıcının konumunu göstermek.
- **Kullanıldığı Ekranlar**: MapScreen

#### 6. LazyColumn
- **Kullanım Amacı**: Yerleri dikey bir liste halinde göstermek.
- **Kullanıldığı Ekranlar**: HomeScreen

#### 7. Spacer
- **Kullanım Amacı**: UI elemanları arasında boşluk bırakmak.
- **Kullanıldığı Ekranlar**: HomeScreen, PlaceDetailScreen

#### 8. Image
- **Kullanım Amacı**: Yerlerin görsellerini göstermek.
- **Kullanıldığı Ekranlar**: HomeScreen

#### 9. Text
- **Kullanım Amacı**: Yer isimleri ve açıklamalarını göstermek.
- **Kullanıldığı Ekranlar**: HomeScreen, PlaceDetailScreen, diğer metin içeren ekranlar

#### 10. Column
- **Kullanım Amacı**: UI elemanlarını dikey olarak düzenlemek.
- **Kullanıldığı Ekranlar**: HomeScreen, PlaceDetailScreen

#### 11. Row
- **Kullanım Amacı**: UI elemanlarını yatay olarak düzenlemek.
- **Kullanıldığı Ekranlar**: BottomNavigationBar, PlaceDetailScreen

#### 12. Scaffold
- **Kullanım Amacı**: Uygulamanın genel layout yapısını oluşturmak.
- **Kullanıldığı Ekranlar**: CityGuideApp

#### 13. Icon
- **Kullanım Amacı**: Simge göstermek (örneğin, favori simgesi, harita simgesi).
- **Kullanıldığı Ekranlar**: BottomNavigationBar, PlaceDetailScreen

#### 14. Modifier
- **Kullanım Amacı**: UI elemanlarının görünümünü ve davranışını değiştirmek.
- **Kullanıldığı Ekranlar**: Tüm ekranlarda geniş kullanımı vardır.

#### 15. rememberScrollState
- **Kullanım Amacı**: Scrollable alanların durumunu hatırlamak.
- **Kullanıldığı Ekranlar**: PlaceDetailScreen

#### 16. PaddingValues
- **Kullanım Amacı**: UI elemanlarına iç boşluk vermek.
- **Kullanıldığı Ekranlar**: HomeScreen, PlaceDetailScreen

#### 17. MapView ve Marker
- **Kullanım Amacı**: Harita üzerinde yerleri işaretlemek ve göstermek.
- **Kullanıldığı Ekranlar**: MapScreen

#### 18. NavController ve NavHost
- **Kullanım Amacı**: Uygulama içi navigasyonu sağlamak.
- **Kullanıldığı Ekranlar**: NavigationGraph

### Uygulamada kullanılan ileri özellikler:
1. **Bildirim**: Konum izni reddedildiğinde kullanıcılara bildirim gönderilir.
2. **Harita**: Google Maps API kullanılarak harita üzerinde yerler gösterilir.

#### API Servisi
Uygulama, Google Places API kullanarak yer verilerini çeker ve kullanıcı arayüzünde gösterir.

#### Ekstra özellikler
1. **Yerel cihazda veri depolama ve ekranlar arası veri aktarımı**: SharedPreferences kullanılarak kullanıcı favorileri yerel olarak saklanır.
2. **Cihazın konum, takvim, rehber gibi özelliklerine erişim**: Kullanıcının mevcut konumu harita üzerinde gösterilir.

### Kullanılan Teknolojiler ve Araçlar
- **Kotlin**: Uygulama geliştirme dili.
- **Android Studio**: Geliştirme ortamı.
- **Google Maps API**: Harita ve konum özellikleri için.
- **Google Places API**: Yer verilerini çekmek için.
- **SharedPreferences**: Yerel veri depolama için.
- **Retrofit**: HTTP isteklerini yapmak için.
- **Material Design Components**: UI bileşenleri için.

### Sonuç
City Guide uygulaması, kullanıcı dostu ve işlevsel bir şehir rehberi uygulamasıdır. Kullanıcılar, şehirdeki önemli yerleri keşfedebilir, favorilerine ekleyebilir ve harita üzerinde konumlarını görebilirler. Bu özellikler, uygulamanın hem yerel hem de turist kullanıcılar için değerli bir kaynak olmasını sağlar.
