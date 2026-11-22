package klinik;

import java.util.ArrayList;

public class Stack {
    public static final int KAPASITAS_DEFAULT = 10;

    private ArrayList<Kunjungan> elemen;
    private int kapasitas;

    public Stack() {
        this(KAPASITAS_DEFAULT);
    }

    public Stack(int kapasitas) {
        this.elemen = new ArrayList<>();
        this.kapasitas = (kapasitas > 0) ? kapasitas : KAPASITAS_DEFAULT;
    }

    public boolean push(Kunjungan kunjungan) {
        if (kunjungan == null) {
            System.out.println("[Stack] Data kosong, tidak bisa di-push.");
            return false;
        }
        if (isFull()) {
            System.out.println("[Stack] GAGAL push: stack penuh (kapasitas " + kapasitas + ").");
            return false;
        }
        elemen.add(kunjungan);
        return true;
    }

    public Kunjungan pop() {
        if (isEmpty()) {
            System.out.println("[Stack] GAGAL pop: stack kosong.");
            return null;
        }
        return elemen.remove(elemen.size() - 1);
    }

    public Kunjungan peek() {
        if (isEmpty()) {
            System.out.println("[Stack] Stack kosong, tidak ada elemen puncak.");
            return null;
        }
        return elemen.get(elemen.size() - 1);
    }

    public boolean isEmpty() { return elemen.isEmpty(); }
    public boolean isFull() { return elemen.size() >= kapasitas; }
    public int size() { return elemen.size(); }
    public int getKapasitas() { return kapasitas; }

    public boolean contains(Kunjungan kunjungan) {
        return elemen.contains(kunjungan);
    }

    public void tampilkan() {
        System.out.println("Isi Stack (" + size() + "/" + kapasitas + "), puncak di atas:");
        if (isEmpty()) {
            System.out.println("  (kosong)");
            return;
        }
        for (int i = elemen.size() - 1; i >= 0; i--) {
            Kunjungan k = elemen.get(i);
            String tanda = (i == elemen.size() - 1) ? "  -> " : "     ";
            System.out.println(tanda + k.getIdKunjungan() + " | " + k.getPasien().getNama()
                    + " | " + k.getDokter().getNama() + " | " + k.getStatus());
        }
    }
}