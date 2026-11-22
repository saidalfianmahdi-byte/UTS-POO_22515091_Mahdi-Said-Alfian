package klinik;

public class Pasien {
    private String idPasien;
    private String nama;
    private int umur;
    private String jenisKelamin;
    private int jumlahKunjungan;

    public Pasien() {
        this.idPasien = "-";
        this.nama = "-";
        this.umur = 0;
        this.jenisKelamin = "L";
        this.jumlahKunjungan = 0;
    }

    public Pasien(String idPasien, String nama, int umur, String jenisKelamin) {
        this();
        setIdPasien(idPasien);
        setNama(nama);
        setUmur(umur);
        setJenisKelamin(jenisKelamin);
    }

    public String getIdPasien() { return idPasien; }
    public String getNama() { return nama; }
    public int getUmur() { return umur; }
    public String getJenisKelamin() { return jenisKelamin; }
    public int getJumlahKunjungan() { return jumlahKunjungan; }

    public void setIdPasien(String idPasien) {
        if (idPasien == null || idPasien.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pasien tidak boleh kosong.");
        }
        this.idPasien = idPasien.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pasien tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }


    public void setUmur(int umur) {
        if (umur < 0 || umur > 120) {
            throw new IllegalArgumentException("Umur pasien harus antara 0 sampai 120.");
        }
        this.umur = umur;
    }

    public void setJenisKelamin(String jenisKelamin) {
        if (jenisKelamin == null) {
            throw new IllegalArgumentException("Jenis kelamin tidak boleh kosong.");
        }
        String jk = jenisKelamin.trim().toUpperCase();
        if (!jk.equals("L") && !jk.equals("P")) {
            throw new IllegalArgumentException("Jenis kelamin harus L atau P.");
        }
        this.jenisKelamin = jk;
    }

    public void tampilkanInfo() {
        System.out.printf("ID: %-6s | Nama: %-15s | Umur: %3d | JK: %s | Kunjungan: %d%n",
                idPasien, nama, umur, jenisKelamin, jumlahKunjungan);
    }

    public void tambahJumlahKunjungan() {
        this.jumlahKunjungan++;
    }

    public void kurangiJumlahKunjungan() {
        if (this.jumlahKunjungan > 0) {
            this.jumlahKunjungan--;
        }
    }

    public boolean isLansia() {
        return umur >= 60;
    }

}
