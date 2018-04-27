package utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TypesCont
{
	@Id
	private Long id;
	
	@ElementCollection
	List<String> types = new ArrayList<>();
	
}
