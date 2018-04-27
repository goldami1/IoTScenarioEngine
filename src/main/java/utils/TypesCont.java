package utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "TYPESCONTS")
@Inheritance(strategy = InheritanceType.JOINED)
public class TypesCont
{
	@Id
	@Column(name = "typescont_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	@JoinTable(name = "TYPESPOSSIBLEVALS", joinColumns=@JoinColumn(name = "typescont_id"))
	@GenericGenerator(name = "hilo-gen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "type_val_idx") }, generator = "hilo-gen", type = @org.hibernate.annotations.Type(type = "long"))
	@SerializedName("types")
	List<String> types = new ArrayList<>();
	
	public TypesCont(Long id, List<String> types) {
		super();
		this.id = id;
		this.types = types;
	}
	
	public TypesCont() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
}
