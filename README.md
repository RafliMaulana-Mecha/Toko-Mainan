# Toko Mainan App
Nama  : Rafli Maulana

NIM   : 2404364


Aplikasi ini merupakan aplikasi desktop sederhana untuk mencatat produk toko mainan, dibuat dengan **Java** dan **JavaFX**. Setiap produk memiliki foto, sehingga produk bisa dipilih langsung dari gambarnya. Proyek ini dikerjakan sebagai tugas individu mata kuliah Pemrograman Lanjut, dengan struktur dan pola kode dari praktikum TiketApp.

## Fitur

- **Tambah produk** lewat form: nama, kategori, harga, dan foto (opsional).
- **Tabel produk** berisi foto, kode, nama, kategori, dan harga (format Rupiah).
- **Pencarian** berdasarkan nama produk lewat tombol **Cari** (tombol **Reset** menampilkan semua produk lagi).
- **Lihat detail** produk yang dipilih, lengkap dengan foto besar. Bisa lewat tombol **Lihat Detail** atau klik dua kali pada baris produk.
- **Hapus produk** dengan pop up konfirmasi sebelum data benar-benar dihapus.
- **Validasi input**: nama tidak boleh kosong, harga harus bilangan bulat lebih dari nol, foto harus PNG/JPEG dengan ukuran maksimal 5 MB.
- Produk tanpa foto atau dengan foto rusak menampilkan gambar cadangan "Belum ada foto".

Aplikasi tidak memakai database. Data disimpan di memori selama aplikasi berjalan, jadi semua perubahan hilang saat aplikasi ditutup.

## Cara Menjalankan

### Lewat NetBeans

1. Buka folder proyek lewat **File → Open Project**.
2. Klik **Run Project**. File `nbactions.xml` sudah mengatur tombol Run agar memakai `javafx:run`.

### Lewat terminal

```bash
mvn clean javafx:run
```

> Aplikasi JavaFX tidak bisa dijalankan dengan `java` biasa atau `exec:exec`. Gunakan `javafx:run`, kalau tidak akan muncul pesan *"JavaFX runtime components are missing"*.

## Struktur Proyek

```
TokoMainanApp
├── pom.xml
├── nbactions.xml
└── src/main
    ├── java/kelas
    │   ├── MainApp.java              # titik masuk aplikasi
    │   ├── Navigator.java            # perpindahan halaman
    │   ├── model/Produk.java         # data produk + validasi
    │   ├── data/DataProduk.java      # penyimpanan produk di memori
    │   ├── controller/
    │   │   ├── DaftarController.java # tabel, pencarian, detail, hapus
    │   │   ├── FormController.java   # tambah produk
    │   │   └── DetailController.java # halaman detail
    │   └── util/GambarUtil.java      # memuat gambar + gambar cadangan
    └── resources/kelas
        ├── daftar.fxml
        ├── form.fxml
        ├── detail.fxml
        ├── styles/app.css
        └── images/                   # foto produk
```

## Model Data

Class `Produk` memiliki atribut private, constructor, dan getter.

| Atribut | Tipe | Keterangan |
|---|---|---|
| `kode` | `String` | Dibuat otomatis: `PR001`, `PR002`, dan seterusnya |
| `nama` | `String` | Wajib diisi |
| `kategori` | `String` | Dipilih dari daftar `DataProduk.KATEGORI` |
| `harga` | `long` | Harus lebih dari nol |
| `gambarUri` | `String` | Lokasi foto, boleh `null` |

Halaman detail menerima objek `Produk` yang dipilih dari tabel (`DetailController.setData`), bukan data yang ditulis langsung dalam kode.

## Mengganti Foto dan Produk Awal

Produk awal didaftarkan di constructor `DataProduk`:

```java
tambah("Hot Wheels", "Mobil-mobilan", 60000, resource("mobil.png"));
```

Untuk mengganti atau menambah foto produk awal:

1. Simpan foto di `src/main/resources/kelas/images/` dalam format **PNG atau JPG asli** (WebP, HEIC, dan AVIF tidak terbaca oleh JavaFX). Rasio 4:3, misalnya 800×600, paling pas.
2. Tambahkan atau ubah baris `tambah(...)` di `DataProduk.java` dengan nama file yang sama persis, termasuk huruf besar-kecil dan ekstensi.
3. Jalankan **Clean and Build** agar foto baru ikut terbaca.

Jangan menghapus atau mengganti nama `kosong.png`, karena itu gambar cadangan untuk produk tanpa foto.

Foto yang dipilih lewat tombol **Pilih Foto** di form hanya disimpan sebagai lokasi file di komputer. Jika file dipindah atau dihapus, produk akan menampilkan gambar "Belum ada foto".

## Mengubah Kategori

Daftar kategori ada di satu tempat, yaitu konstanta `KATEGORI` di `DataProduk.java`:

```java
public static final String[] KATEGORI = {"Action Figure", "Boneka", "Mobil-mobilan", "Puzzle & Edukasi"};
```

## Skenario Pengujian

1. Tambahkan minimal tiga produk lewat form, lalu buka daftar dan pastikan semuanya muncul di tabel.
2. Ketik sebagian nama produk lalu klik **Cari**. Hanya produk yang sesuai yang tampil.
3. Pilih satu produk lalu klik **Lihat Detail**. Halaman detail menampilkan produk yang dipilih.
4. Coba simpan dengan nama kosong, harga `0`, atau harga `abc`. Semuanya harus ditolak dengan pesan kesalahan.
5. Pilih satu produk lalu klik **Hapus**. Pop up konfirmasi muncul, dan produk hanya terhapus jika memilih **Yes**.

## Catatan untuk Git

Folder `target/` dihasilkan otomatis oleh Maven dan sebaiknya tidak ikut di-commit. Tambahkan baris berikut ke `.gitignore`:

```
target/
```

## Tampilan Aplikasi
<img width="1028" height="844" alt="image" src="https://github.com/user-attachments/assets/300a6efe-3b87-44c3-9612-6feb7f18a349" />

<img width="1028" height="844" alt="image" src="https://github.com/user-attachments/assets/abc19bdf-c2b5-4404-9034-1f7cca1916a1" />

