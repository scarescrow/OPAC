package roy.trial.opac;

public class book {

	private int id, year, copies;
	private String name, author, shelf, publisher;
	
	public book() {}
	
	public book(int id, String name, String author, int year, String shelf, String publisher, int copies) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.year = year;
		this.shelf = shelf;
		this.publisher = publisher;
		this.copies = copies;
	}

}
