#Daftar class

1. Pasien
2. Dokter
3. Kunjungan
4. Klinik
5. Stack

Topik : Klinik Rawat Jalan (digit terakhir 1)
ADT   : Stack (digit kedua dari belakang mod 3 = 0)
Aturan bisnis: satu dokter maksimal 5 kunjungan per hari; umur pasien 0-120


#Pasien

Peran: entitas utama, orang yang berobat ke klinik.

Atribut:
- idPasien: String
- nama: String
- umur: int (validasi di setter: 0 sampai 120)
- jenisKelamin: String
- jumlahKunjungan: int

Konstruktor:
+ Pasien()
+ Pasien(idPasien: String, nama: String, umur: int, jenisKelamin: String)

Method:
+ getIdPasien(): String
+ getNama(): String
+ getUmur(): int
+ getJenisKelamin(): String
+ getJumlahKunjungan(): int
+ setIdPasien(idPasien: String): void
+ setNama(nama: String): void
+ setUmur(umur: int): void   (tolak jika < 0 atau > 120)
+ setJenisKelamin(jenisKelamin: String): void
+ tampilkanInfo(): void
+ tambahJumlahKunjungan(): void
+ kurangiJumlahKunjungan(): void
+ isLansia(): boolean   (umur >= 60)

Relasi:
- Asosiasi dengan Kunjungan (1 Pasien melakukan 0..* Kunjungan)


#Dokter

Peran: entitas pendukung, pemeriksa pasien.

Atribut:
- idDokter: String
- nama: String
- spesialisasi: String
- kunjunganHariIni: int
- MAKS_KUNJUNGAN: int (konstanta static final = 5)

Konstruktor:
+ Dokter()
+ Dokter(idDokter: String, nama: String, spesialisasi: String)

Method:
+ getIdDokter(): String
+ getNama(): String
+ getSpesialisasi(): String
+ getKunjunganHariIni(): int
+ setIdDokter(idDokter: String): void
+ setNama(nama: String): void
+ setSpesialisasi(spesialisasi: String): void
+ bisaMenerimaKunjungan(): boolean   (kunjunganHariIni < MAKS_KUNJUNGAN)
+ tambahKunjungan(): boolean   (gagal jika sudah 5)
+ kurangiKunjungan(): void   (dipakai saat pembatalan)
+ resetKunjunganHarian(): void
+ tampilkanInfo(): void

Relasi:
- Asosiasi dengan Kunjungan (1 Dokter menangani 0..* Kunjungan)


#Kunjungan

Peran: transaksi utama yang menghubungkan Pasien dan Dokter.

Atribut:
- idKunjungan: String
- pasien: Pasien
- dokter: Dokter
- tanggal: String
- keluhan: String
- biaya: double
- status: String (TERDAFTAR, SELESAI, DIBATALKAN)

Konstruktor:
+ Kunjungan()
+ Kunjungan(idKunjungan: String, pasien: Pasien, dokter: Dokter, tanggal: String, keluhan: String, biaya: double)

Method:
+ getIdKunjungan(): String
+ getPasien(): Pasien
+ getDokter(): Dokter
+ getTanggal(): String
+ getKeluhan(): String
+ getBiaya(): double
+ getStatus(): String
+ setPasien(pasien: Pasien): void
+ setDokter(dokter: Dokter): void
+ setBiaya(biaya: double): void   (tolak jika < 0)
+ setStatus(status: String): void
+ ubahStatus(statusBaru: String): void
+ batalkan(): void   (status jadi DIBATALKAN, kurangi hitungan dokter dan pasien)
+ hitungBiaya(): double   (tarif dasar, tambah/diskon sesuai aturan)
+ tampilkanInfo(): void

Relasi:
- Asosiasi ke Pasien (banyak Kunjungan -> 1 Pasien)
- Asosiasi ke Dokter (banyak Kunjungan -> 1 Dokter)


#Klinik

Peran: class pengelola, menyimpan semua data dan menjalankan aturan bisnis.

Atribut:
- namaKlinik: String
- alamat: String
- daftarPasien: ArrayList<Pasien>
- daftarDokter: ArrayList<Dokter>
- daftarKunjungan: ArrayList<Kunjungan>
- riwayatKunjungan: Stack

Konstruktor:
+ Klinik()
+ Klinik(namaKlinik: String, alamat: String)

Method:
+ getNamaKlinik(): String
+ getAlamat(): String
+ setNamaKlinik(namaKlinik: String): void
+ setAlamat(alamat: String): void
+ tambahPasien(pasien: Pasien): boolean   (cek ID duplikat)
+ tambahDokter(dokter: Dokter): boolean   (cek ID duplikat)
+ buatKunjungan(idPasien: String, idDokter: String, tanggal: String, keluhan: String): boolean
  (cek pasien ada, dokter ada, dokter belum 5 kunjungan, lalu push ke Stack)
+ cariPasien(kunci: String): Pasien
+ cariDokter(kunci: String): Dokter
+ batalkanKunjunganTerakhir(): boolean   (pop dari Stack, lalu batalkan)
+ tampilkanSemuaPasien(): void
+ tampilkanSemuaDokter(): void
+ tampilkanSemuaKunjungan(): void
+ tampilkanLaporan(): void   (jumlah pasien, dokter, kunjungan, total biaya)

Relasi:
- Agregasi ke Pasien (1 Klinik memiliki 0..* Pasien)
- Agregasi ke Dokter (1 Klinik memiliki 0..* Dokter)
- Komposisi ke Kunjungan (1 Klinik memiliki 0..* Kunjungan)
- Komposisi ke Stack (1 Klinik memiliki 1 Stack)


#Stack

Peran: ADT LIFO untuk riwayat kunjungan terakhir (fitur batalkan kunjungan terakhir).

Atribut:
- elemen: ArrayList<Kunjungan>   (private)
- kapasitas: int   (private, contoh 10)

Konstruktor:
+ Stack()   (kapasitas default 10)
+ Stack(kapasitas: int)

Operasi:
+ push(kunjungan: Kunjungan): boolean
  prasyarat: tidak penuh; jika penuh tampilkan pesan, return false
+ pop(): Kunjungan
  prasyarat: tidak kosong; jika kosong return null dan tampilkan pesan
+ peek(): Kunjungan
  melihat elemen teratas tanpa menghapus; jika kosong return null
+ isEmpty(): boolean
+ isFull(): boolean
+ size(): int
+ tampilkan(): void   (isi stack dari atas ke bawah)
+ getKapasitas(): int

Relasi:
- Dependensi/asosiasi ke Kunjungan (Stack menyimpan 0..kapasitas Kunjungan)
- Dimiliki oleh Klinik (komposisi)


#Main

Peran: class menu console, titik masuk program.

Atribut:
- klinik: Klinik
- scanner: Scanner

Konstruktor:
+ Main()   (opsional, atau pakai static)

Method:
+ main(args: String[]): void
+ tampilkanMenu(): void
+ menuPasien(): void   (menu 1: tambah dan tampilkan pasien)
+ menuDokter(): void   (menu 2: tambah dan tampilkan dokter)
+ menuKunjungan(): void   (menu 3: buat kunjungan)
+ menuCari(): void   (menu 4: cari pasien/dokter)
+ menuStack(): void   (menu 5: push/pop/peek/isEmpty/isFull/size + tampil)
+ menuLaporan(): void   (menu 6: laporan)
+ bacaInt(pesan: String): int   (validasi, ulangi jika input bukan angka)
+ bacaString(pesan: String): String   (validasi, tidak boleh kosong)
+ bacaDouble(pesan: String): double

Relasi:
- Dependensi ke Klinik (Main menggunakan Klinik)
- Dependensi ke Pasien, Dokter, Stack (untuk membuat objek / menampilkan)