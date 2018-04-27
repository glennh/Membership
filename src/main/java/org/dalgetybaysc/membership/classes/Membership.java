package org.dalgetybaysc.membership.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "memberships")
public class Membership {

    public enum MembershipStatus {
        CURRENT,
        OLD
    }
    public enum MembershipClass {
        SAILING,
        JOINT_SAILING,
        SOCIAL,
        JOINT_SOCIAL,
        FAMILY,
        JUNIOR,
        OUTPORT,
        ASSOCIATE
    };
    private Integer id;
    private Integer mainMemberId;
    private MembershipClass memClass;
    private Date joinedDate;
    private Date leftDate;
    private String comment;
    private MembershipStatus status;

    public Membership() {

    }

    public Membership(Integer id, Integer mainMemberberId, MembershipClass memClass, Date joinedDate, String comment) {
        this.id = id;
        this.mainMemberId = mainMemberberId;
        this.joinedDate = joinedDate;
        this.leftDate = null;
        this.comment = comment;
        this.memClass = memClass;
        this.status = MembershipStatus.CURRENT;
    }

    @Id
    public int getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMainMemberId() {
        return this.mainMemberId;
    }

    public void setMainMemberId(Integer mainMemberId) {
        this.mainMemberId = mainMemberId;
    }

    @Enumerated(EnumType.STRING)
    public MembershipClass getMemClass() {
        return this.memClass;
    }

    public void setMemClass(MembershipClass memClass) {
        this.memClass = memClass;
    }

    public Date getJoinedDate() {
        return this.joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Date getLeftDate() {
        return this.leftDate;
    }

    public void setLeftDate(Date leftDate) {
        this.joinedDate = leftDate;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Enumerated(EnumType.STRING)
    public MembershipStatus getStatus() {
        return this.status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + ", Main member id: " + getMainMemberId() +
                ", Membership class: " + getMemClass() + ", Joined date: " + getJoinedDate() +
                ", Left date: " + getLeftDate() + ", Comment: " + getComment();
    }
}
