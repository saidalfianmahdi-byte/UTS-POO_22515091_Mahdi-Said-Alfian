package klinik;

import java.util.Scanner;


public class Main {
    private static Klinik klinik = new Klinik("Klinik Sehat Sentosa", "Jl. Contoh No. 1");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilihan = bacaInt("Pilih menu: ");
            System.out.println();
            switch (pilihan) {
                case 1: menuPasien(); break;
                case 2: menuDokter(); break;
                case 3: menuKunjungan(); break;
                case 4: menuCari(); break;
                case 5: menuStack(); break;
                case 6: menuLaporan(); break;
                case 7:
                    System.out.println("Terima kasih. Program selesai.");
                    jalan = false;
                    break;
                default:
                    System.out.println("[GAGAL] Menu tidak tersedia, pilih 1-7.");
            }
        }
    }

    public static void tampilkanMenu() {
        System.out.println();
        System.out.println("===== KLINIK RAWAT JALAN =====");
        System.out.println("1. Pasien (tambah / tampilkan)");
        System.out.println("2. Dokter (tambah / tampilkan / reset harian)");
        System.out.println("3. Buat kunjungan");
        System.out.println("4. Cari data");
        System.out.println("5. Riwayat kunjungan (Stack)");
        System.out.println("6. Laporan");
        System.out.println("7. Keluar");
    }

    public static void menuPasien() {
        System.out.println("1. Tambah pasien  2. Tampilkan semua pasien");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            String id = bacaString("ID pasien: ");
            String nama = bacaString("Nama: ");
            int umur = bacaInt("Umur: ");
            String jk = bacaString("Jenis kelamin (L/P): ");
            try {
                klinik.tambahPasien(new Pasien(id, nama, umur, jk));
            } catch (IllegalArgumentException e) {
                System.out.println("[GAGAL] " + e.getMessage());
            }
        } else if (p == 2) {
            klinik.tampilkanSemuaPasien();
        } else {
            System.out.println("[GAGAL] Pilihan tidak valid.");
        }
    }

    public static void menuDokter() {
        System.out.println("1. Tambah dokter  2. Tampilkan semua dokter  3. Reset kunjungan harian");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            String id = bacaString("ID dokter: ");
            String nama = bacaString("Nama: ");
            String spesialis = bacaString("Spesialisasi: ");
            try {
                klinik.tambahDokter(new Dokter(id, nama, spesialis));
            } catch (IllegalArgumentException e) {
                System.out.println("[GAGAL] " + e.getMessage());
            }
        } else if (p == 2) {
            klinik.tampilkanSemuaDokter();
        } else if (p == 3) {
            klinik.resetKunjunganHarianSemuaDokter();
        } else {
            System.out.println("[GAGAL] Pilihan tidak valid.");
        }
    }

    public static void menuKunjungan() {
        System.out.println("1. Buat kunjungan  2. Tampilkan semua kunjungan");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            String idPasien = bacaString("ID pasien: ");
            String idDokter = bacaString("ID dokter: ");
            String tanggal = bacaString("Tanggal (contoh 2026-11-22): ");
            String keluhan = bacaString("Keluhan: ");
            klinik.buatKunjungan(idPasien, idDokter, tanggal, keluhan);
        } else if (p == 2) {
            klinik.tampilkanSemuaKunjungan();
        } else {
            System.out.println("[GAGAL] Pilihan tidak valid.");
        }
    }

    public static void menuCari() {
        System.out.println("1. Cari pasien  2. Cari dokter");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            Pasien ps = klinik.cariPasien(bacaString("Kode atau nama pasien: "));
            if (ps == null) {
                System.out.println("[INFO] Pasien tidak ditemukan.");
            } else {
                ps.tampilkanInfo();
            }
        } else if (p == 2) {
            Dokter d = klinik.cariDokter(bacaString("Kode atau nama dokter: "));
            if (d == null) {
                System.out.println("[INFO] Dokter tidak ditemukan.");
            } else {
                d.tampilkanInfo();
            }
        } else {
            System.out.println("[GAGAL] Pilihan tidak valid.");
        }
    }

    public static void menuStack() {
        Stack s = klinik.getRiwayatKunjungan();
        System.out.println("1. Push  2. Pop (batalkan kunjungan terakhir)  3. Peek");
        System.out.println("4. isEmpty  5. isFull  6. Size  7. Tampilkan isi");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1:
                Kunjungan k = klinik.cariKunjungan(bacaString("ID kunjungan: "));
                if (k == null) {
                    System.out.println("[GAGAL] Kunjungan tidak ditemukan.");
                } else if (Kunjungan.STATUS_DIBATALKAN.equals(k.getStatus())) {
                    System.out.println("[GAGAL] Kunjungan sudah dibatalkan.");
                } else if (s.contains(k)) {
                    System.out.println("[GAGAL] Kunjungan sudah ada di stack.");
                } else if (s.push(k)) {
                    System.out.println("[OK] Kunjungan " + k.getIdKunjungan() + " di-push.");
                }
                break;
            case 2: klinik.batalkanKunjunganTerakhir(); break;
            case 3:
                Kunjungan atas = s.peek();
                if (atas != null) {
                    System.out.print("Puncak stack: ");
                    atas.tampilkanInfo();
                }
                break;
            case 4: System.out.println("isEmpty: " + s.isEmpty()); break;
            case 5: System.out.println("isFull : " + s.isFull()); break;
            case 6: System.out.println("size   : " + s.size()); break;
            case 7: break;
            default:
                System.out.println("[GAGAL] Pilihan tidak valid.");
                return;
        }
        System.out.println();
        s.tampilkan();
    }

    public static void menuLaporan() {
        klinik.tampilkanLaporan();
    }

    private static String bacaBaris() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput berakhir. Program selesai.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    public static int bacaInt(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = bacaBaris().trim();
            try {
                return Integer.parseInt(teks);
            } catch (NumberFormatException e) {
                System.out.println("[GAGAL] Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    public static String bacaString(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = bacaBaris().trim();
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.println("[GAGAL] Input tidak boleh kosong. Coba lagi.");
        }
    }

    public static double bacaDouble(String pesan) {
        while (true) {
            System.out.print(pesan);
            String teks = bacaBaris().trim();
            try {
                return Double.parseDouble(teks);
            } catch (NumberFormatException e) {
                System.out.println("[GAGAL] Input harus berupa angka. Coba lagi.");
            }
        }
    }
}