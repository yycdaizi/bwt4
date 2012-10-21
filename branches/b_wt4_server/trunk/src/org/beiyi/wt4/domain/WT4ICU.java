package org.beiyi.wt4.domain;

import java.sql.Timestamp;


/**
 * WT4ICU entity. @author MyEclipse Persistence Tools
 */

public class WT4ICU  implements java.io.Serializable {


    // Fields    

     private Integer BWt4IcuId;
     private Integer BWt4Id;
     private String unitcode;
     private Timestamp indate;
     private Timestamp outdate;


    // Constructors

    /** default constructor */
    public WT4ICU() {
    }

    
    /** full constructor */
    public WT4ICU(Integer BWt4Id, String unitcode, Timestamp indate, Timestamp outdate) {
        this.BWt4Id = BWt4Id;
        this.unitcode = unitcode;
        this.indate = indate;
        this.outdate = outdate;
    }

   
    // Property accessors

    public Integer getBWt4IcuId() {
        return this.BWt4IcuId;
    }
    
    public void setBWt4IcuId(Integer BWt4IcuId) {
        this.BWt4IcuId = BWt4IcuId;
    }

    public Integer getBWt4Id() {
        return this.BWt4Id;
    }
    
    public void setBWt4Id(Integer BWt4Id) {
        this.BWt4Id = BWt4Id;
    }

    public String getUnitcode() {
        return this.unitcode;
    }
    
    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public Timestamp getIndate() {
        return this.indate;
    }
    
    public void setIndate(Timestamp indate) {
        this.indate = indate;
    }

    public Timestamp getOutdate() {
        return this.outdate;
    }
    
    public void setOutdate(Timestamp outdate) {
        this.outdate = outdate;
    }
   








}