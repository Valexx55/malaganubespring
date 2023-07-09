package edu.arelance.nube.dto;
/*
 * {
    "categories": [],
    "created_at": "2020-01-05 13:42:24.40636",
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "rNG1-KjWTwSqoyJqaOfBwQ",
    "updated_at": "2020-01-05 13:42:24.40636",
    "url": "https://api.chucknorris.io/jokes/rNG1-KjWTwSqoyJqaOfBwQ",
    "value": "Chuck Norris counts his chickens before he eats them."
}
 */

public class FraseChuckNorris {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FraseChuckNorris(String value) {
		super();
		this.value = value;
	}

	public FraseChuckNorris() {
		super();
	}

	@Override
	public String toString() {
		return "FraseChuckNorris [value=" + value + "]";
	}
	
	

}
