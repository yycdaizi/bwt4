package org.beiyi.wt4.domain;



/**
 * WT4Surgery entity. @author MyEclipse Persistence Tools
 */

public class WT4Surgery  implements java.io.Serializable {


    // Fields    

     private WT4SurgeryId id;


    // Constructors

    /** default constructor */
    public WT4Surgery() {
    }

    
    /** full constructor */
    public WT4Surgery(WT4SurgeryId id) {
        this.id = id;
    }

   
    // Property accessors

    public WT4SurgeryId getId() {
        return this.id;
    }
    
    public void setId(WT4SurgeryId id) {
        this.id = id;
    }
   








}