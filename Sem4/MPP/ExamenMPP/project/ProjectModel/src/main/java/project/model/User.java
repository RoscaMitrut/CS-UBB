package project.model;

		import jakarta.persistence.*;
		import jakarta.persistence.Entity;

		import java.io.Serializable;
		import java.util.Objects;

@Entity
@Table(name = "useri")
public class User implements Serializable {
	public User(String alias) {
		this.alias = alias;
	}

	public User() {
	this.id=0;
	this.alias=null;
	}
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;
	@Basic
	@Column(name = "alias")
	private String alias;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(alias, user.alias);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, alias);
	}
}
