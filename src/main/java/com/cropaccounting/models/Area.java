package com.cropaccounting.models;

import javax.persistence.*;

import lombok.Data;
/*import play.db.jpa.Model;
import play.modules.chronostamp.NoChronostamp;*/

@Entity

//@Table(name="Area",schema="cropaccounting")
@PersistenceUnit(name = "default")
@Data
public class Area /*extends Model*/
{
	@Id
	private long id;
	private String areaType;
}
