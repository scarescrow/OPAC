package roy.trial.opac;

public class book {

	private int id, year, copies;
	private String name, author, shelf, publisher;
	
	public book() {}
	
	public book(String id, String name, String author, String year, String shelf, String publisher, String copies) {
		this.id = Integer.parseInt(id);
		this.name = name;
		this.author = author;
		this.year = Integer.parseInt(year);
		this.shelf = shelf;
		this.publisher = publisher;
		this.copies = Integer.parseInt(copies);
	}
	
	public String id() {
		return String.valueOf(this.id);
	}
	
	public String name() {
		return String.valueOf(this.name);
	}
	
	public String author() {
		return String.valueOf(this.author);
	}
	
	public String year() {
		return String.valueOf(this.year);
	}
	
	public String shelf() {
		return String.valueOf(this.shelf);
	}
	
	public String publisher() {
		return String.valueOf(this.publisher);
	}
	
	public String copies() {
		return String.valueOf(this.copies);
	}

}
