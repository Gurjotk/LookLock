package brdevelopers.com.jobvibe;
public class EnityInternshipViewJob {

    String JobTitle;
    String Location;
    String CompanyName;

    public EnityInternshipViewJob(String jobTitle, String location, String companyName) {
        JobTitle = jobTitle;
        Location = location;
        CompanyName = companyName;
    }


    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

}
