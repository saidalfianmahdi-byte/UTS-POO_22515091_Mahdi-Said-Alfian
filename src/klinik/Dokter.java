package klinik;

/**
 * Entitas dokter. Menyimpan jumlah kunjungan hari ini dengan batas maksimal 5.
 */
public class Dokter {
    /** Batas kunjungan per dokter per hari (aturan bisnis). */
    public static final int MAKS_KUNJUNGAN = 5;

    private String idDokter;
    private String nama;
    private String spesialisasi;
    private int kunjunganHariIni;

    /** Konstruktor default. */
    public Dokter() {
        this.idDokter = "-";
        this.nama = "-";
        this.spesialisasi = "Umum";
        this.kunjunganHariIni = 0;
    }

    /** Konstruktor berparameter, validasi lewat setter. */
    public Dokter(String idDokter, String nama, String spesialisasi) {
        this();
        setIdDokter(idDokter);
        setNama(nama);
        setSpesialisasi(spesialisasi);
    }

    public String getIdDokter() { return idDokter; }
    public String getNama() { return nama; }
    public String getSpesialisasi() { return spesialisasi; }
    public int getKunjunganHariIni() { return kunjunganHariIni; }

    public void setIdDokter(String idDokter) {
        if (idDokter == null || idDokter.trim().isEmpty()) {
            throw new IllegalArgumentException("ID dokter tidak boleh kosong.");
        }
        this.idDokter = idDokter.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama dokter tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setSpesialisasi(String spesialisasi) {
        if (spesialisasi == null || spesialisasi.trim().isEmpty()) {
            throw new IllegalArgumentException("Spesialisasi tidak boleh kosong.");
        }
        this.spesialisasi = spesialisasi.trim();
    }

    /** True jika dokter masih boleh menerima kunjungan hari ini. */
    public boolean bisaMenerimaKunjungan() {
        return kunjunganHariIni < MAKS_KUNJUNGAN;
    }

    /** Menambah hitungan kunjungan; gagal (false) jika sudah mencapai batas. */
    public boolean tambahKunjungan() {
        if (!bisaMenerimaKunjungan()) {
            return false;
        }
        kunjunganHariIni++;
        return true;
    }

    /** Dipakai saat pembatalan kunjungan. */
    public void kurangiKunjungan() {
        if (kunjunganHariIni > 0) {
            kunjunganHariIni--;
        }
    }

    /** Reset hitungan saat ganti hari. */
    public void resetKunjunganHarian() {
        kunjunganHariIni = 0;
    }

    public void tampilkanInfo() {
        System.out.printf("ID: %-6s | Nama: %-15s | Spesialis: %-12s | Kunjungan hari ini: %d/%d%n",
                idDokter, nama, spesialisasi, kunjunganHariIni, MAKS_KUNJUNGAN);
    }

}