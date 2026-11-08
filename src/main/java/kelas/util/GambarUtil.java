package kelas.util;

import javafx.scene.image.Image;

public class GambarUtil {

    private static final String KOSONG = "/kelas/images/kosong.png";
    
    public static Image muat(String uri, double lebar, double tinggi) {
        if (uri != null) {
            Image image = new Image(uri, lebar, tinggi, true, true);
            if (!image.isError()) {
                return image;
            }
        }
        String kosong = GambarUtil.class.getResource(KOSONG).toExternalForm();
        return new Image(kosong, lebar, tinggi, true, true);
    }
}
