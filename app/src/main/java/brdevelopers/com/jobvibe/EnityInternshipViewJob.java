package brdevelopers.com.jobvibe;
public class EnityInternshipViewJob {

    String JobTitle;
    String Location;
    String CompanyName;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    String dateTime;

    public String getSavedJob() {
        return savedJob;
    }

    public void setSavedJob(String savedJob) {
        this.savedJob = savedJob;
    }

    String savedJob;


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
    String buttonView;
    public String getButtonView() {
        return buttonView;
    }

    public void setButtonView(String buttonView) {
        this.buttonView = buttonView;
    }



    public EnityInternshipViewJob(String jobTitle, String location, String companyName, String Id,String RootName,String InterName,String DateTime,String SavedJob,String ButtonView) {
        JobTitle = jobTitle;
        Location = location;
        CompanyName = companyName;
        id = Id;
        rootName=RootName;
        interName=InterName;
        dateTime=DateTime;
        savedJob=SavedJob;
        buttonView=ButtonView;
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
