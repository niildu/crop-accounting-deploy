package com.cropaccounting.beans;

import javax.xml.bind.annotation.XmlTransient;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Accessors(chain =true)
public class FarmerDetails {
	/** Extracted data from bar code */
	@NonNull
	String name;

	/** Supplier found in matched bar code rule */
	@NonNull
	String nid;
	
	@NonNull
	String mobileNo;

	@NonNull
	long id;
	
	@XmlTransient
	public String getName() {
		return name;
	}
	
	@XmlTransient
	public String getMobileNo() {
		return mobileNo;
	}

	@XmlTransient
	public String getNid() {
		return nid;
	}
	
	@XmlTransient
	public long getId() {
		return id;
	}
}
