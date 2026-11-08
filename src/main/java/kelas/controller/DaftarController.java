package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;
import kelas.util.GambarUtil;

public class DaftarController {

    private static final double LEBAR_FOTO = 120;
    private static final double TINGGI_FOTO = 90;

    @FXML
    private TableView<Produk> tabel;
    @FXML
    private TableColumn<Produk, String> fotoCol;
    @FXML
    private TableColumn<Produk, String> kodeCol;
    @FXML
    private TableColumn<Produk, String> namaCol;
    @FXML
    private TableColumn<Produk, String> kategoriCol;
    @FXML
    private TableColumn<Produk, Long> hargaCol;
    @FXML
    private TextField cariField;
    @FXML
    private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;
    private final ObservableList<Produk> dataTampilan = FXCollections.observableArrayList();
    private final Map<String, Image> cacheFoto = new HashMap<>();

    @FXML
    private void initialize() {
        fotoCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("gambarUri"));
        kodeCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("kode"));
        namaCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("nama"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<Produk, String>("kategori"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<Produk, Long>("harga"));

        // Kolom foto: tampilkan gambar kecil produk.
        fotoCol.setSortable(false);
        fotoCol.setCellFactory(kolom -> new TableCell<Produk, String>() {
            private final ImageView view = new ImageView();

            {
                view.setFitWidth(LEBAR_FOTO);
                view.setFitHeight(TINGGI_FOTO);
                view.setPreserveRatio(true);
                setAlignment(Pos.CENTER);
            }

            @Override
            protected void updateItem(String uri, boolean kosong) {
                super.updateItem(uri, kosong);
                if (kosong) {
                    setGraphic(null);
                    return;
                }
                String kunci = String.valueOf(uri);
                Image gambar = cacheFoto.get(kunci);
                if (gambar == null) {
                    gambar = GambarUtil.muat(uri, LEBAR_FOTO * 2, TINGGI_FOTO * 2);
                    cacheFoto.put(kunci, gambar);
                }
                view.setImage(gambar);
                setGraphic(view);
            }
        });

        NumberFormat rupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        hargaCol.setCellFactory(kolom -> new TableCell<Produk, Long>() {
            @Override
            protected void updateItem(Long nilai, boolean kosong) {
                super.updateItem(nilai, kosong);
                setText(kosong || nilai == null ? null : rupiah.format(nilai));
            }
        });

        tabel.setFixedCellSize(TINGGI_FOTO + 14);
        tabel.setRowFactory(t -> {
            TableRow<Produk> baris = new TableRow<>();
            baris.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !baris.isEmpty()) {
                    detail();
                }
            });
            return baris;
        });
        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada produk yang sesuai."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);

        for (Produk produk : data.getDaftar()) {
            String nama = produk.getNama().toLowerCase(Locale.ROOT);
            if (nama.contains(kata)) {
                dataTampilan.add(produk);
            }
        }
        pesanLabel.setText("Jumlah produk yang tampil: " + dataTampilan.size()
                + ". Pilih produk dari fotonya, lalu klik Lihat Detail (atau klik dua kali).");
    }

    @FXML
    private void resetPencarian() {
        cariField.clear();
        cari();
    }

    @FXML
    private void tambah() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void detail() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            pesanLabel.setText("Pilih satu produk dahulu.");
            return;
        }
        try {
            navigator.bukaDetail(produk);
        } catch (IOException e) {
            pesanLabel.setText("Detail gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void hapus() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            pesanLabel.setText("Pilih satu produk dahulu.");
            return;
        }
        // Pop up konfirmasi kecil sebelum produk benar-benar dihapus.
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus produk \"" + produk.getNama() + "\" (" + produk.getKode() + ")?",
                ButtonType.YES, ButtonType.NO);
        dialog.setTitle("Konfirmasi Hapus");
        dialog.setHeaderText(null);
        dialog.initOwner(tabel.getScene().getWindow());
        ButtonType jawaban = dialog.showAndWait().orElse(ButtonType.NO);
        if (jawaban == ButtonType.YES) {
            data.hapus(produk);
            cari();
            pesanLabel.setText("Produk \"" + produk.getNama() + "\" berhasil dihapus. "
                    + "Jumlah produk yang tampil: " + dataTampilan.size());
        }
    }
}
