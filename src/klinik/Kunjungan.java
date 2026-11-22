package klinik;

/**
 * Transaksi utama: kunjungan seorang Pasien ke seorang Dokter.
 */
public class Kunjungan {
    public static final String STATUS_TERDAFTAR = "TERDAFTAR";
    public static final String STATUS_SELESAI = "SELESAI";
    public static final String STATUS_DIBATALKAN = "DIBATALKAN";
    /** Tarif dasar konsultasi dalam rupiah. */
    public static final double TARIF_DASAR = 50000;
    /** Diskon untuk pasien lansia (20 persen). */
    public static final double DISKON_LANSIA = 0.20;

    private String idKunjungan;
    private Pasien pasien;
    private Dokter dokter;
    private String tanggal;
    private String keluhan;
    private double biaya;
    private String status;

    /** Konstruktor default. */
    public Kunjungan() {
        this.idKunjungan = "-";
        this.pasien = null;
        this.dokter = null;
        this.tanggal = "-";
        this.keluhan = "-";
        this.biaya = 0;
        this.status = STATUS_TERDAFTAR;
    }

    /** Konstruktor berparameter, validasi lewat setter. */
    public Kunjungan(String idKunjungan, Pasien pasien, Dokter dokter,
                     String tanggal, String keluhan, double biaya) {
        this();
        this.idKunjungan = idKunjungan;
        setPasien(pasien);
        setDokter(dokter);
        this.tanggal = tanggal;
        this.keluhan = keluhan;
        setBiaya(biaya);
    }

    public String getIdKunjungan() { return idKunjungan; }
    public Pasien getPasien() { return pasien; }
    public Dokter getDokter() { return dokter; }
    public String getTanggal() { return tanggal; }
    public String getKeluhan() { return keluhan; }
    public double getBiaya() { return biaya; }
    public String getStatus() { return status; }

    public void setPasien(Pasien pasien) {
        if (pasien == null) {
            throw new IllegalArgumentException("Pasien tidak boleh null.");
        }
        this.pasien = pasien;
    }

    public void setDokter(Dokter dokter) {
        if (dokter == null) {
            throw new IllegalArgumentException("Dokter tidak boleh null.");
        }
        this.dokter = dokter;
    }

    public void setBiaya(double biaya) {
        if (biaya < 0) {
            throw new IllegalArgumentException("Biaya tidak boleh negatif.");
        }
        this.biaya = biaya;
    }

    public void setStatus(String status) {
        if (!STATUS_TERDAFTAR.equals(status) && !STATUS_SELESAI.equals(status)
                && !STATUS_DIBATALKAN.equals(status)) {
            throw new IllegalArgumentException("Status tidak dikenal: " + status);
        }
        this.status = status;
    }

    /** Mengubah status dengan validasi; kunjungan yang sudah dibatalkan tidak bisa diubah. */
    public void ubahStatus(String statusBaru) {
        if (STATUS_DIBATALKAN.equals(this.status)) {
            throw new IllegalArgumentException("Kunjungan sudah dibatalkan, status tidak bisa diubah.");
        }
        setStatus(statusBaru);
    }

    /** Membatalkan kunjungan dan mengembalikan hitungan dokter serta pasien. */
    public void batalkan() {
        if (STATUS_DIBATALKAN.equals(this.status)) {
            return;
        }
        this.status = STATUS_DIBATALKAN;
        if (dokter != null) {
            dokter.kurangiKunjungan();
        }
        if (pasien != null) {
            pasien.kurangiJumlahKunjungan();
        }
    }

    /** Biaya = tarif dasar, dikurangi diskon 20 persen jika pasien lansia. */
    public double hitungBiaya() {
        double total = TARIF_DASAR;
        if (pasien != null && pasien.isLansia()) {
            total = total - (total * DISKON_LANSIA);
        }
        return total;
    }

    public void tampilkanInfo() {
        System.out.printf("%s | %s | Pasien: %s | Dokter: %s | Keluhan: %s | Biaya: Rp%,.0f | %s%n",
                idKunjungan, tanggal, pasien.getNama(), dokter.getNama(), keluhan, biaya, status);
    }

}