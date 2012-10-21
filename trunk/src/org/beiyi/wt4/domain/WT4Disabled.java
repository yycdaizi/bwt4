package org.beiyi.wt4.domain;

/**
 * WT4Disabled entity. @author MyEclipse Persistence Tools
 */

public class WT4Disabled  implements java.io.Serializable {


    // Fields    

     private Integer BWt4DisabledId;
     private Integer BWt4Id;
     private String disabledDiagnoseName;
     private String disabledDiagnoseCode;
     private Short sf0101;


    // Constructors

    /** default constructor */
    public WT4Disabled() {
    }

    
    /** full constructor */
    public WT4Disabled(Integer BWt4Id, String disabledDiagnoseName, String disabledDiagnoseCode, Short sf0101) {
        this.BWt4Id = BWt4Id;
        this.disabledDiagnoseName = disabledDiagnoseName;
        this.disabledDiagnoseCode = disabledDiagnoseCode;
        this.sf0101 = sf0101;
    }

   
    // Property accessors

    public Integer getBWt4DisabledId() {
        return this.BWt4DisabledId;
    }
    
    public void setBWt4DisabledId(Integer BWt4DisabledId) {
        this.BWt4DisabledId = BWt4DisabledId;
    }

    public Integer getBWt4Id() {
        return this.BWt4Id;
    }
    
    public void setBWt4Id(Integer BWt4Id) {
        this.BWt4Id = BWt4Id;
    }

    public String getDisabledDiagnoseName() {
        return this.disabledDiagnoseName;
    }
    
    public void setDisabledDiagnoseName(String disabledDiagnoseName) {
        this.disabledDiagnoseName = disabledDiagnoseName;
    }

    public String getDisabledDiagnoseCode() {
        return this.disabledDiagnoseCode;
    }
    
    public void setDisabledDiagnoseCode(String disabledDiagnoseCode) {
        this.disabledDiagnoseCode = disabledDiagnoseCode;
    }

    public Short getSf0101() {
        return this.sf0101;
    }
    
    public void setSf0101(Short sf0101) {
        this.sf0101 = sf0101;
    }
   








}