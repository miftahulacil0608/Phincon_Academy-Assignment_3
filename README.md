# Traver App

Traver App adalah aplikasi itinerary yang memungkinkan pengguna untuk mencari dan menyimpan rencana perjalanan. Aplikasi ini memuat data destinasi dari API eksternal, yang juga dapat disimpan secara lokal untuk akses offline.

## Fitur

- **Pencarian Destinasi**: Telusuri berbagai destinasi wisata yang tersedia.
- **Infinite Scroll**: Lihat lebih banyak destinasi saat scroll hingga akhir daftar.
- **Simpan Offline**: Simpan destinasi ke penyimpanan lokal untuk akses offline.
- **Detail Destinasi**: Lihat detail lengkap dari destinasi yang dipilih.
- **Mode Pencarian dan Tampilan Semua Data**: Dapat mencari destinasi dengan filter atau melihat semua data tanpa filter.

## Arsitektur

Traver App dibangun dengan **Clean Architecture** menggunakan beberapa komponen penting:

- **UI Layer**: Fragment, Adapter, dan `ViewModel` untuk mengontrol antarmuka pengguna.
- **Data Layer**: Repository dan API Service yang menangani data dari sumber eksternal (API) dan penyimpanan lokal (Room Database).
- **Domain Layer**: Use Case untuk setiap fitur utama aplikasi.
- **Dependency Injection**: Menggunakan Hilt untuk pengelolaan dependensi.

## Teknologi yang Digunakan

- **Kotlin**: Bahasa pemrograman utama.
- **MVVM**: Model View ViewModel sebagai arsitektur utama.
- **Room Database**: Penyimpanan lokal data destinasi.
- **Retrofit**: Library HTTP client untuk mengambil data dari API.
- **Hilt**: Dependency Injection.
- **LiveData & Flow**: Mengelola data secara asinkron dan reaktif.
- **RecyclerView**: Menampilkan daftar destinasi dengan dukungan infinite scroll.

## Penggunaan Aplikasi

### 1. Lihat Daftar Destinasi
Pada tampilan utama, daftar destinasi wisata akan ditampilkan secara dinamis menggunakan fitur **infinite scroll**. Aplikasi akan mengambil data dari API dan menampilkan lebih banyak destinasi saat pengguna scroll ke bagian bawah daftar.

### 2. Pencarian Destinasi
Traver App menyediakan fitur pencarian destinasi. Cukup ketikkan nama destinasi di kolom pencarian yang tersedia, dan aplikasi akan menampilkan hasil yang sesuai dengan input pengguna. Daftar yang ditampilkan akan otomatis difilter berdasarkan query pencarian yang dimasukkan.

### 3. Simpan Destinasi
Setelah menemukan destinasi yang menarik, pengguna dapat melihat detail destinasi tersebut dengan mengetuk item dalam daftar. Di halaman detail, terdapat tombol **Simpan** yang memungkinkan pengguna menambahkan destinasi tersebut ke penyimpanan lokal agar dapat diakses kembali di lain waktu.

### 4. Akses Offline
Destinasi yang sudah disimpan akan tetap tersedia meskipun perangkat tidak terhubung ke internet. Hal ini memungkinkan pengguna untuk mengakses destinasi favorit mereka kapan saja, bahkan dalam kondisi offline.
