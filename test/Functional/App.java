import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        List<List<Movie>> movies = new ArrayList<>();

        List<Movie> actionMovies = new ArrayList<>();
        actionMovies.add(new Movie("The Dark Knight", "Action", 9, 2008));
        actionMovies.add(new Movie("The Matrix", "Action", 8, 1999));
        actionMovies.add(new Movie("Gladiator", "Action", 8, 2000));
        actionMovies.add(new Movie("Saving Private Ryan", "Action", 8, 1998));
        actionMovies.add(new Movie("Braveheart", "Action", 8, 1995));
        actionMovies.add(new Movie("Inception", "Action", 8, 2010));
        actionMovies.add(new Movie("Interstellar", "Action", 8, 2014));

        List<Movie> dramaMovies = new ArrayList<>();
        dramaMovies.add(new Movie("Fight Club", "Drama", 8, 1999));
        dramaMovies.add(new Movie("The Shawshank Redemption", "Drama", 9, 1994));

        List<Movie> sciFiMovies = new ArrayList<>();
        sciFiMovies.add(new Movie("Inception", "Sci-Fi", 8, 2010));
        sciFiMovies.add(new Movie("Interstellar", "Sci-Fi", 8, 2014));
        sciFiMovies.add(new Movie("The Matrix", "Sci-Fi", 8, 1999));

        movies.add(actionMovies);
        movies.add(dramaMovies);
        movies.add(sciFiMovies);


        Predicate<Movie> isAction = movie -> movie.getGenre().equals("Action");
        // System.out.println(isAction.test(actionMovies.get(0)));

        // use the isAction predicate to filter the action movies
        List<Movie> filteredMovies = actionMovies.stream().filter(isAction).collect(Collectors.toList());
        System.out.println(filteredMovies);

        List<String> filteredMovies2 = movies.stream().flatMap(movie -> movie.stream()).map(elem -> elem.getName()).collect(Collectors.toList());
        System.out.println(filteredMovies2);

        List<List<Movie>> oldMovies = movies.stream().map(movie -> movie.stream().filter(elem -> elem.getYear() < 2000).collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println(oldMovies);

        
        String strinfOfNames = actionMovies.stream().map(movie -> movie.getName()).collect(Collectors.joining(", "));
        System.out.println(strinfOfNames);

        List<Movie> m = movies.stream().flatMap(movie -> movie.stream()).collect(Collectors.toList());
        System.out.println(m);

        
        
    }

    // public static List<Movie> getMovieNames(Predicate<Movie> predicate) {

    // }
    }

// }
