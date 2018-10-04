package com.cropaccounting.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/*import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
*/import javax.persistence.ManyToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.cropaccounting.util.UnitEnum;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Entity
@PersistenceUnit(name = "default")
@Data
@Accessors (chain = true)
public class Crop {
	@Id
	@GeneratedValue
	private long id;
	//@NonNull
	private String name;

	private String customerId;

	private long portalid;

	private String type;
	private long crop;
	private long varity;
	
	private String landOwner;
	private String landDagNo;
	
	@Transient
	private String cropId;
	@Transient
	private String variety;
	
	private long cropLifeCyle;
	
	private int cropUnit;
	private int cropLandQuantity;
	private BigDecimal landTotalInDefault;
	private String cropCast;
	private String groupId;
	private String agreementId;
	private String description;
	private LocalDate startDate;
	@Transient
	private String startDateString;
	
	private String locations;
	@ManyToOne(cascade = { CascadeType.ALL })
	private Farmer farmer;
	/*
	 * @ManyToMany(cascade = {CascadeType.ALL}) private List<DocDescType>
	 * listOfDocDescType;
	 */
	
	public void setVariety(String variety) {
		this.variety = variety;
		try {
			this.varity = Long.parseLong(variety);
		} catch (NumberFormatException ex) {}
	}
	
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
		try {
			final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate startDate = LocalDate.parse(startDateString, dtf);
			this.startDate = startDate;
		} catch (DateTimeParseException ex) {}
	}
}
