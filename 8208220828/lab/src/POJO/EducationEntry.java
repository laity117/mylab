package POJO;

// POJO for education entry
public class EducationEntry {
    private int id;
    private String dateRange;
    private String schoolName;
    private String degreeMajor;

    public EducationEntry(int id, String dateRange, String schoolName, String degreeMajor) {
        this.id = id;
        this.dateRange = dateRange;
        this.schoolName = schoolName;
        this.degreeMajor = degreeMajor;
    }

    // Getters and setters are required for Gson serialization
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDateRange() { return dateRange; }
    public void setDateRange(String dateRange) { this.dateRange = dateRange; }
    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
    public String getDegreeMajor() { return degreeMajor; }
    public void setDegreeMajor(String degreeMajor) { this.degreeMajor = degreeMajor; }
}