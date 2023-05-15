public class Movie {
    String name;
    String genre;
    int rating;
    int year;

    public Movie(String name, String genre, int rating, int year) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
    }

    // create getters, setters, toString(), equals() and hashCode() here

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Movie) {
            Movie movie = (Movie) obj;
            return name.equals(movie.name) &&
                    genre.equals(movie.genre) &&
                    rating == movie.rating &&
                    year == movie.year;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + genre.hashCode() + rating + year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
