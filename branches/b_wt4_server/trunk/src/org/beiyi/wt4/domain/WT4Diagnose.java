package org.beiyi.wt4.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * WT4Diagnose entity. @author MyEclipse Persistence Tools
 */

public class WT4Diagnose  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    // Fields    

	private BigDecimal BWt4DiagnoseId;
     private BigDecimal BWt4Id;
     private Timestamp created;
     private Long createdby;
     private Timestamp updated;
     private Long updatedby;
     private String isactive;
     private String diagnoseCode;
     private String diagnoseName;
     private String isprimary;
     private String cureEffect;
     private String inStatus;


    // Constructors

    /** default constructor */
    public WT4Diagnose() {
    }

	/** minimal constructor */
    public WT4Diagnose(BigDecimal BWt4Id) {
        this.BWt4Id = BWt4Id;
    }
    
    /** full constructor */
    public WT4Diagnose(BigDecimal BWt4Id, Timestamp created, Long createdby, Timestamp updated, Long updatedby, String isactive, String diagnoseCode, String diagnoseName, String isprimary, String cureEffect, String inStatus) {
        this.BWt4Id = BWt4Id;
        this.created = created;
        this.createdby = createdby;
        this.updated = updated;
        this.updatedby = updatedby;
        this.isactive = isactive;
        this.diagnoseCode = diagnoseCode;
        this.diagnoseName = diagnoseName;
        this.isprimary = isprimary;
        this.cureEffect = cureEffect;
        this.inStatus = inStatus;
    }

   
    // Property accessors

    public BigDecimal getBWt4DiagnoseId() {
        return this.BWt4DiagnoseId;
    }
    
    public void setBWt4DiagnoseId(BigDecimal BWt4DiagnoseId) {
        this.BWt4DiagnoseId = BWt4DiagnoseId;
    }

    public BigDecimal getBWt4Id() {
        return this.BWt4Id;
    }
    
    public void setBWt4Id(BigDecimal BWt4Id) {
        this.BWt4Id = BWt4Id;
    }

    public Timestamp getCreated() {
        return this.created;
    }
    
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Long getCreatedby() {
        return this.createdby;
    }
    
    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Timestamp getUpdated() {
        return this.updated;
    }
    
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Long getUpdatedby() {
        return this.updatedby;
    }
    
    public void setUpdatedby(Long updatedby) {
        this.updatedby = updatedby;
    }

    public String getIsactive() {
        return this.isactive;
    }
    
    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getDiagnoseCode() {
        return this.diagnoseCode;
    }
    
    public void setDiagnoseCode(String diagnoseCode) {
        this.diagnoseCode = diagnoseCode;
    }

    public String getDiagnoseName() {
        return this.diagnoseName;
    }
    
    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    public String getIsprimary() {
        return this.isprimary;
    }
    
    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public String getCureEffect() {
        return this.cureEffect;
    }
    
    public void setCureEffect(String cureEffect) {
        this.cureEffect = cureEffect;
    }

    public String getInStatus() {
        return this.inStatus;
    }
    
    public void setInStatus(String inStatus) {
        this.inStatus = inStatus;
    }
   








}