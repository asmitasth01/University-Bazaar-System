package example.com.bazaar.bean;

/**
 * Created by sumanshrestha on 11/30/16.
 */

public class ClubInfo {
    private String clubName;
    private String clubCategory;
    private String clubDescription;
    private String clubAdmin;

    public ClubInfo(String clubName, String clubCategory, String clubDescription, String clubAdmin) {
        this.clubName = clubName;
        this.clubCategory = clubCategory;
        this.clubDescription = clubDescription;
        this.clubAdmin = clubAdmin;
    }

    public ClubInfo() {

    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubCategory() {
        return clubCategory;
    }

    public void setClubCategory(String clubCategory) {
        this.clubCategory = clubCategory;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getClubAdmin() {
        return clubAdmin;
    }

    public void setClubAdmin(String clubAdmin) {
        this.clubAdmin = clubAdmin;
    }
}
