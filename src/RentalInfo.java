import java.util.HashMap;

public class RentalInfo {
    
  private static final String REGULAR = "regular";
  private static final String NEW = "new";
  private static final String CHILDRENS = "childrens";

  public String statement(Customer customer) {
    
    // Creamos las peliculas
    HashMap<String, Movie> movies = createMovies();

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    
    // Usamos un StringBuilder
    StringBuilder result = new StringBuilder();
    result.append("Rental Record for ").append(customer.getName()).append("\n");
    
    for (MovieRental r : customer.getRentals()) {
        
      // Calcular monto por cada pelicula
      double thisAmount = calculateAmount(movies.get(r.getMovieId()), r.getDays());

      // Calcular puntos de bonificacion por cada pelicula
      frequentEnterPoints += calculateFrequentEnterPoints(movies.get(r.getMovieId()), r.getDays());

      // Añadir cifras por cada pelicula
      result.append("\t").append(movies.get(r.getMovieId()).getTitle()).append("\t").append(thisAmount).append("\n");

      totalAmount += thisAmount;
    }
    // Añadir lineas finales
    result.append("Amount owed is ").append(totalAmount).append("\n");
    result.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

    return result.toString();
  }
  
  private HashMap<String, Movie> createMovies() {
    HashMap<String, Movie> movies = new HashMap();
    movies.put("F001", new Movie("You've Got Mail", REGULAR));
    movies.put("F002", new Movie("Matrix", REGULAR));
    movies.put("F003", new Movie("Cars", CHILDRENS));
    movies.put("F004", new Movie("Fast & Furious X", NEW));
    return movies;
  }
  
  private double calculateAmount(Movie movie, int days) {
      
    double thisAmount = 0;

    switch (movie.getCode()) {
        case REGULAR:
            thisAmount = 2;
            if (days > 2) {
                thisAmount += (days - 2) * 1.5;
            }       break;
        case NEW:
            thisAmount = days * 3;
            break;
        case CHILDRENS:
            thisAmount = 1.5;
            if (days > 3) {
                thisAmount += (days - 3) * 1.5;
            }       break;
        default:
            break;
    }

    return thisAmount;
  }
  
  private int calculateFrequentEnterPoints(Movie movie, int daysRented) {
    int points = 1;

    if (movie.getCode().equals(NEW) && daysRented > 2) {
      points++;
    }

    return points;
  }
  
}
