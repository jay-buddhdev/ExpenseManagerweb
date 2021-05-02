package Project;

import java.util.Date;

public class Transaction {

    Date tdatetime;
    String t_cat;
    Double tot_inc;
    Double tot_exp;
    Long uid;

    public Transaction() {

    }

    public Transaction(Date tdatetime, String t_cat, Double tot_inc, Double tot_exp, Long uid) {
        this.tdatetime = tdatetime;
        this.t_cat = t_cat;
        this.tot_inc = tot_inc;
        this.tot_exp = tot_exp;
        this.uid = uid;
    }

    public Date getTdatetime() {
        return tdatetime;
    }

    public void setTdatetime(Date tdatetime) {
        this.tdatetime = tdatetime;
    }

    public String getT_cat() {
        return t_cat;
    }

    public void setT_cat(String t_cat) {
        this.t_cat = t_cat;
    }

    public Double getTot_inc() {
        return tot_inc;
    }

    public void setTot_inc(Double tot_inc) {
        this.tot_inc = tot_inc;
    }

    public Double getTot_exp() {
        return tot_exp;
    }

    public void setTot_exp(Double tot_exp) {
        this.tot_exp = tot_exp;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
