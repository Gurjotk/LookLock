package brdevelopers.com.jobvibe;

import java.util.List;

public class EntityAdminJobApplied {
    String JobTitle;
    String Location;
    String CompanyName;
    List<Model_User> users;

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

    public EntityAdminJobApplied(String jobTitle, String location, String companyName, String Id,String RootName,String InterName, List<Model_User> users) {
        JobTitle = jobTitle;
        Location = location;
        CompanyName = companyName;
        id = Id;
        rootName=RootName;
        interName=InterName;
        this.users = users;
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
