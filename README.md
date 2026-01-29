🎨 Kotlin Art Book
Bu uygulama, kullanıcıların kendi sanat koleksiyonlarını dijital ortamda saklamalarına olanak tanıyan basit ve kullanışlı bir Android uygulamasıdır. Kullanıcılar sanat eserlerinin isimlerini, sanatçı bilgilerini, yapım yıllarını ve fotoğraflarını cihazlarının yerel hafızasına kaydedebilirler.

🚀 Özellikler
Sanat Eseri Ekleme: Galeriye erişim izni alarak cihazdan fotoğraf seçme ve eser bilgilerini (İsim, Sanatçı, Yıl) kaydetme.

Görsel İşleme: Büyük boyutlu görselleri veritabanına kaydetmeden önce performansı artırmak amacıyla otomatik olarak yeniden boyutlandırma (makeSmallerBitmap).

Yerel Veritabanı: Tüm verilerin SQLite kullanılarak cihazda kalıcı olarak saklanması.

Dinamik Liste: Kaydedilen eserlerin RecyclerView ve CardView kullanılarak ana ekranda şık bir şekilde listelenmesi.

Detay Görüntüleme: Listeden bir esere tıklandığında ilgili eserin tüm detaylarına ve görseline ulaşabilme.

🛠️ Kullanılan Teknolojiler ve Kütüphaneler
Dil: Kotlin

UI Bileşenleri:

RecyclerView & CardView: Dinamik ve performanslı liste yapıları için.

View Binding: XML bileşenlerine güvenli ve kolay erişim için.

ConstraintLayout: Esnek ve modern arayüz tasarımları için.

Veri Yönetimi:

SQLite: Verilerin yerel olarak saklanması ve yönetilmesi.

Android API'leri:

ActivityResultLauncher: Galeriye erişim ve fotoğraf seçme işlemleri için.

Permissions: Android 13+ (Tiramisu) ve eski sürümler için dinamik izin yönetimi.


📦 Kurulum
Bu repoyu bilgisayarınıza klonlayın:

Bash
git clone https://github.com/kullanici_adin/KotlinArtBook.git
Android Studio'yu açın ve projeyi içe aktarın.

Gerekli bağımlılıkların yüklenmesi için projenin senkronize edilmesini bekleyin.

Bir emülatör veya gerçek bir cihaz üzerinde uygulamayı çalıştırın.


Geliştirici Notu

Bu proje, Android uygulama geliştirme sürecinde veritabanı işlemleri, adapter yapısı ve kullanıcı izinleri gibi temel konuları pekiştirmek amacıyla geliştirilmiştir.
