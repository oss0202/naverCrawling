public class CrwalingDto {
    private String country;
    private String sale;
    private String link;

    public CrwalingDto(String country, String sale, String link) {
        this.country = country;
        this.sale = sale;
        this.link = link;
    }

    public String getCountry() {
        return country;
    }

    public String getSale() {
        return sale;
    }

    public String getLink() {
        return link;
    }
}
