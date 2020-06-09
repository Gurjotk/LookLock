package brdevelopers.com.jobvibe;
public class EntityInternship {
    String country;
    String companyName;



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    public EntityInternship(String country, String companyName) {
        this.country = country;
        this.companyName = companyName;
    }






}
