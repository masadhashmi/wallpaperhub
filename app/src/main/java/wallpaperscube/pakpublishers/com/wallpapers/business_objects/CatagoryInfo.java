package wallpaperscube.pakpublishers.com.wallpapers.business_objects;

/**
 * Created by Asad on 8/19/2016.
 */
public class CatagoryInfo {
    public String ID ;
    public String catagoryname ;
    public String catagoryfolder;
    public String count ;
    public String creationupdatedtime ;
    public String description ;
    public String deleted ;

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String ImageURL ;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCatagoryname() {
        return catagoryname;
    }

    public void setCatagoryname(String catagoryname) {
        this.catagoryname = catagoryname;
    }

    public String getCatagoryfolder() {
        return catagoryfolder;
    }

    public void setCatagoryfolder(String catagoryfolder) {
        this.catagoryfolder = catagoryfolder;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCreationupdatedtime() {
        return creationupdatedtime;
    }

    public void setCreationupdatedtime(String creationupdatedtime) {
        this.creationupdatedtime = creationupdatedtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }
}
