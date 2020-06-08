package brdevelopers.com.jobvibe;
public class EnityInternshipViewJob {

    String JobTitle;
    String Location;
    String CompanyName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getInternName() {
        return interName;
    }

    public void setInternName(String internName) {
        this.interName = internName;
    }

    String rootName;
    String interName;

    public EnityInternshipViewJob(String jobTitle, String location, String companyName, String Id,String RootName,String InterName) {
        JobTitle = jobTitle;
        Location = location;
        CompanyName = companyName;
        id = Id;
        rootName=RootName;
        interName=InterName;
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
