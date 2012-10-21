package org.beiyi.wt4.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * WT4SurgeryId entity. @author MyEclipse Persistence Tools
 */

public class WT4SurgeryId  implements java.io.Serializable {


    // Fields    

     private BigDecimal BWt4SurgeryId;
     private Long BWt4Id;
     private Timestamp created;
     private Long createdby;
     private Timestamp updated;
     private Long updatedby;
     private String isactive;
     private String operationCode;
     private Timestamp operationDate;
     private String operationName;
     private String operationDoctor;
     private String firstAssistant;
     private String secondAssistant;
     private String hocus;
     private String closeup;
     private String hocusDoctor;
     private String nurse;
     private String isprimary;
     private String isoperation;
     private String isshushi;
     private BigDecimal flgIndex;
     private Timestamp operationEdate;
     private String operationLevel;


    // Constructors

    /** default constructor */
    public WT4SurgeryId() {
    }

	/** minimal constructor */
    public WT4SurgeryId(BigDecimal BWt4SurgeryId) {
        this.BWt4SurgeryId = BWt4SurgeryId;
    }
    
    /** full constructor */
    public WT4SurgeryId(BigDecimal BWt4SurgeryId, Long BWt4Id, Timestamp created, Long createdby, Timestamp updated, Long updatedby, String isactive, String operationCode, Timestamp operationDate, String operationName, String operationDoctor, String firstAssistant, String secondAssistant, String hocus, String closeup, String hocusDoctor, String nurse, String isprimary, String isoperation, String isshushi, BigDecimal flgIndex, Timestamp operationEdate, String operationLevel) {
        this.BWt4SurgeryId = BWt4SurgeryId;
        this.BWt4Id = BWt4Id;
        this.created = created;
        this.createdby = createdby;
        this.updated = updated;
        this.updatedby = updatedby;
        this.isactive = isactive;
        this.operationCode = operationCode;
        this.operationDate = operationDate;
        this.operationName = operationName;
        this.operationDoctor = operationDoctor;
        this.firstAssistant = firstAssistant;
        this.secondAssistant = secondAssistant;
        this.hocus = hocus;
        this.closeup = closeup;
        this.hocusDoctor = hocusDoctor;
        this.nurse = nurse;
        this.isprimary = isprimary;
        this.isoperation = isoperation;
        this.isshushi = isshushi;
        this.flgIndex = flgIndex;
        this.operationEdate = operationEdate;
        this.operationLevel = operationLevel;
    }

   
    // Property accessors

    public BigDecimal getBWt4SurgeryId() {
        return this.BWt4SurgeryId;
    }
    
    public void setBWt4SurgeryId(BigDecimal BWt4SurgeryId) {
        this.BWt4SurgeryId = BWt4SurgeryId;
    }

    public Long getBWt4Id() {
        return this.BWt4Id;
    }
    
    public void setBWt4Id(Long BWt4Id) {
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

    public String getOperationCode() {
        return this.operationCode;
    }
    
    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public Timestamp getOperationDate() {
        return this.operationDate;
    }
    
    public void setOperationDate(Timestamp operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationName() {
        return this.operationName;
    }
    
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationDoctor() {
        return this.operationDoctor;
    }
    
    public void setOperationDoctor(String operationDoctor) {
        this.operationDoctor = operationDoctor;
    }

    public String getFirstAssistant() {
        return this.firstAssistant;
    }
    
    public void setFirstAssistant(String firstAssistant) {
        this.firstAssistant = firstAssistant;
    }

    public String getSecondAssistant() {
        return this.secondAssistant;
    }
    
    public void setSecondAssistant(String secondAssistant) {
        this.secondAssistant = secondAssistant;
    }

    public String getHocus() {
        return this.hocus;
    }
    
    public void setHocus(String hocus) {
        this.hocus = hocus;
    }

    public String getCloseup() {
        return this.closeup;
    }
    
    public void setCloseup(String closeup) {
        this.closeup = closeup;
    }

    public String getHocusDoctor() {
        return this.hocusDoctor;
    }
    
    public void setHocusDoctor(String hocusDoctor) {
        this.hocusDoctor = hocusDoctor;
    }

    public String getNurse() {
        return this.nurse;
    }
    
    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    public String getIsprimary() {
        return this.isprimary;
    }
    
    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public String getIsoperation() {
        return this.isoperation;
    }
    
    public void setIsoperation(String isoperation) {
        this.isoperation = isoperation;
    }

    public String getIsshushi() {
        return this.isshushi;
    }
    
    public void setIsshushi(String isshushi) {
        this.isshushi = isshushi;
    }

    public BigDecimal getFlgIndex() {
        return this.flgIndex;
    }
    
    public void setFlgIndex(BigDecimal flgIndex) {
        this.flgIndex = flgIndex;
    }

    public Timestamp getOperationEdate() {
        return this.operationEdate;
    }
    
    public void setOperationEdate(Timestamp operationEdate) {
        this.operationEdate = operationEdate;
    }

    public String getOperationLevel() {
        return this.operationLevel;
    }
    
    public void setOperationLevel(String operationLevel) {
        this.operationLevel = operationLevel;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof WT4SurgeryId) ) return false;
		 WT4SurgeryId castOther = ( WT4SurgeryId ) other; 
         
		 return ( (this.getBWt4SurgeryId()==castOther.getBWt4SurgeryId()) || ( this.getBWt4SurgeryId()!=null && castOther.getBWt4SurgeryId()!=null && this.getBWt4SurgeryId().equals(castOther.getBWt4SurgeryId()) ) )
 && ( (this.getBWt4Id()==castOther.getBWt4Id()) || ( this.getBWt4Id()!=null && castOther.getBWt4Id()!=null && this.getBWt4Id().equals(castOther.getBWt4Id()) ) )
 && ( (this.getCreated()==castOther.getCreated()) || ( this.getCreated()!=null && castOther.getCreated()!=null && this.getCreated().equals(castOther.getCreated()) ) )
 && ( (this.getCreatedby()==castOther.getCreatedby()) || ( this.getCreatedby()!=null && castOther.getCreatedby()!=null && this.getCreatedby().equals(castOther.getCreatedby()) ) )
 && ( (this.getUpdated()==castOther.getUpdated()) || ( this.getUpdated()!=null && castOther.getUpdated()!=null && this.getUpdated().equals(castOther.getUpdated()) ) )
 && ( (this.getUpdatedby()==castOther.getUpdatedby()) || ( this.getUpdatedby()!=null && castOther.getUpdatedby()!=null && this.getUpdatedby().equals(castOther.getUpdatedby()) ) )
 && ( (this.getIsactive()==castOther.getIsactive()) || ( this.getIsactive()!=null && castOther.getIsactive()!=null && this.getIsactive().equals(castOther.getIsactive()) ) )
 && ( (this.getOperationCode()==castOther.getOperationCode()) || ( this.getOperationCode()!=null && castOther.getOperationCode()!=null && this.getOperationCode().equals(castOther.getOperationCode()) ) )
 && ( (this.getOperationDate()==castOther.getOperationDate()) || ( this.getOperationDate()!=null && castOther.getOperationDate()!=null && this.getOperationDate().equals(castOther.getOperationDate()) ) )
 && ( (this.getOperationName()==castOther.getOperationName()) || ( this.getOperationName()!=null && castOther.getOperationName()!=null && this.getOperationName().equals(castOther.getOperationName()) ) )
 && ( (this.getOperationDoctor()==castOther.getOperationDoctor()) || ( this.getOperationDoctor()!=null && castOther.getOperationDoctor()!=null && this.getOperationDoctor().equals(castOther.getOperationDoctor()) ) )
 && ( (this.getFirstAssistant()==castOther.getFirstAssistant()) || ( this.getFirstAssistant()!=null && castOther.getFirstAssistant()!=null && this.getFirstAssistant().equals(castOther.getFirstAssistant()) ) )
 && ( (this.getSecondAssistant()==castOther.getSecondAssistant()) || ( this.getSecondAssistant()!=null && castOther.getSecondAssistant()!=null && this.getSecondAssistant().equals(castOther.getSecondAssistant()) ) )
 && ( (this.getHocus()==castOther.getHocus()) || ( this.getHocus()!=null && castOther.getHocus()!=null && this.getHocus().equals(castOther.getHocus()) ) )
 && ( (this.getCloseup()==castOther.getCloseup()) || ( this.getCloseup()!=null && castOther.getCloseup()!=null && this.getCloseup().equals(castOther.getCloseup()) ) )
 && ( (this.getHocusDoctor()==castOther.getHocusDoctor()) || ( this.getHocusDoctor()!=null && castOther.getHocusDoctor()!=null && this.getHocusDoctor().equals(castOther.getHocusDoctor()) ) )
 && ( (this.getNurse()==castOther.getNurse()) || ( this.getNurse()!=null && castOther.getNurse()!=null && this.getNurse().equals(castOther.getNurse()) ) )
 && ( (this.getIsprimary()==castOther.getIsprimary()) || ( this.getIsprimary()!=null && castOther.getIsprimary()!=null && this.getIsprimary().equals(castOther.getIsprimary()) ) )
 && ( (this.getIsoperation()==castOther.getIsoperation()) || ( this.getIsoperation()!=null && castOther.getIsoperation()!=null && this.getIsoperation().equals(castOther.getIsoperation()) ) )
 && ( (this.getIsshushi()==castOther.getIsshushi()) || ( this.getIsshushi()!=null && castOther.getIsshushi()!=null && this.getIsshushi().equals(castOther.getIsshushi()) ) )
 && ( (this.getFlgIndex()==castOther.getFlgIndex()) || ( this.getFlgIndex()!=null && castOther.getFlgIndex()!=null && this.getFlgIndex().equals(castOther.getFlgIndex()) ) )
 && ( (this.getOperationEdate()==castOther.getOperationEdate()) || ( this.getOperationEdate()!=null && castOther.getOperationEdate()!=null && this.getOperationEdate().equals(castOther.getOperationEdate()) ) )
 && ( (this.getOperationLevel()==castOther.getOperationLevel()) || ( this.getOperationLevel()!=null && castOther.getOperationLevel()!=null && this.getOperationLevel().equals(castOther.getOperationLevel()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBWt4SurgeryId() == null ? 0 : this.getBWt4SurgeryId().hashCode() );
         result = 37 * result + ( getBWt4Id() == null ? 0 : this.getBWt4Id().hashCode() );
         result = 37 * result + ( getCreated() == null ? 0 : this.getCreated().hashCode() );
         result = 37 * result + ( getCreatedby() == null ? 0 : this.getCreatedby().hashCode() );
         result = 37 * result + ( getUpdated() == null ? 0 : this.getUpdated().hashCode() );
         result = 37 * result + ( getUpdatedby() == null ? 0 : this.getUpdatedby().hashCode() );
         result = 37 * result + ( getIsactive() == null ? 0 : this.getIsactive().hashCode() );
         result = 37 * result + ( getOperationCode() == null ? 0 : this.getOperationCode().hashCode() );
         result = 37 * result + ( getOperationDate() == null ? 0 : this.getOperationDate().hashCode() );
         result = 37 * result + ( getOperationName() == null ? 0 : this.getOperationName().hashCode() );
         result = 37 * result + ( getOperationDoctor() == null ? 0 : this.getOperationDoctor().hashCode() );
         result = 37 * result + ( getFirstAssistant() == null ? 0 : this.getFirstAssistant().hashCode() );
         result = 37 * result + ( getSecondAssistant() == null ? 0 : this.getSecondAssistant().hashCode() );
         result = 37 * result + ( getHocus() == null ? 0 : this.getHocus().hashCode() );
         result = 37 * result + ( getCloseup() == null ? 0 : this.getCloseup().hashCode() );
         result = 37 * result + ( getHocusDoctor() == null ? 0 : this.getHocusDoctor().hashCode() );
         result = 37 * result + ( getNurse() == null ? 0 : this.getNurse().hashCode() );
         result = 37 * result + ( getIsprimary() == null ? 0 : this.getIsprimary().hashCode() );
         result = 37 * result + ( getIsoperation() == null ? 0 : this.getIsoperation().hashCode() );
         result = 37 * result + ( getIsshushi() == null ? 0 : this.getIsshushi().hashCode() );
         result = 37 * result + ( getFlgIndex() == null ? 0 : this.getFlgIndex().hashCode() );
         result = 37 * result + ( getOperationEdate() == null ? 0 : this.getOperationEdate().hashCode() );
         result = 37 * result + ( getOperationLevel() == null ? 0 : this.getOperationLevel().hashCode() );
         return result;
   }   





}