package com.devworm.edufree;

import com.google.firebase.Timestamp;

public class commentModel {
    String othersname,othercomments,othersprofilepic,id;
    Timestamp timestamp;

    public commentModel(String othersname, String othercomments, String othersprofilepic, String id, Timestamp timestamp) {
        this.othersname = othersname;
        this.othercomments = othercomments;
        this.othersprofilepic = othersprofilepic;
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getOthersname() {
        return othersname;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOthersname(String othersname) {
        this.othersname = othersname;
    }

    public String getOthercomments() {
        return othercomments;
    }

    public commentModel() {
    }

    public void setOthercomments(String othercomments) {
        this.othercomments = othercomments;
    }

    public String getOthersprofilepic() {
        return othersprofilepic;
    }

    public void setOthersprofilepic(String othersprofilepic) {
        this.othersprofilepic = othersprofilepic;
    }

}
