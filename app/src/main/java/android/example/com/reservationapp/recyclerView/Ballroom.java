package android.example.com.reservationapp.recyclerView;

public class Ballroom {
    private String nama;
    private int rating,harga;
    private String imgUrl;

    public Ballroom() {
        //empty constructor needed
    }

    public Ballroom(String nama, int rating, int harga, String imgUrl) {
        this.nama = nama;
        this.rating = rating;
        this.harga = harga;
        this.imgUrl = imgUrl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
