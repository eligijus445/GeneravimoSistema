package Backend;

public class WorkAct {

    int id;
    String number;
    String description;
    String startTimeDate;
    String endTimeDate;
    String createdTime;
    String fileLocation;
    int firmId;
    int clientId;
    int userID;

    public WorkAct() {
    }

    public void addWorkAct(int id, String number, String description, String startTimeDate, String endTimeDate, String createdTime, String fileLocation, int firmId, int clientId, int userID) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.startTimeDate = startTimeDate;
        this.endTimeDate = endTimeDate;
        this.createdTime = createdTime;
        this.fileLocation = fileLocation;
        this.firmId = firmId;
        this.clientId = clientId;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTimeDate() {
        return startTimeDate;
    }

    public void setStartTimeDate(String startTimeDate) {
        this.startTimeDate = startTimeDate;
    }

    public String getEndTimeDate() {
        return endTimeDate;
    }

    public void setEndTimeDate(String endTimeDate) {
        this.endTimeDate = endTimeDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public int getFirmId() {
        return firmId;
    }

    public void setFirmId(int firmId) {
        this.firmId = firmId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "WorkAct{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", description='" + description + '\'' +
                ", startTimeDate='" + startTimeDate + '\'' +
                ", endTimeDate='" + endTimeDate + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", firmId=" + firmId +
                ", clientId=" + clientId +
                ", userID=" + userID +
                '}';
    }
}
