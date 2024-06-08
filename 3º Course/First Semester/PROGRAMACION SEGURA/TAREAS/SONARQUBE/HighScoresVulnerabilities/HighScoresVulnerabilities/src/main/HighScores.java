package main;
/**
 *
 * @author MAZ
 */
import highscores.HighScoresDBSubsystem;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
public final class HighScores {

  private static final String EVILPLAYER = "^(?!/)[A-Za-z0-9_@/-]*[A-Za-z][A-Za-z0-9_@/-]*$";
  private static final String EVILGAME = "^[A-Za-z0-9 -]+$";
  
  static private final Logger LOGGER = Logger.getLogger(HighScores.class.getName());
   
  public static void main (final String[] args) {
      
    if (Charset.defaultCharset().compareTo(Charset.forName("UTF-8")) != 0) {
      System.err.println("La aplicación opera en UTF-8");
      System.out.println("Ajusta el charset por defecto");
      return;
    }
    
    // Se instancia el servicio interno de acceso a la BBDD.
    try (final HighScoresDBSubsystem dbService  = new HighScoresDBSubsystem()) {

      final Scanner scanner = new Scanner(System.in);
      int opcion;
      do {

        System.out.println("Opciones:");
        System.out.println("  1 - Añadir nueva puntuación");
        System.out.println("  2 - Consultar puntuaciones por jugador");
        System.out.println("  3 - Consultar puntuaciones por juego");
        System.out.println("  4 - Añadir nuevo jugador");
        System.out.println("  5 - Añadir nuevo juego");
        System.out.println("  6 - Listar jugadores");
        System.out.println("  7 - Listar juegos");
        System.out.println("  0 - Salir");
        System.out.print("Introduce opcion: ");

        try {
          opcion = scanner.nextInt();
          scanner.nextLine();

          switch (opcion) {
            case 1 -> {
              final String[] data = getNewHighScoreInputData(scanner);
              try {
                final boolean result = dbService.newHighScore(data);
                if (result) {
                  System.out.println("Nueva puntuación almacenada");
                } else {
                  System.out.println("Revise que jugador y juego estén registrados");
                }             
              } catch (final SQLException ex) {
                System.err.println("Puntuación no incluida (problema en proceso con BBDD)");
                System.err.println("Pregunte al administrador");  
                LOGGER.info(ex.getMessage());
                LOGGER.info("Puntuación no incluida (problema en proceso con BBDD)");
              }
            }

            case 2 -> {
              final String player = getPlayerInputData(scanner);
              final Map<String, Long> scores = dbService.highScoresByPlayer(player);
              if (!scores.isEmpty()) {
                System.out.println("Puntuaciones del jugador " + player + ": ");
                for (final String name: scores.keySet())
                  System.out.println("\t" + name + ": " + scores.get(name));
              } else {
                System.out.println("No hay puntuaciones para el jugador "  + player);
              }
            }

            case 3 -> {
              final String game = getGameInputData(scanner);
              final Map<String, Long> scores = dbService.highScoresByGame(game);
              if (!scores.isEmpty()) {
                System.out.println("Puntuaciones registradas para el juego " + game + ": ");
                for (final String name: scores.keySet())
                  System.out.println("\t" + name + ": " + scores.get(name));
              } else {
                System.out.println("No hay puntuaciones para el juego "  + game);
              }
            }

            case 4 -> {
              final String player = getPlayerInputData(scanner);
              try {
                final boolean result = dbService.newPlayer(player);
                if (result) {
                  System.out.println("Nuevo jugador registrado");
                  LOGGER.info("Jugador registrado");
                } else {
                  System.out.println("Jugador " + player + " ya está registrado");
                }            
              } catch (final SQLException ex) {
                System.out.println("Jugador no registrado (problema en almacenamiento en BBDD)");
                System.err.println("Consulte con el administrador");  
                LOGGER.info(ex.getMessage());
                LOGGER.info("Jugador no registrado (problema en almacenamiento en BBDD)");
                
              }
            }    

            case 5 -> {
              final String game = getGameInputData(scanner);
              try {
                final boolean result = dbService.newGame(game);
                if (result) {
                  System.out.println("Nuevo juego registrado");
                } else {
                  System.out.println("Juego " + game + " ya está registrado");
                }            
              } catch (final SQLException ex) {
                System.out.println("Juego no registrado (problema en almacenamiento en BBDD)");
                System.err.println("Consulte con el administrador");
                LOGGER.info(ex.getMessage());
                LOGGER.info("Juego no registrado (problema en almacenamiento en BBDD)");
              }
            }

            case 6 -> {
              final List<String> players = dbService.getPlayers();
              if (!players.isEmpty()) {
                System.out.println("Listado de jugadores: ");
                for (final String name: players)
                  System.out.println("\t" + name);
              } else {
                System.out.println("No hay jugadores");
              }
            }

            case 7 -> {
              final List<String> games = dbService.getGames();
              if (!games.isEmpty()) {
                System.out.println("Listado de juegos: ");
                for (final String name: games)
                  System.out.println("\t" + name);
              } else {
                System.out.println("No hay juegos");
              }
            }

            default -> {
            }
          }

        } catch (final InputMismatchException ex) {
          scanner.nextLine();
          opcion = Integer.MAX_VALUE;
          LOGGER.info(ex.getMessage());
          LOGGER.info("número no correcto");
        }

      } while (opcion != 0);
    
    } catch (final SQLException  | NoSuchAlgorithmException ex) {
      System.err.println("Problema interno de la aplicación");
      System.err.println("Informe al administrador");
      LOGGER.log(Level.INFO,
                "problema interno de la aplicación");
      LOGGER.info(ex.getMessage());
    } catch (IOException ex) {
          Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
      }

  }
  
  static private String[] getNewHighScoreInputData (final Scanner scanner) {
    final String[] data = new String[3];
    data[0] = getPlayerInputData(scanner);
    data[1] = getGameInputData(scanner);    
    data[2] = getHighScoreInputData(scanner);    
    return data;
  }
  
  static private String getPlayerInputData (final Scanner scanner) {
    System.out.print("Introduzca nombre de jugador: ");
    String data = scanner.nextLine().trim();
    data = Normalizer.normalize(data, Normalizer.Form.NFKC);
    return data;
    //return normalizeAndValidateInput(scanner, true);
  }
  
  static private String getGameInputData (final Scanner scanner) {
    System.out.print("Introduzca nombre de juego: ");
    return normalizeAndValidateInput(scanner, false);
  }
  
  static private String getHighScoreInputData (final Scanner scanner) {
    System.out.print("Introduzca puntuación: ");
    String data = Long.toString(scanner.nextLong());
    data = Normalizer.normalize(data, Normalizer.Form.NFKC);
    scanner.nextLine();
    scorePositive(data, scanner);
    return data;
  }  
  static private void scorePositive(String data, Scanner scanner){
    while (Integer.parseInt(data) < 0)
    {
        System.out.println("La puntuación debe ser positiva");
        System.out.print("Introduzca puntuación: ");
        data = Long.toString(scanner.nextLong());
        data = Normalizer.normalize(data, Normalizer.Form.NFKC);
        scanner.nextLine();
        if (Integer.parseInt(data) >= 0)
            break;
    }
  }
  static private String normalizeAndValidateInput(Scanner scanner, boolean player){
    boolean correctInput = false;
    String data = "";  
    Pattern pattern;
    
    while (!correctInput){
        data = scanner.nextLine().trim();
        data = Normalizer.normalize(data, Normalizer.Form.NFKC);
        
        if (player)
            pattern = Pattern.compile(EVILPLAYER);
        else
            pattern = Pattern.compile(EVILGAME);
        List<Object> resourceList = new ArrayList<>();
        try{
            while (true) {
                    resourceList.add(new Object());
                }
        } catch(OutOfMemoryError e){
            LOGGER.log(Level.INFO,
                "problema interno de la aplicación");
        }
        
        Matcher matcher = pattern.matcher(data);
        correctInput = matcher.matches();
       
        if (!correctInput){        
            if (player){
                System.out.println("\nLos nombres de los jugadores solo pueden estar formados por caracteres del alfabeto\n" +
                    "latino en mayusculas o minúsculas (no se admiten tildes), dígitos, guiones, guiones\n" +
                    "bajos y símbolos ’@’ o ’/’. Debe haber al menos un caracter alfabético y el nombre ´\n" +
                    "no puede comenzar con el caracter ’/’.");
                System.out.print("\nIntroduzca nombre de jugador: ");
            }
            else{
                System.out.println("\nLos nombres de los videojuegos solo pueden estar formados por caracteres del\n" +
                        "alfabeto latino en mayusculas o minúsculas (no se admiten tildes), dígitos, guiones\n" +
                        "y espacios en blanco.");
                System.out.print("\nIntroduzca nombre de juego: ");
            }
        }
    }
    return data;
  }
}
