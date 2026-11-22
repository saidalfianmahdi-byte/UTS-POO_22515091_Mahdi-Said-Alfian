package klinik;

import java.util.ArrayList;


public class Klinik {
    private String namaKlinik;
    private String alamat;
    private ArrayList<Pasien> daftarPasien;
    private ArrayList<Dokter> daftarDokter;
    private ArrayList<Kunjungan> daftarKunjungan;
    private Stack riwayatKunjungan;
    private int counterKunjungan;

    public Klinik() {
        this("Klinik", "-");
    }

    public Klinik(String namaKlinik, String alamat) {
        this.namaKlinik = namaKlinik;
        this.alamat = alamat;
        this.daftarPasien = new ArrayList<>();
        this.daftarDokter = new ArrayList<>();
        this.daftarKunjungan = new ArrayList<>();
        this.riwayatKunjungan = new Stack();
        this.counterKunjungan = 0;
    }

    public String getNamaKlinik() { return namaKlinik; }
    public String getAlamat() { return alamat; }
    public Stack getRiwayatKunjungan() { return riwayatKunjungan; }

    public void setNamaKlinik(String namaKlinik) {
        if (namaKlinik == null || namaKlinik.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama klinik tidak boleh kosong.");
        }
        this.namaKlinik = namaKlinik.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    public boolean tambahPasien(Pasien pasien) {
        if (pasien == null) {
            return false;
        }
        if (cariPasienById(pasien.getIdPasien()) != null) {
            System.out.println("[GAGAL] ID pasien " + pasien.getIdPasien() + " sudah terdaftar.");
            return false;
        }
        daftarPasien.add(pasien);
        System.out.println("[OK] Pasien " + pasien.getNama() + " ditambahkan.");
        return true;
    }

    public boolean tambahDokter(Dokter dokter) {
        if (dokter == null) {
            return false;
        }
        if (cariDokterById(dokter.getIdDokter()) != null) {
            System.out.println("[GAGAL] ID dokter " + dokter.getIdDokter() + " sudah terdaftar.");
            return false;
        }
        daftarDokter.add(dokter);
        System.out.println("[OK] Dokter " + dokter.getNama() + " ditambahkan.");
        return true;
    }

    private Pasien cariPasienById(String id) {
        for (Pasien p : daftarPasien) {
            if (p.getIdPasien().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    private Dokter cariDokterById(String id) {
        for (Dokter d : daftarDokter) {
            if (d.getIdDokter().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    public Pasien cariPasien(String kunci) {
        for (Pasien p : daftarPasien) {
            if (p.getIdPasien().equalsIgnoreCase(kunci) || p.getNama().equalsIgnoreCase(kunci)) {
                return p;
            }
        }
        return null;
    }

    public Dokter cariDokter(String kunci) {
        for (Dokter d : daftarDokter) {
            if (d.getIdDokter().equalsIgnoreCase(kunci) || d.getNama().equalsIgnoreCase(kunci)) {
                return d;
            }
        }
        return null;
    }

    /** Mencari kunjungan berdasarkan ID. */
    public Kunjungan cariKunjungan(String id) {
        for (Kunjungan k : daftarKunjungan) {
            if (k.getIdKunjungan().equalsIgnoreCase(id)) {
                return k;
            }
        }
        return null;
    }

    
    public boolean buatKunjungan(String idPasien, String idDokter, String tanggal, String keluhan) {
        if (tanggal == null || tanggal.trim().isEmpty() || keluhan == null || keluhan.trim().isEmpty()) {
            System.out.println("[GAGAL] Tanggal dan keluhan tidak boleh kosong.");
            return false;
        }
        Pasien pasien = cariPasienById(idPasien);
        if (pasien == null) {
            System.out.println("[GAGAL] Pasien dengan ID " + idPasien + " tidak ditemukan.");
            return false;
        }
        Dokter dokter = cariDokterById(idDokter);
        if (dokter == null) {
            System.out.println("[GAGAL] Dokter dengan ID " + idDokter + " tidak ditemukan.");
            return false;
        }
        
        if (!dokter.bisaMenerimaKunjungan()) {
            System.out.println("[GAGAL] " + dokter.getNama() + " sudah mencapai batas "
                    + Dokter.MAKS_KUNJUNGAN + " kunjungan hari ini.");
            return false;
        }
        if (riwayatKunjungan.isFull()) {
            System.out.println("[GAGAL] Riwayat (Stack) penuh. Batalkan kunjungan terakhir terlebih dahulu.");
            return false;
        }

        counterKunjungan++;
        String id = String.format("K%03d", counterKunjungan);
        Kunjungan k = new Kunjungan(id, pasien, dokter, tanggal.trim(), keluhan.trim(), 0);
        k.setBiaya(k.hitungBiaya());

        dokter.tambahKunjungan();
        pasien.tambahJumlahKunjungan();
        daftarKunjungan.add(k);
        riwayatKunjungan.push(k);
        System.out.printf("[OK] Kunjungan %s dibuat. Biaya: Rp%,.0f%n", id, k.getBiaya());
        return true;
    }

    public boolean batalkanKunjunganTerakhir() {
        Kunjungan k = riwayatKunjungan.pop();
        if (k == null) {
            return false;
        }
        k.batalkan();
        System.out.println("[OK] Kunjungan " + k.getIdKunjungan() + " dibatalkan.");
        return true;
    }

    public void tampilkanSemuaPasien() {
        System.out.println("=== Daftar Pasien ===");
        if (daftarPasien.isEmpty()) {
            System.out.println("(belum ada data)");
        }
        for (Pasien p : daftarPasien) {
            p.tampilkanInfo();
        }
    }

    public void tampilkanSemuaDokter() {
        System.out.println("=== Daftar Dokter ===");
        if (daftarDokter.isEmpty()) {
            System.out.println("(belum ada data)");
        }
        for (Dokter d : daftarDokter) {
            d.tampilkanInfo();
        }
    }

    public void tampilkanSemuaKunjungan() {
        System.out.println("=== Daftar Kunjungan ===");
        if (daftarKunjungan.isEmpty()) {
            System.out.println("(belum ada data)");
        }
        for (Kunjungan k : daftarKunjungan) {
            k.tampilkanInfo();
        }
    }

    public void resetKunjunganHarianSemuaDokter() {
        for (Dokter d : daftarDokter) {
            d.resetKunjunganHarian();
        }
        System.out.println("[OK] Hitungan kunjungan harian semua dokter di-reset.");
    }


    public void tampilkanLaporan() {
        double total = 0;
        int aktif = 0;
        for (Kunjungan k : daftarKunjungan) {
            if (!Kunjungan.STATUS_DIBATALKAN.equals(k.getStatus())) {
                total += k.getBiaya();
                aktif++;
            }
        }
        System.out.println("=== Laporan " + namaKlinik + " ===");
        System.out.println("Alamat              : " + alamat);
        System.out.println("Jumlah pasien       : " + daftarPasien.size());
        System.out.println("Jumlah dokter       : " + daftarDokter.size());
        System.out.println("Jumlah kunjungan    : " + daftarKunjungan.size() + " (aktif: " + aktif + ")");
        System.out.printf("Total biaya (aktif) : Rp%,.0f%n", total);
        System.out.println("Isi riwayat (Stack) : " + riwayatKunjungan.size() + "/" + riwayatKunjungan.getKapasitas());
    }

}
