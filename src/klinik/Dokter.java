package klinik;


public class Dokter {
  
    public static final int MAKS_KUNJUNGAN = 5;

    private String idDokter;
    private String nama;
    private String spesialisasi;
    private int kunjunganHariIni;

    public Dokter() {
        this.idDokter = "-";
        this.nama = "-";
        this.spesialisasi = "Umum";
        this.kunjunganHariIni = 0;
    }

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

    public boolean bisaMenerimaKunjungan() {
        return kunjunganHariIni < MAKS_KUNJUNGAN;
    }

    public boolean tambahKunjungan() {
        if (!bisaMenerimaKunjungan()) {
            return false;
        }
        kunjunganHariIni++;
        return true;
    }

   
    public void kurangiKunjungan() {
        if (kunjunganHariIni > 0) {
            kunjunganHariIni--;
        }
    }

   
    public void resetKunjunganHarian() {
        kunjunganHariIni = 0;
    }

    public void tampilkanInfo() {
        System.out.printf("ID: %-6s | Nama: %-15s | Spesialis: %-12s | Kunjungan hari ini: %d/%d%n",
                idDokter, nama, spesialisasi, kunjunganHariIni, MAKS_KUNJUNGAN);
    }

}
