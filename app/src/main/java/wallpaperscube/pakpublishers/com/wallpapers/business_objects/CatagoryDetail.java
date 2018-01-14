package wallpaperscube.pakpublishers.com.wallpapers.business_objects;

/**
 * Created by Asad on 8/21/2016.
 */
public class CatagoryDetail {
    public String ID ;
    public String catagoryid ;

    public String getCatagoryfolder() {
        return catagoryfolder;
    }

    public void setCatagoryfolder(String catagoryfolder) {
        this.catagoryfolder = catagoryfolder;
    }

    public String catagoryfolder ;
    public String itempath;
    public String views ;
    public String creationtime ;
    public String description ;
    public String deleted ;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCatagoryid() {
        return catagoryid;
    }

    public void setCatagoryid(String catagoryid) {
        this.catagoryid = catagoryid;
    }

    public String getItempath() {
        return itempath;
    }

    public void setItempath(String itempath) {
        this.itempath = itempath;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
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

