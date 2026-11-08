package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Produk;

public class DataProduk {

   
    public static final String[] KATEGORI = {"Action Figure", "Boneka", "Mobil-mobilan", "Puzzle"};

    private final ObservableList<Produk> daftar = FXCollections.observableArrayList();
    private int urutan = 1;

    public DataProduk() {
        tambah("Gojo Satoru", "Action Figure", 125000, resource("gojo.png"));
        tambah("Boneka", "Boneka", 85000, resource("boneka.png"));
        tambah("Hot Wheels", "Mobil-mobilan", 60000, resource("mobil.png"));
        tambah("Rubik", "Puzzle", 45000, resource("rubik.png"));
    }

    private static String resource(String namaFile) {
        return DataProduk.class.getResource("/kelas/images/" + namaFile).toExternalForm();
    }

    public ObservableList<Produk> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    public Produk tambah(String nama, String kategori, long harga, String gambarUri) {
        Produk p = new Produk(String.format("PR%03d", urutan), nama, kategori, harga, gambarUri);
        daftar.add(p);
        urutan++;
        return p;
    }

    public void hapus(Produk p) {
        daftar.remove(p);
    }
}
